package com.una.models

class UserModel(
    var username: String,
    var email: String,
    private var password: String,
    var type: String
) {
    fun comparePassword(password_attempt: String) : Boolean {
        return password_attempt.equals(password)
    }
}