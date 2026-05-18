package com.example.sharedbalance.screens.history

class HistoryModel : HistoryContract.Model {

    data class TransactionHistory(
        val groupName: String,
        val transactionType: String,
        val date: String
    )

    override fun getHistory(listener: HistoryContract.OnHistoryListener) {

        val historyList = listOf(
            TransactionHistory(
                "Champs Outing",
                "Expense Calculated",
                "May 14"
            ),

            TransactionHistory(
                "Beach Trip",
                "Payment Received",
                "May 13"
            ),

            TransactionHistory(
                "Food Trip",
                "Expense Added",
                "May 12"
            )
        )

        if (historyList.isNotEmpty()) {
            listener.onSuccess(historyList)
        } else {
            listener.onError("No history found")
        }
    }
}