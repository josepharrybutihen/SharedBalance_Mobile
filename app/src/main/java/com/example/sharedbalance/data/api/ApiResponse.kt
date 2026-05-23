package com.example.sharedbalance.data.api

data class ApiResponse<T>(
    val status: String,
    val payload: T?,
    val errorInfo: Any?,
    val serverTime: String
)