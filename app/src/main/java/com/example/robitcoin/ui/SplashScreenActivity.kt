package com.example.robitcoin.ui

import android.os.Bundle
import com.example.robitcoin.R
import com.example.robitcoin.base.EventBasedActivity
import com.example.robitcoin.utils.NetworkConnectionUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : EventBasedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        launchDashboard()
    }

    private fun launchDashboard() {
        /**
         * A simple usage of Kotlin co routines I wanted to demonstrate.The thought here is perform all onboarding operations
         * ui or computational or netowork in co routine which takes care of threading w.r.t the Dispatchers. Recently with
         * more and more usage of Kotlin I have been using coroutines more than rx java.
         */
        uiScope.launch(Dispatchers.Main) {
            if (NetworkConnectionUtil.isOnline(this@SplashScreenActivity)) {
                delay(1_000)
                DashboardActivity.navigateTo(this@SplashScreenActivity)
                finish() //This need to finish
            } else {
                throwErrorDialog()
            }
        }
    }

    private fun throwErrorDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Network Error")
            .setMessage("Please check your WiFi or cellular connection and try again.")
            .setPositiveButton("Retry") { dialog, which ->
                launchDashboard()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
                finish()
            }
            .show()
    }

}
