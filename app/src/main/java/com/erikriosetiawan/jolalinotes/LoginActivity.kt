package com.erikriosetiawan.jolalinotes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun registerAccount(view: View) {
        Toast.makeText(this, "Register Account clicked!", Toast.LENGTH_SHORT).show()
    }
}