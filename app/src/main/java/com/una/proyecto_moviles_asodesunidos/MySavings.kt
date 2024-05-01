package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.una.models.SavingModel
import java.time.temporal.TemporalAmount

class MySavings : AppCompatActivity() {

    private lateinit var btnEnableChristmas: Button
    private lateinit var txtChristmasAmount: EditText
    private lateinit var btnEnableSchool: Button
    private lateinit var txtSchoolAmount: EditText
    private lateinit var btnEnableVehicle: Button
    private lateinit var txtRegistrationAmount: EditText
    private lateinit var btnEnableExtraordinary: Button
    private lateinit var txtExtraordinaryAmount: EditText
    private lateinit var btnBack: Button

    private val associateId: String by lazy {
        SessionManager.getSession()?.userId.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.manage_savings)


        // Inicialización de vistas
        btnEnableChristmas = findViewById(R.id.btn_manage_savings_enable_christmas)
        txtChristmasAmount = findViewById(R.id.txt_manage_savings_christmas_amount)
        btnEnableSchool = findViewById(R.id.btn_manage_savings_enable_school)
        txtSchoolAmount = findViewById(R.id.txt_manage_savings_school_amount)
        btnEnableVehicle = findViewById(R.id.btn_manage_savings_enable_registration)
        txtRegistrationAmount = findViewById(R.id.txt_manage_savings_registration_amount)
        btnEnableExtraordinary = findViewById(R.id.btn_manage_savings_enable_extraordinary)
        txtExtraordinaryAmount = findViewById(R.id.txt_manage_savings_extraordinary_amount)
        btnBack = findViewById(R.id.btn_manage_savings_back)

        AsodesunidosDB.getSavings(associateId){
            savingsData -> savingsData?.let{
                data->
                txtChristmasAmount.setText(data.christmas.toString())
                txtExtraordinaryAmount.setText(data.extraordinary.toString())
                txtSchoolAmount.setText(data.school.toString())
                txtRegistrationAmount.setText(data.vehicle.toString())

                configureButton(btnEnableChristmas, data.christmas)
                configureButton(btnEnableSchool, data.school)
                configureButton(btnEnableVehicle, data.vehicle)
                configureButton(btnEnableExtraordinary, data.extraordinary)
            }
        }

        // Configuración de listeners
        btnEnableExtraordinary.setOnClickListener { updateSavingsValue("extraordinary", txtExtraordinaryAmount.text.toString(), txtExtraordinaryAmount) }
        btnEnableChristmas.setOnClickListener { updateSavingsValue("christmas", txtChristmasAmount.text.toString(), txtChristmasAmount) }
        btnEnableVehicle.setOnClickListener { updateSavingsValue("vehicle", txtRegistrationAmount.text.toString(), txtRegistrationAmount) }
        btnEnableSchool.setOnClickListener { updateSavingsValue("school", txtSchoolAmount.text.toString(), txtSchoolAmount) }
        btnBack.setOnClickListener{ finish()}
    }

    private fun configureButton(button: Button, amount: Int) {
        if(amount > 0 )
            button.text = "Save"
        else
            button.text = "Enable"
    }


    private fun updateSavingsValue(savingsType: String, newValue: String,edt: EditText) {
        val amount = newValue.toIntOrNull()
        if (amount == null || amount <= 0 || edt.toString().isEmpty()) {
            edt.error = "Enter a valid amount"
            return
        }
        if (amount < 5000) {
            edt.error = "Enter at least 5000"
            return
        }
        AsodesunidosDB.updateSavingsValue(associateId, savingsType, amount) { success, errorMessage ->
            if (success) {
                showToast("Savings updated successfully")
            } else {
                showToast("Error updating savings: $errorMessage")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
