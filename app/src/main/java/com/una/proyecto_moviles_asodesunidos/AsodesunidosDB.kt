package com.una.proyecto_moviles_asodesunidos

import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object AsodesunidosDB {
    private val db = Firebase.database
    data class User(var password: String, var type: String, var email: String){
        constructor() : this("", "", "")
    }
    private var users : MutableList<User> = mutableListOf()


    fun fetchUsers(){
        db.getReference("users").get().addOnSuccessListener {
            for (child in it.children){
                val user = child.getValue<User>()
                if (user != null && !users.contains(user)) {
                    users.add(user)
                }
            }
        }

    }

    private fun userExists(email: String) : User? {
        return users.find{ it.email == email}
    }

    fun attemptLogin(email: String, password: String) : User? {
        var tempUser: User? = userExists(email);
        return if(tempUser != null && tempUser.password == password) tempUser else null
    }

}