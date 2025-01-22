package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { view, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        StudentRepository.addStudent(Student("1", "Alice", false, "1234567890", "123 Street"))
        StudentRepository.addStudent(Student("2", "Bob", true, "9876543210", "456 Avenue"))


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = StudentsAdapter(StudentRepository.getStudents(), this::onStudentClick)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.addStudentButton).setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateStudents(StudentRepository.getStudents())
    }

    private fun onStudentClick(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("studentId", student.id)
        startActivity(intent)
    }
}