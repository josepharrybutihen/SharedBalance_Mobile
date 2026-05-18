package com.example.sharedbalance.screens.profile

class ProfileModel : ProfileContract.Model {

    private var user =
        ProfileContract.User(
            "Kitty",
            "Cat",
            "kitty.cat@gmail.com"
        )

    override fun getUser(): ProfileContract.User {
        return user
    }

    override fun saveUser(
        firstName: String,
        lastName: String
    ) {

        user = ProfileContract.User(
            firstName,
            lastName,
            user.email
        )
    }
}