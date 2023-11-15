package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Admin_Page : AppCompatActivity() {
    private lateinit var btnAdminLogin: Button
    private lateinit var etAdminName: EditText
    private lateinit var etAdminPass: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)

        btnAdminLogin = findViewById(R.id.loginAdmin)
        etAdminName = findViewById(R.id.usernameAdmin)
        etAdminPass = findViewById(R.id.passwordAdmin)

        auth = Firebase.auth

        btnAdminLogin.setOnClickListener {
            val username = etAdminName.text.toString()
            val password = etAdminPass.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty() && username.contains("HR")){

                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this,"Successfully Logged In", Toast.LENGTH_SHORT).show()
                            val toDoc = Intent(this, Admin_Lobby::class.java)
                            startActivity(toDoc)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,"Please enter correct login details", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"Please fill in the empty fields or Enter correct details", Toast.LENGTH_SHORT).show()
            }
        }

    }
}