package com.erikriosetiawan.jolalinotes.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewstate.LoginViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: NotesRepository) : ViewModel() {

    private val _viewState = MutableLiveData<LoginViewState>().apply {
        value = LoginViewState(loading = false)
    }
    val viewState: LiveData<LoginViewState>
        get() = _viewState

    fun authenticateUser(email: String, password: String): Job {
        return viewModelScope.launch {

            _viewState.value = LoginViewState(loading = true)

            try {
                val response = repository.authenticateUser(email, password)

                when {
                    response.isSuccessful -> {
                        _viewState.value = LoginViewState(
                            loading = false,
                            responseCode = response.code(),
                            token = response.body()
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = LoginViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid email or password.")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = LoginViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = LoginViewState(
                    loading = false,
                    exception = Exception(e)
                )
            }
        }
    }
}