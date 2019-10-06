package com.example.robitcoin.network.model

import com.google.gson.annotations.SerializedName


data class BitCoinPriceing(
        @SerializedName("status") val status: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("unit") val unit: String?,
        @SerializedName("period") val period: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("values") val valuers: List<Values>)

data class Values(
        @SerializedName("x") val x: Double?,
        @SerializedName("y") val y: Double?
)

