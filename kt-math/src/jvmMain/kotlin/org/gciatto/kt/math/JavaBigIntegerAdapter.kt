package org.gciatto.kt.math

import java.math.BigInteger as JavaBigInteger

internal class JavaBigIntegerAdapter(
    val value: JavaBigInteger,
) : BigInteger {
    private inline fun adapt(f: () -> JavaBigInteger): JavaBigIntegerAdapter = JavaBigIntegerAdapter(f())

    private inline fun adapt(
        other: JavaBigIntegerAdapter,
        f: (JavaBigInteger) -> JavaBigInteger,
    ): JavaBigIntegerAdapter = JavaBigIntegerAdapter(f(other.value))

    private inline fun adapt(
        other: BigInteger,
        f: (JavaBigInteger) -> JavaBigInteger,
    ): JavaBigIntegerAdapter = adapt(other.castTo(), f)

    private inline fun adaptAll(f: () -> Array<JavaBigInteger>): Array<out JavaBigIntegerAdapter> {
        val javaInts = f()
        return Array(javaInts.size) { index -> adapt { javaInts[index] } }
    }

    private inline fun adaptAll(
        other: BigInteger,
        f: (JavaBigInteger) -> Array<JavaBigInteger>,
    ): Array<out JavaBigIntegerAdapter> {
        val javaInts = f(other.castTo())
        return Array(javaInts.size) { index -> adapt { javaInts[index] } }
    }

    override val absoluteValue: BigInteger
        get() = adapt { value.abs() }

    override val signum: Int
        get() = value.signum()

    override val bitLength: Int
        get() = value.bitLength()

    override val bitCount: Int
        get() = value.bitCount()

    override fun nextProbablePrime(): BigInteger =
        adapt {
            value.nextProbablePrime()
        }

    override fun plus(other: BigInteger): BigInteger = adapt(other) { value.add(it) }

    override fun minus(other: BigInteger): BigInteger = adapt(other) { value.subtract(it) }

    override fun times(other: BigInteger): BigInteger = adapt(other) { value.multiply(it) }

    override fun div(other: BigInteger): BigInteger = adapt(other) { value.divide(it) }

    override fun divideAndRemainder(other: BigInteger): Array<out BigInteger> =
        adaptAll(other) {
            value.divideAndRemainder(it)
        }

    override fun remainder(other: BigInteger): BigInteger = adapt(other) { value.remainder(it) }

    override fun pow(exponent: Int): BigInteger = adapt { value.pow(exponent) }

    override fun sqrt(): BigInteger = adapt { value.sqrt() }

    override fun sqrtAndRemainder(): Array<out BigInteger> = adaptAll { value.sqrtAndRemainder() }

    override fun gcd(other: BigInteger): BigInteger = adapt(other) { value.gcd(it) }

    override fun unaryMinus(): BigInteger = adapt { value.negate() }

    override fun unaryPlus(): BigInteger = this

    override fun rem(modulus: BigInteger): BigInteger = adapt(modulus) { value.remainder(it) }

    override fun modPow(
        exponent: BigInteger,
        modulus: BigInteger,
    ): BigInteger =
        adapt(exponent) {
            value.modPow(it, modulus.castTo<BigInteger, JavaBigIntegerAdapter>().value)
        }

    override fun modInverse(modulus: BigInteger): BigInteger = adapt(modulus) { value.modInverse(it) }

    override fun shl(n: Int): BigInteger = adapt { value.shl(n) }

    override fun shr(n: Int): BigInteger = adapt { value.shr(n) }

    override fun and(other: BigInteger): BigInteger = adapt(other) { value.and(it) }

    override fun or(other: BigInteger): BigInteger = adapt(other) { value.or(it) }

    override fun xor(other: BigInteger): BigInteger = adapt(other) { value.xor(it) }

    override fun not(): BigInteger = adapt { value.not() }

    override fun andNot(other: BigInteger): BigInteger = adapt(other) { value.andNot(it) }

    override fun testBit(n: Int): Boolean = value.testBit(n)

    override fun get(n: Int): Boolean = testBit(n)

    override fun set(
        n: Int,
        b: Boolean,
    ): BigInteger =
        adapt {
            if (b) value.setBit(n) else value.clearBit(n)
        }

    override fun setBit(n: Int): BigInteger = adapt { value.setBit(n) }

    override fun clearBit(n: Int): BigInteger = adapt { value.clearBit(n) }

    override fun flipBit(n: Int): BigInteger = adapt { value.flipBit(n) }

    override fun isProbablePrime(certainty: Int): Boolean = value.isProbablePrime(certainty)

    @Suppress("NAME_SHADOWING")
    override fun compareTo(other: BigInteger): Int {
        val other: JavaBigIntegerAdapter = other.castTo()
        return value.compareTo(other.value)
    }

    @Suppress("NAME_SHADOWING")
    override fun equals(other: Any?): Boolean = other is JavaBigIntegerAdapter && value == other.value

    override fun min(other: BigInteger): BigInteger = if (this <= other) this else other

    override fun max(other: BigInteger): BigInteger = if (this >= other) this else other

    override fun hashCode(): Int = value.hashCode()

    override fun toString(radix: Int): String = value.toString(radix)

    override fun toString(): String = value.toString()

    override fun toByteArray(): ByteArray = value.toByteArray()

    override fun toInt(): Int = value.toInt()

    override fun toLong(): Long = value.toLong()

    override fun toByte(): Byte = value.toByte()

    override fun toChar(): Char = value.toInt().toChar()

    override fun toShort(): Short = value.toShort()

    override fun toFloat(): Float = value.toFloat()

    override fun toDouble(): Double = value.toDouble()

    override fun toLongExact(): Long = value.longValueExact()

    override fun toIntExact(): Int = value.intValueExact()

    override fun toShortExact(): Short = value.shortValueExact()

    @Suppress("NAME_SHADOWING")
    override fun toByteExact(): Byte = value.byteValueExact()
}
