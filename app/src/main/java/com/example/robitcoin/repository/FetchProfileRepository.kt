package com.example.robitcoin.repository

import com.example.robitcoin.listener.*
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.Pricing
import com.example.robitcoin.network.AdaptIdentityApi
import com.example.robitcoin.network.model.BitCoinPriceing
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.network.toGraphData
import com.example.robitcoin.network.toBlockChainPopularStats
import com.example.robitcoin.preferences.AdaptPreferenceHelper

interface FetchProfileRepository {
    fun getProfile(adaptResultListener: BigfootResultListener<Pricing>)
    fun getBlockChainStats(adaptResultListener: BigfootResultListener<BlockChainPopularStats>)

    companion object {
        fun get(): FetchProfileRepository {
            return FetchProfileRepositoryImpl
        }
    }
}

private object FetchProfileRepositoryImpl : FetchProfileRepository {
    override fun getBlockChainStats(adaptResultListener: BigfootResultListener<BlockChainPopularStats>) {

        //TODO
        //Implement caching like below
        identityApi.getBlockChainStats(object : BitCoinNetworkListener<BlockChainStats>{
            override fun onSuccess(response: BlockChainStats) {
                response.toBlockChainPopularStats().run {

                    adaptResultListener onSuccess this
                }
            }

            override fun onFailure(throwable: Throwable) {

            }

        })
    }

    private val identityApi: AdaptIdentityApi by lazy { AdaptIdentityApi.get() }

    override fun getProfile(adaptResultListener: BigfootResultListener<Pricing>) {

        publishUser(adaptResultListener) {
            // no-op on failure
        }
        identityApi.getChartData(object : BitCoinNetworkListener<BitCoinPriceing> {
            override fun onSuccess(response: BitCoinPriceing) {
                response.toGraphData().run {
                    AdaptPreferenceHelper.get().setBigfootUser(this) // I am using the most simplest purpos but we could cache it to database
                    publishUser(adaptResultListener){
                        adaptResultListener onFailure it
                    }
                }
            }

            override fun onFailure(throwable: Throwable) {
            }

        })
    }

    private fun publishUser(adaptResultListener:  BigfootResultListener<Pricing>, onFailure: (Throwable) -> Unit) {

        AdaptPreferenceHelper.get().getBigfootUser(object : BigfootResultListener<Pricing> {
            override fun onEvent(result: BigfootResult<Pricing>) {
                when (result) {
                    is OnSuccessBigfootResult -> adaptResultListener onSuccess result.result
                    is OnFailureBigfootResult -> onFailure(result.throwable)
                }
            }

        })
    }
}