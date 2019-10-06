package com.example.robitcoin.network

import androidx.test.filters.LargeTest
import com.example.robitcoin.listener.BlockChainNetworkListener
import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.test.util.suspendCoroutineWithTimeout
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BlockChainApiImplTest {

    private val blockChainApi: BlockChainApi = BlockChainApi.get()

    @Before
    fun setUp() {
    }

    @Test
    fun getStatsApiTest(){
        runBlocking {
            val networkResponse = getStatsData()
            Truth.assertThat(networkResponse).isNotNull()
            //SINCE the stats keep changing dynamically for now I am going test against of the data
            //the apps needs to load the Ui is populated or now
            Truth.assertThat(networkResponse.marketPrice).isNotNull()
            Truth.assertThat(networkResponse.blocksMined).isNotNull()
            Truth.assertThat(networkResponse.minutedBtwBlocks).isNotNull()
            Truth.assertThat(networkResponse.btcMined).isNotNull()
            Truth.assertThat(networkResponse.marketPrice).isNotNull()
            Truth.assertThat(networkResponse.tradeVolumeUsd).isNotNull()
            Truth.assertThat(networkResponse.tradeVolumeBtc).isNotNull()
            Truth.assertThat(networkResponse.totalFeesBtc).isNotNull()
            Truth.assertThat(networkResponse.tx).isNotNull()
            Truth.assertThat(networkResponse.transactionVolume).isNotNull()
            Truth.assertThat(networkResponse.minersRevenue).isNotNull()
        }
    }


    @Test
    fun getMarketPriceGraphApiTest() {
        runBlocking {
            val networkResponse = getChartData("market-price")
            Truth.assertThat(networkResponse).isNotNull()
            Truth.assertThat(networkResponse.name).isEqualTo("Market Price (USD)")
            Truth.assertThat(networkResponse.unit).isEqualTo("USD")
            Truth.assertThat(networkResponse.period).isEqualTo("day")
            Truth.assertThat(networkResponse.description).isEqualTo("Average USD market price across major bitcoin exchanges.")
            Truth.assertThat(networkResponse.values).isNotNull()
            Truth.assertThat(networkResponse.values).isNotEmpty()
        }
    }

    @Test
    fun getMarketCapGraphApiTest() {
        runBlocking {
            val networkResponse = getChartData("market-cap")
            Truth.assertThat(networkResponse).isNotNull()
            Truth.assertThat(networkResponse.name).isEqualTo("Market Capitalization")
            Truth.assertThat(networkResponse.unit).isEqualTo("USD")
            Truth.assertThat(networkResponse.period).isEqualTo("day")
            Truth.assertThat(networkResponse.description).isEqualTo("The total USD value of bitcoin supply in circulation, as calculated by the daily average market price across major exchanges.")
            Truth.assertThat(networkResponse.values).isNotNull()
            Truth.assertThat(networkResponse.values).isNotEmpty()
        }
    }

    private suspend fun getChartData(chartType : String) :BlockChainGraphPlot {
        return suspendCoroutineWithTimeout(5_000) {
            blockChainApi.getChartData(object : BlockChainNetworkListener<BlockChainGraphPlot>{
                override fun onSuccess(response: BlockChainGraphPlot) {

                    it.resumeWith(Result.success(response))
                }

                override fun onFailure(throwable: Throwable) {
                    it.resumeWith(Result.failure(throwable))
                }

            },chartType)
        }
    }

    private suspend fun getStatsData() :BlockChainStats {
        return suspendCoroutineWithTimeout(5_000) {
            blockChainApi.getBlockChainStats(object : BlockChainNetworkListener<BlockChainStats> {
                override fun onSuccess(response: BlockChainStats) {
                    it.resumeWith(Result.success(response))
                }

                override fun onFailure(throwable: Throwable)
                {it.resumeWith(Result.failure(throwable))
                }
            })
        }
    }
}