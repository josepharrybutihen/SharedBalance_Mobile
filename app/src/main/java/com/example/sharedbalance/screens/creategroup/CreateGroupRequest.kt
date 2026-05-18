package com.example.sharedbalance.screens.creategroup

data class CreateGroupRequest(

    val name: String,

    val description: String,

    val createdBy: Int,

    val members: List<String>,

    val category: String
)