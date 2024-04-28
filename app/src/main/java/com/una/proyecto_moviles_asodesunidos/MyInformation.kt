package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MyInformation : AppCompatActivity() {

    private lateinit var idNumberEditText: EditText
    private lateinit var fullNameEditText: EditText
    private lateinit var wageEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var maritalStatusSpinner: Spinner
    private lateinit var addressEditText: EditText
    private lateinit var updateInfoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AsodesunidosDB.fetchUsers()
        setContentView(R.layout.personal_data)

        idNumberEditText = findViewById(R.id.txt_myInfo_idNumber)
        fullNameEditText = findViewById(R.id.txt_myInfo_fullName)
        wageEditText = findViewById(R.id.txt_myInfo_wage)
        phoneNumberEditText = findViewById(R.id.txt_myInfo_phoneNumber)
        dateOfBirthEditText = findViewById(R.id.txt_myInfo_dateOfBirth)
        maritalStatusSpinner = findViewById(R.id.spn_myInfo_maritalStatus)
        addressEditText = findViewById(R.id.txt_myInfo_address)
        updateInfoButton = findViewById(R.id.btn_myInfo_updateInfo)

        val maritalStatusOptions = arrayOf("Casado(a)", "Soltero(a)", "Unión Libre")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalStatusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maritalStatusSpinner.adapter = adapter



        updateInfoButton.setOnClickListener {
        }
    }
}