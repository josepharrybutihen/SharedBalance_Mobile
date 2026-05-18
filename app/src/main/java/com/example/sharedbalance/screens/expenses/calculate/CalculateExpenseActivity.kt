package com.example.sharedbalance.screens.expenses.calculate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.example.sharedbalance.R
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.expenses.breakdown.ExpenseBreakdownActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.settings.SettingsActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculateExpenseActivity :
    Activity() {

    private lateinit var tvGroupName:
            TextView

    private lateinit var dropdownPayer:
            AutoCompleteTextView

    private lateinit var etAmount:
            EditText

    private lateinit var btnCalculate:
            Button

    private lateinit var navHome:
            LinearLayout

    private lateinit var navExpenses:
            LinearLayout

    private lateinit var navHistory:
            LinearLayout

    private lateinit var navAccount:
            LinearLayout

    private val participants =
        arrayOf(
            "john@gmail.com",
            "mark@gmail.com",
            "you@gmail.com"
        )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_calculate
        )

        initViews()

        setupDropdown()

        setupButtons()

        setupBottomNavigation()
    }

    private fun initViews() {

        tvGroupName =
            findViewById(R.id.tvGroupName)

        dropdownPayer =
            findViewById(R.id.dropdownPayer)

        etAmount =
            findViewById(R.id.etAmount)

        btnCalculate =
            findViewById(R.id.btnCalculate)

        navHome =
            findViewById(R.id.navHome)

        navExpenses =
            findViewById(R.id.navExpenses)

        navHistory =
            findViewById(R.id.navHistory)

        navAccount =
            findViewById(R.id.navAccount)

        tvGroupName.text =
            intent.getStringExtra("groupName")
                ?: ""
    }

    private fun setupDropdown() {

        val adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                participants
            )

        dropdownPayer.setAdapter(adapter)
    }

    private fun setupButtons() {

        btnCalculate.setOnClickListener {

            val payer =
                dropdownPayer.text.toString()

            val amountText =
                etAmount.text.toString()

            if (
                payer.isEmpty() ||
                amountText.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Complete all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val amount =
                amountText.toDouble()

            val request =
                CalculateRequest(
                    tvGroupName.text.toString(),
                    payer,
                    amount
                )

            ApiClient.apiService
                .calculateExpense(request)
                .enqueue(

                    object :
                        Callback<Void> {

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {

                            if (response.isSuccessful) {

                                val splitAmount =
                                    amount / participants.size

                                val intent =
                                    Intent(
                                        this@CalculateExpenseActivity,
                                        ExpenseBreakdownActivity::class.java
                                    )

                                intent.putExtra(
                                    "groupName",
                                    tvGroupName.text.toString()
                                )

                                intent.putExtra(
                                    "payer",
                                    payer
                                )

                                intent.putExtra(
                                    "splitAmount",
                                    splitAmount
                                )

                                startActivity(intent)

                            } else {

                                Toast.makeText(
                                    this@CalculateExpenseActivity,
                                    "Calculation failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(
                            call: Call<Void>,
                            t: Throwable
                        ) {

                            Toast.makeText(
                                this@CalculateExpenseActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
        }
    }

    private fun setupBottomNavigation() {

        navHome.setOnClickListener {
            finish()
        }

        navExpenses.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ExpensesActivity::class.java
                )
            )
        }

        navHistory.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
            )
        }

        navAccount.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }
    }
}