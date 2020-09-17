package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val factory = LoginViewModelFactory(NotesRepository())
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@LoginFragment::handleState))
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextRegisterColor()
        binding?.btnLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
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
        viewState?.let {

        }
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