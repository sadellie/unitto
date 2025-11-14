package ch.obermuhlner.math.big.internal

import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/** [PowerIterator] to calculate x<sup>n</sup>. */
internal class PowerNIterator(private val x: BigDecimal, private val mathContext: MathContext) :
  PowerIterator {

  private var powerOfX: BigDecimal

  init {
    powerOfX = BigDecimal.ONE
  }

  override val currentPower: BigDecimal
    get() = powerOfX

  override fun calculateNextPower() {
    powerOfX = powerOfX.multiply(x, mathContext)
  }
}
