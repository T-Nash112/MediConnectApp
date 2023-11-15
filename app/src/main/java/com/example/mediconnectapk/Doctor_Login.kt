package com.example.mediconnectapk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class Doctor_Login : AppCompatActivity() {

    private lateinit var btnDrLogin: Button
    private lateinit var etDocName: EditText
    private lateinit var etDocPract:EditText
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_login)

        btnDrLogin = findViewById(R.id.docLogin)
        etDocName = findViewById(R.id.docUsername)
        etDocPract = findViewById(R.id.docPractPass)

        auth = Firebase.auth

        btnDrLogin.setOnClickListener {
            val username = etDocName.text.toString()
            val practiceNo = etDocPract.text.toString()

            if(username.isNotEmpty() && practiceNo.isNotEmpty() && username.contains("Dr")){

                auth.signInWithEmailAndPassword(username, practiceNo)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this,"Successfully Logged In", Toast.LENGTH_SHORT).show()
                            val toDoc = Intent(this, Doc_Page::class.java)
                            startActivity(toDoc)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,"Please enter correct login details", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"Please fill in the empty fields!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}