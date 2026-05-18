package com.example.sharedbalance.screens.login

import retrofit2.Call

interface LoginContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showSuccess(
            message: String,
            userId: Int
        )

        fun showError(
            message: String
        )
    }

    interface Presenter {

        fun login(
            email: String,
            password: String
        )
    }

    interface Model {

        fun login(
            request: LoginRequest
        ): Call<LoginResponse>
    }
}