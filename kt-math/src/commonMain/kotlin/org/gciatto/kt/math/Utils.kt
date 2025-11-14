@file:JvmName("Utils")

package org.gciatto.kt.math

import kotlin.jvm.JvmName
import kotlin.random.Random

const val DEBUG = false

internal fun log(lazyObject: () -> Any) {
  if (DEBUG) {
    logImpl(lazyObject)
  }
}

internal fun String.getRadix(): Pair<Int, String> =
  when {
    this.contains("0B", ignoreCase = true) -> {
      Pair(2, this.replaceFirst("0B", "").replaceFirst("0b", ""))
    }
    this.contains("0O", ignoreCase = true) -> {
      Pair(8, this.replaceFirst("0O", "").replaceFirst("0o", ""))
    }
    this.contains("0X", ignoreCase = true) -> {
      Pair(16, this.replaceFirst("0X", "").replaceFirst("0x", ""))
    }
    else -> Pair(10, this)
  }

internal const val PI_STRING =
  "3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862" +
    "803482534211706798214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895" +
    "4930381964428810975665933446128475648233786783165271201909145648"

internal const val E_STRING =
  "2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571" +
    "382178525166427427466391932003059921817413596629043572900334295260595630738132328627943490763233829880753195" +
    "251019011573834187930702154089149934884167509244761460668082264"

internal expect fun logImpl(lazyObject: () -> Any)

internal expect inline fun <T, reified U : T> T?.castTo(): U

internal expect fun bigProbablePrimeInteger(bitLength: Int, rnd: Random): BigInteger

internal expect fun bigIntegerOf(value: Long): BigInteger

internal expect fun bigIntegerOf(value: Int): BigInteger

internal expect fun bigIntegerOf(value: String): BigInteger

internal expect fun bigIntegerOf(value: String, radix: Int): BigInteger

internal expect fun bigIntegerOf(value: IntArray): BigInteger

internal expect fun bigIntegerOf(
  signum: Int,
  magnitude: ByteArray,
  off: Int = 0,
  len: Int = magnitude.size,
): BigInteger

internal expect fun bigDecimalOf(unscaledVal: Long, scale: Int): BigDecimal

internal expect fun bigDecimalOf(unscaledVal: Long, scale: Int, prec: Int): BigDecimal

internal expect fun bigDecimalOf(`val`: Int): BigDecimal

internal expect fun bigDecimalOf(`val`: Long): BigDecimal

internal expect fun bigDecimalOf(intVal: BigInteger, scale: Int, prec: Int): BigDecimal

internal expect fun bigDecimalOf(`val`: Double, ctx: MathContext? = null): BigDecimal

internal expect fun bigDecimalOf(`val`: Float, ctx: MathContext? = null): BigDecimal

internal expect fun bigDecimalOf(`val`: String, ctx: MathContext? = null): BigDecimal

internal expect fun bigDecimalOf(`val`: BigInteger, ctx: MathContext? = null): BigDecimal

internal expect fun bigDecimalOf(`val`: Int, ctx: MathContext): BigDecimal

internal expect fun bigDecimalOf(`val`: Long, ctx: MathContext): BigDecimal

internal expect fun bigDecimalOf(`in`: CharArray, offset: Int, len: Int): BigDecimal

internal expect object BigIntegers {
  val zero: BigInteger
  val one: BigInteger
  val two: BigInteger
  val ten: BigInteger
  val negativeOne: BigInteger
}

internal expect object BigDecimals {
  val zero: BigDecimal
  val one: BigDecimal
  val two: BigDecimal
  val ten: BigDecimal
  val oneTenth: BigDecimal
  val oneHalf: BigDecimal
  val pi: BigDecimal
  val e: BigDecimal
}

internal fun Long.numberOfLeadingZeros(): Int {
  // HD, Figure 5-6
  if (this == 0L) {
    return 64
  }
  var n = 1
  var x = this.ushr(32).toInt()

  if (x == 0) {
    n += 32
    x = this.toInt()
  }
  if (x.ushr(16) == 0) {
    n += 16
    x = x shl 16
  }
  if (x.ushr(24) == 0) {
    n += 8
    x = x shl 8
  }
  if (x.ushr(28) == 0) {
    n += 4
    x = x shl 4
  }
  if (x.ushr(30) == 0) {
    n += 2
    x = x shl 2
  }
  n -= x.ushr(31)
  return n
}

internal fun Int.numberOfLeadingZeros(): Int {
  // HD, Figure 5-6
  var x = this

  if (x == 0) {
    return 32
  }

  var n = 1

  if (x.ushr(16) == 0) {
    n += 16
    x = x shl 16
  }

  if (x.ushr(24) == 0) {
    n += 8
    x = x shl 8
  }

  if (x.ushr(28) == 0) {
    n += 4
    x = x shl 4
  }

  if (x.ushr(30) == 0) {
    n += 2
    x = x shl 2
  }

  n -= x.ushr(31)

  return n
}

internal fun Int.numberOfTrailingZeros(): Int {
  // HD, Figure 5-14

  var y: Int
  var i = this

  if (i == 0) return 32

  var n = 31

  y = i shl 16
  if (y != 0) {
    n -= 16
    i = y
  }

  y = i shl 8
  if (y != 0) {
    n -= 8
    i = y
  }

  y = i shl 4
  if (y != 0) {
    n -= 4
    i = y
  }

  y = i shl 2
  if (y != 0) {
    n -= 2
    i = y
  }

  return n - (i shl 1).ushr(31)
}

internal const val CHAR_MIN_RADIX = 2

internal const val CHAR_MAX_RADIX = 36

internal fun Char.isDigit(): Boolean = this.isDigit(10)

internal fun Char.isDigit(radix: Int): Boolean =
  radix in CHAR_MAX_RADIX..CHAR_MAX_RADIX &&
    if (radix > 10) {
      val delta = radix - 10
      this in '0'..'9' || this in 'a' until ('a' + delta) || this in 'A' until ('A' + delta)
    } else {
      this in '0' until ('0' + radix)
    }

internal fun Char.toDigit(): Int = this.toDigit(10)

internal fun Char.toDigit(radix: Int): Int {
  if (radix in CHAR_MIN_RADIX..CHAR_MAX_RADIX) {
    if (radix > 10) {
      val delta = radix - 10
      when (this) {
        in '0'..'9' -> return this - '0'
        in 'a' until ('a' + delta) -> return this - 'a' + 10
        in 'A' until ('A' + delta) -> return this - 'A' + 10
      }
    } else {
      if (this in '0' until ('0' + radix)) {
        return this - '0'
      }
    }
  }

  return -1
}

internal fun <T> arrayCopy(
  src: Array<T>,
  srcIndex: Int,
  dest: Array<T>,
  destIndex: Int,
  size: Int,
) {
  for (i in 0 until size) {
    dest[destIndex + i] = src[srcIndex + i]
  }
}

internal fun arrayCopy(src: IntArray, srcIndex: Int, dest: IntArray, destIndex: Int, size: Int) {
  for (i in 0 until size) {
    dest[destIndex + i] = src[srcIndex + i]
  }
}

internal inline fun <reified T> Array<T>.cloneArray(): Array<T> =
  Array<T>(this.size) { i -> this[i] }

internal fun IntArray.cloneArray(): IntArray = IntArray(this.size) { i -> this[i] }

internal fun Int.bitCount(): Int {
  // HD, Figure 5-2
  var i = this
  i -= (i.ushr(1) and 0x55555555)
  i = (i and 0x33333333) + (i.ushr(2) and 0x33333333)
  i = i + i.ushr(4) and 0x0f0f0f0f
  i += i.ushr(8)
  i += i.ushr(16)
  return i and 0x3f
}

internal fun StringBuilder.insertChar(index: Int, char: Char): StringBuilder =
  this.insert(index, char)

internal fun CharSequence.toCharArray(): CharArray = CharArray(this.length) { this[it] }

internal fun IntArray.fill(x: Int): IntArray = this.fill(0, this.size, x)

internal fun IntArray.fill(from: Int, to: Int, x: Int): IntArray {
  for (i in from until to) {
    this[i] = x
  }
  return this
}

internal fun StringBuilder.appendCharArray(char: CharArray, offset: Int, len: Int): StringBuilder {
  for (i in offset until offset + len) {
    append(char[i])
  }
  return this
}
