package com.example.sharedbalance.screens.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.login.LoginActivity

class AccountActivity : Activity(),
    AccountContract.View {

    private lateinit var presenter: AccountPresenter

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account)

        presenter = AccountPresenter(
            this,
            AccountModel()
        )

        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        btnLogout = findViewById(R.id.btnLogout)

        presenter.loadUser()

        btnLogout.setOnClickListener {
            presenter.logout()
        }
    }

    override fun showUser(
        name: String,
        email: String
    ) {

        tvName.text = name
        tvEmail.text = email
    }

    override fun logoutSuccess() {

        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )

        finish()
    }
}