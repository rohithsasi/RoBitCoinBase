package com.example.robitcoin.ui.adapter

import com.example.robitcoin.R
import com.example.robitcoin.getString
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.ui.adapter.StatsAdapter.Companion.STATS_DATA
import com.example.robitcoin.ui.adapter.StatsAdapter.Companion.STATS_HEADING


data class StatsDisplayDataSet(val viewType: Int, val data: Pair<String,String>? =null,val heading: String ="BLOCK SUMMARY")

object StatsUtil {

    fun generateStatsDataSet(blockChainStats : BlockChainPopularStats) : MutableList<StatsDisplayDataSet>{
        val dataSet = mutableListOf<StatsDisplayDataSet>()
        dataSet.add(StatsDisplayDataSet(STATS_HEADING,null, R.string.stats_block_summary_heading.getString()))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Blocks Mined",blockChainStats.blocksMined)))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Time Between Blocks",blockChainStats.timeBetweenBlocks)))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Bitcoins Mined",blockChainStats.bitcoinsMined)))

        dataSet.add(StatsDisplayDataSet(STATS_HEADING,null,R.string.stats_market_summary_heading.getString()))

        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Market Price",blockChainStats.marketPrice)))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Trade Volume",blockChainStats.tradeVolumeUsd)))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Trade Volume",blockChainStats.tradeVolumeUsd)))

        dataSet.add(StatsDisplayDataSet(STATS_HEADING,null,R.string.stats_transaction_summary_heading.getString()))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Total Transaction Fees (BTC)",blockChainStats.transactionFees)))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair(" Transaction Volume (USD)",blockChainStats.totalOutputVolume)))

        dataSet.add(StatsDisplayDataSet(STATS_HEADING,null,R.string.stats_mining_cost.getString()))
        dataSet.add(StatsDisplayDataSet(STATS_DATA,Pair("Total Miners Revenue (USD)",blockChainStats.minersRevenue)))

        return dataSet
    }
}