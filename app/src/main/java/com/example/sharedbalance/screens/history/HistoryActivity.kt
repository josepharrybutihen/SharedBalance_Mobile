package com.example.sharedbalance.screens.history

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.history.adapter.HistoryAdapter

class HistoryActivity : Activity(), HistoryContract.View {

    private lateinit var presenter: HistoryPresenter

    private lateinit var recyclerHistory: RecyclerView
    private lateinit var layoutEmpty: LinearLayout

    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        presenter = HistoryPresenter(this, HistoryModel())

        recyclerHistory = findViewById(R.id.recyclerHistory)
        layoutEmpty = findViewById(R.id.layoutEmpty)

        recyclerHistory.layoutManager =
            LinearLayoutManager(this)

        presenter.loadHistory()
    }

    override fun showHistory(historyList: List<HistoryModel.TransactionHistory>) {

        layoutEmpty.visibility = View.GONE
        recyclerHistory.visibility = View.VISIBLE

        adapter = HistoryAdapter(historyList)

        recyclerHistory.adapter = adapter
    }

    override fun showEmpty() {

        layoutEmpty.visibility = View.VISIBLE
        recyclerHistory.visibility = View.GONE
    }

    override fun showError(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}