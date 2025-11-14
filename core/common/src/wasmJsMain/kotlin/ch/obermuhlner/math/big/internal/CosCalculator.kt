package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Calculates cosinus using the Maclaurin series.
 *
 * See [Wikipedia: Taylorreihe](https://de.wikipedia.org/wiki/Taylorreihe)
 *
 * No argument checking or optimizations are done. This implementation is **not** intended to be
 * called directly.
 */
internal class CosCalculator private constructor() : SeriesCalculator(true) {
  private var n = 0
  private var negative = false
  private var factorial2n = BigRational.ONE

  override fun calculateNextFactor() {
    n++
    factorial2n = factorial2n.multiply(2 * n - 1).multiply(2 * n)
    negative = !negative
  }

  override fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator {
    return PowerTwoNIterator(x, mathContext)
  }

  override val currentFactor: BigRational
    get() {
      var factor = factorial2n.reciprocal()
      if (negative) {
        factor = factor.negate()
      }
      return factor
    }

  companion object {
    val INSTANCE: CosCalculator = CosCalculator()
  }
}
