package com.una.proyecto_moviles_asodesunidos

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.una.models.AssociateModel

object AsodesunidosDB {
    private val db = Firebase.database
    private val associatesRef: DatabaseReference = db.getReference("associates")

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

    fun addAssociate(associate: AssociateModel, onComplete: (Boolean, String?) -> Unit) {
        val associateId = associate.id
        if (associateId != null) {
            associate.id = associateId
            associatesRef.child(associateId).setValue(associate)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true, null)
                    } else {
                        onComplete(false, task.exception?.message)
                    }
                }
        } else {
            onComplete(false, "Failed to generate unique ID for associate")
        }
    }

}