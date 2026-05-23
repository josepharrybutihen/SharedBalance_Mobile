package com.example.sharedbalance.data.api

import com.example.sharedbalance.screens.creategroup.*
import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.expenses.calculate.CalculateRequest
import com.example.sharedbalance.screens.login.LoginRequest
import com.example.sharedbalance.screens.register.RegisterRequest
import com.example.sharedbalance.screens.account.UserProfileResponse
import com.example.sharedbalance.screens.expenses.Expenses
import com.example.sharedbalance.screens.expenses.Payment
import com.example.sharedbalance.screens.history.TransactionHistory
import com.example.sharedbalance.screens.profile.ProfileRequest
import com.example.sharedbalance.screens.profile.ProfileResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // AUTH
    @POST("api/v1/users/signup")
    fun register(@Body request: RegisterRequest): Call<ApiResponse<Map<String, Any>>>

    @POST("api/v1/users/login")
    fun login(@Body request: LoginRequest): Call<ApiResponse<Map<String, Any>>>

    // GROUPS
    @POST("api/v1/groups/create")
    fun createGroup(@Body request: CreateGroupRequest): Call<CreateGroupResponse>

    @GET("api/v1/groups")
    fun getGroups(@Query("email") email: String): Call<ApiResponse<List<Group>>>

    @GET("api/v1/groups/{groupId}")
    fun getGroupDetails(@Path("groupId") groupId: Long): Call<ApiResponse<Group>>

    // GROUPS
//    @GET("api/v1/groups")
//    fun getGroups(
//        @Query("email") email: String
//    ): Call<ApiResponse<List<Group>>>

    // HOME BALANCE
    @GET("api/v1/payments/balance")
    fun getBalance(
        @Query("email") email: String
    ): Call<Map<String, Any>>
//    @GET("api/v1/groups/user/{userId}")
//    fun getUserGroups(@Path("userId") userId: Int): Call<List<Group>>

    // EXPENSES
    @POST("api/v1/expenses/calculate")
    fun calculateExpense(
        @Body request: CalculateRequest
    ): Call<ApiResponse<Map<String, Any>>>

    @GET("api/v1/expenses/group/{groupId}")
    fun getExpensesByGroup(
        @Path("groupId") groupId: Long,
        @Query("email") email: String
    ): Call<List<Expenses>>

    // PAYMENTS
    @GET("api/v1/payments/group/{groupId}")
    fun getPaymentsByGroup(@Path("groupId") groupId: Long): Call<List<Payment>>

    @PUT("api/v1/payments/{id}/mark-paid")
    fun markPaymentAsPaid(
        @Path("id") id: Long,
        @Query("userEmail") userEmail: String
    ): Call<Any>


    // USERS
    @GET("api/v1/users/all")
    fun getAllUsers(): Call<UsersResponse>

    @GET("api/v1/users/profile")
    fun getUserProfile(): Call<UserProfileResponse>

    @GET("api/v1/users/profile/{email}")
    fun getProfile(@Path("email") email: String): Call<ProfileResponse>

    @PUT("api/v1/users/update")
    fun updateProfile(@Body request: ProfileRequest): Call<ProfileResponse>

    // HISTORY
    // HISTORY
    @GET("api/v1/transactions")
    fun getHistory(
        @Query("email") email: String
    ): Call<List<TransactionHistory>>
}