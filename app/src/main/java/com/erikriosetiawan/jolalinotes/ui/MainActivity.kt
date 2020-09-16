package com.erikriosetiawan.jolalinotes.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.models.User
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

                    token?.let { createNote(it, "Test Note 1", "Test Description 1") }
                    token?.let { createNote(it, "Test Note 2", "Test Description 2") }
                    GlobalScope.launch {
                        delay(10000L)
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun createNote(token: String, title: String, description: String) {
        val requestCall = repository.createNote(token, title, description)
        requestCall.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val note = response.body()
                    Log.i("API Body Response", note.toString())
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun getNotes(token: String) {
        val requestCall = repository.getNotes(token)
        requestCall.enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful) {
                    val notes = response.body()
                    notes?.forEach {
                        Log.i("API Body Response", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}