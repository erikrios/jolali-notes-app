package com.erikriosetiawan.jolalinotes.ui.viewstate

import com.erikriosetiawan.jolalinotes.models.Note

data class DashboardViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var notes: List<Note>? = null,
    var exception: Exception? = null
)