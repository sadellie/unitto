package org.gciatto.kt.math

import kotlin.test.assertEquals

fun assertBigIntegersAreEquals(
    x: BigInteger,
    y: BigInteger,
    m: String? = "Failed: $x == $y",
) {
    if (m === null) {
        assertEquals(0, x.compareTo(y))
        assertEquals(0, y.compareTo(x))
    } else {
        assertEquals(0, x.compareTo(y), m)
        assertEquals(0, y.compareTo(x), m)
    }
}

fun assertReprEquals(
    repr: Any,
    obj: Any,
    m: String? = "Failed: $obj.toString() == $repr",
) {
    if (m === null) {
        assertEquals(repr.toString().lowercase(), obj.toString().lowercase())
    } else {
        assertEquals(repr.toString().lowercase(), obj.toString().lowercase(), m)
    }
}

fun assertBigDecimalsAreEquals(
    x: BigDecimal,
    y: BigDecimal,
    m: String? = "Failed: ${x.toPlainString()} == ${y.toPlainString()}",
) {
    if (m === null) {
        assertEquals(0, x.compareTo(y))
        assertEquals(0, y.compareTo(x))
    } else {
        assertEquals(0, x.compareTo(y), m)
        assertEquals(0, y.compareTo(x), m)
    }
}
