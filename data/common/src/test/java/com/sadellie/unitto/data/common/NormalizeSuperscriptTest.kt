package com.sadellie.unitto.data.common

import org.junit.Assert.assertEquals
import org.junit.Test

class NormalizeSuperscriptTest {

    @Test
    fun normalizeSuperscript() {
        val input = "⁰¹²³⁴⁵⁶⁷⁸⁹"
        assertEquals("0123456789", input.normalizeSuperscript())
    }
}
