package com.example.sharedbalance.screens.login

import android.content.Context
import android.util.Log
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.data.api.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginModel(
    private val context: Context
) : LoginContract.Model {

    private val api = ApiClient.create(context)

    override fun login(
        request: LoginRequest,
        listener: LoginContract.OnLoginListener
    ) {

        api.login(request)
            .enqueue(object : Callback<ApiResponse<Map<String, Any>>> {

                override fun onResponse(
                    call: Call<ApiResponse<Map<String, Any>>>,
                    response: Response<ApiResponse<Map<String, Any>>>
                ) {

                    // ✅ DEBUG LOGS (ADD HERE)
                    Log.d("API_RESPONSE", response.toString())
                    Log.d("API_BODY", response.body()?.toString() ?: "NULL")

                    val body = response.body()

                    if (response.isSuccessful && body?.status == "success") {

                        val payload = body.payload

                        if (payload != null) {

                            val accountMap = payload["account"] as? Map<*, *>
                            val token = payload["accessToken"] as? String ?: ""

                            // ✅ DEBUG TOKEN (ADD HERE)
                            Log.d("AUTH", "Token received = $token")

                            if (accountMap != null) {

                                val user = User(
                                    id = (accountMap["id"] as? Number)?.toInt(),
                                    email = accountMap["email"] as? String,
                                    firstName = accountMap["firstName"] as? String,
                                    lastName = accountMap["lastName"] as? String
                                )

                                listener.onSuccess(
                                    LoginPayload(
                                        account = user,
                                        accessToken = token
                                    )
                                )
                                return
                            }
                        }

                        listener.onError("Invalid response format")

                    } else {
                        listener.onError(
                            body?.errorInfo?.toString() ?: "Login failed"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<Map<String, Any>>>,
                    t: Throwable
                ) {
                    Log.e("API_ERROR", t.message ?: "Network error")
                    listener.onError(t.message ?: "Network error")
                }
            })
    }
}