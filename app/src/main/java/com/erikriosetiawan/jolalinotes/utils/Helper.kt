package com.erikriosetiawan.jolalinotes.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setCustomActionBar(toolbar: Toolbar?, title: String? = "") {
    this.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
    }
}
