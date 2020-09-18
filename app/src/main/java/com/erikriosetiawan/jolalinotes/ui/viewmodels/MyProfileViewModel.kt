package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewstate.MyProfileViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyProfileViewModel(private val repository: NotesRepository, private val token: String) :
    ViewModel() {

    private val _viewState = MutableLiveData<MyProfileViewState>().apply {
        value = MyProfileViewState(loading = true)
    }

    val viewState: LiveData<MyProfileViewState>
        get() = _viewState

    init {
        getUserDetails(token)
    }

    private fun getUserDetails(token: String): Job {
        return viewModelScope.launch {

            try {
                val response = repository.getUserDetails(token)

                when {
                    response.isSuccessful -> {
                        _viewState.value = MyProfileViewState(
                            loading = false,
                            responseCode = response.code(),
                            user = response.body()
                        )
                    }

                    response.code() == 401 -> {
                        _viewState.value = MyProfileViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Access denied. No token provided.")
                        )
                    }

                    response.code() == 400 -> {
                        _viewState.value = MyProfileViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid token.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = MyProfileViewState(
                    loading = false,
                    exception = Exception(e)
                )
            }
        }
    }
}