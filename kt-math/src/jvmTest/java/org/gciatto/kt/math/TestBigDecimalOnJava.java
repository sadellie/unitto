package org.gciatto.kt.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.gciatto.kt.math.JvmUtils.toJava;
import static org.gciatto.kt.math.JvmUtils.toKotlin;
import static org.junit.Assert.assertEquals;

public class TestBigDecimalOnJava {
    @Test
    public void testFactoryMethods() {
        assertEquals(BigDecimal.ONE, BigDecimal.of(1));
        assertEquals(BigDecimal.ONE, BigDecimal.of(1.0).stripTrailingZeros());
        assertEquals(BigDecimal.ONE, BigDecimal.of(1.0f).stripTrailingZeros());
        assertEquals(BigDecimal.ONE, BigDecimal.of(BigInteger.of(1)));
        assertEquals(BigDecimal.ONE, BigDecimal.of("1"));
        assertEquals(BigDecimal.ONE, BigDecimal.of("1.0").stripTrailingZeros());
        assertEquals(BigDecimal.ONE, BigDecimal.of("1.00").stripTrailingZeros());

        assertEquals(BigDecimal.ZERO, BigDecimal.of(0));
        assertEquals(BigDecimal.ZERO, BigDecimal.of(0.0).stripTrailingZeros());
        assertEquals(BigDecimal.ZERO, BigDecimal.of(0.0f).stripTrailingZeros());
        assertEquals(BigDecimal.ZERO, BigDecimal.of(BigInteger.of(0)));
        assertEquals(BigDecimal.ZERO, BigDecimal.of("0"));
        assertEquals(BigDecimal.ZERO, BigDecimal.of("0.0").stripTrailingZeros());
        assertEquals(BigDecimal.ZERO, BigDecimal.of("0.00").stripTrailingZeros());

        assertEquals(BigDecimal.ONE_HALF, BigDecimal.of(0.5));
        assertEquals(BigDecimal.ONE_HALF, BigDecimal.of(0.50).stripTrailingZeros());
        assertEquals(BigDecimal.ONE_HALF, BigDecimal.of(0.5f));
        assertEquals(BigDecimal.ONE_HALF, BigDecimal.of("0.5"));
        assertEquals(BigDecimal.ONE_HALF, BigDecimal.of("0.50").stripTrailingZeros());

        assertEquals(BigDecimal.of("0.1000000000000000055511151231257827021181583404541015625"), BigDecimal.of(0.1));
        assertEquals(BigDecimal.of("0.1000000000000000055511151231257827021181583404541015625"), BigDecimal.of(0.10));
        assertEquals(BigDecimal.of("0.100000001490116119384765625"), BigDecimal.of(0.1f));
        assertEquals(BigDecimal.ONE_TENTH, BigDecimal.of("0.1"));
        assertEquals(BigDecimal.of(10, 2), BigDecimal.of("0.10"));

        assertEquals(BigDecimal.of(ContantsKt.PI_REPR), BigDecimal.PI);

        assertEquals(BigDecimal.of(ContantsKt.E_REPR), BigDecimal.E);
    }

    @Test
    public void testConversions() {
        List<BigDecimal> toTest = Arrays.asList(
                BigDecimal.ZERO,
                BigDecimal.ONE,
                BigDecimal.TWO,
                BigDecimal.E,
                BigDecimal.PI,
                BigDecimal.ONE_HALF,
                BigDecimal.ONE_TENTH
        );

        for (BigDecimal x : toTest) {
            BigDecimal converted = toKotlin(toJava(x));
            assertEquals(x, converted);
        }
    }
}
