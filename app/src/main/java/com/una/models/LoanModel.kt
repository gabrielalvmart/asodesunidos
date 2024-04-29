package com.una.models

class LoanModel(
    var loanType: LoanTypeOption,
    var amount: Int = 0,
    var amountPaid: Int? = null,
    var loanTerm: LoanTermOption,
    var feeQuantity: Int = 0,
    var feeAmount: Int = 0
){
    enum class LoanTypeOption{
        MORTGAGE, EDUCATION, PERSONAL, TRAVEL
    }

    enum class LoanTermOption(val years: Int){
        SHORT(3),MEDIUM(5),LONG(10),
    }
}
