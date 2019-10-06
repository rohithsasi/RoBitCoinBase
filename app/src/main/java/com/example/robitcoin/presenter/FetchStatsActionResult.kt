package com.example.robitcoin.presenter


import com.example.robitcoin.model.BlockChainPopularStats

import kotlinx.android.parcel.Parcelize

@Parcelize
class FetchStatsActionResult(val stats: BlockChainPopularStats? ) :
    ActionResult
