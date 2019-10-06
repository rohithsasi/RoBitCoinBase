package com.example.robitcoin.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
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
import com.example.robitcoin.base.EventBasedFragment
import com.example.robitcoin.model.Pricing
import com.example.robitcoin.network.model.BitCoinPriceing
import com.example.robitcoin.network.model.Values

import com.example.robitcoin.presenter.ActionResult
import com.example.robitcoin.presenter.BitCoinPricingViewModel
import com.example.robitcoin.presenter.FetchProfileActionResult
import com.example.robitcoin.presenter.FetchStatsActionResult
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.roundToLong


/////Move location unit test
fun Double.parseToDate1() : String{

    val sdf = java.text.SimpleDateFormat("MMM dd")
    val date = java.util.Date(this.toLong() * 1000)
    return sdf.format(date)
}

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()


class HomeFragment : EventBasedFragment() {

    var result: BitCoinPriceing? = null
    var output: List<Values> = mutableListOf()
    val viewModel = BitCoinPricingViewModel()
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            (view as? ViewGroup)?.forEach { it.alpha = 0f }
            viewModel.getUserData()
        }
    }


    fun updateGraph(graphData: Pricing) {

        APIlib.getInstance().setActiveAnyChartView(any_chart_view_1);
        val pie = AnyChart.line()

        val cartesian = AnyChart.line()

        cartesian.animation(true)
        //cartesian.padding(2.0, 4.0, 6.0, 8.0)
        //cartesian.padding(10.0, 20.0, 5.0, 20.0)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair().yLabel(true)
            // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title("Price Chart")
        //cartesian.yAxis(0).title("USD")
        //cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
        //cartesian.xAxis(0).labels().padding(10.0, 10.0, 10.0, 10.0)

        cartesian.yAxis(0).labels().format("$ {%Value}");

        val seriesData = mutableListOf<DataEntry>()
        graphData.coordinates?.forEach {
            seriesData.add(ValueDataEntry(it.x?.parseToDate1(), it.y))
        }

        val set = Set.instantiate()
        set.data(seriesData)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        //cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1 = cartesian.line(series1Mapping)
            series1.name("Marker Price : ${graphData.coordinates?.last()?.y?.round(3)} USD")
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFetchProfileResult(result: ActionResult) {
        when (result) {
            is FetchProfileActionResult -> {
                Log.d("BITCOIN", "${result.bigfootUser?.coordinates?.joinToString { "***" }}")
                result.bigfootUser?.let {
                    updateGraph(it)
                } ?: let {

                }

               //viewModel.getBlockChainStats()
            }

            is FetchStatsActionResult -> {
                result.stats?.let {
                    //Toast.makeText(this, " Bitcoin Price is ${it}", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    companion object {

        fun newFragment(): Fragment {
            return HomeFragment()
        }
    }
}