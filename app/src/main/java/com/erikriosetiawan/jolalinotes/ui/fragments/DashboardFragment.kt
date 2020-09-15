package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.erikriosetiawan.jolalinotes.adapters.NoteAdapter
import com.erikriosetiawan.jolalinotes.databinding.FragmentDashboardBinding
import com.erikriosetiawan.jolalinotes.models.Note

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notes = setDummyData()
        setRecyclerView(notes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView(notes: List<Note>) {
        val noteAdapter = context?.let { NoteAdapter(it, notes) }
        noteAdapter?.setOnItemClickListener { note ->
            Toast.makeText(context, note.title, Toast.LENGTH_SHORT).show()
        }

        binding?.rvNotes?.adapter = noteAdapter
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