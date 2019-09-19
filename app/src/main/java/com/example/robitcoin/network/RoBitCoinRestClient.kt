package com.example.robitcoin.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal interface RoBitCoinRestClient {
    val identityServiceApi: BitCoinPricingApi

    companion object {
        fun get(): RoBitCoinRestClient {
            return RoBitCoinRestClientImpl
        }

        fun reset() {
            RoBitCoinRestClientImpl.reset()
        }
    }
}

private object RoBitCoinRestClientImpl : RoBitCoinRestClient {

    override val identityServiceApi: BitCoinPricingApi
        get() {
            return getIdentityApi()
        }

    private var identity: BitCoinPricingApi? = null

    private fun getIdentityApi(): BitCoinPricingApi {
        return identity
                ?: nikeApiBuilder(BitCoinPricingApi::class.java).apply { identity = this }
    }

    private fun <T> nikeApiBuilder(clz: Class<T>, baseUrl: String = "https://api.nike.com"): T {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(clz)
    }

    fun reset() {
        identity = null
        client = null
    }

    private var client: OkHttpClient? = null

    private fun getHttpClient(): OkHttpClient? {
        //TODO
        return client
    }

    private val okHttpClient: OkHttpClient?
        get() {
            return getHttpClient()
        }
}
