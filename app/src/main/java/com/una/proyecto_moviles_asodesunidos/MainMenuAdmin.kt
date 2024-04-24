package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainMenuAdmin : AppCompatActivity() {

    lateinit var btn_logout: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_menu_admin)


        btn_logout = findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener{

        }
    }

    // TODO: A lot to do here :(

}