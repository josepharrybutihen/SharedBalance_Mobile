package com.example.sharedbalance.screens.expenses.calculate

data class CalculateRequest(
    val groupId: Long,
    val payer: String,          // ✅ was payerEmail
    val amount: Double,
    val participants: List<String>,  // ✅ was missing
    val groupName: String       // ✅ was missing
)