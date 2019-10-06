package com.example.robitcoin.presentation

import com.example.robitcoin.model.BlockChainPopularStats

import kotlinx.android.parcel.Parcelize

@Parcelize
class FetchBlockChainStatsActionResult(val stats: BlockChainPopularStats? ) : ActionResult
