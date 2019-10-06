package com.example.robitcoin.network

import android.util.Log
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.PricePoints
import com.example.robitcoin.model.Pricing
import com.example.robitcoin.network.model.BitCoinPriceing
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.network.model.Values
import java.math.BigDecimal


fun BlockChainStats.toBlockChainPopularStats(): BlockChainPopularStats{
    return BlockChainPopularStats(blocksMined,minutedBtwBlocks,btcMined,marketPrice,tradeVolumeUsd,tradeVolumeBtc,totalFeesBtc,tx,transactionVolume,minersRevenue)
}

fun BitCoinPriceing.toGraphData(): Pricing {
    return Pricing(this.valuers.parseToPricePoints(),description,name,unit,period)

}


//fun String.parseToDecimalFormat():String{
//
//    return this.toLong().toString()
//}

fun List<Values>.parseToPricePoints() : List<PricePoints>{
   val result = mutableListOf<PricePoints>()
    //todo Isnt this correct and more kotliny
    apply {
        forEach {
            result.add(PricePoints(it.x, it.y))
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