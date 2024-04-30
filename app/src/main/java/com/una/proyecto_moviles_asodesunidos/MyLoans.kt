package com.una.proyecto_moviles_asodesunidos

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.una.models.LoanModel

class MyLoans : AppCompatActivity() {
    private lateinit var loanList: List<LoanModel>
    private lateinit var linearLayout: LinearLayout
    private lateinit var btnGoBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.my_loans)

        btnGoBack = findViewById(R.id.btn_my_loans_back)
        btnGoBack.setOnClickListener {
            finish()
        }

        if (SessionManager.getUser()?.loans!!.isNotEmpty() ) {
            loanList = SessionManager.getUser()?.loans!!.filter { true }.sortedBy { it.loanID }
            linearLayout = findViewById(R.id.linearLayoutRow)

            for (loan in loanList) {
                val newRow = LayoutInflater.from(this).inflate(R.layout.my_loans_table_row, null) as LinearLayout
                val loanIdTextView = newRow.findViewById<TextView>(R.id.my_loans_tv_loan_id)
                val amountTextView = newRow.findViewById<TextView>(R.id.my_loans_tv_amount)
                val btnDetails = newRow.findViewById<Button>(R.id.my_loans_tv_button_details)

                loanIdTextView.text = loan.loanID.toString()
                amountTextView.text = (loan.amount - loan.amountPaid).toString()

                btnDetails.setOnClickListener {
                    val intent = Intent(this, LoanDetail::class.java)
                    intent.putExtra("loanID", loan.loanID)
                    startActivity(intent)
                }

                linearLayout.addView(newRow)
            }
        }
    }
}