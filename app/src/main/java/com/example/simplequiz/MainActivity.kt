package com.example.simplequiz

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    var nameTxtView : TextView? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        nameTxtView = findViewById(R.id.name_input)
        val startQuizBtn : Button  = findViewById(R.id.startQuizBtn)

        startQuizBtn.setOnClickListener {
            email = nameTxtView?.text.toString()


            //        Firebase Authentication

            auth.createUserWithEmailAndPassword("$email@gmail.com", email)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, QuizQuestion::class.java))
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
//                        updateUI(null)
                    }
                }
        }






    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, QuizQuestion::class.java))
        }
    }

}