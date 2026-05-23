package com.example.sharedbalance.screens.login

import retrofit2.Call

interface LoginContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showSuccess(message: String, userId: Int, token: String)
        fun showError(message: String)
    }

    interface Presenter {
        fun login(email: String, password: String)
    }

    interface Model {
        fun login(
            request: LoginRequest,
            listener: OnLoginListener
        )
    }

    interface OnLoginListener {
        fun onSuccess(payload: LoginPayload)
        fun onError(msg: String)
    }
}