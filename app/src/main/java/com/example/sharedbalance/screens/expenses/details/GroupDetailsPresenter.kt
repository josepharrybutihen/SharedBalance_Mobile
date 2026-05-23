package com.example.sharedbalance.screens.expenses.details

import com.example.sharedbalance.data.api.ApiResponse
import com.example.sharedbalance.screens.expenses.Group
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDetailsPresenter(
    private val view: GroupDetailsContract.View,
    private val model: GroupDetailsContract.Model
) : GroupDetailsContract.Presenter {


    override fun loadGroup(groupId: Long) {

        if (groupId <= 0) {
            view.showError("Invalid group ID")
            return
        }

        model.getGroup(groupId)
            .enqueue(object : Callback<ApiResponse<Group>> {

                override fun onResponse(
                    call: Call<ApiResponse<Group>>,
                    response: Response<ApiResponse<Group>>
                ) {
                    println("RAW RESPONSE: " + response.body())
                    println("ERROR BODY: " + response.errorBody()?.string())
                    println("CODE: " + response.code())

                    if (response.isSuccessful &&
                        response.body()?.payload != null
                    ) {

                        val group = response.body()!!.payload!!

                        view.showGroup(group)

                        if (group.calculated) {
                            view.hideCalculateButton()
                        }

                    } else {
                        view.showError("Failed to load group")
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<Group>>,
                    t: Throwable
                ) {
                    view.showError(t.message ?: "Network error")
                }
            })
    }
}