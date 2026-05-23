package com.example.sharedbalance.screens.history

class HistoryPresenter(

    private val view:
    HistoryContract.View,

    private val model:
    HistoryContract.Model,

    private val email: String

) :
    HistoryContract.Presenter,

    HistoryContract.OnHistoryListener {

    override fun loadHistory() {

        model.getHistory(
            email,
            this
        )
    }

    override fun onSuccess(
        historyList: List<TransactionHistory>
    ) {

        if (historyList.isEmpty()) {

            view.showEmpty()

        } else {

            view.showHistory(historyList)
        }
    }

    override fun onError(
        message: String
    ) {

        view.showError(message)
    }
}