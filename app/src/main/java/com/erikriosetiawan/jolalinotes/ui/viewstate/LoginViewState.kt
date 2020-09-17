package com.erikriosetiawan.jolalinotes.ui.viewstate

data class LoginViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var token: String? = null
)