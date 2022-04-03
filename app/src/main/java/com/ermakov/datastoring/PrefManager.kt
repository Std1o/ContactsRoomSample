package com.ermakov.datastoring

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    companion object {
        private const val PREF_NAME = "Data Storing"
        private const val KEY_NAME = "name"
        private const val KEY_AGE = "age"
    }

    private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    fun getName(): String {
        return pref.getString(KEY_NAME, "")!!
    }

    fun setName(value: String) {
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun getAge(): Int {
        return pref.getInt(KEY_AGE, 0)
    }

    fun setAge(value: Int) {
        editor.putInt(KEY_AGE, value)
        editor.apply()
    }
}