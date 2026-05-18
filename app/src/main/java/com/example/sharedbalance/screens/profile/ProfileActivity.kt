package com.example.sharedbalance.screens.profile

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.sharedbalance.R

class ProfileActivity : Activity(),
    ProfileContract.View {

    private lateinit var presenter: ProfilePresenter

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView

    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        presenter = ProfilePresenter(
            this,
            ProfileModel()
        )

        etFirstName =
            findViewById(R.id.etFirstName)

        etLastName =
            findViewById(R.id.etLastName)

        etEmail =
            findViewById(R.id.etEmail)

        etPassword =
            findViewById(R.id.etPassword)

        etConfirmPassword =
            findViewById(R.id.etConfirmPassword)

        tvProfileName =
            findViewById(R.id.tvProfileName)

        tvProfileEmail =
            findViewById(R.id.tvProfileEmail)

        btnSave =
            findViewById(R.id.btnSave)

        btnCancel =
            findViewById(R.id.btnCancel)

        btnBack =
            findViewById(R.id.btnBack)

        presenter.loadUser()

        btnSave.setOnClickListener {

            presenter.saveProfile(
                etFirstName.text.toString(),
                etLastName.text.toString(),
                etPassword.text.toString(),
                etConfirmPassword.text.toString()
            )
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun showUser(
        firstName: String,
        lastName: String,
        email: String
    ) {

        val fullName =
            "$firstName $lastName"

        tvProfileName.text =
            fullName

        tvProfileEmail.text =
            email

        etFirstName.setText(
            firstName
        )

        etLastName.setText(
            lastName
        )

        etEmail.setText(
            email
        )
    }

    override fun showSuccess() {

        Toast.makeText(
            this,
            "Profile updated successfully",
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }

    override fun showError(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}