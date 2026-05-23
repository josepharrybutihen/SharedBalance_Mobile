package com.example.sharedbalance.screens.home

import com.example.sharedbalance.data.api.ApiResponse
import com.example.sharedbalance.screens.expenses.Group
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(
    private val view: HomeContract.View,
    private val model: HomeContract.Model
) : HomeContract.Presenter {

    private var summaryLoaded = false
    private var groupsLoaded = false

    override fun loadHomeData(email: String) {

        view.showLoading()

        summaryLoaded = false
        groupsLoaded = false

        loadSummary(email)
        loadGroups(email)
    }

    private fun loadSummary(email: String) {

        model.getSummary(email)
            .enqueue(object : Callback<Map<String, Any>> {

                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {

                    println("SUMMARY RESPONSE CODE = ${response.code()}")
                    println("SUMMARY BODY = ${response.body()}")

                    val body = response.body()

                    if (response.isSuccessful && body != null) {

                        val toPay =
                            (body["toPay"] as Number).toDouble()

                        val toReceive =
                            (body["toReceive"] as Number).toDouble()

                        val netBalance =
                            (body["netBalance"] as Number).toDouble()

                        view.showSummary(
                            toPay,
                            toReceive,
                            netBalance
                        )
                    }

                    summaryLoaded = true
                    checkDone()
                }

                override fun onFailure(
                    call: Call<Map<String, Any>>,
                    t: Throwable
                ) {

                    view.showError(
                        t.message ?: "Summary failed"
                    )

                    summaryLoaded = true
                    checkDone()
                }
            })
    }

    private fun loadGroups(email: String) {

        model.getGroups(email)
            .enqueue(object : Callback<ApiResponse<List<Group>>> {

                override fun onResponse(
                    call: Call<ApiResponse<List<Group>>>,
                    response: Response<ApiResponse<List<Group>>>
                ) {

                    println("GROUPS RESPONSE CODE = ${response.code()}")
                    println("GROUPS BODY = ${response.body()}")

                    val groups = response.body()?.payload?.reversed()  // ✅ newest first

                    if (
                        response.isSuccessful &&
                        !groups.isNullOrEmpty()
                    ) {
                        view.showGroups(groups)
                    } else {
                        view.showEmptyGroups()
                    }

                    groupsLoaded = true
                    checkDone()
                }

                override fun onFailure(
                    call: Call<ApiResponse<List<Group>>>,
                    t: Throwable
                ) {

                    view.showError(
                        t.message ?: "Network error"
                    )

                    groupsLoaded = true
                    checkDone()
                }
            })
    }

    private fun checkDone() {

        if (summaryLoaded && groupsLoaded) {
            view.hideLoading()
        }
    }
}