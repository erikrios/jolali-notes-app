package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextRegisterColor()
        binding?.btnLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun registerAccount(view: View) {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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