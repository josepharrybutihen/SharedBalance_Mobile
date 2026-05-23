package com.example.sharedbalance.screens.home

data class BalanceResponse(
    val toPay: Double,
    val toReceive: Double,
    val netBalance: Double
)