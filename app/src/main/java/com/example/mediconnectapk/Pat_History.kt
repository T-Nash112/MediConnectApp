package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class Pat_History : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter

    private val databaseReference = FirebaseDatabase.getInstance().getReference("History")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pat_history)

        recyclerView = findViewById(R.id.rvHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val textItems = mutableListOf<TextHistory>()

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                textItems.clear()
                for (snapshot in dataSnapshot.children) {
                    val text = snapshot.child("text").getValue(String::class.java) ?: ""
                    textItems.add(TextHistory(text))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Toast.makeText(this@Pat_History,"Failed to add patient history", Toast.LENGTH_SHORT).show()
            }
        }

        databaseReference.addValueEventListener(valueEventListener)

        adapter = HistoryAdapter(textItems)
        recyclerView.adapter = adapter

        val navbar = findViewById<BottomNavigationView>(R.id.bnvMenu)
        //navbar
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
