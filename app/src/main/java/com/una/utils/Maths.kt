package com.una.utils

import kotlin.math.pow

fun calculateMonthlyPayment(loanAmount: Double, interest: Double, termInYears: Int): Double{
    val monthlyInterestRate = interest / 12 / 100
    val numberOfPayments = termInYears * 12

    val numerator = loanAmount * monthlyInterestRate
    val denominator = 1 - (1 + monthlyInterestRate).pow(-numberOfPayments)

    return numerator / denominator
}