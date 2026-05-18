package com.example.sharedbalance.screens.login

data class LoginResponse(

    val success: Boolean,

    val message: String,

    val token: String,

    val userId: Int
)