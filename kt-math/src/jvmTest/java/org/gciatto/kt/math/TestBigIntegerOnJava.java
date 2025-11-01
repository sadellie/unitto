package org.gciatto.kt.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.gciatto.kt.math.JvmUtils.toJava;
import static org.gciatto.kt.math.JvmUtils.toKotlin;
import static org.junit.Assert.assertEquals;

public class TestBigIntegerOnJava {
    @Test
    public void testFactoryMethods() {
        assertEquals(BigInteger.ONE, BigInteger.of(1));
        assertEquals(BigInteger.ONE, BigInteger.of(1L));
        assertEquals(BigInteger.ONE, BigInteger.of(new int[]{1}));
        assertEquals(BigInteger.ONE, BigInteger.of("1"));
        assertEquals(BigInteger.ONE, BigInteger.of("1", 2));

        assertEquals(BigInteger.ZERO, BigInteger.of(0));
        assertEquals(BigInteger.ZERO, BigInteger.of(0L));
        assertEquals(BigInteger.ZERO, BigInteger.of(new int[]{0}));
        assertEquals(BigInteger.ZERO, BigInteger.of("0"));
        assertEquals(BigInteger.ZERO, BigInteger.of("0", 2));

        assertEquals(BigInteger.TEN, BigInteger.of(10));
        assertEquals(BigInteger.TEN, BigInteger.of(10L));
        assertEquals(BigInteger.TEN, BigInteger.of(new int[]{10}));
        assertEquals(BigInteger.TEN, BigInteger.of("10"));
        assertEquals(BigInteger.TEN, BigInteger.of("1010", 2));

        assertEquals(BigInteger.TWO, BigInteger.of(2));
        assertEquals(BigInteger.TWO, BigInteger.of(2L));
        assertEquals(BigInteger.TWO, BigInteger.of(new int[]{2}));
        assertEquals(BigInteger.TWO, BigInteger.of("2"));
        assertEquals(BigInteger.TWO, BigInteger.of("10", 2));

        assertEquals(BigInteger.NEGATIVE_ONE, BigInteger.of(-1));
        assertEquals(BigInteger.NEGATIVE_ONE, BigInteger.of(-1L));
        assertEquals(BigInteger.NEGATIVE_ONE, BigInteger.of(new int[]{-1}));
        assertEquals(BigInteger.NEGATIVE_ONE, BigInteger.of("-1"));
        assertEquals(BigInteger.NEGATIVE_ONE, BigInteger.of("-1", 2));
    }

    @Test
    public void testConversions() {
        List<BigInteger> toTest = Arrays.asList(
                BigInteger.ZERO,
                BigInteger.ONE,
                BigInteger.TWO,
                BigInteger.TEN,
                BigInteger.NEGATIVE_ONE,
                BigInteger.of(Long.MAX_VALUE).plus(BigInteger.ONE)
        );

        for (BigInteger x : toTest) {
            BigInteger converted = toKotlin(toJava(x));
            assertEquals(x, converted);
        }
    }
}
