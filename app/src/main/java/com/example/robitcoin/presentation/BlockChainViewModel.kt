package com.example.robitcoin.presentation

import com.example.robitcoin.eventbus.EventBus
import com.example.robitcoin.listener.RoBitcoinResult
import com.example.robitcoin.listener.BlockChainResultListener
import com.example.robitcoin.listener.OnFailureRoBitcoinResult
import com.example.robitcoin.listener.OnSuccessRoBitcoinResult
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.repository.BlockChainDataRepository

class BlockChainViewModel {

    private val repository by lazy { BlockChainDataRepository.get() }

    fun fetchBlockChainPricingGraph(chart: Chart){
        getGraphData(chart)
    }

    fun fetchBlockChainMarketCapGraph(chart: Chart){
        getGraphData(chart)
    }


    fun fetchBlockChainStats(){
        repository.getBlockChainStats(object :BlockChainResultListener<BlockChainPopularStats>{
            override fun onEvent(result: RoBitcoinResult<BlockChainPopularStats>) {
                when (result) {
                    is OnSuccessRoBitcoinResult -> EventBus.post(
                        FetchBlockChainStatsActionResult(
                            result.result
                        )
                    )
                    is OnFailureRoBitcoinResult ->  EventBus.post(
                        FetchBlockChainStatsActionResult(
                            null
                        )
                    )
                }
            }

        })
    }

    private fun getGraphData(chart: Chart){
        repository.getGraphData(object : BlockChainResultListener<BlockChainGraph> {
            override fun onEvent(result: RoBitcoinResult<BlockChainGraph>) {

                when (result) {
                    is OnSuccessRoBitcoinResult -> EventBus.post(
                        FetchGraphDataActionResult(
                            result.result
                        )
                    )
                    is OnFailureRoBitcoinResult ->  EventBus.post(
                        FetchGraphDataActionResult(
                            null
                        )
                    )
                }
            }
        },chart)
    }
}


enum class Chart(val type:String){
    MARKET_PRICE("market-price"),
    MARKETY_CAP("market-cap")
}