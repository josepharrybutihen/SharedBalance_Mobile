package com.example.sharedbalance.screens.account

class AccountModel : AccountContract.Model {

    override fun getUser(): AccountContract.User {

        return AccountContract.User(
            "Kitty Cat",
            "kitty.cat@gmail.com"
        )
    }
}