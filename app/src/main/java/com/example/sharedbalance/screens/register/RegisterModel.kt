package com.example.sharedbalance.screens.register

import com.example.sharedbalance.data.api.ApiClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterModel : RegisterContract.Model {

    override fun register(
        request: RegisterRequest,
        listener: RegisterContract.OnRegisterListener
    ) {

        ApiClient
            .apiService
            .register(request)
            .enqueue(

                object : Callback<RegisterResponse> {

                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body()?.success == true
                        ) {

                            listener.onSuccess()

                        } else {

                            listener.onError(
                                response.body()?.message
                                    ?: "Registration failed"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<RegisterResponse>,
                        t: Throwable
                    ) {

                        listener.onError(
                            t.message ?: "Network Error"
                        )
                    }
                }
            )
    }
}