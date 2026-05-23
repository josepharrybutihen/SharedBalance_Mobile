package com.example.sharedbalance.screens.history

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.account.AccountActivity
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.history.adapter.HistoryAdapter
import com.example.sharedbalance.screens.home.HomeActivity

class HistoryActivity :
    Activity(),
    HistoryContract.View {

    private lateinit var presenter:
            HistoryPresenter

    private lateinit var recyclerHistory:
            RecyclerView

    private lateinit var layoutEmpty:
            LinearLayout

    private lateinit var adapter:
            HistoryAdapter

    private lateinit var btnBack:
            ImageView

    private lateinit var navHome:
            LinearLayout

    private lateinit var navExpenses:
            LinearLayout

    private lateinit var navAccount:
            LinearLayout

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_history
        )

        val prefs =
            getSharedPreferences(
                "sharedbalance",
                MODE_PRIVATE
            )

        val email =
            prefs.getString(
                "email",
                ""
            ) ?: ""

        presenter =
            HistoryPresenter(
                this,
                HistoryModel(this),
                email
            )

        recyclerHistory =
            findViewById(
                R.id.recyclerHistory
            )

        layoutEmpty =
            findViewById(
                R.id.layoutEmpty
            )

        btnBack =
            findViewById(
                R.id.btnBack
            )

        navHome =
            findViewById(
                R.id.navHome
            )

        navExpenses =
            findViewById(
                R.id.navExpenses
            )

        navAccount =
            findViewById(
                R.id.navAccount
            )

        recyclerHistory.layoutManager =
            LinearLayoutManager(this)

        setupListeners()

        presenter.loadHistory()
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {

            finish()
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

        navAccount.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AccountActivity::class.java
                )
            )
        }
    }

    override fun showHistory(
        historyList: List<TransactionHistory>
    ) {

        layoutEmpty.visibility =
            View.GONE

        recyclerHistory.visibility =
            View.VISIBLE

        adapter =
            HistoryAdapter(historyList)

        recyclerHistory.adapter =
            adapter
    }

    override fun showEmpty() {

        layoutEmpty.visibility =
            View.VISIBLE

        recyclerHistory.visibility =
            View.GONE
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