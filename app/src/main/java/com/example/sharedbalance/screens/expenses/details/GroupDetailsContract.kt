package com.example.sharedbalance.screens.expenses.details

import com.example.sharedbalance.screens.expenses.Group

import retrofit2.Call

interface GroupDetailsContract {

    interface View {

        fun showGroup(
            group: Group
        )

        fun hideCalculateButton()

        fun showError(
            message: String
        )
    }

    interface Presenter {

        fun loadGroup(
            groupId: Int
        )
    }

    interface Model {

        fun getGroup(
            groupId: Int
        ): Call<Group>
    }
}