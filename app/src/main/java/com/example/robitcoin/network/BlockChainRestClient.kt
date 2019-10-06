package com.example.robitcoin.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


internal interface BlockChainRestClient {
    val identityServiceServiceApi: BlockChainServiceApi

    companion object {
        fun get(): BlockChainRestClient {
            return BlockChainRestClientImpl
        }

        //TODO CALL THIS
        fun reset() {
            BlockChainRestClientImpl.reset()
        }
    }
}

private object BlockChainRestClientImpl : BlockChainRestClient {

    override val identityServiceServiceApi: BlockChainServiceApi
        get() {
            return getIdentityApi()
        }

    private var identity: BlockChainServiceApi? = null

    private fun getIdentityApi(): BlockChainServiceApi {
        return identity
                ?: blockChainApiBuilder(BlockChainServiceApi::class.java).apply { identity = this }
    }

    private fun <T> blockChainApiBuilder(clz: Class<T>, baseUrl: String = "https://api.blockchain.info"): T {
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
