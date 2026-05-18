package com.example.sharedbalance.screens.expenses

data class Group(

    val id: Int,

    val name: String,

    val createdBy: String,

    val membersCount: Int,

    val calculated: Boolean
)