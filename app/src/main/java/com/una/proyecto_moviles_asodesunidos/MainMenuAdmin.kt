package com.una.proyecto_moviles_asodesunidos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainMenuAdmin : AppCompatActivity() {

    private lateinit var btnLogout: Button
    private lateinit var btnNewAssociate: Button
    private lateinit var btnAssignLoan: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_menu_admin)

        btnAssignLoan = findViewById(R.id.btn_assign_a_loan)
        btnLogout = findViewById(R.id.btn_logout)

        btnNewAssociate = findViewById(R.id.btn_new_associate)
        btnLogout.setOnClickListener{

        }

        btnNewAssociate.setOnClickListener{
            startActivity(Intent(this, AddAssociateActivity::class.java ))
        }
        btnAssignLoan.setOnClickListener {
            startActivity(Intent(this, AssignLoan::class.java ))
        }
    }


}