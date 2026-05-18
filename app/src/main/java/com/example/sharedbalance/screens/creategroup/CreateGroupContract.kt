package com.example.sharedbalance.screens.creategroup

import retrofit2.Call

interface CreateGroupContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showSuccess(
            message: String
        )

        fun showError(
            message: String
        )
    }

    interface Presenter {

        fun createGroup(
            request: CreateGroupRequest
        )
    }

    interface Model {

        fun createGroup(
            request: CreateGroupRequest
        ): Call<CreateGroupResponse>
    }
}