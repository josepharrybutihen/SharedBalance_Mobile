package com.example.sharedbalance.screens.settings

class SettingsPresenter(
    private var view: SettingsContract.View?,
    private val model: SettingsContract.Model
) : SettingsContract.Presenter {

    private var notificationEnabled = true

    private var selectedLanguage = "English"

    override fun loadSettings() {

        notificationEnabled =
            model.isNotificationEnabled()

        selectedLanguage =
            model.getLanguage()

        view?.showNotificationStatus(
            notificationEnabled
        )

        view?.showSelectedLanguage(
            selectedLanguage
        )

        view?.showUserInfo(
            model.getUserFirstName(),
            model.getUserLastName(),
            model.getUserEmail()
        )
    }

    override fun onNotificationChanged(
        enabled: Boolean
    ) {

        notificationEnabled = enabled
    }

    override fun onLanguageChanged(
        language: String
    ) {

        selectedLanguage = language
    }

    override fun onSaveClicked() {

        model.setNotificationEnabled(
            notificationEnabled
        )

        model.setLanguage(
            selectedLanguage
        )

        view?.showMessage(
            "Settings saved successfully"
        )
    }

    override fun onBackPressed() {

        view?.closeScreen()
    }

    override fun onDestroy() {

        view = null
    }
}