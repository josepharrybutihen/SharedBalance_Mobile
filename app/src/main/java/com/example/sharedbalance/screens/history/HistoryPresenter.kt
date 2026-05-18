package com.example.sharedbalance.screens.history

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val model: HistoryContract.Model
) : HistoryContract.Presenter,
    HistoryContract.OnHistoryListener {

    override fun loadHistory() {
        model.getHistory(this)
    }

    override fun onSuccess(historyList: List<HistoryModel.TransactionHistory>) {

        if (historyList.isEmpty()) {
            view.showEmpty()
        } else {
            view.showHistory(historyList)
        }
    }

    override fun onError(message: String) {
        view.showError(message)
    }
}