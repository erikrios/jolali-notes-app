package com.erikriosetiawan.jolalinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erikriosetiawan.jolalinotes.databinding.ActivityDashboardBinding

private lateinit var binding: ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}