package com.sadellie.unitto.screens

import org.junit.Assert.*

import org.junit.Test
import java.math.BigDecimal

class MinimumRequiredScaleTest {

    @Test
    fun setMinimumRequiredScalePrefTooLow() {
        // We prefer scale of 2, which is too low
        val bd = BigDecimal("0.000000123456").setMinimumRequiredScale(2)
        assertEquals("0.0000001", bd.toPlainString())
    }

    @Test
    fun setMinimumRequiredScalePrefTooHigh() {
        // We prefer scale of 15, which is too high
        val bd = BigDecimal("0.000000123456").setMinimumRequiredScale(15)
        assertEquals("0.000000123456000", bd.toPlainString())
    }

    @Test
    fun setMinimumRequiredScalePrefBetween() {
        // We prefer scale of 9, which will cut this value
        val bd = BigDecimal("0.000000123456").setMinimumRequiredScale(9)
        assertEquals("0.000000123", bd.toPlainString())
    }
}