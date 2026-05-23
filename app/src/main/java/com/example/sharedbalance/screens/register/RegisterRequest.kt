package com.example.sharedbalance.screens.register

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)