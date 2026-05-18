package com.example.sharedbalance.screens.register

interface RegisterContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showSuccess()

        fun showError(message: String)
    }

    interface Presenter {

        fun register(
            firstName: String,
            lastName: String,
            email: String,
            pass: String,
            confirm: String
        )
    }

    interface Model {

        fun register(
            request: RegisterRequest,
            listener: OnRegisterListener
        )
    }

    interface OnRegisterListener {

        fun onSuccess()

        fun onError(msg: String)
    }
}
