package com.example.sharedbalance.screens.expenses


data class Expenses(
    val id: Long = 0,
    val payer: String = "",
    val totalAmount: Double = 0.0,
    val participantCount: Int = 0,
    val participants: String = "",
    val breakdown: String? = null,
    val groupId: Long = 0,
    val groupName: String = ""
)