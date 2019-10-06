package com.example.robitcoin.utils


import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor


/**
 * Utils for enabling Facebook Stetho
 * https://github.com/facebook/stetho
 */
object StethoUtils {

    private val interceptor: Interceptor = StethoInterceptor()

    /**
     * Initializes stetho
     *
     * @param application used for the context
     */
    fun init(application: Application) {
        Stetho.initializeWithDefaults(application)
    }

    /**
     * Adds the interceptor for monitoring network requests
     *
     * @param builder to add to
     */
    fun getInterceptor(): Interceptor? {
        return interceptor
    }
}
