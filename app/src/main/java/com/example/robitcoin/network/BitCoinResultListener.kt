package com.example.robitcoin.network

interface BitCoinResultListener<Result> {

    /**
     * Invoked when a network request has succeeded.
     * @param value returned request object
     */
     fun onSuccess(value: Result)

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
     fun onFailure(t: Throwable)
}