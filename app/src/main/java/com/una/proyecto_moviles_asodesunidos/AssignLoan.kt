package com.una.proyecto_moviles_asodesunidos

import android.annotation.SuppressLint
import android.icu.util.CurrencyAmount
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.una.models.LoanModel
import com.una.models.UserModel
import com.una.utils.calculateMonthlyPayment

class AssignLoan : AppCompatActivity() {
    private lateinit var btnAssignLoanBack: Button
    private lateinit var txtAssignLoanAssociateID: EditText
    private lateinit var txtAssignLoanMonthlyPayment: EditText
    private lateinit var txtLoanAmount: EditText
    private lateinit var txtAssignLoanWage: EditText
    private lateinit var btnAssignLoanSearchAssociate: Button
    private lateinit var btnAssignLoanCalculateMonthlyPayment: Button
    private lateinit var btnAssignLoanAssign: Button

    private lateinit var spnLoanType: Spinner
    private lateinit var spnLoanTerm: Spinner
    private lateinit var spnLoanTypeAdapter: ArrayAdapter<String>
    private lateinit var spnLoanTermAdapter: ArrayAdapter<String>

    private lateinit var userList: List<UserModel>
    private var currentUser: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.assign_loan)
        AsodesunidosDB.fetchUsers()
        userList = AsodesunidosDB.getUsers()

        initializeVariables()
        setSecondSectionInvisible()
        setOnClickListeners()
    }


    private fun handleAssignLoanAssign() {

        if (txtLoanAmount.text.isEmpty()){
            txtLoanAmount.error = "Please fill this field"
            return
        }
        if (txtAssignLoanMonthlyPayment.text.isEmpty()){
            txtAssignLoanMonthlyPayment.error = "Please fill this field"
            return
        }

        txtAssignLoanMonthlyPayment.error = null;
        txtLoanAmount.error = null
        val interestRate: Double = when {
            spnLoanType.selectedItem.toString().contains("7.5") -> 7.5
            spnLoanType.selectedItem.toString().contains("8") -> 8.0
            spnLoanType.selectedItem.toString().contains("10") -> 10.0
            spnLoanType.selectedItem.toString().contains("12") -> 12.0
            else -> 0.0
        }
        val loanTermInYears: Int = when {
            spnLoanTerm.selectedItem.toString().contains("3") -> 3
            spnLoanTerm.selectedItem.toString().contains("5") -> 5
            spnLoanTerm.selectedItem.toString().contains("10") -> 10
            else -> 0
        }
        val loanAmount: Double = txtLoanAmount.text.toString().toDouble()
        val monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, loanTermInYears)

        if (monthlyPayment > currentUser!!.calculateMaximumMonthlyPayment()){
            txtAssignLoanMonthlyPayment.error = "The monthly payment is too high!"
            Toast.makeText(this, "Couldn't add loan",Toast.LENGTH_SHORT).show()
        }
        else{
            val loanID = (currentUser!!.loans.filter{ true }.size).toString()
            val tempLoan = LoanModel(loanAmount, loanTermInYears, interestRate, loanID)
            AsodesunidosDB.addLoanToAssociate(currentUser!!.id,tempLoan) { success, message ->
                if(success) {
                    Toast.makeText(this, "Loan Added Successfully", Toast.LENGTH_SHORT).show()
                    SessionManager.updateUser()
                    finish()
                }
                else Toast.makeText(this, "Loan failed to be added",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun handleAssociateSearchOnClick(){
        if (validateAssociateId()) {
            var tempUser: UserModel? = null
            val associateId = txtAssignLoanAssociateID.text.toString()
            for (user in userList){
                if (user.id == associateId){
                    tempUser = user
                    break
                }
            }
            if (tempUser != null) handleUserFound(tempUser)
            else handleUserNotFound()
        }
    }

    private fun handleUserNotFound(){
        Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
        txtAssignLoanAssociateID.setText("")
        setSecondSectionInvisible()
        currentUser = null
    }

    private fun handleUserFound(user: UserModel){
        Toast.makeText(this, "User found!", Toast.LENGTH_SHORT).show()
        txtAssignLoanWage.setText(user.salary.toString())
        currentUser = user
        setSecondSectionVisible()
    }

    @SuppressLint("SetTextI18n")
    private fun handleCalculateMonthlyPayment(){
        if(validateLoanAmount()){
            val interestRate: Double = when {
                spnLoanType.selectedItem.toString().contains("7.5") -> 7.5
                spnLoanType.selectedItem.toString().contains("8") -> 8.0
                spnLoanType.selectedItem.toString().contains("10") -> 10.0
                spnLoanType.selectedItem.toString().contains("12") -> 12.0
                else -> 0.0
            }
            val loanTermInYears: Int = when {
                spnLoanTerm.selectedItem.toString().contains("3") -> 3
                spnLoanTerm.selectedItem.toString().contains("5") -> 5
                spnLoanTerm.selectedItem.toString().contains("10") -> 10
                else -> 0
            }
            val loanAmount: Double = txtLoanAmount.text.toString().toDouble()
            val monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, loanTermInYears)
            Toast.makeText(this, "Monthly payment calculated", Toast.LENGTH_SHORT).show()
            txtAssignLoanMonthlyPayment.setText("%.2f".format(monthlyPayment))
        }
    }
    private fun initializeVariables(){
        txtAssignLoanAssociateID = findViewById(R.id.txt_assignLoan_associateID)
        txtAssignLoanMonthlyPayment = findViewById(R.id.txt_assignLoan_monthlyPayment)
        txtLoanAmount = findViewById(R.id.txt_loan_amount)
        txtAssignLoanWage = findViewById(R.id.txt_assignLoan_wage)
        spnLoanTerm = findViewById(R.id.spn_assignLoan_loanTime)
        spnLoanType = findViewById(R.id.spn_assignLoan_loanType)
        btnAssignLoanCalculateMonthlyPayment = findViewById(R.id.btn_assignLoan_calculateMonthlyPayment)
        btnAssignLoanAssign = findViewById(R.id.btn_assignLoan_assign)
        btnAssignLoanBack = findViewById(R.id.btn_assignLoan_back)
        btnAssignLoanSearchAssociate = findViewById(R.id.btn_assignLoan_searchAssociate)


        spnLoanTypeAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,LoanModel.LoanTypeOption.getPrettyString())
        spnLoanTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnLoanType.adapter = spnLoanTypeAdapter

        spnLoanTermAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,LoanModel.LoanTermOption.getPrettyString())
        spnLoanTermAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnLoanTerm.adapter = spnLoanTermAdapter

        spnLoanTerm.setSelection(0)
        spnLoanType.setSelection(0)

    }
    private fun setSecondSectionInvisible(){
        val textviewType = findViewById<TextView>(R.id.textview_assign_loan_type)
        val textviewTerm = findViewById<TextView>(R.id.textview_assign_loan_term)
        val textviewAmount = findViewById<TextView>(R.id.textview_assign_loan_amount)
        val textviewWage = findViewById<TextView>(R.id.textview_assign_loan_wage)

        textviewType.visibility = View.INVISIBLE
        textviewTerm.visibility = View.INVISIBLE
        textviewAmount.visibility = View.INVISIBLE
        textviewWage.visibility = View.INVISIBLE
        spnLoanTerm.visibility = View.INVISIBLE
        spnLoanType.visibility = View.INVISIBLE
        txtLoanAmount.visibility = View.INVISIBLE
        txtAssignLoanMonthlyPayment.visibility = View.INVISIBLE
        txtAssignLoanWage.visibility = View.INVISIBLE
        btnAssignLoanCalculateMonthlyPayment.visibility = View.INVISIBLE
        btnAssignLoanAssign.visibility = View.INVISIBLE

    }
    private fun setSecondSectionVisible() {
        val textviewType = findViewById<TextView>(R.id.textview_assign_loan_type)
        val textviewTerm = findViewById<TextView>(R.id.textview_assign_loan_term)
        val textviewAmount = findViewById<TextView>(R.id.textview_assign_loan_amount)
        val textviewWage = findViewById<TextView>(R.id.textview_assign_loan_wage)

        textviewType.visibility = View.VISIBLE
        textviewTerm.visibility = View.VISIBLE
        textviewAmount.visibility = View.VISIBLE
        textviewWage.visibility = View.VISIBLE
        spnLoanTerm.visibility = View.VISIBLE
        spnLoanType.visibility = View.VISIBLE
        txtLoanAmount.visibility = View.VISIBLE
        txtAssignLoanMonthlyPayment.visibility = View.VISIBLE
        txtAssignLoanWage.visibility = View.VISIBLE
        btnAssignLoanCalculateMonthlyPayment.visibility = View.VISIBLE
        btnAssignLoanAssign.visibility = View.VISIBLE

    }


    private fun setOnClickListeners(){
        btnAssignLoanBack.setOnClickListener {
            finish()
        }

        btnAssignLoanSearchAssociate.setOnClickListener {
            handleAssociateSearchOnClick()
        }

        btnAssignLoanCalculateMonthlyPayment.setOnClickListener {
            handleCalculateMonthlyPayment()
        }

        btnAssignLoanAssign.setOnClickListener {
            handleAssignLoanAssign()
        }
    }

    private fun validateAssociateId(): Boolean {
        val associateId = txtAssignLoanAssociateID.text.toString()
        if (associateId.isEmpty()) {
            txtAssignLoanAssociateID.error = "Associate ID is required"
            return false
        }
        return true
    }

    private fun validateLoanAmount(): Boolean {
        val loanAmount = txtLoanAmount.text.toString()
        if (loanAmount.isEmpty()) {
            txtLoanAmount.error = "El valor del prestamo es requerido"
            return false
        }
        val loanAmountValue = loanAmount.toDoubleOrNull()
        if (loanAmountValue == null || loanAmountValue <= 0) {
            txtLoanAmount.error = "Ingresa un valor válido para el prestamo"
            return false
        }
        return true
    }

    private fun validateFields(): Boolean {
        return validateAssociateId() && validateLoanAmount()
    }

}