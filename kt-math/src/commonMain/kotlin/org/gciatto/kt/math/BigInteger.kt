package org.gciatto.kt.math

import kotlin.js.JsName
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic
import kotlin.random.Random

@Suppress("NON_EXPORTABLE_TYPE")
interface BigInteger : Comparable<BigInteger> {
  /**
   * Returns a BigInteger whose value is the absolute value of this BigInteger.
   *
   * @return `absoluteValue(this)`
   */
  @JsName("absoluteValue") val absoluteValue: BigInteger

  /**
   * Returns the _signum function of this BigInteger.
   *
   * @return -1, 0 or 1 as the value of this BigInteger is negative, zero or positive.
   */
  @JsName("signum") val signum: Int

  /**
   * Returns the number of bits in the minimal two's-complement representation of this BigInteger,
   * *excluding* a sign bit. For positive BigIntegers, this is equivalent to the number of bits in
   * the ordinary binary representation. (Computes `(ceil(log2(this < 0 ? -this : this+1)))`.)
   *
   * @return number of bits in the minimal two's-complement representation of this BigInteger,
   *   *excluding* a sign bit.
   */
  @JsName("bitLength") val bitLength: Int

  /**
   * Returns the number of bits in the two's complement representation of this BigInteger that
   * differ from its sign bit. This method is useful when implementing bit-vector style sets atop
   * BigIntegers.
   *
   * @return number of bits in the two's complement representation of this BigInteger that differ
   *   from its sign bit.
   */
  @JsName("bitCount") val bitCount: Int

  @JsName("rangeTo")
  operator fun rangeTo(endInclusive: BigInteger): BigIntegerRange =
    BigIntegerRange(this, endInclusive)

  @JsName("rangeToInt")
  operator fun rangeTo(endInclusive: Int): BigIntegerRange = BigIntegerRange(this, of(endInclusive))

  @JsName("rangeToLong")
  operator fun rangeTo(endInclusive: Long): BigIntegerRange =
    BigIntegerRange(this, of(endInclusive))

  /**
   * Returns the first integer greater than this `BigInteger` that is probably prime. The
   * probability that the number returned by this method is composite does not exceed
   * 2<sup>-100</sup>. This method will never skip over a prime when searching: if it returns `p`,
   * there is no prime `q` such that `this < q < p`.
   *
   * @return the first integer greater than this `BigInteger` that is probably prime.
   * @throws ArithmeticException `this < 0` or `this` is too large.
   * @since 1.5
   */
  @JsName("nextProbablePrime") fun nextProbablePrime(): BigInteger

  /**
   * Returns a BigInteger whose value is `(this + val)`.
   *
   * @param other value to be added to this BigInteger.
   * @return `this + val`
   */
  @JsName("plus") operator fun plus(other: BigInteger): BigInteger

  @JsName("plusInt") operator fun plus(other: Int): BigInteger = plus(of(other))

  @JsName("plusLong") operator fun plus(other: Long): BigInteger = plus(of(other))

  /**
   * Returns a BigInteger whose value is `(this - val)`.
   *
   * @param other value to be subtracted from this BigInteger.
   * @return `this - val`
   */
  @JsName("minus") operator fun minus(other: BigInteger): BigInteger

  @JsName("minusInt") operator fun minus(other: Int): BigInteger = minus(of(other))

  @JsName("minusLong") operator fun minus(other: Long): BigInteger = minus(of(other))

  /**
   * Returns a BigInteger whose value is `(this * val)`.
   *
   * @implNote An implementation may offer better algorithmic performance when `val == this`.
   *
   * @param other value to be multiplied by this BigInteger.
   * @return `this * val`
   */
  @JsName("times") operator fun times(other: BigInteger): BigInteger

  @JsName("timesInt") operator fun times(other: Int): BigInteger = times(of(other))

  @JsName("timesLong") operator fun times(other: Long): BigInteger = times(of(other))

  /**
   * Returns a BigInteger whose value is `(this / val)`.
   *
   * @param other value by which this BigInteger is to be divided.
   * @return `this / val`
   * @throws ArithmeticException if other is zero.
   */
  @JsName("div") operator fun div(other: BigInteger): BigInteger

  @JsName("divInt") operator fun div(other: Int): BigInteger = div(of(other))

  @JsName("divLong") operator fun div(other: Long): BigInteger = div(of(other))

  /**
   * Returns an array of two BigIntegers containing `(this / val)` followed by `(this % val)`.
   *
   * @param other value by which this BigInteger is to be divided, and the remainder computed.
   * @return an array of two BigIntegers: the quotient `(this / val)` is the initial element, and
   *   the remainder `(this % val)` is the final element.
   * @throws ArithmeticException if other is zero.
   */
  @JsName("divideAndRemainder") fun divideAndRemainder(other: BigInteger): Array<out BigInteger>

  @JsName("divideAndRemainderInt")
  fun divideAndRemainder(other: Int): Array<out BigInteger> = divideAndRemainder(of(other))

  @JsName("divideAndRemainderLong")
  fun divideAndRemainder(other: Long): Array<out BigInteger> = divideAndRemainder(of(other))

  /**
   * Returns a BigInteger whose value is `(this % val)`.
   *
   * @param other value by which this BigInteger is to be divided, and the remainder computed.
   * @return `this % val`
   * @throws ArithmeticException if other is zero.
   */
  @JsName("reminder") fun remainder(other: BigInteger): BigInteger

  @JsName("reminderInt") fun remainder(other: Int): BigInteger = remainder(of(other))

  @JsName("reminderLong") fun remainder(other: Long): BigInteger = remainder(of(other))

  /**
   * Returns a BigInteger whose value is `(this<sup>exponent</sup>)`. Note that `exponent` is an
   * integer rather than a BigInteger.
   *
   * @param exponent exponent to which this BigInteger is to be raised.
   * @return `this<sup>exponent</sup>`
   * @throws ArithmeticException `exponent` is negative. (This would cause the operation to yield a
   *   non-integer value.)
   */
  @JsName("pow") infix fun pow(exponent: Int): BigInteger

  /**
   * Returns the integer square root of this BigInteger. The integer square root of the
   * corresponding mathematical integer `n` is the largest mathematical integer `s` such that `s*s
   * <= n`. It is equal to the value of `floor(sqrt(n))`, where `sqrt(n)` denotes the real square
   * root of `n` treated as a real. Note that the integer square root will be less than the real
   * square root if the latter is not representable as an integral value.
   *
   * @return the integer square root of `this`
   * @throws ArithmeticException if `this` is negative. (The square root of a negative integer other
   *   is `(i * sqrt(-val))` where *i* is the *imaginary unit* and is equal to `sqrt(-1)`.)
   * @since 9
   */
  @JsName("sqrt") fun sqrt(): BigInteger

  /**
   * Returns an array of two BigIntegers containing the integer square root `s` of `this` and its
   * remainder `this - s*s`, respectively.
   *
   * @return an array of two BigIntegers with the integer square root at offset 0 and the remainder
   *   at offset 1
   * @throws ArithmeticException if `this` is negative. (The square root of a negative integer other
   *   is `(i * sqrt(-val))` where *i* is the *imaginary unit* and is equal to `sqrt(-1)`.)
   * @see .sqrt
   * @since 9
   */
  @JsName("sqrtAndRemainder") fun sqrtAndRemainder(): Array<out BigInteger>

  /**
   * Returns a BigInteger whose value is the greatest common divisor of `absoluteValue(this)` and
   * `absoluteValue(val)`. Returns 0 if `this == 0 && val == 0`.
   *
   * @param other value with which the GCD is to be computed.
   * @return `GCD(absoluteValue(this), absoluteValue(val))`
   */
  @JsName("gcd") fun gcd(other: BigInteger): BigInteger

  @JsName("gcdInt") fun gcd(other: Int): BigInteger = gcd(of(other))

  @JsName("gcdLong") fun gcd(other: Long): BigInteger = gcd(of(other))

  /**
   * Returns a BigInteger whose value is `(-this)`.
   *
   * @return `-this`
   */
  @JsName("unaryMinus") operator fun unaryMinus(): BigInteger

  @JsName("unaryPlus") operator fun unaryPlus(): BigInteger

  /**
   * Returns a BigInteger whose value is `(this rem m`). This method differs from `remainder` in
   * that it always returns a *non-negative* BigInteger.
   *
   * @param modulus the modulus.
   * @return `this rem m`
   * @throws ArithmeticException `m` 0
   * @see .remainder
   */
  @JsName("rem") operator fun rem(modulus: BigInteger): BigInteger

  @JsName("remInt") operator fun rem(modulus: Int): BigInteger = rem(of(modulus))

  @JsName("remLong") operator fun rem(modulus: Long): BigInteger = rem(of(modulus))

  /**
   * Returns a BigInteger whose value is `(this<sup>exponent</sup> rem m)`. (Unlike `pow`, this
   * method permits negative exponents.)
   *
   * @param exponent the exponent.
   * @param modulus the modulus.
   * @return `this<sup>exponent</sup> rem m`
   * @throws ArithmeticException `m` 0 or the exponent is negative and this BigInteger is not
   *   *relatively prime* to `m`.
   * @see .modInverse
   */
  @JsName("modPow") fun modPow(exponent: BigInteger, modulus: BigInteger): BigInteger

  /**
   * Returns a BigInteger whose value is `(this`<sup>-1</sup> `rem m)`.
   *
   * @param modulus the modulus.
   * @return `this`<sup>-1</sup> `rem m`.
   * @throws ArithmeticException `m` 0, or this BigInteger has no multiplicative inverse rem m (that
   *   is, this BigInteger is not *relatively prime* to m).
   */
  @JsName("modInverse") fun modInverse(modulus: BigInteger): BigInteger

  @JsName("modInverseInt") fun modInverse(modulus: Int): BigInteger = modInverse(of(modulus))

  @JsName("modInverseLong") fun modInverse(modulus: Long): BigInteger = modInverse(of(modulus))

  /**
   * Returns a BigInteger whose value is `(this << n)`. The shift distance, `n`, may be negative, in
   * which case this method performs a right shift. (Computes `floor(this * 2<sup>n</sup>)`.)
   *
   * @param n shift distance, in bits.
   * @return `this << n`
   * @see .shr
   */
  @JsName("shl") infix fun shl(n: Int): BigInteger

  /**
   * Returns a BigInteger whose value is `(this >> n)`. Sign extension is performed. The shift
   * distance, `n`, may be negative, in which case this method performs a left shift. (Computes
   * `floor(this / 2<sup>n</sup>)`.)
   *
   * @param n shift distance, in bits.
   * @return `this >> n`
   * @see .shl
   */
  @JsName("shr") infix fun shr(n: Int): BigInteger

  /**
   * Returns a BigInteger whose value is `(this & val)`. (This method returns a negative BigInteger
   * if and only if this and val are both negative.)
   *
   * @param other value to be AND'ed with this BigInteger.
   * @return `this & val`
   */
  @JsName("and") fun and(other: BigInteger): BigInteger

  @JsName("andInt") fun and(other: Int): BigInteger = and(of(other))

  @JsName("andLong") fun and(other: Long): BigInteger = and(of(other))

  /**
   * Returns a BigInteger whose value is `(this | val)`. (This method returns a negative BigInteger
   * if and only if either this or val is negative.)
   *
   * @param other value to be OR'ed with this BigInteger.
   * @return `this | val`
   */
  @JsName("or") fun or(other: BigInteger): BigInteger

  @JsName("orInt") fun or(other: Int): BigInteger = or(of(other))

  @JsName("orLong") fun or(other: Long): BigInteger = or(of(other))

  /**
   * Returns a BigInteger whose value is `(this ^ val)`. (This method returns a negative BigInteger
   * if and only if exactly one of this and val are negative.)
   *
   * @param other value to be XOR'ed with this BigInteger.
   * @return `this ^ val`
   */
  @JsName("xor") fun xor(other: BigInteger): BigInteger

  @JsName("xorInt") fun xor(other: Int): BigInteger = xor(of(other))

  @JsName("xorLong") fun xor(other: Long): BigInteger = xor(of(other))

  /**
   * Returns a BigInteger whose value is `(~this)`. (This method returns a negative value if and
   * only if this BigInteger is non-negative.)
   *
   * @return `~this`
   */
  @JsName("not") operator fun not(): BigInteger

  /**
   * Returns a BigInteger whose value is `(this & ~val)`. This method, which is equivalent to
   * `and(val.not())`, is provided as a convenience for masking operations. (This method returns a
   * negative BigInteger if and only if `this` is negative and other is positive.)
   *
   * @param other value to be complemented and AND'ed with this BigInteger.
   * @return `this & ~val`
   */
  @JsName("andNot") fun andNot(other: BigInteger): BigInteger

  @JsName("andNotInt") fun andNot(other: Int): BigInteger = andNot(of(other))

  @JsName("andNotLong") fun andNot(other: Long): BigInteger = andNot(of(other))

  /**
   * Returns `true` if and only if the designated bit is set. (Computes `((this & (1<<n)) != 0)`.)
   *
   * @param n index of bit to test.
   * @return `true` if and only if the designated bit is set.
   * @throws ArithmeticException `n` is negative.
   */
  @JsName("testBit") fun testBit(n: Int): Boolean

  @JsName("get") operator fun get(n: Int): Boolean

  @JsName("set") operator fun set(n: Int, b: Boolean): BigInteger

  /**
   * Returns a BigInteger whose value is equivalent to this BigInteger with the designated bit set.
   * (Computes `(this | (1<<n))`.)
   *
   * @param n index of bit to set.
   * @return `this | (1<<n)`
   * @throws ArithmeticException `n` is negative.
   */
  @JsName("setBit") fun setBit(n: Int): BigInteger

  /**
   * Returns a BigInteger whose value is equivalent to this BigInteger with the designated bit
   * cleared. (Computes `(this & ~(1<<n))`.)
   *
   * @param n index of bit to clear.
   * @return `this & ~(1<<n)`
   * @throws ArithmeticException `n` is negative.
   */
  @JsName("clearBit") fun clearBit(n: Int): BigInteger

  /**
   * Returns a BigInteger whose value is equivalent to this BigInteger with the designated bit
   * flipped. (Computes `(this ^ (1<<n))`.)
   *
   * @param n index of bit to flip.
   * @return `this ^ (1<<n)`
   * @throws ArithmeticException `n` is negative.
   */
  @JsName("flipBit") fun flipBit(n: Int): BigInteger

  /**
   * Returns `true` if this BigInteger is probably prime, `false` if it's definitely composite. If
   * `certainty` is 0, `true` is returned.
   *
   * @param certainty a measure of the uncertainty that the caller is willing to tolerate: if the
   *   call returns `true` the probability that this BigInteger is prime exceeds (1 -
   *   1/2<sup>`certainty`</sup>). The execution time of this method is proportional to the value of
   *   this parameter.
   * @return `true` if this BigInteger is probably prime, `false` if it's definitely composite.
   */
  @JsName("isProbablePrime") fun isProbablePrime(certainty: Int): Boolean

  /**
   * Compares this BigInteger with the specified BigInteger. This method is provided in preference
   * to individual methods for each of the six boolean comparison operators (&lt;, ==, &gt;, &gt;=,
   * !=, &lt;=). The suggested idiom for performing these comparisons is: `(x.compareTo(y)`
   * &lt;*op*&gt; `0)`, where &lt;*op*&gt; is one of the six comparison operators.
   *
   * @param other BigInteger to which this BigInteger is to be compared.
   * @return -1, 0 or 1 as this BigInteger is numerically less than, equal to, or greater than
   *   other.
   */
  override operator fun compareTo(other: BigInteger): Int

  /**
   * Compares this BigInteger with the specified Object for equality.
   *
   * @param other Object to which this BigInteger is to be compared.
   * @return `true` if and only if the specified Object is a BigInteger whose value is numerically
   *   equal to this BigInteger.
   */
  override fun equals(other: Any?): Boolean

  /**
   * Returns the minimum of this BigInteger and other.
   *
   * @param other value with which the minimum is to be computed.
   * @return the BigInteger whose value is the lesser of this BigInteger and other. If they are
   *   equal, either may be returned.
   */
  @JsName("min") fun min(other: BigInteger): BigInteger

  /**
   * Returns the maximum of this BigInteger and other.
   *
   * @param other value with which the maximum is to be computed.
   * @return the BigInteger whose value is the greater of this and other. If they are equal, either
   *   may be returned.
   */
  @JsName("max") fun max(other: BigInteger): BigInteger

  /**
   * Returns the hash code for this BigInteger.
   *
   * @return hash code for this BigInteger.
   */
  override fun hashCode(): Int

  /**
   * Returns the String representation of this BigInteger in the given radix. If the radix is
   * outside the range from [CHAR_MIN_RADIX] to [CHAR_MAX_RADIX] inclusive, it will default to 10
   * (as is the case for `Int.toString`). The digit-to-character mapping provided by
   * `Character.forDigit` is used, and a subtract sign is prepended if appropriate. (This
   * representation is compatible with the String constructor.)
   *
   * @param radix radix of the String representation.
   * @return String representation of this BigInteger in the given radix.
   * @see Int.toString
   */
  @JsName("toStringWithRadix") fun toString(radix: Int): String

  /**
   * Returns the decimal String representation of this BigInteger. The digit-to-character mapping
   * provided by `Character.forDigit` is used, and a subtract sign is prepended if appropriate.
   * (This representation is compatible with the string constructor, and allows for String
   * concatenation with Java's + operator.)
   *
   * @return decimal String representation of this BigInteger.
   */
  override fun toString(): String

  /**
   * Returns a byte array containing the two's-complement representation of this BigInteger. The
   * byte array will be in *big-endian* byte-order: the most significant byte is in the zeroth
   * element. The array will contain the minimum number of bytes required to represent this
   * BigInteger, including at least one sign bit, which is `(ceil((this.bitLength() + 1)/8))`. (This
   * representation is compatible with the byte array constructor.)
   *
   * @return a byte array containing the two's-complement representation of this BigInteger.
   */
  @JsName("toByteArray") fun toByteArray(): ByteArray

  /**
   * Converts this BigInteger to an `int`. This conversion is analogous to a *narrowing primitive
   * conversion* from `long` to `int` as defined in <cite>The Java Language Specification</cite>: if
   * this BigInteger is too big to fit in an `int`, only the low-order 32 bits are returned. Note
   * that this conversion can lose information about the overall magnitude of the BigInteger value
   * as well as return a result with the opposite sign.
   *
   * @return this BigInteger converted to an `int`.
   * @see BigInteger.toIntExact
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toInt") fun toInt(): Int

  /**
   * Converts this BigInteger to a `long`. This conversion is analogous to a *narrowing primitive
   * conversion* from `long` to `int` as defined in <cite>The Java Language Specification</cite>: if
   * this BigInteger is too big to fit in a `long`, only the low-order 64 bits are returned. Note
   * that this conversion can lose information about the overall magnitude of the BigInteger value
   * as well as return a result with the opposite sign.
   *
   * @return this BigInteger converted to a `long`.
   * @see .toLongExact
   * @jls 5.1.3 Narrowing Primitive Conversion
   */
  @JsName("toLong") fun toLong(): Long

  @JsName("toByte") fun toByte(): Byte

  @JsName("toChar") fun toChar(): Char

  @JsName("toShort") fun toShort(): Short

  @JsName("toFloat") fun toFloat(): Float

  @JsName("toDouble") fun toDouble(): Double

  /**
   * Converts this `BigInteger` to a `long`, checking for lost information. If the value of this
   * `BigInteger` is out of the range of the `long` type, then an `ArithmeticException` is thrown.
   *
   * @return this `BigInteger` converted to a `long`.
   * @throws ArithmeticException if the value of `this` will not exactly fit in a `long`.
   * @see BigInteger.toLong
   * @since 1.8
   */
  fun toLongExact(): Long

  /**
   * Converts this `BigInteger` to an `int`, checking for lost information. If the value of this
   * `BigInteger` is out of the range of the `int` type, then an `ArithmeticException` is thrown.
   *
   * @return this `BigInteger` converted to an `int`.
   * @throws ArithmeticException if the value of `this` will not exactly fit in an `int`.
   * @see BigInteger.toInt
   * @since 1.8
   */
  @JsName("toIntExact") fun toIntExact(): Int

  /**
   * Converts this `BigInteger` to a `short`, checking for lost information. If the value of this
   * `BigInteger` is out of the range of the `short` type, then an `ArithmeticException` is thrown.
   *
   * @return this `BigInteger` converted to a `short`.
   * @throws ArithmeticException if the value of `this` will not exactly fit in a `short`.
   * @see BigInteger.toShort
   * @since 1.8
   */
  @JsName("toShortExact") fun toShortExact(): Short

  /**
   * Converts this `BigInteger` to a `byte`, checking for lost information. If the value of this
   * `BigInteger` is out of the range of the `byte` type, then an `ArithmeticException` is thrown.
   *
   * @return this `BigInteger` converted to a `byte`.
   * @throws ArithmeticException if the value of `this` will not exactly fit in a `byte`.
   * @see BigInteger.toByte
   * @since 1.8
   */
  @JsName("toByteExact") fun toByteExact(): Byte

  companion object {
    /**
     * The BigInteger constant zero.
     *
     * @since 1.2
     */
    @JvmField @JsName("ZERO") val ZERO: BigInteger = BigIntegers.zero

    /**
     * The BigInteger constant one.
     *
     * @since 1.2
     */
    @JvmField @JsName("ONE") val ONE: BigInteger = BigIntegers.one

    /**
     * The BigInteger constant two.
     *
     * @since 9
     */
    @JvmField @JsName("TWO") val TWO: BigInteger = BigIntegers.two

    /** The BigInteger constant -1. (Not exported.) */
    @JvmField @JsName("NEGATIVE_ONE") val NEGATIVE_ONE: BigInteger = BigIntegers.negativeOne

    /**
     * The BigInteger constant ten.
     *
     * @since 1.5
     */
    @JvmField @JsName("TEN") val TEN: BigInteger = BigIntegers.ten

    /**
     * Returns a positive BigInteger that is probably prime, with the specified bitLength. The
     * probability that a BigInteger returned by this method is composite does not exceed
     * 2<sup>-100</sup>.
     *
     * @param bitLength bitLength of the returned BigInteger.
     * @param rnd source of random bits used to select candidates to be tested for primality.
     * @return a BigInteger of `bitLength` bits that is probably prime
     * @throws ArithmeticException `bitLength < 2` or `bitLength` is too large.
     * @see BigInteger.bitLength
     * @since 1.4
     */
    @JvmStatic
    @JsName("probablePrime")
    fun probablePrime(bitLength: Int, rnd: Random): BigInteger =
      bigProbablePrimeInteger(bitLength, rnd)

    /**
     * Returns a BigInteger whose value is equal to that of the specified `long`.
     *
     * @apiNote This static factory method is provided in preference to a (`long`) constructor
     * because it allows for reuse of frequently used BigIntegers.
     *
     * @param value value of the BigInteger to return.
     * @return a BigInteger with the specified value.
     */
    @JsName("ofLong") @JvmStatic fun of(value: Long): BigInteger = bigIntegerOf(value)

    @JsName("of") @JvmStatic fun of(value: Int): BigInteger = bigIntegerOf(value)

    @JsName("parse") @JvmStatic fun of(value: String): BigInteger = bigIntegerOf(value)

    @JsName("parseWithRadix")
    @JvmStatic
    fun of(value: String, radix: Int): BigInteger = bigIntegerOf(value, radix)

    /**
     * Returns a BigInteger with the given two's complement representation. Assumes that the input
     * array will not be modified (the returned BigInteger will reference the input array if
     * feasible).
     */
    @JsName("ofIntArray") @JvmStatic fun of(value: IntArray): BigInteger = bigIntegerOf(value)

    @JsName("ofSlideIntArray")
    fun of(signum: Int, magnitude: ByteArray, off: Int, len: Int): BigInteger =
      bigIntegerOf(signum, magnitude, off, len)
  }
}
