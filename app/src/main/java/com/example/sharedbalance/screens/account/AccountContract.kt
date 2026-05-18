package com.example.sharedbalance.screens.account

interface AccountContract {

    interface View {
        fun showUser(
            name: String,
            email: String
        )

        fun logoutSuccess()
    }

    interface Presenter {
        fun loadUser()
        fun logout()
    }

    interface Model {
        fun getUser(): User
    }

    data class User(
        val name: String,
        val email: String
    )
}