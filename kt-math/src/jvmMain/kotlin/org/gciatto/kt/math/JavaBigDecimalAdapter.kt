package org.gciatto.kt.math

import java.math.BigDecimal as JavaBigDecimal

class JavaBigDecimalAdapter(val value: JavaBigDecimal) : BigDecimal {
  private inline fun adapt(f: () -> JavaBigDecimal): JavaBigDecimalAdapter =
    JavaBigDecimalAdapter(f())

  private inline fun adapt(
    other: JavaBigDecimalAdapter,
    f: (JavaBigDecimal) -> JavaBigDecimal,
  ): JavaBigDecimalAdapter = JavaBigDecimalAdapter(f(other.value))

  private inline fun adapt(
    other: BigDecimal?,
    f: (JavaBigDecimal) -> JavaBigDecimal,
  ): JavaBigDecimalAdapter = adapt(other.castTo(), f)

  override val absoluteValue: BigDecimal
    get() = adapt { value.abs() }

  override val signum: Int
    get() = value.signum()

  override val scale: Int
    get() = value.scale()

  override val precision: Int
    get() = value.precision()

  override val unscaledValue: BigInteger
    get() = JavaBigIntegerAdapter(value.unscaledValue())

  override fun plus(augend: BigDecimal?): BigDecimal = adapt(augend) { value.add(it) }

  override fun plus(augend: BigDecimal?, mc: MathContext): BigDecimal =
    adapt(augend) { value.add(it, mc.toJava()) }

  override fun minus(subtrahend: BigDecimal): BigDecimal = adapt(subtrahend) { value.subtract(it) }

  override fun minus(subtrahend: BigDecimal, mc: MathContext): BigDecimal =
    adapt(subtrahend) { value.subtract(it, mc.toJava()) }

  override fun times(multiplicand: BigDecimal?): BigDecimal =
    adapt(multiplicand) { value.multiply(it) }

  override fun times(multiplicand: BigDecimal, mc: MathContext): BigDecimal =
    adapt(multiplicand) { value.multiply(it, mc.toJava()) }

  override fun div(divisor: BigDecimal): BigDecimal = adapt(divisor) { value.divide(it) }

  override fun div(divisor: BigDecimal, mc: MathContext): BigDecimal =
    adapt(divisor) { value.divide(it, mc.toJava()) }

  override fun divide(divisor: BigDecimal, scale: Int, roundingMode: RoundingMode): BigDecimal =
    adapt(divisor) { value.divide(it, scale, roundingMode.toJava()) }

  override fun divideToIntegralValue(divisor: BigDecimal): BigDecimal =
    adapt(divisor) { value.divideToIntegralValue(it) }

  override fun divideToIntegralValue(divisor: BigDecimal, mc: MathContext): BigDecimal =
    adapt(divisor) { value.divideToIntegralValue(it, mc.toJava()) }

  override fun rem(divisor: BigDecimal): BigDecimal = adapt(divisor) { value.remainder(it) }

  override fun rem(divisor: BigDecimal, mc: MathContext): BigDecimal =
    adapt(divisor) { value.remainder(it, mc.toJava()) }

  private fun Array<JavaBigDecimal>.toKotlinPair(): Pair<BigDecimal, BigDecimal> =
    this[0].toKotlin() to this[1].toKotlin()

  override fun divideAndRemainder(divisor: BigDecimal): Pair<BigDecimal, BigDecimal> =
    value.divideAndRemainder(divisor.toJava()).toKotlinPair()

  override fun divideAndRemainder(
    divisor: BigDecimal,
    mc: MathContext,
  ): Pair<BigDecimal, BigDecimal> =
    value.divideAndRemainder(divisor.toJava(), mc.toJava()).toKotlinPair()

  override fun sqrt(mc: MathContext): BigDecimal = adapt { value.sqrt(mc.toJava()) }

  override fun pow(n: Int): BigDecimal = adapt { value.pow(n) }

  override fun pow(n: Int, mc: MathContext): BigDecimal = adapt { value.pow(n, mc.toJava()) }

  override fun absoluteValue(mc: MathContext): BigDecimal = adapt { value.abs(mc.toJava()) }

  override fun unaryMinus(): BigDecimal = adapt { value.negate() }

  override fun unaryMinus(mc: MathContext): BigDecimal = adapt { value.negate(mc.toJava()) }

  override fun unaryPlus(): BigDecimal = this

  override fun unaryPlus(mc: MathContext): BigDecimal = adapt { value.plus(mc.toJava()) }

  override fun round(mc: MathContext): BigDecimal = adapt { value.round(mc.toJava()) }

  override fun setScale(newScale: Int, roundingMode: RoundingMode): BigDecimal = adapt {
    value.setScale(newScale, roundingMode.toJava())
  }

  @Suppress("DEPRECATION")
  @Deprecated(
    "The method {@link #setScale(int, RoundingMode)} should " +
      "be used in preference to this legacy method."
  )
  override fun setScale(newScale: Int, roundingMode: Int): BigDecimal = adapt {
    value.setScale(newScale, roundingMode)
  }

  override fun setScale(newScale: Int): BigDecimal = adapt { value.setScale(newScale) }

  override fun movePointLeft(n: Int): BigDecimal = adapt { value.movePointLeft(n) }

  override fun movePointRight(n: Int): BigDecimal = adapt { value.movePointRight(n) }

  override fun scaleByPowerOfTen(n: Int): BigDecimal = adapt { value.scaleByPowerOfTen(n) }

  override fun stripTrailingZeros(): BigDecimal = adapt { value.stripTrailingZeros() }

  @Suppress("NAME_SHADOWING")
  override fun compareTo(other: BigDecimal): Int {
    val other: JavaBigDecimalAdapter = other.castTo()
    return value.compareTo(other.value)
  }

  override fun min(`val`: BigDecimal): BigDecimal = adapt(`val`) { value.min(it) }

  override fun max(`val`: BigDecimal): BigDecimal = adapt(`val`) { value.max(it) }

  override fun toEngineeringString(): String = value.toEngineeringString()

  override fun toPlainString(): String = value.toPlainString()

  override fun toBigInteger(): BigInteger = value.toBigInteger().toKotlin()

  override fun toBigIntegerExact(): BigInteger = value.toBigIntegerExact().toKotlin()

  override fun toLong(): Long = value.toLong()

  override fun toLongExact(): Long = value.longValueExact()

  override fun toInt(): Int = value.toInt()

  override fun toIntExact(): Int = value.intValueExact()

  override fun toByte(): Byte = value.toByte()

  override fun toByteExact(): Byte = value.byteValueExact()

  override fun toChar(): Char = value.toInt().toChar()

  override fun toShort(): Short = value.toShort()

  override fun toShortExact(): Short = value.shortValueExact()

  override fun toFloat(): Float = value.toFloat()

  override fun toDouble(): Double = value.toDouble()

  override fun ulp(): BigDecimal = value.ulp().toKotlin()

  override fun equals(other: Any?): Boolean = other is JavaBigDecimalAdapter && value == other.value

  override fun hashCode(): Int = value.hashCode()

  override fun toString(): String = value.toString()
}
