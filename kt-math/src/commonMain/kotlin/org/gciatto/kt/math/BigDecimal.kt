package org.gciatto.kt.math

import kotlin.js.JsName
import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

@Suppress("NON_EXPORTABLE_TYPE", "ktlint:standard:backing-property-naming")
interface BigDecimal : Comparable<BigDecimal> {
  /**
   * Returns a [BigDecimal] whose value is the absolute value of this [BigDecimal], and whose _scale
   * is `this._scale()`.
   *
   * @return `absoluteValue(this)`
   */
  @JsName("absoluteValue") val absoluteValue: BigDecimal

  /**
   * Returns the _signum function of this [BigDecimal].
   *
   * @return -1, 0, or 1 as the value of this [BigDecimal] is negative, zero, or positive.
   */
  @JsName("signum") val signum: Int

  /**
   * Returns the *_scale* of this [BigDecimal]. If zero or positive, the _scale is the number of
   * digits to the right of the decimal point. If negative, the unscaled value of the number is
   * multiplied by ten to the power of the negation of the _scale. For example, a _scale of `-3`
   * means the unscaled value is multiplied by 1000.
   *
   * @return the _scale of this [BigDecimal].
   */
  @JsName("scale") val scale: Int

  /**
   * Returns the *_precision* of this [BigDecimal]. (The _precision is the number of digits in the
   * unscaled value.)
   *
   * The _precision of a zero value is 1.
   *
   * @return the _precision of this [BigDecimal].
   * @since 1.5
   */
  @JsName("precision") val precision: Int

  /**
   * Returns a [BigInteger] whose value is the *unscaled value* of this [BigDecimal]. (Computes
   * `(this * 10<sup>this._scale()</sup>)`.)
   *
   * @return the unscaled value of this [BigDecimal].
   * @since 1.2
   */
  @JsName("unscaledValue") val unscaledValue: BigInteger

  /**
   * Returns a [BigDecimal] whose value is `(this + augend)`, and whose _scale is
   * `max(this._scale(), augend._scale())`.
   *
   * @param augend value to be added to this [BigDecimal].
   * @return `this + augend`
   */
  @JsName("plus") operator fun plus(augend: BigDecimal?): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this + augend)`, with rounding according to the context
   * settings.
   *
   * If either number is zero and the _precision setting is nonzero then the other number, rounded
   * if necessary, is used as the result.
   *
   * @param augend value to be added to this [BigDecimal].
   * @param mc the context to use.
   * @return `this + augend`, rounded as necessary.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`.
   * @since 1.5
   */
  @JsName("plusWithContext") fun plus(augend: BigDecimal?, mc: MathContext): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this - subtrahend)`, and whose _scale is
   * `max(this._scale(), subtrahend._scale())`.
   *
   * @param subtrahend value to be subtracted from this [BigDecimal].
   * @return `this - subtrahend`
   */
  @JsName("minus") operator fun minus(subtrahend: BigDecimal): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this - subtrahend)`, with rounding according to the
   * context settings.
   *
   * If `subtrahend` is zero then this, rounded if necessary, is used as the result. If this is zero
   * then the result is `subtrahend.unaryMinus(mc)`.
   *
   * @param subtrahend value to be subtracted from this [BigDecimal].
   * @param mc the context to use.
   * @return `this - subtrahend`, rounded as necessary.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`.
   * @since 1.5
   */
  @JsName("minusWithContext") fun minus(subtrahend: BigDecimal, mc: MathContext): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this multiplicand)`, and whose _scale is
   * `(this._scale() + multiplicand._scale())`.
   *
   * @param multiplicand value to be multiplied by this [BigDecimal].
   * @return `this * multiplicand`
   */
  @JsName("times") operator fun times(multiplicand: BigDecimal?): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this multiplicand)`, with rounding according to the
   * context settings.
   *
   * @param multiplicand value to be multiplied by this [BigDecimal].
   * @param mc the context to use.
   * @return `this * multiplicand`, rounded as necessary.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`.
   * @since 1.5
   */
  @JsName("timesWithContext") fun times(multiplicand: BigDecimal, mc: MathContext): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this / divisor)`, and whose preferred _scale is
   * `(this._scale() - divisor._scale())`; if the exact quotient cannot be represented (because it
   * has a non-terminating decimal expansion) an `ArithmeticException` is thrown.
   *
   * @param divisor value by which this [BigDecimal] is to be divided.
   * @return `this / divisor`
   * @throws ArithmeticException if the exact quotient does not have a terminating decimal expansion
   * @author Joseph D. Darcy
   * @since 1.5
   */
  @JsName("div") operator fun div(divisor: BigDecimal): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this / divisor)`, with rounding according to the
   * context settings.
   *
   * @param divisor value by which this [BigDecimal] is to be divided.
   * @param mc the context to use.
   * @return `this / divisor`, rounded as necessary.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY` or
   *   `mc._precision == 0` and the quotient has a non-terminating decimal expansion.
   * @since 1.5
   */
  @JsName("divWithContext") fun div(divisor: BigDecimal, mc: MathContext): BigDecimal?

  /**
   * Returns a [BigDecimal] whose value is the integer part of the quotient `(this / divisor)`
   * rounded down. The preferred _scale of the result is `(this._scale() - divisor._scale())`.
   *
   * @param divisor value by which this [BigDecimal] is to be divided.
   * @return The integer part of `this / divisor`.
   * @throws ArithmeticException if `divisor==0`
   * @since 1.5
   */
  @JsName("divideToIntegralValue") fun divideToIntegralValue(divisor: BigDecimal): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is the integer part of `(this / divisor)`. Since the integer
   * part of the exact quotient does not depend on the rounding mode, the rounding mode does not
   * affect the values returned by this method. The preferred _scale of the result is
   * `(this._scale() - divisor._scale())`. An `ArithmeticException` is thrown if the integer part of
   * the exact quotient needs more than `mc._precision` digits.
   *
   * @param divisor value by which this [BigDecimal] is to be divided.
   * @param mc the context to use.
   * @return The integer part of `this / divisor`.
   * @throws ArithmeticException if `divisor==0`
   * @throws ArithmeticException if `mc._precision` &gt; 0 and the result requires a _precision of
   *   more than `mc._precision` digits.
   * @author Joseph D. Darcy
   * @since 1.5
   */
  @JsName("divideToIntegralValueWithContext")
  fun divideToIntegralValue(divisor: BigDecimal, mc: MathContext): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this % divisor)`.
   *
   * The remainder is given by `this.minus(this.divideToIntegralValue(divisor).timesLong(divisor))`.
   * Note that this is *not* the modulo operation (the result can be negative).
   *
   * @param divisor value by which this [BigDecimal] is to be divided.
   * @return `this % divisor`.
   * @throws ArithmeticException if `divisor==0`
   * @since 1.5
   */
  @JsName("rem") operator fun rem(divisor: BigDecimal): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this % divisor)`, with rounding according to the
   * context settings. The `MathContext` settings affect the implicit div used to compute the
   * remainder. The remainder computation itself is by definition exact. Therefore, the remainder
   * may contain more than `mc.getPrecision()` digits.
   *
   * The remainder is given by `this.minus(this.divideToIntegralValue(divisor,
   * mc).timesLong(divisor))`. Note that this is not the modulo operation (the result can be
   * negative).
   *
   * @param divisor value by which this [BigDecimal] is to be divided.
   * @param mc the context to use.
   * @return `this % divisor`, rounded as necessary.
   * @throws ArithmeticException if `divisor==0`
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`, or
   *   `mc._precision` &gt; 0 and the result of `this.divideToIntgralValue(divisor)` would require a
   *   _precision of more than `mc._precision` digits.
   * @see .divideToIntegralValue
   * @since 1.5
   */
  @JsName("remWithContext") fun rem(divisor: BigDecimal, mc: MathContext): BigDecimal

  /**
   * Returns a two-element [BigDecimal] array containing the result of `divideToIntegralValue`
   * followed by the result of `remainder` on the two operands.
   *
   * Note that if both the integer quotient and remainder are needed, this method is faster than
   * using the `divideToIntegralValue` and `remainder` methods separately because the division need
   * only be carried out once.
   *
   * @param divisor value by which this [BigDecimal] is to be divided, and the remainder computed.
   * @return a two element [BigDecimal] array: the quotient (the result of `divideToIntegralValue`)
   *   is the initial element and the remainder is the final element.
   * @throws ArithmeticException if `divisor==0`
   * @see BigDecimal.divideToIntegralValue
   * @see BigDecimal.rem
   * @since 1.5
   */
  @JsName("divideAndRemainder")
  fun divideAndRemainder(divisor: BigDecimal): Pair<BigDecimal, BigDecimal>

  /**
   * Returns a two-element [BigDecimal] array containing the result of `divideToIntegralValue`
   * followed by the result of `remainder` on the two operands calculated with rounding according to
   * the context settings.
   *
   * Note that if both the integer quotient and remainder are needed, this method is faster than
   * using the `divideToIntegralValue` and `remainder` methods separately because the division need
   * only be carried out once.
   *
   * @param divisor value by which this [BigDecimal] is to be divided, and the remainder computed.
   * @param mc the context to use.
   * @return a two element [BigDecimal] array: the quotient (the result of `divideToIntegralValue`)
   *   is the initial element and the remainder is the final element.
   * @throws ArithmeticException if `divisor==0`
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`, or
   *   `mc._precision` &gt; 0 and the result of `this.divideToIntgralValue(divisor)` would require a
   *   _precision of more than `mc._precision` digits.
   * @see BigDecimal.divideToIntegralValue
   * @see BigDecimal.rem
   * @since 1.5
   */
  @JsName("divideAndRemainderWithContext")
  fun divideAndRemainder(divisor: BigDecimal, mc: MathContext): Pair<BigDecimal, BigDecimal>

  /**
   * Returns an approximation to the square root of `this` with rounding according to the context
   * settings.
   *
   * The preferred _scale of the returned result is equal to `this._scale()/2`. The value of the
   * returned result is always within one ulp of the exact decimal value for the _precision in
   * question. If the rounding mode is [ ][RoundingMode.HALF_UP], [
   * HALF_DOWN][RoundingMode.HALF_DOWN], or [HALF_EVEN][RoundingMode.HALF_EVEN], the result is
   * within one half an ulp of the exact decimal value.
   *
   * Special case:
   * * The square root of a number numerically equal to `ZERO` is numerically equal to `ZERO` with a
   *   preferred _scale according to the general rule above. In particular, for `ZERO`,
   *   `ZERO.sqrt(mc).equals(ZERO)` is true with any `MathContext` as an argument.
   *
   * @param mc the context to use.
   * @return the square root of `this`.
   * @throws ArithmeticException if `this` is less than zero.
   * @throws ArithmeticException if an exact result is requested (`mc.getPrecision()==0`) and there
   *   is no finite decimal expansion of the exact result
   * @throws ArithmeticException if `(mc.getRoundingMode()==RoundingMode.UNNECESSARY`) and the exact
   *   result cannot fit in `mc.getPrecision()` digits.
   * @see BigInteger.sqrt
   * @since 9
   */
  @JsName("sqrt") fun sqrt(mc: MathContext = MathContext()): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this<sup>n</sup>)`, The power is computed exactly, to
   * unlimited _precision.
   *
   * The parameter `n` must be in the range 0 through 999999999, inclusive. `ZERO.pow(0)` returns
   * [BigDecimal.ONE].
   *
   * Note that future releases may expand the allowable exponent range of this method.
   *
   * @param n power to raise this [BigDecimal] to.
   * @return `this<sup>n</sup>`
   * @throws ArithmeticException if `n` is out of range.
   * @since 1.5
   */
  @JsName("pow") infix fun pow(n: Int): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(this<sup>n</sup>)`. The current implementation uses the
   * core algorithm defined in ANSI standard X3.274-1996 with rounding according to the context
   * settings. In general, the returned numerical value is within two ulps of the exact numerical
   * value for the chosen _precision. Note that future releases may use a different algorithm with a
   * decreased allowable error bound and increased allowable exponent range.
   *
   * The X3.274-1996 algorithm is:
   * * An `ArithmeticException` exception is thrown if
   * * `absoluteValue(n) > 999999999`
   * * `mc._precision == 0` and `n < 0`
   * * `mc._precision > 0` and `n` has more than `mc._precision` decimal digits
   * * if `n` is zero, [BigDecimal.ONE] is returned even if `this` is zero, otherwise
   * * if `n` is positive, the result is calculated via the repeated squaring technique into a
   *   single accumulator. The individual multiplications with the accumulator use the same math
   *   context settings as in `mc` except for a _precision increased to `mc._precision + elength +
   *   1` where `elength` is the number of decimal digits in `n`.
   * * if `n` is negative, the result is calculated as if `n` were positive; this value is then
   *   divided into one using the working _precision specified above.
   * * The final value from either the positive or negative case is then rounded to the destination
   *   _precision.
   *
   * @param n power to raise this [BigDecimal] to.
   * @param mc the context to use.
   * @return `this<sup>n</sup>` using the ANSI standard X3.274-1996 algorithm
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`, or
   *   `n` is out of range.
   * @since 1.5
   */
  @JsName("powWithContext") fun pow(n: Int, mc: MathContext): BigDecimal?

  /**
   * Returns a [BigDecimal] whose value is the absolute value of this [BigDecimal], with rounding
   * according to the context settings.
   *
   * @param mc the context to use.
   * @return `absoluteValue(this)`, rounded as necessary.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`.
   * @since 1.5
   */
  @JsName("absoluteValueWithContext") fun absoluteValue(mc: MathContext): BigDecimal?

  /**
   * Returns a [BigDecimal] whose value is `(-this)`, and whose _scale is `this._scale()`.
   *
   * @return `-this`.
   */
  @JsName("unaryMinus") operator fun unaryMinus(): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(-this)`, with rounding according to the context
   * settings.
   *
   * @param mc the context to use.
   * @return `-this`, rounded as necessary.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`.
   * @since 1.5
   */
  @JsName("unaryMinusWithContext") fun unaryMinus(mc: MathContext): BigDecimal?

  /**
   * Returns a [BigDecimal] whose value is `(+this)`, and whose _scale is `this._scale()`.
   *
   * This method, which simply returns this [BigDecimal] is included for symmetry with the unary
   * minus method [ ][BigDecimal.unaryMinus].
   *
   * @return `this`.
   * @see .unaryMinus
   * @since 1.5
   */
  @JsName("unaryPlus") operator fun unaryPlus(): BigDecimal

  /**
   * Returns a [BigDecimal] whose value is `(+this)`, with rounding according to the context
   * settings.
   *
   * The effect of this method is identical to that of the [ ][BigDecimal.round] method.
   *
   * @param mc the context to use.
   * @return `this`, rounded as necessary. A zero result will have a _scale of 0.
   * @throws ArithmeticException if the result is inexact but the rounding mode is `UNNECESSARY`.
   * @see .round
   * @since 1.5
   */
  @JsName("unaryPlusWithContext") fun unaryPlus(mc: MathContext): BigDecimal?

  /**
   * Returns a [BigDecimal] rounded according to the `MathContext` settings. If the _precision
   * setting is 0 then no rounding takes place.
   *
   * The effect of this method is identical to that of the [BigDecimal.plus] method.
   *
   * @param mc the context to use.
   * @return a [BigDecimal] rounded according to the `MathContext` settings.
   * @throws ArithmeticException if the rounding mode is `UNNECESSARY` and the [BigDecimal]
   *   operation would require rounding.
   * @see .plus
   * @since 1.5
   */
  @JsName("round") fun round(mc: MathContext): BigDecimal?

  /**
   * Returns a [BigDecimal] whose _scale is the specified value, and whose unscaled value is
   * determined by multiplying or dividing this [BigDecimal]'s unscaled value by the appropriate
   * power of ten to maintain its overall value. If the _scale is reduced by the operation, the
   * unscaled value must be divided (rather than multiplied), and the value may be changed; in this
   * case, the specified rounding mode is applied to the division.
   *
   * @apiNote Since BigDecimal objects are immutable, calls of this method do *not* result in the
   * original object being modified, contrary to the usual convention of having methods named
   * `set*X*` mutate field *`X`*. Instead, `setScale` returns an object with the proper _scale; the
   * returned object may or may not be newly allocated.
   *
   * @param newScale _scale of the [BigDecimal] value to be returned.
   * @param roundingMode The rounding mode to apply.
   * @return a [BigDecimal] whose _scale is the specified value, and whose unscaled value is
   *   determined by multiplying or dividing this [BigDecimal]'s unscaled value by the appropriate
   *   power of ten to maintain its overall value.
   * @throws ArithmeticException if `roundingMode==UNNECESSARY` and the specified scaling operation
   *   would require rounding.
   * @see RoundingMode
   * @since 1.5
   */
  @JsName("setScaleRounding") fun setScale(newScale: Int, roundingMode: RoundingMode): BigDecimal

  /**
   * Returns a [BigDecimal] whose _scale is the specified value, and whose unscaled value is
   * determined by multiplying or dividing this [BigDecimal]'s unscaled value by the appropriate
   * power of ten to maintain its overall value. If the _scale is reduced by the operation, the
   * unscaled value must be divided (rather than multiplied), and the value may be changed; in this
   * case, the specified rounding mode is applied to the division.
   *
   * @apiNote Since BigDecimal objects are immutable, calls of this method do *not* result in the
   * original object being modified, contrary to the usual convention of having methods named
   * `set*X*` mutate field *`X`*. Instead, `setScale` returns an object with the proper _scale; the
   * returned object may or may not be newly allocated.
   *
   * @param newScale _scale of the [BigDecimal] value to be returned.
   * @param roundingMode The rounding mode to apply.
   * @return a [BigDecimal] whose _scale is the specified value, and whose unscaled value is
   *   determined by multiplying or dividing this [BigDecimal]'s unscaled value by the appropriate
   *   power of ten to maintain its overall value.
   * @throws ArithmeticException if `roundingMode==ROUND_UNNECESSARY` and the specified scaling
   *   operation would require rounding.
   * @throws IllegalArgumentException if `roundingMode` does not represent a valid rounding mode.
   * @see .ROUND_UP
   * @see .ROUND_DOWN
   * @see .ROUND_CEILING
   * @see .ROUND_FLOOR
   * @see .ROUND_HALF_UP
   * @see .ROUND_HALF_DOWN
   * @see .ROUND_HALF_EVEN
   * @see .ROUND_UNNECESSARY
   */
  @Deprecated(
    "The method {@link #setScale(int, RoundingMode)} should be used in preference to this legacy method."
  )
  @JsName("setScaleInt")
  fun setScale(newScale: Int, roundingMode: Int): BigDecimal

  /**
   * Returns a [BigDecimal] whose _scale is the specified value, and whose value is numerically
   * equal to this [BigDecimal]'s. Throws an `ArithmeticException` if this is not possible.
   *
   * This call is typically used to increase the _scale, in which case it is guaranteed that there
   * exists a [BigDecimal] of the specified _scale and the correct value. The call can also be used
   * to reduce the _scale if the caller knows that the [BigDecimal] has sufficiently many zeros at
   * the end of its fractional part (i.e., factors of ten in its integer value) to allow for the
   * rescaling without changing its value.
   *
   * This method returns the same result as the two-argument versions of `setScale`, but saves the
   * caller the trouble of specifying a rounding mode in cases where it is irrelevant.
   *
   * @apiNote Since [BigDecimal] objects are immutable, calls of this method do *not* result in the
   * original object being modified, contrary to the usual convention of having methods named
   * `set*X*` mutate field *`X`*. Instead, `setScale` returns an object with the proper _scale; the
   * returned object may or may not be newly allocated.
   *
   * @param newScale _scale of the [BigDecimal] value to be returned.
   * @return a [BigDecimal] whose _scale is the specified value, and whose unscaled value is
   *   determined by multiplying or dividing this [BigDecimal]'s unscaled value by the appropriate
   *   power of ten to maintain its overall value.
   * @throws ArithmeticException if the specified scaling operation would require rounding.
   * @see .setScale
   * @see .setScale
   */
  @JsName("setScale") fun setScale(newScale: Int): BigDecimal

  /**
   * Returns a [BigDecimal] which is equivalent to this one with the decimal point moved `n` places
   * to the left. If `n` is non-negative, the call merely adds `n` to the _scale. If `n` is
   * negative, the call is equivalent to `movePointRight(-n)`. The [BigDecimal] returned by this
   * call has value `(this 10<sup>-n</sup>)` and _scale `max(this._scale()+n, 0)`.
   *
   * @param n number of places to move the decimal point to the left.
   * @return a [BigDecimal] which is equivalent to this one with the decimal point moved `n` places
   *   to the left.
   * @throws ArithmeticException if _scale overflows.
   */
  @JsName("movePointLeft") fun movePointLeft(n: Int): BigDecimal

  /**
   * Returns a [BigDecimal] which is equivalent to this one with the decimal point moved `n` places
   * to the right. If `n` is non-negative, the call merely subtracts `n` from the _scale. If `n` is
   * negative, the call is equivalent to `movePointLeft(-n)`. The [BigDecimal] returned by this call
   * has value `(this 10<sup>n</sup>)` and _scale `max(this._scale()-n, 0)`.
   *
   * @param n number of places to move the decimal point to the right.
   * @return a [BigDecimal] which is equivalent to this one with the decimal point moved `n` places
   *   to the right.
   * @throws ArithmeticException if _scale overflows.
   */
  @JsName("movePointRight") fun movePointRight(n: Int): BigDecimal

  /**
   * Returns a BigDecimal whose numerical value is equal to (`this` * 10<sup>n</sup>). The _scale of
   * the result is `(this._scale() - n)`.
   *
   * @param n the exponent power of ten to _scale by
   * @return a BigDecimal whose numerical value is equal to (`this` * 10<sup>n</sup>)
   * @throws ArithmeticException if the _scale would be outside the range of a 32-bit integer.
   * @since 1.5
   */
  @JsName("scaleByPowerOfTen") fun scaleByPowerOfTen(n: Int): BigDecimal

  /**
   * Returns a [BigDecimal] which is numerically equal to this one but with any trailing zeros
   * removed from the representation. For example, stripping the trailing zeros from the
   * [BigDecimal] value `600.0`, which has [[BigInteger], `_scale`] components equals to `[6000,
   * 1]`, yields `6E2` with `[BigInteger, scale]` components equals to `[6, -2]`. If this BigDecimal
   * is numerically equal to zero, then `BigDecimal.ZERO` is returned.
   *
   * @return a numerically equal [BigDecimal] with any trailing zeros removed.
   * @since 1.5
   */
  @JsName("stripTrailingZeros") fun stripTrailingZeros(): BigDecimal

  /**
   * Compares this [BigDecimal] with the specified [BigDecimal]. Two [BigDecimal] objects that are
   * equal in value but have a different _scale (like 2.0 and 2.00) are considered equal by this
   * method. This method is provided in preference to individual methods for each of the six boolean
   * comparison operators (&lt;, ==, &gt;, &gt;=, !=, &lt;=). The suggested idiom for performing
   * these comparisons is: `(x.compareTo(y)` &lt;*op*&gt; `0)`, where &lt;*op*&gt; is one of the six
   * comparison operators.
   *
   * @param other [BigDecimal] to which this [BigDecimal] is to be compared.
   * @return -1, 0, or 1 as this [BigDecimal] is numerically less than, equal to, or greater than
   *   `val`.
   */
  override fun compareTo(other: BigDecimal): Int

  /**
   * Returns the minimum of this [BigDecimal] and `val`.
   *
   * @param val value with which the minimum is to be computed.
   * @return the [BigDecimal] whose value is the lesser of this [BigDecimal] and `val`. If they are
   *   equal, as defined by the [compareTo][BigDecimal.compareTo] method, `this` is returned.
   * @see .compareTo
   */
  @JsName("min") fun min(`val`: BigDecimal): BigDecimal

  /**
   * Returns the maximum of this [BigDecimal] and `val`.
   *
   * @param val value with which the maximum is to be computed.
   * @return the [BigDecimal] whose value is the greater of this [BigDecimal] and `val`. If they are
   *   equal, as defined by the [compareTo][BigDecimal.compareTo] method, `this` is returned.
   * @see .compareTo
   */
  @JsName("max") fun max(`val`: BigDecimal): BigDecimal

  /**
   * Returns a string representation of this [BigDecimal], using engineering notation if an exponent
   * is needed.
   *
   * Returns a string that represents the [BigDecimal] as described in the [BigDecimal.toString]
   * method, except that if exponential notation is used, the power of ten is adjusted to be a
   * multiple of three (engineering notation) such that the integer part of nonzero values will be
   * in the range 1 through
   * 999. If exponential notation is used for zero values, a decimal point and one or two fractional
   *      zero digits are used so that the _scale of the zero value is preserved. Note that unlike
   *      the output of [BigDecimal.toString], the output of this method is *not* guaranteed to
   *      recover the same `[integer, _scale]` pair of this [BigDecimal] if the output string is
   *      converting back to a [BigDecimal] using the aforementioned. The result of this method
   *      meets the weaker constraint of always producing a numerically equal result from applying
   *      the string constructor to the method's output.
   *
   * @return string representation of this [BigDecimal], using engineering notation if an exponent
   *   is needed.
   * @since 1.5
   */
  @JsName("toEngineeringString") fun toEngineeringString(): String

  /**
   * Returns a string representation of this [BigDecimal] without an exponent field. For values with
   * a positive _scale, the number of digits to the right of the decimal point is used to indicate
   * _scale. For values with a zero or negative _scale, the resulting string is generated as if the
   * value were converted to a numerically equal value with zero _scale and as if all the trailing
   * zeros of the zero _scale value were present in the result.
   *
   * The entire string is prefixed by a minus sign character '-' (`'&#92;u002D'`) if the unscaled
   * value is less than zero. No sign character is prefixed if the unscaled value is zero or
   * positive.
   *
   * Note that if the result of this method is passed to the string constructor, only the numerical
   * value of this [BigDecimal] will necessarily be recovered; the representation of the new
   * [BigDecimal] may have a different _scale. In particular, if this [BigDecimal] has a negative
   * _scale, the string resulting from this method will have a _scale of zero when processed by the
   * string constructor.
   *
   * (This method behaves analogously to the `toString` method in 1.4 and earlier releases.)
   *
   * @return a string representation of this [BigDecimal] without an exponent field.
   * @see .toString
   * @see .toEngineeringString
   * @since 1.5
   */
  @JsName("toPlainString") fun toPlainString(): String

  /**
   * Converts this [BigDecimal] to a [BigInteger]. This conversion is analogous to the *narrowing
   * primitive conversion* from `double` to `long` as defined in <cite>The Java Language
   * Specification</cite>: any fractional part of this [BigDecimal] will be discarded. Note that
   * this conversion can lose information about the _precision of the [BigDecimal] value.
   *
   * To have an exception thrown if the conversion is inexact (in other words if a nonzero
   * fractional part is discarded), use the [BigDecimal.toBigIntegerExact] method.
   *
   * @return this [BigDecimal] converted to a [BigInteger].
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toBigInteger") fun toBigInteger(): BigInteger

  /**
   * Converts this [BigDecimal] to a [BigInteger], checking for lost information. An exception is
   * thrown if this [BigDecimal] has a nonzero fractional part.
   *
   * @return this [BigDecimal] converted to a [BigInteger].
   * @throws ArithmeticException if `this` has a nonzero fractional part.
   * @since 1.5
   */
  @JsName("toBigIntegerExact") fun toBigIntegerExact(): BigInteger

  /**
   * Converts this [BigDecimal] to a `long`. This conversion is analogous to the *narrowing
   * primitive conversion* from `double` to `short` as defined in <cite>The Java Language
   * Specification</cite>: any fractional part of this [BigDecimal] will be discarded, and if the
   * resulting "[BigInteger]" is too big to fit in a `long`, only the low-order 64 bits are
   * returned. Note that this conversion can lose information about the overall magnitude and
   * _precision of this [BigDecimal] value as well as return a result with the opposite sign.
   *
   * @return this [BigDecimal] converted to a `long`.
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toLong") fun toLong(): Long

  /**
   * Converts this [BigDecimal] to a `long`, checking for lost information. If this [BigDecimal] has
   * a nonzero fractional part or is out of the possible range for a `long` result then an
   * `ArithmeticException` is thrown.
   *
   * @return this [BigDecimal] converted to a `long`.
   * @throws ArithmeticException if `this` has a nonzero fractional part, or will not fit in a
   *   `long`.
   * @since 1.5
   */
  @JsName("toLongExact") fun toLongExact(): Long

  /**
   * Converts this [BigDecimal] to an `int`. This conversion is analogous to the *narrowing
   * primitive conversion* from `double` to `short` as defined in <cite>The Java Language
   * Specification</cite>: any fractional part of this [BigDecimal] will be discarded, and if the
   * resulting "[BigInteger]" is too big to fit in an `int`, only the low-order 32 bits are
   * returned. Note that this conversion can lose information about the overall magnitude and
   * _precision of this [BigDecimal] value as well as return a result with the opposite sign.
   *
   * @return this [BigDecimal] converted to an `int`.
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toInt") fun toInt(): Int

  @JsName("toByte") fun toByte(): Byte

  @JsName("toChar") fun toChar(): Char

  @JsName("toShort") fun toShort(): Short

  /**
   * Converts this [BigDecimal] to an `int`, checking for lost information. If this [BigDecimal] has
   * a nonzero fractional part or is out of the possible range for an `int` result then an
   * `ArithmeticException` is thrown.
   *
   * @return this [BigDecimal] converted to an `int`.
   * @throws ArithmeticException if `this` has a nonzero fractional part, or will not fit in an
   *   `int`.
   * @since 1.5
   */
  @JsName("toIntExact") fun toIntExact(): Int

  /**
   * Converts this [BigDecimal] to a `short`, checking for lost information. If this [BigDecimal]
   * has a nonzero fractional part or is out of the possible range for a `short` result then an
   * `ArithmeticException` is thrown.
   *
   * @return this [BigDecimal] converted to a `short`.
   * @throws ArithmeticException if `this` has a nonzero fractional part, or will not fit in a
   *   `short`.
   * @since 1.5
   */
  @JsName("toShortExact") fun toShortExact(): Short

  /**
   * Converts this [BigDecimal] to a `byte`, checking for lost information. If this [BigDecimal] has
   * a nonzero fractional part or is out of the possible range for a `byte` result then an
   * `ArithmeticException` is thrown.
   *
   * @return this [BigDecimal] converted to a `byte`.
   * @throws ArithmeticException if `this` has a nonzero fractional part, or will not fit in a
   *   `byte`.
   * @since 1.5
   */
  @JsName("toByteExact") fun toByteExact(): Byte

  /**
   * Converts this [BigDecimal] to a `float`. This conversion is similar to the *narrowing primitive
   * conversion* from `double` to `float` as defined in <cite>The Java Language
   * Specification</cite>: if this [BigDecimal] has too great a magnitude to represent as a `float`,
   * it will be converted to [Float.NEGATIVE_INFINITY] or [ ][Float.POSITIVE_INFINITY] as
   * appropriate. Note that even when the return value is finite, this conversion can lose
   * information about the _precision of the [BigDecimal] value.
   *
   * @return this [BigDecimal] converted to a `float`.
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toFloat") fun toFloat(): Float

  /**
   * Converts this [BigDecimal] to a `double`. This conversion is similar to the *narrowing
   * primitive conversion* from `double` to `float` as defined in <cite>The Java Language
   * Specification</cite>: if this [BigDecimal] has too great a magnitude represent as a `double`,
   * it will be converted to [Double.NEGATIVE_INFINITY] or [ ][Double.POSITIVE_INFINITY] as
   * appropriate. Note that even when the return value is finite, this conversion can lose
   * information about the _precision of the [BigDecimal] value.
   *
   * @return this [BigDecimal] converted to a `double`.
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toDouble") fun toDouble(): Double

  /**
   * Returns the size of an ulp, a unit in the last place, of this [BigDecimal]. An ulp of a nonzero
   * [BigDecimal] value is the positive distance between this value and the [BigDecimal] value next
   * larger in magnitude with the same number of digits. An ulp of a zero value is numerically equal
   * to 1 with the _scale of `this`. The result is stored with the same _scale as `this` so the
   * result for zero and nonzero values is equal to `[1, this._scale()]`.
   *
   * @return the size of an ulp of `this`
   * @since 1.5
   */
  @JsName("ulp") fun ulp(): BigDecimal

  companion object {
    /**
     * The value 0, with a _scale of 0.
     *
     * @since 1.5
     */
    @JvmField @JsName("ZERO") val ZERO: BigDecimal = BigDecimals.zero

    /**
     * The value 1, with a _scale of 0.
     *
     * @since 1.5
     */
    @JvmField @JsName("ONE") val ONE: BigDecimal = BigDecimals.one

    @JvmField @JsName("TWO") val TWO: BigDecimal = BigDecimals.two

    /**
     * The value 10, with a _scale of 0.
     *
     * @since 1.5
     */
    @JvmField @JsName("TEN") val TEN: BigDecimal = BigDecimals.ten

    /** The value 0.1, with a _scale of 1. */
    @JvmField @JsName("ONE_TENTH") val ONE_TENTH: BigDecimal = BigDecimals.oneTenth

    /** The value 0.5, with a _scale of 1. */
    @JvmField @JsName("ONE_HALF") val ONE_HALF: BigDecimal = BigDecimals.oneHalf

    @JvmField @JsName("PI") val PI: BigDecimal = BigDecimals.pi

    @JvmField @JsName("E") val E: BigDecimal = BigDecimals.e

    /**
     * Translates a `long` unscaled value and an `int` _scale into a [BigDecimal].
     *
     * @apiNote This static factory method is provided in preference to a (`long`, `int`)
     * constructor because it allows for reuse of frequently used [BigDecimal] values.
     *
     * @param unscaledVal unscaled value of the [BigDecimal].
     * @param scale _scale of the [BigDecimal].
     * @return a [BigDecimal] whose value is `(unscaledVal 10<sup>-_scale</sup>)`.
     */
    @JvmStatic
    @JsName("ofScaledLong")
    fun of(unscaledVal: Long, scale: Int): BigDecimal = bigDecimalOf(unscaledVal, scale)

    /**
     * Translates a `long` value into a [BigDecimal] with a _scale of zero.
     *
     * @apiNote This static factory method is provided in preference to a (`long`) constructor
     * because it allows for reuse of frequently used [BigDecimal] values.
     *
     * @param val value of the [BigDecimal].
     * @return a [BigDecimal] whose value is `val`.
     */
    @JvmStatic
    @JsName("ofScaledLongWithPrecision")
    fun of(unscaledVal: Long, scale: Int, prec: Int): BigDecimal =
      bigDecimalOf(unscaledVal, scale, prec)

    @JvmStatic @JsName("ofInt") fun of(`val`: Int): BigDecimal = bigDecimalOf(`val`)

    @JvmStatic @JsName("of") fun of(`val`: Long): BigDecimal = bigDecimalOf(`val`)

    @JvmStatic
    @JsName("ofScaledBigIntegerWithPrecision")
    fun of(intVal: BigInteger, scale: Int, prec: Int): BigDecimal =
      bigDecimalOf(intVal, scale, prec)

    /**
     * Translates a `double` into a [BigDecimal], using the `double`'s canonical string
     * representation provided by the [Double.toString] method.
     *
     * @apiNote This is generally the preferred way to convert a `double` (or `float`) into a
     * [BigDecimal], as the value returned is equal to that resulting from constructing a
     * [BigDecimal] from the result of using [ ][Double.toString].
     *
     * @param val `double` to convert to a [BigDecimal].
     * @return a [BigDecimal] whose value is equal to or approximately equal to the value of `val`.
     * @throws NumberFormatException if `val` is infinite or NaN.
     * @since 1.5
     */
    @JvmStatic
    @JsName("ofDouble")
    @JvmOverloads
    fun of(`val`: Double, ctx: MathContext? = null): BigDecimal = bigDecimalOf(`val`, ctx)

    @JvmStatic
    @JsName("ofFloat")
    @JvmOverloads
    fun of(`val`: Float, ctx: MathContext? = null): BigDecimal = bigDecimalOf(`val`, ctx)

    @JvmStatic
    @JsName("parse")
    @JvmOverloads
    fun of(`val`: String, ctx: MathContext? = null): BigDecimal = bigDecimalOf(`val`, ctx)

    @JvmStatic
    @JsName("ofBigInteger")
    @JvmOverloads
    fun of(`val`: BigInteger, ctx: MathContext? = null): BigDecimal = bigDecimalOf(`val`, ctx)

    @JvmStatic
    @JsName("ofIntWithContext")
    fun of(`val`: Int, ctx: MathContext): BigDecimal = bigDecimalOf(`val`, ctx)

    @JvmStatic
    @JsName("ofWithContext")
    fun of(`val`: Long, ctx: MathContext): BigDecimal = bigDecimalOf(`val`, ctx)
  }
}
