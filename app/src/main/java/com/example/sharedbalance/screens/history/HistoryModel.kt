package com.example.sharedbalance.screens.history

import android.content.Context
import com.example.sharedbalance.data.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryModel(
    private val context: Context
) : HistoryContract.Model {

    private val api =
        ApiClient.create(context)

    override fun getHistory(
        email: String,
        listener: HistoryContract.OnHistoryListener
    ) {

        api.getHistory(email)
            .enqueue(

                object :
                    Callback<List<TransactionHistory>> {

                    override fun onResponse(
                        call: Call<List<TransactionHistory>>,
                        response: Response<List<TransactionHistory>>
                    ) {

                        println("HISTORY CODE = ${response.code()}")
                        println("HISTORY BODY = ${response.body()}")

                        if (
                            response.isSuccessful &&
                            response.body() != null
                        ) {

                            listener.onSuccess(
                                response.body()!!
                            )

                        } else {

                            listener.onError(
                                "Failed to load history"
                            )
                        }
                    }

                    override fun onFailure(
                        call: Call<List<TransactionHistory>>,
                        t: Throwable
                    ) {

                        listener.onError(
                            t.message
                                ?: "Network error"
                        )
                    }
                }
            )
    }
}