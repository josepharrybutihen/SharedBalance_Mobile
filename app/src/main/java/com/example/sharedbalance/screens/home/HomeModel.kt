package com.example.sharedbalance.screens.home

import com.example.sharedbalance.data.api.ApiClient

class HomeModel :
    HomeContract.Model {

    override fun getSummary(
        userId: Int
    ) = ApiClient
        .apiService
        .getHomeSummary(userId)

    override fun getGroups(
        userId: Int
    ) = ApiClient
        .apiService
        .getUserGroups(userId)
}