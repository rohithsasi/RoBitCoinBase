package com.example.robitcoin.eventbus

import androidx.annotation.CallSuper


interface EventBased {
    @CallSuper
    fun started() {
    }

    @CallSuper
    fun stopped() {
    }
}