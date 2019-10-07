package com.example.robitcoin.eventbus

import android.util.Log
import com.example.robitcoin.presentation.ActionResult
import com.example.robitcoin.BuildConfig


import org.greenrobot.eventbus.EventBus

object EventBus {
    private val eventBus = EventBus
            .builder()
            .eventInheritance(true)
            .throwSubscriberException(BuildConfig.DEBUG)
            .logNoSubscriberMessages(BuildConfig.DEBUG)
            .logSubscriberExceptions(BuildConfig.DEBUG)
            .sendNoSubscriberEvent(BuildConfig.DEBUG)
            .sendSubscriberExceptionEvent(BuildConfig.DEBUG)
            /*.logger(object : Logger {
                override fun log(level: Level?, msg: String?) {
                    if (BuildConfig.CAN_LOG && msg != null) {
                        Log.d("EventBus", msg)
                    }
                }

                override fun log(level: Level?, msg: String?, th: Throwable?) {
                    if (BuildConfig.CAN_LOG && msg != null) {
                        if (th == null) {
                            NikeLogger.error("EventBus", msg)
                        } else {
                            NikeLogger.error("EventBus", msg, th)
                        }
                    }
                }
            })*/
            .build()

    fun register(any: EventBased) {
        try {
            eventBus.register(any)
        } catch (throwable: Throwable) {
            Log.d("EventBus", "${throwable.message}", throwable)
        }
    }

    fun unregister(any: EventBased) {
        try {
            eventBus.unregister(any)
        } catch (throwable: Throwable) {
            Log.d("EventBus", "${throwable.message}", throwable)
        }
    }

    fun post(actionResult: ActionResult) {
        try {
            eventBus.post(actionResult)
        } catch (throwable: Throwable) {
           Log.d("EventBus", "${throwable.message}", throwable)
        }
    }
}