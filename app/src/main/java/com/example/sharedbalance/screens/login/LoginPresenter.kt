package com.example.sharedbalance.screens.login

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(

    private val view: LoginContract.View,

    private val model: LoginContract.Model

) : LoginContract.Presenter {

    override fun login(
        email: String,
        password: String
    ) {

        if (
            email.isEmpty() ||
            password.isEmpty()
        ) {

            view.showError(
                "Please fill all fields"
            )

            return
        }

        view.showLoading()

        val request =
            LoginRequest(
                email,
                password
            )

        model.login(request)
            .enqueue(

                object :
                    Callback<LoginResponse> {

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        view.hideLoading()

                        if (
                            response.isSuccessful &&
                            response.body() != null &&
                            response.body()!!.success
                        ) {

                            val data =
                                response.body()!!

                            view.showSuccess(
                                data.message,
                                data.userId
                            )

                        } else {

                            view.showError(
                                "Invalid credentials"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<LoginResponse>,
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