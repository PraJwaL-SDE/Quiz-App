package com.example.simplequiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat


class QuizQuestion : AppCompatActivity() {
    var queTxt : TextView? = null
    var queImageVIew : ImageView? = null
    var progressBar : ProgressBar? = null
    var progress_text : TextView? = null

    var optionA : Button? = null
    var optionB : Button? = null
    var optionC : Button? = null
    var optionD : Button? = null
    var SubmitBtn : Button? = null
    var selectedOption =-1
    var questionCount = 10
    private var ans = -1

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
        SubmitBtn = findViewById(R.id.SubmitBtn)
        Questions =  Constants.getQuestions()

        queTxt?.setText(Questions[0].question)

        updateQue(0)
        var counter = 1
        SubmitBtn?.setText("Check Answer")
        SubmitBtn?.setOnClickListener({

            if(SubmitBtn?.text.toString()=="Check Answer"){
                checkAnswer()
                if(questionCount!=counter)
                    SubmitBtn?.setText("Next")
                else
                    SubmitBtn?.setText("Submit")
            }else if(SubmitBtn?.text.toString()=="Next"){
                if(selectedOption!=-1)
                updateQue(counter++)
                SubmitBtn?.setText("Check Answer")
            }else{
                moveToDashboard()
            }

        })

        optionA?.setOnClickListener({
            onOptionSelect(optionA!!)
            selectedOption=1
        })
        optionB?.setOnClickListener({
            onOptionSelect(optionB!!)
            selectedOption=2
        })
        optionC?.setOnClickListener({
            onOptionSelect(optionC!!)
            selectedOption=3
        })
        optionD?.setOnClickListener({
            onOptionSelect(optionD!!)
            selectedOption=4
        })

    }

    fun moveToDashboard(){

    }
    fun checkAnswer(){
        ChangeOptionColor(selectedOption,R.color.red)
        ChangeOptionColor(ans,R.color.green)
    }

    @SuppressLint("ResourceAsColor")
    fun onOptionSelect(option : Button){
        for(i in 1..4)
            ChangeOptionColor(i,R.color.white)
        option?.setBackgroundColor(ContextCompat.getColor(this,R.color.yellow))

    }

    fun ChangeOptionColor(option: Int, color: Int) {
        val button: Button? = when (option) {
            1 -> optionA
            2 -> optionB
            3 -> optionC
            4 -> optionD
            else -> null
        }
        button?.setBackgroundColor(ContextCompat.getColor(this, color))
    }

    fun updateQue(idx : Int){
        for(i in 1..4)
            ChangeOptionColor(i,R.color.white)
        queTxt?.setText(Questions[idx].question)
        optionA?.setText(Questions[idx].optionA)
        optionB?.setText(Questions[idx].optionB)
        optionC?.setText(Questions[idx].optionC)
        optionC?.setText(Questions[idx].optionC)
        queImageVIew?.setImageResource(Questions[idx].Image)
        ans = Questions[idx].correctAns
        selectedOption = -1



    }
}