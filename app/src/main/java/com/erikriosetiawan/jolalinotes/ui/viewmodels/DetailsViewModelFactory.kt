package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.jolalinotes.repository.NotesRepository

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val repository: NotesRepository, private val token: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repository, token) as T
        }

        throw IllegalArgumentException()
    }
}