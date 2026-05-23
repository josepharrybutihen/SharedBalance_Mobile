package com.example.sharedbalance.screens.profile

data class ProfileRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String?,
    val profileImage: String? = null
)