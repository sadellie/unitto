package org.gciatto.kt.math

import kotlin.test.Test

class TestRepresentations {
  @Test
  fun testDoubleMinValueRepr() {
    val x = BigDecimal.of(Double.MIN_VALUE)
    log { x.toPlainString() }
    assertBigDecimalsAreEquals(BigDecimal.of(DOUBLE_MIN_VALUE_REPR), x)
  }

  @Test
  fun testDoubleMaxValueRepr() {
    val x = BigDecimal.of(Double.MAX_VALUE)
    log { x.toPlainString() }
    assertBigDecimalsAreEquals(BigDecimal.of(DOUBLE_MAX_VALUE_REPR), x)
  }

  @Test
  fun testFloatMinValueRepr() {
    val x = BigDecimal.of(Float.MIN_VALUE)
    log { x.toPlainString() }
    assertBigDecimalsAreEquals(BigDecimal.of(FLOAT_MIN_VALUE_REPR), x)
  }

  @Test
  fun testFloatMaxValueRepr() {
    val x = BigDecimal.of(Float.MAX_VALUE)
    log { x.toPlainString() }
    assertBigDecimalsAreEquals(BigDecimal.of(FLOAT_MAX_VALUE_REPR), x)
  }
}
