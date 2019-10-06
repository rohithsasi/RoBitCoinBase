package com.example.robitcoin

import android.content.Context
import android.net.ConnectivityManager
import android.widget.TextView
import androidx.core.content.ContextCompat

fun Int.getString(): String {
    return RoBitCoinApplication.APPLICATION.getString(this)
}

infix fun Int.insertString(textView: TextView) {
    textView.text = this.getString()
}

fun Int.getColor(): Int {
    return ContextCompat.getColor(RoBitCoinApplication.APPLICATION, this)
}
