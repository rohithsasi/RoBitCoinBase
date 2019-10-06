package com.example.robitcoin.listener

//add internal back
/*internal*/ interface BlockChainNetworkListener<Response> {
    fun onSuccess(response: Response)
    fun onFailure(throwable: Throwable)
}