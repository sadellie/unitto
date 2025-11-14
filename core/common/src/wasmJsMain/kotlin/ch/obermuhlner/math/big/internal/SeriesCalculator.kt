package ch.obermuhlner.math.big.internal

import ch.obermuhlner.math.big.BigRational
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext

/**
 * Utility class to calculate taylor series efficiently until the maximum error (as defined by the
 * precision in the [MathContext] is reached.
 *
 * Stores the factors of the taylor series terms so that future calculations will be faster.
 */
internal abstract class SeriesCalculator
/** Constructs a [SeriesCalculator] that calculates single terms. */
protected constructor(private val calculateInPairs: Boolean = false) {
  private val factors: MutableList<BigRational> = ArrayList()

  /**
   * Constructs a [SeriesCalculator] with control over whether the sum terms are calculated in
   * pairs.
   *
   * Calculation of pairs is useful for taylor series where the terms alternate the sign. In these
   * cases it is more efficient to calculate two terms at once check then whether the acceptable
   * error has been reached.
   *
   * @param calculateInPairs `true` to calculate the terms in pairs, `false` to calculate single
   *   terms
   */

  /**
   * Calculates the series for the specified value x and the precision defined in the [MathContext].
   *
   * @param x the value x
   * @param mathContext the [MathContext]
   * @return the calculated result
   */
  fun calculate(x: BigDecimal, mathContext: MathContext): BigDecimal {
    val acceptableError: BigDecimal = BigDecimal.ONE.movePointLeft(mathContext.precision + 1)

    val powerIterator: PowerIterator = createPowerIterator(x, mathContext)

    var sum: BigDecimal = BigDecimal.ZERO
    var step: BigDecimal
    var i = 0
    do {
      var factor: BigRational
      var xToThePower: BigDecimal

      factor = getFactor(i)
      xToThePower = powerIterator.currentPower
      powerIterator.calculateNextPower()
      step =
        factor.getNumerator().multiply(xToThePower).divide(factor.getDenominator(), mathContext)
      i++

      if (calculateInPairs) {
        factor = getFactor(i)
        xToThePower = powerIterator.currentPower
        powerIterator.calculateNextPower()
        val step2: BigDecimal =
          factor.getNumerator().multiply(xToThePower).divide(factor.getDenominator(), mathContext)
        step = step.add(step2)
        i++
      }

      sum = sum.add(step)
      // System.out.println(sum + " " + step);
    } while (step.abs() > acceptableError)

    return sum.round(mathContext)
  }

  /**
   * Creates the [PowerIterator] used for this series.
   *
   * @param x the value x
   * @param mathContext the [MathContext]
   * @return the [PowerIterator]
   */
  protected abstract fun createPowerIterator(x: BigDecimal, mathContext: MathContext): PowerIterator

  /**
   * Returns the factor of the term with specified index.
   *
   * All mutable state of this class (and all its subclasses) must be modified in this method. This
   * method is synchronized to allow thread-safe usage of this class.
   *
   * @param index the index (starting with 0)
   * @return the factor of the specified term
   */
  protected fun getFactor(index: Int): BigRational {
    while (factors.size <= index) {
      val factor = currentFactor
      addFactor(factor)
      calculateNextFactor()
    }
    return factors[index]
  }

  private fun addFactor(factor: BigRational) {
    factors.add(factor)
  }

  /**
   * Returns the factor of the highest term already calculated.
   *
   * When called for the first time will return the factor of the first term (index 0).
   *
   * After this call the method [.calculateNextFactor] will be called to prepare for the next term.
   *
   * @return the factor of the highest term
   */
  protected abstract val currentFactor: BigRational

  /** Calculates the factor of the next term. */
  protected abstract fun calculateNextFactor()
}
