package com.example.robitcoin.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.example.robitcoin.RoBitCoinApplication
import com.example.robitcoin.listener.BigfootResultListener
import com.example.robitcoin.listener.onFailure
import com.example.robitcoin.listener.onSuccess
import com.example.robitcoin.model.Pricing
import com.example.robitcoin.network.BitCoinResultListener
import com.google.gson.Gson

import com.nike.adapt.util.preferences.UserDoesNotExistInCacheException
private const val PREF_BIGFOOT_USER = "PREF_BIGFOOT_USER"


private val sharedPreferences: SharedPreferences by lazy {
    RoBitCoinApplication.APPLICATION.getSharedPreferences("adapt_preferences", Context.MODE_PRIVATE)
}

interface AdaptPreferenceHelper {
    fun getBigfootUser(adaptResult: BigfootResultListener<Pricing>)
    fun getBigfootUser(): Pricing?
    fun setBigfootUser(bigfootUser: Pricing)

    fun nuke()

    companion object {
        fun get(): AdaptPreferenceHelper {
            return AdaptPreferenceHelperImpl
        }
    }
}

private object AdaptPreferenceHelperImpl : AdaptPreferenceHelper {

    override fun getBigfootUser(adaptResult: BigfootResultListener<Pricing>) {

        //TODO Check if this need executor ,IO or maybe rx java
        //retrieve {
            sharedPreferences.getString(PREF_BIGFOOT_USER, null)?.let {
                Gson().fromJson(it, Pricing::class.java)
            }?.run {
                adaptResult onSuccess this }
                    ?: let {
                        adaptResult onFailure UserDoesNotExistInCacheException() }
        //}
    }

    @WorkerThread
    override fun getBigfootUser(): Pricing? {
        return sharedPreferences.getString(PREF_BIGFOOT_USER, null)?.let {
            Gson().fromJson(it, Pricing::class.java)
        }
    }

    override fun setBigfootUser(bigfootUser: Pricing) {
        sharedPreferences.edit {
            putString(PREF_BIGFOOT_USER, Gson().toJson(bigfootUser))
        }
    }

    override fun nuke() {
        sharedPreferences.edit().clear().apply()
    }
}

private fun retrieve(retrieve: () -> Unit) {

    //TODO Check if this need executor ,IO or maybe rx java
    //AdaptExecutor.IO.execute(retrieve)
}
