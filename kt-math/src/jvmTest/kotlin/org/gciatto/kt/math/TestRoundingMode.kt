package org.gciatto.kt.math

import kotlin.test.Test
import kotlin.test.assertEquals
import java.math.RoundingMode as JavaRoundingMode

class TestRoundingMode {
    @Test
    @Suppress("DEPRECATION")
    fun testOldModesAreCorrect() {
        assertEquals(JavaRoundingMode.CEILING.value, RoundingMode.CEILING.value)
        assertEquals(JavaRoundingMode.DOWN.value, RoundingMode.DOWN.value)
        assertEquals(JavaRoundingMode.FLOOR.value, RoundingMode.FLOOR.value)
        assertEquals(JavaRoundingMode.HALF_DOWN.value, RoundingMode.HALF_DOWN.value)
        assertEquals(JavaRoundingMode.HALF_EVEN.value, RoundingMode.HALF_EVEN.value)
        assertEquals(JavaRoundingMode.HALF_UP.value, RoundingMode.HALF_UP.value)
        assertEquals(JavaRoundingMode.UP.value, RoundingMode.UP.value)
        assertEquals(JavaRoundingMode.UNNECESSARY.value, RoundingMode.UNNECESSARY.value)
    }

    private val JavaRoundingMode.value: Int
        get() =
            JavaRoundingMode::class.java.getDeclaredField("oldMode").let {
                if (it.trySetAccessible()) {
                    it.getInt(this)
                } else {
                    System.err.println("w: cannot get $this.oldValue")
                    this.ordinal
                }
            }
}
