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

    fun createNote(token: String, title: String, description: String) =
        notesService.createNote(token, title, description)

    fun getNotes(token: String) = notesService.getNotes(token)

    fun getNote(id: String, token: String) = notesService.getNote(id, token)

    fun updateNote(id: String, token: String, title: String, description: String) =
        notesService.updateNote(id, token, title, description)

    fun deleteNote(id: String, token: String) = notesService.deleteNote(id, token)
}