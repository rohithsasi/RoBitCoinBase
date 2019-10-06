package com.example.robitcoin.listener

//add internal back
/*internal*/ interface BitCoinNetworkListener<Response> {
    fun onSuccess(response: Response)
    fun onFailure(throwable: Throwable)
}