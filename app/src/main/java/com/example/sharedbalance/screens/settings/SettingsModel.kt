package com.example.sharedbalance.screens.settings

import android.content.Context
import android.content.SharedPreferences

class SettingsModel(private val context: Context) : SettingsContract.Model {

    companion object {
        private const val PREF_SETTINGS = "settings_pref"
        private const val PREF_APP = "sharedbalance"
        private const val KEY_NOTIFICATION = "notification"
        private const val KEY_LANGUAGE = "language"
    }

    private val settingsPrefs: SharedPreferences =
        context.getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)

    private val appPrefs: SharedPreferences =
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)

    override fun isNotificationEnabled(): Boolean =
        settingsPrefs.getBoolean(KEY_NOTIFICATION, true)

    override fun setNotificationEnabled(enabled: Boolean) {
        settingsPrefs.edit().putBoolean(KEY_NOTIFICATION, enabled).apply()
    }

    override fun getLanguage(): String =
        settingsPrefs.getString(KEY_LANGUAGE, "English") ?: "English"

    override fun setLanguage(language: String) {
        settingsPrefs.edit().putString(KEY_LANGUAGE, language).apply()
    }

    // ✅ read from login session
    override fun getUserFirstName(): String =
        appPrefs.getString("firstName", "") ?: ""

    override fun getUserLastName(): String =
        appPrefs.getString("lastName", "") ?: ""

    override fun getUserEmail(): String =
        appPrefs.getString("email", "") ?: ""
}