package com.example.robitcoin.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val PATH = "user/sharedprofile"

interface BitCoinPricingApi {
    @GET("$PATH{chartName}/{timespan}/{rollingAverage}/{start}/{format}{sampled}")
    @Headers(value = ["Accept:application/json; charset=utf-8", "Accept-Charset:utf-8"])
    fun getIdentity(
        @Query("chartName") chartName: String,
        @Path("timespan") timespan: String,
        @Path("rollingAverage") rollingAverage: String,
        @Path("start") start: String,
        @Path("format") format: String,
        @Path("sampled") sampled: String
    ): Call<BitCoinPricingApi>
}