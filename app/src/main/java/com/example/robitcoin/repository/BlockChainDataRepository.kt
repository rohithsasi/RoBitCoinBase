package com.example.robitcoin.repository

import androidx.annotation.VisibleForTesting
import com.example.robitcoin.listener.BlockChainNetworkListener
import com.example.robitcoin.listener.BlockChainResultListener
import com.example.robitcoin.listener.onFailure
import com.example.robitcoin.listener.onSuccess
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.network.BlockChainApi
import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.network.toBlockChainPopularStats
import com.example.robitcoin.network.toGraphData
import com.example.robitcoin.preferences.BlockChainPreferenceHelper
import com.example.robitcoin.presentation.Chart
import io.reactivex.schedulers.Schedulers

interface BlockChainDataRepository {
    fun getGraphData(resultListener: BlockChainResultListener<BlockChainGraph>,chart: Chart)
    fun getBlockChainStats(blockChainResultListener: BlockChainResultListener<BlockChainPopularStats>)

    companion object {
        fun get(): BlockChainDataRepository {
            return BlockChainDataRepositoryImpl
        }
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal object BlockChainDataRepositoryImpl : BlockChainDataRepository {
    override fun getBlockChainStats(blockChainResultListener: BlockChainResultListener<BlockChainPopularStats>) {
        publishStatsData(blockChainResultListener) {
            blockChainResultListener onFailure it
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var blockChainApi: BlockChainApi = BlockChainApi.get()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var blockChainPrefHelper = BlockChainPreferenceHelper.get()

    override fun getGraphData(resultListener: BlockChainResultListener<BlockChainGraph>,chart: Chart) {

        publishChartData(resultListener,chart) {
            // no-op on failure
        }
        blockChainApi.getChartData(object : BlockChainNetworkListener<BlockChainGraphPlot> {
            override fun onSuccess(response: BlockChainGraphPlot) {
                response.toGraphData().run {
                    
                    if(chart==Chart.MARKET_PRICE) {
                        BlockChainPreferenceHelper.get().setBlockChainMarketPrice(this)
                    }
                    else{
                        BlockChainPreferenceHelper.get().setBlockChainMarketCap(this)
                    }
                    publishChartData(resultListener) {
                        resultListener onFailure it
                    }
                }
            }

            override fun onFailure(throwable: Throwable) {
            }
        },chart.type)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun publishStatsData(resultListener: BlockChainResultListener<BlockChainPopularStats>,onFailure: (Throwable) -> Unit) {
        blockChainApi.getBlockChainStats(object : BlockChainNetworkListener<BlockChainStats> {
            override fun onSuccess(response: BlockChainStats) {

                response.toBlockChainPopularStats().run {
                    resultListener onSuccess this
                }
            }

            override fun onFailure(throwable: Throwable) {
                resultListener onFailure throwable
            }

        })

    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun publishChartData(resultListener: BlockChainResultListener<BlockChainGraph>, chart: Chart =Chart.MARKET_PRICE, onFailure: (Throwable) -> Unit) {
            var observable = if(chart ==Chart.MARKET_PRICE)blockChainPrefHelper.getBlockChainMarketPrice() 
            else blockChainPrefHelper.getBlockChainMarketCap()
            
            val disposable =observable.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io()).subscribe {
            resultListener onSuccess it
        }
    }
}