package com.example.sharedbalance.screens.creategroup

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.example.sharedbalance.R

class CreateGroupActivity :
    Activity(),
    CreateGroupContract.View {

    private lateinit var etGroupName:
            EditText

    private lateinit var etDescription:
            EditText

    private lateinit var etMembers:
            EditText

    private lateinit var etCategory:
            EditText

    private lateinit var btnCreate:
            Button

    private lateinit var btnCancel:
            Button

    private lateinit var progressBar:
            ProgressBar

    private lateinit var presenter:
            CreateGroupPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_create_group
        )

        initViews()

        presenter =
            CreateGroupPresenter(
                this,
                CreateGroupModel()
            )

        setupListeners()
    }

    private fun initViews() {

        etGroupName =
            findViewById(R.id.etGroupName)

        etDescription =
            findViewById(R.id.etDescription)

        etMembers =
            findViewById(R.id.etMembers)

        etCategory =
            findViewById(R.id.etCategory)

        btnCreate =
            findViewById(R.id.btnCreate)

        btnCancel =
            findViewById(R.id.btnCancel)

        progressBar =
            findViewById(R.id.progressBar)
    }

    private fun setupListeners() {

        btnCancel.setOnClickListener {

            finish()
        }

        btnCreate.setOnClickListener {

            val name =
                etGroupName.text.toString().trim()

            val description =
                etDescription.text.toString().trim()

            val membersText =
                etMembers.text.toString().trim()

            val category =
                etCategory.text.toString().trim()

            if (
                name.isEmpty() ||
                description.isEmpty() ||
                membersText.isEmpty() ||
                category.isEmpty()
            ) {

                showError(
                    "Complete all fields"
                )

                return@setOnClickListener
            }

            val prefs =
                getSharedPreferences(
                    "sharedbalance",
                    MODE_PRIVATE
                )

            val userId =
                prefs.getInt(
                    "userId",
                    -1
                )

            val members =
                membersText.split(",")

            val request =
                CreateGroupRequest(
                    name,
                    description,
                    userId,
                    members,
                    category
                )

            presenter.createGroup(request)
        }
    }

    override fun showLoading() {

        progressBar.visibility =
            View.VISIBLE
    }

    override fun hideLoading() {

        progressBar.visibility =
            View.GONE
    }

    override fun showSuccess(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()

        finish()
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