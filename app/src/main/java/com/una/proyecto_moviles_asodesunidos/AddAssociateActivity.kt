package com.una.proyecto_moviles_asodesunidos

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.una.models.SavingModel
import com.una.models.UserModel

class AddAssociateActivity : AppCompatActivity() {
    lateinit var idNumberEditText: EditText
    lateinit var fullNameEditText: EditText
    lateinit var wageEditText: EditText
    lateinit var phoneNumberEditText: EditText
    lateinit var dateOfBirthEditText: EditText
    lateinit var maritalStatusSpinner: Spinner
    lateinit var addressEditText: EditText
    lateinit var createAssociateButton: Button
    lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_associate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idNumberEditText = findViewById(R.id.txt_newAssociate_idNumber)
        fullNameEditText = findViewById(R.id.txt_newAssociate_fullName)
        wageEditText = findViewById(R.id.txt_newAssociate_wage)
        phoneNumberEditText = findViewById(R.id.txt_newAssociate_phoneNumber)
        dateOfBirthEditText = findViewById(R.id.txt_newAssociate_dateOfBirth)
        maritalStatusSpinner = findViewById(R.id.spn_newAssociate_maritalStatus)
        addressEditText = findViewById(R.id.txt_newAssociate_address)
        createAssociateButton = findViewById(R.id.btn_newAssociate_createAssociate)
        backButton = findViewById(R.id.btn_newAssociate_back)



        val maritalStatusOptions = arrayOf("Casado(a)", "Soltero(a)", "Unión Libre")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalStatusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maritalStatusSpinner.adapter = adapter

        createAssociateButton.setOnClickListener {
            val idNumber = idNumberEditText.text.toString()
            val fullName = fullNameEditText.text.toString()
            val wage = wageEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val dateOfBirth = dateOfBirthEditText.text.toString()
            val maritalStatus = maritalStatusSpinner.selectedItem.toString()
            val address = addressEditText.text.toString()

            if (validateAssociateFields(idNumber, fullName, wage, phoneNumber, dateOfBirth, address)) {
                val wageInt = wage.toInt()
                val savings = SavingModel(0, 0, 0, 0)
                val associate = UserModel(
                    idNumber, "TEMPORAL", "associate", fullName, wageInt, phoneNumber,
                    dateOfBirth, maritalStatus, address, emptyList(), savings
                )
                addAssociateToDataBase(associate)
            }
        }

        backButton.setOnClickListener{
            finish()
        }
    }
    private fun validateAssociateFields(
        idNumber: String,
        fullName: String,
        wage: String,
        phoneNumber: String,
        dateOfBirth: String,
        address: String
    ): Boolean {
        if (idNumber.isEmpty()) {
            idNumberEditText.error = "Campo obligatorio"
            return false
        }

        if (fullName.isEmpty()) {
            fullNameEditText.error = "Campo obligatorio"
            return false
        }

        if (wage.isEmpty()) {
            wageEditText.error = "Campo obligatorio"
            return false
        }

        val wageInt = wage.toIntOrNull()
        if (wageInt == null || wageInt <= 0) {
            wageEditText.error = "Ingrese un salario válido"
            return false
        }

        if (phoneNumber.isEmpty()) {
            phoneNumberEditText.error = "Campo obligatorio"
            return false
        }

        if (dateOfBirth.isEmpty()) {
            dateOfBirthEditText.error = "Campo obligatorio"
            return false
        }

        if (address.isEmpty()) {
            addressEditText.error = "Campo obligatorio"
            return false
        }
        return true
    }

    private fun addAssociateToDataBase(associate: UserModel) {
        AsodesunidosDB.addAssociate(associate) { success, errorMessage ->
            if (success) {
                showToast("Asociado agregado exitosamente")
                finish()
            } else {
                showToast("Error al agregar el asociado: $errorMessage")
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}