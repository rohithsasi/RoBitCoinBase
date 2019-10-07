package com.example.robitcoin.model

import com.google.common.truth.Truth
import org.junit.Test

class CoordinatesTest{

    @Test
    fun testCoordinates(){
        Coordinates(x = 3.4, y = 5.6).run {
            Truth.assertThat(x).isEqualTo(3.4)
            Truth.assertThat(y).isEqualTo(5.6)
        }
    }
}