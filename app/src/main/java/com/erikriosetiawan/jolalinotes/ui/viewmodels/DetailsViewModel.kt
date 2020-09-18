package com.erikriosetiawan.jolalinotes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewstate.DetailsViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: NotesRepository, private val token: String) :
    ViewModel() {

    private val _viewState = MutableLiveData<DetailsViewState>().apply {
        value = DetailsViewState(loading = false)
    }

    val viewState: LiveData<DetailsViewState>
        get() = _viewState

    fun getNote(id: String): Job {
        return viewModelScope.launch {
            _viewState.value = DetailsViewState(loading = true)

            try {
                val response = repository.getNote(id, token)
                when {
                    response.isSuccessful -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            note = response.body()
                        )
                    }
                    response.code() == 401 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Access denied. No token provided.")
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid token.")
                        )
                    }
                    response.code() == 404 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Note with given id was not found.")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = DetailsViewState(
                    loading = false,
                    exception = Exception(e.message)
                )
            }
        }
    }

    fun createNote(title: String, description: String): Job {
        return viewModelScope.launch {
            _viewState.value = DetailsViewState(loading = true)

            try {
                val response = repository.createNote(token, title, description)
                when {
                    response.isSuccessful -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            note = response.body()
                        )
                    }
                    response.code() == 401 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Access denied. No token provided.")
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid token.")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = DetailsViewState(
                    loading = false,
                    exception = Exception(e.message)
                )
            }
        }
    }

    fun updateNote(id: String, title: String, description: String): Job {
        return viewModelScope.launch {
            _viewState.value = DetailsViewState(loading = true)

            try {
                val response = repository.updateNote(id, token, title, description)
                when {
                    response.isSuccessful -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            note = response.body()
                        )
                    }
                    response.code() == 401 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Access denied. No token provided.")
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid token.")
                        )
                    }
                    response.code() == 404 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Note with given id was not found..")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = DetailsViewState(
                    loading = false,
                    exception = Exception(e.message)
                )
            }
        }
    }

    fun deleteNote(id: String): Job {
        return viewModelScope.launch {
            _viewState.value = DetailsViewState(loading = true)

            try {
                val response = repository.deleteNote(id, token)
                when {
                    response.isSuccessful -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            note = response.body()
                        )
                    }
                    response.code() == 401 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Access denied. No token provided.")
                        )
                    }
                    response.code() == 400 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Invalid token.")
                        )
                    }
                    response.code() == 404 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Note with given id was not found..")
                        )
                    }
                    response.code() == 500 -> {
                        _viewState.value = DetailsViewState(
                            loading = false,
                            responseCode = response.code(),
                            exception = Exception("Internal server error.")
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _viewState.value = DetailsViewState(
                    loading = false,
                    exception = Exception(e.message)
                )
            }
        }
    }
}