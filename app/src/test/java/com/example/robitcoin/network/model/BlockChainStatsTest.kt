package com.example.robitcoin.network.model

import com.example.robitcoin.network.toBlockChainPopularStats
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*

class BlockChainStatsTest {

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
   private val bitCoinStats = Gson().fromJson(statsResponse, BlockChainStats::class.java)
    private val bitcoinPopularStats = bitCoinStats.toBlockChainPopularStats()

    @Test
    fun getTimestamp() {
        assertEquals("1474035340000",bitCoinStats.timestamp)
    }

    @Test
    fun getMarketPrice() {
        assertEquals("610.036975",bitCoinStats.marketPrice)
        assertEquals("610.036975",bitcoinPopularStats.marketPrice)
    }

    @Test
    fun getHashRate() {
        assertEquals("1.8410989266292908E9",bitCoinStats.hashRate)
    }

    @Test
    fun getTotalFeesBtc() {
        assertEquals("6073543165",bitCoinStats.totalFeesBtc)
        assertEquals("6073543165",bitcoinPopularStats.transactionFees)
    }

    @Test
    fun getBtcMined() {
        assertEquals("205000000000",bitCoinStats.btcMined)
    }

    @Test
    fun getTx() {
        assertEquals("233805",bitCoinStats.tx)
        assertEquals("233805",bitcoinPopularStats.totalTransactions)
    }

    @Test
    fun getBlocksMined() {
        assertEquals("164",bitCoinStats.blocksMined)
        assertEquals("164",bitcoinPopularStats.blocksMined)
    }

    @Test
    fun getMinutedBtwBlocks() {
        assertEquals("8.2577",bitCoinStats.minutedBtwBlocks)
        assertEquals("8.2577",bitcoinPopularStats.timeBetweenBlocks)
    }

    @Test
    fun getTotalBc() {
        assertEquals("1587622500000000",bitCoinStats.totalBc)
    }

    @Test
    fun getBlocksTotal() {
        assertEquals("430098",bitCoinStats.blocksTotal)
    }

    @Test
    fun getTransactionVolume() {
        assertEquals("1.2342976868108143E8",bitCoinStats.transactionVolume)
        assertEquals("1.2342976868108143E8",bitcoinPopularStats.totalOutputVolume)
    }

    @Test
    fun getBlocksSize() {
        assertEquals("117490685",bitCoinStats.blocksSize)
    }

    @Test
    fun getMinersRevenue() {
        assertEquals("1287626.6577490852",bitCoinStats.minersRevenue)
        assertEquals("1287626.6577490852",bitcoinPopularStats.minersRevenue)
    }

    @Test
    fun getNextTarget() {
        assertEquals("431423",bitCoinStats.nextTarget)
    }

    @Test
    fun getDifficulty() {
        assertEquals("225832872179",bitCoinStats.difficulty)
    }

    @Test
    fun getBtcSent() {
        assertEquals("20233161880242",bitCoinStats.btcSent)
    }

    @Test
    fun getMinersRevenueBtc() {
        assertEquals("1287626.6577490852",bitCoinStats.minersRevenue)
    }

    @Test
    fun getTotalBtcSent() {
        assertEquals("184646388663542",bitCoinStats.totalBtcSent)
    }

    @Test
    fun getTradeVolumeBtc() {
        assertEquals("21597.09997288",bitCoinStats.tradeVolumeBtc)
        assertEquals("21597.09997288",bitcoinPopularStats.tradeVolumeBtc)
    }

    @Test
    fun getTradeVolumeUsd() {
        assertEquals("1.3175029536228297E7",bitCoinStats.tradeVolumeUsd)
        assertEquals("1.3175029536228297E7",bitcoinPopularStats.tradeVolumeUsd)
    }
}