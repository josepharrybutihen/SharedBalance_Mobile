package com.example.sharedbalance.screens.creategroup

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R

class CreateGroupActivity :
    Activity(),
    CreateGroupContract.View {

    private lateinit var etGroupName:
            EditText

    private lateinit var etDescription:
            EditText

    private lateinit var btnCreate:
            Button

    private lateinit var btnCancel:
            Button

    private lateinit var progressBar:
            ProgressBar

    private lateinit var recyclerMembers:
            RecyclerView

    private lateinit var recyclerCategories:
            RecyclerView

    private lateinit var presenter:
            CreateGroupPresenter

    private val selectedMembers =
        mutableListOf<User>()

    private var selectedCategory =
        "beach"

    private var selectedCategoryImg =
        ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_create_group
        )

        initViews()

        presenter = CreateGroupPresenter(
            this,
            CreateGroupModel(this)
        )

        presenter.loadUsers()

        setupCategories()

        setupListeners()
    }

    private fun initViews() {

        etGroupName =
            findViewById(R.id.etGroupName)

        etDescription =
            findViewById(R.id.etDescription)

        btnCreate =
            findViewById(R.id.btnCreate)

        btnCancel =
            findViewById(R.id.btnCancel)

        progressBar =
            findViewById(R.id.progressBar)

        recyclerMembers =
            findViewById(R.id.recyclerMembers)

        recyclerCategories =
            findViewById(R.id.recyclerCategories)
    }

    private fun setupCategories() {

        val categories = listOf(
            R.drawable.beach,
            R.drawable.dinner,
            R.drawable.hotel,
            R.drawable.party,
            R.drawable.roadtrip,
            R.drawable.others,
        )

        recyclerCategories.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerCategories.adapter =
            CategoryAdapter(
                categories,
                categories[0]
            ) {

                selectedCategoryImg =
                    it.toString()

                selectedCategory =
                    when (it) {

                        R.drawable.beach ->
                            "beach"

                        R.drawable.dinner ->
                            "dinner"

                        R.drawable.hotel ->
                            "hotel"

                        R.drawable.party ->
                            "party"

                        R.drawable.roadtrip ->
                            "roadtrip"

                        else ->
                            "others"
                    }
            }
    }

    private fun setupListeners() {

        btnCancel.setOnClickListener {

            finish()
        }

        btnCreate.setOnClickListener {

            val name =
                etGroupName.text.toString()

            val description =
                etDescription.text.toString()

            if (
                name.isEmpty() ||
                description.isEmpty()
            ) {

                showError(
                    "Complete all fields"
                )

                return@setOnClickListener
            }

            if (selectedMembers.isEmpty()) {

                showError(
                    "Select members"
                )

                return@setOnClickListener
            }

            val prefs =
                getSharedPreferences(
                    "sharedbalance",
                    MODE_PRIVATE
                )

            val creatorName =
                prefs.getString(
                    "name",
                    ""
                ) ?: ""

            val creatorEmail =
                prefs.getString(
                    "email",
                    ""
                ) ?: ""

            val members =
                selectedMembers.map {
                    it.email
                }

            val request =
                CreateGroupRequest(
                    name,
                    description,
                    members,
                    selectedCategory,
                    selectedCategoryImg,
                    creatorName,
                    creatorEmail
                )

            presenter.createGroup(request)
        }
    }

    override fun showUsers(
        users: List<User>
    ) {

        recyclerMembers.layoutManager =
            LinearLayoutManager(this)

        recyclerMembers.adapter =
            MemberAdapter(
                users,
                selectedMembers
            ) { user ->

                if (
                    selectedMembers.contains(user)
                ) {

                    selectedMembers.remove(user)

                } else {

                    selectedMembers.add(user)
                }
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