package com.example.robitcoin

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.robitcoin.network.BlockChainRestClient
import com.example.robitcoin.utils.StethoUtils

class BlockChainApplication : Application(), LifecycleObserver {

    //private val backgroundScope: BackgroundCoroutineScope = BackgroundCoroutineScope()

    override fun onCreate() {
        super.onCreate()
        //PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, false)
        application = this
        //EnvironmentUtils.get().init()
        ProcessLifecycleOwner.get()
            .lifecycle.addObserver(this) // as of now, best for detecting if backgrounded,
        initCoroutine()
        initActivityCallbacks()
        initStetho()
    }


    private fun initStetho() {
        StethoUtils.init(this)
    }

    private fun initActivityCallbacks() {
    }

    private fun initCoroutine() {
//        ProcessLifecycleOwner.get().lifecycle.addObserver(backgroundScope)
//        backgroundScope.launch {
//            updateClientApi()
//        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var application: Application
        val APPLICATION by lazy { application }
    }
}