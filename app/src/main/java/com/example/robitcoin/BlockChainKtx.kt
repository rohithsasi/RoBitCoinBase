package com.example.robitcoin

import android.widget.TextView
import androidx.core.content.ContextCompat

fun Int.getString(): String {
    return BlockChainApplication.APPLICATION.getString(this)
}

//TODO
infix fun Int.insertString(textView: TextView) {
    textView.text = this.getString()
}

//TODO
fun Int.getColor(): Int {
    return ContextCompat.getColor(BlockChainApplication.APPLICATION, this)
}
