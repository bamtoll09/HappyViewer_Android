package me.bamtoll.obi.happyviewer.Preference

import android.content.Context
import android.content.SharedPreferences

class Preference(name: String, appContext: Context) {

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        sharedPreferences = appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun save(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun save(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun save(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun save(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun save(key: String, value: Array<String>) {
        editor.putStringSet(key, value.toMutableSet())
        editor.apply()
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun findAll(): Array<String> {
        return sharedPreferences.all.keys.toTypedArray()
    }

    fun find(key: String): String {
        return sharedPreferences.getString(key, "")
    }
}