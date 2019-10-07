package com.example.robitcoin.network

import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*

class NetworkMarshellerTest {

    private val statsResponse: String = """
        {
        "market_price_usd": 610.036975,
        "hash_rate": 1.8410989266292908E9,
        "total_fees_btc": 6073543165,
        "n_btc_mined": 205000000000,
        "n_tx": 233805,
        "n_blocks_mined": 164,
        "minutes_between_blocks": 8.2577,
        "totalbc": 1587622500000000,
        "n_blocks_total": 430098,
        "estimated_transaction_volume_usd": 1.2342976868108143E8,
        "blocks_size": 117490685,
        "miners_revenue_usd": 1287626.6577490852,
        "nextretarget": 431423,
        "difficulty": 225832872179,
        "estimated_btc_sent": 20233161880242,
        "miners_revenue_btc": 2110,
        "total_btc_sent": 184646388663542,
        "trade_volume_btc": 21597.09997288,
        "trade_volume_usd": 1.3175029536228297E7,
        "timestamp": 1474035340000
    }
"""
    private val pricingResponse: String = """
    {
        "status": "OK",
        "name": "Transaction Rate",
        "unit": "Transactions Per Second",
        "period": "minute",
        "description": "The number of Bitcoin transactions added to the mempool per second.",
        "values": 
        [
               {"x":1570353620,"y":5667834},
               {"x":5667834,"y":4343},
               {"x":45464,"y":4545},
               {"x":34,"y":445},
               {"x":1570210620,"y":4214097222222219},
               {"x":1570210620,"y":44097222222219}

        ]
    }
"""
    @Test
    fun toBlockChainPopularStats() {
        val bitCoinStats = Gson().fromJson(statsResponse, BlockChainStats::class.java)
        val bitcoinPopularStats = bitCoinStats.toBlockChainPopularStats()
        assertEquals("1.3175029536228297E7",bitcoinPopularStats.tradeVolumeUsd)
        assertEquals("21597.09997288",bitcoinPopularStats.tradeVolumeBtc)
        assertEquals("1287626.6577490852",bitcoinPopularStats.minersRevenue)
        assertEquals("1.2342976868108143E8",bitcoinPopularStats.totalOutputVolume)
        assertEquals("8.2577",bitcoinPopularStats.timeBetweenBlocks)
        assertEquals("164",bitcoinPopularStats.blocksMined)
        assertEquals("233805",bitcoinPopularStats.totalTransactions)
        assertEquals("6073543165",bitcoinPopularStats.transactionFees)
        assertEquals("610.036975",bitcoinPopularStats.marketPrice)

    }

    @Test
    fun toGraphData() {
        val bitCoinPriceing = Gson().fromJson(pricingResponse, BlockChainGraphPlot::class.java)
        val pricing = bitCoinPriceing.toGraphData()
        assertNotNull(pricing)
        assertEquals("Transaction Rate", pricing.name)
        assertEquals("Transactions Per Second", pricing.unit)
        assertEquals("minute", pricing.period)
        assertEquals("minute", pricing.period)
        assertEquals("The number of Bitcoin transactions added to the mempool per second.", pricing.descripton)
        assertEquals(pricing.coordinates?.size,6)
    }

    @Test
    fun parseToPricePoints() {
        val bitCoinPriceing = Gson().fromJson(pricingResponse, BlockChainGraphPlot::class.java)
        val pricing = bitCoinPriceing.toGraphData()
        assertEquals(pricing.coordinates?.get(0)?.x,1.57035362E9)
        assertEquals(pricing.coordinates?.get(0)?.y,5667834.0)
        assertEquals(pricing.coordinates?.get(2)?.x,45464.0)
        assertEquals(pricing.coordinates?.get(4)?.y,4.214097222222219E15)
        assertEquals(pricing.coordinates?.size,6)
    }
}