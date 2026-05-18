package com.example.sharedbalance.screens.expenses.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.expenses.breakdown.ExpenseBreakdownActivity
import com.example.sharedbalance.screens.expenses.calculate.CalculateExpenseActivity

class GroupDetailsActivity :
    Activity(),
    GroupDetailsContract.View {

    private lateinit var presenter:
            GroupDetailsPresenter

    private lateinit var tvName:
            TextView

    private lateinit var btnCalculate:
            Button

    private lateinit var btnView:
            Button

    private var currentGroup:
            Group? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_group_details
        )

        tvName =
            findViewById(R.id.tvGroupName)

        btnCalculate =
            findViewById(R.id.btnCalculate)

        btnView =
            findViewById(R.id.btnViewShare)

        presenter =
            GroupDetailsPresenter(
                this,
                GroupDetailsModel()
            )

        val groupId =
            intent.getIntExtra(
                "groupId",
                -1
            )

        if (groupId == -1) {

            finish()

            return
        }

        presenter.loadGroup(groupId)

        btnCalculate.setOnClickListener {

            currentGroup?.let { group ->

                val intent =
                    Intent(
                        this,
                        CalculateExpenseActivity::class.java
                    )

                intent.putExtra(
                    "groupName",
                    group.name
                )

                startActivity(intent)
            }
        }

        btnView.setOnClickListener {

            currentGroup?.let { group ->

                val intent =
                    Intent(
                        this,
                        ExpenseBreakdownActivity::class.java
                    )

                intent.putExtra(
                    "groupName",
                    group.name
                )

                startActivity(intent)
            }
        }
    }

    override fun showGroup(
        group: Group
    ) {

        currentGroup = group

        tvName.text =
            group.name
    }

    override fun hideCalculateButton() {

        btnCalculate.visibility =
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