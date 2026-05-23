package com.example.sharedbalance.screens.expenses.calculate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.sharedbalance.R
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.data.api.ApiResponse
import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.expenses.breakdown.ExpenseBreakdownActivity
import com.example.sharedbalance.screens.history.HistoryActivity
import com.example.sharedbalance.screens.home.HomeActivity
import com.example.sharedbalance.screens.account.AccountActivity
import com.example.sharedbalance.screens.expenses.ExpensesActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculateExpenseActivity : Activity() {

    private lateinit var tvGroupName: TextView
    private lateinit var dropdownPayer: AutoCompleteTextView
    private lateinit var etAmount: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnBack: ImageView
    private lateinit var participantContainer: LinearLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var navHome: LinearLayout
    private lateinit var navExpenses: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navAccount: LinearLayout

    private var groupId: Long = -1
    private var groupName: String = ""
    private var groupMembers: List<String> = emptyList()
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        val prefs = getSharedPreferences("sharedbalance", MODE_PRIVATE)
        currentUser = prefs.getString("email", "") ?: ""

        groupId = intent.getLongExtra("groupId", -1)
        groupName = intent.getStringExtra("groupName") ?: ""

        initViews()
        setupNavigation()
        loadGroupFromBackend()
        setupButtons()
    }

    private fun initViews() {
        tvGroupName = findViewById(R.id.tvGroupName)
        dropdownPayer = findViewById(R.id.dropdownPayer)
        etAmount = findViewById(R.id.etAmount)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnBack = findViewById(R.id.btnBack)
        participantContainer = findViewById(R.id.participantContainer)
        progressBar = findViewById(R.id.progressBar)
        navHome = findViewById(R.id.navHome)
        navExpenses = findViewById(R.id.navExpenses)
        navHistory = findViewById(R.id.navHistory)
        navAccount = findViewById(R.id.navAccount)
        tvGroupName.text = groupName
    }

    private fun setupNavigation() {
        // ✅ back to group details
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

    private fun loadGroupFromBackend() {
        progressBar.visibility = View.VISIBLE

        ApiClient.create(this)
            .getGroupDetails(groupId)
            .enqueue(object : Callback<ApiResponse<Group>> {

                override fun onResponse(
                    call: Call<ApiResponse<Group>>,
                    response: Response<ApiResponse<Group>>
                ) {
                    val group = response.body()?.payload

                    if (group != null) {
                        val members = group.members.toMutableList()
                        if (currentUser.isNotEmpty() && !members.contains(currentUser)) {
                            members.add(currentUser)
                        }
                        groupMembers = members

                        renderParticipants(groupMembers)
                        setupDropdown(groupMembers)

                        if (currentUser.isNotEmpty()) {
                            dropdownPayer.setText(currentUser, false)
                        }

                        // ✅ check expenses directly instead of trusting group.calculated
                        checkIfAlreadyCalculated()
                    } else {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@CalculateExpenseActivity,
                            "Failed to load group",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Group>>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@CalculateExpenseActivity,
                        t.message ?: "Network error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun checkIfAlreadyCalculated() {
        ApiClient.create(this)
            .getExpensesByGroup(groupId, currentUser)
            .enqueue(object : Callback<List<com.example.sharedbalance.screens.expenses.Expenses>> {

                override fun onResponse(
                    call: Call<List<com.example.sharedbalance.screens.expenses.Expenses>>,
                    response: Response<List<com.example.sharedbalance.screens.expenses.Expenses>>
                ) {
                    progressBar.visibility = View.GONE

                    val hasExpenses = response.isSuccessful &&
                            !response.body().isNullOrEmpty()

                    if (hasExpenses) {
                        // ✅ already calculated — go straight to breakdown
                        Toast.makeText(
                            this@CalculateExpenseActivity,
                            "Already calculated — showing breakdown",
                            Toast.LENGTH_SHORT
                        ).show()
                        goToBreakdown()
                    }
                    // else: stay on calculate screen, user can proceed
                }

                override fun onFailure(
                    call: Call<List<com.example.sharedbalance.screens.expenses.Expenses>>,
                    t: Throwable
                ) {
                    progressBar.visibility = View.GONE
                    // silent fail — let user try to calculate
                }
            })
    }

    private fun renderParticipants(members: List<String>) {
        participantContainer.removeAllViews()
        for (email in members) {
            val tv = TextView(this)
            tv.text = "• $email"
            tv.textSize = 14f
            tv.setPadding(0, 4, 0, 4)
            participantContainer.addView(tv)
        }
    }

    private fun setupDropdown(members: List<String>) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            members
        )
        dropdownPayer.setAdapter(adapter)
    }

    private fun setupButtons() {
        btnCalculate.setOnClickListener {
            val payer = dropdownPayer.text.toString().trim()
            val amountText = etAmount.text.toString().trim()

            when {
                payer.isEmpty() -> {
                    Toast.makeText(this, "Select a payer", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                amountText.isEmpty() -> {
                    Toast.makeText(this, "Enter an amount", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                groupMembers.isEmpty() -> {
                    Toast.makeText(this, "Members not loaded yet", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnCalculate.isEnabled = false
            progressBar.visibility = View.VISIBLE

            val request = CalculateRequest(
                groupId = groupId,
                payer = payer,
                amount = amount,
                participants = groupMembers,
                groupName = groupName
            )

            ApiClient.create(this)
                .calculateExpense(request)
                .enqueue(object : Callback<ApiResponse<Map<String, Any>>> {

                    override fun onResponse(
                        call: Call<ApiResponse<Map<String, Any>>>,
                        response: Response<ApiResponse<Map<String, Any>>>
                    ) {
                        progressBar.visibility = View.GONE

                        when {
                            response.isSuccessful && response.body()?.payload != null -> {
                                goToBreakdown()
                            }
                            response.code() == 403 || response.code() == 500 -> {
                                Toast.makeText(
                                    this@CalculateExpenseActivity,
                                    "Already calculated — showing breakdown",
                                    Toast.LENGTH_SHORT
                                ).show()
                                goToBreakdown()
                            }
                            else -> {
                                btnCalculate.isEnabled = true
                                Toast.makeText(
                                    this@CalculateExpenseActivity,
                                    "Calculation failed (${response.code()})",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<ApiResponse<Map<String, Any>>>,
                        t: Throwable
                    ) {
                        progressBar.visibility = View.GONE
                        btnCalculate.isEnabled = true
                        Toast.makeText(
                            this@CalculateExpenseActivity,
                            t.message ?: "Network error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    private fun goToBreakdown() {
        val intent = Intent(this, ExpenseBreakdownActivity::class.java)
        intent.putExtra("groupId", groupId)
        intent.putExtra("groupName", groupName)
        startActivity(intent)
        finish()
    }
}