package com.example.robitcoin.listener

interface BlockChainResultListener<Result> {

    fun onEvent(result: RoBitcoinResult<Result>)

}

inline infix fun <reified Result> BlockChainResultListener<Result>.onSuccess(result: Result) {
    onEvent(OnSuccessRoBitcoinResult(result))
}

inline infix fun <reified Result> BlockChainResultListener<Result>.onFailure(result: Throwable) {
    onEvent(OnFailureRoBitcoinResult(result))
}

@Suppress("unused")

sealed class RoBitcoinResult<Result>

data class OnSuccessRoBitcoinResult<Result>(val result: Result) : RoBitcoinResult<Result>()

data class OnFailureRoBitcoinResult<Result>(val throwable: Throwable) : RoBitcoinResult<Result>()