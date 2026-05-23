package com.example.sharedbalance.screens.home

import android.content.Context
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.data.api.ApiResponse
import com.example.sharedbalance.screens.expenses.Group
import retrofit2.Call

class HomeModel(
    private val context: Context
) : HomeContract.Model {

    private val api = ApiClient.create(context)

    override fun getSummary(
        email: String
    ): Call<Map<String, Any>> {

        return api.getBalance(email)
    }

    override fun getGroups(
        email: String
    ): Call<ApiResponse<List<Group>>> {

        return api.getGroups(email)
    }
}