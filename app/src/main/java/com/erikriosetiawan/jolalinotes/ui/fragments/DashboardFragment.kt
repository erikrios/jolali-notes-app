package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.adapters.NoteAdapter
import com.erikriosetiawan.jolalinotes.databinding.FragmentDashboardBinding
import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewmodels.DashboardViewModel
import com.erikriosetiawan.jolalinotes.ui.viewmodels.DashboardViewModelFactory
import com.erikriosetiawan.jolalinotes.ui.viewstate.DashboardViewState
import com.erikriosetiawan.jolalinotes.utils.getToken
import com.erikriosetiawan.jolalinotes.utils.setCustomActionBar
import com.startapp.sdk.adsbase.StartAppAd

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private lateinit var token: String
    private lateinit var viewModel: DashboardViewModel

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

        token = getToken().toString()

        val factory = DashboardViewModelFactory(NotesRepository(), token)
        viewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@DashboardFragment::handleState))
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes(token)
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
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(note._id)
            findNavController().navigate(action)
            StartAppAd.showAd(context)
        }

        binding?.rvNotes?.adapter = noteAdapter
    }

    private fun handleState(viewState: DashboardViewState?) {
        viewState?.let { dashboardViewState ->
            showLoading(dashboardViewState.loading)

            dashboardViewState.notes.apply {
                this?.let { setRecyclerView(it) }
            }

            dashboardViewState.exception.apply {
                showError(this)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(exception: Exception?) {
        exception?.let {
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }
    }
}