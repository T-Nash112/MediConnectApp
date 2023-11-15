package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class Pat_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pat_page)

        val btnBooking = findViewById<Button>(R.id.btnBookAppointment)
        val btnPatHist = findViewById<Button>(R.id.btnPatientHistory)
        val btnPatTreatment = findViewById<Button>(R.id.btnPatientTreatment)
        val navbar = findViewById<BottomNavigationView>(R.id.bnvMenu)

        btnBooking.setOnClickListener {
            val book = Intent(this, Book_appointment::class.java)
            startActivity(book)
        }

        btnPatHist.setOnClickListener {
            val patientHist = Intent(this, Pat_History::class.java)
            startActivity(patientHist)
        }

        btnPatTreatment.setOnClickListener {
            val patientTreat = Intent(this, Pat_treat::class.java)
            startActivity(patientTreat)
        }

        navbar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.menu_Home -> {
                    val toHome = Intent(this, Pat_page::class.java)
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