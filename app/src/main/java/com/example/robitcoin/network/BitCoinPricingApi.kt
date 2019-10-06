package com.example.robitcoin.network

import com.example.robitcoin.network.model.BitCoinPriceing
import com.example.robitcoin.network.model.BlockChainStats
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import io.reactivex.Observable

const val PATH = "charts/transactions-per-second"
const val CHARTS = "charts/{chartType}"
const val STATS = "stats"

interface BitCoinPricingApi {
    @GET("$CHARTS")
    @Headers(value = ["Accept:application/json; charset=utf-8", "Accept-Charset:utf-8"])
    fun getCharts(
        /*@Query("chartName") chartName: String,*/
        @Path("chartType") chartType: String,
        @Query("timespan") timespan: String,
        @Query("rollingAverage") rollingAverage: String
        /* @Path("start") start: String,
         @Path("format") format: String
         @Path("sampled") sampled: String*/
    ): Observable<Response<BitCoinPriceing>>


    @GET("$STATS")
    @Headers(value = ["Accept:application/json; charset=utf-8", "Accept-Charset:utf-8"])
    fun getBlockChainStats() : Observable<Response<BlockChainStats>>

}