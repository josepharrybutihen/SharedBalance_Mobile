package com.example.sharedbalance.screens.expenses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.screens.account.AccountActivity
import com.example.sharedbalance.screens.expenses.adapter.GroupAdapter
import com.example.sharedbalance.screens.expenses.details.GroupDetailsActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.home.HomeActivity

class ExpensesActivity : Activity(), ExpensesContract.View {

    private lateinit var tvToPay: TextView
    private lateinit var tvReceived: TextView
    private lateinit var tvNet: TextView

    private lateinit var recycler: RecyclerView
    private lateinit var presenter: ExpensesPresenter

    private lateinit var navHome: LinearLayout
    private lateinit var navExpenses: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navAccount: LinearLayout

    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        initViews()
        setupListeners()

        recycler.layoutManager = LinearLayoutManager(this)

        presenter = ExpensesPresenter(
            this,
            ExpensesModel(ApiClient.create(this))
        )

        userEmail = getSharedPreferences("sharedbalance", MODE_PRIVATE)
            .getString("email", null)
    }

    override fun onResume() {
        super.onResume()
        // ✅ always reload on resume so summary stays current after mark paid
        userEmail?.let { presenter.loadData(it) }
    }

    private fun initViews() {
        tvToPay = findViewById(R.id.tvToPay)
        tvReceived = findViewById(R.id.tvReceived)
        tvNet = findViewById(R.id.tvNet)
        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navHistory = findViewById(R.id.navHistory)
        navAccount = findViewById(R.id.navAccount)
        recycler = findViewById(R.id.recyclerGroups)
    }

    private fun setupListeners() {
        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        navExpenses.setOnClickListener {
            Toast.makeText(this, "Already on Expenses", Toast.LENGTH_SHORT).show()
        }
        navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        navAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }

    override fun showSummary(toPay: Double, toReceive: Double, netBalance: Double) {
        tvToPay.text = "₱%.2f".format(toPay)
        tvReceived.text = "₱%.2f".format(toReceive)
        tvNet.text = "₱%.2f".format(netBalance)
    }

    override fun showGroups(groups: List<Group>) {
        recycler.adapter = GroupAdapter(groups) { group ->
            val intent = Intent(this, GroupDetailsActivity::class.java)
            intent.putExtra("groupId", group.id)
            startActivity(intent)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}