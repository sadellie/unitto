package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Calculates arc sinus using the Maclaurin series.
 *
 * See [Wikipedia: Taylorreihe](https://de.wikipedia.org/wiki/Taylorreihe)
 *
 * No argument checking or optimizations are done. This implementation is **not** intended to be
 * called directly.
 */
internal class AsinCalculator private constructor() : SeriesCalculator() {
  private var n = 0
  private var factorial2n = BigRational.ONE
  private var factorialN = BigRational.ONE
  private var fourPowerN = BigRational.ONE

  override val currentFactor: BigRational
    get() {
      val factor =
        factorial2n.divide(fourPowerN.multiply(factorialN).multiply(factorialN).multiply(2 * n + 1))
      return factor
    }

  override fun calculateNextFactor() {
    n++
    factorial2n = factorial2n.multiply(2 * n - 1).multiply(2 * n)
    factorialN = factorialN.multiply(n)
    fourPowerN = fourPowerN.multiply(4)
  }

  override fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator {
    return PowerTwoNPlusOneIterator(x, mathContext)
  }

  companion object {
    val INSTANCE: AsinCalculator = AsinCalculator()
  }
}
