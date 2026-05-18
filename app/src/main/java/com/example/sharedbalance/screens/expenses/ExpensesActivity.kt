package com.example.sharedbalance.screens.expenses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.adapter.GroupAdapter
import com.example.sharedbalance.screens.expenses.details.GroupDetailsActivity

class ExpensesActivity :
    Activity(),
    ExpensesContract.View {

    private lateinit var recycler: RecyclerView

    private lateinit var presenter:
            ExpensesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_expenses
        )

        recycler =
            findViewById(R.id.recyclerGroups)

        recycler.layoutManager =
            LinearLayoutManager(this)

        presenter =
            ExpensesPresenter(
                this,
                ExpensesModel()
            )

        presenter.loadGroups()
    }

    override fun showGroups(
        groups: List<Group>
    ) {

        recycler.adapter =
            GroupAdapter(groups) { group ->

                val intent =
                    Intent(
                        this,
                        GroupDetailsActivity::class.java
                    )

                intent.putExtra(
                    "groupId",
                    group.id
                )

                intent.putExtra(
                    "groupName",
                    group.name
                )

                intent.putExtra(
                    "groupCalculated",
                    group.calculated
                )

                startActivity(intent)
            }
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