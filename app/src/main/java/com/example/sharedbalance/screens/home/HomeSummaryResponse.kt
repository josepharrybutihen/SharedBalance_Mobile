package com.example.sharedbalance.screens.home

data class HomeSummaryResponse(

    val toPay: Double,

    val received: Double,

    val net: Double
)