package com.example.sharedbalance.screens.login

data class LoginPayload(
    val account: User,
    val accessToken: String
)

data class User(
    val id: Int? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null
)