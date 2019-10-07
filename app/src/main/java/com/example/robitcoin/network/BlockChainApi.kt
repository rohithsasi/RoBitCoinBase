package com.example.robitcoin.network

import android.util.Log
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

private val BLOCK_CHAIN_SERVICE_API: BlockChainServiceApi by lazy {
    BlockChainRestClient.get().identityServiceServiceApi
}

private val BLOCK_CHAIN_DISPOSABLE: BlockChainServiceApi by lazy {
    BlockChainRestClient.get().identityServiceServiceApi
}

private object BlockChainApiImpl : BlockChainApi {
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

    override fun getChartData(
        networkListener: BlockChainNetworkListener<BlockChainGraphPlot>,
        chartType: String
    ) {
        //TODO destroy these
        val disposable =BLOCK_CHAIN_SERVICE_API.getCharts(
            chartType,
            "6months",
            "8hours"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                Log.d("BITCOIN  ", "Success ")
                Log.d("BITCOIN  ", "Success")
                Log.d("BITCOIN  ", "Success")
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
}



