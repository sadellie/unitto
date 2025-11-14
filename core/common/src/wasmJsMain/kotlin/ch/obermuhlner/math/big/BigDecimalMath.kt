package ch.obermuhlner.math.big

import ch.obermuhlner.math.big.internal.AsinCalculator
import ch.obermuhlner.math.big.internal.CosCalculator
import ch.obermuhlner.math.big.internal.ExpCalculator
import ch.obermuhlner.math.big.internal.SinCalculator
import com.sadellie.unitto.core.common.KBigDecimal as BigDecimal
import com.sadellie.unitto.core.common.KMathContext as MathContext
import kotlin.math.ln

/** Provides advanced functions operating on [BigDecimal]s. */
internal object BigDecimalMath {
  private val TWO: BigDecimal = BigDecimal.valueOf(2)
  private val THREE: BigDecimal = BigDecimal.valueOf(3)
  private val MINUS_ONE: BigDecimal = BigDecimal.valueOf(-1)
  private val ONE_HALF: BigDecimal = BigDecimal.valueOf(0.5)

  private val ONE_HUNDRED_EIGHTY: BigDecimal = BigDecimal.valueOf(180)

  private val DOUBLE_MAX_VALUE: BigDecimal = BigDecimal.valueOf(Double.MAX_VALUE)

  private var log2Cache: BigDecimal? = null

  private var log3Cache: BigDecimal? = null

  private var log10Cache: BigDecimal? = null

  private var piCache: BigDecimal? = null

  private var eCache: BigDecimal? = null

  private val ROUGHLY_TWO_PI: BigDecimal = BigDecimal("3.141592653589793").multiply(TWO)

  private const val EXPECTED_INITIAL_PRECISION = 15

  private val factorialCache: Array<BigDecimal?> = arrayOfNulls(100)

  init {
    var result: BigDecimal = BigDecimal.ONE
    factorialCache[0] = result
    for (i in 1..<factorialCache.size) {
      result = result.multiply(BigDecimal.valueOf(i.toLong()))
      factorialCache[i] = result
    }
  }

  private fun toBigDecimalRecursive(
    chars: CharArray,
    offset: Int,
    length: Int,
    scale: Int,
    splitLength: Int,
  ): BigDecimal {
    if (length > splitLength) {
      val mid = length / 2
      val bigDecimalLeft: BigDecimal =
        toBigDecimalRecursive(chars, offset, mid, scale + length - mid, splitLength)
      val bigDecimalRight: BigDecimal =
        toBigDecimalRecursive(chars, offset + mid, length - mid, scale, splitLength)
      return bigDecimalLeft.add(bigDecimalRight)
    }
    if (length == 0) {
      return BigDecimal.ZERO
    }
    return BigDecimal(chars, offset, length).movePointRight(scale)
  }

  /**
   * Returns whether the specified [BigDecimal] value can be represented as `double`.
   *
   * If this returns `true` you can call [BigDecimal.toDouble] without fear of getting
   * [Double.POSITIVE_INFINITY] or [Double.NEGATIVE_INFINITY] as result.
   *
   * Example: `BigDecimalMath.isDoubleValue(new BigDecimal("1E309"))` returns `false`, because `new
   * BigDecimal("1E309").doubleValue()` returns `Infinity`.
   *
   * Note: This method does **not** check for possible loss of precision.
   *
   * For example `BigDecimalMath.isDoubleValue(new
   * BigDecimal("1.23400000000000000000000000000000001"))` will return `true`, because `new
   * BigDecimal("1.23400000000000000000000000000000001").doubleValue()` returns a valid double
   * value, although it loses precision and returns `1.234`.
   *
   * `BigDecimalMath.isDoubleValue(new BigDecimal("1E-325"))` will return `true` although this value
   * is smaller than [Double.MIN_VALUE] (and therefore outside the range of values that can be
   * represented as `double`) because `new BigDecimal("1E-325").doubleValue()` returns `0` which is
   * a legal value with loss of precision.
   *
   * @param value the [BigDecimal] to check
   * @return `true` if the value can be represented as `double` value
   */
  fun isDoubleValue(value: BigDecimal): Boolean {
    if (value > DOUBLE_MAX_VALUE) {
      return false
    }
    if (value < DOUBLE_MAX_VALUE.negate()) {
      return false
    }

    return true
  }

  /**
   * Returns the mantissa of the specified [BigDecimal] written as *mantissa *
   * 10<sup>exponent</sup>*.
   *
   * The mantissa is defined as having exactly 1 digit before the decimal point.
   *
   * @param value the [BigDecimal]
   * @return the mantissa
   * @see .exponent
   */
  fun mantissa(value: BigDecimal): BigDecimal {
    val exponent = exponent(value)
    if (exponent == 0) {
      return value
    }

    return value.movePointLeft(exponent)
  }

  /**
   * Returns the exponent of the specified [BigDecimal] written as *mantissa *
   * 10<sup>exponent</sup>*.
   *
   * The mantissa is defined as having exactly 1 digit before the decimal point.
   *
   * @param value the [BigDecimal]
   * @return the exponent
   * @see .mantissa
   */
  fun exponent(value: BigDecimal): Int {
    return value.precision() - value.scale() - 1
  }

  /**
   * Returns the integral part of the specified [BigDecimal] (left of the decimal point).
   *
   * @param value the [BigDecimal]
   * @return the integral part
   * @see .fractionalPart
   */
  fun integralPart(value: BigDecimal): BigDecimal {
    return value.setScale(0, BigDecimal.ROUND_DOWN)
  }

  /**
   * Returns the fractional part of the specified [BigDecimal] (right of the decimal point).
   *
   * @param value the [BigDecimal]
   * @return the fractional part
   * @see .integralPart
   */
  fun fractionalPart(value: BigDecimal): BigDecimal {
    return value.minus(integralPart(value))
  }

  /**
   * Rounds the specified [BigDecimal] to the precision of the specified [MathContext].
   *
   * This method calls [BigDecimal.round].
   *
   * @param value the [BigDecimal] to round
   * @param mathContext the [MathContext] used for the result
   * @return the rounded [BigDecimal] value
   * @see BigDecimal.round
   * @see BigDecimalMath.roundWithTrailingZeroes
   */
  fun round(value: BigDecimal, mathContext: MathContext): BigDecimal {
    return value.round(mathContext)
  }

  /**
   * Rounds the specified [BigDecimal] to the precision of the specified [MathContext] including
   * trailing zeroes.
   *
   * This method is similar to [BigDecimal.round] but does **not** remove the trailing zeroes.
   *
   * Example:
   * <pre>
   * MathContext mc = new MathContext(5);
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("1.234567"), mc));    // 1.2346
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("123.4567"), mc));    // 123.46
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("0.001234567"), mc)); // 0.0012346
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("1.23"), mc));        // 1.2300
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("1.230000"), mc));    // 1.2300
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("0.00123"), mc));     // 0.0012300
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("0"), mc));           // 0.0000
   * System.out.println(BigDecimalMath.roundWithTrailingZeroes(new BigDecimal("0.00000000"), mc));  // 0.0000
   * </pre> *
   *
   * @param value the [BigDecimal] to round
   * @param mathContext the [MathContext] used for the result
   * @return the rounded [BigDecimal] value including trailing zeroes
   * @see BigDecimal.round
   * @see BigDecimalMath.round
   */
  fun roundWithTrailingZeroes(value: BigDecimal, mathContext: MathContext): BigDecimal {
    if (value.precision() == mathContext.precision) {
      return value
    }
    if (value.signum == 0) {
      return BigDecimal.ZERO.setScale(mathContext.precision - 1)
    }

    try {
      val stripped: BigDecimal = value.stripTrailingZeros()
      val exponentStripped = exponent(stripped) // value.precision() - value.scale() - 1;
      val zero: BigDecimal =
        if (exponentStripped < -1) {
          BigDecimal.ZERO.setScale(mathContext.precision - exponentStripped)
        } else {
          BigDecimal.ZERO.setScale(mathContext.precision + exponentStripped + 1)
        }
      return stripped.add(zero, mathContext)
    } catch (_: ArithmeticException) {
      return value.round(mathContext)
    }
  }

  /**
   * Calculates the reciprocal of the specified [BigDecimal].
   *
   * @param x the [BigDecimal]
   * @param mathContext the [MathContext] used for the result
   * @return the reciprocal [BigDecimal]
   * @throws ArithmeticException if x = 0
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY` or
   *   `mc.precision == 0` and the quotient has a non-terminating decimal expansion.
   */
  fun reciprocal(x: BigDecimal, mathContext: MathContext): BigDecimal {
    return BigDecimal.ONE.divide(x, mathContext)
  }

  private fun factorialLoop(n1: Int, n2: Int): BigDecimal {
    var n1 = n1
    val limit = Long.MAX_VALUE / n2
    var accu: Long = 1
    var result: BigDecimal = BigDecimal.ONE
    while (n1 <= n2) {
      if (accu <= limit) {
        accu *= n1.toLong()
      } else {
        result = result.multiply(BigDecimal.valueOf(accu))
        accu = n1.toLong()
      }
      n1++
    }
    return result.multiply(BigDecimal.valueOf(accu))
  }

  private fun factorialRecursion(n1: Int, n2: Int): BigDecimal {
    val threshold = if (n1 > 200) 80 else 150
    if (n2 - n1 < threshold) {
      return factorialLoop(n1, n2)
    }
    val mid = (n1 + n2) shr 1
    return factorialRecursion(mid + 1, n2).multiply(factorialRecursion(n1, mid))
  }

  /**
   * Calculates [BigDecimal] x to the power of [BigDecimal] y (x<sup>y</sup>).
   *
   * @param x the [BigDecimal] value to take to the power
   * @param y the [BigDecimal] value to serve as exponent
   * @param mathContext the [MathContext] used for the result
   * @return the calculated x to the power of y with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   * @see .pow
   */
  fun pow(x: BigDecimal, y: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    if (x.signum == 0) {
      when (y.signum) {
        0 -> return round(BigDecimal.ONE, mathContext)
        1 -> return round(BigDecimal.ZERO, mathContext)
      }
    }

    // TODO optimize y=0, y=1, y=10^k, y=-1, y=-10^k
    try {
      val longValue: Long = y.longValueExact()
      return pow(x, longValue, mathContext)
    } catch (_: ArithmeticException) {
      // ignored
    }

    if (fractionalPart(y).signum == 0) {
      return powInteger(x, y, mathContext)
    }

    // x^y = exp(y*log(x))
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)
    val result: BigDecimal = exp(y.multiply(log(x, mc), mc), mc)

    return round(result, mathContext)
  }

  /**
   * Calculates [BigDecimal] x to the power of `long` y (x<sup>y</sup>).
   *
   * The implementation tries to minimize the number of multiplications of [x][BigDecimal] (using
   * squares whenever possible).
   *
   * See:
   * [Wikipedia: Exponentiation - efficient computation](https://en.wikipedia.org/wiki/Exponentiation#Efficient_computation_with_integer_exponents)
   *
   * @param x the [BigDecimal] value to take to the power
   * @param y the `long` value to serve as exponent
   * @param mathContext the [MathContext] used for the result
   * @return the calculated x to the power of y with the precision specified in the `mathContext`
   * @throws ArithmeticException if y is negative and the result is inexact but the rounding mode is
   *   `UNNECESSARY` or `mc.precision == 0` and the quotient has a non-terminating decimal
   *   expansion.
   * @throws ArithmeticException if the rounding mode is `UNNECESSARY` and the `BigDecimal`
   *   operation would require rounding.
   */
  fun pow(x: BigDecimal, y: Long, mathContext: MathContext): BigDecimal {
    var x: BigDecimal = x
    var y = y
    val mc: MathContext =
      if (mathContext.precision == 0) mathContext
      else MathContext(mathContext.precision + 10, mathContext.roundingMode)

    // TODO optimize y=0, y=1, y=10^k, y=-1, y=-10^k
    if (y < 0) {
      val value: BigDecimal = reciprocal(pow(x, -y, mc), mc)
      return round(value, mathContext)
    }

    var result: BigDecimal = BigDecimal.ONE
    while (y > 0) {
      if ((y and 1L) == 1L) {
        // odd exponent -> multiply result with x
        result = result.multiply(x, mc)
        y -= 1
      }

      if (y > 0) {
        // even exponent -> square x
        x = x.multiply(x, mc)
      }

      y = y shr 1
    }

    return round(result, mathContext)
  }

  /**
   * Calculates [BigDecimal] x to the power of the integer value y (x<sup>y</sup>).
   *
   * The value y MUST be an integer value.
   *
   * @param x the [BigDecimal] value to take to the power
   * @param integerY the [BigDecimal] **integer** value to serve as exponent
   * @param mathContext the [MathContext] used for the result
   * @return the calculated x to the power of y with the precision specified in the `mathContext`
   * @see .pow
   */
  private fun powInteger(
    x: BigDecimal,
    integerY: BigDecimal,
    mathContext: MathContext,
  ): BigDecimal {
    var x: BigDecimal = x
    var integerY: BigDecimal = integerY
    require(fractionalPart(integerY).signum == 0) { "Not integer value: $integerY" }

    if (integerY.signum < 0) {
      return BigDecimal.ONE.divide(powInteger(x, integerY.negate(), mathContext), mathContext)
    }

    val mc =
      MathContext(
        kotlin.math.max(mathContext.precision, -integerY.scale()) + 30,
        mathContext.roundingMode,
      )

    var result: BigDecimal = BigDecimal.ONE
    while (integerY.signum > 0) {
      var halfY: BigDecimal = integerY.divide(TWO, mc)

      if (fractionalPart(halfY).signum != 0) {
        // odd exponent -> multiply result with x
        result = result.multiply(x, mc)
        integerY = integerY.minus(BigDecimal.ONE)
        halfY = integerY.divide(TWO, mc)
      }

      if (halfY.signum > 0) {
        // even exponent -> square x
        x = x.multiply(x, mc)
      }

      integerY = halfY
    }

    return round(result, mathContext)
  }

  /**
   * Calculates the square root of [BigDecimal] x.
   *
   * See [Wikipedia: Square root](http://en.wikipedia.org/wiki/Square_root)
   *
   * @param x the [BigDecimal] value to calculate the square root
   * @param mathContext the [MathContext] used for the result
   * @return the calculated square root of x with the precision specified in the `mathContext`
   * @throws ArithmeticException if x &lt; 0
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun sqrt(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    when (x.signum) {
      0 -> return BigDecimal.ZERO
      -1 -> throw ArithmeticException("Illegal sqrt(x) for x < 0: x = $x")
    }

    val maxPrecision: Int = mathContext.precision + 6
    val acceptableError: BigDecimal = BigDecimal.ONE.movePointLeft(mathContext.precision + 1)

    var result: BigDecimal
    var adaptivePrecision: Int
    if (isDoubleValue(x)) {
      result = BigDecimal.valueOf(kotlin.math.sqrt(x.toDouble()))
      adaptivePrecision = EXPECTED_INITIAL_PRECISION
    } else {
      result = x.multiply(ONE_HALF, mathContext)
      adaptivePrecision = 1
    }

    var last: BigDecimal

    if (adaptivePrecision < maxPrecision) {
      if (result.multiply(result).compareTo(x) == 0) {
        return round(result, mathContext) // early exit if x is a square number
      }

      do {
        last = result
        adaptivePrecision = adaptivePrecision shl 1
        if (adaptivePrecision > maxPrecision) {
          adaptivePrecision = maxPrecision
        }
        val mc = MathContext(adaptivePrecision, mathContext.roundingMode)
        result = x.divide(result, mc).add(last).multiply(ONE_HALF, mc)
      } while (adaptivePrecision < maxPrecision || result.minus(last).abs() > acceptableError)
    }

    return round(result, mathContext)
  }

  /**
   * Calculates the natural logarithm of [BigDecimal] x.
   *
   * See: [Wikipedia: Natural logarithm](http://en.wikipedia.org/wiki/Natural_logarithm)
   *
   * @param x the [BigDecimal] to calculate the natural logarithm for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated natural logarithm [BigDecimal] with the precision specified in the
   *   `mathContext`
   * @throws ArithmeticException if x &lt;= 0
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun log(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    if (x.signum <= 0) {
      throw ArithmeticException("Illegal log(x) for x <= 0: x = $x")
    }
    if (x.compareTo(BigDecimal.ONE) == 0) {
      return BigDecimal.ZERO
    }
    val result: BigDecimal =
      when (x.compareTo(BigDecimal.TEN)) {
        0 -> logTen(mathContext)
        1 -> logUsingExponent(x, mathContext)
        else -> logUsingTwoThree(x, mathContext)
      }

    return round(result, mathContext)
  }

  /**
   * Calculates the logarithm of [BigDecimal] x to the base 10.
   *
   * @param x the [BigDecimal] to calculate the logarithm base 10 for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated natural logarithm [BigDecimal] to the base 10 with the precision
   *   specified in the `mathContext`
   * @throws ArithmeticException if x &lt;= 0
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun log10(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    val mc = MathContext(mathContext.precision + 2, mathContext.roundingMode)

    val result: BigDecimal = log(x, mc).divide(logTen(mc), mc)
    return round(result, mathContext)
  }

  private fun logUsingNewton(x: BigDecimal, mathContext: MathContext): BigDecimal {
    // https://en.wikipedia.org/wiki/Natural_logarithm in chapter 'High Precision'
    // y = y + 2 * (x-exp(y)) / (x+exp(y))

    val maxPrecision: Int = mathContext.precision + 20
    val acceptableError: BigDecimal = BigDecimal.ONE.movePointLeft(mathContext.precision + 1)

    // System.out.println("logUsingNewton(" + x + " " + mathContext + ") precision " +
    // maxPrecision);
    var result: BigDecimal
    var adaptivePrecision: Int
    val doubleX: Double = x.toDouble()
    if (doubleX > 0.0 && isDoubleValue(x)) {
      result = BigDecimal.valueOf(ln(doubleX))
      adaptivePrecision = EXPECTED_INITIAL_PRECISION
    } else {
      result = x.divide(TWO, mathContext)
      adaptivePrecision = 1
    }

    var step: BigDecimal

    do {
      adaptivePrecision *= 3
      if (adaptivePrecision > maxPrecision) {
        adaptivePrecision = maxPrecision
      }
      val mc = MathContext(adaptivePrecision, mathContext.roundingMode)

      val expY: BigDecimal = exp(result, mc)
      step = TWO.multiply(x.minus(expY)).divide(x.add(expY), mc)
      // System.out.println("  step " + step + " adaptivePrecision=" + adaptivePrecision);
      result = result.add(step)
    } while (adaptivePrecision < maxPrecision || step.abs() > acceptableError)

    return result
  }

  private fun logUsingExponent(x: BigDecimal, mathContext: MathContext): BigDecimal {
    val mcDouble = MathContext(mathContext.precision shl 1, mathContext.roundingMode)
    val mc = MathContext(mathContext.precision + 4, mathContext.roundingMode)

    // System.out.println("logUsingExponent(" + x + " " + mathContext + ") precision " + mc);
    val exponent = exponent(x)
    val mantissa: BigDecimal = mantissa(x)

    var result: BigDecimal = logUsingTwoThree(mantissa, mc)
    if (exponent != 0) {
      result = result.add(BigDecimal.valueOf(exponent.toLong()).multiply(logTen(mcDouble), mc))
    }
    return result
  }

  private fun logUsingTwoThree(x: BigDecimal, mathContext: MathContext): BigDecimal {
    val mcDouble = MathContext(mathContext.precision shl 1, mathContext.roundingMode)
    val mc = MathContext(mathContext.precision + 4, mathContext.roundingMode)

    // System.out.println("logUsingTwoThree(" + x + " " + mathContext + ") precision " + mc);
    var factorOfTwo = 0
    var powerOfTwo = 1
    var factorOfThree = 0
    var powerOfThree = 1

    var value: Double = x.toDouble()
    if (value < 0.01) {
      // do nothing
    } else if (value < 0.1) { // never happens when called by logUsingExponent()
      while (value < 0.6) {
        value *= 2.0
        factorOfTwo--
        powerOfTwo = powerOfTwo shl 1
      }
    } else if (value < 0.115) { // (0.1 - 0.11111 - 0.115) -> (0.9 - 1.0 - 1.035)
      factorOfThree = -2
      powerOfThree = 9
    } else if (value < 0.14) { // (0.115 - 0.125 - 0.14) -> (0.92 - 1.0 - 1.12)
      factorOfTwo = -3
      powerOfTwo = 8
    } else if (value < 0.2) { // (0.14 - 0.16667 - 0.2) - (0.84 - 1.0 - 1.2)
      factorOfTwo = -1
      powerOfTwo = 2
      factorOfThree = -1
      powerOfThree = 3
    } else if (value < 0.3) { // (0.2 - 0.25 - 0.3) -> (0.8 - 1.0 - 1.2)
      factorOfTwo = -2
      powerOfTwo = 4
    } else if (value < 0.42) { // (0.3 - 0.33333 - 0.42) -> (0.9 - 1.0 - 1.26)
      factorOfThree = -1
      powerOfThree = 3
    } else if (value < 0.7) { // (0.42 - 0.5 - 0.7) -> (0.84 - 1.0 - 1.4)
      factorOfTwo = -1
      powerOfTwo = 2
    } else if (value < 1.4) { // (0.7 - 1.0 - 1.4) -> (0.7 - 1.0 - 1.4)
      // do nothing
    } else if (value < 2.5) { // (1.4 - 2.0 - 2.5) -> (0.7 - 1.0 - 1.25)
      factorOfTwo = 1
      powerOfTwo = 2
    } else if (value < 3.5) { // (2.5 - 3.0 - 3.5) -> (0.833333 - 1.0 - 1.166667)
      factorOfThree = 1
      powerOfThree = 3
    } else if (value < 5.0) { // (3.5 - 4.0 - 5.0) -> (0.875 - 1.0 - 1.25)
      factorOfTwo = 2
      powerOfTwo = 4
    } else if (value < 7.0) { // (5.0 - 6.0 - 7.0) -> (0.833333 - 1.0 - 1.166667)
      factorOfThree = 1
      powerOfThree = 3
      factorOfTwo = 1
      powerOfTwo = 2
    } else if (value < 8.5) { // (7.0 - 8.0 - 8.5) -> (0.875 - 1.0 - 1.0625)
      factorOfTwo = 3
      powerOfTwo = 8
    } else if (value < 10.0) { // (8.5 - 9.0 - 10.0) -> (0.94444 - 1.0 - 1.11111)
      factorOfThree = 2
      powerOfThree = 9
    } else {
      while (value > 1.4) { // never happens when called by logUsingExponent()
        value /= 2.0
        factorOfTwo++
        powerOfTwo = powerOfTwo shl 1
      }
    }

    var correctedX: BigDecimal = x
    var result: BigDecimal = BigDecimal.ZERO

    if (factorOfTwo > 0) {
      correctedX = correctedX.divide(BigDecimal.valueOf(powerOfTwo.toLong()), mc)
      result = result.add(logTwo(mcDouble).multiply(BigDecimal.valueOf(factorOfTwo.toLong()), mc))
    } else if (factorOfTwo < 0) {
      correctedX = correctedX.multiply(BigDecimal.valueOf(powerOfTwo.toLong()), mc)
      result =
        result.minus(logTwo(mcDouble).multiply(BigDecimal.valueOf((-factorOfTwo).toLong()), mc))
    }

    if (factorOfThree > 0) {
      correctedX = correctedX.divide(BigDecimal.valueOf(powerOfThree.toLong()), mc)
      result =
        result.add(logThree(mcDouble).multiply(BigDecimal.valueOf(factorOfThree.toLong()), mc))
    } else if (factorOfThree < 0) {
      correctedX = correctedX.multiply(BigDecimal.valueOf(powerOfThree.toLong()), mc)
      result =
        result.minus(logThree(mcDouble).multiply(BigDecimal.valueOf((-factorOfThree).toLong()), mc))
    }

    if (x === correctedX && result === BigDecimal.ZERO) {
      return logUsingNewton(x, mathContext)
    }

    result = result.add(logUsingNewton(correctedX, mc), mc)

    return result
  }

  /**
   * Returns the number pi.
   *
   * See [Wikipedia: Pi](https://en.wikipedia.org/wiki/Pi)
   *
   * @param mathContext the [MathContext] used for the result
   * @return the number pi with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun pi(mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    val currentCache = piCache

    if (currentCache != null && mathContext.precision <= currentCache.precision()) {
      return round(currentCache, mathContext)
    } else {
      val result = piChudnovski(mathContext)
      piCache = result
      return result
    }
  }

  private fun piChudnovski(mathContext: MathContext): BigDecimal {
    val mc = MathContext(mathContext.precision + 10, mathContext.roundingMode)

    val value24: BigDecimal = BigDecimal.valueOf(24)
    val value640320: BigDecimal = BigDecimal.valueOf(640320)
    val value13591409: BigDecimal = BigDecimal.valueOf(13591409)
    val value545140134: BigDecimal = BigDecimal.valueOf(545140134)
    val valueDivisor: BigDecimal = value640320.pow(3).divide(value24, mc)

    var sumA: BigDecimal = BigDecimal.ONE
    var sumB: BigDecimal = BigDecimal.ZERO

    var a: BigDecimal = BigDecimal.ONE
    var dividendTerm1: Long = 5 // -(6*k - 5)
    var dividendTerm2: Long = -1 // 2*k - 1
    var dividendTerm3: Long = -1 // 6*k - 1
    var kPower3: BigDecimal = BigDecimal.ZERO

    val iterationCount: Long = ((mc.precision + 13) / 14).toLong()
    for (k in 1..iterationCount) {
      val valueK: BigDecimal = BigDecimal.valueOf(k)
      dividendTerm1 += -6
      dividendTerm2 += 2
      dividendTerm3 += 6
      val dividend: BigDecimal =
        BigDecimal.valueOf(dividendTerm1)
          .multiply(BigDecimal.valueOf(dividendTerm2))
          .multiply(BigDecimal.valueOf(dividendTerm3))
      kPower3 = valueK.pow(3)
      val divisor: BigDecimal = kPower3.multiply(valueDivisor, mc)
      a = a.multiply(dividend).divide(divisor, mc)
      val b: BigDecimal = valueK.multiply(a, mc)

      sumA = sumA.add(a)
      sumB = sumB.add(b)
    }

    val value426880: BigDecimal = BigDecimal.valueOf(426880)
    val value10005: BigDecimal = BigDecimal.valueOf(10005)
    val factor: BigDecimal = value426880.multiply(sqrt(value10005, mc))
    val pi: BigDecimal =
      factor.divide(value13591409.multiply(sumA, mc).add(value545140134.multiply(sumB, mc)), mc)

    return round(pi, mathContext)
  }

  /**
   * Returns the number e.
   *
   * See
   * [Wikipedia: E (mathematical_constant)](https://en.wikipedia.org/wiki/E_(mathematical_constant))
   *
   * @param mathContext the [MathContext] used for the result
   * @return the number e with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun e(mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    val currentCache = eCache

    if (currentCache != null && mathContext.precision <= currentCache.precision()) {
      return round(currentCache, mathContext)
    } else {
      val result = exp(BigDecimal.ONE, mathContext)
      eCache = result
      return result
    }
  }

  private fun logTen(mathContext: MathContext): BigDecimal {
    val currentCache = log10Cache

    if (currentCache != null && mathContext.precision <= currentCache.precision()) {
      return round(currentCache, mathContext)
    } else {
      val result = logUsingNewton(BigDecimal.TEN, mathContext)
      log10Cache = result
      return result
    }
  }

  private fun logTwo(mathContext: MathContext): BigDecimal {
    val currentCache = log2Cache

    if (currentCache != null && mathContext.precision <= currentCache.precision()) {
      return round(currentCache, mathContext)
    } else {
      val result = logUsingNewton(TWO, mathContext)
      log2Cache = result
      return result
    }
  }

  private fun logThree(mathContext: MathContext): BigDecimal {
    val currentCache = log3Cache

    if (currentCache != null && mathContext.precision <= currentCache.precision()) {
      return round(currentCache, mathContext)
    } else {
      val result = logUsingNewton(THREE, mathContext)
      log2Cache = result
      return result
    }
  }

  /**
   * Calculates the natural exponent of [BigDecimal] x (e<sup>x</sup>).
   *
   * See: [Wikipedia: Exponent](http://en.wikipedia.org/wiki/Exponent)
   *
   * @param x the [BigDecimal] to calculate the exponent for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated exponent [BigDecimal] with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun exp(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    if (x.signum == 0) {
      return BigDecimal.ONE
    }

    return expIntegralFractional(x, mathContext)
  }

  private fun expIntegralFractional(x: BigDecimal, mathContext: MathContext): BigDecimal {
    val integralPart: BigDecimal = integralPart(x)

    if (integralPart.signum == 0) {
      return expTaylor(x, mathContext)
    }

    val fractionalPart: BigDecimal = x.minus(integralPart)

    val mc = MathContext(mathContext.precision + 10, mathContext.roundingMode)

    val z: BigDecimal = BigDecimal.ONE.add(fractionalPart.divide(integralPart, mc))
    val t: BigDecimal = expTaylor(z, mc)

    val result: BigDecimal = pow(t, integralPart.intValueExact().toLong(), mc)

    return round(result, mathContext)
  }

  private fun expTaylor(x: BigDecimal, mathContext: MathContext): BigDecimal {
    var x: BigDecimal = x
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)

    x = x.divide(BigDecimal.valueOf(256), mc)

    var result: BigDecimal = ExpCalculator.INSTANCE.calculate(x, mc)
    result = pow(result, 256, mc)
    return round(result, mathContext)
  }

  /**
   * Calculates the sine (sinus) of [BigDecimal] x.
   *
   * See: [Wikipedia: Sine](http://en.wikipedia.org/wiki/Sine)
   *
   * @param x the [BigDecimal] to calculate the sine for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated sine [BigDecimal] with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun sin(x: BigDecimal, mathContext: MathContext): BigDecimal {
    var x: BigDecimal = x
    checkMathContext(mathContext)
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)

    if (x.abs() > ROUGHLY_TWO_PI) {
      val mc2 = MathContext(mc.precision + 4, mathContext.roundingMode)
      val twoPi: BigDecimal = TWO.multiply(pi(mc2))
      x = x.remainder(twoPi, mc2)
    }

    val result: BigDecimal = SinCalculator.INSTANCE.calculate(x, mc)
    return round(result, mathContext)
  }

  /**
   * Calculates the arc sine (inverted sine) of [BigDecimal] x.
   *
   * See: [Wikipedia: Arcsine](http://en.wikipedia.org/wiki/Arcsine)
   *
   * @param x the [BigDecimal] to calculate the arc sine for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated arc sine [BigDecimal] with the precision specified in the `mathContext`
   * @throws ArithmeticException if x &gt; 1 or x &lt; -1
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun asin(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    if (x > BigDecimal.ONE) {
      throw ArithmeticException("Illegal asin(x) for x > 1: x = $x")
    }
    if (x < MINUS_ONE) {
      throw ArithmeticException("Illegal asin(x) for x < -1: x = $x")
    }

    if (x.signum == -1) {
      return asin(x.negate(), mathContext).negate()
    }

    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)

    if (x >= BigDecimal.valueOf(0.707107)) {
      val xTransformed: BigDecimal = sqrt(BigDecimal.ONE.minus(x.multiply(x)), mc)
      return acos(xTransformed, mathContext)
    }

    val result: BigDecimal = AsinCalculator.INSTANCE.calculate(x, mc)
    return round(result, mathContext)
  }

  /**
   * Calculates the cosine (cosinus) of [BigDecimal] x.
   *
   * See: [Wikipedia: Cosine](http://en.wikipedia.org/wiki/Cosine)
   *
   * @param x the [BigDecimal] to calculate the cosine for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated cosine [BigDecimal] with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun cos(x: BigDecimal, mathContext: MathContext): BigDecimal {
    var x: BigDecimal = x
    checkMathContext(mathContext)
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)

    if (x.abs() > ROUGHLY_TWO_PI) {
      val mc2 = MathContext(mc.precision + 4, mathContext.roundingMode)
      val twoPi: BigDecimal = TWO.multiply(pi(mc2), mc2)
      x = x.remainder(twoPi, mc2)
    }

    val result: BigDecimal = CosCalculator.INSTANCE.calculate(x, mc)
    return round(result, mathContext)
  }

  /**
   * Calculates the arc cosine (inverted cosine) of [BigDecimal] x.
   *
   * See: [Wikipedia: Arccosine](http://en.wikipedia.org/wiki/Arccosine)
   *
   * @param x the [BigDecimal] to calculate the arc cosine for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated arc sine [BigDecimal] with the precision specified in the `mathContext`
   * @throws ArithmeticException if x &gt; 1 or x &lt; -1
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun acos(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    if (x > BigDecimal.ONE) {
      throw ArithmeticException("Illegal acos(x) for x > 1: x = $x")
    }
    if (x < MINUS_ONE) {
      throw ArithmeticException("Illegal acos(x) for x < -1: x = $x")
    }

    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)

    val result: BigDecimal = pi(mc).divide(TWO, mc).minus(asin(x, mc))
    return round(result, mathContext)
  }

  /**
   * Calculates the tangens of [BigDecimal] x.
   *
   * See: [Wikipedia: Tangens](http://en.wikipedia.org/wiki/Tangens)
   *
   * @param x the [BigDecimal] to calculate the tangens for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated tangens [BigDecimal] with the precision specified in the `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun tan(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    if (x.signum == 0) {
      return BigDecimal.ZERO
    }

    val mc = MathContext(mathContext.precision + 4, mathContext.roundingMode)
    val result: BigDecimal = sin(x, mc).divide(cos(x, mc), mc)
    return round(result, mathContext)
  }

  /**
   * Calculates the arc tangens (inverted tangens) of [BigDecimal] x.
   *
   * See: [Wikipedia: Arctangens](http://en.wikipedia.org/wiki/Arctangens)
   *
   * @param x the [BigDecimal] to calculate the arc tangens for
   * @param mathContext the [MathContext] used for the result
   * @return the calculated arc tangens [BigDecimal] with the precision specified in the
   *   `mathContext`
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun atan(x: BigDecimal, mathContext: MathContext): BigDecimal {
    var x: BigDecimal = x
    checkMathContext(mathContext)
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)

    x = x.divide(sqrt(BigDecimal.ONE.add(x.multiply(x, mc)), mc), mc)

    val result: BigDecimal = asin(x, mc)
    return round(result, mathContext)
  }

  /**
   * Converts an angle measured in radians to an approximately equivalent angle measured in degrees.
   * The conversion from radians to degrees is generally inexact, it uses the number PI with the
   * precision specified in the mathContext.
   *
   * @param x An angle in radians.
   * @param mathContext the [MathContext] used for the result
   * @return The angle in degrees.
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun toDegrees(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)
    val result: BigDecimal = x.multiply(ONE_HUNDRED_EIGHTY.divide(pi(mc), mc), mc)
    return round(result, mathContext)
  }

  /**
   * / ** Converts an angle measured in degrees to an approximately equivalent angle measured in
   * radians. The conversion from degrees to radians is generally inexact, it uses the number PI
   * with the precision specified in the mathContext.
   *
   * @param x An angle in degrees.
   * @param mathContext the [MathContext] used for the result
   * @return The angle in radians.
   * @throws UnsupportedOperationException if the [MathContext] has unlimited precision
   */
  fun toRadians(x: BigDecimal, mathContext: MathContext): BigDecimal {
    checkMathContext(mathContext)
    val mc = MathContext(mathContext.precision + 6, mathContext.roundingMode)
    val result: BigDecimal = x.multiply(pi(mc).divide(ONE_HUNDRED_EIGHTY, mc), mc)
    return round(result, mathContext)
  }

  private fun checkMathContext(mathContext: MathContext) {
    if (mathContext.precision == 0) {
      throw UnsupportedOperationException("Unlimited MathContext not supported")
    }
  }
}
