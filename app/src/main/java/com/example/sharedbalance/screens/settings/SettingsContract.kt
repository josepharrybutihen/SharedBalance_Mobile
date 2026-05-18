package com.example.sharedbalance.screens.settings

interface SettingsContract {

    interface View {

        fun showNotificationStatus(enabled: Boolean)

        fun showSelectedLanguage(language: String)

        fun showUserInfo(
            firstName: String,
            lastName: String,
            email: String
        )

        fun showMessage(message: String)

        fun closeScreen()
    }

    interface Presenter {

        fun loadSettings()

        fun onNotificationChanged(enabled: Boolean)

        fun onLanguageChanged(language: String)

        fun onSaveClicked()

        fun onBackPressed()

        fun onDestroy()
    }

    interface Model {

        fun isNotificationEnabled(): Boolean

        fun setNotificationEnabled(enabled: Boolean)

        fun getLanguage(): String

        fun setLanguage(language: String)

        fun getUserFirstName(): String

        fun getUserLastName(): String

        fun getUserEmail(): String
    }
}