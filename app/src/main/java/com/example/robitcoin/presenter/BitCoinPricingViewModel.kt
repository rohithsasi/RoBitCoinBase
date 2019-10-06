package com.example.robitcoin.presenter

import com.example.robitcoin.eventbus.EventBus
import com.example.robitcoin.listener.BigfootResult
import com.example.robitcoin.listener.BigfootResultListener
import com.example.robitcoin.listener.OnFailureBigfootResult
import com.example.robitcoin.listener.OnSuccessBigfootResult
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.Pricing
import com.example.robitcoin.repository.FetchProfileRepository

class BitCoinPricingViewModel {

    private val repository by lazy { FetchProfileRepository.get() }

    fun getUserData(){
        repository.getProfile(object : BigfootResultListener<Pricing> {
            override fun onEvent(result: BigfootResult<Pricing>) {

                when (result) {
                    is OnSuccessBigfootResult -> EventBus.post(
                        FetchProfileActionResult(
                            result.result
                        )
                    )
                    is OnFailureBigfootResult ->  EventBus.post(
                        FetchProfileActionResult(
                            null
                        )
                    )
                }
            }

        })
    }

    fun getBlockChainStats(){
        repository.getBlockChainStats(object :BigfootResultListener<BlockChainPopularStats>{
            override fun onEvent(result: BigfootResult<BlockChainPopularStats>) {
                when (result) {
                    is OnSuccessBigfootResult -> EventBus.post(
                        FetchStatsActionResult(
                            result.result
                        )
                    )
                    is OnFailureBigfootResult ->  EventBus.post(
                        FetchStatsActionResult(
                            null
                        )
                    )
                }
            }

        })
    }
}