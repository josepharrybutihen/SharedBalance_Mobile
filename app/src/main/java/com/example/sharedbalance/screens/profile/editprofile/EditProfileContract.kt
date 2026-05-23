package com.example.sharedbalance.screens.profile.editprofile

import android.net.Uri
import com.example.sharedbalance.screens.profile.ProfileRequest

interface EditProfileContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showProfile(profile: EditProfile)
        fun showSuccess(message: String)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadProfile()
        fun updateProfile(firstName: String, lastName: String, password: String, imageUri: Uri?)
    }

    interface Model {
        fun getProfile(callback: (EditProfile?) -> Unit)
        fun updateProfile(request: ProfileRequest, callback: (Boolean, String) -> Unit)
        fun uploadImage(imageUri: Uri, callback: (String?) -> Unit)
    }
}