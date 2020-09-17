package com.erikriosetiawan.jolalinotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.databinding.ActivityMainBinding
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.utils.Constant.PREF_FILE_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: NotesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSharedPreferences(PREF_FILE_KEY, MODE_PRIVATE)

        repository = NotesRepository()
    }
}