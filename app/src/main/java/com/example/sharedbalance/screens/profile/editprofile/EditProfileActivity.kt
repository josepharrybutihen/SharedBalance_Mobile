package com.example.sharedbalance.screens.profile.editprofile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.example.sharedbalance.R

class EditProfileActivity : Activity(), EditProfileContract.View {

    private lateinit var presenter: EditProfilePresenter
    private lateinit var imgProfile: ImageView
    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var btnBack: ImageView
    private lateinit var btnEditPhoto: Button
    private lateinit var progressBar: ProgressBar

    private var selectedImageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initViews()

        presenter = EditProfilePresenter(this, EditProfileModel(this))
        presenter.loadProfile()
        setupListeners()
    }

    private fun initViews() {
        imgProfile = findViewById(R.id.imgProfile)
        tvProfileName = findViewById(R.id.tvProfileName)
        tvProfileEmail = findViewById(R.id.tvProfileEmail)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        btnBack = findViewById(R.id.btnBack)
        btnEditPhoto = findViewById(R.id.btnEditPhoto)
        progressBar = findViewById(R.id.progressBar)

        etEmail.isEnabled = false
        etEmail.isFocusable = false
        etEmail.isClickable = false
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
        btnCancel.setOnClickListener { finish() }

        // ✅ open image picker
        btnEditPhoto.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, PICK_IMAGE)
        }

        btnSave.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            when {
                firstName.isEmpty() -> {
                    showError("First name is required")
                    return@setOnClickListener
                }
                lastName.isEmpty() -> {
                    showError("Last name is required")
                    return@setOnClickListener
                }
                password.isNotEmpty() && password != confirmPassword -> {
                    showError("Passwords do not match")
                    return@setOnClickListener
                }
            }

            presenter.updateProfile(firstName, lastName, password, selectedImageUri)
        }
    }

    // ✅ receive picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            Glide.with(this)
                .load(selectedImageUri)
                .placeholder(R.drawable.ic_account)
                .into(imgProfile)
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        btnSave.isEnabled = false
        btnEditPhoto.isEnabled = false
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        btnSave.isEnabled = true
        btnEditPhoto.isEnabled = true
    }

    override fun showProfile(profile: EditProfile) {
        tvProfileName.text = "${profile.firstName} ${profile.lastName}"
        tvProfileEmail.text = profile.email
        etFirstName.setText(profile.firstName)
        etLastName.setText(profile.lastName)
        etEmail.setText(profile.email)

        Glide.with(this)
            .load(profile.profileImage)
            .placeholder(R.drawable.ic_account)
            .into(imgProfile)
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}