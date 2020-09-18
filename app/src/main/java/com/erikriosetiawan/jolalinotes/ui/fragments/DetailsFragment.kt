package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.databinding.FragmentDetailsBinding
import com.erikriosetiawan.jolalinotes.models.Note
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewmodels.DetailsViewModel
import com.erikriosetiawan.jolalinotes.ui.viewmodels.DetailsViewModelFactory
import com.erikriosetiawan.jolalinotes.ui.viewstate.DetailsViewState
import com.erikriosetiawan.jolalinotes.utils.getToken
import com.erikriosetiawan.jolalinotes.utils.setCustomActionBar

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: DetailsViewModel

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setCustomActionBar(
            binding?.toolbar?.root,
            context?.getString(R.string.new_note)
        )

        val token = getToken().toString()
        val factory = DetailsViewModelFactory(NotesRepository(), token)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@DetailsFragment::handleState))
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.notesId?.let {
            viewModel.getNote(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (args.notesId == null) {
            val deleteItem = menu.findItem(R.id.item_delete)
            deleteItem.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = args.notesId

        when (item.itemId) {
            R.id.item_delete -> {
                id?.let {
                    viewModel.deleteNote(it).invokeOnCompletion {
                        findNavController().popBackStack()
                        Toast.makeText(
                            context, getString(R.string.note_deleted), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            R.id.item_save -> {
                val title = binding?.etTitle?.text.toString()
                val description = binding?.etTitle?.text.toString()

                if (id != null) {
                    // Update Note
                    viewModel.updateNote(id, title, description).invokeOnCompletion {
                        findNavController().popBackStack()
                        Toast.makeText(
                            context, getString(R.string.note_updated), Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Create Note
                    viewModel.createNote(title, description)
                    findNavController().popBackStack()
                    Toast.makeText(context, getString(R.string.note_saved), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleState(viewState: DetailsViewState?) {
        viewState?.let { detailsViewState ->
            showLoading(detailsViewState.loading)
            showError(detailsViewState.exception)
            args.notesId?.let {
                getData(detailsViewState.note)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(exception: Exception?) {
        exception?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData(note: Note?) {
        note?.let {
            binding?.apply {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
            }
        }
    }
}