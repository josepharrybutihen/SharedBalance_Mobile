package com.example.sharedbalance.screens.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.account.AccountActivity
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.home.HomeActivity

class ProfileActivity : Activity(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var btnBack: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var navHome: LinearLayout
    private lateinit var navExpenses: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navAccount: LinearLayout
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenter = ProfilePresenter(this, ProfileModel(this))

        initViews()

        val prefs = getSharedPreferences("sharedbalance", MODE_PRIVATE)
        email = prefs.getString("email", "") ?: ""

        presenter.loadUser(email)
        setupListeners()
    }

    private fun initViews() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        tvProfileName = findViewById(R.id.tvProfileName)
        tvProfileEmail = findViewById(R.id.tvProfileEmail)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        btnBack = findViewById(R.id.btnBack)
        progressBar = findViewById(R.id.progressBar)
        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navHistory = findViewById(R.id.navHistory)
        navAccount = findViewById(R.id.navAccount)

        // ✅ email is display only
        etEmail.isEnabled = false
        etEmail.isFocusable = false
        etEmail.isClickable = false
    }

    private fun setupListeners() {
        btnSave.setOnClickListener {
            presenter.saveProfile(
                email,
                etFirstName.text.toString().trim(),
                etLastName.text.toString().trim(),
                etPassword.text.toString().trim(),
                etConfirmPassword.text.toString().trim()
            )
        }
        btnCancel.setOnClickListener { finish() }
        btnBack.setOnClickListener { finish() }

        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        navExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
        }
        navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        navAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showUser(firstName: String, lastName: String, email: String) {
        tvProfileName.text = "$firstName $lastName"
        tvProfileEmail.text = email
        etFirstName.setText(firstName)
        etLastName.setText(lastName)
        etEmail.setText(email)  // ✅ shown but not editable
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}