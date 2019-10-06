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
        uiScope.launch(Dispatchers.Main) {
            if (NetworkConnectionUtil.isOnline(this@SplashScreenActivity)) {
                //this would be when I would ideally fetch everything onboarding related (access token, right user accoubnt)
                // before launching the dashboard where I acrtuallt  interaction starts. Now I am running the spinned for a second
                //to mock that experience.
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
