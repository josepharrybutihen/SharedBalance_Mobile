package com.example.sharedbalance.screens.profile.editprofile

import android.net.Uri
import com.example.sharedbalance.screens.profile.ProfileRequest

class EditProfilePresenter(
    private val view: EditProfileContract.View,
    private val model: EditProfileContract.Model
) : EditProfileContract.Presenter {

    private var currentProfile: EditProfile? = null

    override fun loadProfile() {
        view.showLoading()
        model.getProfile { profile ->
            view.hideLoading()
            if (profile != null) {
                currentProfile = profile
                view.showProfile(profile)
            } else {
                view.showError("Failed to load profile")
            }
        }
    }

    override fun updateProfile(
        firstName: String,
        lastName: String,
        password: String,
        imageUri: Uri?
    ) {
        val email = currentProfile?.email ?: return
        view.showLoading()

        if (imageUri != null) {
            // ✅ upload image first, then save profile
            model.uploadImage(imageUri) { imageUrl ->
                val request = ProfileRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password.ifEmpty { null },
                    profileImage = imageUrl ?: currentProfile?.profileImage
                )
                saveProfile(request)
            }
        } else {
            val request = ProfileRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password.ifEmpty { null },
                profileImage = currentProfile?.profileImage
            )
            saveProfile(request)
        }
    }

    private fun saveProfile(request: ProfileRequest) {
        model.updateProfile(request) { success, message ->
            view.hideLoading()
            if (success) view.showSuccess(message)
            else view.showError(message)
        }
    }
}