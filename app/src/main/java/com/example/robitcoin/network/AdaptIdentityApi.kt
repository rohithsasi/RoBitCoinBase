package com.example.robitcoin.network

import android.util.Log
import com.example.robitcoin.listener.BitCoinNetworkListener
import com.example.robitcoin.network.model.BitCoinPriceing
import com.example.robitcoin.network.model.BlockChainStats
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface AdaptIdentityApi {
    fun getChartData(networListener: BitCoinNetworkListener<BitCoinPriceing>, chartType :String ="market-price")
    fun getBlockChainStats(networkListener: BitCoinNetworkListener<BlockChainStats>)

    companion object {
        fun get(): AdaptIdentityApi {
            return AdaptIdentityApiImpl
        }
    }
}

private val identityServiceApi: BitCoinPricingApi by lazy {
    RoBitCoinRestClient.get().identityServiceApi
}

private object AdaptIdentityApiImpl : AdaptIdentityApi {
    override fun getBlockChainStats(networkListener: BitCoinNetworkListener<BlockChainStats>) {

        identityServiceApi.getBlockChainStats()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (it.isSuccessful)
                {
                    it.body()?.let { it1 -> networkListener.onSuccess(it1) } ?: let {
                        //TODO
                        //throw error bruh!!!!!!
                    }
                }
                else{
                    //TODO
                    //throw error bruh!!!!!!
                }


            }, {

                //TODO
                //throw error bruh!!!!!!
            })

    }

    override fun getChartData(networListener: BitCoinNetworkListener<BitCoinPriceing>, chartType :String) {
       identityServiceApi.getCharts(
            chartType,
            "6months",
            "8hours"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                Log.d("BITCOIN  ", "Success ")
                Log.d("BITCOIN  ", "Success")
                Log.d("BITCOIN  ", "Success")
                it.body()?.let { it1 -> networListener.onSuccess(it1) } ?: let {
                    //TODO
                    //throw error bruh!!!!!!
                }
            } else {
                //throw error bruh!!!!!!
                //TODO
                Log.d(" BITCOIN ERROR  ", "ERROR ${it.code()}")
            }
        }, {
            //TODO
            //throw error bruh!!!!!!
            Log.d(" BITCOIN ERROR  ", "${it.message}")
        })
    }

    fun  handleError(){
        //TODO Extension function like bigfootpaireddeviceapi
        //Unit testable
        //Impelement!

    }


    fun  handleResponse(){
        //TODO
        //UnitTestable

    }
}



