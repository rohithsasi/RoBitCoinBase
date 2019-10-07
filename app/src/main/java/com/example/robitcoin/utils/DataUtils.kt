package com.example.robitcoin.utils

import java.util.*

fun Double.parseToDate(): String {

    val sdf = java.text.SimpleDateFormat("MMM dd", Locale.US)
    val date = Date(this.toLong() * 1000)
    return sdf.format(date)
}

//TODO UNIT TEST
fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
