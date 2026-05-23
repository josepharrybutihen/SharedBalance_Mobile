package com.example.sharedbalance.screens.expenses

import com.google.gson.annotations.SerializedName

data class Group(
    val id: Long = 0L,
    val name: String = "",
    @SerializedName("creatorName")
    val createdBy: String? = null,      // ✅ nullable
    val members: List<String> = emptyList(),
    val calculated: Boolean = false,
    val creatorEmail: String? = null,
    val category: String? = null,       // ✅ nullable
    val categoryImg: String? = null
) {
    val membersCount: Int get() = members.size
}