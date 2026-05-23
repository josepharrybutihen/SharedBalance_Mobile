package com.example.sharedbalance.screens.account

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountPresenter(

    private val view:
    AccountContract.View,

    private val model:
    AccountContract.Model

) : AccountContract.Presenter {

    override fun loadUser() {

        view.showLoading()

        model.getUser()
            .enqueue(

                object :
                    Callback<UserProfileResponse> {

                    override fun onResponse(
                        call: Call<UserProfileResponse>,
                        response: Response<UserProfileResponse>
                    ) {

                        view.hideLoading()

                        if (
                            response.isSuccessful &&
                            response.body() != null
                        ) {

                            view.showUser(
                                response.body()!!.payload
                            )

                        } else {

                            view.showError(
                                "Failed to load profile"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<UserProfileResponse>,
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

    override fun logout() {

        view.logoutSuccess()
    }
}