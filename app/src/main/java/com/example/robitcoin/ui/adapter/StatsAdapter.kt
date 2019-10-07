package com.example.robitcoin.ui.adapter

import android.annotation.SuppressLint
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.robitcoin.R
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.ui.adapter.StatsUtil.generateStatsDataSet

class StatsAdapter(blockChainStats: BlockChainPopularStats) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var dataSet = mutableListOf<StatsDisplayDataSet>()


     fun updateDataSet(blockChainStats: BlockChainPopularStats) {
        dataSet = generateStatsDataSet(blockChainStats)
        //notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            STATS_DATA ->StatsDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stats_row_layout, parent, false))
            else -> StatsDataHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stats_row_heading_layout, parent, false))

        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].viewType
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            STATS_DATA -> updateStatsDataViewHolder(holder as StatsDataViewHolder,dataSet[position])
            else -> updateStatsHeaderViewHolder(holder as StatsDataHeaderViewHolder,dataSet[position])
        }
    }

    private fun updateStatsDataViewHolder(holder: StatsDataViewHolder,stat: StatsDisplayDataSet) {
        stat.data?.let {
            holder.name.text = it.first
            holder.value.text =it.second
        }
    }

    private fun updateStatsHeaderViewHolder(holder: StatsDataHeaderViewHolder,stat: StatsDisplayDataSet) {
        holder.heading.text= stat.heading
    }

    class StatsDataViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val name: TextView = row.findViewById(R.id.stats_name)
        val value: TextView = row.findViewById(R.id.stats_value)
    }


    class StatsDataHeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val heading: TextView = row.findViewById(R.id.stats_heading)
    }

    companion object {
        const val STATS_DATA: Int = 0
        const val STATS_HEADING: Int = 1
    }
}