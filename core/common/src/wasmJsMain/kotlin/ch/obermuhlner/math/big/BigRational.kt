package ch.obermuhlner.math.big

import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KBigInteger as BigInteger
import com.sadellie.unitto.core.common.KMathContext as MathContext
import kotlin.math.ln

/**
 * A rational number represented as a quotient of two values.
 *
 * Basic calculations with rational numbers (+ - * /) have no loss of precision. This allows to use
 * [BigRational] as a replacement for [BigDecimal] if absolute accuracy is desired.
 *
 * [Wikipedia: Rational number](http://en.wikipedia.org/wiki/Rational_number)
 *
 * The values are internally stored as [BigDecimal] (for performance optimizations) but represented
 * as [BigInteger] (for mathematical correctness) when accessed with [.getNumeratorBigInteger] and
 * [.getDenominatorBigInteger].
 *
 * The following basic calculations have no loss of precision:
 * * [.add]
 * * [.subtract]
 * * [.multiply]
 * * [.divide]
 * * [.pow]
 *
 * The following calculations are special cases of the ones listed above and have no loss of
 * precision:
 * * [.negate]
 * * [.reciprocal]
 * * [.increment]
 * * [.decrement]
 *
 * Any [BigRational] value can be converted into an arbitrary [precision][.withPrecision] (number of
 * significant digits) or [scale][.withScale] (number of digits after the decimal point).
 */
internal class BigRational private constructor(num: BigDecimal, denom: BigDecimal) :
  Number(), Comparable<BigRational?> {
  private val numerator: BigDecimal

  private val denominator: BigDecimal

  private constructor(value: Int) : this(BigDecimal.valueOf(value.toLong()), BigDecimal.ONE)

  /**
   * Returns the numerator of this rational number as BigDecimal.
   *
   * @return the numerator as BigDecimal
   */
  fun getNumerator(): BigDecimal {
    return numerator
  }

  /**
   * Returns the denominator of this rational number as BigDecimal.
   *
   * Guaranteed to not be 0.
   *
   * Guaranteed to be positive.
   *
   * @return the denominator as BigDecimal
   */
  fun getDenominator(): BigDecimal {
    return denominator
  }

  /**
   * Negates this rational number (inverting the sign).
   *
   * The result has no loss of precision.
   *
   * Examples:
   * * `BigRational.valueOf(3.5).negate()` returns `BigRational.valueOf(-3.5)`
   *
   * @return the negated rational number
   */
  fun negate(): BigRational {
    if (isZero) {
      return this
    }

    return of(numerator.negate(), denominator)
  }

  /**
   * Calculates the reciprocal of this rational number (1/x).
   *
   * The result has no loss of precision.
   *
   * Examples:
   * * `BigRational.valueOf(0.5).reciprocal()` returns `BigRational.valueOf(2)`
   * * `BigRational.valueOf(-2).reciprocal()` returns `BigRational.valueOf(-0.5)`
   *
   * @return the reciprocal rational number
   * @throws ArithmeticException if this number is 0 (division by zero)
   */
  fun reciprocal(): BigRational {
    return of(denominator, numerator)
  }

  /**
   * Calculates the multiplication (*) of this rational number and the specified argument.
   *
   * The result has no loss of precision.
   *
   * @param value the rational number to multiply
   * @return the resulting rational number
   */
  fun multiply(value: BigRational): BigRational {
    if (isZero || value.isZero) {
      return ZERO
    }
    if (equals(ONE)) {
      return value
    }
    if (value == ONE) {
      return this
    }

    val n: BigDecimal = numerator.multiply(value.numerator)
    val d: BigDecimal = denominator.multiply(value.denominator)
    return of(n, d)
  }

  // private, because we want to hide that we use BigDecimal internally
  private fun multiply(value: BigDecimal): BigRational {
    val n: BigDecimal = numerator.multiply(value)
    val d: BigDecimal = denominator
    return of(n, d)
  }

  /**
   * Calculates the multiplication (*) of this rational number and the specified argument.
   *
   * This is functionally identical to `this.multiply(BigRational.valueOf(value))` but slightly
   * faster.
   *
   * The result has no loss of precision.
   *
   * @param value the [BigInteger] to multiply
   * @return the resulting rational number
   */
  fun multiply(value: BigInteger): BigRational {
    if (isZero || value.signum == 0) {
      return ZERO
    }
    if (equals(ONE)) {
      return valueOf(value)
    }
    if (value == BigInteger.ONE) {
      return this
    }

    return multiply(BigDecimal(value))
  }

  /**
   * Calculates the multiplication (*) of this rational number and the specified argument.
   *
   * This is functionally identical to `this.multiply(BigRational.valueOf(value))` but slightly
   * faster.
   *
   * The result has no loss of precision.
   *
   * @param value the int value to multiply
   * @return the resulting rational number
   */
  fun multiply(value: Int): BigRational {
    return multiply(BigInteger.valueOf(value.toLong()))
  }

  /**
   * Calculates the division (/) of this rational number and the specified argument.
   *
   * The result has no loss of precision.
   *
   * @param value the rational number to divide (0 is not allowed)
   * @return the resulting rational number
   * @throws ArithmeticException if the argument is 0 (division by zero)
   */
  fun divide(value: BigRational): BigRational {
    if (value == ONE) {
      return this
    }

    val n: BigDecimal = numerator.multiply(value.denominator)
    val d: BigDecimal = denominator.multiply(value.numerator)
    return of(n, d)
  }

  private fun divide(value: BigDecimal): BigRational {
    val n: BigDecimal = numerator
    val d: BigDecimal = denominator.multiply(value)
    return of(n, d)
  }

  /**
   * Calculates the division (/) of this rational number and the specified argument.
   *
   * This is functionally identical to `this.divide(BigRational.valueOf(value))` but slightly
   * faster.
   *
   * The result has no loss of precision.
   *
   * @param value the [BigInteger] to divide (0 is not allowed)
   * @return the resulting rational number
   * @throws ArithmeticException if the argument is 0 (division by zero)
   */
  fun divide(value: BigInteger): BigRational {
    if (value == BigInteger.ONE) {
      return this
    }

    return divide(BigDecimal(value))
  }

  /**
   * Calculates the division (/) of this rational number and the specified argument.
   *
   * This is functionally identical to `this.divide(BigRational.valueOf(value))` but slightly
   * faster.
   *
   * The result has no loss of precision.
   *
   * @param value the int value to divide (0 is not allowed)
   * @return the resulting rational number
   * @throws ArithmeticException if the argument is 0 (division by zero)
   */
  fun divide(value: Int): BigRational {
    return divide(BigInteger.valueOf(value.toLong()))
  }

  val isZero: Boolean
    /**
     * Returns whether this rational number is zero.
     *
     * @return `true` if this rational number is zero (0), `false` if it is not zero
     */
    get() = numerator.signum == 0

  private val isIntegerInternal: Boolean
    /**
     * Returns whether this rational number is an integer number without fraction part.
     *
     * Will return `false` if this number is not reduced to the integer representation yet (e.g. 4/4
     * or 4/2)
     *
     * @return `true` if this rational number is an integer number, `false` if it has a fraction
     *   part
     * @see .isInteger
     */
    get() = denominator.compareTo(BigDecimal.ONE) == 0

  // TODO what is precision of a rational?
  private fun precision(): Int {
    return countDigits(numerator.toBigInteger()) + countDigits(denominator.toBigInteger())
  }

  /**
   * Returns this rational number as a double value.
   *
   * If the rational number cannot be represented as double then one of the following results will
   * be returned:
   * * &gt; `Double.MAX_VALUE` returns [Double.POSITIVE_INFINITY]
   * * &lt; `-Double.MAX_VALUE` returns [Double.NEGATIVE_INFINITY]
   * * &lt; `Double.MIN_VALUE` returns `+0.0`
   * * &gt; `-Double.MIN_VALUE` returns `-0.0`
   *
   * @return the double value
   */
  override fun toDouble(): Double {
    return toBigDecimal().toDouble()
  }

  /**
   * Returns this rational number as a float value.
   *
   * If the rational number cannot be represented as float then one of the following results will be
   * returned:
   * * &gt; `Float.MAX_VALUE` returns [Float.POSITIVE_INFINITY]
   * * &lt; `-Float.MAX_VALUE` returns [Float.NEGATIVE_INFINITY]
   * * &lt; `Float.MIN_VALUE` returns `+0.0f`
   * * &gt; `-Float.MIN_VALUE` returns `-0.0f`
   *
   * @return the float value
   */
  override fun toFloat(): Float {
    return toBigDecimal().toFloat()
  }

  /**
   * Returns this rational number as a [BigDecimal].
   *
   * @return the [BigDecimal] value
   */
  fun toBigDecimal(): BigDecimal {
    val precision: Int = kotlin.math.max(precision(), MathContext.DECIMAL128.precision)
    return toBigDecimal(MathContext(precision))
  }

  /**
   * Returns this rational number as a [BigDecimal] with the precision specified by the
   * [MathContext].
   *
   * @param mc the [MathContext] specifying the precision of the calculated result
   * @return the [BigDecimal]
   */
  fun toBigDecimal(mc: MathContext): BigDecimal {
    return numerator.divide(denominator, mc)
  }

  override fun compareTo(other: BigRational?): Int {
    if (other == null) return -1
    if (this === other) {
      return 0
    }
    return numerator.multiply(other.denominator).compareTo(denominator.multiply(other.numerator))
  }

  override fun hashCode(): Int {
    if (isZero) {
      return 0
    }
    return numerator.hashCode() + denominator.hashCode()
  }

  override fun toByte(): Byte {
    TODO("Not yet implemented")
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) {
      return true
    }

    if (other !is BigRational) {
      return false
    }

    if (numerator.compareTo(other.numerator) != 0) {
      return false
    }
    return denominator.compareTo(other.denominator) == 0
  }

  override fun toString(): String {
    if (isZero) {
      return "0"
    }
    if (isIntegerInternal) {
      return numerator.toString()
    }
    return toBigDecimal().toString()
  }

  init {
    var n: BigDecimal = num
    var d: BigDecimal = denom

    if (d.signum == 0) {
      throw ArithmeticException("Divide by zero")
    }

    if (d.signum < 0) {
      n = n.negate()
      d = d.negate()
    }

    numerator = n
    denominator = d
  }

  override fun toInt(): Int {
    return toBigDecimal().toInt()
  }

  override fun toLong(): Long {
    return toBigDecimal().toLong()
  }

  override fun toShort(): Short {
    TODO("Not yet implemented")
  }

  companion object {
    /** The value 0 as [BigRational]. */
    val ZERO: BigRational = BigRational(0)

    /** The value 1 as [BigRational]. */
    val ONE: BigRational = BigRational(1)

    private fun countDigits(number: BigInteger): Int {
      val factor = ln(2.0) / ln(10.0)
      val digitCount: Int = (factor * number.bitLength() + 1).toInt()
      if (BigInteger.TEN.pow(digitCount - 1) > number) {
        return digitCount - 1
      }
      return digitCount
    }

    /**
     * Creates a rational number of the specified numerator/denominator BigInteger values.
     *
     * @param numerator the numerator [BigInteger] value
     * @param denominator the denominator [BigInteger] value (0 not allowed)
     * @return the rational number
     * @throws ArithmeticException if the denominator is 0 (division by zero)
     */
    fun valueOf(numerator: BigInteger, denominator: BigInteger): BigRational {
      return of(BigDecimal(numerator), BigDecimal(denominator))
    }

    /**
     * Creates a rational number of the specified [BigInteger] value.
     *
     * @param value the [BigInteger] value
     * @return the rational number
     */
    fun valueOf(value: BigInteger): BigRational {
      if (value.compareTo(BigInteger.ZERO) == 0) {
        return ZERO
      }
      if (value.compareTo(BigInteger.ONE) == 0) {
        return ONE
      }
      return valueOf(value, BigInteger.ONE)
    }

    private fun of(numerator: BigDecimal, denominator: BigDecimal): BigRational {
      if (numerator.signum == 0 && denominator.signum != 0) {
        return ZERO
      }
      if (numerator.compareTo(BigDecimal.ONE) == 0 && denominator.compareTo(BigDecimal.ONE) == 0) {
        return ONE
      }
      return BigRational(numerator, denominator)
    }
  }
}
