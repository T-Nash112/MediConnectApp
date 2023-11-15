package com.example.mediconnectapk

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Book_appointment : AppCompatActivity() {

    private lateinit var calendar: Calendar
    private lateinit var date: EditText
    private lateinit var time: EditText
    private val user = FirebaseAuth.getInstance().currentUser
    private val database = FirebaseDatabase.getInstance().reference
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        val enterPatName = findViewById<EditText>(R.id.editTextPatientName)
        val rdDepartment = findViewById<RadioGroup>(R.id.radioGroupDepartment)
        date = findViewById(R.id.editTextDate)
        time = findViewById(R.id.editTextTime)
        val bookAppoint = findViewById<Button>(R.id.buttonBookAppointment)
        btnBack = findViewById(R.id.butBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, Pat_page::class.java)
            startActivity(intent)
        }
        // Initialize the calendar
        calendar = Calendar.getInstance()

        // Set initial date and time in EditText fields
        date.setOnClickListener {
            selectDate()
        }

        // Set a click listener for the time EditText
        time.setOnClickListener {
            selectTime()
        }

        // Departments group
        rdDepartment.setOnCheckedChangeListener { group, checkedId ->
            val selected = findViewById<RadioButton>(checkedId)
            val selectedText = selected.text
            Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
        }

        // Set a click listener for the date EditText
        date.setOnClickListener {
            selectDate()
        }

        // Set a click listener for the time EditText
        time.setOnClickListener {
            selectTime()
        }

        // Appointment button
        bookAppoint.setOnClickListener {
            val patientName = enterPatName.text.toString()
            val department = findViewById<RadioButton>(rdDepartment.checkedRadioButtonId).text.toString()
            val appointmentDate = date.text.toString()
            val appointmentTime = time.text.toString()

            // Check if the user already has an appointment on this date
            checkExistingAppointment(user!!.uid, appointmentDate) { hasAppointment ->
                if (hasAppointment) {
                    Toast.makeText(this, "You have already booked an appointment for this date.", Toast.LENGTH_SHORT).show()
                } else {
                    val appointment = Appointment(patientName, department, appointmentDate, appointmentTime)
                    val appointmentRef = database.child("appointments").push()
                    appointmentRef.setValue(appointment)

                    // Store the user's appointment date in a separate location
                    database.child("user_appointments").child(user.uid).child(appointmentRef.key!!).setValue(appointmentDate)

                    Toast.makeText(this, "Appointment booked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun selectDate() {
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            date.setText(updateDate())
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }

    private fun updateDate(): String {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        return dateFormat.format(calendar.time)
    }

    private fun selectTime() {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        if (hour in 8..18) {
            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                selectedTime.set(Calendar.MINUTE, selectedMinute)

                if (selectedTime.get(Calendar.HOUR_OF_DAY) in 8..18) {
                    time.setText(updateTime(selectedHour, selectedMinute))
                } else {
                    Toast.makeText(this, "Please Book appoint between 8am and 5pm", Toast.LENGTH_SHORT).show()
                }
            }, hour, minute, true)

            timePickerDialog.setTitle("Select Time")
            timePickerDialog.show()
        } else {
            Toast.makeText(this, "Current time is out of bounds", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTime(hour: Int, minute: Int): String {
        val myFormat = "HH:mm:ss"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        val selectedTime = Calendar.getInstance()
        selectedTime.set(Calendar.HOUR_OF_DAY, hour)
        selectedTime.set(Calendar.MINUTE, minute)
        return dateFormat.format(selectedTime.time)
    }

    private fun checkExistingAppointment(userId: String, appointmentDate: String, callback: (Boolean) -> Unit) {
        // Check if the user already has an appointment on the given date
        database.child("user_appointments").child(userId).orderByValue().equalTo(appointmentDate)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    callback(dataSnapshot.exists())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(false)
                }
            })
    }
}
