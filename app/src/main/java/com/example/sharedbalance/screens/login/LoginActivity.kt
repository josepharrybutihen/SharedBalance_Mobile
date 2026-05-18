package com.example.sharedbalance.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.home.HomeActivity
import com.example.sharedbalance.screens.register.RegisterActivity

class LoginActivity :
    Activity(),
    LoginContract.View {

    private lateinit var presenter:
            LoginPresenter

    private lateinit var etEmail:
            EditText

    private lateinit var etPassword:
            EditText

    private lateinit var btnLogin:
            Button

    private lateinit var progressBar:
            ProgressBar

    private lateinit var navHome:
            LinearLayout

    private lateinit var navExpenses:
            LinearLayout

    private lateinit var navHistory:
            LinearLayout

    private lateinit var navAccount:
            LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_login
        )

        presenter =
            LoginPresenter(
                this,
                LoginModel()
            )

        initViews()

        setupListeners()
    }

    private fun initViews() {

        etEmail =
            findViewById(R.id.etEmail)

        etPassword =
            findViewById(R.id.etPassword)

        btnLogin =
            findViewById(R.id.btnLogin)

        progressBar =
            findViewById(R.id.progressBar)

        navHome =
            findViewById(R.id.navHome)

        navExpenses =
            findViewById(R.id.navExpenses)

        navHistory =
            findViewById(R.id.navHistory)

        navAccount =
            findViewById(R.id.navAccount)
    }

    private fun setupListeners() {

        btnLogin.setOnClickListener {

            presenter.login(
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }

        findViewById<TextView>(
            R.id.tvSignup
        ).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        navHome.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
        }

        navExpenses.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ExpensesActivity::class.java
                )
            )
        }

        navHistory.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
            )
        }

        navAccount.setOnClickListener {

            Toast.makeText(
                this,
                "Already on Login",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun showLoading() {

        progressBar.visibility =
            View.VISIBLE

        btnLogin.isEnabled = false
    }

    override fun hideLoading() {

        progressBar.visibility =
            View.GONE

        btnLogin.isEnabled = true
    }

    override fun showSuccess(
        message: String,
        userId: Int
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()

        val prefs =
            getSharedPreferences(
                "sharedbalance",
                MODE_PRIVATE
            )

        prefs.edit()
            .putInt(
                "userId",
                userId
            )
            .apply()

        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            )
        )

        finish()
    }

    override fun showError(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}