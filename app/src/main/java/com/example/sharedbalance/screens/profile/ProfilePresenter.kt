package com.example.sharedbalance.screens.profile

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val model: ProfileContract.Model
) : ProfileContract.Presenter {

    override fun loadUser() {

        val user = model.getUser()

        view.showUser(
            user.firstName,
            user.lastName,
            user.email
        )
    }

    override fun saveProfile(
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ) {

        if (firstName.isEmpty() ||
            lastName.isEmpty()
        ) {

            view.showError(
                "Please fill all required fields"
            )

            return
        }

        if (password != confirmPassword) {

            view.showError(
                "Passwords do not match"
            )

            return
        }

        model.saveUser(
            firstName,
            lastName
        )

        view.showSuccess()
    }
}