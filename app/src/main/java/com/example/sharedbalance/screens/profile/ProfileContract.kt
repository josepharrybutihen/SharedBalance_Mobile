package com.example.sharedbalance.screens.profile

interface ProfileContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showUser(firstName: String, lastName: String, email: String)
        fun showSuccess(message: String)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadUser(email: String)
        fun saveProfile(
            email: String,
            firstName: String,
            lastName: String,
            password: String,
            confirmPassword: String
        )
    }

    interface Model {
        fun getUser(email: String, listener: OnFinishedListener)
        fun updateProfile(email: String, request: ProfileRequest, listener: OnFinishedListener)
    }

    interface OnFinishedListener {
        fun onUserLoaded(response: ProfileResponse)
        fun onSuccess(response: ProfileResponse)
        fun onError(message: String)
    }
}