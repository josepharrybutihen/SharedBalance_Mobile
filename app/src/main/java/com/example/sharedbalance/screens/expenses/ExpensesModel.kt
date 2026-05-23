package com.example.sharedbalance.screens.expenses

import com.example.sharedbalance.data.api.ApiResponse
import com.example.sharedbalance.data.api.ApiService
import retrofit2.Call

class ExpensesModel(
    private val apiService: ApiService
) : ExpensesContract.Model {

    override fun getGroups(email: String): Call<ApiResponse<List<Group>>> =
        apiService.getGroups(email)

    override fun getBalance(email: String): Call<Map<String, Any>> =
        apiService.getBalance(email)
}