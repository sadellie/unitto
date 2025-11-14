package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Calculates cosinus hyperbolicus using the Taylor series.
 *
 * See [Wikipedia: Taylor series](https://en.wikipedia.org/wiki/Taylor_series)
 *
 * No argument checking or optimizations are done. This implementation is **not** intended to be
 * called directly.
 */
internal class CoshCalculator private constructor() : SeriesCalculator(true) {
  private var n = 0

  private var factorial2n = BigRational.ONE

  override val currentFactor: BigRational
    get() = factorial2n.reciprocal()

  override fun calculateNextFactor() {
    n++
    factorial2n = factorial2n.multiply(2 * n - 1).multiply(2 * n)
  }

  override fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator {
    return PowerTwoNIterator(x, mathContext)
  }

  companion object {
    val INSTANCE: CoshCalculator = CoshCalculator()
  }
}
