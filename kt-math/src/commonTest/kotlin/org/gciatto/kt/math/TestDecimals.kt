package org.gciatto.kt.math

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestDecimals {
    @Test
    fun testSpecialDoubles() {
        assertFailsWith<NumberFormatException> { BigDecimal.of(Double.NaN) }
        assertFailsWith<NumberFormatException> { BigDecimal.of(Double.NEGATIVE_INFINITY) }
        assertFailsWith<NumberFormatException> { BigDecimal.of(Double.POSITIVE_INFINITY) }
        assertFailsWith<NumberFormatException> { BigDecimal.of("NaN") }
    }

    @Test
    fun testSpecialFloats() {
        assertFailsWith<NumberFormatException> { BigDecimal.of(Float.NaN) }
        assertFailsWith<NumberFormatException> { BigDecimal.of(Float.NEGATIVE_INFINITY) }
        assertFailsWith<NumberFormatException> { BigDecimal.of(Float.POSITIVE_INFINITY) }
    }

    @Test
    fun testToChar() {
        assertEquals((0).toChar(), BigDecimal.of(0.1).toChar())
        assertEquals((1).toChar(), BigDecimal.of(1.1).toChar())
        assertEquals((-1).toChar(), BigDecimal.of(-1.1).toChar())
        assertEquals((2).toChar(), BigDecimal.of(2.1).toChar())
        assertEquals((10).toChar(), BigDecimal.of(10.1).toChar())
    }

    @Test
    fun testCreation() {
        assertReprEquals("0", BigDecimal.ZERO)
        assertReprEquals("1", BigDecimal.ONE)
        assertReprEquals("2", BigDecimal.TWO)
        assertReprEquals("10", BigDecimal.TEN)
        assertReprEquals("0.5", BigDecimal.ONE_HALF)
        assertReprEquals("0.1", BigDecimal.ONE_TENTH)
        assertReprEquals("-1", -BigDecimal.ONE)
        assertReprEquals("-2", -BigDecimal.TWO)
        assertReprEquals("-10", -BigDecimal.TEN)
        assertReprEquals("-0.5", -BigDecimal.ONE_HALF)
        assertReprEquals("-0.1", -BigDecimal.ONE_TENTH)

        assertReprEquals(DOUBLE_MAX_VALUE_REPR, BigDecimal.of(Double.MAX_VALUE).toPlainString())
        assertReprEquals(DOUBLE_MIN_VALUE_REPR, BigDecimal.of(Double.MIN_VALUE).toPlainString())
        assertReprEquals(FLOAT_MAX_VALUE_REPR, BigDecimal.of(Float.MAX_VALUE).toPlainString())
        assertReprEquals(FLOAT_MIN_VALUE_REPR, BigDecimal.of(Float.MIN_VALUE).toPlainString())

        assertReprEquals(PI_REPR, BigDecimal.PI)

        assertReprEquals(E_REPR, BigDecimal.E)

        assertBigDecimalsAreEquals(BigDecimal.ZERO, BigDecimal.of(0))
        assertBigDecimalsAreEquals(BigDecimal.ONE, BigDecimal.of(1))
        assertBigDecimalsAreEquals(BigDecimal.TWO, BigDecimal.of(2))
        assertBigDecimalsAreEquals(BigDecimal.TEN, BigDecimal.of(10))
        assertBigDecimalsAreEquals(BigDecimal.ONE_HALF, BigDecimal.of(1) / BigDecimal.of(2))
        assertBigDecimalsAreEquals(BigDecimal.ONE_TENTH, BigDecimal.of(1) / BigDecimal.of(10))
        assertBigDecimalsAreEquals(-BigDecimal.ONE, BigDecimal.of(-1))
        assertBigDecimalsAreEquals(-BigDecimal.TWO, BigDecimal.of(-2))
        assertBigDecimalsAreEquals(-BigDecimal.TEN, BigDecimal.of(-10))
        assertBigDecimalsAreEquals(-BigDecimal.ONE_HALF, BigDecimal.of(-1) / BigDecimal.of(2))
        assertBigDecimalsAreEquals(-BigDecimal.ONE_TENTH, BigDecimal.of(-1) / BigDecimal.of(10))

        assertBigDecimalsAreEquals(BigDecimal.ZERO, BigDecimal.of(0.0))
        assertBigDecimalsAreEquals(BigDecimal.ONE, BigDecimal.of(1.0))
        assertBigDecimalsAreEquals(BigDecimal.TWO, BigDecimal.of(2.0))
        assertBigDecimalsAreEquals(BigDecimal.TEN, BigDecimal.of(10.0))
        assertBigDecimalsAreEquals(BigDecimal.ONE_HALF, BigDecimal.of(0.5))
        assertBigDecimalsAreEquals(BigDecimal.ONE_TENTH, BigDecimal.of("0.1"))
        assertBigDecimalsAreEquals(-BigDecimal.ONE, BigDecimal.of(-1.0))
        assertBigDecimalsAreEquals(-BigDecimal.TWO, BigDecimal.of(-2.0))
        assertBigDecimalsAreEquals(-BigDecimal.TEN, BigDecimal.of(-10.0))
        assertBigDecimalsAreEquals(-BigDecimal.ONE_HALF, BigDecimal.of(-0.5))
        assertBigDecimalsAreEquals(-BigDecimal.ONE_TENTH, BigDecimal.of("-0.1"))
    }

    @Test
    fun testSum() {
        assertBigDecimalsAreEquals(BigDecimal.of(-1 + 1), BigDecimal.of(-1) + BigDecimal.ONE)
        assertBigDecimalsAreEquals(BigDecimal.of(0 + 1), BigDecimal.of(0) + BigDecimal.ONE)
        assertBigDecimalsAreEquals(BigDecimal.of(1 + 1), BigDecimal.of(1) + BigDecimal.ONE)

        assertBigDecimalsAreEquals(BigDecimal.of(-1 + 2), BigDecimal.of(-1) + BigDecimal.TWO)
        assertBigDecimalsAreEquals(BigDecimal.of(0 + 2), BigDecimal.of(0) + BigDecimal.TWO)
        assertBigDecimalsAreEquals(BigDecimal.of(1 + 2), BigDecimal.of(1) + BigDecimal.TWO)

        assertBigDecimalsAreEquals(BigDecimal.of(-1 + 10), BigDecimal.of(-1) + BigDecimal.TEN)
        assertBigDecimalsAreEquals(BigDecimal.of(0 + 10), BigDecimal.of(0) + BigDecimal.TEN)
        assertBigDecimalsAreEquals(BigDecimal.of(1 + 10), BigDecimal.of(1) + BigDecimal.TEN)

        assertReprEquals(
            PI_REPR,
            BigDecimal.PI + BigDecimal.ZERO,
        )
        assertReprEquals(
            "4" + PI_REPR.subSequence(1, PI_REPR.length),
            BigDecimal.PI + BigDecimal.ONE,
        )
        assertReprEquals(
            "5" + PI_REPR.subSequence(1, PI_REPR.length),
            BigDecimal.PI + BigDecimal.TWO,
        )
        assertReprEquals(
            "13" + PI_REPR.subSequence(1, PI_REPR.length),
            BigDecimal.PI + BigDecimal.TEN,
        )
        assertReprEquals(
            PI_REPR.replace(".1", ".6"),
            BigDecimal.PI + BigDecimal.ONE_HALF,
        )
        assertReprEquals(
            PI_REPR.replace(".1", ".2"),
            BigDecimal.PI + BigDecimal.ONE_TENTH,
        )

        assertReprEquals(
            E_REPR,
            BigDecimal.E + BigDecimal.ZERO,
        )
        assertReprEquals(
            "3" + E_REPR.subSequence(1, E_REPR.length),
            BigDecimal.E + BigDecimal.ONE,
        )
        assertReprEquals(
            "4" + E_REPR.subSequence(1, E_REPR.length),
            BigDecimal.E + BigDecimal.TWO,
        )
        assertReprEquals(
            "12" + E_REPR.subSequence(1, E_REPR.length),
            BigDecimal.E + BigDecimal.TEN,
        )
        assertReprEquals(
            E_REPR.replace("2.7", "3.2"),
            BigDecimal.E + BigDecimal.ONE_HALF,
        )
        assertReprEquals(
            E_REPR.replace(".7", ".8"),
            BigDecimal.E + BigDecimal.ONE_TENTH,
        )
    }

    @Test
    fun testSub() {
        assertBigDecimalsAreEquals(BigDecimal.of(-1 - 1), BigDecimal.of(-1) - BigDecimal.ONE)
        assertBigDecimalsAreEquals(BigDecimal.of(0 - 1), BigDecimal.of(0) - BigDecimal.ONE)
        assertBigDecimalsAreEquals(BigDecimal.of(1 - 1), BigDecimal.of(1) - BigDecimal.ONE)

        assertBigDecimalsAreEquals(BigDecimal.of(-1 - 2), BigDecimal.of(-1) - BigDecimal.TWO)
        assertBigDecimalsAreEquals(BigDecimal.of(0 - 2), BigDecimal.of(0) - BigDecimal.TWO)
        assertBigDecimalsAreEquals(BigDecimal.of(1 - 2), BigDecimal.of(1) - BigDecimal.TWO)

        assertBigDecimalsAreEquals(BigDecimal.of(-1 - 10), BigDecimal.of(-1) - BigDecimal.TEN)
        assertBigDecimalsAreEquals(BigDecimal.of(0 - 10), BigDecimal.of(0) - BigDecimal.TEN)
        assertBigDecimalsAreEquals(BigDecimal.of(1 - 10), BigDecimal.of(1) - BigDecimal.TEN)

        assertReprEquals(
            PI_REPR,
            BigDecimal.PI - BigDecimal.ZERO,
        )
        assertReprEquals(
            "2" + PI_REPR.subSequence(1, PI_REPR.length),
            BigDecimal.PI - BigDecimal.ONE,
        )
        assertReprEquals(
            "1" + PI_REPR.subSequence(1, PI_REPR.length),
            BigDecimal.PI - BigDecimal.TWO,
        )
        assertReprEquals(
            "-6.85840734641020676153735661672049711580283060062489417902505540769218359371379100137196517465788" +
                "293201785191348671769335290615539044941776827464059187151888254971589729806147889444035537705104" +
                "5069618035571189024334066553871524351766213216834728798090854352",
            BigDecimal.PI - BigDecimal.TEN,
        )
        assertReprEquals(
            PI_REPR.replace("3.1", "2.6"),
            BigDecimal.PI - BigDecimal.ONE_HALF,
        )
        assertReprEquals(
            PI_REPR.replace(".1", ".0"),
            BigDecimal.PI - BigDecimal.ONE_TENTH,
        )

        assertReprEquals(
            E_REPR,
            BigDecimal.E - BigDecimal.ZERO,
        )
        assertReprEquals(
            E_REPR.replace("2.7", "1.7"),
            BigDecimal.E - BigDecimal.ONE,
        )
        assertReprEquals(
            E_REPR.replace("2.", "0."),
            BigDecimal.E - BigDecimal.TWO,
        )
        assertReprEquals(
            "-7.28171817154095476463971252864733750224275290630004042503303237227592336964645240542861782147483" +
                "357257253360806799694007818258640337095642709966570473940436926186767137205650923676617011924680" +
                "4748980988426165812069297845910850065115832490755238539331917736",
            BigDecimal.E - BigDecimal.TEN,
        )
        assertReprEquals(
            E_REPR.replace("2.7", "2.2"),
            BigDecimal.E - BigDecimal.ONE_HALF,
        )
        assertReprEquals(
            E_REPR.replace("2.7", "2.6"),
            BigDecimal.E - BigDecimal.ONE_TENTH,
        )

        log { "Notice that Double.MIN_VALUE=${Double.MIN_VALUE}" }
        assertReprEquals(
            BigDecimal.of(DOUBLE_MIN_VALUE_REPR) - BigDecimal.ONE,
            BigDecimal.of(Double.MIN_VALUE) - BigDecimal.ONE,
        )
    }

    @Test
    fun testDecimalParsing() {
        assertBigDecimalsAreEquals(BigDecimal.ZERO, BigDecimal.of("0.0"))
        assertBigDecimalsAreEquals(BigDecimal.ONE, BigDecimal.of("1.0"))
        assertBigDecimalsAreEquals(BigDecimal.TWO, BigDecimal.of("2.0"))
        assertBigDecimalsAreEquals(BigDecimal.TEN, BigDecimal.of("10.0"))
        assertBigDecimalsAreEquals(BigDecimal.ONE_HALF, BigDecimal.of("0.5"))
        assertBigDecimalsAreEquals(BigDecimal.ONE_TENTH, BigDecimal.of("0.1"))
        assertBigDecimalsAreEquals(-BigDecimal.ONE, BigDecimal.of("-1.0"))
        assertBigDecimalsAreEquals(-BigDecimal.TWO, BigDecimal.of("-2.0"))
        assertBigDecimalsAreEquals(-BigDecimal.TEN, BigDecimal.of("-10.0"))
        assertBigDecimalsAreEquals(-BigDecimal.ONE_HALF, BigDecimal.of("-0.5"))
        assertBigDecimalsAreEquals(-BigDecimal.ONE_TENTH, BigDecimal.of("-0.1"))

        assertBigDecimalsAreEquals(
            BigDecimal.PI,
            BigDecimal.of(PI_REPR),
        )
        assertBigDecimalsAreEquals(
            -BigDecimal.PI,
            BigDecimal.of("-$PI_REPR"),
        )
        assertBigDecimalsAreEquals(
            BigDecimal.E,
            BigDecimal.of(E_REPR),
        )
        assertBigDecimalsAreEquals(
            -BigDecimal.E,
            BigDecimal.of("-$E_REPR"),
        )

        assertBigDecimalsAreEquals(
            BigDecimal.of(1, 1000),
            BigDecimal.of("0." + (0 until 999).map { "0" }.joinToString("") + "1"),
        )
    }

    @Test
    fun testSqrt() {
        assertBigDecimalsAreEquals(BigDecimal.ZERO, BigDecimal.ZERO.sqrt())
        assertBigDecimalsAreEquals(BigDecimal.ONE, BigDecimal.ONE.sqrt())
        assertBigDecimalsAreEquals(BigDecimal.of(2), BigDecimal.of(4).sqrt())
        assertBigDecimalsAreEquals(BigDecimal.of("1.1"), BigDecimal.of("1.21").sqrt())
    }
}
