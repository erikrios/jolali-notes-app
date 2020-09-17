package com.erikriosetiawan.jolalinotes.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

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