package com.example.robitcoin.base

import androidx.fragment.app.Fragment
import com.example.robitcoin.eventbus.EventBased
import com.example.robitcoin.eventbus.EventBus

/**
 * Base class such that all children would be registereed to even bus messages
 */
abstract class EventBasedFragment : Fragment(), EventBased {

    override fun onResume() {
        super.onResume()
        EventBus.register(this)
        started()
    }

    /**
     * Cannot use, instead override stopped()
     */
    override fun onPause() {
        EventBus.unregister(this)
        stopped()
        super.onPause()
    }
}
