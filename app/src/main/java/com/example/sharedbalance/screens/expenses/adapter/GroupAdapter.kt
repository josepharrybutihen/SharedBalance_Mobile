package com.example.sharedbalance.screens.expenses.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.Group

class GroupAdapter(
    private val groups: List<Group>,
    private val onClick: (Group) -> Unit
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val tvName =
            view.findViewById<TextView>(R.id.tvGroupName)

        val tvCreator =
            view.findViewById<TextView>(R.id.tvCreator)

        val tvMembers =
            view.findViewById<TextView>(R.id.tvMembers)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.group_item,
                parent,
                false
            )

        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(
        holder: GroupViewHolder,
        position: Int
    ) {

        val group = groups[position]

        holder.tvName.text =
            group.name

        holder.tvCreator.text =
            "Created by: ${group.createdBy}"

        holder.tvMembers.text =
            "${group.membersCount} members"

        holder.itemView.setOnClickListener {
            onClick(group)
        }
    }
}