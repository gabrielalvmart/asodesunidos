package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.una.models.LoanModel

class LoanDetail :AppCompatActivity() {
    private var loanDetail: LoanModel? = null
    private lateinit var txtLoanAmount: EditText
    private lateinit var txtAmountPaid: EditText
    private lateinit var txtRemaining: EditText
    private lateinit var txtMakePayment: EditText
    private lateinit var btnPay: Button
    private lateinit var btnGoBack: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.my_loans_loan_detail)

        val loanID = intent.getStringExtra("loanID")
        loanDetail = SessionManager.getUser()!!.loans.filter{ true }.find{ it.loanID == loanID}

        initializeVars()

        txtLoanAmount.setText(loanDetail!!.amount.toString())
        txtAmountPaid.setText(loanDetail!!.amountPaid.toString())
        txtRemaining.setText((loanDetail!!.amount - loanDetail!!.amountPaid).toString())

        btnGoBack.setOnClickListener { finish() }

        btnPay.setOnClickListener {
            makePayment()
        }

    }

    private fun initializeVars(){
        txtLoanAmount = findViewById(R.id.txt_loan_details_total_amount)
        txtAmountPaid = findViewById(R.id.txt_loan_details_amount_paid)
        txtRemaining = findViewById(R.id.txt_loan_details_amount_remaining)
        txtMakePayment = findViewById(R.id.txt_loan_make_payment)
        btnPay = findViewById(R.id.btn_loan_details_pay)
        btnGoBack = findViewById(R.id.btn_loan_detail_back)
    }


    private fun makePayment(){
        val tempLoan = loanDetail
        val currentPaymentAmount = txtMakePayment.text.toString().toInt()
        tempLoan!!.amountPaid = (tempLoan.amountPaid + currentPaymentAmount )
        AsodesunidosDB.addLoanToAssociate(SessionManager.getUser()!!.id,tempLoan) { success, _ ->
            if(success) {
                Toast.makeText(this, "Payment added successfully", Toast.LENGTH_SHORT).show()
                SessionManager.updateUser()
                finish()
            }
            else Toast.makeText(this, "Loan wasn't paid", Toast.LENGTH_SHORT).show()
        }
    }
}