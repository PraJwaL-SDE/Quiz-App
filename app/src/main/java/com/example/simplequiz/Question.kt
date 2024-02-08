package com.example.simplequiz

data class Question(
    val ID : Int,
    val question : String,
    val Image : Int,
    val optionA : String,
    val optionB : String,
    val optionC : String,
    val optionD : String,
    val correctAns : Int
)
