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

                        if (
                            response.isSuccessful &&
                            response.body() != null
                        ) {

                            val result =
                                response.body()!!

                            if (result.success) {

                                view.showSuccess(
                                    result.message
                                )

                            } else {

                                view.showError(
                                    result.message
                                )
                            }

                        } else {

                            view.showError(
                                "Failed to create group"
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