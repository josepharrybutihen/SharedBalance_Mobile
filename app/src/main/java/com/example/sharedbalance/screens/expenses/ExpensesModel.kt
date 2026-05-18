package com.example.sharedbalance.screens.expenses

import com.example.sharedbalance.data.api.ApiClient

import retrofit2.Call

class ExpensesModel :
    ExpensesContract.Model {

    override fun getGroups():
            Call<List<Group>> {

        return ApiClient
            .apiService
            .getGroups()
    }
}