package com.example.robitcoin.network

import com.example.robitcoin.listener.BlockChainNetworkListener
import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface BlockChainApi {
    fun getChartData(
        networkListener: BlockChainNetworkListener<BlockChainGraphPlot>,
        chartType: String = "market-price"
    )

    fun getBlockChainStats(networkListener: BlockChainNetworkListener<BlockChainStats>)

    companion object {
        fun get(): BlockChainApi {
            return BlockChainApiImpl
        }
    }
}

/**
 * Rest client
 */
private val BLOCK_CHAIN_SERVICE_API: BlockChainServiceApi by lazy {
    BlockChainRestClient.get().identityServiceServiceApi
}

/**
 * Makes network requests to the block chain apis and returns a result as an observable. The result is communicated
 * back to the repository layer (up the chain) using a network result listener (simple interface callbacks)
 */
private object BlockChainApiImpl : BlockChainApi {

    /**
     * Get stats api call
     */
    override fun getBlockChainStats(networkListener: BlockChainNetworkListener<BlockChainStats>) {
       //todo destroy these
        val disposable=  BLOCK_CHAIN_SERVICE_API.getBlockChainStats()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (it.isSuccessful) {
                    it.body()?.let { it1 -> networkListener.onSuccess(it1) } ?: let {
                        networkListener.onFailure(Throwable("Netowork Failure"))
                    }
                } else {
                    networkListener.onFailure(Throwable("Netowork Failure"))
                }

            }, {
                networkListener.onFailure(it)
            })
    }

    /**
     * Get chart data base on the chart type
     */
    override fun getChartData(
        networkListener: BlockChainNetworkListener<BlockChainGraphPlot>,
        chartType: String
    ) {
        //TODO destroy the disposable on destroy
        val disposable =BLOCK_CHAIN_SERVICE_API.getCharts(
            chartType,
            "6months",
            "8hours"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                it.body()?.let { it1 -> networkListener.onSuccess(it1) } ?: let {
                    networkListener.onFailure(Throwable("Netowork Failure"))
                }
            } else {
                networkListener.onFailure(Throwable("Network Failure"))
            }
        }, {
            networkListener.onFailure(it)
        })
    }
}



