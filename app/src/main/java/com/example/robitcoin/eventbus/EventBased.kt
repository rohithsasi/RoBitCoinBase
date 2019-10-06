package com.nike.adapt.eventbus

import androidx.annotation.CallSuper

interface EventBased {
    @CallSuper
    fun started() {
    }

    @CallSuper
    fun stopped() {
    }
}