package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewstate.RegisterViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: NotesRepository) : ViewModel() {

    private val _viewState = MutableLiveData<RegisterViewState>().apply {
        value = RegisterViewState(loading = false)
    }

    val viewState: LiveData<RegisterViewState>
        get() = _viewState

    fun registerUser(name: String, email: String, password: String): Job {

        return viewModelScope.launch {

            _viewState.value = RegisterViewState(loading = true)

            try {
                val response = repository.registerUser(name, email, password)

                when {
                    response.isSuccessful -> {
                        _viewState.value = RegisterViewState(
                            loading = false,
                            responseCode = response.code(),
                            token = response.headers()["Auth-Token"],
                            user = response.body()
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = RegisterViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("User already registered.")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = RegisterViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = RegisterViewState(
                    loading = false,
                    exception = Exception(e)
                )
            }
        }
    }
}