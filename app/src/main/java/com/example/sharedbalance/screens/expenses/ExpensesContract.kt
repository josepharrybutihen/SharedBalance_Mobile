package com.example.sharedbalance.screens.expenses

import com.example.sharedbalance.data.api.ApiResponse
import retrofit2.Call

interface ExpensesContract {

    interface View {
        fun showSummary(totalSpent: Double, totalOwed: Double, netBalance: Double)
        fun showGroups(groups: List<Group>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadData(email: String)
    }

    interface Model {
        fun getGroups(email: String): Call<ApiResponse<List<Group>>>
        fun getBalance(email: String): Call<Map<String, Any>>
    }
}