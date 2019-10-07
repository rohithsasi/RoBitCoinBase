package com.example.robitcoin.model

import com.google.common.truth.Truth
import org.junit.Test

class BlockChainGraphTest {

    @Test
    fun testBuild() {
        val graph = BlockChainGraph(
            coordinates = arrayListOf(),
            descripton = "Description",
            name = "Name",
            unit = "Unit",
            period = "Period"
        )
        Truth.assertThat(graph.coordinates).isEmpty()
        Truth.assertThat(graph.descripton).isEqualTo("Description")
        Truth.assertThat(graph.name).isEqualTo("Name")
        Truth.assertThat(graph.unit).isEqualTo("Unit")
        Truth.assertThat(graph.period).isEqualTo("Period")
    }
}