package com.erikriosetiawan.jolalinotes.ui.viewstate

import com.erikriosetiawan.jolalinotes.models.User

data class MyProfileViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var user: User? = null,
    var exception: Exception? = null
)