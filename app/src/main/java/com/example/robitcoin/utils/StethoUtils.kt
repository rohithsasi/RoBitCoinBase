package com.example.robitcoin.utils


import android.app.Application
import com.facebook.stetho.Stetho


/**
 * Utils for enabling Facebook Stetho
 * https://github.com/facebook/stetho
 */
object StethoUtils {

    /**
     * Initializes stetho
     *
     * @param application used for the context
     */
    fun init(application: Application) {
        Stetho.initializeWithDefaults(application)
    }

}
