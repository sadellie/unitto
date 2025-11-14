package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Calculates sinus using the Maclaurin series.
 *
 * See [Wikipedia: Taylorreihe](https://de.wikipedia.org/wiki/Taylorreihe)
 *
 * No argument checking or optimizations are done. This implementation is **not** intended to be
 * called directly.
 */
internal class SinCalculator private constructor() : SeriesCalculator(true) {
  private var n = 0
  private var negative = false
  private var factorial2nPlus1 = BigRational.ONE

  override val currentFactor: BigRational
    get() {
      var factor = factorial2nPlus1.reciprocal()
      if (negative) {
        factor = factor.negate()
      }
      return factor
    }

  override fun calculateNextFactor() {
    n++
    factorial2nPlus1 = factorial2nPlus1.multiply(2 * n)
    factorial2nPlus1 = factorial2nPlus1.multiply(2 * n + 1)
    negative = !negative
  }

  override fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator {
    return PowerTwoNPlusOneIterator(x, mathContext)
  }

  companion object {
    val INSTANCE: SinCalculator = SinCalculator()
  }
}
