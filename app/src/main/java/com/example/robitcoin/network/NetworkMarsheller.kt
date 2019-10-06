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


//fun String.parseToDecimalFormat():String{
//
//    return this.toLong().toString()
//}

fun List<Values>.parseToPricePoints() : List<Coordinates>{
   val result = mutableListOf<Coordinates>()
    //todo Isnt this correct and more kotliny
    apply {
        forEach {
            result.add(Coordinates(it.x, it.y))
        }

        val x  = 1442534400.0
        //Log.d ("ROHITH",x.parseToDate())
        return result
    }
}


//fun Double.parseToDate() : String{
//
//    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
//    val date = java.util.Date(1532358895 * 1000)
//    return sdf.format(date)
//}