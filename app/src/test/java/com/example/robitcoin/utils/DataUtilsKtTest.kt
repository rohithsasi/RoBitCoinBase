package com.example.robitcoin.utils

import org.junit.Test

import org.junit.Assert.*

class DataUtilsKtTest {

    @Test
    fun parseToDateTest() {
        assertEquals("Mar 09",1552176000.0.parseToDate())
        assertEquals("Mar 24",1553472000.0.parseToDate())
        assertEquals("Apr 14",1555286400.0.parseToDate())
        assertEquals("May 02",1556841600.0.parseToDate())
        assertEquals("May 24",1558742400.0.parseToDate())
        assertEquals("Jun 19",1560974400.0.parseToDate())
        assertEquals("Jun 04",1559645820.0.parseToDate())
        assertEquals("Jul 14",1563148800.0.parseToDate())
        assertEquals("Aug 25",1566724680.0.parseToDate())
        assertEquals("Sep 02",1567473420.0.parseToDate())
        assertEquals("Sep 29",1569756180.0.parseToDate())
        assertEquals("Oct 07",1570470840.0.parseToDate())
    }


    @Test
    fun round() {
        assertEquals(7993.049,7993.049166666665.round(),.1)
        assertEquals(4.374,4.37402777777778.round(),.1)
        assertEquals(123457.2344566,123457.234.round(),.1)
        assertEquals(1.123456789,1.23457.round(1),.1)

    }
}