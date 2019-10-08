package com.example.robitcoin.presentation

import com.example.robitcoin.eventbus.EventBus
import com.example.robitcoin.listener.RoBitcoinResult
import com.example.robitcoin.listener.BlockChainResultListener
import com.example.robitcoin.listener.OnFailureRoBitcoinResult
import com.example.robitcoin.listener.OnSuccessRoBitcoinResult
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.repository.BlockChainDataRepository


/**
 * The presentation layer that acquires the data from the lower repository layer and emmits the processed ui data
 * to the Views with the help of an event bus. Now its called a view model as its the architecture is more representative
 * of MVVVM as opposed to MVP. Just like MVVP this presentation layer hold no hard reference to the Ui layer or the botton networking layer
 * This layer interact with network layer via a repository.
 */
class BlockChainViewModel {

    /**
     * The only reference this class hols is that of the repository.
     */
    private val repository by lazy { BlockChainDataRepository.get() }

    /**
     * Fetches the block market price and updates the corresponding fragment.
     */
    fun fetchBlockChainPricingGraph(){
        getGraphData(Chart.MARKET_PRICE)
    }

    /**
     * Fetches the block market cap and updates the corresponding fragment.
     */
    fun fetchBlockChainMarketCapGraph(){
        getGraphData(Chart.MARKET_CAP)
    }

    /**
     * Fetches the block chain stats and updates the corresponding fragment.
     */
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
    MARKET_CAP("market-cap")
}