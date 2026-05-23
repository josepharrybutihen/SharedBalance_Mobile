package com.example.sharedbalance.screens.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.sharedbalance.R
import com.example.sharedbalance.screens.history.TransactionHistory

class HistoryAdapter(

    private val historyList:
    List<TransactionHistory>

) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {

        val tvGroupName:
                TextView =
            itemView.findViewById(
                R.id.tvGroupName
            )

        val tvTransactionType:
                TextView =
            itemView.findViewById(
                R.id.tvTransactionType
            )

        val tvDate:
                TextView =
            itemView.findViewById(
                R.id.tvDate
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.history_item,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun getItemCount():
            Int {

        return historyList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val history =
            historyList[position]

        holder.tvGroupName.text =
            history.groupName

        holder.tvTransactionType.text =
            history.transactionType

        holder.tvDate.text =
            history.date
    }
}