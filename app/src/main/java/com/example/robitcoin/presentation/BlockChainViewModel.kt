package com.example.robitcoin.presentation

import com.example.robitcoin.eventbus.EventBus
import com.example.robitcoin.listener.BigfootResult
import com.example.robitcoin.listener.BlockChainResultListener
import com.example.robitcoin.listener.OnFailureBigfootResult
import com.example.robitcoin.listener.OnSuccessBigfootResult
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.repository.BlockChainDataRepository

class BlockChainViewModel {

    private val repository by lazy { BlockChainDataRepository.get() }

    fun fetchBlockChainGraphPlot(){
        repository.getGraphData(object : BlockChainResultListener<BlockChainGraph> {
            override fun onEvent(result: BigfootResult<BlockChainGraph>) {

                when (result) {
                    is OnSuccessBigfootResult -> EventBus.post(
                        FetchGraphDataActionResult(
                            result.result
                        )
                    )
                    is OnFailureBigfootResult ->  EventBus.post(
                        FetchGraphDataActionResult(
                            null
                        )
                    )
                }
            }

        })
    }

    fun fetchBlockChainStats(){
        repository.getBlockChainStats(object :BlockChainResultListener<BlockChainPopularStats>{
            override fun onEvent(result: BigfootResult<BlockChainPopularStats>) {
                when (result) {
                    is OnSuccessBigfootResult -> EventBus.post(
                        FetchBlockChainStatsActionResult(
                            result.result
                        )
                    )
                    is OnFailureBigfootResult ->  EventBus.post(
                        FetchBlockChainStatsActionResult(
                            null
                        )
                    )
                }
            }

        })
    }
}