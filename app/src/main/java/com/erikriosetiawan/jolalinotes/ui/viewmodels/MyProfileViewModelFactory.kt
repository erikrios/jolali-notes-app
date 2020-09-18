package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.jolalinotes.repository.NotesRepository

@Suppress("UNCHECKED_CAST")
class MyProfileViewModelFactory(
    private val repository: NotesRepository,
    private val token: String
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProfileViewModel::class.java)) {
            return MyProfileViewModel(repository, token) as T
        }

        throw IllegalArgumentException()
    }
}