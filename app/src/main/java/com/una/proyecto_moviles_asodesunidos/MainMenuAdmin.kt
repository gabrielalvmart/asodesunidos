package com.una.proyecto_moviles_asodesunidos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainMenuAdmin : AppCompatActivity() {

    lateinit var btn_logout: Button;
    lateinit var btn_new_associate: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_menu_admin)


        btn_logout = findViewById(R.id.btn_logout)

        btn_new_associate = findViewById(R.id.btn_new_associate)
        btn_logout.setOnClickListener{

        }

        btn_new_associate.setOnClickListener{
            startActivity(Intent(this, AddNewAssociateActivity::class.java ))
        }
    }


}