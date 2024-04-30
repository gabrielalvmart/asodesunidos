package com.una.proyecto_moviles_asodesunidos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var btn_login : Button
    lateinit var txt_user : EditText
    lateinit var txt_password : EditText

    private var sessionManager = SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AsodesunidosDB.fetchUsers()
        setContentView(R.layout.login_screen)

        sessionManager.initialize(this)
        btn_login = findViewById(R.id.btn_login)
        txt_user = findViewById(R.id.txt_login_userid)
        txt_password = findViewById(R.id.txt_login_password)


        btn_login.setOnClickListener{
            if (validateCredentials()) {
                if (loginUser())
                    Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                    txt_user.setText("")
                    txt_password.setText("")
                }
            }
        }


    }

    private fun loginUser(): Boolean {
        val tempUser = AsodesunidosDB.attemptLogin(txt_user.text.toString(), txt_password.text.toString())
        return if (tempUser != null) {
            sessionManager.saveLogin(tempUser.id, generateSessionId())
            when (tempUser.type) {
                "associate" -> { startActivity(Intent(this, MainMenuCustomer::class.java )) }
                "admin" -> { startActivity(Intent(this, MainMenuAdmin::class.java )) }
                else -> return false
            }
            true
        } else false
    }

    private fun generateSessionId(): String {
        val currentTime = System.currentTimeMillis().toString()
        val randomString = (0..9999).random().toString().padStart(4, '0')
        return "$currentTime-$randomString"
    }
    private fun validateCredentials(): Boolean {
        val username = txt_user.text.toString()
        val password = txt_password.text.toString()

        if (username.isEmpty()) {
            txt_user.error = "Username is required"
            return false
        }

        if (password.isEmpty()) {
            txt_password.error = "Password is required"
            return false
        }

        return true
    }


}
