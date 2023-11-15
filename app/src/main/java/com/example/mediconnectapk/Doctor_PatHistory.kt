package com.example.mediconnectapk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase

class Doctor_PatHistory : AppCompatActivity() {

    private lateinit var btnSubmit: Button
    private lateinit var btnView: Button
    private lateinit var edtEnterPatHist: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_pat_history)

        btnSubmit = findViewById(R.id.butSub)
        btnView = findViewById(R.id.viewHistory)
        edtEnterPatHist = findViewById(R.id.edtPatHist) // Initialize the EditText

        btnSubmit.setOnClickListener {
            saveHistory()
        }

        btnView.setOnClickListener {
            val intent = Intent(this, Pat_History::class.java)
            startActivity(intent)
        }

        val navbar = findViewById<BottomNavigationView>(R.id.bnvMenudocPat)


        navbar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.menu_Home -> {
                    val toHome = Intent(this, Doc_Page::class.java)
                    startActivity(toHome)
                    true
                }
                R.id.menu_Chat ->{
                    val toDash = Intent(this, Chatbot::class.java)
                    startActivity(toDash)
                    true
                }
                R.id.menu_Set->{
                    val toSet = Intent(this, AppSettings::class.java)
                    startActivity(toSet)
                    true
                }

                else -> false
            }
        }
    }

    private fun saveHistory() {
        val history = edtEnterPatHist.text.toString().trim()

        if (history.isNotEmpty()) {
            try {
                val databaseRef = FirebaseDatabase.getInstance().getReference("History")
                val upload = TextHistory(history)
                val uploadId: String? = databaseRef.push().key // Use push() to generate a unique key

                uploadId?.let {
                    databaseRef.child(it).setValue(upload)
                }

                // Display a success message
                Toast.makeText(this, "Treatment saved: $history", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                // Handle the exception (e.g., log the error and show an error message)
                Log.e("FirebaseError", "Error saving history: ${e.message}", e)
                Toast.makeText(this, "Failed to save treatment history", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Display an error message if the treatment name is empty
            Toast.makeText(this, "Please enter a valid treatment name", Toast.LENGTH_SHORT).show()
        }
    }
}
