package com.example.robitcoin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.robitcoin.R
import com.example.robitcoin.base.EventBasedActivity
import com.example.robitcoin.getString
import com.example.robitcoin.utils.NetworkConnectionUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_view.*
import kotlinx.android.synthetic.main.view_toolbar.*


class DashboardActivity : EventBasedActivity() {
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)
        setSupportActionBar(bgf_toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = R.string.app_dashboard.getString()
        }

        setupDrawer(bgf_toolbar)
        launchFragment(HomeFragment.newFragment())
    }

    private fun launchFragment(fragment: Fragment) {
        if (NetworkConnectionUtil.isOnline(this@DashboardActivity)) {
            supportFragmentManager.beginTransaction().replace(
                R.id.content, fragment,
                MAIN_CONTENT_TAG
            )
                .commit()

        } else {
            throwErrorDialog(fragment)
        }
    }

    private fun throwErrorDialog(fragment: Fragment) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Network Error")
            .setMessage("Please check your WiFi or cellular connection and try again.")
            .setPositiveButton("Retry") { dialog, which ->
                launchFragment(fragment)
            }
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }


    private val onBottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    bgf_toolbar.title = R.string.bottom_nav_home.getString()
                    val homeFragment = HomeFragment.newFragment()
                    launchFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_stats -> {
                    bgf_toolbar.title = R.string.bottom_nav_stats.getString()
                    val songsFragment = StatsFragment.newFragment()
                    launchFragment(songsFragment)
                    // Toast.makeText(this, "Selected navigation item 1", Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_store -> {
                    bgf_toolbar.title = R.string.bottom_nav_market.getString()
                    val songsFragment = MarketFragment.newFragment()
                    launchFragment(songsFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private val onDrawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            mDrawerLayout.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.nav_wallet -> {
                    BlockChainWebViewActivity.navigateTo(
                        this@DashboardActivity,
                        BlockChainWebViewActivity.Companion.WebViewState.WALLET
                    )
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_lock_box -> {
                    BlockChainWebViewActivity.navigateTo(
                        this@DashboardActivity,
                        BlockChainWebViewActivity.Companion.WebViewState.LOCK
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_portal -> {
                    BlockChainWebViewActivity.navigateTo(
                        this@DashboardActivity,
                        BlockChainWebViewActivity.Companion.WebViewState.LEARNING
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_drawer_settings -> {
                    Toast.makeText(this, "To be Implemeneted", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

    private fun setupDrawer(toolbar: Toolbar) {
        mDrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar,
            R.string.app_name,
            R.string.app_name
        )
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mDrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })

        navigation_view.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
    }

    companion object {
        private const val MAIN_CONTENT_TAG = "MAIN_CONTENT_TAG"

        fun navigateTo(activity: Context) {
            activity.startActivity(Intent(activity, DashboardActivity::class.java))

        }
    }


}



