package com.example.simplequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView


class QuizQuestion : AppCompatActivity() {
    var queTxt : TextView? = null
    var queImageVIew : ImageView? = null
    var progressBar : ProgressBar? = null
    var progress_text : TextView? = null

    var optionA : Button? = null
    var optionB : Button? = null
    var optionC : Button? = null
    var optionD : Button? = null

    lateinit var Questions : ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        queTxt = findViewById(R.id.queTxt)
        queImageVIew = findViewById(R.id.queImageVIew)
        progressBar = findViewById(R.id.progressBar)
        progress_text = findViewById(R.id.progress_text)

        optionA = findViewById(R.id.optionA)
        optionB = findViewById(R.id.optionB)
        optionC = findViewById(R.id.optionC)
        optionD = findViewById(R.id.optionD)
        val SubmitBtn = findViewById<Button>(R.id.SubmitBtn)
        Questions =  Constants.getQuestions()

        queTxt?.setText(Questions[0].question)

        updateQue(0)
        var counter = 1
        SubmitBtn.setOnClickListener({

        })





    }

    fun updateQue(idx : Int){
        queTxt?.setText(Questions[idx].question)
        optionA?.setText(Questions[idx].optionA)
        optionB?.setText(Questions[idx].optionB)
        optionC?.setText(Questions[idx].optionC)
        optionC?.setText(Questions[idx].optionC)
        queImageVIew?.setImageResource(Questions[idx].Image)

    }
}