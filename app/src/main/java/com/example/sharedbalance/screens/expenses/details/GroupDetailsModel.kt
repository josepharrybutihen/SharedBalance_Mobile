package com.example.sharedbalance.screens.expenses.details

import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.screens.expenses.Group

import retrofit2.Call

class GroupDetailsModel :
    GroupDetailsContract.Model {

    override fun getGroup(
        groupId: Int
    ): Call<Group> {

        return ApiClient
            .apiService
            .getGroupDetails(groupId)
    }
}