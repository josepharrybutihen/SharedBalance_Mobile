package com.example.sharedbalance.screens.account

import retrofit2.Call

interface AccountContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showUser(
            user: UserProfile
        )

        fun showError(
            message: String
        )

        fun logoutSuccess()
    }

    interface Presenter {

        fun loadUser()

        fun logout()
    }

    interface Model {

        fun getUser():
                Call<UserProfileResponse>
    }
}