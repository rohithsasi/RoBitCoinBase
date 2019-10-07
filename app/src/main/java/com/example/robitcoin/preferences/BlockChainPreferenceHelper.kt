package com.example.robitcoin.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.robitcoin.BlockChainApplication
import com.example.robitcoin.model.BlockChainGraph
import com.google.gson.Gson
import io.reactivex.Observable

private const val PREF_GRAPH_DATA = "PREF_GRAPH_DATA"

private val sharedPreferences: SharedPreferences by lazy {
    BlockChainApplication.APPLICATION.getSharedPreferences(
        "block_chain_preferences",
        Context.MODE_PRIVATE
    )
}

interface BlockChainPreferenceHelper {
    fun getBlockChainGraph(): Observable<BlockChainGraph>
    fun setBlockChainGraph(graph: BlockChainGraph)

    fun nuke()

    companion object {
        fun get(): BlockChainPreferenceHelper {
            return BlockChainPreferenceHelperImpl
        }
    }
}

private object BlockChainPreferenceHelperImpl : BlockChainPreferenceHelper {

    override fun getBlockChainGraph(): Observable<BlockChainGraph> {
        return defer {
            return@defer sharedPreferences.getString(PREF_GRAPH_DATA, null)?.let { data ->
                Gson().fromJson(data, BlockChainGraph::class.java)
            } ?: return@defer BlockChainGraph()
        }
    }

    override fun setBlockChainGraph(graph: BlockChainGraph) {
        sharedPreferences.edit {
            putString(PREF_GRAPH_DATA, Gson().toJson(graph))
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

