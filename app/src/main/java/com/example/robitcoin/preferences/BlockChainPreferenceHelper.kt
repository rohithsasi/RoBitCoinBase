package com.example.robitcoin.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.robitcoin.BlockChainApplication
import com.example.robitcoin.model.BlockChainGraph
import com.google.gson.Gson
import io.reactivex.Observable

private const val PREF_MARKET_PRICE_DATA = "PREF_MARKET_PRICE_DATA"
private const val PREF_MARKET_CAP_DATA = "PREF_MARKET_CAP_DATA"

private val sharedPreferences: SharedPreferences by lazy {
    BlockChainApplication.APPLICATION.getSharedPreferences(
        "block_chain_preferences",
        Context.MODE_PRIVATE
    )
}

interface BlockChainPreferenceHelper {
    fun getBlockChainMarketPrice(): Observable<BlockChainGraph>
    fun setBlockChainMarketPrice(graph: BlockChainGraph)

    fun getBlockChainMarketCap(): Observable<BlockChainGraph>
    fun setBlockChainMarketCap(graph: BlockChainGraph)

    fun nuke()

    companion object {
        fun get(): BlockChainPreferenceHelper {
            return BlockChainPreferenceHelperImpl
        }
    }
}

private object BlockChainPreferenceHelperImpl : BlockChainPreferenceHelper {
    override fun getBlockChainMarketCap(): Observable<BlockChainGraph> {
        return defer {
            return@defer sharedPreferences.getString(PREF_MARKET_CAP_DATA, null)?.let { data ->
                Gson().fromJson(data, BlockChainGraph::class.java)
            } ?: return@defer BlockChainGraph()
        }
    }

    override fun setBlockChainMarketCap(graph: BlockChainGraph) {
        sharedPreferences.edit {
            putString(PREF_MARKET_CAP_DATA, Gson().toJson(graph))
        }
    }

    override fun getBlockChainMarketPrice(): Observable<BlockChainGraph> {
        return defer {
            return@defer sharedPreferences.getString(PREF_MARKET_PRICE_DATA, null)?.let { data ->
                Gson().fromJson(data, BlockChainGraph::class.java)
            } ?: return@defer BlockChainGraph()
        }
    }

    override fun setBlockChainMarketPrice(graph: BlockChainGraph) {
        sharedPreferences.edit {
            putString(PREF_MARKET_PRICE_DATA, Gson().toJson(graph))
        }
    }

    override fun nuke() {
        sharedPreferences.edit().clear().apply()
    }

    inline fun <reified T> defer(crossinline block: () -> T): Observable<T> {
        return Observable.defer {
            Observable.just(block())
        }
    }
}

