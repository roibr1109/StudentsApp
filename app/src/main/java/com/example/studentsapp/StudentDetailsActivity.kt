package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type

class StudentDetailsActivity : AppCompatActivity() {

    private val editStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK, result.data)
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { view, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val studentId = intent.getStringExtra("studentId")
        val student = StudentRepository.getStudentById(studentId!!)

        if (student != null) {
            // Populate the views with student details
            findViewById<TextView>(R.id.nameText).text = student.name
            findViewById<TextView>(R.id.idText).text = student.id
            findViewById<TextView>(R.id.phoneText).text = student.phone
            findViewById<TextView>(R.id.addressText).text = student.address
            findViewById<ImageView>(R.id.studentImage).setImageResource(student.imageResource)
            findViewById<CheckBox>(R.id.checkBox).isChecked = student.isChecked

            // Edit button navigation
            findViewById<Button>(R.id.editButton).setOnClickListener {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("student", student)
                editStudentLauncher.launch(intent)
            }
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            finish() // Exit the activity if student is null
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // Close the current activity and go back
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
