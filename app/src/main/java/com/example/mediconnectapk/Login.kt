package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    companion object {
        private lateinit var logEmail: EditText
        private lateinit var logPassword: EditText
        private lateinit var btnLogin: Button
        private lateinit var regLink: TextView
        private lateinit var auth: FirebaseAuth
        private lateinit var database: DatabaseReference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.buttonLogin)
        logEmail = findViewById(R.id.editTextLoginEmail)
        logPassword = findViewById(R.id.editTextLoginPassword)
        regLink = findViewById(R.id.signUpLinkTextView)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")


        btnLogin.setOnClickListener {
            val email = logEmail.text.toString()
            val password = logPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods
                        if (signInMethods != null && signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        // Check if the user's email is verified
                                        val currentUser = auth.currentUser
                                        if (currentUser?.isEmailVerified == true) {
                                            val home = Intent(this, Pat_page::class.java)
                                            startActivity(home)
                                        } else {
                                            Toast.makeText(this, "Email is not verified", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Authentication failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Error fetching sign-in methods", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


        regLink.setOnClickListener {
            val register = Intent(this, Register::class.java)
            startActivity(register)
        }
    }
}