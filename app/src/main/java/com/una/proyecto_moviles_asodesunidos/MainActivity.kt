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
    lateinit var txt_email : EditText
    lateinit var txt_password : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        asodesunidos_db.fetchUsers()
        setContentView(R.layout.login_screen)

        btn_login = findViewById(R.id.btn_login)
        txt_email = findViewById(R.id.txt_login_emailAddress)
        txt_password = findViewById(R.id.txt_login_password)


        btn_login.setOnClickListener{
            var tempUser = asodesunidos_db.attemptLogin(txt_email.text.toString(), txt_password.text.toString())
            if (tempUser != null) {
                Toast.makeText(this, "Logged In!", Toast.LENGTH_SHORT).show()
                if (tempUser.type == "customer"){
                    val intent = Intent(this, MainMenuCustomer::class.java )
                    startActivity(intent)
                    finish()
                }
                if (tempUser.type == "admin"){
                    val intent = Intent(this, MainMenuAdmin::class.java )
                    startActivity(intent)
                    finish()
                }
            }
            else
                Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
        }


    }
}
