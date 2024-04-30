package com.una.proyecto_moviles_asodesunidos

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.una.data.SessionData
import com.una.models.UserModel

object SessionManager {
    private lateinit var sharedPreferences: SharedPreferences
    private var currentUser: UserModel? = null
    fun saveLogin(userId: String, sessionId: String) {
        sharedPreferences.edit {
            putString("userId", userId)
            putString("sessionId", sessionId)
            putLong("loginTimestamp", System.currentTimeMillis())
            commit()
        }
    }

    fun getData(data: String): String? {
        return sharedPreferences.getString(data, null)
    }

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("asodesunidos", Context.MODE_PRIVATE)
    }

    fun setUser(user: UserModel){
        currentUser = user
    }

    fun getUser(): UserModel? {
        return currentUser
    }

    fun updateUser() {
        val tempUser = AsodesunidosDB.getUsers().find { it.id == currentUser!!.id }
        currentUser = tempUser
    }

    fun sessionUserExists(): Boolean{
        return currentUser != null
    }
    fun getSession(): SessionData? {
        val userId = sharedPreferences.getString("userId", null)
        val sessionId = sharedPreferences.getString("sessionId", null)
        return if (userId.isNullOrEmpty() or sessionId.isNullOrEmpty()){
            null
        } else SessionData(userId!!, sessionId!!)

    }

    fun clearSession() {
        sharedPreferences.edit {
            clear()
        }
    }


}
