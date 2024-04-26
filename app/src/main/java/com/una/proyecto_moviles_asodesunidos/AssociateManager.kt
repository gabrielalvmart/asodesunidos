package com.una.proyecto_moviles_asodesunidos

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.una.models.AssociateModel

object AssociateManager {
    private lateinit var sharedPreferences: SharedPreferences

    fun saveAssociate(associate: AssociateModel) {
        sharedPreferences.edit {
            putInt("id", associate.id)
            putString("name", associate.name)
            putInt("salary", associate.salary)
            putString("phone", associate.phone)
            putString("birthDate", associate.birthDate)
            putString("maritalStatus", associate.maritalStatus)
            putString("address", associate.address)
            commit()
        }
    }

    fun getAssociate(): AssociateModel {
        val id = sharedPreferences.getInt("id", 0)
        val name = sharedPreferences.getString("name", "") ?: ""
        val salary = sharedPreferences.getInt("salary", 0)
        val phone = sharedPreferences.getString("phone", "") ?: ""
        val birthDate = sharedPreferences.getString("birthDate", "") ?: ""
        val maritalStatus = sharedPreferences.getString("maritalStatus", "") ?: ""
        val address = sharedPreferences.getString("address", "") ?: ""

        return AssociateModel(id, name, salary, phone, birthDate, maritalStatus, address)
    }

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("asodesunidos", Context.MODE_PRIVATE)
    }

    fun clearAssociate() {
        sharedPreferences.edit {
            clear()
        }
    }
}
