package com.example.robitcoin.network.model

import com.google.gson.annotations.SerializedName

data class BlockChainStats(
    @SerializedName("timestamp")val timestamp : String,
    @SerializedName("market_price_usd")val marketPrice : String,
    @SerializedName("hash_rate")val hashRate : String,
    @SerializedName("total_fees_btc")val totalFeesBtc: String,
    @SerializedName("n_btc_mined")val btcMined : String,
    @SerializedName("n_tx")val tx : String,
    @SerializedName("n_blocks_mined")val blocksMined : String,
    @SerializedName("minutes_between_blocks")val minutedBtwBlocks : String,
    @SerializedName("totalbc")val totalBc : String,
    @SerializedName("n_blocks_total")val blocksTotal : String,
    @SerializedName("estimated_transaction_volume_usd")val transactionVolume : String,
    @SerializedName("blocks_size")val blocksSize : String,
    @SerializedName("miners_revenue_usd")val minersRevenue: String,
    @SerializedName("nextretarget")val nextTarget : String,
    @SerializedName("difficulty")val difficulty : String,
    @SerializedName("estimated_btc_sent")val btcSent : String?,
    @SerializedName("miners_revenue_btc")val minersRevenueBtc : String,
    @SerializedName("total_btc_sent")val totalBtcSent : String,
    @SerializedName("trade_volume_btc")val tradeVolumeBtc : String,
    @SerializedName("trade_volume_usd")val tradeVolumeUsd : String)