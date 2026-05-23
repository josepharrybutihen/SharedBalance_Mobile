package com.example.sharedbalance.screens.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.account.AccountActivity
import com.example.sharedbalance.screens.creategroup.CreateGroupActivity
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.history.HistoryActivity

class HomeActivity : Activity(), HomeContract.View {

    private lateinit var presenter: HomePresenter
    private lateinit var tvToPay: TextView
    private lateinit var tvReceived: TextView
    private lateinit var tvNet: TextView
    private lateinit var btnCreateGroup: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerGroups: RecyclerView
    private lateinit var navHome: LinearLayout
    private lateinit var navExpenses: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navAccount: LinearLayout
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()

        presenter = HomePresenter(this, HomeModel(this))

        val prefs = getSharedPreferences("sharedbalance", MODE_PRIVATE)
        userEmail = prefs.getString("email", null)

        if (userEmail == null) {
            Toast.makeText(this, "Session expired", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupListeners()
        // ✅ do NOT call loadHomeData here — onResume handles it
    }

    override fun onResume() {
        super.onResume()
        // ✅ single load point — fires on first open AND every return
        userEmail?.let { presenter.loadHomeData(it) }
    }

    private fun initViews() {
        tvToPay = findViewById(R.id.tvToPay)
        tvReceived = findViewById(R.id.tvReceived)
        tvNet = findViewById(R.id.tvNet)
        btnCreateGroup = findViewById(R.id.btnCreateGroup)
        progressBar = findViewById(R.id.progressBar)
        recyclerGroups = findViewById(R.id.recyclerGroups)
        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navHistory = findViewById(R.id.navHistory)
        navAccount = findViewById(R.id.navAccount)
        recyclerGroups.layoutManager = LinearLayoutManager(this)
    }

    private fun setupListeners() {
        btnCreateGroup.setOnClickListener {
            startActivity(Intent(this, CreateGroupActivity::class.java))
        }
        navHome.setOnClickListener {
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show()
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

    override fun showLoading() { progressBar.visibility = View.VISIBLE }
    override fun hideLoading() { progressBar.visibility = View.GONE }

    override fun showSummary(toPay: Double, received: Double, net: Double) {
        tvToPay.text = "₱%.2f".format(toPay)
        tvReceived.text = "₱%.2f".format(received)
        tvNet.text = "₱%.2f".format(net)
    }

    override fun showGroups(groups: List<Group>) {
        findViewById<LinearLayout>(R.id.layoutEmpty).visibility = View.GONE
        recyclerGroups.visibility = View.VISIBLE
        recyclerGroups.adapter = HomeGroupsAdapter(groups)
    }

    override fun showEmptyGroups() {
        findViewById<LinearLayout>(R.id.layoutEmpty).visibility = View.VISIBLE
        recyclerGroups.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}