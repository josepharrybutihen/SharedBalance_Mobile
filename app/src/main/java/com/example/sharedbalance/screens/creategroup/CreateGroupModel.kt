package com.example.sharedbalance.screens.creategroup

import android.content.Context

import com.example.sharedbalance.data.api.ApiClient

import retrofit2.Call

class CreateGroupModel(

    private val context: Context

) : CreateGroupContract.Model {

    private val api =
        ApiClient.create(context)

    override fun getUsers():
            Call<UsersResponse> {

        return api.getAllUsers()
    }

    override fun createGroup(
        request: CreateGroupRequest
    ): Call<CreateGroupResponse> {

        return api.createGroup(request)
    }
}