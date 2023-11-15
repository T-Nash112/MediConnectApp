package com.example.mediconnectapk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class Homepage : AppCompatActivity() {
        private lateinit var btnDoc: Button
        private lateinit var btnPat: Button
        private lateinit var btnAdmin: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        btnDoc = findViewById(R.id.btnDoctor)
        btnPat = findViewById(R.id.btnPatient)
        btnAdmin = findViewById(R.id.btnAdmin)


        btnDoc.setOnClickListener {

            val docPage = Intent(this, Doctor_Login::class.java)
            startActivity(docPage)
        }

        btnPat.setOnClickListener {
            val patPage = Intent(this, Login::class.java)
            startActivity(patPage)
            }

        btnAdmin.setOnClickListener {
            val intent = Intent(this, Admin_Page::class.java)
            startActivity(intent)
        }
    }
}
