package com.una.proyecto_moviles_asodesunidos

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.una.models.UserModel

object asodesunidos_db {
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