package com.example.robitcoin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BlockChainGraph(
    val coordinates: List<Coordinates>? = null, val descripton: String? = null,
    val name: String? = null, val unit: String? = null, val period: String? = null
) : Parcelable