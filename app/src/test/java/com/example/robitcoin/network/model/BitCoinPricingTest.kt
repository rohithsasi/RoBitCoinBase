package com.example.robitcoin.network.model

import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Test

class BitCoinPricingTest {

    val pricingResponse: String = """
    {
        "status": "OK",
        "name": "Transaction Rate",
        "unit": "Transactions Per Second",
        "period": "minute",
        "description": "The number of Bitcoin transactions added to the mempool per second.",
        "values": 
        [
               {"x":1570353620,"y":5667834},
               {"x":5667834,"y":4343},
               {"x":45464,"y":4545},
               {"x":34,"y":445},
               {"x":1570210620,"y":4214097222222219},
               {"x":1570210620,"y":44097222222219}

        ]
    }
"""
    val bitCoinPriceing = Gson().fromJson(pricingResponse, BlockChainGraphPlot::class.java)

    @Test
    fun getName() {
        assertEquals("Transaction Rate", bitCoinPriceing.name)
    }

    @Test
    fun getUnit() {
        assertEquals("Transactions Per Second", bitCoinPriceing.unit)
    }

    @Test
    fun getPeriod() {
        assertEquals("minute", bitCoinPriceing.period)
    }

    @Test
    fun getDescription() {
       assertEquals("The number of Bitcoin transactions added to the mempool per second.", bitCoinPriceing.description)
    }

    @Test
    fun getValuers() {
        assertEquals(bitCoinPriceing.values?.get(0)?.x,1.57035362E9)
        assertEquals(bitCoinPriceing.values?.get(0)?.y,5667834.0)
        assertEquals(bitCoinPriceing.values?.get(2)?.x,45464.0)
        assertEquals(bitCoinPriceing.values.get(4)?.y,4.214097222222219E15)
        assertEquals(bitCoinPriceing.values?.size,6)
    }
}