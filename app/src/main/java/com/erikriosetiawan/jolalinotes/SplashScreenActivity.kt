package com.erikriosetiawan.jolalinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setStatusBar()
    }

    // Change the status bar color
    private fun setStatusBar() {
        window.addFlags((WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS))
        window.addFlags((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS))
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorSplashScreenStatusBar)
    }
}