package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewstate.DashboardViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: NotesRepository, private val token: String) :
    ViewModel() {

    private val _viewState = MutableLiveData<DashboardViewState>().apply {
        value = DashboardViewState(loading = true)
    }

    val viewState: LiveData<DashboardViewState>
        get() = _viewState

    init {
        getNotes(token)
    }

    private fun getNotes(token: String): Job {
        return viewModelScope.launch {
            _viewState.value = DashboardViewState(loading = true)

            try {
                val response = repository.getNotes(token)

                when {
                    response.isSuccessful -> {
                        _viewState.value = DashboardViewState(
                            loading = false,
                            responseCode = response.code(),
                            notes = response.body()
                        )
                    }
                    response.code() == 401 -> {
                        _viewState.value = DashboardViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Access denied. No token provided.")
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = DashboardViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid token.")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = DashboardViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = DashboardViewState(
                    loading = false,
                    exception = Exception(e)
                )
            }
        }
    }
}