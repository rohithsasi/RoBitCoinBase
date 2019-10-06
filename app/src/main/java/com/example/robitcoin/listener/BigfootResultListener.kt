package com.example.robitcoin.listener

interface BigfootResultListener<Result> {

    fun onEvent(result: BigfootResult<Result>)

}

inline infix fun <reified Result> BigfootResultListener<Result>.onSuccess(result: Result) {
    onEvent(OnSuccessBigfootResult(result))
}

inline infix fun <reified Result> BigfootResultListener<Result>.onFailure(result: Throwable) {
    onEvent(OnFailureBigfootResult(result))
}

@Suppress("unused")

sealed class BigfootResult<Result>

data class OnSuccessBigfootResult<Result>(val result: Result) : BigfootResult<Result>()

data class OnFailureBigfootResult<Result>(val throwable: Throwable) : BigfootResult<Result>()