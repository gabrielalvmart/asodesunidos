package com.una.models

class SavingModel {
    var idAssociate: Int
    var savingType: String?
    var savingAmount: Int
    var fee: Int

    constructor(
        idAssociate: Int = 0,
        savingType: String? = null,
        savingAmount: Int = 0,
        fee: Int = 0
    ) {
        this.idAssociate = idAssociate
        this.savingType = savingType
        this.savingAmount = savingAmount
        this.fee = fee
    }
}