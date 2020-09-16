package com.erikriosetiawan.jolalinotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.models.User
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var repository: NotesRepository
    private var token: String? = ""
    private var notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = NotesRepository()

    }

    private fun registerUser(name: String, email: String, password: String) {
        val requestCall =
            repository.registerUser(name, email, password)
        requestCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()

                    // Get headers
                    val headers = response.headers()
                    token = headers["Auth-Token"]
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

    private fun createNote(token: String, title: String, description: String) {
        val requestCall = repository.createNote(token, title, description)
        requestCall.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val note = response.body()
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
                    notes = response.body() as MutableList<Note>
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun getNote(id: String, token: String) {
        val requestCall = repository.getNote(id, token)
        requestCall.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val note = response.body()
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun updateNote(id: String, token: String, title: String, description: String) {
        val requestCall = repository.updateNote(id, token, title, description)
        requestCall.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val note = response.body()
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun deleteNote(id: String, token: String) {
        val requestCall = repository.deleteNote(id, token)
        requestCall.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val note = response.body()
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}