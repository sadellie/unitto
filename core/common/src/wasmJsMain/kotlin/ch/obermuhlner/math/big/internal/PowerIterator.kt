package ch.obermuhlner.math.big.internal

import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal

/**
 * Iterator over the powers of a value x.
 *
 * This API allows to efficiently calculate the various powers of x in a taylor series by storing
 * intermediate results.
 *
 * For example x<sup>n</sup> can be calculated using one multiplication by storing the previously
 * calculated x<sup>n-1</sup> and x.
 *
 * [.getCurrentPower] will be called first to retrieve the initial value.
 *
 * For later iterations [.calculateNextPower] will be called before [.getCurrentPower].
 */
internal interface PowerIterator {
  /**
   * Returns the current power.
   *
   * @return the current power.
   */
  val currentPower: BigDecimal

  /** Calculates the next power. */
  fun calculateNextPower()
}
