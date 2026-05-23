package com.example.sharedbalance.screens.login

import android.content.Context
import android.util.Log

class LoginPresenter(
    private val context: Context,
    private val view: LoginContract.View,
    private val model: LoginContract.Model
) : LoginContract.Presenter,
    LoginContract.OnLoginListener {

    override fun login(email: String, password: String) {

        when {
            email.isEmpty() -> {
                view.showError("Email required")
                return
            }
            password.isEmpty() -> {
                view.showError("Password required")
                return
            }
        }

        view.showLoading()

        model.login(
            LoginRequest(email, password),
            this
        )
    }

    override fun onSuccess(payload: LoginPayload) {

        view.hideLoading()

        val prefs = context.getSharedPreferences(
            "sharedbalance",
            Context.MODE_PRIVATE
        )

        // ✅ DEBUG (ADD HERE)
        Log.d("AUTH", "Saving token = ${payload.accessToken}")

        prefs.edit()
            .putInt("userId", payload.account.id ?: -1)
            .putString("token", payload.accessToken) // 🔥 IMPORTANT
            .putString("email", payload.account.email)
            .apply()

        // ✅ VERIFY IMMEDIATELY (ADD THIS DEBUG)
        Log.d("AUTH", "Saved token = " + prefs.getString("token", "NULL"))

        view.showSuccess(
            "Login successful",
            payload.account.id ?: -1,
            payload.accessToken
        )
    }

    override fun onError(msg: String) {
        view.hideLoading()
        view.showError(msg)
    }
}