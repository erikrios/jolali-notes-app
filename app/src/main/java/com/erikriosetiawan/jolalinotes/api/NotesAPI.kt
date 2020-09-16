package com.erikriosetiawan.jolalinotes.api

import com.erikriosetiawan.jolalinotes.models.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

interface NotesAPI {

    /**
     * POST Request to register a user
     */
    @POST("api/users")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>
}