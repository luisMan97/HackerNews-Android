package com.example.hackernewscolegium.utils.helpers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.Serializable
import java.lang.reflect.Type

class SharedPreference(val context: Context) {

    private val prefsName = "kotlinCodes"
    val sharedPref: SharedPreferences? = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: Serializable) {
        val editor = sharedPref?.edit()
        val gson = Gson()
        val json = gson.toJson(value)

        editor?.putString(KEY_NAME, json)

        editor?.apply()
    }

    inline fun <reified T> getParseArray(KEY_NAME: String, value: Type): T? {
        val gson = GsonBuilder().create()
        val json = this.sharedPref?.getString(KEY_NAME, "")
        return gson.fromJson<T>(json, value)
    }

}
