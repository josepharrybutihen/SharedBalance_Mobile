package com.example.sharedbalance.screens.creategroup

import com.example.sharedbalance.data.api.ApiClient

import retrofit2.Call

class CreateGroupModel :
    CreateGroupContract.Model {

    override fun createGroup(
        request: CreateGroupRequest
    ): Call<CreateGroupResponse> {

        return ApiClient
            .apiService
            .createGroup(request)
    }
}