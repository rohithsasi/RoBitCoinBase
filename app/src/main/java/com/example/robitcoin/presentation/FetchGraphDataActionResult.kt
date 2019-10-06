package com.example.robitcoin.presentation

import com.example.robitcoin.model.BlockChainGraph

import kotlinx.android.parcel.Parcelize

@Parcelize
class FetchGraphDataActionResult(val graphPlot: BlockChainGraph? ) : ActionResult
