package com.erikriosetiawan.jolalinotes.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Patterns
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
import com.erikriosetiawan.jolalinotes.utils.Constant.PREF_FILE_KEY

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: RegisterViewModel
    private var isLoading = false
    private var token: String? = null
    private var exception: Exception? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val factory = RegisterViewModelFactory(NotesRepository())
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@RegisterFragment::handleState))
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnRegister?.setOnClickListener {
            val name = binding?.etFullName?.text.toString()
            val email = binding?.etEmailAddress?.text.toString()
            val password = binding?.etPassword?.text.toString()
            val rePassword = binding?.etRePassword?.text.toString()

            val isValid = validate(name, email, password, rePassword)

            if (isValid) {
                viewModel.registerUser(name, email, password).invokeOnCompletion {
                    if (!isLoading && (token != null) && (exception == null)) {
                        findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
                    }
                }
            }
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
        val sharedPref = activity?.getSharedPreferences(PREF_FILE_KEY, MODE_PRIVATE)
        sharedPref?.edit { putString(PREF_AUTH_TOKEN, token) }
    }

    private fun validate(
        name: String,
        email: String,
        password: String,
        rePassword: String
    ): Boolean {
        var isEmpty = false
        var isInvalid = false

        if (name.isEmpty()) {
            binding?.etFullName?.error = getString(R.string.empty_name_error)
            isEmpty = true
        } else if (name.length < 5 || name.length > 50) {
            binding?.etFullName?.error = getString(R.string.invalid_name_error)
            isInvalid = true
        }

        if (email.isEmpty()) {
            binding?.etEmailAddress?.error = getString(R.string.empty_email_error)
            isEmpty = true
        } else if (email.length < 5 || email.length > 255) {
            binding?.etEmailAddress?.error = getString(R.string.invalid_email_error)
            isInvalid = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding?.etEmailAddress?.error = getString(R.string.invalid_email_error)
            isInvalid = true
        }

        if (password.isEmpty()) {
            binding?.etPassword?.error = getString(R.string.empty_password_error)
            isEmpty = true
        } else if (password.length < 5 || password.length > 1024) {
            binding?.etPassword?.error = getString(R.string.invalid_password_error)
            isInvalid = true
        }

        if (rePassword.isEmpty()) {
            binding?.etRePassword?.error = getString(R.string.empty_password_error)
            isEmpty = true
        } else if (rePassword.length < 5 || rePassword.length > 1024) {
            binding?.etRePassword?.error = getString(R.string.invalid_password_error)
            isInvalid = true
        }

        if (password != rePassword) {
            binding?.etPassword?.error = getString(R.string.password_not_match_error)
            binding?.etRePassword?.error = getString(R.string.password_not_match_error)
            isInvalid = true
        }

        if (!isEmpty && !isInvalid) {
            return true
        }

        return false
    }
}