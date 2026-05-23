package com.example.sharedbalance.screens.profile

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val model: ProfileContract.Model
) : ProfileContract.Presenter, ProfileContract.OnFinishedListener {

    override fun loadUser(email: String) {
        view.showLoading()
        model.getUser(email, this)
    }

    override fun saveProfile(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            view.showError("Fill all required fields")
            return
        }

        if (password.isNotEmpty() && password != confirmPassword) {
            view.showError("Passwords do not match")
            return
        }

        view.showLoading()

        val request = ProfileRequest(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password.ifEmpty { null }
        )

        model.updateProfile(email, request, this)
    }

    override fun onUserLoaded(response: ProfileResponse) {
        view.hideLoading()
        // ProfileResponse only has message now, loading is handled by AccountModel
        // This callback is unused for profile loading — getProfile uses getUserProfile
    }

    override fun onSuccess(response: ProfileResponse) {
        view.hideLoading()
        view.showSuccess(response.message)
    }

    override fun onError(message: String) {
        view.hideLoading()
        view.showError(message)
    }
}