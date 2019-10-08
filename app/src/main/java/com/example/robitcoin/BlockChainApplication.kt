package com.example.robitcoin

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.robitcoin.utils.StethoUtils

class BlockChainApplication : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        application = this
        ProcessLifecycleOwner.get()
            .lifecycle.addObserver(this) // as of now, best for detecting if backgrounded,
        initStetho()
    }

    private fun initStetho() {
        StethoUtils.init(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var application: Application
        val APPLICATION by lazy { application }
    }
}