package com.example.newdesign.utils

import android.content.Context
import com.dro.doctoronline_doctorapp.localDataBase.SharedPreferencesTool
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    lateinit var context: Context
    fun setup(context: Context) {
        this.context = context
    }

    fun convertLongToDate(time:Long):String{
        val date= Date(time)
        val sdf= SimpleDateFormat("dd:MM:yyyy", Locale.US)
        return sdf.format(date)
    }

    fun Long.toTimeDateString(): String {
        val dateTime = Date(this)
        val format = SimpleDateFormat(" yyyy/MM/dd", Locale.US)
        return format.format(dateTime)
    }
    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy.MM.dd")
        return df.parse(date).time
    }


    fun getToken(): String? {
        return SharedPreferencesTool.getString(context,SharedPreferencesKeys.PREF_USER_TOKEN.name)
    }

    fun setToken(token: String) {
        SharedPreferencesTool.setString(context, SharedPreferencesKeys.PREF_USER_TOKEN.name, token)
    }
}