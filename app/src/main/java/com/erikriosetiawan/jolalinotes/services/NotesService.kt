package com.erikriosetiawan.jolalinotes.services

import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.models.User
import retrofit2.Response
import retrofit2.http.*

interface NotesService {

    /**
     * POST Request to register a user
     */
    @POST("api/users")
    @FormUrlEncoded
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<User>

    /**
     * GET Request to get the user details
     */
    @GET("api/users/me")
    fun getUserDetails(
        @Header("Auth-Token")
        token: String
    ): Response<User>

    /**
     * Check the user login
     */
    @POST("api/auth")
    @FormUrlEncoded
    suspend fun authenticateUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<String>

    /**
     * Create a note
     */
    @POST("api/notes")
    @FormUrlEncoded
    fun createNote(
        @Header("Auth-Token") token: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Response<Note>

    /**
     * Get all notes
     */
    @GET("api/notes")
    fun getNotes(
        @Header("Auth-Token") token: String
    ): Response<List<Note>>

    /**
     * Get a note by id
     */
    @GET("api/notes/{id}")
    fun getNote(
        @Path("id") id: String,
        @Header("Auth-Token") token: String
    ): Response<Note>

    /**
     * Update a note
     */
    @PUT("api/notes/{id}")
    @FormUrlEncoded
    fun updateNote(
        @Path("id") id: String,
        @Header("Auth-Token") token: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Response<Note>

    /**
     * Delete a note
     */
    @DELETE("api/notes/{id}")
    fun deleteNote(
        @Path("id") id: String,
        @Header("Auth-Token") token: String
    ): Response<Note>
}