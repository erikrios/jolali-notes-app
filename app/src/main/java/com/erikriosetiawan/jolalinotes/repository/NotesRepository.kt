package com.erikriosetiawan.jolalinotes.repository

import com.erikriosetiawan.jolalinotes.services.NotesService
import com.erikriosetiawan.jolalinotes.services.ServiceBuilder

class NotesRepository {

    private val notesService = ServiceBuilder.buildService(NotesService::class.java)

    suspend fun registerUser(name: String, email: String, password: String) =
        notesService.registerUser(name, email, password)

    suspend fun getUserDetails(token: String) = notesService.getUserDetails(token)

    suspend fun authenticateUser(email: String, password: String) =
        notesService.authenticateUser(email, password)

    suspend fun createNote(token: String, title: String, description: String) =
        notesService.createNote(token, title, description)

    suspend fun getNotes(token: String) = notesService.getNotes(token)

    suspend fun getNote(id: String, token: String) = notesService.getNote(id, token)

    suspend fun updateNote(id: String, token: String, title: String, description: String) =
        notesService.updateNote(id, token, title, description)

    suspend fun deleteNote(id: String, token: String) = notesService.deleteNote(id, token)
}