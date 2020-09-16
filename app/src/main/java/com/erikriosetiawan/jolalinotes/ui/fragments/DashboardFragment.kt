package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.adapters.NoteAdapter
import com.erikriosetiawan.jolalinotes.databinding.FragmentDashboardBinding
import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.models.NoteOwner
import com.erikriosetiawan.jolalinotes.utils.setCustomActionBar

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setCustomActionBar(
            binding?.toolbar?.root,
            context?.getString(R.string.my_notes)
        )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notes = setDummyData()
        setRecyclerView(notes)

        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_profile -> findNavController().navigate(R.id.action_dashboardFragment_to_myProfileFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView(notes: List<Note>) {
        val noteAdapter = context?.let { NoteAdapter(it, notes) }
        noteAdapter?.setOnItemClickListener { note ->
            Toast.makeText(context, note.title, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment)
        }

        binding?.rvNotes?.adapter = noteAdapter
    }

    private fun setDummyData(): List<Note> {
        val notes = mutableListOf<Note>()

        for (i in 1..99) {
            val note = Note(
                "$i Lorem Ipsum Dolor $i",
                "$i Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                "2020/09/$i",
                "date",
                "time",
                NoteOwner("", "")
            )
            notes.add(note)
        }

        return notes
    }
}