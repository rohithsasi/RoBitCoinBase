package com.example.robitcoin.ui

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
import com.example.robitcoin.R
import com.example.robitcoin.base.EventBasedFragment
import com.example.robitcoin.model.BlockChainGraph
import com.example.robitcoin.presentation.*
import com.example.robitcoin.utils.parseToDate
import com.example.robitcoin.utils.round
import kotlinx.android.synthetic.main.fragment_market.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MarketFragment : EventBasedFragment() {

    private val viewModel = BlockChainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_market, container, false).apply {
            (view as? ViewGroup)?.forEach { it.alpha = 0f }
            viewModel.fetchBlockChainMarketCapGraph(Chart.MARKETY_CAP)
        }
    }

    private fun updateGraph(graphData: BlockChainGraph) {
        APIlib.getInstance().setActiveAnyChartView(any_chart_view_market)
        val cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair().yLabel(true)
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title("Market Cap")

        cartesian.padding(10.0, 20.0, 5.0, 20.0)
        cartesian.yAxis(0).title("Dollar (Billion)")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData = mutableListOf<DataEntry>()
        graphData.coordinates?.forEach {
            seriesData.add(ValueDataEntry(it.x.parseToDate(), it.y.div(1000000000)))
        }

        val set = Set.instantiate()
        set.data(seriesData)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)

        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1 = cartesian.line(series1Mapping)
        series1.name("Today Market Cap : ${graphData.coordinates?.last()?.y?.round(2)?.div(1000000000
        )} Billion")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)

        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        any_chart_view_market.setChart(cartesian)

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFetchProfileResult(result: ActionResult) {
        when (result) {
            is FetchGraphDataActionResult -> {
                Log.d("BITCOIN", "${result.graphPlot?.coordinates?.joinToString { "***" }}")
                result.graphPlot?.let {
                    updateGraph(it)
                } ?: let {

                }

                //viewModel.fetchBlockChainStats()
            }

            is FetchBlockChainStatsActionResult -> {
                result.stats?.let {
                }

            }
        }
    }

    companion object {

        fun newFragment(): Fragment {
            return MarketFragment()
        }
    }
}


//TODO CLEAN UP STATS NUMBERS