package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MyInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AsodesunidosDB.fetchUsers()
        setContentView(R.layout.personal_data)
    }
    // TODO: Add logic here to modify the user info
}