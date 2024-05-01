package com.una.proyecto_moviles_asodesunidos

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.una.models.LoanModel
import com.una.models.SavingModel
import com.una.models.UserModel
import kotlinx.coroutines.flow.callbackFlow

object AsodesunidosDB {
    private val db = Firebase.database
    private val associatesRef: DatabaseReference = db.getReference("users")

    private var users : MutableList<UserModel> = mutableListOf()

    fun getUsers(): MutableList<UserModel> {
        fetchUsers()
        return users
    }

    fun fetchUsers(){
        db.getReference("users").get().addOnSuccessListener {
            users = mutableListOf()
            for (child in it.children){
                val user = child.getValue<UserModel>()
                if (user != null && !users.contains(user)) {
                    users.add(user)
                }
            }
        }
    }

    fun userExists(userid: String) : UserModel? {
        return users.find{ it.id == userid}
    }

    fun attemptLogin(userid: String, password: String) : UserModel? {
        val tempUser: UserModel? = userExists(userid)
        return if(tempUser != null && tempUser.comparePassword(password)) tempUser else null
    }

    fun addAssociate(associate: UserModel, onComplete: (Boolean, String?) -> Unit) {
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

    fun getAssociate(associateId: String, onComplete: (UserModel?) -> Unit) {
        associatesRef.child(associateId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val associateData = snapshot.getValue(UserModel::class.java)
                onComplete(associateData)
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(null)
            }
        })
    }
    fun updateAssociate(updatedUser: UserModel, onComplete: (Boolean, String?) -> Unit) {
        val userId = updatedUser.id
        if (userId != "") {
            val userRef = associatesRef.child(userId)
            userRef.setValue(updatedUser)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true, null)
                    } else {
                        onComplete(false, task.exception?.message)
                    }
                }
        } else {
            onComplete(false, "El ID del usuario no puede estar vacío")
        }
    }

    fun updateSavingsValue(userId: String, savingsType: String, newValue: Int, onComplete: (Boolean, String?) -> Unit) {
        associatesRef.child(userId).child("savings").child(savingsType).setValue(newValue)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
    fun addLoanToAssociate(associateId: String, loan: LoanModel, onComplete: (Boolean, String?) -> Unit) {
        val userRef = associatesRef.child(associateId)
        userRef.child("loans").child(loan.loanID.toString()).setValue(loan)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                    Log.d("MRG", "Correctly added")
                } else {
                    onComplete(false, task.exception?.message)
                    Log.e("MRG", "Not added at all")
                }
            }
        fetchUsers()
    }

    fun getSavings(associateId: String, saving: (SavingModel?)->Unit){
        associatesRef.child(associateId).child("savings").addListenerForSingleValueEvent(object :
            ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val savingsData = snapshot.getValue(SavingModel::class.java)
                    saving(savingsData)
                }
                override fun onCancelled(error: DatabaseError) {
                    saving(null)
                }
            })
    }

}