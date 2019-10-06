package com.example.robitcoin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.robitcoin.*
import com.example.robitcoin.base.EventBasedActivity
import com.example.robitcoin.model.Pricing
import com.example.robitcoin.network.model.BitCoinPriceing
import com.example.robitcoin.network.model.Values
import com.example.robitcoin.presenter.BitCoinPricingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.navigation_view.*
import kotlinx.android.synthetic.main.view_toolbar.*


class DashboardActivity : EventBasedActivity() {

    var result: BitCoinPriceing? = null
    var output: List<Values> = mutableListOf()
    val viewModel = BitCoinPricingViewModel()
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)
        viewModel.getUserData()

        setSupportActionBar(bgf_toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = R.string.app_dashboard.getString()
        }

        setupDrawer(bgf_toolbar)
        launchFragment(HomeFragment.newFragment())


//        AdaptIdentityApi.get().getChartData(object :BitCoinResultListener<Response<BitCoinPriceing>>{
//            override fun onSuccess(value: Response<BitCoinPriceing>) {
//
//                 result = value.body()!!
//                output= result?.valuers!!
//
//                output.isNotEmpty().run {
//                    updateGraph()
//                }
//            }
//
//            override fun onFailure(t: Throwable) {
//
//                Toast.makeText(this@DashboardActivity,"error",Toast.LENGTH_SHORT).show()
//            }
//
//        })


//        APIlib.getInstance().setActiveAnyChartView(any_chart_view_2);
//
//        val pie = AnyChart.pie()
//
//        val data = mutableListOf<DataEntry>()
//
//        data.add(ValueDataEntry("John", 10000))
//        data.add(ValueDataEntry("Jake", 12000))
//        data.add(ValueDataEntry("Peter", 18000))
//
//        pie.data(data);
//        any_chart_view_2.setChart(pie)

    }

    fun updateGraph(graphData: Pricing) {

        APIlib.getInstance().setActiveAnyChartView(any_chart_view_1);
        val pie = AnyChart.line()

        val cartesian = AnyChart.line()

        cartesian.animation(true)
        cartesian.padding(2.0, 4.0, 6.0, 8.0)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair().yLabel(true)
            // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title("Price Chart")
        cartesian.yAxis(0).title("USD")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData = mutableListOf<DataEntry>()
        graphData.coordinates?.forEach {
            seriesData.add(ValueDataEntry(it.x, it.y))
        }

        val set = Set.instantiate()
        set.data(seriesData)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1 = cartesian.line(series1Mapping)
        series1.name("Marker Price : USD")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

//        data.add(ValueDataEntry("John", 10000))
//        data.add(ValueDataEntry("Jake", 12000))
//        data.add(ValueDataEntry("Peter", 18000))

        pie.data(seriesData);
        any_chart_view_1.setChart(cartesian)


    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onFetchProfileResult(result: ActionResult) {
//        when (result) {
//            is FetchProfileActionResult -> {
//                Log.d("BITCOIN", "${result.bigfootUser?.coordinates?.joinToString { "***" }}")
//                result.bigfootUser?.let {
//                    updateGraph(it)
//                } ?: let {
//
//                }
//
//                viewModel.getBlockChainStats()
//            }
//
//            is FetchStatsActionResult -> {
//                result.stats?.let {
//                    //Toast.makeText(this, " Bitcoin Price is ${it}", Toast.LENGTH_LONG).show()
//                }
//
//            }
//        }
//    }

    private fun launchFragment(fragment: Fragment) {
        //any_chart_view_1.visibility = View.GONE
        supportFragmentManager.beginTransaction().replace(
            R.id.content, fragment,
            MAIN_CONTENT_TAG
        )
            .commit()
    }


    /*private fun setupBottomNavigationMenu() {
        //bottomNavigation.inflateMenu(R.menu.menu_bottom_nav)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, "Selected navigation item 1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item2 -> {
                    Toast.makeText(this, "Selected navigation item 2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item3 -> {
                    Toast.makeText(this, "Selected navigation item 3", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, "Reselected navigation item 1", Toast.LENGTH_SHORT).show()
                }
                R.id.item2 -> {
                    Toast.makeText(this, "Reselected navigation item 2", Toast.LENGTH_SHORT).show()
                }
                R.id.item3 -> {
                    Toast.makeText(this, "Reselected navigation item 3", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Use this to programmatically select navigation items
        //bottomNavigation.selectedItemId = R.id.item1
    }
*/

    private val onBottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    bgf_toolbar.title = "Home"
                    val homeFragment = HomeFragment.newFragment()
                    launchFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_stats -> {
                    bgf_toolbar.title = "Stats"
                    val songsFragment = StatsFragment.newFragment()
                    launchFragment(songsFragment)
                    // Toast.makeText(this, "Selected navigation item 1", Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_store -> {
                    bgf_toolbar.title = "Store"
                    val songsFragment = StatsFragment.newFragment()
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
                R.id.nav_drawer_home -> {
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_drawer_stats -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_drawer_store -> {

                    return@OnNavigationItemSelectedListener true
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
        private val TAG = DashboardActivity::class.java.simpleName

        fun navigateTo(activity: Context) {
            activity.startActivity(Intent(activity, DashboardActivity::class.java))

        }
    }


}



