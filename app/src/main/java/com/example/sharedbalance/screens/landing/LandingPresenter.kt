package com.example.sharedbalance.screens.landing

class LandingPresenter(private val view: LandingContract.View)
    : LandingContract.Presenter {

    override fun onLoginClicked() {
        view.goToLogin()
    }

    override fun onRegisterClicked() {
        view.goToRegister()
    }
}