package com.erikriosetiawan.jolalinotes.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.models.User
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var repository: NotesRepository
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = NotesRepository()

        registerUser("User Test 2", "usertest2@gmail.com", "test1234")
    }

    private fun registerUser(name: String, email: String, password: String) {
        val requestCall =
            repository.registerUser(name, email, password)
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.i("API Body Response", user.toString())

                    // Get headers
                    val headers = response.headers()
                    token = headers["Auth-Token"]

                    token?.let { getUserDetails(it) }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun getUserDetails(token: String) {
        val requestCall = repository.getUserDetails(token)
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.i("API Body Response", user.toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}