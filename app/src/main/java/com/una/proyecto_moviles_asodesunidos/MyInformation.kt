package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.una.models.UserModel

class MyInformation : AppCompatActivity() {

    lateinit var idNumberEditText: EditText
    lateinit var fullNameEditText: EditText
    lateinit var wageEditText: EditText
    lateinit var phoneNumberEditText: EditText
    lateinit var dateOfBirthEditText: EditText
    lateinit var maritalStatusSpinner: Spinner
    lateinit var addressEditText: EditText
    lateinit var updateInfoButton: Button
    lateinit var backButton: Button
    private val associateId: String by lazy {
        SessionManager.getSession()?.userId.toString()
    }

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
        backButton = findViewById(R.id.btn_myInfo_back)
        val maritalStatusOptions = resources.getStringArray(R.array.marital_status_options)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalStatusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maritalStatusSpinner.adapter = adapter


        updateInfoButton.setOnClickListener {
            updateUserInfo()
        }

        backButton.setOnClickListener{
            finish()
        }


        AsodesunidosDB.getAssociate(associateId) { associate ->
            associate?.let { populateAssociateFields(it)}
        }
    }

    private fun populateAssociateFields(user: UserModel) {
        idNumberEditText.setText(user.id)
        fullNameEditText.setText(user.name)
        wageEditText.setText(user.salary.toString())
        phoneNumberEditText.setText(user.phone)
        dateOfBirthEditText.setText(user.birthDate)
        maritalStatusSpinner.setSelection(getMaritalStatusIndex(user.maritalStatus))
        addressEditText.setText(user.address)
    }

    private fun getMaritalStatusIndex(maritalStatus: String): Int {
        val maritalStatusOptions = resources.getStringArray(R.array.marital_status_options)
        return maritalStatusOptions.indexOf(maritalStatus)
    }

    private fun updateUserInfo() {
        val savedUser: UserModel? = AsodesunidosDB.userExists(associateId)

        val updatedUser = UserModel(
            id = idNumberEditText.text.toString(),
            name = fullNameEditText.text.toString(),
            salary = wageEditText.text.toString().toInt(),
            phone = phoneNumberEditText.text.toString(),
            birthDate = dateOfBirthEditText.text.toString(),
            maritalStatus = maritalStatusSpinner.selectedItem.toString(),
            address = addressEditText.text.toString(),
            password = savedUser?.password,
            type = savedUser?.type
        )
        if(validateFields()){
            AsodesunidosDB.updateAssociate(updatedUser) { success, errorMessage ->
                if (success) {
                    showToast("The user's information has been updated")
                    finish()
                } else {
                    showToast("An error occurred while updating user information:$errorMessage ")
                }
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateFields(): Boolean {
        val phoneNumber = phoneNumberEditText.text.toString()
        val address = addressEditText.text.toString()

        if (phoneNumber.isEmpty()) {
            phoneNumberEditText.error = "Phone Number is required"
            return false
        }

        if (address.isEmpty()) {
            addressEditText.error = "Address is required"
            return false
        }

        return true
    }
}