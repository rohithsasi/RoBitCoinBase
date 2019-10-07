package com.example.robitcoin.ui.adapter

import com.example.robitcoin.ui.BlockChainWebViewActivity
import org.junit.Assert
import org.junit.Test

class WebViewStateTest {
    @Test
    fun testStateWalletUrl() {
        Assert.assertEquals(BlockChainWebViewActivity.Companion.WebViewState.WALLET.url, "https://www.blockchain.com/wallet")
    }

    @Test
    fun testStateLearningUrl() {
        Assert.assertEquals(BlockChainWebViewActivity.Companion.WebViewState.LEARNING.url, "https://www.blockchain.com/learning-portal/security")
    }

    @Test
    fun testStateLockUrl() {
        Assert.assertEquals(BlockChainWebViewActivity.Companion.WebViewState.LOCK.url, "https://www.blockchain.com/lockbox")
    }

    @Test
    fun testStateWalletName() {
        Assert.assertEquals(BlockChainWebViewActivity.Companion.WebViewState.WALLET.name, "WALLET")
    }

    @Test
    fun testStateLearningName() {
        Assert.assertEquals(BlockChainWebViewActivity.Companion.WebViewState.LEARNING.name, "LEARNING")
    }

    @Test
    fun testStateLockName() {
        Assert.assertEquals(BlockChainWebViewActivity.Companion.WebViewState.LOCK.name, "LOCK")
    }
}