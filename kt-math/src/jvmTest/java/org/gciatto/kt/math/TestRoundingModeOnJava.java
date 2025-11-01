package org.gciatto.kt.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRoundingModeOnJava {
    @Test
    public void testFactoryMethods() {
        assertEquals(RoundingMode.UP, RoundingMode.valueOf(0));
        assertEquals(RoundingMode.DOWN, RoundingMode.valueOf(1));
        assertEquals(RoundingMode.CEILING, RoundingMode.valueOf(2));
        assertEquals(RoundingMode.FLOOR, RoundingMode.valueOf(3));
        assertEquals(RoundingMode.HALF_UP, RoundingMode.valueOf(4));
        assertEquals(RoundingMode.HALF_DOWN, RoundingMode.valueOf(5));
        assertEquals(RoundingMode.HALF_EVEN, RoundingMode.valueOf(6));
        assertEquals(RoundingMode.UNNECESSARY, RoundingMode.valueOf(7));
    }
}
