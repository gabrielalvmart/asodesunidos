package com.una.proyecto_moviles_asodesunidos

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class CalculateMonthlyPayment : AppCompatActivity() {
    private val loanTypeOptions = arrayOf("Mortgage (7.5%)", "Education (8%)", "Personal (10%)", "Trips (12%)")
    private val loanTermOptions = arrayOf("3 years", "5 years", "10 years")
    private lateinit var loanTypeAdapter: ArrayAdapter<String>
    private lateinit var loanTernAdapter: ArrayAdapter<String>

    private lateinit var spnLoanType : Spinner
    private lateinit var spnLoanTime : Spinner
    private lateinit var btnCalculateMonthlyPayment: Button
    private lateinit var btnBack: Button
    private lateinit var txtLoanAmount: EditText
    private lateinit var txtMonthlyPayment: EditText
    private lateinit var txtvwMonthlyPayment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AsodesunidosDB.fetchUsers()
        setContentView(R.layout.calculate_monthly_payment)

        // Initializations
        loanTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, loanTypeOptions)
        loanTernAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, loanTermOptions)
        spnLoanType = findViewById(R.id.spn_assignLoan_loanType)
        spnLoanTime = findViewById(R.id.spn_assignLoan_loanTime)
        btnCalculateMonthlyPayment = findViewById(R.id.btn_calculate_monthly_payment)
        txtLoanAmount = findViewById(R.id.txt_loan_amount)
        txtMonthlyPayment = findViewById(R.id.txt_monthly_payment_result)
        txtvwMonthlyPayment = findViewById(R.id.txtvw_monthly_payment_result)
        btnBack = findViewById(R.id.btn_calculate_monthly_payment_back)
        // Spinner logic
        loanTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        loanTernAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnLoanType.adapter = loanTypeAdapter
        spnLoanTime.adapter = loanTernAdapter
        spnLoanType.setSelection(0)
        spnLoanTime.setSelection(0)

        // Making results invisible
        txtMonthlyPayment.visibility = View.INVISIBLE
        txtvwMonthlyPayment.visibility = View.INVISIBLE

        btnBack.setOnClickListener { finish() }
        // Calculate the monthly payment
        btnCalculateMonthlyPayment.setOnClickListener {
            if (validateLoanAmount()) {
                val loanAmount =
                    txtLoanAmount.text.toString().toDoubleOrNull() ?: return@setOnClickListener
                val annualInterestRate = when (spnLoanType.selectedItemPosition) {
                    0 -> 7.5 // Mortgage (7.5%)
                    1 -> 8.0 // Education (8%)
                    2 -> 10.0 // Personal (10%)
                    3 -> 12.0 // Trips (12%)
                    else -> return@setOnClickListener
                }
                val loanTermInYears = when (spnLoanTime.selectedItemPosition) {
                    0 -> 3 // 3 years
                    1 -> 5 // 5 years
                    2 -> 10 // 10 years
                    else -> return@setOnClickListener
                }


                val monthlyPayment = calculateMonthlyPayment(
                    txtLoanAmount.text.toString().toInt(),
                    annualInterestRate,
                    loanTermInYears
                )
                txtMonthlyPayment.setText(monthlyPayment.toString())
                txtMonthlyPayment.visibility = View.VISIBLE
                txtvwMonthlyPayment.visibility = View.VISIBLE

                // TODO: Add a 'back' button
            }
        }
    }
    private fun calculateMonthlyPayment(loanAmount: Int, annualInterestRate: Double, loanTermInYears: Int): String {
        val monthlyInterestRate = annualInterestRate / 12 / 100
        val numberOfPayments = loanTermInYears * 12

        val numerator = loanAmount * monthlyInterestRate
        val denominator = 1 - (1 + monthlyInterestRate).pow(-numberOfPayments.toDouble())

        return "%.2f".format(numerator / denominator)
    } // TODO: Abstract this to another maths file

    private fun validateLoanAmount(): Boolean {
        val loanAmount = txtLoanAmount.text.toString()
        if (loanAmount.isEmpty()) {
            txtLoanAmount.error = "Loan amount is required"
            return false
        }

        val loanAmountValue = loanAmount.toDoubleOrNull()
        if (loanAmountValue == null || loanAmountValue <= 0) {
            txtLoanAmount.error = "Enter a valid loan amount"
            return false
        }
        return true
    }
}