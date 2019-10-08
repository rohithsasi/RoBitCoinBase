package com.example.robitcoin.presentation

import org.junit.Assert
import org.junit.Test

class ChartTest {

    @Test
    fun testChartMarketPriceType() {
        Assert.assertEquals(Chart.MARKET_PRICE.type, "market-price")
    }

    @Test
    fun testChartMarketCapType() {
        Assert.assertEquals(Chart.MARKET_CAP.type, "market-cap")
    }


    @Test
    fun testChartMarketPriceName() {
        Assert.assertEquals(Chart.MARKET_PRICE.name, "MARKET_PRICE")
    }

    @Test
    fun testChartMarketCapName() {
        Assert.assertEquals(Chart.MARKET_CAP.name, "MARKET_CAP")
    }
}