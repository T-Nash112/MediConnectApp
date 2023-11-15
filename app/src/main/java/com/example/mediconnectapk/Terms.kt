package com.example.mediconnectapk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Terms : AppCompatActivity() {

    private lateinit var navBar: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        navBar = findViewById(R.id.bnvMenuTerms)

        navBar.setOnItemSelectedListener {menuItem->
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