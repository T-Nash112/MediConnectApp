package com.example.mediconnectapk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class Doc_Page : AppCompatActivity() {
    companion object{
        private lateinit var btnPatientDetails: Button
        private lateinit var btnHistory: Button
        private lateinit var btnTreatment: Button
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_page)

        btnPatientDetails = findViewById(R.id.btnEnterNewPatient)
        btnHistory = findViewById(R.id.btnPatientHistory)
        btnTreatment = findViewById(R.id.btnPatientTreatment)

        btnPatientDetails.setOnClickListener {
            val patientDetails = Intent(this, Enter_new_patiant::class.java)
            startActivity(patientDetails)
        }

        btnHistory.setOnClickListener {
            val history = Intent(this, Doctor_PatHistory::class.java)
            startActivity(history)
        }

        btnTreatment.setOnClickListener {
            val treatment = Intent(this, Enter_PatTreatment::class.java)
            startActivity(treatment)
        }

        val navbar = findViewById<BottomNavigationView>(R.id.bnvMenuDocPage)


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
}