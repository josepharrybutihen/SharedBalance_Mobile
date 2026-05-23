package com.example.sharedbalance.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.home.HomeActivity
import com.example.sharedbalance.screens.register.RegisterActivity

class LoginActivity :
    Activity(),
    LoginContract.View {

    private lateinit var presenter: LoginPresenter

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        presenter =
            LoginPresenter(
                this,
                this,
                LoginModel(this)
            )

        setupListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupListeners() {

        btnLogin.setOnClickListener {
            presenter.login(
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }

        findViewById<TextView>(R.id.tvSignup).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun showLoading() {
        progressBar.visibility = android.view.View.VISIBLE
        btnLogin.isEnabled = false
    }

    override fun hideLoading() {
        progressBar.visibility = android.view.View.GONE
        btnLogin.isEnabled = true
    }

    override fun showSuccess(message: String, userId: Int, token: String) {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}