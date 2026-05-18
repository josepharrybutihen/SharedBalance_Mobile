package com.example.sharedbalance.screens.expenses

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpensesPresenter(
    private val view: ExpensesContract.View,
    private val model: ExpensesContract.Model
) : ExpensesContract.Presenter {

    override fun loadGroups() {

        model.getGroups()
            .enqueue(

                object :
                    Callback<List<Group>> {

                    override fun onResponse(
                        call: Call<List<Group>>,
                        response: Response<List<Group>>
                    ) {

                        if (response.isSuccessful) {

                            response.body()?.let {

                                view.showGroups(it)
                            }

                        } else {

                            view.showError(
                                "Failed to load groups"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<List<Group>>,
                        t: Throwable
                    ) {

                        view.showError(
                            t.message ?: "Unknown error"
                        )
                    }
                }
            )
    }
}