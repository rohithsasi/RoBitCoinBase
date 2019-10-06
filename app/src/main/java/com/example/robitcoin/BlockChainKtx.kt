package com.example.robitcoin

import android.widget.TextView
import androidx.core.content.ContextCompat

fun Int.getString(): String {
    return BlockChainApplication.APPLICATION.getString(this)
}

infix fun Int.insertString(textView: TextView) {
    textView.text = this.getString()
}

fun Int.getColor(): Int {
    return ContextCompat.getColor(BlockChainApplication.APPLICATION, this)
}
