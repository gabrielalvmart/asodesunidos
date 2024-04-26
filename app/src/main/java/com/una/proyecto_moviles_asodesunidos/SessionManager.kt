package com.una.proyecto_moviles_asodesunidos

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import androidx.core.content.edit
import com.una.data.SessionData

object SessionManager {
    private lateinit var sharedPreferences: SharedPreferences
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
