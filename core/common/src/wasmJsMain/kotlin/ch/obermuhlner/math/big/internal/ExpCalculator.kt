package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Calculates exp using the Maclaurin series.
 *
 * See [Wikipedia: Taylorreihe](https://de.wikipedia.org/wiki/Taylorreihe)
 *
 * No argument checking or optimizations are done. This implementation is **not** intended to be
 * called directly.
 */
internal class ExpCalculator private constructor() : SeriesCalculator() {
  private var n = 0
  override var currentFactor: BigRational = BigRational.ONE
    private set

  override fun calculateNextFactor() {
    n++
    currentFactor = currentFactor.divide(n)
  }

  override fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator {
    return PowerNIterator(x, mathContext)
  }

  companion object {
    val INSTANCE: ExpCalculator = ExpCalculator()
  }
}
