package com.example.robitcoin.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.example.robitcoin.R
import com.example.robitcoin.eventbus.EventBased
import com.example.robitcoin.eventbus.EventBus
import com.example.robitcoin.coroutine.MainCoroutineScope

/**
 *Base class such that all children would be registereed to even bus messages.
 *
 * */
abstract class EventBasedActivity : AppCompatActivity(), EventBased {

    protected val uiScope = MainCoroutineScope()


    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(uiScope)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        EventBus.register(this)
        started()
    }

    @CallSuper
    override fun onPause() {
        EventBus.unregister(this)
        stopped()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.fade_out)
    }
}