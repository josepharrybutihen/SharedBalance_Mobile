package com.example.sharedbalance.screens.account

class AccountPresenter(
    private val view: AccountContract.View,
    private val model: AccountContract.Model
) : AccountContract.Presenter {

    override fun loadUser() {

        val user = model.getUser()

        view.showUser(
            user.name,
            user.email
        )
    }

    override fun logout() {
        view.logoutSuccess()
    }
}