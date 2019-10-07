package com.example.robitcoin.network

import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.Coordinates
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.network.model.Values


fun BlockChainStats.toBlockChainPopularStats(): BlockChainPopularStats{
    return BlockChainPopularStats(blocksMined,minutedBtwBlocks,btcMined,marketPrice,tradeVolumeUsd,tradeVolumeBtc,totalFeesBtc,tx,transactionVolume,minersRevenue)
}

fun BlockChainGraphPlot.toGraphData(): BlockChainGraph {
    return BlockChainGraph(this.values.parseToPricePoints(),description,name,unit,period)

}

fun List<Values>.parseToPricePoints() : List<Coordinates>{
   val result = mutableListOf<Coordinates>()
    apply {
        forEach {
            result.add(Coordinates(it.x, it.y))
        }

        return result
    }
}
