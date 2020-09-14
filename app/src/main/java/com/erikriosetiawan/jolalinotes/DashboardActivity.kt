package com.erikriosetiawan.jolalinotes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.adapters.NoteAdapter
import com.erikriosetiawan.jolalinotes.databinding.ActivityDashboardBinding
import com.erikriosetiawan.jolalinotes.models.Note

private lateinit var binding: ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notes = setDummyData()
        setRecyclerView(notes)
    }

    private fun setRecyclerView(notes: List<Note>) {
        val noteAdapter = NoteAdapter(this, notes)
        noteAdapter.setOnItemClickListener { note ->
            Toast.makeText(this, note.title, Toast.LENGTH_SHORT).show()
        }

        binding.rvNotes.adapter = noteAdapter
    }

    private fun setDummyData(): List<Note> {
        val notes = mutableListOf<Note>()

        for (i in 1..99) {
            val note = Note(
                "$i Lorem Ipsum Dolor $i",
                "$i Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                "2020/09/$i"
            )
            notes.add(note)
        }

        return notes
    }
}