package org.gciatto.kt.math

import kotlin.test.Test
import kotlin.test.assertEquals

class TestIntegers {
    @Test
    fun testToChar() {
        assertEquals((0).toChar(), BigInteger.of(0).toChar())
        assertEquals((1).toChar(), BigInteger.of(1).toChar())
        assertEquals((-1).toChar(), BigInteger.of(-1).toChar())
        assertEquals((2).toChar(), BigInteger.of(2).toChar())
        assertEquals((10).toChar(), BigInteger.of(10).toChar())
    }

    @Test
    fun testLimits() {
        assertEquals("0", BigInteger.of(0).toString())
        assertEquals("1", BigInteger.of(1).toString())
        assertEquals("-1", BigInteger.of(-1).toString())
        assertEquals("2", BigInteger.of(2).toString())
        assertEquals("10", BigInteger.of(10).toString())
        assertEquals(Int.MAX_VALUE.toString(), BigInteger.of(Int.MAX_VALUE).toString())
        assertEquals(Int.MIN_VALUE.toString(), BigInteger.of(Int.MIN_VALUE).toString())
        assertEquals(Long.MAX_VALUE.toString(), BigInteger.of(Long.MAX_VALUE).toString())
        assertEquals(Long.MIN_VALUE.toString(), BigInteger.of(Long.MIN_VALUE).toString())
        assertEquals("9223372036854775808", (BigInteger.of(Long.MAX_VALUE) + BigInteger.ONE).toString())
        assertEquals("-9223372036854775809", (BigInteger.of(Long.MIN_VALUE) - BigInteger.ONE).toString())
    }

    @Test
    fun testSum() {
        assertBigIntegersAreEquals(BigInteger.of(-1 + 1), BigInteger.of(-1) + BigInteger.ONE)
        assertBigIntegersAreEquals(BigInteger.of(0 + 1), BigInteger.of(0) + BigInteger.ONE)
        assertBigIntegersAreEquals(BigInteger.of(1 + 1), BigInteger.of(1) + BigInteger.ONE)

        assertBigIntegersAreEquals(BigInteger.of(-1 + 2), BigInteger.of(-1) + BigInteger.TWO)
        assertBigIntegersAreEquals(BigInteger.of(0 + 2), BigInteger.of(0) + BigInteger.TWO)
        assertBigIntegersAreEquals(BigInteger.of(1 + 2), BigInteger.of(1) + BigInteger.TWO)

        assertBigIntegersAreEquals(BigInteger.of(-1 + 10), BigInteger.of(-1) + BigInteger.TEN)
        assertBigIntegersAreEquals(BigInteger.of(0 + 10), BigInteger.of(0) + BigInteger.TEN)
        assertBigIntegersAreEquals(BigInteger.of(1 + 10), BigInteger.of(1) + BigInteger.TEN)

        assertEquals("9223372036854775808", (BigInteger.of(Long.MAX_VALUE) + BigInteger.ONE).toString())
        assertEquals("9223372036854775809", (BigInteger.of(Long.MAX_VALUE) + BigInteger.TWO).toString())
        assertEquals("9223372036854775817", (BigInteger.of(Long.MAX_VALUE) + BigInteger.TEN).toString())

        assertEquals("-9223372036854775807", (BigInteger.of(Long.MIN_VALUE) + BigInteger.ONE).toString())
        assertEquals("-9223372036854775806", (BigInteger.of(Long.MIN_VALUE) + BigInteger.TWO).toString())
        assertEquals("-9223372036854775798", (BigInteger.of(Long.MIN_VALUE) + BigInteger.TEN).toString())
    }

    @Test
    fun testSub() {
        assertBigIntegersAreEquals(BigInteger.of(-1 - 1), BigInteger.of(-1) - BigInteger.ONE)
        assertBigIntegersAreEquals(BigInteger.of(0 - 1), BigInteger.of(0) - BigInteger.ONE)
        assertBigIntegersAreEquals(BigInteger.of(1 - 1), BigInteger.of(1) - BigInteger.ONE)

        assertBigIntegersAreEquals(BigInteger.of(-1 - 2), BigInteger.of(-1) - BigInteger.TWO)
        assertBigIntegersAreEquals(BigInteger.of(0 - 2), BigInteger.of(0) - BigInteger.TWO)
        assertBigIntegersAreEquals(BigInteger.of(1 - 2), BigInteger.of(1) - BigInteger.TWO)

        assertBigIntegersAreEquals(BigInteger.of(-1 - 10), BigInteger.of(-1) - BigInteger.TEN)
        assertBigIntegersAreEquals(BigInteger.of(0 - 10), BigInteger.of(0) - BigInteger.TEN)
        assertBigIntegersAreEquals(BigInteger.of(1 - 10), BigInteger.of(1) - BigInteger.TEN)

        assertEquals("9223372036854775806", (BigInteger.of(Long.MAX_VALUE) - BigInteger.ONE).toString())
        assertEquals("9223372036854775805", (BigInteger.of(Long.MAX_VALUE) - BigInteger.TWO).toString())
        assertEquals("9223372036854775797", (BigInteger.of(Long.MAX_VALUE) - BigInteger.TEN).toString())

        assertEquals("-9223372036854775809", (BigInteger.of(Long.MIN_VALUE) - BigInteger.ONE).toString())
        assertEquals("-9223372036854775810", (BigInteger.of(Long.MIN_VALUE) - BigInteger.TWO).toString())
        assertEquals("-9223372036854775818", (BigInteger.of(Long.MIN_VALUE) - BigInteger.TEN).toString())
    }

    @Test
    fun testDecimalParsing() {
        assertEquals(0, BigInteger.of("0").toInt())
        assertEquals(1, BigInteger.of("1").toInt())
        assertEquals(2, BigInteger.of("2").toInt())
        assertEquals(3, BigInteger.of("3").toInt())
        assertEquals(4, BigInteger.of("4").toInt())
        assertEquals(5, BigInteger.of("5").toInt())
        assertEquals(6, BigInteger.of("6").toInt())
        assertEquals(7, BigInteger.of("7").toInt())
        assertEquals(8, BigInteger.of("8").toInt())
        assertEquals(9, BigInteger.of("9").toInt())
        assertEquals(10, BigInteger.of("10").toInt())
        assertEquals(-1, BigInteger.of("-1").toInt())
        assertEquals(-10, BigInteger.of("-10").toInt())
    }

    @Test
    fun testHexadecimalParsing() {
        assertEquals(0, BigInteger.of("0", 16).toInt())
        assertEquals(1, BigInteger.of("1", 16).toInt())
        assertEquals(2, BigInteger.of("2", 16).toInt())
        assertEquals(3, BigInteger.of("3", 16).toInt())
        assertEquals(4, BigInteger.of("4", 16).toInt())
        assertEquals(5, BigInteger.of("5", 16).toInt())
        assertEquals(6, BigInteger.of("6", 16).toInt())
        assertEquals(7, BigInteger.of("7", 16).toInt())
        assertEquals(8, BigInteger.of("8", 16).toInt())
        assertEquals(9, BigInteger.of("9", 16).toInt())
        assertEquals(10, BigInteger.of("a", 16).toInt())
        assertEquals(11, BigInteger.of("b", 16).toInt())
        assertEquals(12, BigInteger.of("c", 16).toInt())
        assertEquals(13, BigInteger.of("d", 16).toInt())
        assertEquals(14, BigInteger.of("e", 16).toInt())
        assertEquals(15, BigInteger.of("f", 16).toInt())
        assertEquals(16, BigInteger.of("10", 16).toInt())
        assertEquals(10, BigInteger.of("A", 16).toInt())
        assertEquals(11, BigInteger.of("B", 16).toInt())
        assertEquals(12, BigInteger.of("C", 16).toInt())
        assertEquals(13, BigInteger.of("D", 16).toInt())
        assertEquals(14, BigInteger.of("E", 16).toInt())
        assertEquals(15, BigInteger.of("F", 16).toInt())
        assertEquals(-1, BigInteger.of("-1", 16).toInt())
        assertEquals(-10, BigInteger.of("-A", 16).toInt())
        assertEquals(-15, BigInteger.of("-F", 16).toInt())
    }

    @Test
    fun testOctalParsing() {
        assertEquals(0, BigInteger.of("0", 8).toInt())
        assertEquals(1, BigInteger.of("1", 8).toInt())
        assertEquals(2, BigInteger.of("2", 8).toInt())
        assertEquals(3, BigInteger.of("3", 8).toInt())
        assertEquals(4, BigInteger.of("4", 8).toInt())
        assertEquals(5, BigInteger.of("5", 8).toInt())
        assertEquals(6, BigInteger.of("6", 8).toInt())
        assertEquals(7, BigInteger.of("7", 8).toInt())
        assertEquals(8, BigInteger.of("10", 8).toInt())
        assertEquals(-1, BigInteger.of("-1", 8).toInt())
        assertEquals(-8, BigInteger.of("-10", 8).toInt())
    }

    @Test
    fun testBinaryParsing() {
        assertEquals(0, BigInteger.of("0", 2).toInt())
        assertEquals(1, BigInteger.of("1", 2).toInt())
        assertEquals(-1, BigInteger.of("-1", 2).toInt())
    }
}
