package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.jolalinotes.repository.NotesRepository

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }

        throw IllegalArgumentException()
    }
}