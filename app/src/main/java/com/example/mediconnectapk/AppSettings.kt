package com.example.mediconnectapk

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppSettings : AppCompatActivity() {

    private lateinit var btnLogout:Button
    private lateinit var btnVid:Button
    private lateinit var btnTerms:Button
    private lateinit var navBar:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_settings)

        btnLogout = findViewById(R.id.butLogout)
        btnVid = findViewById(R.id.butRequestVid)
        btnTerms = findViewById(R.id.butTandCs)
        navBar = findViewById(R.id.bnvMenuSettings)

        btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
            Toast.makeText(this,"Successfully logged out", Toast.LENGTH_SHORT).show()
        }

        btnVid.setOnClickListener {
            val teams = Intent(Intent.ACTION_VIEW)
            teams.data = Uri.parse("https://www.microsoft.com/en/microsoft-teams/join-a-meeting")
            startActivity(teams)
        }

        btnTerms.setOnClickListener {
            val terms = Intent(this, Terms::class.java)
            startActivity(terms)
        }

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