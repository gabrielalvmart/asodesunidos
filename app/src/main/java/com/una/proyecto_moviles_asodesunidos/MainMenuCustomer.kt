package com.una.proyecto_moviles_asodesunidos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.una.data.SessionData

class MainMenuCustomer: AppCompatActivity() {
    private var sessionManager = SessionManager
    private lateinit var sessionData: SessionData
    private lateinit var uid: String
    private lateinit var sid: String

    private lateinit var btnMyLoans: Button // TODO
    private lateinit var btnManageSavings: Button // TODO
    private lateinit var btnCalculatePayment: Button
    private lateinit var btnMyInformation: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_menu_customer)

        sessionData = sessionManager.getSession()!!

        uid = sessionData.userId ?: "ERROR"
        sid = sessionData.sessionId ?: "ERROR"

        btnCalculatePayment = findViewById(R.id.btn_payment_calculator)
        btnMyInformation = findViewById(R.id.btn_personal_info)
        btnLogout = findViewById(R.id.btn_associate_logout)


        btnCalculatePayment.setOnClickListener{
            startActivity(Intent(this, CalculateMonthlyPayment::class.java ))
        }

        btnMyInformation.setOnClickListener{
            startActivity(Intent(this, MyInformation::class.java ))
        }

        btnLogout.setOnClickListener {
            SessionManager.clearSession()
            startActivity(Intent(this, MainActivity::class.java ))
        }

        // TODO: Create "My Loans" screen
        // TODO: Create "Manage savings" screen

    }

}