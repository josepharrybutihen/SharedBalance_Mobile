package com.example.sharedbalance.screens.creategroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.sharedbalance.R

class MemberAdapter(

    private val users: List<User>,
    private val selected:
    MutableList<User>,

    private val onClick:
        (User) -> Unit

) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    inner class MemberViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val img =
            view.findViewById<ImageView>(R.id.imgProfile)

        val tvName =
            view.findViewById<TextView>(R.id.tvName)

        val tvEmail =
            view.findViewById<TextView>(R.id.tvEmail)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_member,
                    parent,
                    false
                )

        return MemberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val user = users[position]
        val isSelected = selected.contains(user)

        holder.tvName.text = "${user.firstName} ${user.lastName}"
        holder.tvEmail.text = user.email

        Glide.with(holder.itemView.context)
            .load(user.profileImage)
            .placeholder(R.drawable.ic_account)
            .into(holder.img)

        // ✅ Visual indication
        holder.itemView.alpha = if (isSelected) 1f else 0.5f
        holder.itemView.setBackgroundColor(
            if (isSelected)
                android.graphics.Color.parseColor("#EDE7F6")
            else
                android.graphics.Color.TRANSPARENT
        )

        holder.itemView.setOnClickListener {
            onClick(user)
            notifyItemChanged(position)
        }
    }
}