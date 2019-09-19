package com.example.robitcoin

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

object RoBitCoinApplicationState : LifecycleObserver {

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this) // as of now, best for detecting if backgrounded, has a 700 ms timeout set
    }

    var isBackgrounded: Boolean = true

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        isBackgrounded = true
        reset()
    }

    private fun reset() {
       //no-op
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        isBackgrounded = false
    }
}
