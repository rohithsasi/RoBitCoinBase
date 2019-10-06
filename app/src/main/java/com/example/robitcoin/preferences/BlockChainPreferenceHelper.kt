package com.example.robitcoin.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.example.robitcoin.BlockChainApplication
import com.example.robitcoin.listener.BlockChainResultListener
import com.example.robitcoin.listener.onFailure
import com.example.robitcoin.listener.onSuccess
import com.example.robitcoin.model.BlockChainGraph
import com.google.gson.Gson

private const val PREF_GRAPH_DATA = "PREF_GRAPH_DATA"


private val sharedPreferences: SharedPreferences by lazy {
    BlockChainApplication.APPLICATION.getSharedPreferences("adapt_preferences", Context.MODE_PRIVATE)
}

interface BlockChainPreferenceHelper {
    fun getBlockChainGraph(blockChainResult: BlockChainResultListener<BlockChainGraph>)
    fun getBlockChainGraph(): BlockChainGraph?
    fun setBlockChainGraph(bigfootUser: BlockChainGraph)

    fun nuke()

    companion object {
        fun get(): BlockChainPreferenceHelper {
            return BlockChainPreferenceHelperImpl
        }
    }
}

private object BlockChainPreferenceHelperImpl : BlockChainPreferenceHelper {

    override fun getBlockChainGraph(blockChainResult: BlockChainResultListener<BlockChainGraph>) {

        //TODO Check if this need executor ,IO or maybe rx java
        //retrieve {
        sharedPreferences.getString(PREF_GRAPH_DATA, null)?.let {
            Gson().fromJson(it, BlockChainGraph::class.java)
        }?.run {
            blockChainResult onSuccess this
        }
            ?: let {
                blockChainResult onFailure UserDoesNotExistInCacheException()
            }
        //}
    }

    @WorkerThread
    override fun getBlockChainGraph(): BlockChainGraph? {
        return sharedPreferences.getString(PREF_GRAPH_DATA, null)?.let {
            Gson().fromJson(it, BlockChainGraph::class.java)
        }
    }

    override fun setBlockChainGraph(bigfootUser: BlockChainGraph) {
        sharedPreferences.edit {
            putString(PREF_GRAPH_DATA, Gson().toJson(bigfootUser))
        }
    }

    override fun nuke() {
        //TODO IMPLEMET NUKE
        sharedPreferences.edit().clear().apply()
    }
}

private fun retrieve(retrieve: () -> Unit) {
    //TODO Check if this need executor ,IO or maybe rx java
    //AdaptExecutor.IO.execute(retrieve)
}
