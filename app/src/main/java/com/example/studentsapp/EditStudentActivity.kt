package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type

class EditStudentActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var idInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { view, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameInput = findViewById(R.id.nameInput)
        idInput = findViewById(R.id.idInput)
        phoneInput = findViewById(R.id.phoneInput)
        addressInput = findViewById(R.id.addressInput)
        checkBox = findViewById(R.id.checkBox)
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        student = intent.getSerializableExtra("student") as Student

        if (student != null) {
            nameInput.setText(student.name)
            idInput.setText(student.id)
            phoneInput.setText(student.phone)
            addressInput.setText(student.address)
            checkBox.isChecked = student.isChecked

            cancelButton.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }

            saveButton.setOnClickListener {
                val oldId = student.id

                val updatedName = nameInput.text.toString()
                val updatedId = idInput.text.toString()
                val updatedPhone = phoneInput.text.toString()
                val updatedAddress = addressInput.text.toString()
                val updatedCheckStatus = checkBox.isChecked

                // Validate inputs
                if (updatedName.isBlank() || updatedId.isBlank() || updatedPhone.isBlank() || updatedAddress.isBlank()) {
                    Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else {
                    // Update student data in the repository
                    student.name = updatedName
                    student.id = updatedId
                    student.phone = updatedPhone
                    student.address = updatedAddress
                    student.isChecked = updatedCheckStatus

                    Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()

                    StudentRepository.updateStudent(oldId, student)

                    // Return the updated student to the previous activity
                    val resultIntent = Intent().apply {
                        putExtra("updatedStudent", student)
                        putExtra("oldStudentId", oldId)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }

            deleteButton.setOnClickListener {
                val resultIntent = Intent()
                StudentRepository.deleteStudent(student.id)
                resultIntent.putExtra("deletedStudentId", student.id)
                setResult(RESULT_OK, resultIntent)
                Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                finish() // Return to the previous activity
            }
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
