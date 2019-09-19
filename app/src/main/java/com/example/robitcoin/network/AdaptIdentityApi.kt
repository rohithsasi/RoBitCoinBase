package com.example.robitcoin.network

interface AdaptIdentityApi {
    fun getUser()

    companion object {
        fun get(): AdaptIdentityApi {
            return AdaptIdentityApiImpl
        }
    }
}

private object AdaptIdentityApiImpl : AdaptIdentityApi {

    private val identityServiceApi: BitCoinPricingApi by lazy { RoBitCoinRestClient.get().identityServiceApi }

    override fun getUser() {

    }
}



