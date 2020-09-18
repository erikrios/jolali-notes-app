package com.erikriosetiawan.jolalinotes.ui.viewstate

import com.erikriosetiawan.jolalinotes.models.Note

data class DetailsViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var note: Note? = null,
    var exception: Exception? = null
)