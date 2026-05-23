package com.example.sharedbalance.screens.creategroup

data class CreateGroupRequest(

    val name: String,

    val description: String,

    val members: List<String>,

    val category: String,

    val categoryImg: String,

    val creatorName: String,

    val creatorEmail: String
)