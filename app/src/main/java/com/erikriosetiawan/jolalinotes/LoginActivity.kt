package com.erikriosetiawan.jolalinotes

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erikriosetiawan.jolalinotes.databinding.ActivityLoginBinding

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        setTextRegisterColor()
    }

    fun registerAccount(view: View) {
        Toast.makeText(this, "Register Account clicked!", Toast.LENGTH_SHORT).show()
    }

    private fun setTextRegisterColor() {
        val haveNotAccount = getColorSpanned(getString(R.string.have_not_account), "#FFFFFF")
        val register = getColorSpanned(getString(R.string.register), "#D4FF00", true)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            binding.tvHaveNotAccount.text = Html.fromHtml("$haveNotAccount $register", 0)
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