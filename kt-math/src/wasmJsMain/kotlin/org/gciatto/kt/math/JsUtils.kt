package org.gciatto.kt.math

import kotlin.random.Random

internal actual fun logImpl(lazyObject: () -> Any) {
  println(lazyObject())
}

internal actual inline fun <T, reified U : T> T?.castTo(): U {
  if (this is U) return this else error("Failed to cast $this")
}

internal actual fun bigProbablePrimeInteger(bitLength: Int, rnd: Random): BigInteger =
  CommonBigInteger.probablePrime(bitLength, rnd)

internal actual fun bigIntegerOf(value: Long): BigInteger = CommonBigInteger.of(value)

internal actual fun bigIntegerOf(value: String): BigInteger = CommonBigInteger.of(value)

internal actual fun bigIntegerOf(value: Int): BigInteger = CommonBigInteger.of(value)

internal actual fun bigIntegerOf(value: String, radix: Int): BigInteger =
  CommonBigInteger.of(value, radix)

internal actual fun bigIntegerOf(value: IntArray): BigInteger = CommonBigInteger.of(value)

internal actual fun bigDecimalOf(unscaledVal: Long, scale: Int): BigDecimal =
  CommonBigDecimal.of(unscaledVal, scale)

internal actual fun bigDecimalOf(unscaledVal: Long, scale: Int, prec: Int): BigDecimal =
  CommonBigDecimal.of(unscaledVal, scale, prec)

internal actual fun bigDecimalOf(`val`: Int): BigDecimal = CommonBigDecimal.of(`val`)

internal actual fun bigDecimalOf(`val`: Long): BigDecimal = CommonBigDecimal.of(`val`)

internal actual fun bigDecimalOf(intVal: BigInteger, scale: Int, prec: Int): BigDecimal =
  CommonBigDecimal.of(intVal.castTo<BigInteger, CommonBigInteger>(), scale, prec)

private val exceptionalDoubles =
  setOf(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)

internal actual fun bigDecimalOf(`val`: Double, ctx: MathContext?): BigDecimal =
  when {
    `val` in exceptionalDoubles -> throw NumberFormatException("Infinite or NaN")
    ctx == null -> CommonBigDecimal(`val`)
    else -> CommonBigDecimal.of(`val`, ctx)
  }

internal actual fun bigDecimalOf(`val`: Float, ctx: MathContext?): BigDecimal =
  bigDecimalOf(`val`.toDouble(), ctx)

internal actual fun bigDecimalOf(`val`: String, ctx: MathContext?): BigDecimal =
  if (ctx == null) {
    CommonBigDecimal(`val`)
  } else {
    CommonBigDecimal.of(`val`, ctx)
  }

internal actual fun bigDecimalOf(`val`: BigInteger, ctx: MathContext?): BigDecimal {
  val value: CommonBigInteger = `val`.castTo()
  return if (ctx == null) {
    CommonBigDecimal(value)
  } else {
    CommonBigDecimal.of(value, ctx)
  }
}

internal actual fun bigDecimalOf(`val`: Int, ctx: MathContext): BigDecimal =
  CommonBigDecimal.of(`val`, ctx)

internal actual fun bigDecimalOf(`val`: Long, ctx: MathContext): BigDecimal =
  CommonBigDecimal.of(`val`, ctx)

internal actual object BigDecimals {
  actual val zero: BigDecimal = CommonBigDecimal.ZERO

  actual val one: BigDecimal = CommonBigDecimal.ONE

  actual val two: BigDecimal = CommonBigDecimal.TWO

  actual val ten: BigDecimal = CommonBigDecimal.TEN

  actual val oneTenth: BigDecimal = CommonBigDecimal.ONE_TENTH

  actual val oneHalf: BigDecimal = CommonBigDecimal.ONE_HALF

  actual val pi: BigDecimal = CommonBigDecimal.PI

  actual val e: BigDecimal = CommonBigDecimal.E
}

internal actual object BigIntegers {
  actual val zero: BigInteger = CommonBigInteger.ZERO

  actual val one: BigInteger = CommonBigInteger.ONE

  actual val two: BigInteger = CommonBigInteger.TWO

  actual val ten: BigInteger = CommonBigInteger.TEN

  actual val negativeOne: BigInteger = CommonBigInteger.NEGATIVE_ONE
}

internal actual fun bigIntegerOf(
  signum: Int,
  magnitude: ByteArray,
  off: Int,
  len: Int,
): BigInteger = CommonBigInteger(signum, magnitude, off, len)
