package com.example.sharedbalance.screens.expenses.details

import android.content.Context
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.data.api.ApiResponse
import com.example.sharedbalance.screens.expenses.Group
import retrofit2.Call

class GroupDetailsModel(
    private val context: Context
) : GroupDetailsContract.Model {

    private val api = ApiClient.create(context)

    override fun getGroup(groupId: Long): Call<ApiResponse<Group>> {
        return api.getGroupDetails(groupId)
    }
}