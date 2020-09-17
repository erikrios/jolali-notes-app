package com.erikriosetiawan.jolalinotes.ui.viewstate

import com.erikriosetiawan.jolalinotes.models.User

data class RegisterViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var token: String? = null,
    var exception: Exception? = null,
    var user: User? = null
)