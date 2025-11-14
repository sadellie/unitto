package ch.obermuhlner.math.big.internal

import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/** [PowerIterator] to calculate x<sup>2*n+1</sup>. */
internal class PowerTwoNPlusOneIterator(x: BigDecimal, private val mathContext: MathContext) :
  PowerIterator {

  private val xPowerTwo: BigDecimal = x.multiply(x, mathContext)

  private var powerOfX: BigDecimal

  init {
    powerOfX = x
  }

  override val currentPower: BigDecimal
    get() = powerOfX

  override fun calculateNextPower() {
    powerOfX = powerOfX.multiply(xPowerTwo, mathContext)
  }
}
