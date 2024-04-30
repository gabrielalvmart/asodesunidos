package com.una.models

class LoanModel(
    var amount: Double = 0.0, // Initial amount sent to associate
    var amountPaid: Double = 0.0, // How much the associate has paid off
    var loanTermYears: Int = 0, // How many years to pay off
    var interestRate: Double = 0.0, // Percentage rate
    var loanID: String? = ""
) {

    enum class LoanTypeOption(var interest: Number) {
        MORTGAGE(7.5), EDUCATION(8), PERSONAL(10), TRAVEL(12);

        companion object {
            fun getLoanTypes(): Array<String> {
                return entries.map { it.name }.toTypedArray()
            }

            fun getInterestRates(): Array<Number> {
                return entries.map { it.interest }.toTypedArray()
            }

            fun getPrettyString(): Array<String> {
                return entries.map { "${it.name} - ${it.interest}%" }.toTypedArray()
            }
        }
    }



    enum class LoanTermOption(val years: Int) {
        SHORT(3), MEDIUM(5), LONG(10);

        companion object {
            fun getLoanTypes(): Array<String> {
                return entries.map { it.name }.toTypedArray()
            }

            fun getInterestRates(): Array<Number> {
                return entries.map { it.years }.toTypedArray()
            }

            fun getPrettyString(): Array<String> {
                return entries.map { "${it.years} years" }.toTypedArray()
            }
        }
    }


    // Constructor with loanType, amount, loanTerm, and interestRate
    constructor(
        amount: Double,
        loanTerm: Int,
        interestRate: Double,
        loanID: String
    ) : this(amount = amount, amountPaid = 0.0, loanTermYears=loanTerm, interestRate = interestRate, loanID=loanID)

    fun addID(id: String){
        this.loanID=id
    }

    fun getAmountDue(): Double{
        return amount - amountPaid
    }
}
