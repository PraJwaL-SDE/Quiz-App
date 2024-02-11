package com.example.simplequiz

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


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
    var counter = 1
    var correct = 0
    var incorrect = 0
    var image_progressBar : ProgressBar? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

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
        image_progressBar = findViewById(R.id.image_progressBar)
        queTxt?.setText(Questions[0].question)

        updateQue(0)

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

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
// Reference to the root node
        myRef = database.reference

        val usersRef = myRef.child("users")

// Get the current user ID
        val userId = FirebaseAuth.getInstance().currentUser?.uid

// Ensure user ID is not null before proceeding
        userId?.let { uid ->
            // Get the current user's email
            val userEmail = FirebaseAuth.getInstance().currentUser?.email.orEmpty()

            // Extract username from the email (assuming email format is used as the username)
            val username = userEmail.substringBeforeLast("@") // Extract the username part before the "@" symbol

            // Create a HashMap to hold the data to be sent
            val userData = hashMapOf(
                "username" to username,
                "correct" to correct, // Assuming correct is some variable representing correctness
                "Attempt" to 1 // Assuming some value associated with the user
            )

            // Add data to the database under the generated user ID
            usersRef.child(uid).setValue(userData)
                .addOnSuccessListener {
                    // Data successfully written to the database
                    Log.d(TAG, "Data sent successfully")
                    Toast.makeText(this, "Data sent successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Failed to write data to the database
                    Log.w(TAG, "Error sending data", it)
                    Toast.makeText(this, "Failed to send data", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Log.e(TAG, "User ID is null")
            Toast.makeText(this, "Failed to send data: User ID is null", Toast.LENGTH_SHORT).show()
        }


        startActivity(Intent(this,Dashboard::class.java))
    }
    fun checkAnswer(){
        if(selectedOption==ans) correct++
        else incorrect++
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

    fun updateQue(idx: Int) {
        // Show progress bar
        image_progressBar?.visibility  = View.VISIBLE
        queImageVIew?.visibility = View.GONE
        // Delay loading of image by 1 second
        for (i in 1..4)
            ChangeOptionColor(i, R.color.white)
        Handler(Looper.getMainLooper()).postDelayed({

            queTxt?.setText(Questions[idx].question)
            optionA?.setText(Questions[idx].optionA)
            optionB?.setText(Questions[idx].optionB)
            optionC?.setText(Questions[idx].optionC)
            optionD?.setText(Questions[idx].optionD)
            queImageVIew?.setImageResource(Questions[idx].Image)
            ans = Questions[idx].correctAns
            selectedOption = -1
            progressBar?.progress = counter
            progress_text?.setText("$counter/10")

            // Hide progress bar
            queImageVIew?.visibility  = View.VISIBLE
            image_progressBar?.visibility = View.GONE
        }, 1000) // Delay for 1 second (1000 milliseconds)
    }
}