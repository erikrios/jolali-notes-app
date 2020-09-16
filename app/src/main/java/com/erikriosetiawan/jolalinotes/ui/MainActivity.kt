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

        registerUser("Erik Android", "erikandroid@gmail.com", "Erik1997")
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

                    authenticateUser("erikandroid@gmail.com", "Erik1997")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun authenticateUser(email: String, password: String) {
        val requestCall = repository.authenticateUser(email, password)
        requestCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val headers = response.headers()
                    token = headers["Auth-Token"]
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}