package com.example.sharedbalance.screens.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.login.LoginActivity

class RegisterActivity :
    Activity(),
    RegisterContract.View {

    private lateinit var presenter:
            RegisterPresenter

    private lateinit var etFirstName:
            EditText

    private lateinit var etLastName:
            EditText

    private lateinit var etEmail:
            EditText

    private lateinit var etPassword:
            EditText

    private lateinit var etConfirmPassword:
            EditText

    private lateinit var btnRegister:
            Button

    private lateinit var progressBar:
            ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_register
        )

        presenter = RegisterPresenter(
            this,
            RegisterModel()
        )

        etFirstName =
            findViewById(R.id.etFirstName)

        etLastName =
            findViewById(R.id.etLastName)

        etEmail =
            findViewById(R.id.etEmail)

        etPassword =
            findViewById(R.id.etPassword)

        etConfirmPassword =
            findViewById(R.id.etConfirmPassword)

        btnRegister =
            findViewById(R.id.btnRegister)

        progressBar =
            findViewById(R.id.progressBar)

        findViewById<TextView>(
            R.id.tvLoginLink
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }

        btnRegister.setOnClickListener {

            presenter.register(
                etFirstName.text.toString().trim(),
                etLastName.text.toString().trim(),
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim(),
                etConfirmPassword.text.toString().trim()
            )
        }
    }

    override fun showLoading() {

        progressBar.visibility =
            View.VISIBLE
    }

    override fun hideLoading() {

        progressBar.visibility =
            View.GONE
    }

    override fun showSuccess() {

        Toast.makeText(
            this,
            "Registration successful",
            Toast.LENGTH_SHORT
        ).show()

        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )

        finish()
    }

    override fun showError(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}