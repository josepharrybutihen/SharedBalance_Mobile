package com.example.sharedbalance.screens.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.home.HomeActivity
import com.example.sharedbalance.screens.login.LoginActivity
import com.example.sharedbalance.screens.profile.editprofile.EditProfileActivity
import com.example.sharedbalance.screens.settings.SettingsActivity

class AccountActivity : Activity(), AccountContract.View {

    private lateinit var presenter: AccountPresenter
    private lateinit var imgProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnEditProfile: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var btnBack: ImageView
    private lateinit var navHome: LinearLayout
    private lateinit var navExpenses: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navAccount: LinearLayout
    private lateinit var layoutPreferences: LinearLayout
    private lateinit var layoutAccountInfo: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        initViews()
        presenter = AccountPresenter(this, AccountModel(this))
        setupListeners()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        showCachedUser()        // ✅ instant display from cache
        presenter.loadUser()    // ✅ always refresh on resume (catches edits)
    }

    private fun initViews() {
        imgProfile = findViewById(R.id.imgProfile)
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        btnLogout = findViewById(R.id.btnLogout)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        progressBar = findViewById(R.id.progressBar)
        btnBack = findViewById(R.id.btnBack)
        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navHistory = findViewById(R.id.navHistory)
        navAccount = findViewById(R.id.navAccount)
        layoutPreferences = findViewById(R.id.layoutPreferences)
        layoutAccountInfo = findViewById(R.id.layoutAccountInfo)
    }

    private fun showCachedUser() {
        val prefs = getSharedPreferences("sharedbalance", MODE_PRIVATE)
        val name = prefs.getString("name", "") ?: ""
        val email = prefs.getString("email", "") ?: ""
        val profileImage = prefs.getString("profileImage", null)

        tvName.text = name.ifEmpty { "Loading..." }
        tvEmail.text = email

        Glide.with(this)
            .load(profileImage)
            .placeholder(R.drawable.ic_account)
            .into(imgProfile)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
        btnLogout.setOnClickListener { presenter.logout() }

        btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        layoutPreferences.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        layoutAccountInfo.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }

    private fun setupBottomNavigation() {
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        navExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
        }
        navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        navAccount.setOnClickListener { }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showUser(user: UserProfile) {
        tvName.text = "${user.firstName} ${user.lastName}"
        tvEmail.text = user.email

        Glide.with(this)
            .load(user.profileImage)
            .placeholder(R.drawable.ic_account)
            .into(imgProfile)

        // ✅ persist to cache so Settings and other screens stay in sync
        getSharedPreferences("sharedbalance", MODE_PRIVATE).edit()
            .putString("name", "${user.firstName} ${user.lastName}")
            .putString("firstName", user.firstName)
            .putString("lastName", user.lastName)
            .putString("profileImage", user.profileImage)
            .apply()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun logoutSuccess() {
        getSharedPreferences("sharedbalance", MODE_PRIVATE).edit().clear().apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}