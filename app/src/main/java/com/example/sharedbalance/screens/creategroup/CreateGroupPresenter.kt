package com.example.sharedbalance.screens.creategroup

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateGroupPresenter(

    private val view:
    CreateGroupContract.View,

    private val model:
    CreateGroupContract.Model

) : CreateGroupContract.Presenter {

    override fun loadUsers() {

        view.showLoading()

        model.getUsers()
            .enqueue(

                object :
                    Callback<UsersResponse> {

                    override fun onResponse(
                        call: Call<UsersResponse>,
                        response: Response<UsersResponse>
                    ) {

                        view.hideLoading()

                        val body =
                            response.body()

                        if (
                            response.isSuccessful &&
                            body?.payload != null
                        ) {

                            view.showUsers(
                                body.payload
                            )

                        } else {

                            view.showError(
                                "Failed to load users"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<UsersResponse>,
                        t: Throwable
                    ) {

                        view.hideLoading()

                        view.showError(
                            t.message
                                ?: "Network error"
                        )
                    }
                }
            )
    }

    override fun createGroup(
        request: CreateGroupRequest
    ) {

        view.showLoading()

        model.createGroup(request)
            .enqueue(

                object :
                    Callback<CreateGroupResponse> {

                    override fun onResponse(
                        call: Call<CreateGroupResponse>,
                        response: Response<CreateGroupResponse>
                    ) {

                        view.hideLoading()

                        if (response.isSuccessful) {

                            view.showSuccess(
                                response.body()?.message
                                    ?: "Group created successfully"
                            )

                        } else {

                            view.showError(
                                "Failed creating group"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<CreateGroupResponse>,
                        t: Throwable
                    ) {

                        view.hideLoading()

                        view.showError(
                            t.message
                                ?: "Network error"
                        )
                    }
                }
            )
    }
}