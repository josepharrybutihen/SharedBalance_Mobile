package com.example.sharedbalance.screens.expenses.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.Group

class GroupAdapter(
    private val groups: List<Group>,
    private val onClick: (Group) -> Unit
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.tvGroupName)
        val tvCreator: TextView = view.findViewById(R.id.tvCreator)
        val tvMembers: TextView = view.findViewById(R.id.tvMembers)
        val imgCategory: ImageView = view.findViewById(R.id.imgCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)

        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {

        val group = groups[position]

        holder.tvName.text = group.name
        holder.tvCreator.text = "Created by: ${group.createdBy}"
        holder.tvMembers.text = "${group.membersCount} members"

        val imageRes = when (group.category?.lowercase()) {
            "party" -> R.drawable.party
            "beach" -> R.drawable.beach
            "dinner" -> R.drawable.dinner
            "travel" -> R.drawable.roadtrip
            else -> R.drawable.others
        }

        holder.imgCategory.setImageResource(imageRes)

        holder.itemView.setOnClickListener {
            onClick(group)
        }
    }
}