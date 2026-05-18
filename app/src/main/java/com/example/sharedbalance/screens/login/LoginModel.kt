package com.example.sharedbalance.screens.login

import com.example.sharedbalance.data.api.ApiClient

class LoginModel : LoginContract.Model {

    override fun login(
        request: LoginRequest
    ) = ApiClient
        .apiService
        .login(request)
}