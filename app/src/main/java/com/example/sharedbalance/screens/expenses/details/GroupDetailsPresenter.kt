package com.example.sharedbalance.screens.expenses.details

import com.example.sharedbalance.screens.expenses.Group

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDetailsPresenter(

    private val view:
    GroupDetailsContract.View,

    private val model:
    GroupDetailsContract.Model

) : GroupDetailsContract.Presenter {

    override fun loadGroup(
        groupId: Int
    ) {

        model.getGroup(groupId)
            .enqueue(

                object :
                    Callback<Group> {

                    override fun onResponse(
                        call: Call<Group>,
                        response: Response<Group>
                    ) {

                        if (
                            response.isSuccessful &&
                            response.body() != null
                        ) {

                            val group =
                                response.body()!!

                            view.showGroup(group)

                            if (group.calculated) {

                                view.hideCalculateButton()
                            }

                        } else {

                            view.showError(
                                "Failed to load group"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<Group>,
                        t: Throwable
                    ) {

                        view.showError(
                            t.message
                                ?: "Network error"
                        )
                    }
                }
            )
    }
}