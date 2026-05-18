package com.example.sharedbalance.screens.register

class RegisterPresenter(

    private val view: RegisterContract.View,

    private val model: RegisterContract.Model

) : RegisterContract.Presenter,
    RegisterContract.OnRegisterListener {

    override fun register(
        firstName: String,
        lastName: String,
        email: String,
        pass: String,
        confirm: String
    ) {

        when {

            firstName.isEmpty() -> {
                view.showError("Name required")
                return
            }

            lastName.isEmpty() -> {
                view.showError("Name required")
                return
            }

            email.isEmpty() -> {
                view.showError("Email required")
                return
            }

            pass.isEmpty() -> {
                view.showError("Password required")
                return
            }

            pass != confirm -> {
                view.showError("Passwords do not match")
                return
            }
        }

        view.showLoading()

        val request = RegisterRequest(
            firstName,
            lastName,
            email,
            pass
        )

        model.register(request, this)
    }

    override fun onSuccess() {

        view.hideLoading()

        view.showSuccess()
    }

    override fun onError(msg: String) {

        view.hideLoading()

        view.showError(msg)
    }
}