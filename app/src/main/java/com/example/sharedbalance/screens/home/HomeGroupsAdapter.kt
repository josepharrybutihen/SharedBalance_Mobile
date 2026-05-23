package com.example.sharedbalance.screens.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharedbalance.R
import com.example.sharedbalance.screens.expenses.Group
import com.example.sharedbalance.screens.expenses.details.GroupDetailsActivity

class HomeGroupsAdapter(
    private val groups: List<Group>
) : RecyclerView.Adapter<HomeGroupsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvGroupName: TextView = view.findViewById(R.id.tvGroupName)
        val tvCreator: TextView = view.findViewById(R.id.tvCreator)
        val tvMembers: TextView = view.findViewById(R.id.tvMembers)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val imgCategory: ImageView = view.findViewById(R.id.imgCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]

        holder.tvGroupName.text = group.name
        holder.tvCreator.text = "Created by: ${group.createdBy ?: "Unknown"}"
        holder.tvMembers.text = "${group.membersCount} members"
        holder.tvStatus.text = if (group.calculated) "CALCULATED" else "PENDING"

        // ✅ null-safe
        val imageRes = when (group.category?.lowercase()) {
            "party" -> R.drawable.party
            "beach" -> R.drawable.beach
            "dinner" -> R.drawable.dinner
            "travel", "roadtrip" -> R.drawable.roadtrip
            "hotel" -> R.drawable.hotel
            else -> R.drawable.others
        }
        holder.imgCategory.setImageResource(imageRes)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, GroupDetailsActivity::class.java)
            intent.putExtra("groupId", group.id)
            intent.putExtra("groupName", group.name)
            intent.putExtra("groupCalculated", group.calculated)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = groups.size
}