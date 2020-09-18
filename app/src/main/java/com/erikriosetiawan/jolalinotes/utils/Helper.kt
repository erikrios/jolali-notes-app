package com.erikriosetiawan.jolalinotes.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.setCustomActionBar(toolbar: Toolbar?, title: String? = "") {
    this.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
    }
}

/**
 * Get token from shared preferences
 */
fun Fragment.getToken(): String? {
    val sharedPref: SharedPreferences? =
        activity?.getSharedPreferences(Constant.PREF_FILE_KEY, Context.MODE_PRIVATE)
    return sharedPref?.getString(Constant.PREF_AUTH_TOKEN, "")
}

/**
 * Date formatting
 */
@SuppressLint("SimpleDateFormat")
fun Context.dateFormat(isoFormat: String): String? {
    val dateFormat: DateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = dateFormat.parse(isoFormat)
    return date?.let {
        SimpleDateFormat("yyyy/MM/dd").format(date)
    }
}