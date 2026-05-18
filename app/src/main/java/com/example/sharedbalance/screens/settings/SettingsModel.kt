package com.example.sharedbalance.screens.settings

import android.content.Context
import android.content.SharedPreferences

class SettingsModel(context: Context) : SettingsContract.Model {

    companion object {

        private const val PREF_NAME =
            "settings_pref"

        private const val KEY_NOTIFICATION =
            "notification"

        private const val KEY_LANGUAGE =
            "language"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

    override fun isNotificationEnabled(): Boolean {

        return preferences.getBoolean(
            KEY_NOTIFICATION,
            true
        )
    }

    override fun setNotificationEnabled(
        enabled: Boolean
    ) {

        preferences.edit()
            .putBoolean(
                KEY_NOTIFICATION,
                enabled
            )
            .apply()
    }

    override fun getLanguage(): String {

        return preferences.getString(
            KEY_LANGUAGE,
            "English"
        ) ?: "English"
    }

    override fun setLanguage(language: String) {

        preferences.edit()
            .putString(
                KEY_LANGUAGE,
                language
            )
            .apply()
    }

    override fun getUserFirstName(): String {

        return "Kitty"
    }

    override fun getUserLastName(): String {

        return "Cat"
    }

    override fun getUserEmail(): String {

        return "kitty.cat@gmail.com"
    }
}