package com.example.sharedbalance.screens.expenses

import com.example.sharedbalance.data.api.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpensesPresenter(
    private val view: ExpensesContract.View,
    private val model: ExpensesContract.Model
) : ExpensesContract.Presenter {

    override fun loadData(email: String) {
        loadGroups(email)
        loadBalance(email)
    }

    private fun loadGroups(email: String) {
        model.getGroups(email)
            .enqueue(object : Callback<ApiResponse<List<Group>>> {

                override fun onResponse(
                    call: Call<ApiResponse<List<Group>>>,
                    response: Response<ApiResponse<List<Group>>>
                ) {
                    val groups = response.body()?.payload?.reversed()

                    if (response.isSuccessful && !groups.isNullOrEmpty()) {
                        view.showGroups(groups)
                    } else {
                        view.showGroups(emptyList())
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<List<Group>>>,
                    t: Throwable
                ) {
                    view.showError(t.message ?: "Groups failed")
                }
            })
    }

    private fun loadBalance(email: String) {
        model.getBalance(email)
            .enqueue(object : Callback<Map<String, Any>> {

                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        val toPay = (body["toPay"] as? Number)?.toDouble() ?: 0.0
                        val toReceive = (body["toReceive"] as? Number)?.toDouble() ?: 0.0
                        val netBalance = (body["netBalance"] as? Number)?.toDouble() ?: 0.0
                        view.showSummary(toPay, toReceive, netBalance)
                    }
                }

                override fun onFailure(
                    call: Call<Map<String, Any>>,
                    t: Throwable
                ) {
                    // silent fail
                }
            })
    }
}