package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Calculates sinus hyperbolicus using the Taylor series.
 *
 * See [Wikipedia: Taylor series](https://en.wikipedia.org/wiki/Taylor_series)
 *
 * No argument checking or optimizations are done. This implementation is **not** intended to be
 * called directly.
 */
internal class SinhCalculator private constructor() : SeriesCalculator(true) {
  private var n = 0

  private var factorial2nPlus1 = BigRational.ONE

  override val currentFactor: BigRational
    get() = factorial2nPlus1.reciprocal()

  override fun calculateNextFactor() {
    n++
    factorial2nPlus1 = factorial2nPlus1.multiply(2 * n)
    factorial2nPlus1 = factorial2nPlus1.multiply(2 * n + 1)
  }

  override fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator {
    return PowerTwoNPlusOneIterator(x, mathContext)
  }

  companion object {
    val INSTANCE: SinhCalculator = SinhCalculator()
  }
}
