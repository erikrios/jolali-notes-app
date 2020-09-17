package com.erikriosetiawan.jolalinotes.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.databinding.FragmentRegisterBinding
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewmodels.RegisterViewModel
import com.erikriosetiawan.jolalinotes.ui.viewmodels.RegisterViewModelFactory
import com.erikriosetiawan.jolalinotes.ui.viewstate.RegisterViewState
import com.erikriosetiawan.jolalinotes.utils.Constant.PREF_AUTH_TOKEN

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private var isLoading = false
    private var token: String? = null
    private var exception: Exception? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnRegister?.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment) }

        val factory = RegisterViewModelFactory(NotesRepository())
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@RegisterFragment::handleState))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(viewState: RegisterViewState?) {
        viewState?.let { registerViewState ->
            isLoading = registerViewState.loading
            showLoading(isLoading)

            registerViewState.token.apply {
                token = this
                saveToken(token)
            }

            registerViewState.exception.apply {
                exception = this
                showError(exception)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.apply {
                btnRegister.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }

        } else {
            binding?.apply {
                progressBar.visibility = View.GONE
                btnRegister.visibility = View.VISIBLE
            }
        }
    }

    private fun showError(exception: Exception?) {
        if (exception != null) {
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToken(token: String?) {
        val sharedPref = activity?.getSharedPreferences(PREF_AUTH_TOKEN, MODE_PRIVATE)
        sharedPref?.edit { putString(PREF_AUTH_TOKEN, token) }
    }
}