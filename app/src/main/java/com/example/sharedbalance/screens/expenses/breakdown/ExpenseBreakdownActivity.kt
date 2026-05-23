package com.example.sharedbalance.screens.expenses.breakdown

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.sharedbalance.R
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.screens.expenses.Expenses
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import com.example.sharedbalance.screens.expenses.Payment
import com.example.sharedbalance.screens.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseBreakdownActivity : Activity() {

    private lateinit var tvGroupName: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvPayer: TextView
    private lateinit var tvSplitAmount: TextView
    private lateinit var breakdownContainer: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var btnBackToExpenses: Button

    private var groupId: Long = -1
    private var currentUser: String = ""
    private var isCreator: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_breakdown)

        groupId = intent.getLongExtra("groupId", -1)

        val prefs = getSharedPreferences("sharedbalance", MODE_PRIVATE)
        currentUser = prefs.getString("email", "") ?: ""

        tvGroupName = findViewById(R.id.tvGroup)
        tvTotalAmount = findViewById(R.id.tvAmount)
        tvPayer = findViewById(R.id.tvPayer)
        tvSplitAmount = findViewById(R.id.tvSplitAmount)
        breakdownContainer = findViewById(R.id.breakdownContainer)
        progressBar = findViewById(R.id.progressBar)
        btnBackToExpenses = findViewById(R.id.btnBackToExpenses)

        tvGroupName.text = "Group: ${intent.getStringExtra("groupName") ?: ""}"

        // ✅ back to expenses
        btnBackToExpenses.setOnClickListener {
            startActivity(Intent(this, ExpensesActivity::class.java))
            finish()
        }

        loadGroupInfo()
        loadBreakdown()
    }

    private fun loadGroupInfo() {
        ApiClient.create(this)
            .getGroupDetails(groupId)
            .enqueue(object : Callback<com.example.sharedbalance.data.api.ApiResponse<com.example.sharedbalance.screens.expenses.Group>> {
                override fun onResponse(
                    call: Call<com.example.sharedbalance.data.api.ApiResponse<com.example.sharedbalance.screens.expenses.Group>>,
                    response: Response<com.example.sharedbalance.data.api.ApiResponse<com.example.sharedbalance.screens.expenses.Group>>
                ) {
                    val group = response.body()?.payload
                    if (group != null) {
                        // ✅ check if current user is creator
                        isCreator = group.creatorEmail == currentUser
                        loadPayments() // load payments after we know isCreator
                    }
                }
                override fun onFailure(
                    call: Call<com.example.sharedbalance.data.api.ApiResponse<com.example.sharedbalance.screens.expenses.Group>>,
                    t: Throwable
                ) {}
            })
    }

    private fun loadBreakdown() {
        progressBar.visibility = View.VISIBLE

        ApiClient.create(this)
            .getExpensesByGroup(groupId, currentUser)
            .enqueue(object : Callback<List<Expenses>> {

                override fun onResponse(
                    call: Call<List<Expenses>>,
                    response: Response<List<Expenses>>
                ) {
                    progressBar.visibility = View.GONE
                    val expenses = response.body()

                    if (response.isSuccessful && !expenses.isNullOrEmpty()) {
                        val expense = expenses[0]
                        tvPayer.text = "Paid By: ${expense.payer}"
                        tvTotalAmount.text = "Total: ₱${"%.2f".format(expense.totalAmount)}"
                        val split = expense.totalAmount / expense.participantCount
                        tvSplitAmount.text = "Each pays: ₱${"%.2f".format(split)}"
                    } else {
                        Toast.makeText(
                            this@ExpenseBreakdownActivity,
                            "No expense data found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Expenses>>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@ExpenseBreakdownActivity, t.message ?: "Network error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun loadPayments() {
        ApiClient.create(this)
            .getPaymentsByGroup(groupId)
            .enqueue(object : Callback<List<Payment>> {

                override fun onResponse(
                    call: Call<List<Payment>>,
                    response: Response<List<Payment>>
                ) {
                    val payments = response.body()
                    if (response.isSuccessful && !payments.isNullOrEmpty()) {
                        renderPayments(payments)
                    }
                }

                override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                    Toast.makeText(this@ExpenseBreakdownActivity, t.message ?: "Network error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun renderPayments(payments: List<Payment>) {
        breakdownContainer.removeAllViews()

        for (payment in payments) {
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL
            row.setPadding(0, 12, 0, 12)

            // person info
            val tvInfo = TextView(this)
            tvInfo.text = "${payment.payer} → ${payment.receiver}"
            tvInfo.textSize = 13f
            tvInfo.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )

            // amount
            val tvAmt = TextView(this)
            tvAmt.text = "₱${"%.2f".format(payment.amount)}"
            tvAmt.textSize = 13f
            tvAmt.setTextColor(Color.parseColor("#E91E1E"))

            // status or mark paid button
            val statusView: View

            if (payment.paid) {
                val tvPaid = TextView(this)
                tvPaid.text = "✓ Paid"
                tvPaid.textSize = 12f
                tvPaid.setTextColor(Color.parseColor("#159A78"))
                statusView = tvPaid
            } else if (isCreator) {
                // ✅ only creator sees mark as paid button
                val btnPaid = Button(this)
                btnPaid.text = "Mark Paid"
                btnPaid.textSize = 11f
                btnPaid.setPadding(12, 4, 12, 4)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                btnPaid.layoutParams = params
                btnPaid.setBackgroundColor(Color.parseColor("#159A78"))
                btnPaid.setTextColor(Color.WHITE)

                btnPaid.setOnClickListener {
                    markAsPaid(payment.id, btnPaid, row)
                }
                statusView = btnPaid
            } else {
                val tvPending = TextView(this)
                tvPending.text = "Pending"
                tvPending.textSize = 12f
                tvPending.setTextColor(Color.parseColor("#999999"))
                statusView = tvPending
            }

            row.addView(tvInfo)
            row.addView(tvAmt)
            row.addView(statusView)
            breakdownContainer.addView(row)

            // divider
            val divider = View(this)
            divider.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            )
            divider.setBackgroundColor(Color.parseColor("#EEEEEE"))
            breakdownContainer.addView(divider)
        }
    }

    private fun markAsPaid(paymentId: Long, btn: Button, row: LinearLayout) {
        btn.isEnabled = false
        btn.text = "..."

        ApiClient.create(this)
            .markPaymentAsPaid(paymentId, currentUser)
            .enqueue(object : Callback<Any> {

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@ExpenseBreakdownActivity,
                            "Marked as paid",
                            Toast.LENGTH_SHORT
                        ).show()

                        // ✅ replace button with "Paid" text
                        val index = row.indexOfChild(btn)
                        row.removeView(btn)

                        val tvPaid = TextView(this@ExpenseBreakdownActivity)
                        tvPaid.text = "✓ Paid"
                        tvPaid.textSize = 12f
                        tvPaid.setTextColor(Color.parseColor("#159A78"))
                        row.addView(tvPaid, index)

                        // ✅ refresh home summary
                        refreshHomeSummary()

                    } else {
                        btn.isEnabled = true
                        btn.text = "Mark Paid"
                        Toast.makeText(
                            this@ExpenseBreakdownActivity,
                            "Failed to mark as paid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    btn.isEnabled = true
                    btn.text = "Mark Paid"
                    Toast.makeText(this@ExpenseBreakdownActivity, t.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun refreshHomeSummary() {
        // trigger home to reload by setting a flag
        getSharedPreferences("sharedbalance", MODE_PRIVATE)
            .edit()
            .putBoolean("needsRefresh", true)
            .apply()
    }
}