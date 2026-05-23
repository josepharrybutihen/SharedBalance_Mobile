package com.example.sharedbalance.screens.account

data class UserProfileResponse(

    val payload: UserProfile

)

data class UserProfile(

    val firstName: String,

    val lastName: String,

    val email: String,

    val profileImage: String?
)