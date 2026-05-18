package com.example.sharedbalance.screens.expenses.calculate

data class CalculateRequest(

    val groupName: String,

    val payer: String,

    val amount: Double
)