package com.erikriosetiawan.jolalinotes.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.databinding.FragmentMyProfileBinding
import com.erikriosetiawan.jolalinotes.models.User
import com.erikriosetiawan.jolalinotes.repository.NotesRepository
import com.erikriosetiawan.jolalinotes.ui.viewmodels.MyProfileViewModel
import com.erikriosetiawan.jolalinotes.ui.viewmodels.MyProfileViewModelFactory
import com.erikriosetiawan.jolalinotes.ui.viewstate.MyProfileViewState
import com.erikriosetiawan.jolalinotes.utils.getToken
import com.erikriosetiawan.jolalinotes.utils.setCustomActionBar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setCustomActionBar(
            binding?.toolbar?.root,
            context?.getString(R.string.my_profile)
        )

        val factory = MyProfileViewModelFactory(NotesRepository(), getToken().toString())
        viewModel = ViewModelProvider(this, factory).get(MyProfileViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@MyProfileFragment::handleState))
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(viewState: MyProfileViewState?) {
        viewState?.let { myProfileViewState ->
            showLoading(myProfileViewState.loading)
            showError(myProfileViewState.exception)
            showData(myProfileViewState.user)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showError(exception: Exception?) {
        exception?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showData(user: User?) {
        user?.let {
            binding?.apply {
                tvFirstCharacter.text = it.name[0].toString()
                tvFullName.text = it.name
                tvEmailAddress.text = it.email
                tvMemberSinceDate.text = dateFormat(it.dateRegistered).toString()
                tvLastLoginDate.text = dateFormat(it.lastLogin)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateFormat(isoFormat: String): String? {
        val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(isoFormat)
        return date?.let {
            SimpleDateFormat("yyyy/MM/dd").format(date)
        }
    }
}