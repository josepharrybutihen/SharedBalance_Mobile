package com.example.sharedbalance.screens.profile

interface ProfileContract {

    interface View {

        fun showUser(
            firstName: String,
            lastName: String,
            email: String
        )

        fun showSuccess()

        fun showError(message: String)
    }

    interface Presenter {

        fun loadUser()

        fun saveProfile(
            firstName: String,
            lastName: String,
            password: String,
            confirmPassword: String
        )
    }

    interface Model {

        fun getUser(): User

        fun saveUser(
            firstName: String,
            lastName: String
        )
    }

    data class User(
        val firstName: String,
        val lastName: String,
        val email: String
    )
}