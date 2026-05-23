package com.example.sharedbalance.screens.history

interface HistoryContract {

    interface View {

        fun showHistory(
            historyList: List<TransactionHistory>
        )

        fun showEmpty()

        fun showError(
            message: String
        )
    }

    interface Presenter {

        fun loadHistory()
    }

    interface Model {

        fun getHistory(
            email: String,
            listener: OnHistoryListener
        )
    }

    interface OnHistoryListener {

        fun onSuccess(
            historyList: List<TransactionHistory>
        )

        fun onError(
            message: String
        )
    }
}