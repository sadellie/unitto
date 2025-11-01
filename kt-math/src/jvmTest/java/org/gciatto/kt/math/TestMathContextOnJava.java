package org.gciatto.kt.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMathContextOnJava {
    @Test
    public void testFactoryMethods() {
        assertEquals(MathContext.UNLIMITED, new MathContext(0, RoundingMode.HALF_UP));
        assertEquals(MathContext.DECIMAL32, new MathContext(7, RoundingMode.HALF_EVEN));
        assertEquals(MathContext.DECIMAL64, new MathContext(16, RoundingMode.HALF_EVEN));
        assertEquals(MathContext.DECIMAL128, new MathContext(34, RoundingMode.HALF_EVEN));
    }
}
