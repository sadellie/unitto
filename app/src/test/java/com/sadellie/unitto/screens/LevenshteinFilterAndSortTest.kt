package com.sadellie.unitto.screens

import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.UnitGroup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

val baseList: List<AbstractUnit> = listOf(
    "Attometer",
    "Nanometer",
    "Millimeter",
    "Meter",
    "Kilometer",
    "Mile",
    "Pound",
    "Kilometer per square"
).map { name ->
    MyUnit("", BigDecimal.ONE, UnitGroup.ANGLE, 0, 0)
        .also { it.renderedName = name }
}

class LevenshteinFilterAndSortTest {

    @Test
    fun testOneEdit() {
        val searchQuery = "Kelometer"
        val result = baseList.asSequence().sortByLev(searchQuery).map { it.renderedName }.toList()
        println(result)
        assertEquals(
            listOf("Kilometer", "Kilometer per square", "Attometer", "Nanometer"),
            result
        )
    }

    @Test
    fun testLongQuery() {
        val searchQuery = "Kelometers per"
        val result = baseList.asSequence().sortByLev(searchQuery).map { it.renderedName }.toList()
        println(result)
        assertEquals(
            listOf("Kilometer per square", "Kilometer"),
            result
        )
    }

    @Test
    fun testMultipleMatches() {
        val searchQuery = "meter"
        val result = baseList.asSequence().sortByLev(searchQuery).map { it.renderedName }.toList()
        println(result)
        assertEquals(
            listOf("Meter", "Attometer", "Nanometer", "Millimeter", "Kilometer","Kilometer per square"),
            result
        )
    }

    @Test
    fun testNone() {
        val searchQuery = "Very long unit name that doesn't exist"
        val result = baseList.asSequence().sortByLev(searchQuery).map { it.renderedName }.toList()
        println(result)
        assertEquals(
            listOf<String>(),
            result
        )
    }
}