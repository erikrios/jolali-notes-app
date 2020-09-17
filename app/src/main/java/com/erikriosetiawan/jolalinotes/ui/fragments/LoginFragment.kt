package com.erikriosetiawan.jolalinotes.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Html
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
import com.erikriosetiawan.jolalinotes.databinding.FragmentLoginBinding
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewmodels.LoginViewModel
import com.erikriosetiawan.jolalinotes.ui.viewmodels.LoginViewModelFactory
import com.erikriosetiawan.jolalinotes.ui.viewstate.LoginViewState
import com.erikriosetiawan.jolalinotes.utils.Constant.PREF_AUTH_TOKEN
import com.erikriosetiawan.jolalinotes.utils.Constant.PREF_FILE_KEY

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: LoginViewModel
    private var isLoading = false
    private var token: String? = null
    private var exception: Exception? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val factory = LoginViewModelFactory(NotesRepository())
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@LoginFragment::handleState))
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextRegisterColor()
        binding?.btnLogin?.setOnClickListener {
            val email = binding?.etEmailAddress?.text.toString()
            val password = binding?.etPassword?.text.toString()

            val isValid = validate(email, password)

            if (isValid) {
                viewModel.authenticateUser(email, password).invokeOnCompletion {
                    if (!isLoading && (token != null) && (exception == null))
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }
        }

        binding?.tvHaveNotAccount?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(viewState: LoginViewState?) {
        viewState?.let { loginViewState ->
            isLoading = loginViewState.loading
            showLoading(isLoading)

            loginViewState.token.apply {
                token = this
                saveToken(token)
            }

            loginViewState.exception.apply {
                exception = this
                showError(exception)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.apply {
                btnLogin.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        } else {
            binding?.apply {
                btnLogin.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showError(exception: Exception?) {
        if (exception != null)
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
    }

    private fun saveToken(token: String?) {
        val sharedPref = activity?.getSharedPreferences(PREF_FILE_KEY, MODE_PRIVATE)
        sharedPref?.edit { putString(PREF_AUTH_TOKEN, token) }
    }

    private fun validate(email: String, password: String): Boolean {
        var isEmpty = false
        var isInvalid = false

        if (email.isEmpty()) {
            binding?.etEmailAddress?.error = getString(R.string.empty_email_error)
            isEmpty = true
        } else if (email.length < 5 || email.length > 255) {
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

        if (!isEmpty && !isInvalid) {
            return true
        }

        return false
    }

    private fun setTextRegisterColor() {
        val haveNotAccount = getColorSpanned(getString(R.string.have_not_account), "#FFFFFF")
        val register = getColorSpanned(getString(R.string.register), "#D4FF00", true)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            binding?.tvHaveNotAccount?.text = Html.fromHtml("$haveNotAccount $register", 0)
        }
    }

    private fun getColorSpanned(text: String, color: String, isUnderline: Boolean = false): String {
        return if (!isUnderline) {
            "<font color=\"$color\">$text</font>"
        } else {
            "<font color=\"$color\"><u>$text</u></font>"
        }
    }
}