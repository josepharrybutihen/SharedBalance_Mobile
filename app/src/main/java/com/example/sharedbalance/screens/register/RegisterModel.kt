package com.example.sharedbalance.screens.register

import android.content.Context
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.data.api.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterModel(
    private val context: Context
) : RegisterContract.Model {

    private val api = ApiClient.create(context)

    override fun register(
        request: RegisterRequest,
        listener: RegisterContract.OnRegisterListener
    ) {

        api.register(request)
            .enqueue(object : Callback<ApiResponse<Map<String, Any>>> {

                override fun onResponse(
                    call: Call<ApiResponse<Map<String, Any>>>,
                    response: Response<ApiResponse<Map<String, Any>>>
                ) {

                    val body = response.body()

                    if (response.isSuccessful && body?.status == "success") {
                        listener.onSuccess()
                    } else {
                        listener.onError(
                            body?.errorInfo?.toString() ?: "Registration failed"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<Map<String, Any>>>,
                    t: Throwable
                ) {
                    listener.onError(t.message ?: "Network error")
                }
            })
    }
}