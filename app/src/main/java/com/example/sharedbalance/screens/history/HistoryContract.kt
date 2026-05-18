package com.example.sharedbalance.screens.history

interface HistoryContract {

    interface View {
        fun showHistory(historyList: List<HistoryModel.TransactionHistory>)
        fun showEmpty()
        fun showError(message: String)
    }

    interface Presenter {
        fun loadHistory()
    }

    interface Model {
        fun getHistory(listener: OnHistoryListener)
    }

    interface OnHistoryListener {
        fun onSuccess(historyList: List<HistoryModel.TransactionHistory>)
        fun onError(message: String)
    }
}