package com.example.sharedbalance.screens.expenses

data class Payment(
    val id: Long = 0,
    val groupId: Long = 0,
    val payer: String = "",
    val receiver: String = "",
    val amount: Double = 0.0,
    val paid: Boolean = false
)