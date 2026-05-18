package com.example.sharedbalance.screens.expenses.breakdown

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

import com.example.sharedbalance.R

class ExpenseBreakdownActivity : Activity() {

    private lateinit var tvGroup: TextView
    private lateinit var tvPayer: TextView
    private lateinit var tvAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_expense_breakdown
        )

        tvGroup =
            findViewById(R.id.tvGroup)

        tvPayer =
            findViewById(R.id.tvPayer)

        tvAmount =
            findViewById(R.id.tvAmount)

        loadData()
    }

    private fun loadData() {

        val group =
            intent.getStringExtra("groupName")
                ?: ""

        val payer =
            intent.getStringExtra("payer")
                ?: ""

        val amount =
            intent.getDoubleExtra(
                "amount",
                0.0
            )

        val splitAmount =
            intent.getDoubleExtra(
                "splitAmount",
                0.0
            )

        tvGroup.text =
            "Group: $group"

        tvPayer.text =
            "Paid By: $payer"

        tvAmount.text =
            "Each Member Pays: ₱$splitAmount"
    }
}