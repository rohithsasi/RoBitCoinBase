package com.example.robitcoin.presenter


import com.example.robitcoin.model.Pricing

import kotlinx.android.parcel.Parcelize

@Parcelize
class FetchProfileActionResult( val bigfootUser: Pricing? ) :
    ActionResult
