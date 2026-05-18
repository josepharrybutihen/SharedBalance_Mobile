package com.example.sharedbalance.screens.landing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.login.LoginActivity
import com.example.sharedbalance.screens.register.RegisterActivity

class LandingActivity : Activity(), LandingContract.View {

    private lateinit var presenter: LandingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_landing)

        presenter = LandingPresenter(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            presenter.onLoginClicked()
        }

        btnRegister.setOnClickListener {
            presenter.onRegisterClicked()
        }
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}