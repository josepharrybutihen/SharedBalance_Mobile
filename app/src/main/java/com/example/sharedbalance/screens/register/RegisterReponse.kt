package com.example.sharedbalance.screens.register

data class RegisterResponse(
    val status: String,
    val payload: Any?,
    val errorInfo: ErrorInfo?
)

data class ErrorInfo(
    val type: String,
    val message: String
)