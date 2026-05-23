package com.example.sharedbalance.screens.creategroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R

class CategoryAdapter(
    private val categories: List<Int>,
    private var selected: Int,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.image.setImageResource(category)

        // ✅ Visual indication
        if (selected == category) {
            holder.itemView.alpha = 1f
            holder.itemView.scaleX = 1.1f
            holder.itemView.scaleY = 1.1f
            holder.image.setBackgroundResource(R.drawable.selected_border) // add this drawable
        } else {
            holder.itemView.alpha = 0.4f
            holder.itemView.scaleX = 1f
            holder.itemView.scaleY = 1f
            holder.image.setBackgroundResource(0)
        }

        holder.itemView.setOnClickListener {
            selected = category
            notifyDataSetChanged()
            onClick(category)
        }
    }
}