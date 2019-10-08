package com.example.robitcoin

import org.junit.Test

import org.junit.Assert.*

class BlockChainKtxKtTest {

    @Test
    fun getString() {
        assertEquals("Dashboard", R.string.app_dashboard.getString())
        assertEquals("Wallet", R.string.app_wallet.getString())
        assertEquals("Blockchain", R.string.app_name.getString())
        assertEquals("Bitcoin Stats", R.string.stats_title.getString())
        assertEquals("Bitcoin Summary statistics for the last 24 hours.", R.string.stats_sub_title.getString())
        assertEquals("MARKET SUMMARY", R.string.stats_market_summary_heading.getString())
        assertEquals("BLOCK SUMMARY", R.string.stats_block_summary_heading.getString())
        assertEquals("MINING COST", R.string.stats_mining_cost.getString())
        assertEquals("TRANSACTION SUMMARY", R.string.stats_transaction_summary_heading.getString())
        assertEquals("Lockbox", R.string.nav_drawer_lockbox.getString())
        assertEquals("Learn", R.string.nav_drawer_portal.getString())
        assertEquals("Wallet", R.string.nav_drawer_wallet.getString())
        assertEquals("johnwick@gmail.com", R.string.nav_drawer_sample_email.getString())
    }
}