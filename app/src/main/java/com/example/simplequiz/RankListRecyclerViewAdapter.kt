package com.example.simplequiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankListRecyclerViewAdapter(private var rankList: List<RankItem>) :
    RecyclerView.Adapter<RankListRecyclerViewAdapter.ViewHolder>() {

    // ViewHolder class to hold references to the views in each item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankTextView: TextView = itemView.findViewById(R.id.rank_tv)
        val nameTextView: TextView = itemView.findViewById(R.id.name_tv)
        val scoreTextView: TextView = itemView.findViewById(R.id.score_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and create ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rank_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the views in each item
        var rank = position ;
        val currentItem = rankList[position]
        if(rank==0) rank = rankList.size
        holder.rankTextView.text = "$rank"
        holder.nameTextView.text = currentItem.name
        holder.scoreTextView.text = currentItem.score.toString()
    }

    override fun getItemCount(): Int {
        // Return the size of the list
        return rankList.size
    }

    // Function to update the rank list and sort it based on scores

}
