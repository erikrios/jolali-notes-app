package com.erikriosetiawan.jolalinotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.databinding.ActivityMainBinding
import com.erikriosetiawan.jolalinotes.utils.Constant.PREF_FILE_KEY
import com.startapp.sdk.adsbase.StartAppAd

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // TODO Always use test ads during development and testing
//        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG)
        StartAppAd.disableSplash()

        setContentView(binding.root)

        getSharedPreferences(PREF_FILE_KEY, MODE_PRIVATE)
    }
}