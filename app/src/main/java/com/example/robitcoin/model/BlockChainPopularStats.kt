package com.example.robitcoin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BlockChainPopularStats(
    //BLOCK SUMMARY
    val blocksMined: String,
    val timeBetweenBlocks: String,
    val bitcoinsMined: String,

    //MARKET SUMMARY
    val marketPrice: String,
    val tradeVolumeUsd: String,
    val tradeVolumeBtc: String,

    //TRANSACTION SUMMARY
    val transactionFees: String,
    val totalTransactions: String,
    val totalOutputVolume: String,

    //MINING
    val minersRevenue: String
) : Parcelable
