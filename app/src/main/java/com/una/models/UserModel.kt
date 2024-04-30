package com.una.models

class UserModel(
    var id: String = "",
    var password: String? = null,
    var type: String? = null,
    var name: String = "",
    var salary: Int = 0,
    var phone: String = "",
    var birthDate: String = "",
    var maritalStatus: String = "",
    var address: String = "",
    var loans: List<LoanModel> = emptyList(),
    var savings : SavingModel ?= null

){
    fun comparePassword(passwordAttempt: String) : Boolean {
        return passwordAttempt == password
    }


    fun calculateMaximumMonthlyPayment(): Double{
        return salary * 0.45
    }
}