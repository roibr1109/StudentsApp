package com.example.studentsapp

data class Student(
    var id: String,
    var name: String,
    var isChecked: Boolean = false,
    val imageResource: Int = R.drawable.student_pic
)