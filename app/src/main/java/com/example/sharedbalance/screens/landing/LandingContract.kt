package com.example.sharedbalance.screens.landing

    interface LandingContract {

        interface View {
            fun goToLogin()
            fun goToRegister()
        }

        interface Presenter {
            fun onLoginClicked()
            fun onRegisterClicked()
        }
    }
