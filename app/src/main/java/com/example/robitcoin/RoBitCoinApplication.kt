package com.example.robitcoin

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.robitcoin.utils.StethoUtils
import com.nike.adapt.coroutine.BackgroundCoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class RoBitCoinApplication: Application(),LifecycleObserver {

    private val backgroundScope: BackgroundCoroutineScope = BackgroundCoroutineScope()
    private val AKAMAI_BOT_HEADER = "X-acf-sensor-data"

    override fun onCreate() {
        super.onCreate()
        //PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, false)
        application = this
        //EnvironmentUtils.get().init()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this) // as of now, best for detecting if backgrounded,
        initCoroutine()
        initActivityCallbacks()
        initStetho()
        initModules()
        initLibraries()
    }
    private fun initModules() {
      //TODO
    }

    private fun initLibraries() {

     //TODO
    }

    private fun initStetho() {
        StethoUtils.init(this)
    }

    private fun initActivityCallbacks() {
    }

    fun updateClientApi() {
       // clientApi.firmwareApi = EnvironmentUtils.get().getTestBigfootPreferenceHelper().firmwareApi
        //clientApi.deviceApi = EnvironmentUtils.get().getTestBigfootPreferenceHelper().deviceApi
    }

    private fun initCoroutine() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(backgroundScope)
        backgroundScope.launch {
            updateClientApi()
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var application: Application
        val APPLICATION by lazy { application }
        //Not needed
        val SESSION_ID: String by lazy(LazyThreadSafetyMode.NONE) { UUID.randomUUID().toString() }
    }
}