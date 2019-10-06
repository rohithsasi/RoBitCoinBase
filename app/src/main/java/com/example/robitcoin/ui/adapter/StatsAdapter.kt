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

    init {
        //updateDataSet(blockChainStats)
    }

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
        return dataSet.size;
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            STATS_DATA -> updateStatsDataViewHolder(holder as StatsDataViewHolder,dataSet.get(position))
            else -> updateStatsHeaderViewHolder(holder as StatsDataHeaderViewHolder,dataSet.get(position))
        }

    }

    /**
     * @see ControlViewHolder
     */
    private fun updateStatsDataViewHolder(holder: StatsDataViewHolder,stat: StatsDisplayDataSet) {
        stat.data?.let {
            holder.name.text = it.first
            holder.value.text =it.second
        }
    }

    /**
     * @see ControlViewHolder
     */
    private fun updateStatsHeaderViewHolder(holder: StatsDataHeaderViewHolder,stat: StatsDisplayDataSet) {
        holder.heading.text= stat.heading
    }


    /**
     * View holder used when no custom modes are present. It holds a single borderless button.
     * @see updateAddModeViewType
     */
    class StatsDataViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val name: TextView = row.findViewById(R.id.stats_name)
        val value: TextView = row.findViewById(R.id.stats_value)
    }

    /**
     * View holder used when at least one custom mode is present, but the maximum amount is not reached.
     * @see updatePlusViewType
     * @see MAXIMUM_CUSTOM_MODES
     */
    class StatsDataHeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val heading: TextView = row.findViewById(R.id.stats_heading)
    }





    companion object {
        const val STATS_DATA: Int = 0
        const val STATS_HEADING: Int = 1
    }
}