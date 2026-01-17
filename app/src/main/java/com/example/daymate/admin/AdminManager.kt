package com.example.daymate.admin

import android.content.Context
import android.content.SharedPreferences

object AdminManager {
    private const val PREF_NAME = "DayMateAdminPrefs"
    private const val KEY_IS_ADMIN = "is_admin"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setAdminStatus(context: Context, isAdmin: Boolean) {
        getPreferences(context).edit().putBoolean(KEY_IS_ADMIN, isAdmin).apply()
    }

    fun isAdmin(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_IS_ADMIN, false)
    }

    fun clearAdminStatus(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}

