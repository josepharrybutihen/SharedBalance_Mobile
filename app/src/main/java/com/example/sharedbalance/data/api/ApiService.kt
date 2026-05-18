package com.example.sharedbalance.data.api

import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.expenses.calculate.CalculateRequest
import com.example.sharedbalance.screens.login.LoginRequest
import com.example.sharedbalance.screens.login.LoginResponse
import com.example.sharedbalance.screens.home.HomeSummaryResponse
import com.example.sharedbalance.screens.register.RegisterRequest
import com.example.sharedbalance.screens.register.RegisterResponse
import com.example.sharedbalance.screens.creategroup.CreateGroupRequest
import com.example.sharedbalance.screens.creategroup.CreateGroupResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("groups/create")
    fun createGroup(
        @Body request: CreateGroupRequest
    ): Call<CreateGroupResponse>

    @GET("groups")
    fun getGroups():
            Call<List<Group>>

    @GET("groups/user/{userId}")
    fun getUserGroups(
        @Path("userId") userId: Int
    ): Call<List<Group>>

    @GET("groups/{groupId}")
    fun getGroupDetails(
        @Path("groupId") groupId: Int
    ): Call<Group>

    @GET("summary/{userId}")
    fun getHomeSummary(
        @Path("userId") userId: Int
    ): Call<HomeSummaryResponse>

    @POST("calculate")
    fun calculateExpense(
        @Body request: CalculateRequest
    ): Call<Void>


}