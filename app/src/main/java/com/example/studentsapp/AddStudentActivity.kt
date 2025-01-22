package com.example.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        enableEdgeToEdge()

        // Adjust padding for system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { view, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Handle the Save button click
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val nameInput = findViewById<EditText>(R.id.nameInput).text.toString()
            val idInput = findViewById<EditText>(R.id.idInput).text.toString()
            val phoneInput = findViewById<EditText>(R.id.phoneInput).text.toString()
            val addressInput = findViewById<EditText>(R.id.addressInput).text.toString()
            val isChecked = findViewById<CheckBox>(R.id.checkBox).isChecked

            // Validate inputs
            if (nameInput.isBlank() || idInput.isBlank() || phoneInput.isBlank() || addressInput.isBlank()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (StudentRepository.getStudentById(idInput) != null) {
                Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
            } else {
                // Add the new student to the repository
                val newStudent = Student(
                    id = idInput,
                    name = nameInput,
                    isChecked = isChecked,
                    phone = phoneInput,
                    address = addressInput
                )

                StudentRepository.addStudent(newStudent)

                Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()

                // Finish the activity and return to the StudentListActivity
                finish()
            }
        }

        // Handle the Cancel button click
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish() // Close the activity without saving
        }
    }
}