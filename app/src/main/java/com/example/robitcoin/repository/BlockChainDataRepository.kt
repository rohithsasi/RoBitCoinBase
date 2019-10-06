package com.example.robitcoin.repository

import com.example.robitcoin.listener.*
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.network.BlockChainApi
import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.network.toGraphData
import com.example.robitcoin.network.toBlockChainPopularStats
import com.example.robitcoin.preferences.BlockChainPreferenceHelper

interface BlockChainDataRepository {
    fun getGraphData(adaptResultListener: BlockChainResultListener<BlockChainGraph>)
    fun getBlockChainStats(blockChainResultListener: BlockChainResultListener<BlockChainPopularStats>)

    companion object {
        fun get(): BlockChainDataRepository {
            return BlockChainDataRepositoryImpl
        }
    }
}

private object BlockChainDataRepositoryImpl : BlockChainDataRepository {
    override fun getBlockChainStats(blockChainResultListener: BlockChainResultListener<BlockChainPopularStats>) {
        //TODO
        //Implement caching like below
        blockChainApi.getBlockChainStats(object : BlockChainNetworkListener<BlockChainStats>{
            override fun onSuccess(response: BlockChainStats) {
                response.toBlockChainPopularStats().run {
                    blockChainResultListener onSuccess this
                }
            }

            override fun onFailure(throwable: Throwable) {

            }

        })
    }

    private val blockChainApi: BlockChainApi by lazy { BlockChainApi.get() }

    override fun getGraphData(blockChainResultListener: BlockChainResultListener<BlockChainGraph>) {

        publishChartData(blockChainResultListener) {
            // no-op on failure
        }
        blockChainApi.getChartData(object : BlockChainNetworkListener<BlockChainGraphPlot> {
            override fun onSuccess(response: BlockChainGraphPlot) {
                response.toGraphData().run {
                    BlockChainPreferenceHelper.get().setBlockChainGraph(this) // I am using the most simplest purpos but we could cache it to database
                    publishChartData(blockChainResultListener){
                        blockChainResultListener onFailure it
                    }
                }
            }

            override fun onFailure(throwable: Throwable) {
            }
        })
    }

    private fun publishChartData(adaptResultListener:  BlockChainResultListener<BlockChainGraph>, onFailure: (Throwable) -> Unit) {
        BlockChainPreferenceHelper.get().getBlockChainGraph(object : BlockChainResultListener<BlockChainGraph> {
            override fun onEvent(result: BigfootResult<BlockChainGraph>) {
                when (result) {
                    is OnSuccessBigfootResult -> adaptResultListener onSuccess result.result
                    is OnFailureBigfootResult -> onFailure(result.throwable)
                }
            }

        })
    }
}