package com.una.models

class UserModel(
    var username: String? = null,
    var email: String? = null,
    private var password: String? = null,
    var type: String? = null
) {
    fun comparePassword(password_attempt: String) : Boolean {
        return password_attempt.equals(password)
    }
}