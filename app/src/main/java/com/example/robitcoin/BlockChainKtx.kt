package com.example.robitcoin

fun Int.getString(): String {
    return BlockChainApplication.APPLICATION.getString(this)
}
