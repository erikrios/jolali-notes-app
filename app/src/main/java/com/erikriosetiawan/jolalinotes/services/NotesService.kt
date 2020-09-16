package com.erikriosetiawan.jolalinotes.services

import com.erikriosetiawan.jolalinotes.models.User
import retrofit2.Call
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
    ): Call<User>

    /**
     * GET Request to get the user details
     */
    @GET("api/users/me")
    fun getUserDetails(
        @Header("Auth-Token")
        token: String
    ): Call<User>
}