package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewstate.LoginViewState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: NotesRepository) : ViewModel() {

    private val _viewState = MutableLiveData<LoginViewState>().apply {
        value = LoginViewState(loading = true)
    }
    val viewState: LiveData<LoginViewState>
        get() = _viewState

    fun authenticateUser(email: String, password: String) {
        val requestCall = repository.authenticateUser(email, password)
        requestCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
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
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                _viewState.value = LoginViewState(
                    loading = false,
                    exception = Exception(t)
                )
            }
        })
    }
}