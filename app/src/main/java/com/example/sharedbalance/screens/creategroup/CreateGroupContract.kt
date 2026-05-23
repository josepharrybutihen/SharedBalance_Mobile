package com.example.sharedbalance.screens.creategroup

import retrofit2.Call

interface CreateGroupContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showUsers(users: List<User>)

        fun showSuccess(message: String)

        fun showError(message: String)
    }

    interface Presenter {

        fun loadUsers()

        fun createGroup(
            request: CreateGroupRequest
        )
    }

    interface Model {

        fun getUsers():
                Call<UsersResponse>

        fun createGroup(
            request: CreateGroupRequest
        ): Call<CreateGroupResponse>
    }
}