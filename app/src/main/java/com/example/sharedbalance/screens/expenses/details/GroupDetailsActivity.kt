package com.example.sharedbalance.screens.expenses.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sharedbalance.R
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.screens.expenses.Expenses
import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.expenses.breakdown.ExpenseBreakdownActivity
import com.example.sharedbalance.screens.expenses.calculate.CalculateExpenseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDetailsActivity : Activity(), GroupDetailsContract.View {

    private lateinit var presenter: GroupDetailsPresenter
    private lateinit var tvName: TextView
    private lateinit var tvMembers: TextView
    private lateinit var tvCreatedBy: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnCalculate: Button
    private lateinit var btnView: Button
    private lateinit var btnBack: Button

    private var currentGroup: Group? = null
    private var groupId: Long = -1L
    private var currentUserEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)

        tvName = findViewById(R.id.tvGroupName)
        tvMembers = findViewById(R.id.tvMembers)
        tvCreatedBy = findViewById(R.id.tvCreatedBy)
        tvStatus = findViewById(R.id.tvStatus)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnView = findViewById(R.id.btnViewShare)
        btnBack = findViewById(R.id.btnBack)

        groupId = intent.getLongExtra("groupId", -1L)
        currentUserEmail = getSharedPreferences("sharedbalance", MODE_PRIVATE)
            .getString("email", "") ?: ""

        if (groupId == -1L) {
            Toast.makeText(this, "Invalid group ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        presenter = GroupDetailsPresenter(this, GroupDetailsModel(this))

        btnBack.setOnClickListener { finish() }

        btnCalculate.setOnClickListener {
            currentGroup?.let { group ->
                startActivity(
                    Intent(this, CalculateExpenseActivity::class.java)
                        .putExtra("groupId", group.id)
                        .putExtra("groupName", group.name)
                )
            }
        }

        btnView.setOnClickListener {
            currentGroup?.let { goToBreakdown(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadGroup(groupId)
    }

    override fun showGroup(group: Group) {
        currentGroup = group
        tvName.text = group.name
        tvMembers.text = "Members: ${group.membersCount}"
        tvCreatedBy.text = "Created by: ${group.createdBy ?: "Unknown"}"

        // ✅ hide both buttons while we check expenses
        btnCalculate.visibility = View.GONE
        btnView.visibility = View.GONE

        // ✅ check backend for expenses to determine calculated state
        checkIfCalculated(group)
    }

    private fun checkIfCalculated(group: Group) {
        ApiClient.create(this)
            .getExpensesByGroup(groupId, currentUserEmail)
            .enqueue(object : Callback<List<Expenses>> {

                override fun onResponse(
                    call: Call<List<Expenses>>,
                    response: Response<List<Expenses>>
                ) {
                    val hasExpenses = response.isSuccessful &&
                            !response.body().isNullOrEmpty()

                    if (hasExpenses) {
                        // ✅ expenses exist = already calculated
                        tvStatus.text = "Status: Calculated"
                        btnCalculate.visibility = View.GONE
                        btnView.visibility = View.VISIBLE

                        // ✅ update local flag so btnCalculate click also stays safe
                        currentGroup = group.copy(calculated = true)
                    } else {
                        // ✅ no expenses = not yet calculated
                        tvStatus.text = "Status: Pending Calculation"
                        btnCalculate.visibility = View.VISIBLE
                        btnView.visibility = View.VISIBLE
                        currentGroup = group.copy(calculated = false)
                    }
                }

                override fun onFailure(
                    call: Call<List<Expenses>>,
                    t: Throwable
                ) {
                    // on failure, show both buttons so user isn't stuck
                    btnCalculate.visibility = View.VISIBLE
                    btnView.visibility = View.VISIBLE
                }
            })
    }

    override fun hideCalculateButton() {
        btnCalculate.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToBreakdown(group: Group) {
        startActivity(
            Intent(this, ExpenseBreakdownActivity::class.java)
                .putExtra("groupId", group.id)
                .putExtra("groupName", group.name)
        )
    }
}