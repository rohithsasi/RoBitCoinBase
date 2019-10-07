package com.example.robitcoin.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.robitcoin.eventbus.EventBased
import com.example.robitcoin.eventbus.EventBus

abstract class EventBasedFragment : Fragment(), EventBased {


//    //TODO REMOVE
//    protected val uiScope = MainCoroutineScope()
//
//    @Suppress("LeakingThis")
//    private val eventTag: String = this::class.java.simpleName
//    private val uuid: UUID = UUID.randomUUID()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //lifecycle.addObserver(uiScope)
    }

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

    override fun onDestroyView() {
        //lifecycle.removeObserver(uiScope)
        super.onDestroyView()
    }
}
