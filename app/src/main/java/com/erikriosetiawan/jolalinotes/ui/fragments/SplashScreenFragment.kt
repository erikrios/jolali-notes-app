package com.erikriosetiawan.jolalinotes.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.jolalinotes.R
import com.erikriosetiawan.jolalinotes.utils.getToken

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    companion object {
        // Splash screen delay
        private const val SPLASH_DELAY = 2000L
    }

    // Declare the handler and runnable
    private var handler: Handler? = null
    private val runnable: Runnable = Runnable {

        val token: String = getToken().toString()

        // Check the token value
        if (token.isEmpty()) {
            // If token is empty, navigate to Login Fragment
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        } else {
            // If token is present, navigate to Dashboard fragment
            findNavController().navigate(R.id.action_splashScreenFragment_to_dashboardFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Instantiate the handler
        handler = Handler()
        // Start the runnable
        handler?.postDelayed(runnable, SPLASH_DELAY)
    }

    override fun onStop() {
        super.onStop()
        setTransparentStatusBar(activity, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the runnable when activity completely cleared
        handler?.removeCallbacks(runnable)
    }

    /**
     * Create transparent status bar
     */
    private fun setTransparentStatusBar(
        activity: FragmentActivity?,
        isTransparent: Boolean = true
    ) {
        val defaultStatusBarColor = activity?.window?.statusBarColor

        if (isTransparent) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else {
            activity?.window?.apply {

                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                if (defaultStatusBarColor != null)
                    statusBarColor = defaultStatusBarColor
            }
        }
    }
}