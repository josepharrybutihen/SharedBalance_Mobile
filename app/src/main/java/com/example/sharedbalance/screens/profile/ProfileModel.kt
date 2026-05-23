package com.example.sharedbalance.screens.profile

import android.content.Context
import com.example.sharedbalance.data.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModel(
    private val context: Context
) : ProfileContract.Model {

    private val api = ApiClient.create(context)

    override fun getUser(
        email: String,
        listener: ProfileContract.OnFinishedListener
    ) {
        api.getProfile(email)
            .enqueue(object : Callback<ProfileResponse> {

                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        listener.onUserLoaded(response.body()!!)
                    } else {
                        listener.onError("Failed to load profile")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    listener.onError(t.message ?: "Network error")
                }
            })
    }

    override fun updateProfile(
        email: String,
        request: ProfileRequest,
        listener: ProfileContract.OnFinishedListener
    ) {
        api.updateProfile(request)
            .enqueue(object : Callback<ProfileResponse> {

                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        listener.onSuccess(response.body()!!)
                    } else {
                        listener.onError("Update failed")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    listener.onError(t.message ?: "Network error")
                }
            })
    }
}