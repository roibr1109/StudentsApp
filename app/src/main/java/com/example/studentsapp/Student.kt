package com.example.studentsapp

import java.io.Serializable

data class Student(
    var id: String,
    var name: String,
    var isChecked: Boolean = false,
    var phone: String = "",
    var address: String = "",
    val imageResource: Int = R.drawable.student_pic,
) : Serializable