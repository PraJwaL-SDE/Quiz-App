package com.example.simplequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var nameTxtView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameTxtView = findViewById(R.id.name_input)
        val startQuizBtn : Button  = findViewById(R.id.startQuizBtn)

        startQuizBtn.setOnClickListener {
            startActivity(Intent(this, QuizQuestion::class.java))
        }

    }
}