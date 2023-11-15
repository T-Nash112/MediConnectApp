package com.example.mediconnectapk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase

class Enter_new_patiant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_new_patiant)

        val nameDepart = findViewById<EditText>(R.id.edtDepartmentName)
        val practNo = findViewById<EditText>(R.id.edtPracticeNumber)
        val patName = findViewById<EditText>(R.id.edtPatientName)
        val patId = findViewById<EditText>(R.id.edtPatientId)
        val patCon = findViewById<EditText>(R.id.edtCondition)
        val addInfo = findViewById<EditText>(R.id.edtAdditionalInfo)
        val patTreatment = findViewById<EditText>(R.id.edtTreatment)

        val submit = findViewById<Button>(R.id.btnSubmit)
        val back = findViewById<Button>(R.id.btnBack)

        //val rdYes = findViewById<RadioButton>(R.id.radioYes)
        //val rdNo = findViewById<RadioButton>(R.id.radioNo)

        val rdGroupRtn = findViewById<RadioGroup>(R.id.radioGroupReturn)
        val rdGroupRfl = findViewById<RadioGroup>(R.id.radioGroupReferral)

        //Return radio button
        rdGroupRtn.setOnCheckedChangeListener{group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text

            Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
        }

        //Referral radio button
        rdGroupRfl.setOnCheckedChangeListener{group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text

            Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
        }

        //submit button
        submit.setOnClickListener {

            val name = nameDepart.text.toString()
            val practiceNumber = practNo.text.toString()
            val patientName = patName.text.toString()
            val patientId = patId.text.toString()
            val condition = patCon.text.toString()
            val additionalInfo = addInfo.text.toString()
            val treatment = patTreatment.text.toString()

            // Create a new node in the database with the patient's information
            val database = FirebaseDatabase.getInstance().reference
            val patient = Patient(name, practiceNumber, patientName, patientId, condition, additionalInfo, treatment)
            database.child("patients").push().setValue(patient)

            Toast.makeText(this, "Patient information saved", Toast.LENGTH_SHORT).show()
        }


        //back button
        back.setOnClickListener {
            val toDocPage = Intent(this, Doc_Page::class.java)
            startActivity(toDocPage)
        }


    }
}