package com.erikriosetiawan.jolalinotes.repository

import com.erikriosetiawan.jolalinotes.services.NotesService
import com.erikriosetiawan.jolalinotes.services.ServiceBuilder

class NotesRepository {

    private val notesService = ServiceBuilder.buildService(NotesService::class.java)

    fun registerUser(name: String, email: String, password: String) =
        notesService.registerUser(name, email, password)

    fun getUserDetails(token: String) = notesService.getUserDetails(token)

    fun authenticateUser(email: String, password: String) =
        notesService.authenticateUser(email, password)
}