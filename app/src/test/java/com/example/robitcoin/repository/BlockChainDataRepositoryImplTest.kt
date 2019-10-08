package com.example.robitcoin.repository

import com.example.robitcoin.listener.BlockChainNetworkListener
import com.example.robitcoin.listener.BlockChainResultListener
import com.example.robitcoin.listener.OnSuccessRoBitcoinResult
import com.example.robitcoin.listener.RoBitcoinResult
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.model.BlockChainPopularStats
import com.example.robitcoin.network.BlockChainApi
import com.example.robitcoin.network.model.BlockChainGraphPlot
import com.example.robitcoin.network.model.BlockChainStats
import com.example.robitcoin.network.toBlockChainPopularStats
import com.example.robitcoin.preferences.BlockChainPreferenceHelper
import com.example.robitcoin.presentation.Chart
import com.google.common.truth.Truth
import io.reactivex.Observable
import org.junit.Test

class BlockChainDataRepositoryImplTest {

    @Test
    fun testPublishMarketPriceData() {
        val repo: BlockChainDataRepositoryImpl =
            BlockChainDataRepository.get() as BlockChainDataRepositoryImpl
        val success = OnSuccessRoBitcoinResult(BlockChainGraph())
        repo.blockChainPrefHelper = object : BlockChainPreferenceHelper {
            override fun getBlockChainMarketCap(): Observable<BlockChainGraph> {
                return Observable.empty()
            }

            override fun setBlockChainMarketCap(graph: BlockChainGraph) {
                //no-op
            }

            override fun getBlockChainMarketPrice(): Observable<BlockChainGraph> {
                return Observable.create { it.onNext(success.result) }
            }

            override fun setBlockChainMarketPrice(graph: BlockChainGraph) {
            }

            override fun nuke() {
            }

        }
        var testResult: RoBitcoinResult<BlockChainGraph>? = null
        repo.publishChartData(object : BlockChainResultListener<BlockChainGraph> {
            override fun onEvent(result: RoBitcoinResult<BlockChainGraph>) {
                testResult = result
            }
        }, onFailure = {

        })

        Thread.sleep(30) // wait for Schedulers.io()
        Truth.assertThat(testResult).isEqualTo(success)
    }

    @Test
    fun testPublishMarketCapData() {
        val repo: BlockChainDataRepositoryImpl =
            BlockChainDataRepository.get() as BlockChainDataRepositoryImpl
        val success = OnSuccessRoBitcoinResult(BlockChainGraph())
        repo.blockChainPrefHelper = object : BlockChainPreferenceHelper {
            override fun getBlockChainMarketCap(): Observable<BlockChainGraph> {
                return Observable.create { it.onNext(success.result) }
            }

            override fun setBlockChainMarketCap(graph: BlockChainGraph) {
                //no-op
            }

            override fun getBlockChainMarketPrice(): Observable<BlockChainGraph> {
                return Observable.empty()
            }

            override fun setBlockChainMarketPrice(graph: BlockChainGraph) {
            }

            override fun nuke() {
            }

        }
        var testResult: RoBitcoinResult<BlockChainGraph>? = null
        repo.publishChartData(object : BlockChainResultListener<BlockChainGraph> {
            override fun onEvent(result: RoBitcoinResult<BlockChainGraph>) {
                testResult = result
            }
        }, onFailure = {

        }, chart = Chart.MARKET_CAP)

        Thread.sleep(30) // wait for Schedulers.io()
        Truth.assertThat(testResult).isEqualTo(success)
    }

    @Test
    fun testPublishStatsData() {
        val repo: BlockChainDataRepositoryImpl =
            BlockChainDataRepository.get() as BlockChainDataRepositoryImpl

        var testResult: RoBitcoinResult<BlockChainPopularStats>? = null
        val success = BlockChainStats(
            "1212", "133434", "22",
            "231", "12", "88888", "323", "23",
            "23", "32", "232", "23", "3232",
            "3", "difficult", "232", "2323", "232",
            "323", "sasa"
        )

        repo.blockChainApi = object : BlockChainApi {
            override fun getChartData(
                networkListener: BlockChainNetworkListener<BlockChainGraphPlot>,
                chartType: String
            ) {
            }

            override fun getBlockChainStats(networkListener: BlockChainNetworkListener<BlockChainStats>) {
                networkListener.onSuccess(success)
            }
        }

        repo.publishStatsData(object : BlockChainResultListener<BlockChainPopularStats> {
            override fun onEvent(result: RoBitcoinResult<BlockChainPopularStats>) {
                testResult = result
            }

        }) {

        }

        Truth.assertThat(testResult)
            .isEqualTo(OnSuccessRoBitcoinResult(success.toBlockChainPopularStats()))
    }
}