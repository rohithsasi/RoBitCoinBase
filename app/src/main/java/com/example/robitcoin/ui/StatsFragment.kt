package com.example.robitcoin.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.robitcoin.R
import com.example.robitcoin.base.EventBasedFragment
import com.example.robitcoin.presenter.ActionResult
import com.example.robitcoin.presenter.BitCoinPricingViewModel
import com.example.robitcoin.presenter.FetchProfileActionResult
import com.example.robitcoin.presenter.FetchStatsActionResult
import com.example.robitcoin.ui.adapter.StatsAdapter
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.android.synthetic.main.fragment_stats.stats_recycle_view
import kotlinx.android.synthetic.main.fragment_stats.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class StatsFragment : EventBasedFragment() {

    val viewModel = BitCoinPricingViewModel()
    lateinit var adapter: StatsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false).apply {
            (view as? ViewGroup)?.forEach { it.alpha = 0f }

            stats_recycle_view?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
            }

            viewModel.getBlockChainStats()
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFetchBlockChainStatsResult(result: ActionResult) {
        when (result) {
            is FetchStatsActionResult -> {
                result.stats?.let {
                    adapter = StatsAdapter(it)
                    stats_recycle_view.adapter =adapter
                    adapter.updateDataSet(it)
                    stats_progress.visibility = View.GONE
                    stats_holder.visibility = View.VISIBLE
                }
            }
        }
    }


    companion object {

        fun newFragment(): Fragment {
            return StatsFragment()
        }
    }
}