package com.una.models

class LoanModel(
    var idAssociate: Int = 0,
    var loanType: String = "",
    var amount: Int = 0,
    var amountPaid: Int? = null,
    var loanPeriod: String = "",
    var feeQuantity: Int = 0,
    var feeAmount: Int = 0
){}
