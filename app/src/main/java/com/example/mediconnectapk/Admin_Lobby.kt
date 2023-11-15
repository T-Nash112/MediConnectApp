package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Admin_Lobby : AppCompatActivity() {

    private lateinit var etUsername:EditText
    private lateinit var etEmail:EditText
    private lateinit var etPractNo:EditText
    private lateinit var etSpecial:EditText
    private lateinit var etClinic:EditText
    private lateinit var etPhone:EditText

    private lateinit var btnDocReg:Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_lobby)

        etUsername = findViewById(R.id.adminUsername)
        etEmail = findViewById(R.id.adminEmail)
        etPractNo = findViewById(R.id.adminPassword)
        etSpecial = findViewById(R.id.editTextSpecialization)
        etClinic = findViewById(R.id.editTextClinicAddress)
        etPhone = findViewById(R.id.editTextPhoneNumber)

        btnDocReg = findViewById(R.id.buttonCreateAccount)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Doctors").push()


        btnDocReg.setOnClickListener {
            val name = etUsername.text.toString()
            val email = etEmail.text.toString()
            val practice = etPractNo.text.toString()
            val specialization = etSpecial.text.toString()
            val clinic = etClinic.text.toString()
            val phone = etPhone.text.toString().toInt()

            if (name.isEmpty() && email.isEmpty() && practice.isEmpty() && specialization.isEmpty() && clinic.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (!email.contains("Dr")) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Create user in Firebase Authentication
                auth.createUserWithEmailAndPassword(email, practice)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val currentUser: FirebaseUser? = auth.currentUser
                            if (currentUser != null) {
                                val doctorId = currentUser.uid
                                val doctor = Doctor(name,specialization,email,clinic,practice,phone)
                                // Save user data in Firebase Realtime Database
                                database.child(doctorId).setValue(doctor)
                                Toast.makeText(
                                    this,
                                    "User created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Redirect to login page
                                val login = Intent(this, Doctor_Login::class.java)
                                startActivity(login)
                            }
                        } else {
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}