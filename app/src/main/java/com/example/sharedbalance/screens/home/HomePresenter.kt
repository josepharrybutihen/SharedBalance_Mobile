package com.example.sharedbalance.screens.home

import com.example.sharedbalance.screens.expenses.Group

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(

    private val view:
    HomeContract.View,

    private val model:
    HomeContract.Model

) : HomeContract.Presenter {

    override fun loadHomeData(
        userId: Int
    ) {

        view.showLoading()

        loadSummary(userId)

        loadGroups(userId)
    }

    private fun loadSummary(
        userId: Int
    ) {

        model.getSummary(userId)
            .enqueue(

                object :
                    Callback<HomeSummaryResponse> {

                    override fun onResponse(
                        call: Call<HomeSummaryResponse>,
                        response: Response<HomeSummaryResponse>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body() != null
                        ) {

                            val data =
                                response.body()!!

                            view.showSummary(
                                data.toPay,
                                data.received,
                                data.net
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<HomeSummaryResponse>,
                        t: Throwable
                    ) {

                        view.showError(
                            t.message
                                ?: "Summary load failed"
                        )
                    }
                }
            )
    }

    private fun loadGroups(
        userId: Int
    ) {

        model.getGroups(userId)
            .enqueue(

                object :
                    Callback<List<Group>> {

                    override fun onResponse(
                        call: Call<List<Group>>,
                        response: Response<List<Group>>
                    ) {

                        view.hideLoading()

                        if (
                            response.isSuccessful &&
                            response.body() != null
                        ) {

                            val groups =
                                response.body()!!

                            if (groups.isEmpty()) {

                                view.showEmptyGroups()

                            } else {

                                view.showGroups(groups)
                            }

                        } else {

                            view.showError(
                                "Failed loading groups"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<List<Group>>,
                        t: Throwable
                    ) {

                        view.hideLoading()

                        view.showError(
                            t.message
                                ?: "Network error"
                        )
                    }
                }
            )
    }
}