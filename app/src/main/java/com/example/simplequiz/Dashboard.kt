package com.example.simplequiz

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var userList: RecyclerView
    private lateinit var userAdapter: RankListRecyclerViewAdapter
    private lateinit var tv_name: TextView
    private lateinit var tv_score: TextView
    private var users: MutableList<RankItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        // Reference to the root node
        myRef = database.reference

        // Initialize RecyclerView and adapter
        userList = findViewById(R.id.userList)

        // Call function to get data from Firebase
        getDataFromFirebase()

        updateList(users)

        userAdapter = RankListRecyclerViewAdapter(users)
        userList.layoutManager = LinearLayoutManager(this)
        userList.adapter = userAdapter

        // Initialize TextViews
        tv_name = findViewById(R.id.tv_name)
        tv_score = findViewById(R.id.tv_score)


    }

    private fun getDataFromFirebase() {
        // Reference to the "users" node
        val usersRef = myRef.child("users")

        // Get the current user's ID
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Add a ValueEventListener to read data from Firebase
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the list before adding new data
                users.clear()

                // Iterate through each user snapshot
                for (userSnapshot in dataSnapshot.children) {
                    // Get user data and create a User object
                    val username = userSnapshot.child("username").getValue(String::class.java)
                    val correct = userSnapshot.child("correct").getValue(Int::class.java)
                    if (username != null && correct != null) {
                        val user = RankItem("1",username,correct)
                        // Add the user to the list
                        users.add(user)
                    }

                    // If the user ID matches the current user's ID, show the data
                    if (userSnapshot.key == userId) {
                        tv_name.text = username
                        tv_score.text = "Your Score is $correct out of 5"
                    }
                }

                // Notify the adapter of the data change
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                databaseError.toException().printStackTrace()
            }
        })
    }
    fun updateList(rankItems: List<RankItem>) {
        rankItems.sortedByDescending { it.score }
        for (index in rankItems.indices) {
            rankItems[index].rank = (index + 1).toString()
        }
    }
}
