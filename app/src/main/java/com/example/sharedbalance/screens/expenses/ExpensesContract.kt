package com.example.sharedbalance.screens.expenses

import retrofit2.Call

interface ExpensesContract {

    interface View {

        fun showGroups(
            groups: List<Group>
        )

        fun showError(
            message: String
        )
    }

    interface Presenter {

        fun loadGroups()
    }

    interface Model {

        fun getGroups():
                Call<List<Group>>
    }
}