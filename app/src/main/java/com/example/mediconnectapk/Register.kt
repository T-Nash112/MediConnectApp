package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtSurname: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtDoB: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var linkSign: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtName = findViewById(R.id.editTextName)
        edtSurname = findViewById(R.id.editTextSurname)
        edtEmail = findViewById(R.id.editTextEmail)
        edtDoB = findViewById(R.id.editTextDOB)
        edtPassword = findViewById(R.id.editTextPassword)
        edtConPassword = findViewById(R.id.editTextConfirmPassword)
        btnRegister = findViewById(R.id.buttonRegister)
        linkSign = findViewById(R.id.signUpLinkTextView)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        btnRegister.setOnClickListener {
            val name = edtName.text.toString()
            val surname = edtSurname.text.toString()
            val email = edtEmail.text.toString()
            val dOb = edtDoB.text.toString()
            val password = edtPassword.text.toString()
            val conPassword = edtConPassword.text.toString()

            // Validate input fields
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || dOb.isEmpty() || password.isEmpty() || conPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (password != conPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Create user in Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val currentUser: FirebaseUser? = auth.currentUser
                            if (currentUser != null) {
                                // Send email verification
                                currentUser.sendEmailVerification().addOnCompleteListener { emailTask ->
                                    if (emailTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Email verification sent. Please check your email and verify your account.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Email verification failed to send. Please try again.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                val userId = currentUser.uid
                                val user = User(name, surname, email, dOb, password)
                                // Save user data in Firebase Realtime Database
                                database.child(userId).setValue(user)
                                Toast.makeText(
                                    this,
                                    "User created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Redirect to login page
                                val login = Intent(this, Login::class.java)
                                startActivity(login)
                            }
                        } else {
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        linkSign.setOnClickListener {
            val login = Intent(this, Login::class.java)
            startActivity(login)
        }
    }
}
