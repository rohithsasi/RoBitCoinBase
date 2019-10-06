package com.example.robitcoin.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson



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

    private fun <T> nikeApiBuilder(clz: Class<T>, baseUrl: String = "https://api.blockchain.info"): T {

//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        okHttpClient?.interceptors()?.add(interceptor)
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
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
        return OkHttpClient.Builder().build()
    }

    private val okHttpClient: OkHttpClient?
        get() {
            return getHttpClient()
        }
}
