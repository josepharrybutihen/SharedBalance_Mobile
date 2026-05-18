package com.example.sharedbalance.screens.home

import com.example.sharedbalance.screens.expenses.Group

import retrofit2.Call

interface HomeContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showSummary(
            toPay: Double,
            received: Double,
            net: Double
        )

        fun showGroups(
            groups: List<Group>
        )

        fun showEmptyGroups()

        fun showError(
            message: String
        )
    }

    interface Presenter {

        fun loadHomeData(
            userId: Int
        )
    }

    interface Model {

        fun getSummary(
            userId: Int
        ): Call<HomeSummaryResponse>

        fun getGroups(
            userId: Int
        ): Call<List<Group>>
    }
}