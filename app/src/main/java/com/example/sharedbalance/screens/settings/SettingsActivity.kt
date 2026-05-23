package com.example.sharedbalance.screens.settings

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

import com.example.sharedbalance.R

class SettingsActivity : Activity(), SettingsContract.View {

    private lateinit var presenter: SettingsPresenter

    private lateinit var btnBack: ImageView
    private lateinit var imgProfile: ImageView

    private lateinit var switchNotification: Switch

    private lateinit var dropdownLanguage: AutoCompleteTextView

    private lateinit var btnSave: Button

    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var tvVersion: TextView

    private val languages = arrayOf(
        "English",
        "French",
        "Arabic",
        "Spanish",
        "German"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        presenter = SettingsPresenter(
            this,
            SettingsModel(this)
        )

        initViews()

        setupDropdown()

        presenter.loadSettings()

        setupListeners()
    }

    private fun initViews() {

        btnBack =
            findViewById(R.id.btnBack)

        imgProfile =
            findViewById(R.id.imgProfile)

        switchNotification =
            findViewById(R.id.switchNotification)

        dropdownLanguage =
            findViewById(R.id.dropdownLanguage)

        btnSave =
            findViewById(R.id.btnSave)

        txtName =
            findViewById(R.id.txtName)

        txtEmail =
            findViewById(R.id.txtEmail)

        tvVersion =
            findViewById(R.id.tvVersion)
    }

    private fun setupDropdown() {

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            languages
        )

        dropdownLanguage.setAdapter(adapter)
    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            presenter.onBackPressed()
        }

        switchNotification.setOnCheckedChangeListener {
                _, isChecked ->

            presenter.onNotificationChanged(
                isChecked
            )
        }

        dropdownLanguage.setOnItemClickListener {
                _, _, position, _ ->

            presenter.onLanguageChanged(
                languages[position]
            )
        }

        btnSave.setOnClickListener {

            presenter.onSaveClicked()
        }
    }

    override fun showNotificationStatus(enabled: Boolean) {

        switchNotification.isChecked =
            enabled
    }

    override fun showSelectedLanguage(language: String) {

        dropdownLanguage.setText(
            language,
            false
        )
    }

    override fun showUserInfo(
        firstName: String,
        lastName: String,
        email: String
    ) {

        txtName.text =
            "$firstName $lastName"

        txtEmail.text =
            email
    }

    override fun showMessage(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun closeScreen() {

        finish()
    }

    override fun onDestroy() {

        super.onDestroy()

        presenter.onDestroy()
    }
}