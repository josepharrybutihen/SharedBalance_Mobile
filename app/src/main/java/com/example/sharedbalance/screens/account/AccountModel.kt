package com.example.sharedbalance.screens.account

import android.content.Context
import com.example.sharedbalance.data.api.ApiClient
import retrofit2.Call

class AccountModel(
    private val context: Context
) : AccountContract.Model {

    private val api =
        ApiClient.create(context)

    override fun getUser():
            Call<UserProfileResponse> {

        return api.getUserProfile()
    }
}