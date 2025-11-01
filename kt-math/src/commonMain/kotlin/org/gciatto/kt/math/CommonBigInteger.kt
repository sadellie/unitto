/*
 * Copyright (c) 1996, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 * Portions Copyright (c) 1995  Colin Plumb.  All rights reserved.
 */

package org.gciatto.kt.math

import kotlin.experimental.and
import kotlin.js.JsName
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic
import kotlin.math.absoluteValue
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.math.sign
import kotlin.random.Random

/**
 * Immutable arbitrary-precision integers.  All operations behave as if
 * BigIntegers were represented in two's-complement notation (like Java's
 * primitive integer types).  BigInteger provides analogues to all of Java's
 * primitive integer operators, and all relevant methods from `kotlin.math`.
 * Additionally, BigInteger provides operations for modular arithmetic, GCD
 * calculation, primality testing, prime generation, bit manipulation,
 * and a few other miscellaneous operations.
 *
 *
 * Semantics of arithmetic operations exactly mimic those of Java's integer
 * arithmetic operators, as defined in *The Java Language Specification*.
 * For example, division by zero throws an `ArithmeticException`, and
 * division of a negative by a positive yields a negative (or zero) remainder.
 * All of the details in the Spec concerning overflow are ignored, as
 * BigIntegers are made as large as necessary to accommodate the results of an
 * operation.
 *
 *
 * Semantics of shift operations extend those of Java's shift operators
 * to allow for negative shift distances.  A right-shift with a negative
 * shift distance results in a left shift, and vice-versa.  The unsigned
 * right shift operator (`>>>`) is omitted, as this operation makes
 * little sense in combination with the "infinite word size" abstraction
 * provided by this class.
 *
 *
 * Semantics of bitwise logical operations exactly mimic those of Java's
 * bitwise integer operators.  The binary operators (`and`,
 * `or`, `xor`) implicitly perform sign extension on the shorter
 * of the two operands prior to performing the operation.
 *
 *
 * Comparison operations perform signed integer comparisons, analogous to
 * those performed by Java's relational and equality operators.
 *
 *
 * Modular arithmetic operations are provided to compute residues, perform
 * exponentiation, and compute multiplicative inverses.  These methods always
 * return a non-negative result, between `0` and `(modulus - 1)`,
 * inclusive.
 *
 *
 * Bit operations operate on a single bit of the two's-complement
 * representation of their operand.  If necessary, the operand is sign-
 * extended so that it contains the designated bit.  None of the single-bit
 * operations can produce a BigInteger with a different sign from the
 * BigInteger being operated on, as they affect only a single bit, and the
 * "infinite word size" abstraction provided by this class ensures that there
 * are infinitely many "virtual sign bits" preceding each BigInteger.
 *
 *
 * For the sake of brevity and clarity, pseudo-code is used throughout the
 * descriptions of BigInteger methods.  The pseudo-code expression
 * `(i + j)` is shorthand for "a BigInteger whose value is
 * that of the BigInteger `i` plus that of the BigInteger `j`."
 * The pseudo-code expression `(i == j)` is shorthand for
 * "`true` if and only if the BigInteger `i` represents the same
 * value as the BigInteger `j`."  Other pseudo-code expressions are
 * interpreted similarly.
 *
 *
 * All methods and constructors in this class throw
 * `NullPointerException` when passed
 * a null object reference for any input parameter.
 *
 * BigInteger must support values in the range
 * -2<sup>`Int.MAX_VALUE`</sup> (exclusive) to
 * +2<sup>`Int.MAX_VALUE`</sup> (exclusive)
 * and may support values outside of that range.
 *
 * The range of probable prime values is limited and may be less than
 * the full supported positive range of `BigInteger`.
 * The range must be at least 1 to 2<sup>500000000</sup>.
 *
 * @implNote
 * BigInteger constructors and operations throw `ArithmeticException` when
 * the result is out of the supported range of
 * -2<sup>`Int.MAX_VALUE`</sup> (exclusive) to
 * +2<sup>`Int.MAX_VALUE`</sup> (exclusive).
 *
 * @see BigDecimal
 *
 * @jls 4.2.2 Integer Operations
 * @author Josh Bloch
 * @author Michael McCloskey
 * @author Alan Eliasen
 * @author Timothy Buktu
 * @since 1.1
 */

@Suppress(
    "NAME_SHADOWING",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER",
    "ktlint:standard:property-naming",
    "ktlint:standard:backing-property-naming",
)
internal class CommonBigInteger : BigInteger {
    /**
     * The _signum of this BigInteger: -1 for negative, 0 for zero, or
     * 1 for positive.  Note that the BigInteger zero *must* have
     * a _signum of 0.  This is necessary to ensures that there is exactly one
     * representation for each BigInteger value.
     */
    internal val _signum: Int

    /**d
     * The magnitude of this BigInteger, in *big-endian* order: the
     * zeroth element of this array is the most-significant int of the
     * magnitude.  The magnitude must be "minimal" in that the most-significant
     * int (`_mag[0]`) must be non-zero.  This is necessary to
     * ensure that there is exactly one representation for each BigInteger
     * value.  Note that this implies that the BigInteger zero has a
     * zero-length _mag array.
     */
    @JvmField
    internal val _mag: IntArray

    // The following fields are stable variables. A stable variable's value
    // changes at most once from the default zero value to a non-zero stable
    // value. A stable value is calculated lazily on demand.

    /**
     * One plus the bitCount of this BigInteger. This is a stable variable.
     *
     * @see .bitCount
     */
    private var _bitCountPlusOne: Int = 0

    /**
     * One plus the bitLength of this BigInteger. This is a stable variable.
     * (either value is acceptable).
     *
     * @see .bitLength
     */
    private var _bitLengthPlusOne: Int = 0

    /**
     * Two plus the lowest set bit of this BigInteger. This is a stable variable.
     *
     * @see .getLowestSetBit
     */
    private var _lowestSetBitPlusTwo: Int = 0

    /**
     * Two plus the index of the lowest-order int in the magnitude of this
     * BigInteger that contains a nonzero int. This is a stable variable. The
     * least significant int has int-number 0, the next int in order of
     * increasing significance has int-number 1, and so forth.
     *
     *
     * Note: never used for a BigInteger with a magnitude of zero.
     *
     * @see .firstNonzeroIntNum
     */
    private var _firstNonzeroIntNumPlusTwo: Int = 0

    /**
     * Returns the index of the rightmost (lowest-order) one bit in this
     * BigInteger (the number of zero bits to the right of the rightmost
     * one bit).  Returns -1 if this BigInteger contains no one bits.
     * (Computes `(this == 0? -1 : log2(this & -this))`.)
     *
     * @return index of the rightmost one bit in this BigInteger.
     */
    private val lowestSetBit: Int
        get() {
            var lsb = _lowestSetBitPlusTwo - 2
            if (lsb == -2) {
                lsb = 0
                if (_signum == 0) {
                    lsb -= 1
                } else {
                    var i: Int
                    var b: Int
                    i = 0
                    b = getInt(i)
                    while (b == 0) {
                        i++
                        b = getInt(i)
                    }
                    lsb += (i shl 5) + b.numberOfTrailingZeros()
                }
                _lowestSetBitPlusTwo = lsb + 2
            }
            return lsb
        }

    // Constructors

    /**
     * Translates a byte sub-array containing the two's-complement binary
     * representation of a BigInteger into a BigInteger.  The sub-array is
     * specified via an offset into the array and a length.  The sub-array is
     * assumed to be in *big-endian* byte-order: the most significant
     * byte is the element at index `off`.  The other array is
     * assumed to be unchanged for the duration of the constructor call.
     *
     * An `IndexOutOfBoundsException` is thrown if the length of the array
     * other is non-zero and either `off` is negative, `len`
     * is negative, or `off+len` is greater than the length of
     * other.
     *
     * @param other byte array containing a sub-array which is the big-endian
     * two's-complement binary representation of a BigInteger.
     * @param off the start offset of the binary representation.
     * @param len the number of bytes to use.
     * @throws NumberFormatException other is zero bytes long.
     * @throws IndexOutOfBoundsException if the provided array offset and
     * length would cause an index into the byte array to be
     * negative or greater than or equal to the array length.
     * @since 9
     */
    constructor(other: ByteArray, off: Int = 0, len: Int = other.size) {
        if (other.isEmpty()) {
            throw NumberFormatException("Zero length BigInteger")
        } else if (off < 0 ||
            off >= other.size ||
            len < 0 ||
            len > other.size - off
        ) { // 0 <= off < val.length
            throw IndexOutOfBoundsException()
        }

        if (other[off] < 0) {
            _mag = makePositive(other, off, len)
            _signum = -1
        } else {
            _mag = stripLeadingZeroBytes(other, off, len)
            _signum = if (_mag.isEmpty()) 0 else 1
        }
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /**
     * This private constructor translates an int array containing the
     * two's-complement binary representation of a BigInteger into a
     * BigInteger. The input array is assumed to be in *big-endian*
     * int-order: the most significant int is in the zeroth element.  The
     * other array is assumed to be unchanged for the duration of
     * the constructor call.
     */
    private constructor(other: IntArray) {
        if (other.isEmpty()) {
            throw NumberFormatException("Zero length BigInteger")
        }

        if (other[0] < 0) {
            _mag = makePositive(other)
            _signum = -1
        } else {
            _mag = trustedStripLeadingZeroInts(other)
            _signum = if (_mag.isEmpty()) 0 else 1
        }
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /**
     * Translates the sign-magnitude representation of a BigInteger into a
     * BigInteger.  The sign is represented as an integer _signum value: -1 for
     * negative, 0 for zero, or 1 for positive.  The magnitude is a sub-array of
     * a byte array in *big-endian* byte-order: the most significant byte
     * is the element at index `off`.  A zero value of the length
     * `len` is permissible, and will result in a BigInteger value of 0,
     * whether _signum is -1, 0 or 1.  The `magnitude` array is assumed to
     * be unchanged for the duration of the constructor call.
     *
     * An `IndexOutOfBoundsException` is thrown if the length of the array
     * `magnitude` is non-zero and either `off` is negative,
     * `len` is negative, or `off+len` is greater than the length of
     * `magnitude`.
     *
     * @param signum _signum of the number (-1 for negative, 0 for zero, 1
     * for positive).
     * @param magnitude big-endian binary representation of the magnitude of
     * the number.
     * @param off the start offset of the binary representation.
     * @param len the number of bytes to use.
     * @throws NumberFormatException `_signum` is not one of the three
     * legal values (-1, 0, and 1), or `_signum` is 0 and
     * `magnitude` contains one or more non-zero bytes.
     * @throws IndexOutOfBoundsException if the provided array offset and
     * length would cause an index into the byte array to be
     * negative or greater than or equal to the array length.
     * @since 9
     */
    constructor(signum: Int, magnitude: ByteArray, off: Int = 0, len: Int = magnitude.size) {
        if (signum < -1 || signum > 1) {
            throw NumberFormatException("Invalid _signum value")
        } else if (off < 0 ||
            len < 0 ||
            len > 0 &&
            (off >= magnitude.size || len > magnitude.size - off)
        ) { // 0 <= off < magnitude.length
            throw IndexOutOfBoundsException()
        }

        // stripLeadingZeroBytes() returns a zero length array if len == 0
        this._mag = stripLeadingZeroBytes(magnitude, off, len)

        if (this._mag.isEmpty()) {
            this._signum = 0
        } else {
            if (signum == 0) {
                throw NumberFormatException("_signum-magnitude mismatch")
            }
            this._signum = signum
        }
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /**
     * A constructor for internal use that translates the sign-magnitude
     * representation of a BigInteger into a BigInteger. It checks the
     * arguments and copies the magnitude so this constructor would be
     * safe for external use.  The `magnitude` array is assumed to be
     * unchanged for the duration of the constructor call.
     */
    private constructor(signum: Int, magnitude: IntArray) {
        this._mag = stripLeadingZeroInts(magnitude)

        if (signum < -1 || signum > 1) {
            throw NumberFormatException("Invalid _signum value")
        }

        if (this._mag.isEmpty()) {
            this._signum = 0
        } else {
            if (signum == 0) {
                throw NumberFormatException("_signum-magnitude mismatch")
            }
            this._signum = signum
        }
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /**
     * Translates the String representation of a BigInteger in the
     * specified radix into a BigInteger.  The String representation
     * consists of an optional subtract or plus sign followed by a
     * sequence of one or more digits in the specified radix.  The
     * character-to-digit mapping is provided by `Character.digit`.  The String may not contain any extraneous
     * characters (whitespace, for example).
     *
     * @param other String representation of BigInteger.
     * @param radix radix to be used in interpreting other.
     * @throws NumberFormatException other is not a valid representation
     * of a BigInteger in the specified radix, or `radix` is
     * outside the range from [CHAR_MIN_RADIX] to
     * [CHAR_MAX_RADIX], inclusive.
     * @see Char.toDigit
     */
    constructor(other: String, radix: Int = 10) {
        var cursor = 0
        val numDigits: Int
        val len = other.length

        if (radix < CHAR_MIN_RADIX || radix > CHAR_MAX_RADIX) {
            throw NumberFormatException("Radix out of range")
        }
        if (len == 0) {
            throw NumberFormatException("Zero length BigInteger")
        }

        // Check for at most one leading sign
        var sign = 1
        val index1 = other.lastIndexOf('-')
        val index2 = other.lastIndexOf('+')
        if (index1 >= 0) {
            if (index1 != 0 || index2 >= 0) {
                throw NumberFormatException("Illegal embedded sign character")
            }
            sign = -1
            cursor = 1
        } else if (index2 >= 0) {
            if (index2 != 0) {
                throw NumberFormatException("Illegal embedded sign character")
            }
            cursor = 1
        }
        if (cursor == len) {
            throw NumberFormatException("Zero length BigInteger")
        }

        // Skip leading zeros and compute number of digits in magnitude
        while (cursor < len && other[cursor].toDigit(radix) == 0) {
            cursor++
        }

        if (cursor == len) {
            _signum = 0
            _mag = ZERO._mag
            return
        }

        numDigits = len - cursor
        _signum = sign

        // Pre-allocate array of expected size. May be too large but can
        // never be too small. Typically exact.
        val numBits = (numDigits * bitsPerDigit[radix]).ushr(10) + 1
        if (numBits + 31 >= 1L shl 32) {
            reportOverflow()
        }
        val numWords = (numBits + 31).toInt().ushr(5)
        val magnitude = IntArray(numWords)

        // Process first (potentially short) digit group
        var firstGroupLen = numDigits % digitsPerInt[radix]
        if (firstGroupLen == 0) {
            firstGroupLen = digitsPerInt[radix]
        }
        var group = other.substring(cursor, cursor + firstGroupLen)
        cursor += firstGroupLen
        magnitude[numWords - 1] = group.toInt(radix)
        if (magnitude[numWords - 1] < 0) {
            throw NumberFormatException("Illegal digit")
        }

        // Process remaining digit groups
        val superRadix = intRadix[radix]
        var groupVal = 0
        while (cursor < len) {
            group = other.substring(cursor, cursor + digitsPerInt[radix])
            cursor += digitsPerInt[radix]
            groupVal = group.toInt(radix)
            if (groupVal < 0) {
                throw NumberFormatException("Illegal digit")
            }
            destructiveMulAdd(magnitude, superRadix, groupVal)
        }
        // Required for cases where the array was overallocated.
        _mag = trustedStripLeadingZeroInts(magnitude)
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /*
     * Constructs a new BigInteger using a char array with radix=10.
     * Sign is precalculated outside and not allowed in the val. The {@code val}
     * array is assumed to be unchanged for the duration of the constructor
     * call.
     */
    internal constructor(other: CharArray, sign: Int, len: Int) {
        var cursor = 0
        val numDigits: Int

        // Skip leading zeros and compute number of digits in magnitude
        while (cursor < len && other[cursor].toDigit(10) == 0) {
            cursor++
        }
        if (cursor == len) {
            _signum = 0
            _mag = ZERO._mag
            return
        }

        numDigits = len - cursor
        _signum = sign
        // Pre-allocate array of expected size
        val numWords: Int
        if (len < 10) {
            numWords = 1
        } else {
            val numBits = (numDigits * bitsPerDigit[10]).ushr(10) + 1
            if (numBits + 31 >= 1L shl 32) {
                reportOverflow()
            }
            numWords = (numBits + 31).toInt().ushr(5)
        }
        val magnitude = IntArray(numWords)

        // Process first (potentially short) digit group
        var firstGroupLen = numDigits % digitsPerInt[10]
        if (firstGroupLen == 0) {
            firstGroupLen = digitsPerInt[10]
        }
        magnitude[numWords - 1] = parseInt(other, cursor, cursor + firstGroupLen)
        cursor += firstGroupLen

        // Process remaining digit groups
        while (cursor < len) {
            val groupVal = parseInt(other, cursor, cursor + digitsPerInt[10])
            cursor += digitsPerInt[10]
            destructiveMulAdd(
                magnitude,
                intRadix[10],
                groupVal,
            )
        }
        _mag = trustedStripLeadingZeroInts(magnitude)
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    // Create an integer with the digits between the two indexes
    // Assumes start < end. The result may be negative, but it
    // is to be treated as an unsigned value.
    private fun parseInt(
        source: CharArray,
        start: Int,
        end: Int,
    ): Int {
        var result = source[start].toDigit(10)
        if (result == -1) {
            throw NumberFormatException(source.concatToString())
        }

        for (index in (start + 1) until end) {
            val nextVal = source[index].toDigit(10)
            if (nextVal == -1) {
                throw NumberFormatException(source.concatToString())
            }
            result = 10 * result + nextVal
        }

        return result
    }

    /**
     * Constructs a randomly generated BigInteger, uniformly distributed over
     * the range 0 to (2<sup>`numBits`</sup> - 1), inclusive.
     * The uniformity of the distribution assumes that a fair source of random
     * bits is provided in `rnd`.  Note that this constructor always
     * constructs a non-negative BigInteger.
     *
     * @param numBits maximum bitLength of the new BigInteger.
     * @param rnd source of randomness to be used in computing the new
     * BigInteger.
     * @throws IllegalArgumentException `numBits` is negative.
     * @see .bitLength
     */
    constructor(numBits: Int, rnd: Random) : this(1, randomBits(numBits, rnd)) {}

    /**
     * Constructs a randomly generated positive BigInteger that is probably
     * prime, with the specified bitLength.
     *
     * @apiNote It is recommended that the [probablePrime][CommonBigInteger.probablePrime]
     * method be used in preference to this constructor unless there
     * is a compelling need to specify a certainty.
     *
     * @param bitLength bitLength of the returned BigInteger.
     * @param certainty a measure of the uncertainty that the caller is
     * willing to tolerate.  The probability that the new BigInteger
     * represents a prime number will exceed
     * (1 - 1/2<sup>`certainty`</sup>).  The execution time of
     * this constructor is proportional to the value of this parameter.
     * @param rnd source of random bits used to select candidates to be
     * tested for primality.
     * @throws ArithmeticException `bitLength < 2` or `bitLength` is too large.
     * @see .bitLength
     */
    constructor(bitLength: Int, certainty: Int, rnd: Random) {
        val prime: CommonBigInteger

        if (bitLength < 2) {
            throw ArithmeticException("bitLength < 2")
        }
        prime =
            if (bitLength < SMALL_PRIME_THRESHOLD) {
                smallPrime(bitLength, certainty, rnd)
            } else {
                largePrime(bitLength, certainty, rnd)
            }
        _signum = 1
        _mag = prime._mag
    }

    /**
     * Returns the first integer greater than this `BigInteger` that
     * is probably prime.  The probability that the number returned by this
     * method is composite does not exceed 2<sup>-100</sup>. This method will
     * never skip over a prime when searching: if it returns `p`, there
     * is no prime `q` such that `this < q < p`.
     *
     * @return the first integer greater than this `BigInteger` that
     * is probably prime.
     * @throws ArithmeticException `this < 0` or `this` is too large.
     * @since 1.5
     */
    @Suppress("UNREACHABLE_CODE", "UNUSED_VARIABLE")
    override fun nextProbablePrime(): CommonBigInteger {
        if (this._signum < 0) {
            throw ArithmeticException("start < 0: $this")
        }

        // Handle trivial cases
        if (this._signum == 0 || this == ONE) {
            return TWO
        }

        var result = this.plus(ONE)

        // Fastpath for small numbers
        if (result.bitLength < SMALL_PRIME_THRESHOLD) {
            // Ensure an odd number
            if (!result.testBit(0)) {
                result = result.plus(ONE)
            }

            while (true) {
                // Do cheap "pre-test" if applicable
                if (result.bitLength > 6) {
                    val r = result.remainder(SMALL_PRIME_PRODUCT).toLong()
                    if (r % 3 == 0L ||
                        r % 5 == 0L ||
                        r % 7 == 0L ||
                        r % 11 == 0L ||
                        r % 13 == 0L ||
                        r % 17 == 0L ||
                        r % 19 == 0L ||
                        r % 23 == 0L ||
                        r % 29 == 0L ||
                        r % 31 == 0L ||
                        r % 37 == 0L ||
                        r % 41 == 0L
                    ) {
                        result = result.plus(TWO)
                        continue // Candidate is composite; try another
                    }
                }

                // All candidates of bitLength 2 and 3 are prime by this point
                if (result.bitLength < 4) {
                    return result
                }

                // The expensive test
                if (result.primeToCertainty(DEFAULT_PRIME_CERTAINTY, null)) {
                    return result
                }

                result = result.plus(TWO)
            }
        }

        // Start at previous even number
        if (result.testBit(0)) {
            result = result.minus(ONE)
        }

        // Looking for the next large prime
        val searchLen = getPrimeSearchLen(result.bitLength)

        while (true) {
            val searchSieve = BitSieve(result, searchLen)
            val candidate =
                searchSieve.retrieve(
                    result,
                    DEFAULT_PRIME_CERTAINTY,
                    null!!,
                )
            if (candidate != null) {
                return candidate
            }
            result = result.plus(of((2 * searchLen).toLong()))
        }
    }

    /**
     * Returns `true` if this BigInteger is probably prime,
     * `false` if it's definitely composite.
     *
     * This method assumes bitLength > 2.
     *
     * @param certainty a measure of the uncertainty that the caller is
     * willing to tolerate: if the call returns `true`
     * the probability that this BigInteger is prime exceeds
     * `(1 - 1/2<sup>certainty</sup>)`.  The execution time of
     * this method is proportional to the value of this parameter.
     * @return `true` if this BigInteger is probably prime,
     * `false` if it's definitely composite.
     */
    internal fun primeToCertainty(
        certainty: Int,
        random: Random?,
    ): Boolean {
        var rounds = 0
        val n = (max(certainty, Int.MAX_VALUE - 1) + 1) / 2

        // The relationship between the certainty and the number of rounds
        // we perform is given in the draft standard ANSI X9.80, "PRIME
        // NUMBER GENERATION, PRIMALITY TESTING, AND PRIMALITY CERTIFICATES".
        val sizeInBits = this.bitLength
        if (sizeInBits < 100) {
            rounds = 50
            rounds = if (n < rounds) n else rounds
            return passesMillerRabin(rounds, random)
        }

        if (sizeInBits < 256) {
            rounds = 27
        } else if (sizeInBits < 512) {
            rounds = 15
        } else if (sizeInBits < 768) {
            rounds = 8
        } else if (sizeInBits < 1024) {
            rounds = 4
        } else {
            rounds = 2
        }
        rounds = if (n < rounds) n else rounds

        return passesMillerRabin(rounds, random) && passesLucasLehmer()
    }

    /**
     * Returns true iff this BigInteger is a Lucas-Lehmer probable prime.
     *
     * The following assumptions are made:
     * This BigInteger is a positive, odd number.
     */
    private fun passesLucasLehmer(): Boolean {
        val thisPlusOne = this.plus(ONE)

        // Step 1
        var d = 5
        while (jacobiSymbol(d, this) != -1) {
            // 5, -7, 9, -11, ...
            d = if (d < 0) d.absoluteValue + 2 else -(d + 2)
        }

        // Step 2
        val u = lucasLehmerSequence(d, thisPlusOne, this)

        // Step 3
        return u.rem(this) == ZERO
    }

    /**
     * Returns true iff this BigInteger passes the specified number of
     * Miller-Rabin tests. This test is taken from the DSA spec (NIST FIPS
     * 186-2).
     *
     * The following assumptions are made:
     * This BigInteger is a positive, odd number greater than 2.
     * iterations<=50.
     */
    private fun passesMillerRabin(
        iterations: Int,
        rnd: Random?,
    ): Boolean {
        var rnd = rnd
        // Find a and m such that m is odd and this == 1 + 2**a * m
        val thisMinusOne = this.minus(ONE)
        var m = thisMinusOne
        val a = m.lowestSetBit
        m = m.shr(a)

        if (rnd == null) {
            rnd = Random.Default
        }
        // Do the tests
        for (i in 0 until iterations) {
            // Generate a uniform random on (1, this)
            var b: CommonBigInteger
            do {
                b = CommonBigInteger(this.bitLength, rnd)
            } while (b.compareTo(ONE) <= 0 || b.compareTo(this) >= 0)

            var j = 0
            var z = b.modPow(m, this)
            while (!(j == 0 && z == ONE || z == thisMinusOne)) {
                if (j > 0 && z == ONE || ++j == a) {
                    return false
                }
                z = z.modPow(TWO, this)
            }
        }
        return true
    }

    /**
     * This internal constructor differs from its public cousin
     * with the arguments reversed in two ways: it assumes that its
     * arguments are correct, and it doesn't copy the magnitude array.
     */
    internal constructor(magnitude: IntArray, signum: Int) {
        this._signum = if (magnitude.isEmpty()) 0 else signum
        this._mag = magnitude
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /**
     * This private constructor is for internal use and assumes that its
     * arguments are correct.  The `magnitude` array is assumed to be
     * unchanged for the duration of the constructor call.
     */
    private constructor(magnitude: ByteArray, signum: Int) {
        this._signum = if (magnitude.isEmpty()) 0 else signum
        this._mag = stripLeadingZeroBytes(magnitude, 0, magnitude.size)
        if (_mag.size >= MAX_MAG_LENGTH) {
            checkRange()
        }
    }

    /**
     * Throws an `ArithmeticException` if the `BigInteger` would be
     * out of the supported range.
     *
     * @throws ArithmeticException if `this` exceeds the supported range.
     */
    private fun checkRange() {
        if (_mag.size > MAX_MAG_LENGTH || _mag.size == MAX_MAG_LENGTH && _mag[0] < 0) {
            reportOverflow()
        }
    }

    /**
     * Constructs a BigInteger with the specified value, which may not be zero.
     */
    private constructor(other: Long) {
        var other = other
        if (other < 0) {
            other = -other
            _signum = -1
        } else {
            _signum = 1
        }

        val highWord = other.ushr(32).toInt()
        if (highWord == 0) {
            _mag = IntArray(1)
            _mag[0] = other.toInt()
        } else {
            _mag = IntArray(2)
            _mag[0] = highWord
            _mag[1] = other.toInt()
        }
    }

    // Arithmetic Operations

    /**
     * Returns a BigInteger whose value is `(this + val)`.
     *
     * @param other value to be added to this BigInteger.
     * @return `this + val`
     */

    override operator fun plus(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        if (other.signum == 0) {
            return this
        }
        if (_signum == 0) {
            return other
        }
        if (other._signum == _signum) {
            return CommonBigInteger(sum(_mag, other._mag), _signum)
        }

        val cmp = compareMagnitude(other)
        if (cmp == 0) {
            return ZERO
        }
        var resultMag =
            if (cmp > 0) {
                subtract(_mag, other._mag)
            } else {
                subtract(other._mag, _mag)
            }
        resultMag = trustedStripLeadingZeroInts(resultMag)

        return CommonBigInteger(resultMag, if (cmp == _signum) 1 else -1)
    }

    /**
     * Package private methods used by BigDecimal code to plus a BigInteger
     * with a long. Assumes val is not equal to INFLATED.
     */
    internal fun plusLong(other: Long): CommonBigInteger {
        if (other == 0L) {
            return this
        }
        if (_signum == 0) {
            return of(other)
        }
        if (other.sign == _signum) {
            return CommonBigInteger(sum(_mag, other.absoluteValue), _signum)
        }
        val cmp = compareMagnitude(other)
        if (cmp == 0) {
            return ZERO
        }
        var resultMag =
            if (cmp > 0) {
                subtract(
                    _mag,
                    other.absoluteValue,
                )
            } else {
                subtract(other.absoluteValue, _mag)
            }
        resultMag = trustedStripLeadingZeroInts(resultMag)
        return CommonBigInteger(resultMag, if (cmp == _signum) 1 else -1)
    }

    /**
     * Returns a BigInteger whose value is `(this - val)`.
     *
     * @param other value to be subtracted from this BigInteger.
     * @return `this - val`
     */

    override operator fun minus(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        if (other._signum == 0) {
            return this
        }
        if (_signum == 0) {
            return other.unaryMinus()
        }
        if (other._signum != _signum) {
            return CommonBigInteger(sum(_mag, other._mag), _signum)
        }

        val cmp = compareMagnitude(other)
        if (cmp == 0) {
            return ZERO
        }
        var resultMag =
            if (cmp > 0) {
                subtract(_mag, other._mag)
            } else {
                subtract(other._mag, _mag)
            }
        resultMag = trustedStripLeadingZeroInts(resultMag)
        return CommonBigInteger(resultMag, if (cmp == _signum) 1 else -1)
    }

    /**
     * Returns a BigInteger whose value is `(this * val)`.
     *
     * @implNote An implementation may offer better algorithmic
     * performance when `val == this`.
     *
     * @param other value to be multiplied by this BigInteger.
     * @return `this * val`
     */

    override operator fun times(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()

        if (other._signum == 0 || _signum == 0) {
            return ZERO
        }

        val xlen = _mag.size

        if (other === this && xlen > MULTIPLY_SQUARE_THRESHOLD) {
            return square()
        }

        val ylen = other._mag.size

        if (xlen < KARATSUBA_THRESHOLD || ylen < KARATSUBA_THRESHOLD) {
            val resultSign = if (_signum == other._signum) 1 else -1
            if (other._mag.size == 1) {
                return multiplyByInt(_mag, other._mag[0], resultSign)
            }
            if (_mag.size == 1) {
                return multiplyByInt(other._mag, _mag[0], resultSign)
            }
            var result =
                multiplyToLen(
                    _mag,
                    xlen,
                    other._mag,
                    ylen,
                    null,
                )
            result = trustedStripLeadingZeroInts(result)
            return CommonBigInteger(result, resultSign)
        } else {
            return if (xlen < TOOM_COOK_THRESHOLD && ylen < TOOM_COOK_THRESHOLD) {
                multiplyKaratsuba(this, other)
            } else {
                multiplyToomCook3(this, other)
            }
        }
    }

    /**
     * Package private methods used by BigDecimal code to timesLong a BigInteger
     * with a long. Assumes v is not equal to INFLATED.
     */
    internal fun timesLong(other: Long): CommonBigInteger {
        var v = other
        if (v == 0L || _signum == 0) {
            return ZERO
        }
        if (v == CommonBigDecimal.INFLATED) {
            return times(of(v))
        }
        val rsign = if (v > 0) _signum else -_signum
        if (v < 0) {
            v = -v
        }
        val dh = v.ushr(32) // higher order bits
        val dl = v and LONG_MASK // lower order bits

        val xlen = _mag.size
        val value = _mag
        var rmag = if (dh == 0L) IntArray(xlen + 1) else IntArray(xlen + 2)
        var carry: Long = 0
        var rstart = rmag.size - 1
        for (i in xlen - 1 downTo 0) {
            val product = (value[i].toLong() and LONG_MASK) * dl + carry
            rmag[rstart--] = product.toInt()
            carry = product.ushr(32)
        }
        rmag[rstart] = carry.toInt()
        if (dh != 0L) {
            carry = 0
            rstart = rmag.size - 2
            for (i in xlen - 1 downTo 0) {
                val product =
                    (value[i].toLong() and LONG_MASK) * dh +
                        (rmag[rstart].toLong() and LONG_MASK) + carry
                rmag[rstart--] = product.toInt()
                carry = product.ushr(32)
            }
            rmag[0] = carry.toInt()
        }
        if (carry == 0L) {
            rmag = rmag.copyOfRange(1, rmag.size)
        }
        return CommonBigInteger(rmag, rsign)
    }

    /**
     * Returns a slice of a BigInteger for use in Toom-Cook multiplication.
     *
     * @param lowerSize The size of the lower-order bit slices.
     * @param upperSize The size of the higher-order bit slices.
     * @param slice The index of which slice is requested, which must be a
     * number from 0 to size-1. Slice 0 is the highest-order bits, and slice
     * size-1 are the lowest-order bits. Slice 0 may be of different size than
     * the other slices.
     * @param fullsize The size of the larger integer array, used to align
     * slices to the appropriate position when multiplying different-sized
     * numbers.
     */
    private fun getToomSlice(
        lowerSize: Int,
        upperSize: Int,
        slice: Int,
        fullsize: Int,
    ): CommonBigInteger {
        var start: Int
        val end: Int
        val sliceSize: Int
        val len: Int
        val offset: Int

        len = _mag.size
        offset = fullsize - len

        if (slice == 0) {
            start = 0 - offset
            end = upperSize - 1 - offset
        } else {
            start = upperSize + (slice - 1) * lowerSize - offset
            end = start + lowerSize - 1
        }

        if (start < 0) {
            start = 0
        }
        if (end < 0) {
            return ZERO
        }

        sliceSize = end - start + 1

        if (sliceSize <= 0) {
            return ZERO
        }

        // While performing Toom-Cook, all slices are positive and
        // the sign is adjusted when the final number is composed.
        if (start == 0 && sliceSize >= len) {
            return this.absoluteValue
        }

        val intSlice = IntArray(sliceSize)
        arrayCopy(_mag, start, intSlice, 0, sliceSize)

        return CommonBigInteger(trustedStripLeadingZeroInts(intSlice), 1)
    }

    /**
     * Does an exact division (that is, the remainder is known to be zero)
     * of the specified number by 3.  This is used in Toom-Cook
     * multiplication.  This is an efficient algorithm that runs in linear
     * time.  If the argument is not exactly divisible by 3, results are
     * undefined.  Note that this is expected to be called with positive
     * arguments only.
     */
    private fun exactDivideBy3(): CommonBigInteger {
        val len = _mag.size
        var result = IntArray(len)
        var x: Long
        var w: Long
        var q: Long
        var borrow: Long
        borrow = 0L
        for (i in len - 1 downTo 0) {
            x = _mag[i].toLong() and LONG_MASK
            w = x - borrow
            if (borrow > x) { // Did we make the number go negative?
                borrow = 1L
            } else {
                borrow = 0L
            }

            // 0xAAAAAAAB is the modular inverse of 3 (rem 2^32).  Thus,
            // the effect of this is to div by 3 (rem 2^32).
            // This is much faster than division on most architectures.
            q = w * 0xAAAAAAABL and LONG_MASK
            result[i] = q.toInt()

            // Now check the borrow. The second check can of course be
            // eliminated if the first fails.
            if (q >= 0x55555556L) {
                borrow++
                if (q >= 0xAAAAAAABL) {
                    borrow++
                }
            }
        }
        result = trustedStripLeadingZeroInts(result)
        return CommonBigInteger(result, _signum)
    }

    /**
     * Returns a new BigInteger representing n lower ints of the number.
     * This is used by Karatsuba multiplication and Karatsuba squaring.
     */
    private fun getLower(n: Int): CommonBigInteger {
        val len = _mag.size

        if (len <= n) {
            return absoluteValue
        }

        val lowerInts = IntArray(n)
        arrayCopy(_mag, len - n, lowerInts, 0, n)

        return CommonBigInteger(trustedStripLeadingZeroInts(lowerInts), 1)
    }

    /**
     * Returns a new BigInteger representing _mag.length-n upper
     * ints of the number.  This is used by Karatsuba multiplication and
     * Karatsuba squaring.
     */
    private fun getUpper(n: Int): CommonBigInteger {
        val len = _mag.size

        if (len <= n) {
            return ZERO
        }

        val upperLen = len - n
        val upperInts = IntArray(upperLen)
        arrayCopy(_mag, 0, upperInts, 0, upperLen)

        return CommonBigInteger(trustedStripLeadingZeroInts(upperInts), 1)
    }

    // Squaring

    /**
     * Returns a BigInteger whose value is `(this<sup>2</sup>)`.
     *
     * @return `this<sup>2</sup>`
     */
    private fun square(): CommonBigInteger {
        if (_signum == 0) {
            return ZERO
        }
        val len = _mag.size

        if (len < KARATSUBA_SQUARE_THRESHOLD) {
            val z = squareToLen(_mag, len, null)
            return CommonBigInteger(trustedStripLeadingZeroInts(z), 1)
        } else {
            return if (len < TOOM_COOK_SQUARE_THRESHOLD) {
                squareKaratsuba()
            } else {
                squareToomCook3()
            }
        }
    }

    /**
     * Squares a BigInteger using the Karatsuba squaring algorithm.  It should
     * be used when both numbers are larger than a certain threshold (found
     * experimentally).  It is a recursive div-and-conquer algorithm that
     * has better asymptotic performance than the algorithm used in
     * squareToLen.
     */
    private fun squareKaratsuba(): CommonBigInteger {
        val half = (_mag.size + 1) / 2

        val xl = getLower(half)
        val xh = getUpper(half)

        val xhs = xh.square() // xhs = xh^2
        val xls = xl.square() // xls = xl^2

        // xh^2 << 64  +  (((xl+xh)^2 - (xh^2 + xl^2)) << 32) + xl^2
        return xhs
            .shl(half * 32)
            .plus(xl.plus(xh).square().minus(xhs.plus(xls)))
            .shl(half * 32)
            .plus(xls)
    }

    /**
     * Squares a BigInteger using the 3-way Toom-Cook squaring algorithm.  It
     * should be used when both numbers are larger than a certain threshold
     * (found experimentally).  It is a recursive div-and-conquer algorithm
     * that has better asymptotic performance than the algorithm used in
     * squareToLen or squareKaratsuba.
     */
    private fun squareToomCook3(): CommonBigInteger {
        val len = _mag.size

        // k is the size (in ints) of the lower-order slices.
        val k = (len + 2) / 3 // Equal to ceil(largest/3)

        // r is the size (in ints) of the highest-order slice.
        val r = len - 2 * k

        // Obtain slices of the numbers. a2 is the most significant
        // bits of the number, and a0 the least significant.
        val a0: CommonBigInteger
        val a1: CommonBigInteger
        val a2: CommonBigInteger
        a2 = getToomSlice(k, r, 0, len)
        a1 = getToomSlice(k, r, 1, len)
        a0 = getToomSlice(k, r, 2, len)
        val v0: CommonBigInteger
        val v1: CommonBigInteger
        val v2: CommonBigInteger
        val vm1: CommonBigInteger
        val vinf: CommonBigInteger
        var t1: CommonBigInteger
        var t2: CommonBigInteger
        var tm1: CommonBigInteger
        var da1: CommonBigInteger

        v0 = a0.square()
        da1 = a2.plus(a0)
        vm1 = da1.minus(a1).square()
        da1 = da1.plus(a1)
        v1 = da1.square()
        vinf = a2.square()
        v2 =
            da1
                .plus(a2)
                .shl(1)
                .minus(a0)
                .square()

        // The algorithm requires two divisions by 2 and one by 3.
        // All divisions are known to be exact, that is, they do not produce
        // remainders, and all results are positive.  The divisions by 2 are
        // implemented as right shifts which are relatively efficient, leaving
        // only a division by 3.
        // The division by 3 is done by an optimized algorithm for this case.
        t2 = v2.minus(vm1).exactDivideBy3()
        tm1 = v1.minus(vm1).shr(1)
        t1 = v1.minus(v0)
        t2 = t2.minus(t1).shr(1)
        t1 = t1.minus(tm1).minus(vinf)
        t2 = t2.minus(vinf.shl(1))
        tm1 = tm1.minus(t2)

        // Number of bits to shift left.
        val ss = k * 32

        return vinf
            .shl(ss)
            .plus(t2)
            .shl(ss)
            .plus(t1)
            .shl(ss)
            .plus(tm1)
            .shl(ss)
            .plus(v0)
    }

    // Division

    /**
     * Returns a BigInteger whose value is `(this / val)`.
     *
     * @param other value by which this BigInteger is to be divided.
     * @return `this / val`
     * @throws ArithmeticException if other is zero.
     */
    override operator fun div(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        val magSizeBelowThreshold = _mag.size < BURNIKEL_ZIEGLER_THRESHOLD
        val magSizeDiffIsOffset = _mag.size - other._mag.size < BURNIKEL_ZIEGLER_OFFSET
        return if (magSizeBelowThreshold || magSizeDiffIsOffset) {
            divideKnuth(other)
        } else {
            divideBurnikelZiegler(other)
        }
    }

    /**
     * Returns a BigInteger whose value is `(this / val)` using an O(n^2) algorithm from Knuth.
     *
     * @param other value by which this BigInteger is to be divided.
     * @return `this / val`
     * @throws ArithmeticException if other is zero.
     * @see MutableBigInteger.divideKnuth
     */
    private fun divideKnuth(other: CommonBigInteger): CommonBigInteger {
        val q = MutableBigInteger()
        val a = MutableBigInteger(this._mag)
        val b = MutableBigInteger(other._mag)

        a.divideKnuth(b, q, false)
        return q.toBigInteger(this._signum * other._signum)
    }

    /**
     * Returns an array of two BigIntegers containing `(this / val)`
     * followed by `(this % val)`.
     *
     * @param other value by which this BigInteger is to be divided, and the
     * remainder computed.
     * @return an array of two BigIntegers: the quotient `(this / val)`
     * is the initial element, and the remainder `(this % val)`
     * is the final element.
     * @throws ArithmeticException if other is zero.
     */
    override fun divideAndRemainder(other: BigInteger): Array<CommonBigInteger> {
        val other: CommonBigInteger = other.castTo()
        val magSizeBelowThreshold = _mag.size < BURNIKEL_ZIEGLER_THRESHOLD
        val magSizeDiffIsOffset = _mag.size - other._mag.size < BURNIKEL_ZIEGLER_OFFSET
        return if (magSizeBelowThreshold || magSizeDiffIsOffset) {
            divideAndRemainderKnuth(other)
        } else {
            divideAndRemainderBurnikelZiegler(other)
        }
    }

    /** Long division  */
    private fun divideAndRemainderKnuth(other: CommonBigInteger): Array<CommonBigInteger> {
//        val result = arrayOfNulls<BigInteger>(2)
        val q = MutableBigInteger()
        val a = MutableBigInteger(this._mag)
        val b = MutableBigInteger(other._mag)
        val r = a.divideKnuth(b, q)
        return arrayOf(
            q.toBigInteger(if (this._signum == other._signum) 1 else -1),
            r!!.toBigInteger(this._signum),
        )
//        result[0] = q.toBigInteger(if (this._signum == other._signum) 1 else -1)
//        result[1] = r!!.toBigInteger(this._signum)
//        return result
    }

    /**
     * Returns a BigInteger whose value is `(this % val)`.
     *
     * @param other value by which this BigInteger is to be divided, and the
     * remainder computed.
     * @return `this % val`
     * @throws ArithmeticException if other is zero.
     */
    override fun remainder(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        val magSizeBelowThreshold = _mag.size < BURNIKEL_ZIEGLER_THRESHOLD
        val magSizeDiffIsOffset = _mag.size - other._mag.size < BURNIKEL_ZIEGLER_OFFSET
        return if (magSizeBelowThreshold || magSizeDiffIsOffset) {
            remainderKnuth(other)
        } else {
            remainderBurnikelZiegler(other)
        }
    }

    /** Long division  */
    private fun remainderKnuth(other: CommonBigInteger): CommonBigInteger {
        val q = MutableBigInteger()
        val a = MutableBigInteger(this._mag)
        val b = MutableBigInteger(other._mag)

        return a.divideKnuth(b, q)!!.toBigInteger(this._signum)
    }

    /**
     * Calculates `this / val` using the Burnikel-Ziegler algorithm.
     * @param other the divisor
     * @return `this / val`
     */
    private fun divideBurnikelZiegler(other: CommonBigInteger): CommonBigInteger =
        divideAndRemainderBurnikelZiegler(other)[0]

    /**
     * Calculates `this % val` using the Burnikel-Ziegler algorithm.
     * @param other the divisor
     * @return `this % val`
     */
    private fun remainderBurnikelZiegler(other: CommonBigInteger): CommonBigInteger =
        divideAndRemainderBurnikelZiegler(other)[1]

    /**
     * Computes `this / val` and `this % val` using the
     * Burnikel-Ziegler algorithm.
     * @param other the divisor
     * @return an array containing the quotient and remainder
     */
    private fun divideAndRemainderBurnikelZiegler(other: CommonBigInteger): Array<CommonBigInteger> {
        val q = MutableBigInteger()
        val r =
            MutableBigInteger(this)
                .divideAndRemainderBurnikelZiegler(MutableBigInteger(other), q)
        val qBigInt = if (q.isZero) ZERO else q.toBigInteger(_signum * other._signum)
        val rBigInt = if (r.isZero) ZERO else r.toBigInteger(_signum)
        return arrayOf(qBigInt, rBigInt)
    }

    /**
     * Returns a BigInteger whose value is `(this<sup>exponent</sup>)`.
     * Note that `exponent` is an integer rather than a BigInteger.
     *
     * @param exponent exponent to which this BigInteger is to be raised.
     * @return `this<sup>exponent</sup>`
     * @throws ArithmeticException `exponent` is negative.  (This would
     * cause the operation to yield a non-integer value.)
     */
    override infix fun pow(exponent: Int): CommonBigInteger {
        if (exponent < 0) {
            throw ArithmeticException("Negative exponent")
        }
        if (_signum == 0) {
            return if (exponent == 0) ONE else this
        }

        var partToSquare = this.absoluteValue

        // Factor out powers of two from the base, as the exponentiation of
        // these can be done by left shifts only.
        // The remaining part can then be exponentiated faster.  The
        // powers of two will be multiplied back at the end.
        val powersOfTwo = partToSquare.lowestSetBit
        val bitsToShift = powersOfTwo.toLong() * exponent
        if (bitsToShift > Int.MAX_VALUE) {
            reportOverflow()
        }

        val remainingBits: Int

        // Factor the powers of two out quickly by shifting right, if needed.
        if (powersOfTwo > 0) {
            partToSquare = partToSquare.shr(powersOfTwo)
            remainingBits = partToSquare.bitLength
            if (remainingBits == 1) { // Nothing left but +/- 1?
                return if (_signum < 0 && exponent and 1 == 1) {
                    NEGATIVE_ONE.shl(powersOfTwo * exponent)
                } else {
                    ONE.shl(powersOfTwo * exponent)
                }
            }
        } else {
            remainingBits = partToSquare.bitLength
            if (remainingBits == 1) { // Nothing left but +/- 1?
                return if (_signum < 0 && exponent and 1 == 1) {
                    NEGATIVE_ONE
                } else {
                    ONE
                }
            }
        }

        // This is a quick way to approximate the size of the result,
        // similar to doing log2[n] * exponent.  This will give an upper bound
        // of how big the result can be, and which algorithm to use.
        val scaleFactor = remainingBits.toLong() * exponent

        // Use slightly different algorithms for small and large operands.
        // See if the result will safely fit into a long. (Largest 2^63-1)
        if (partToSquare._mag.size == 1 && scaleFactor <= 62) {
            // Small number algorithm.  Everything fits into a long.
            val newSign = if (_signum < 0 && exponent and 1 == 1) -1 else 1
            var result: Long = 1
            var baseToPow2 = partToSquare._mag[0].toLong() and LONG_MASK

            var workingExponent = exponent

            // Perform exponentiation using repeated squaring trick
            while (workingExponent != 0) {
                if (workingExponent and 1 == 1) {
                    result = result * baseToPow2
                }

                workingExponent = workingExponent ushr 1
                if (workingExponent != 0) {
                    baseToPow2 = baseToPow2 * baseToPow2
                }
            }

            // Multiply back the powers of two (quickly, by shifting left)
            return if (powersOfTwo > 0) {
                if (bitsToShift + scaleFactor <= 62) { // Fits in long?
                    of((result shl bitsToShift.toInt()) * newSign)
                } else {
                    of(result * newSign).shl(bitsToShift.toInt())
                }
            } else {
                of(result * newSign)
            }
        } else {
            // Large number algorithm.  This is basically identical to
            // the algorithm above, but calls timesLong() and square()
            // which may use more efficient algorithms for large numbers.
            var answer = ONE

            var workingExponent = exponent
            // Perform exponentiation using repeated squaring trick
            while (workingExponent != 0) {
                if (workingExponent and 1 == 1) {
                    answer = answer.times(partToSquare)
                }

                workingExponent = workingExponent ushr 1
                if (workingExponent != 0) {
                    partToSquare = partToSquare.square()
                }
            }
            // Multiply back the (exponentiated) powers of two (quickly,
            // by shifting left)
            if (powersOfTwo > 0) {
                answer = answer.shl(powersOfTwo * exponent)
            }

            return if (_signum < 0 && exponent and 1 == 1) {
                answer.unaryMinus()
            } else {
                answer
            }
        }
    }

    /**
     * Returns the integer square root of this BigInteger.  The integer square
     * root of the corresponding mathematical integer `n` is the largest
     * mathematical integer `s` such that `s*s <= n`.  It is equal
     * to the value of `floor(sqrt(n))`, where `sqrt(n)` denotes the
     * real square root of `n` treated as a real.  Note that the integer
     * square root will be less than the real square root if the latter is not
     * representable as an integral value.
     *
     * @return the integer square root of `this`
     * @throws ArithmeticException if `this` is negative.  (The square
     * root of a negative integer other is
     * `(i * sqrt(-val))` where *i* is the
     * *imaginary unit* and is equal to
     * `sqrt(-1)`.)
     * @since 9
     */
    override fun sqrt(): CommonBigInteger {
        if (this._signum < 0) {
            throw ArithmeticException("Negative BigInteger")
        }

        return MutableBigInteger(this._mag).sqrt().toBigInteger()
    }

    /**
     * Returns an array of two BigIntegers containing the integer square root
     * `s` of `this` and its remainder `this - s*s`,
     * respectively.
     *
     * @return an array of two BigIntegers with the integer square root at
     * offset 0 and the remainder at offset 1
     * @throws ArithmeticException if `this` is negative.  (The square
     * root of a negative integer other is
     * `(i * sqrt(-val))` where *i* is the
     * *imaginary unit* and is equal to
     * `sqrt(-1)`.)
     * @see .sqrt
     * @since 9
     */
    override fun sqrtAndRemainder(): Array<CommonBigInteger> {
        val s = sqrt()
        val r = this.minus(s.square())
        require(r >= ZERO)
        return arrayOf(s, r)
    }

    /**
     * Returns a BigInteger whose value is the greatest common divisor of
     * `absoluteValue(this)` and `absoluteValue(val)`.  Returns 0 if
     * `this == 0 && val == 0`.
     *
     * @param other value with which the GCD is to be computed.
     * @return `GCD(absoluteValue(this), absoluteValue(val))`
     */
    override fun gcd(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        if (other._signum == 0) {
            return this.absoluteValue
        } else if (this._signum == 0) {
            return other.absoluteValue
        }

        val a = MutableBigInteger(this)
        val b = MutableBigInteger(other)

        val result = a.hybridGCD(b)

        return result.toBigInteger(1)
    }

    /**
     * Returns a BigInteger whose value is the absolute value of this
     * BigInteger.
     *
     * @return `absoluteValue(this)`
     */
    override val absoluteValue: CommonBigInteger
        get() {
            return if (_signum >= 0) this else this.unaryMinus()
        }

    /**
     * Returns a BigInteger whose value is `(-this)`.
     *
     * @return `-this`
     */
    override operator fun unaryMinus(): CommonBigInteger = CommonBigInteger(this._mag, -this._signum)

    override operator fun unaryPlus(): CommonBigInteger = this

    /**
     * Returns the _signum function of this BigInteger.
     *
     * @return -1, 0 or 1 as the value of this BigInteger is negative, zero or
     * positive.
     */
    override val signum: Int
        get() {
            return this._signum
        }

    // Modular Arithmetic Operations

    /**
     * Returns a BigInteger whose value is `(this rem m`).  This method
     * differs from `remainder` in that it always returns a
     * *non-negative* BigInteger.
     *
     * @param modulus the modulus.
     * @return `this rem other`
     * @throws ArithmeticException when `other` is 0
     * @see .remainder
     */
    override operator fun rem(modulus: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = modulus.castTo()
        if (other._signum <= 0) {
            throw ArithmeticException("BigInteger: modulus not positive")
        }

        val result = this.remainder(other)
        return if (result._signum >= 0) result else result.plus(other)
    }

    /**
     * Returns a BigInteger whose value is
     * `(this<sup>exponent</sup> rem m)`.  (Unlike `pow`, this
     * method permits negative exponents.)
     *
     * @param exponent the exponent.
     * @param modulus the modulus.
     * @return `this<sup>exponent</sup> rem m`
     * @throws ArithmeticException `m`  0 or the exponent is
     * negative and this BigInteger is not *relatively
     * prime* to `m`.
     * @see .modInverse
     */
    override fun modPow(
        exponent: BigInteger,
        modulus: BigInteger,
    ): CommonBigInteger {
        var exponent: CommonBigInteger = exponent.castTo()
        val modulus: CommonBigInteger = modulus.castTo()
        if (modulus._signum <= 0) {
            throw ArithmeticException("BigInteger: modulus not positive")
        }

        // Trivial cases
        if (exponent._signum == 0) {
            return if (modulus == ONE) ZERO else ONE
        }

        if (this == ONE) {
            return if (modulus == ONE) ZERO else ONE
        }

        if (this == ZERO && exponent._signum >= 0) {
            return ZERO
        }

        if (this == negConst[1] && !exponent.testBit(0)) {
            return if (modulus == ONE) ZERO else ONE
        }

        val invertResult: Boolean = exponent._signum < 0
        if (invertResult) {
            exponent = exponent.unaryMinus()
        }

        val base =
            if (this._signum < 0 || this >= modulus) {
                this.rem(modulus)
            } else {
                this
            }
        val result: CommonBigInteger
        if (modulus.testBit(0)) { // odd modulus
            result = base.oddModPow(exponent, modulus)
        } else {
            /*
             * Even modulus.  Tear it into an "odd part" (m1) and power of two
             * (m2), exponentiate rem m1, manually exponentiate rem m2, and
             * use Chinese Remainder Theorem to combine results.
             */

            // Tear m apart into odd part (m1) and power of 2 (m2)
            val p = modulus.lowestSetBit // Max pow of 2 that divides m

            val m1 = modulus.shr(p) // m/2**p
            val m2 = ONE.shl(p) // 2**p

            // Calculate new base from m1
            val base2 =
                if (this._signum < 0 || this.compareTo(m1) >= 0) {
                    this.rem(m1)
                } else {
                    this
                }

            // Caculate (base ** exponent) rem m1.
            val a1 =
                if (m1 == ONE) {
                    ZERO
                } else {
                    base2.oddModPow(exponent, m1)
                }

            // Calculate (this ** exponent) rem m2
            val a2 = base.modPow2(exponent, p)

            // Combine results using Chinese Remainder Theorem
            val y1 = m2.modInverse(m1)
            val y2 = m1.modInverse(m2)

            if (modulus._mag.size < MAX_MAG_LENGTH / 2) {
                result =
                    a1
                        .times(m2)
                        .times(y1)
                        .plus(a2.times(m1).times(y2))
                        .rem(modulus)
            } else {
                val t1 = MutableBigInteger()
                MutableBigInteger(a1.times(m2)).multiply(MutableBigInteger(y1), t1)
                val t2 = MutableBigInteger()
                MutableBigInteger(a2.times(m1)).multiply(MutableBigInteger(y2), t2)
                t1.add(t2)
                val q = MutableBigInteger()
                result = t1.divide(MutableBigInteger(modulus), q)!!.toBigInteger()
            }
        }

        return if (invertResult) result.modInverse(modulus) else result
    }

    /**
     * Returns a BigInteger whose value is x to the power of y rem z.
     * Assumes: z is odd && x < z.
     */
    private fun oddModPow(
        y: CommonBigInteger,
        z: CommonBigInteger,
    ): CommonBigInteger {
        /*
         * The algorithm is adapted from Colin Plumb's C library.
         *
         * The window algorithm:
         * The idea is to keep a running product of b1 = n^(high-order bits of exp)
         * and then keep appending exponent bits to it.  The following patterns
         * apply to a 3-bit window (k = 3):
         * To append   0: square
         * To append   1: square, timesLong by n^1
         * To append  10: square, timesLong by n^1, square
         * To append  11: square, square, timesLong by n^3
         * To append 100: square, timesLong by n^1, square, square
         * To append 101: square, square, square, timesLong by n^5
         * To append 110: square, square, timesLong by n^3, square
         * To append 111: square, square, square, timesLong by n^7
         *
         * Since each pattern involves only one timesLong, the longer the pattern
         * the better, except that a 0 (no multiplies) can be appended directly.
         * We precompute a table of odd powers of n, up to 2^k, and can then
         * timesLong k bits of exponent at a time.  Actually, assuming random
         * exponents, there is on average one zero bit between needs to
         * timesLong (1/2 of the time there's none, 1/4 of the time there's 1,
         * 1/8 of the time, there's 2, 1/32 of the time, there's 3, etc.), so
         * you have to do one timesLong per k+1 bits of exponent.
         *
         * The loop walks down the exponent, squaring the result buffer as
         * it goes.  There is a wbits+1 bit lookahead buffer, buf, that is
         * filled with the upcoming exponent bits.  (What is read after the
         * end of the exponent is unimportant, but it is filled with zero here.)
         * When the most-significant bit of this buffer becomes set, i.e.
         * (buf & tblmask) != 0, we have to decide what pattern to timesLong
         * by, and when to do it.  We decide, remember to do it in future
         * after a suitable number of squarings have passed (e.g. a pattern
         * of "100" in the buffer requires that we timesLong by n^1 immediately;
         * a pattern of "110" calls for multiplying by n^3 after one more
         * squaring), clear the buffer, and continue.
         *
         * When we start, there is one more optimization: the result buffer
         * is implcitly one, so squaring it or multiplying by it can be
         * optimized away.  Further, if we start with a pattern like "100"
         * in the lookahead window, rather than placing n into the buffer
         * and then starting to square it, we have already computed n^2
         * to compute the odd-powers table, so we can place that into
         * the buffer and save a squaring.
         *
         * This means that if you have a k-bit window, to compute n^z,
         * where z is the high k bits of the exponent, 1/2 of the time
         * it requires no squarings.  1/4 of the time, it requires 1
         * squaring, ... 1/2^(k-1) of the time, it reqires k-2 squarings.
         * And the remaining 1/2^(k-1) of the time, the top k bits are a
         * 1 followed by k-1 0 bits, so it again only requires k-2
         * squarings, not k-1.  The average of these is 1.  Add that
         * to the one squaring we have to do to compute the table,
         * and you'll see that a k-bit window saves k-2 squarings
         * as well as reducing the multiplies.  (It actually doesn't
         * hurt in the case k = 1, either.)
         */
        if (y == ONE) {
            return this
        }

        // Special case for base of zero
        if (_signum == 0) {
            return ZERO
        }

        val base = _mag.cloneArray()
        val exp = y._mag
        var mod = z._mag
        var modLen = mod.size

        // Make modLen even. It is conventional to use a cryptographic
        // modulus that is 512, 768, 1024, or 2048 bits, so this code
        // will not normally be executed. However, it is necessary for
        // the correct functioning of the HotSpot intrinsics.
        if (modLen and 1 != 0) {
            val x = IntArray(modLen + 1)
            arrayCopy(mod, 0, x, 1, modLen)
            mod = x
            modLen++
        }

        // Select an appropriate window size
        var wbits = 0
        var ebits = bitLength(exp, exp.size)
        // if exponent is 65537 (0x10001), use minimum window size
        if (ebits != 17 || exp[0] != 65537) {
            while (ebits > bnExpModThreshTable[wbits]) {
                wbits++
            }
        }

        // Calculate appropriate table size
        val tblmask = 1 shl wbits

        // Allocate table for precomputed odd powers of base in Montgomery form
        val table = (0 until tblmask).map { IntArray(modLen) }.toTypedArray()

        // Compute the modular inverse of the least significant 64-bit
        // digit of the modulus
        val n0 = (mod[modLen - 1].toLong() and LONG_MASK) + (mod[modLen - 2].toLong() and LONG_MASK shl 32)
        val inv = -MutableBigInteger.inverseMod64(n0)

        // Convert base to Montgomery form
        var a = leftShift(base, base.size, modLen shl 5)

        val q = MutableBigInteger()
        val a2 = MutableBigInteger(a)
        val b2 = MutableBigInteger(mod)
        b2.normalize() // MutableBigInteger.div() assumes that its
        // divisor is in normal form.

        val r = a2.divide(b2, q)
        table[0] = r!!.toIntArray()

        // Pad table[0] with leading zeros so its length is at least modLen
        if (table[0].size < modLen) {
            val offset = modLen - table[0].size
            val t2 = IntArray(modLen)
            arrayCopy(table[0], 0, t2, offset, table[0].size)
            table[0] = t2
        }

        // Set b to the square of the base
        var b = montgomerySquare(table[0], mod, modLen, inv, null)

        // Set t to high half of b
        var t = b.copyOf(modLen)

        // Fill in the table with odd powers of the base
        for (i in 1 until tblmask) {
            table[i] = montgomeryMultiply(t, table[i - 1], mod, modLen, inv, null)
        }

        // Pre load the window that slides over the exponent
        var bitpos = 1 shl (ebits - 1 and 32 - 1)

        var buf = 0
        var elen = exp.size
        var eIndex = 0
        for (i in 0..wbits) {
            buf = buf shl 1 or if (exp[eIndex] and bitpos != 0) 1 else 0
            bitpos = bitpos ushr 1
            if (bitpos == 0) {
                eIndex++
                bitpos = 1 shl 32 - 1
                elen--
            }
        }

        var multpos = ebits

        // The first iteration, which is hoisted out of the main loop
        ebits--
        var isone = true

        multpos = ebits - wbits
        while (buf and 1 == 0) {
            buf = buf ushr 1
            multpos++
        }

        var mult = table[buf.ushr(1)]

        buf = 0
        if (multpos == ebits) {
            isone = false
        }

        // The main loop
        while (true) {
            ebits--
            // Advance the window
            buf = buf shl 1

            if (elen != 0) {
                buf = buf or if (exp[eIndex] and bitpos != 0) 1 else 0
                bitpos = bitpos ushr 1
                if (bitpos == 0) {
                    eIndex++
                    bitpos = 1 shl 32 - 1
                    elen--
                }
            }

            // Examine the window for pending multiplies
            if (buf and tblmask != 0) {
                multpos = ebits - wbits
                while (buf and 1 == 0) {
                    buf = buf ushr 1
                    multpos++
                }
                mult = table[buf.ushr(1)]
                buf = 0
            }

            // Perform timesLong
            if (ebits == multpos) {
                if (isone) {
                    b = mult.cloneArray()
                    isone = false
                } else {
                    t = b
                    a = montgomeryMultiply(t, mult, mod, modLen, inv, a)
                    t = a
                    a = b
                    b = t
                }
            }

            // Check if done
            if (ebits == 0) {
                break
            }

            // Square the input
            if (!isone) {
                t = b
                a = montgomerySquare(t, mod, modLen, inv, a)
                t = a
                a = b
                b = t
            }
        }

        // Convert result out of Montgomery form and return
        var t2 = IntArray(2 * modLen)
        arrayCopy(b, 0, t2, modLen, modLen)

        b = montReduce(t2, mod, modLen, inv.toInt())

        t2 = b.copyOf(modLen)

        return CommonBigInteger(1, t2)
    }

    /**
     * Returns a BigInteger whose value is (this ** exponent) rem (2**p)
     */
    private fun modPow2(
        exponent: CommonBigInteger,
        p: Int,
    ): CommonBigInteger {
        /*
         * Perform exponentiation using repeated squaring trick, chopping off
         * high order bits as indicated by modulus.
         */
        var result = ONE
        var baseToPow2 = this.mod2(p)
        var expOffset = 0

        var limit = exponent.bitLength

        if (this.testBit(0)) {
            limit = if (p - 1 < limit) p - 1 else limit
        }

        while (expOffset < limit) {
            if (exponent.testBit(expOffset)) {
                result = result.times(baseToPow2).mod2(p)
            }
            expOffset++
            if (expOffset < limit) {
                baseToPow2 = baseToPow2.square().mod2(p)
            }
        }

        return result
    }

    /**
     * Returns a BigInteger whose value is this rem(2**p).
     * Assumes that this `BigInteger >= 0` and `p > 0`.
     */
    private fun mod2(p: Int): CommonBigInteger {
        if (bitLength <= p) {
            return this
        }

        // Copy remaining ints of _mag
        val numInts = (p + 31).ushr(5)
        val mag = IntArray(numInts)
        arrayCopy(this._mag, this._mag.size - numInts, mag, 0, numInts)

        // Mask out any excess bits
        val excessBits = (numInts shl 5) - p
        mag[0] = mag[0] and ((1L shl 32 - excessBits) - 1).toInt()

        return if (mag[0] == 0) CommonBigInteger(1, mag) else CommonBigInteger(mag, 1)
    }

    /**
     * Returns a BigInteger whose value is `(this`<sup>-1</sup> `rem m)`.
     *
     * @param modulus the modulus.
     * @return `this`<sup>-1</sup> `rem m`.
     * @throws ArithmeticException `m`  0, or this BigInteger
     * has no multiplicative inverse rem m (that is, this BigInteger
     * is not *relatively prime* to m).
     */
    override fun modInverse(modulus: BigInteger): CommonBigInteger {
        val modulus: CommonBigInteger = modulus.castTo()
        if (modulus._signum != 1) {
            throw ArithmeticException("BigInteger: modulus not positive")
        }

        if (modulus == ONE) {
            return ZERO
        }

        // Calculate (this rem m)
        var modVal = this
        if (_signum < 0 || this.compareMagnitude(modulus) >= 0) {
            modVal = this.rem(modulus)
        }

        if (modVal == ONE) {
            return ONE
        }

        val a = MutableBigInteger(modVal)
        val b = MutableBigInteger(modulus)

        val result = a.mutableModInverse(b)
        return result!!.toBigInteger(1)
    }

    // Shift Operations

    /**
     * Returns a BigInteger whose value is `(this << n)`.
     * The shift distance, `n`, may be negative, in which case
     * this method performs a right shift.
     * (Computes `floor(this * 2<sup>n</sup>)`.)
     *
     * @param n shift distance, in bits.
     * @return `this << n`
     * @see .shr
     */
    override infix fun shl(n: Int): CommonBigInteger {
        if (_signum == 0) {
            return ZERO
        }
        return if (n > 0) {
            CommonBigInteger(shl(_mag, n), _signum)
        } else if (n == 0) {
            this
        } else {
            // Possible int overflow in (-n) is not a trouble,
            // because shiftRightImpl considers its argument unsigned
            shiftRightImpl(-n)
        }
    }

    /**
     * Returns a BigInteger whose value is `(this >> n)`.  Sign
     * extension is performed.  The shift distance, `n`, may be
     * negative, in which case this method performs a left shift.
     * (Computes `floor(this / 2<sup>n</sup>)`.)
     *
     * @param n shift distance, in bits.
     * @return `this >> n`
     * @see .shl
     */
    override infix fun shr(n: Int): CommonBigInteger {
        if (_signum == 0) {
            return ZERO
        }
        return if (n > 0) {
            shiftRightImpl(n)
        } else if (n == 0) {
            this
        } else {
            // Possible int overflow in {@code -n} is not a trouble,
            // because shl considers its argument unsigned
            CommonBigInteger(shl(_mag, -n), _signum)
        }
    }

    /**
     * Returns a BigInteger whose value is `(this >> n)`. The shift
     * distance, `n`, is considered unsigned.
     * (Computes `floor(this * 2<sup>-n</sup>)`.)
     *
     * @param n unsigned shift distance, in bits.
     * @return `this >> n`
     */
    private fun shiftRightImpl(n: Int): CommonBigInteger {
        val nInts = n.ushr(5)
        val nBits = n and 0x1f
        val magLen = _mag.size
        var newMag: IntArray? = null

        // Special case: entire contents shifted off the end
        if (nInts >= magLen) {
            return if (_signum >= 0) ZERO else (negConst[1] ?: throw IllegalStateException())
        }

        if (nBits == 0) {
            val newMagLen = magLen - nInts
            newMag = _mag.copyOf(newMagLen)
        } else {
            var i = 0
            val highBits = _mag[0].ushr(nBits)
            if (highBits != 0) {
                newMag = IntArray(magLen - nInts)
                newMag[i++] = highBits
            } else {
                newMag = IntArray(magLen - nInts - 1)
            }

            val nBits2 = 32 - nBits
            var j = 0
            while (j < magLen - nInts - 1) {
                newMag[i++] = _mag[j++] shl nBits2 or _mag[j].ushr(nBits)
            }
        }

        if (_signum < 0) {
            // Find out whether any one-bits were shifted off the end.
            var onesLost = false
            var i = magLen - 1
            val j = magLen - nInts
            while (i >= j && !onesLost) {
                onesLost = _mag[i] != 0
                i--
            }
            if (!onesLost && nBits != 0) {
                onesLost = _mag[magLen - nInts - 1] shl 32 - nBits != 0
            }

            if (onesLost) {
                newMag = javaIncrement(newMag)
            }
        }

        return CommonBigInteger(newMag, _signum)
    }

    private fun javaIncrement(other: IntArray): IntArray {
        var result = other
        var lastSum = 0
        var i = result.size - 1
        while (i >= 0 && lastSum == 0) {
            lastSum = (result[i]++)
            i--
        }
        if (lastSum == 0) {
            result = IntArray(result.size + 1)
            result[0] = 1
        }
        return result
    }

    // Bitwise Operations

    /**
     * Returns a BigInteger whose value is `(this & val)`.  (This
     * method returns a negative BigInteger if and only if this and val are
     * both negative.)
     *
     * @param other value to be AND'ed with this BigInteger.
     * @return `this & val`
     */
    override fun and(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        val result = IntArray(max(intLength, other.intLength))
        for (i in result.indices) {
            result[i] = getInt(result.size - i - 1) and other.getInt(result.size - i - 1)
        }

        return of(result)
    }

    /**
     * Returns a BigInteger whose value is `(this | val)`.  (This method
     * returns a negative BigInteger if and only if either this or val is
     * negative.)
     *
     * @param other value to be OR'ed with this BigInteger.
     * @return `this | val`
     */
    override fun or(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        val result = IntArray(max(intLength, other.intLength))
        for (i in result.indices) {
            result[i] = getInt(result.size - i - 1) or other.getInt(result.size - i - 1)
        }

        return of(result)
    }

    /**
     * Returns a BigInteger whose value is `(this ^ val)`.  (This method
     * returns a negative BigInteger if and only if exactly one of this and
     * val are negative.)
     *
     * @param other value to be XOR'ed with this BigInteger.
     * @return `this ^ val`
     */
    override fun xor(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        val result = IntArray(max(intLength, other.intLength))
        for (i in result.indices) {
            result[i] = getInt(result.size - i - 1) xor other.getInt(result.size - i - 1)
        }

        return of(result)
    }

    /**
     * Returns a BigInteger whose value is `(~this)`.  (This method
     * returns a negative value if and only if this BigInteger is
     * non-negative.)
     *
     * @return `~this`
     */
    override operator fun not(): CommonBigInteger {
        val result = IntArray(intLength)
        for (i in result.indices) {
            result[i] = getInt(result.size - i - 1).inv()
        }

        return of(result)
    }

    /**
     * Returns a BigInteger whose value is `(this & ~val)`.  This
     * method, which is equivalent to `and(val.not())`, is provided as
     * a convenience for masking operations.  (This method returns a negative
     * BigInteger if and only if `this` is negative and other is
     * positive.)
     *
     * @param other value to be complemented and AND'ed with this BigInteger.
     * @return `this & ~val`
     */
    override fun andNot(other: BigInteger): CommonBigInteger {
        val other: CommonBigInteger = other.castTo()
        val result = IntArray(max(intLength, other.intLength))
        for (i in result.indices) {
            result[i] = getInt(result.size - i - 1) and other.getInt(result.size - i - 1).inv()
        }

        return of(result)
    }

    // Single Bit Operations

    /**
     * Returns `true` if and only if the designated bit is set.
     * (Computes `((this & (1<<n)) != 0)`.)
     *
     * @param n index of bit to test.
     * @return `true` if and only if the designated bit is set.
     * @throws ArithmeticException `n` is negative.
     */
    override fun testBit(n: Int): Boolean {
        if (n < 0) {
            throw ArithmeticException("Negative bit address")
        }

        return getInt(n.ushr(5)) and (1 shl (n and 31)) != 0
    }

    override operator fun get(n: Int): Boolean = testBit(n)

    override operator fun set(
        n: Int,
        b: Boolean,
    ): CommonBigInteger = if (b) setBit(n) else clearBit(n)

    /**
     * Returns a BigInteger whose value is equivalent to this BigInteger
     * with the designated bit set.  (Computes `(this | (1<<n))`.)
     *
     * @param n index of bit to set.
     * @return `this | (1<<n)`
     * @throws ArithmeticException `n` is negative.
     */
    override fun setBit(n: Int): CommonBigInteger {
        if (n < 0) {
            throw ArithmeticException("Negative bit address")
        }

        val intNum = n.ushr(5)
        val result = IntArray(max(intLength, intNum + 2))

        for (i in result.indices) {
            result[result.size - i - 1] = getInt(i)
        }

        result[result.size - intNum - 1] = result[result.size - intNum - 1] or (1 shl (n and 31))

        return of(result)
    }

    /**
     * Returns a BigInteger whose value is equivalent to this BigInteger
     * with the designated bit cleared.
     * (Computes `(this & ~(1<<n))`.)
     *
     * @param n index of bit to clear.
     * @return `this & ~(1<<n)`
     * @throws ArithmeticException `n` is negative.
     */
    override fun clearBit(n: Int): CommonBigInteger {
        if (n < 0) {
            throw ArithmeticException("Negative bit address")
        }

        val intNum = n.ushr(5)
        val result = IntArray(max(intLength, (n + 1).ushr(5) + 1))

        for (i in result.indices) {
            result[result.size - i - 1] = getInt(i)
        }

        result[result.size - intNum - 1] = result[result.size - intNum - 1] and (1 shl (n and 31)).inv()

        return of(result)
    }

    /**
     * Returns a BigInteger whose value is equivalent to this BigInteger
     * with the designated bit flipped.
     * (Computes `(this ^ (1<<n))`.)
     *
     * @param n index of bit to flip.
     * @return `this ^ (1<<n)`
     * @throws ArithmeticException `n` is negative.
     */
    override fun flipBit(n: Int): CommonBigInteger {
        if (n < 0) {
            throw ArithmeticException("Negative bit address")
        }

        val intNum = n.ushr(5)
        val result = IntArray(max(intLength, intNum + 2))

        for (i in result.indices) {
            result[result.size - i - 1] = getInt(i)
        }

        result[result.size - intNum - 1] = result[result.size - intNum - 1] xor (1 shl (n and 31))

        return of(result)
    }

    // Miscellaneous Bit Operations

    /**
     * Returns the number of bits in the minimal two's-complement
     * representation of this BigInteger, *excluding* a sign bit.
     * For positive BigIntegers, this is equivalent to the number of bits in
     * the ordinary binary representation.  (Computes
     * `(ceil(log2(this < 0 ? -this : this+1)))`.)
     *
     * @return number of bits in the minimal two's-complement
     * representation of this BigInteger, *excluding* a sign bit.
     */
    override val bitLength: Int
        get() {
            var n = _bitLengthPlusOne - 1
            if (n == -1) { // bitLength not initialized yet
                val m = _mag
                val len = m.size
                if (len == 0) {
                    n = 0 // offset by one to initialize
                } else {
                    // Calculate the bit length of the magnitude
                    val magBitLength = (len - 1 shl 5) + bitLengthForInt(_mag[0])
                    if (_signum < 0) {
                        // Check if magnitude is a power of two
                        var pow2 = _mag[0].bitCount() == 1
                        var i = 1
                        while (i < len && pow2) {
                            pow2 = _mag[i] == 0
                            i++
                        }

                        n = if (pow2) magBitLength - 1 else magBitLength
                    } else {
                        n = magBitLength
                    }
                }
                _bitLengthPlusOne = n + 1
            }
            return n
        }

    /**
     * Returns the number of bits in the two's complement representation
     * of this BigInteger that differ from its sign bit.  This method is
     * useful when implementing bit-vector style sets atop BigIntegers.
     *
     * @return number of bits in the two's complement representation
     * of this BigInteger that differ from its sign bit.
     */
    override val bitCount: Int
        get() {
            var bc = _bitCountPlusOne - 1
            if (bc == -1) { // bitCount not initialized yet
                bc = 0 // offset by one to initialize
                // Count the bits in the magnitude
                for (i in _mag.indices) {
                    bc += _mag[i].bitCount()
                }
                if (_signum < 0) {
                    // Count the trailing zeros in the magnitude
                    var magTrailingZeroCount = 0
                    var j: Int
                    j = _mag.size - 1
                    while (_mag[j] == 0) {
                        magTrailingZeroCount += 32
                        j--
                    }
                    magTrailingZeroCount += _mag[j].numberOfTrailingZeros()
                    bc += magTrailingZeroCount - 1
                }
                _bitCountPlusOne = bc + 1
            }
            return bc
        }

    // Primality Testing

    /**
     * Returns `true` if this BigInteger is probably prime,
     * `false` if it's definitely composite.  If
     * `certainty` is  0, `true` is
     * returned.
     *
     * @param certainty a measure of the uncertainty that the caller is
     * willing to tolerate: if the call returns `true`
     * the probability that this BigInteger is prime exceeds
     * (1 - 1/2<sup>`certainty`</sup>).  The execution time of
     * this method is proportional to the value of this parameter.
     * @return `true` if this BigInteger is probably prime,
     * `false` if it's definitely composite.
     */
    override fun isProbablePrime(certainty: Int): Boolean {
        if (certainty <= 0) {
            return true
        }
        val w = this.absoluteValue
        if (w == TWO) {
            return true
        }
        return if (!w.testBit(0) || w == ONE) false else w.primeToCertainty(certainty, null)
    }

    // Comparison Operations

    /**
     * Compares this BigInteger with the specified BigInteger.  This
     * method is provided in preference to individual methods for each
     * of the six boolean comparison operators (&lt;, ==,
     * &gt;, &gt;=, !=, &lt;=).  The suggested
     * idiom for performing these comparisons is: `(x.compareTo(y)` &lt;*op*&gt; `0)`, where
     * &lt;*op*&gt; is one of the six comparison operators.
     *
     * @param other BigInteger to which this BigInteger is to be compared.
     * @return -1, 0 or 1 as this BigInteger is numerically less than, equal
     * to, or greater than other.
     */
    override fun compareTo(other: BigInteger): Int {
        val other: CommonBigInteger = other.castTo()
        if (_signum == other._signum) {
            when (_signum) {
                1 -> return compareMagnitude(other)
                -1 -> return other.compareMagnitude(this)
                else -> return 0
            }
        }
        return if (_signum > other._signum) 1 else -1
    }

    /**
     * Compares the magnitude array of this BigInteger with the specified
     * BigInteger's. This is the version of compareTo ignoring sign.
     *
     * @param other BigInteger whose magnitude array to be compared.
     * @return -1, 0 or 1 as this magnitude array is less than, equal to or
     * greater than the magnitude aray for the specified BigInteger's.
     */
    internal fun compareMagnitude(other: CommonBigInteger): Int {
        val m1 = _mag
        val len1 = m1.size
        val m2 = other._mag
        val len2 = m2.size
        if (len1 < len2) {
            return -1
        }
        if (len1 > len2) {
            return 1
        }
        for (i in 0 until len1) {
            val a = m1[i]
            val b = m2[i]
            if (a != b) {
                return if (a.toLong() and LONG_MASK < b.toLong() and LONG_MASK) -1 else 1
            }
        }
        return 0
    }

    /**
     * Version of compareMagnitude that compares magnitude with long value.
     * val can't be Long.MIN_VALUE.
     */
    private fun compareMagnitude(other: Long): Int {
        var other = other
        require(other != Long.MIN_VALUE)
        val m1 = _mag
        val len = m1.size
        if (len > 2) {
            return 1
        }
        if (other < 0) {
            other = -other
        }
        val highWord = other.ushr(32).toInt()
        if (highWord == 0) {
            if (len < 1) {
                return -1
            }
            if (len > 1) {
                return 1
            }
            val a = m1[0]
            val b = other.toInt()
            return if (a != b) {
                if (a.toLong() and LONG_MASK < b.toLong() and LONG_MASK) -1 else 1
            } else {
                0
            }
        } else {
            if (len < 2) {
                return -1
            }
            var a = m1[0]
            var b = highWord
            if (a != b) {
                return if (a.toLong() and LONG_MASK < b.toLong() and LONG_MASK) -1 else 1
            }
            a = m1[1]
            b = other.toInt()
            return if (a != b) {
                if (a.toLong() and LONG_MASK < b.toLong() and LONG_MASK) -1 else 1
            } else {
                0
            }
        }
    }

    /**
     * Compares this BigInteger with the specified Object for equality.
     *
     * @param other Object to which this BigInteger is to be compared.
     * @return `true` if and only if the specified Object is a
     * BigInteger whose value is numerically equal to this BigInteger.
     */
    override fun equals(other: Any?): Boolean {
        // This test is just an optimization, which may or may not help
        if (other === this) {
            return true
        }

        if (other !is CommonBigInteger) {
            return false
        }

        val xInt = other as CommonBigInteger?
        if (xInt!!._signum != _signum) {
            return false
        }

        val m = _mag
        val len = m.size
        val xm = xInt._mag
        if (len != xm.size) {
            return false
        }

        for (i in 0 until len) {
            if (xm[i] != m[i]) {
                return false
            }
        }

        return true
    }

    /**
     * Returns the minimum of this BigInteger and other.
     *
     * @param other value with which the minimum is to be computed.
     * @return the BigInteger whose value is the lesser of this BigInteger and
     * other.  If they are equal, either may be returned.
     */
    override fun min(other: BigInteger): BigInteger = if (this < other) this else other

    /**
     * Returns the maximum of this BigInteger and other.
     *
     * @param other val value with which the maximum is to be computed.
     * @return the BigInteger whose value is the greater of this and
     * other.  If they are equal, either may be returned.
     */
    override fun max(other: BigInteger): BigInteger = if (this > other) this else other

    // Hash Function

    /**
     * Returns the hash code for this BigInteger.
     *
     * @return hash code for this BigInteger.
     */
    override fun hashCode(): Int {
        var hashCode = 0

        for (i in _mag.indices) {
            hashCode = 31 * hashCode + (_mag[i].toLong() and LONG_MASK).toInt()
        }

        return hashCode * _signum
    }

    /**
     * Returns the String representation of this BigInteger in the
     * given radix.  If the radix is outside the range from [CHAR_MIN_RADIX] to [CHAR_MAX_RADIX] inclusive,
     * it will default to 10 (as is the case for
     * `Int.toString`).  The digit-to-character mapping
     * provided by `Character.forDigit` is used, and a subtract
     * sign is prepended if appropriate.  (This representation is
     * compatible with the String constructor.)
     *
     * @param radix  radix of the String representation.
     * @return String representation of this BigInteger in the given radix.
     * @see Int.toString
     */
    override fun toString(radix: Int): String {
        var radix = radix
        if (_signum == 0) {
            return "0"
        }
        if (radix < CHAR_MIN_RADIX || radix > CHAR_MAX_RADIX) {
            radix = 10
        }

        // If it's small enough, use smallToString.
        if (_mag.size <= SCHOENHAGE_BASE_CONVERSION_THRESHOLD) {
            return smallToString(radix)
        }

        // Otherwise use recursive toString, which requires positive arguments.
        // The results will be concatenated into this StringBuilder
        var sb = StringBuilder()
        if (_signum < 0) {
            toString(this.unaryMinus(), sb, radix, 0)
            sb = sb.insertChar(0, '-')
        } else {
            toString(this, sb, radix, 0)
        }

        return sb.toString()
    }

    /** This method is used to perform toString when arguments are small.  */
    private fun smallToString(radix: Int): String {
        if (_signum == 0) {
            return "0"
        }

        // Compute upper bound on number of digit groups and allocate space
        val maxNumDigitGroups = (4 * _mag.size + 6) / 7
        val digitGroup = arrayOfNulls<String>(maxNumDigitGroups)

        // Translate number to string, a digit group at a time
        var tmp = this.absoluteValue
        var numGroups = 0
        while (tmp._signum != 0) {
            val d = longRadix[radix]

            val q = MutableBigInteger()
            val a = MutableBigInteger(tmp._mag)
            val b = MutableBigInteger(d!!._mag)
            val r = a.divide(b, q)
            val q2 = q.toBigInteger(tmp._signum * d._signum)
            val r2 = r!!.toBigInteger(tmp._signum * d._signum)

            digitGroup[numGroups++] = r2.toLong().toString(radix)
            // Long.toString(r2.toLong(), radix)
            tmp = q2
        }

        // Put sign (if any) and first digit group into result buffer
        val buf = StringBuilder(numGroups * digitsPerLong[radix] + 1)
        if (_signum < 0) {
            buf.append('-')
        }
        buf.append(digitGroup[numGroups - 1])

        // Append remaining digit groups padded with leading zeros
        for (i in numGroups - 2 downTo 0) {
            // Prepend (any) leading zeros for this digit group
            val numLeadingZeros = digitsPerLong[radix] - digitGroup[i]!!.length
            if (numLeadingZeros != 0) {
                buf.append(zeros[numLeadingZeros])
            }
            buf.append(digitGroup[i])
        }
        return buf.toString()
    }

    /**
     * Returns the decimal String representation of this BigInteger.
     * The digit-to-character mapping provided by
     * `Character.forDigit` is used, and a subtract sign is
     * prepended if appropriate.  (This representation is compatible
     * with the string constructor, and
     * allows for String concatenation with Java's + operator.)
     *
     * @return decimal String representation of this BigInteger.
     */
    override fun toString(): String = toString(10)

    /**
     * Returns a byte array containing the two's-complement
     * representation of this BigInteger.  The byte array will be in
     * *big-endian* byte-order: the most significant byte is in
     * the zeroth element.  The array will contain the minimum number
     * of bytes required to represent this BigInteger, including at
     * least one sign bit, which is `(ceil((this.bitLength() +
     * 1)/8))`.  (This representation is compatible with the
     * byte array constructor.)
     *
     * @return a byte array containing the two's-complement representation of
     * this BigInteger.
     */
    override fun toByteArray(): ByteArray {
        val byteLen = bitLength / 8 + 1
        val byteArray = ByteArray(byteLen)

        var i = byteLen - 1
        var bytesCopied = 4
        var nextInt = 0
        var intIndex = 0
        while (i >= 0) {
            if (bytesCopied == 4) {
                nextInt = getInt(intIndex++)
                bytesCopied = 1
            } else {
                nextInt = nextInt ushr 8
                bytesCopied++
            }
            byteArray[i] = nextInt.toByte()
            i--
        }
        return byteArray
    }

    /**
     * Converts this BigInteger to an `int`.  This
     * conversion is analogous to a
     * *narrowing primitive conversion* from `long` to
     * `int` as defined in
     * <cite>The Java Language Specification</cite>:
     * if this BigInteger is too big to fit in an
     * `int`, only the low-order 32 bits are returned.
     * Note that this conversion can lose information about the
     * overall magnitude of the BigInteger value as well as return a
     * result with the opposite sign.
     *
     * @return this BigInteger converted to an `int`.
     * @see CommonBigInteger.toIntExact
     * @jls 5.1.3 Narrowing Primitive Conversion
     */
    override fun toInt(): Int {
        var result = 0
        result = getInt(0)
        return result
    }

    /**
     * Converts this BigInteger to a `long`.  This
     * conversion is analogous to a
     * *narrowing primitive conversion* from `long` to
     * `int` as defined in
     * <cite>The Java Language Specification</cite>:
     * if this BigInteger is too big to fit in a
     * `long`, only the low-order 64 bits are returned.
     * Note that this conversion can lose information about the
     * overall magnitude of the BigInteger value as well as return a
     * result with the opposite sign.
     *
     * @return this BigInteger converted to a `long`.
     * @see .toLongExact
     * @jls 5.1.3 Narrowing Primitive Conversion
     */
    override fun toLong(): Long {
        var result: Long = 0

        for (i in 1 downTo 0) {
            result = (result shl 32) + (getInt(i).toLong() and LONG_MASK)
        }
        return result
    }

    override fun toByte(): Byte = toInt().toByte()

    override fun toChar(): Char = toInt().toChar()

    override fun toShort(): Short = toInt().toShort()

    override fun toFloat(): Float = toInt().toFloat()

    override fun toDouble(): Double = toLong().toDouble()

    /**
     * Returns the length of the two's complement representation in ints,
     * including space for at least one sign bit.
     */
    private val intLength: Int
        get() {
            return bitLength.ushr(5) + 1
        }

    // Returns sign bit
    private val signBit: Int
        get() {
            return if (_signum < 0) 1 else 0
        }

    // Returns an int of sign bits
    private val signInt: Int
        get() {
            return if (_signum < 0) -1 else 0
        }

    /**
     * Returns the specified int of the little-endian two's complement
     * representation (int 0 is the least significant).  The int number can
     * be arbitrarily high (values are logically preceded by infinitely many
     * sign ints).
     */
    private fun getInt(n: Int): Int {
        if (n < 0) {
            return 0
        }
        if (n >= _mag.size) {
            return signInt
        }

        val magInt = _mag[_mag.size - n - 1]

        return if (_signum >= 0) {
            magInt
        } else {
            if (n <= firstNonzeroIntNum()) -magInt else magInt.inv()
        }
    }

    /**
     * Returns the index of the int that contains the first nonzero int in the
     * little-endian binary representation of the magnitude (int 0 is the
     * least significant). If the magnitude is zero, return value is undefined.
     *
     *
     * Note: never used for a BigInteger with a magnitude of zero.
     * @see .getInt
     */
    private fun firstNonzeroIntNum(): Int {
        var fn = _firstNonzeroIntNumPlusTwo - 2
        if (fn == -2) { // firstNonzeroIntNum not initialized yet
            // Search for the first nonzero int
            var i: Int
            val mlen = _mag.size
            i = mlen - 1
            while (i >= 0 && _mag[i] == 0) {
                i--
            }
            fn = mlen - i - 1
            _firstNonzeroIntNumPlusTwo = fn + 2 // offset by two to initialize
        }
        return fn
    }

    /**
     * Returns the _mag array as an array of bytes.
     */
    private fun magSerializedForm(): ByteArray {
        val len = _mag.size

        val bitLen = if (len == 0) 0 else (len - 1 shl 5) + bitLengthForInt(_mag[0])
        val byteLen = (bitLen + 7).ushr(3)
        val result = ByteArray(byteLen)

        var i = byteLen - 1
        var bytesCopied = 4
        var intIndex = len - 1
        var nextInt = 0
        while (i >= 0) {
            if (bytesCopied == 4) {
                nextInt = _mag[intIndex--]
                bytesCopied = 1
            } else {
                nextInt = nextInt ushr 8
                bytesCopied++
            }
            result[i] = nextInt.toByte()
            i--
        }
        return result
    }

    /**
     * Converts this `BigInteger` to a `long`, checking
     * for lost information.  If the value of this `BigInteger`
     * is out of the range of the `long` type, then an
     * `ArithmeticException` is thrown.
     *
     * @return this `BigInteger` converted to a `long`.
     * @throws ArithmeticException if the value of `this` will
     * not exactly fit in a `long`.
     * @see CommonBigInteger.toLong
     *
     * @since 1.8
     */
    override fun toLongExact(): Long =
        if (_mag.size <= 2 && bitLength <= 63) {
            toLong()
        } else {
            throw ArithmeticException("BigInteger out of long range")
        }

    /**
     * Converts this `BigInteger` to an `int`, checking
     * for lost information.  If the value of this `BigInteger`
     * is out of the range of the `int` type, then an
     * `ArithmeticException` is thrown.
     *
     * @return this `BigInteger` converted to an `int`.
     * @throws ArithmeticException if the value of `this` will
     * not exactly fit in an `int`.
     * @see CommonBigInteger.toInt
     *
     * @since 1.8
     */
    override fun toIntExact(): Int =
        if (_mag.size <= 1 && bitLength <= 31) {
            toInt()
        } else {
            throw ArithmeticException("BigInteger out of int range")
        }

    /**
     * Converts this `BigInteger` to a `short`, checking
     * for lost information.  If the value of this `BigInteger`
     * is out of the range of the `short` type, then an
     * `ArithmeticException` is thrown.
     *
     * @return this `BigInteger` converted to a `short`.
     * @throws ArithmeticException if the value of `this` will
     * not exactly fit in a `short`.
     * @see CommonBigInteger.toShort
     *
     * @since 1.8
     */
    override fun toShortExact(): Short {
        if (_mag.size <= 1 && bitLength <= 31) {
            val value = toInt()
            if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                return toShort()
            }
        }
        throw ArithmeticException("BigInteger out of short range")
    }

    /**
     * Converts this `BigInteger` to a `byte`, checking
     * for lost information.  If the value of this `BigInteger`
     * is out of the range of the `byte` type, then an
     * `ArithmeticException` is thrown.
     *
     * @return this `BigInteger` converted to a `byte`.
     * @throws ArithmeticException if the value of `this` will
     * not exactly fit in a `byte`.
     * @see CommonBigInteger.toByte
     *
     * @since 1.8
     */
    override fun toByteExact(): Byte {
        if (_mag.size <= 1 && bitLength <= 31) {
            val value = toInt()
            if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                return toByte()
            }
        }
        throw ArithmeticException("BigInteger out of byte range")
    }

    companion object {
        /**
         * This mask is used to obtain the value of an int as if it were unsigned.
         */
        const val LONG_MASK = 0xffffffffL

        /**
         * This constant limits `_mag.length` of BigIntegers to the supported
         * range.
         */
        private const val MAX_MAG_LENGTH = Int.MAX_VALUE / Int.SIZE_BITS + 1 // (1 << 26)

        /**
         * Bit lengths larger than this constant can cause overflow in searchLen
         * calculation and in BitSieve.singleSearch method.
         */
        private const val PRIME_SEARCH_BIT_LENGTH_LIMIT = 500000000

        /**
         * The threshold value for using Karatsuba multiplication.  If the number
         * of ints in both _mag arrays are greater than this number, then
         * Karatsuba multiplication will be used.   This value is found
         * experimentally to work well.
         */
        private const val KARATSUBA_THRESHOLD = 80

        /**
         * The threshold value for using 3-way Toom-Cook multiplication.
         * If the number of ints in each _mag array is greater than the
         * Karatsuba threshold, and the number of ints in at least one of
         * the _mag arrays is greater than this threshold, then Toom-Cook
         * multiplication will be used.
         */
        private const val TOOM_COOK_THRESHOLD = 240

        /**
         * The threshold value for using Karatsuba squaring.  If the number
         * of ints in the number are larger than this value,
         * Karatsuba squaring will be used.   This value is found
         * experimentally to work well.
         */
        private const val KARATSUBA_SQUARE_THRESHOLD = 128

        /**
         * The threshold value for using Toom-Cook squaring.  If the number
         * of ints in the number are larger than this value,
         * Toom-Cook squaring will be used.   This value is found
         * experimentally to work well.
         */
        private const val TOOM_COOK_SQUARE_THRESHOLD = 216

        /**
         * The threshold value for using Burnikel-Ziegler division.  If the number
         * of ints in the divisor are larger than this value, Burnikel-Ziegler
         * division may be used.  This value is found experimentally to work well.
         */
        internal const val BURNIKEL_ZIEGLER_THRESHOLD = 80

        /**
         * The offset value for using Burnikel-Ziegler division.  If the number
         * of ints in the divisor exceeds the Burnikel-Ziegler threshold, and the
         * number of ints in the dividend is greater than the number of ints in the
         * divisor plus this value, Burnikel-Ziegler division will be used.  This
         * value is found experimentally to work well.
         */
        internal const val BURNIKEL_ZIEGLER_OFFSET = 40

        /**
         * The threshold value for using Schoenhage recursive base conversion. If
         * the number of ints in the number are larger than this value,
         * the Schoenhage algorithm will be used.  In practice, it appears that the
         * Schoenhage routine is faster for any threshold down to 2, and is
         * relatively flat for thresholds between 2-25, so this choice may be
         * varied within this range for very small effect.
         */
        private const val SCHOENHAGE_BASE_CONVERSION_THRESHOLD = 20

        /**
         * The threshold value for using squaring code to perform multiplication
         * of a `BigInteger` instance by itself.  If the number of ints in
         * the number are larger than this value, `timesLong(this)` will
         * return `square()`.
         */
        private const val MULTIPLY_SQUARE_THRESHOLD = 20

        /**
         * The threshold for using an intrinsic version of
         * implMontgomeryXXX to perform Montgomery multiplication.  If the
         * number of ints in the number is more than this value we do not
         * use the intrinsic.
         */
        private const val MONTGOMERY_INTRINSIC_THRESHOLD = 512

        // bitsPerDigit in the given radix timesLong 1024
        // Rounded up to avoid underallocation.
        private val bitsPerDigit =
            longArrayOf(
                0,
                0,
                1024,
                1624,
                2048,
                2378,
                2648,
                2875,
                3072,
                3247,
                3402,
                3543,
                3672,
                3790,
                3899,
                4001,
                4096,
                4186,
                4271,
                4350,
                4426,
                4498,
                4567,
                4633,
                4696,
                4756,
                4814,
                4870,
                4923,
                4975,
                5025,
                5074,
                5120,
                5166,
                5210,
                5253,
                5295,
            )

        // Multiply x array timesLong word y in place, and plus word z
        private fun destructiveMulAdd(
            x: IntArray,
            y: Int,
            z: Int,
        ) {
            // Perform the multiplication word by word
            val ylong = y.toLong() and LONG_MASK
            val zlong = z.toLong() and LONG_MASK
            val len = x.size

            var product: Long = 0
            var carry: Long = 0
            for (i in len - 1 downTo 0) {
                product = ylong * (x[i].toLong() and LONG_MASK) + carry
                x[i] = product.toInt()
                carry = product.ushr(32)
            }

            // Perform the addition
            var sum = (x[len - 1].toLong() and LONG_MASK) + zlong
            x[len - 1] = sum.toInt()
            carry = sum.ushr(32)
            for (i in len - 2 downTo 0) {
                sum = (x[i].toLong() and LONG_MASK) + carry
                x[i] = sum.toInt()
                carry = sum.ushr(32)
            }
        }

        private fun randomBits(
            numBits: Int,
            rnd: Random,
        ): ByteArray {
            if (numBits < 0) {
                throw IllegalArgumentException("numBits must be non-negative")
            }
            val numBytes = ((numBits.toLong() + 7) / 8).toInt() // avoid overflow
            val randomBits = ByteArray(numBytes)

            // Generate random bytes and mask out any excess bits
            if (numBytes > 0) {
                rnd.nextBytes(randomBits)
                val excessBits = 8 * numBytes - numBits
                randomBits[0] = randomBits[0] and ((1 shl 8 - excessBits) - 1).toByte()
            }
            return randomBits
        }

        // Minimum size in bits that the requested prime number has
        // before we use the large prime number generating algorithms.
        // The cutoff of 95 was chosen empirically for best performance.
        private const val SMALL_PRIME_THRESHOLD = 95

        // Certainty required to meet the spec of probablePrime
        private const val DEFAULT_PRIME_CERTAINTY = 100

        /**
         * Returns a positive BigInteger that is probably prime, with the
         * specified bitLength. The probability that a BigInteger returned
         * by this method is composite does not exceed 2<sup>-100</sup>.
         *
         * @param bitLength bitLength of the returned BigInteger.
         * @param rnd source of random bits used to select candidates to be
         * tested for primality.
         * @return a BigInteger of `bitLength` bits that is probably prime
         * @throws ArithmeticException `bitLength < 2` or `bitLength` is too large.
         * @see CommonBigInteger.bitLength
         * @since 1.4
         */
        @JvmStatic
        @JsName("probablePrime")
        fun probablePrime(
            bitLength: Int,
            rnd: Random,
        ): CommonBigInteger {
            if (bitLength < 2) {
                throw ArithmeticException("bitLength < 2")
            }

            return if (bitLength < SMALL_PRIME_THRESHOLD) {
                smallPrime(
                    bitLength,
                    DEFAULT_PRIME_CERTAINTY,
                    rnd,
                )
            } else {
                largePrime(
                    bitLength,
                    DEFAULT_PRIME_CERTAINTY,
                    rnd,
                )
            }
        }

        /**
         * Find a random number of the specified bitLength that is probably prime.
         * This method is used for smaller primes, its performance degrades on
         * larger bitlengths.
         *
         * This method assumes bitLength > 1.
         */
        private fun smallPrime(
            bitLength: Int,
            certainty: Int,
            rnd: Random,
        ): CommonBigInteger {
            val magLen = (bitLength + 31).ushr(5)
            val temp = IntArray(magLen)
            val highBit = 1 shl (bitLength + 31 and 0x1f) // High bit of high int
            val highMask = (highBit shl 1) - 1 // Bits to keep in high int

            while (true) {
                // Construct a candidate
                for (i in 0 until magLen) {
                    temp[i] = rnd.nextInt()
                }
                temp[0] = temp[0] and highMask or highBit // Ensure exact length
                if (bitLength > 2) {
                    temp[magLen - 1] = temp[magLen - 1] or 1 // Make odd if bitlen > 2
                }

                val p = CommonBigInteger(temp, 1)

                // Do cheap "pre-test" if applicable
                if (bitLength > 6) {
                    val r = p.remainder(SMALL_PRIME_PRODUCT).toLong()
                    if (r % 3 == 0L ||
                        r % 5 == 0L ||
                        r % 7 == 0L ||
                        r % 11 == 0L ||
                        r % 13 == 0L ||
                        r % 17 == 0L ||
                        r % 19 == 0L ||
                        r % 23 == 0L ||
                        r % 29 == 0L ||
                        r % 31 == 0L ||
                        r % 37 == 0L ||
                        r % 41 == 0L
                    ) {
                        continue // Candidate is composite; try another
                    }
                }

                // All candidates of bitLength 2 and 3 are prime by this point
                if (bitLength < 4) {
                    return p
                }

                // Do expensive test if we survive pre-test (or it's inapplicable)
                if (p.primeToCertainty(certainty, rnd)) {
                    return p
                }
            }
        }

        private val SMALL_PRIME_PRODUCT = of(3L * 5 * 7 * 11 * 13 * 17 * 19 * 23 * 29 * 31 * 37 * 41)

        /**
         * Find a random number of the specified bitLength that is probably prime.
         * This method is more appropriate for larger bitlengths since it uses
         * a sieve to eliminate most composites before using a more expensive
         * test.
         */
        private fun largePrime(
            bitLength: Int,
            certainty: Int,
            rnd: Random,
        ): CommonBigInteger {
            var p: CommonBigInteger
            p = CommonBigInteger(bitLength, rnd).setBit(bitLength - 1)
            p._mag[p._mag.size - 1] = p._mag[p._mag.size - 1] and -0x2

            // Use a sieve length likely to contain the next prime number
            val searchLen = getPrimeSearchLen(bitLength)
            var searchSieve = BitSieve(p, searchLen)
            var candidate = searchSieve.retrieve(p, certainty, rnd)

            while (candidate == null || candidate.bitLength != bitLength) {
                p = p.plus(of((2 * searchLen).toLong()))
                if (p.bitLength != bitLength) {
                    p = CommonBigInteger(bitLength, rnd).setBit(bitLength - 1)
                }
                p._mag[p._mag.size - 1] = p._mag[p._mag.size - 1] and -0x2
                searchSieve = BitSieve(p, searchLen)
                candidate = searchSieve.retrieve(p, certainty, rnd)
            }
            return candidate
        }

        private fun getPrimeSearchLen(bitLength: Int): Int {
            if (bitLength > PRIME_SEARCH_BIT_LENGTH_LIMIT + 1) {
                throw ArithmeticException("Prime search implementation restriction on bitLength")
            }
            return bitLength / 20 * 64
        }

        /**
         * Computes Jacobi(p,n).
         * Assumes n positive, odd, n>=3.
         */
        private fun jacobiSymbol(
            p: Int,
            n: CommonBigInteger,
        ): Int {
            var p = p
            if (p == 0) {
                return 0
            }

            // Algorithm and comments adapted from Colin Plumb's C library.
            var j = 1
            var u = n._mag[n._mag.size - 1]

            // Make p positive
            if (p < 0) {
                p = -p
                val n8 = u and 7
                if (n8 == 3 || n8 == 7) {
                    j = -j // 3 (011) or 7 (111) rem 8
                }
            }

            // Get rid of factors of 2 in p
            while (p and 3 == 0) {
                p = p shr 2
            }
            if (p and 1 == 0) {
                p = p shr 1
                if (u xor (u shr 1) and 2 != 0) {
                    j = -j // 3 (011) or 5 (101) rem 8
                }
            }
            if (p == 1) {
                return j
            }
            // Then, apply quadratic reciprocity
            if (p and u and 2 != 0) {
                // p = u = 3 (rem 4)?
                j = -j
            }
            // And reduce u rem p
            u = n.rem(of(p.toLong())).toInt()

            // Now compute Jacobi(u,p), u < p
            while (u != 0) {
                while (u and 3 == 0) {
                    u = u shr 2
                }
                if (u and 1 == 0) {
                    u = u shr 1
                    if (p xor (p shr 1) and 2 != 0) {
                        j = -j // 3 (011) or 5 (101) rem 8
                    }
                }
                if (u == 1) {
                    return j
                }
                // Now both u and p are odd, so use quadratic reciprocity
                require(u < p)
                val t = u
                u = p
                p = t
                if (u and p and 2 != 0) {
                    // u = p = 3 (rem 4)?
                    j = -j
                }
                // Now u >= p, so it can be reduced
                u %= p
            }
            return 0
        }

        private fun lucasLehmerSequence(
            z: Int,
            k: CommonBigInteger,
            n: CommonBigInteger,
        ): CommonBigInteger {
            val d = of(z.toLong())
            var u = ONE
            var u2: CommonBigInteger
            var v = ONE
            var v2: CommonBigInteger

            for (i in k.bitLength - 2 downTo 0) {
                u2 = u.times(v).rem(n)

                v2 = v.square().plus(d.times(u.square())).rem(n)
                if (v2.testBit(0)) {
                    v2 = v2.minus(n)
                }

                v2 = v2.shr(1)

                u = u2
                v = v2
                if (k.testBit(i)) {
                    u2 = u.plus(v).rem(n)
                    if (u2.testBit(0)) {
                        u2 = u2.minus(n)
                    }

                    u2 = u2.shr(1)
                    v2 = v.plus(d.times(u)).rem(n)
                    if (v2.testBit(0)) {
                        v2 = v2.minus(n)
                    }
                    v2 = v2.shr(1)

                    u = u2
                    v = v2
                }
            }
            return u
        }

        private fun reportOverflow(): Unit = throw ArithmeticException("BigInteger would overflow supported range")

        // Static Factory Methods

        /**
         * Returns a BigInteger whose value is equal to that of the
         * specified `long`.
         *
         * @apiNote This static factory method is provided in preference
         * to a (`long`) constructor because it allows for reuse of
         * frequently used BigIntegers.
         *
         * @param other value of the BigInteger to return.
         * @return a BigInteger with the specified value.
         */
        @JsName("ofLong")
        @JvmStatic
        fun of(other: Long): CommonBigInteger {
            // If -MAX_CONSTANT < val < MAX_CONSTANT, return stashed constant
            if (other == 0L) {
                return ZERO
            }
            if (other in 1..MAX_CONSTANT) {
                return posConst[other.toInt()] ?: throw IllegalStateException()
            } else if (other < 0 && other >= -MAX_CONSTANT) {
                return negConst[(-other).toInt()] ?: throw IllegalStateException()
            }

            return CommonBigInteger(other)
        }

        @JsName("of")
        @JvmStatic
        fun of(value: Int): CommonBigInteger = of(value.toLong())

        @JsName("parse")
        @JvmStatic
        fun of(value: String): CommonBigInteger {
            val radixed = value.getRadix()
            return CommonBigInteger(radixed.second, radixed.first)
        }

        @JsName("parseWithRadix")
        @JvmStatic
        fun of(
            value: String,
            radix: Int,
        ): CommonBigInteger = CommonBigInteger(value, radix)

        /**
         * Returns a BigInteger with the given two's complement representation.
         * Assumes that the input array will not be modified (the returned
         * BigInteger will reference the input array if feasible).
         */
        @JsName("ofIntArray")
        @JvmStatic
        fun of(other: IntArray): CommonBigInteger =
            if (other[0] > 0) CommonBigInteger(other, 1) else CommonBigInteger(other)

        // Constants

        /**
         * Initialize static constant array when class is loaded.
         */
        private val MAX_CONSTANT = 16
        private val posConst = arrayOfNulls<CommonBigInteger>(MAX_CONSTANT + 1)
        private val negConst = arrayOfNulls<CommonBigInteger>(MAX_CONSTANT + 1)

        /**
         * The cache of powers of each radix.  This allows us to not have to
         * recalculate powers of radix^(2^n) more than once.  This speeds
         * Schoenhage recursive base conversion significantly.
         */
        private var powerCache: Array<Array<CommonBigInteger>?> = arrayOfNulls(0)

        /** The cache of logarithms of radices for base conversion.  */
        private val logCache: DoubleArray

        /** The natural log of 2.  This is used in computing cache indices.  */
        private val LOG_TWO = ln(2.0)

        init {
            for (i in 1..MAX_CONSTANT) {
                val magnitude = IntArray(1)
                magnitude[0] = i
                posConst[i] = CommonBigInteger(magnitude, 1)
                negConst[i] = CommonBigInteger(magnitude, -1)
            }

            /*
             * Initialize the cache of radix^(2^x) values used for base conversion
             * with just the very first value.  Additional values will be created
             * on demand.
             */
            powerCache = arrayOfNulls(CHAR_MAX_RADIX + 1)
            logCache = DoubleArray(CHAR_MAX_RADIX + 1)

            for (i in CHAR_MIN_RADIX..CHAR_MAX_RADIX) {
                powerCache[i] = arrayOf(of(i.toLong()))
                logCache[i] = ln(i.toDouble())
            }
        }

        /**
         * The BigInteger constant zero.
         *
         * @since 1.2
         */
        @JvmField
        @JsName("ZERO")
        val ZERO = CommonBigInteger(IntArray(0), 0)

        /**
         * The BigInteger constant one.
         *
         * @since 1.2
         */
        @JvmField
        @JsName("ONE")
        val ONE = of(1)

        /**
         * The BigInteger constant two.
         *
         * @since 9
         */
        @JvmField
        @JsName("TWO")
        val TWO = of(2)

        /**
         * The BigInteger constant -1.  (Not exported.)
         */
        @JvmField
        @JsName("NEGATIVE_ONE")
        val NEGATIVE_ONE = of(-1)

        /**
         * The BigInteger constant ten.
         *
         * @since 1.5
         */
        @JvmField
        @JsName("TEN")
        val TEN = of(10)

        /**
         * Adds the contents of the int array x and long value val. This
         * method allocates a new int array to hold the answer and returns
         * a reference to that array.  Assumes x.length &gt; 0 and val is
         * non-negative
         */
        private fun sum(
            x: IntArray,
            other: Long,
        ): IntArray {
            var sum: Long = 0
            var xIndex = x.size
            val result: IntArray
            val highWord = other.ushr(32).toInt()
            if (highWord == 0) {
                result = IntArray(xIndex)
                sum = (x[--xIndex].toLong() and LONG_MASK) + other
                result[xIndex] = sum.toInt()
            } else {
                if (xIndex == 1) {
                    result = IntArray(2)
                    sum = other + (x[0].toLong() and LONG_MASK)
                    result[1] = sum.toInt()
                    result[0] = sum.ushr(32).toInt()
                    return result
                } else {
                    result = IntArray(xIndex)
                    sum = (x[--xIndex].toLong() and LONG_MASK) + (other.toLong() and LONG_MASK)
                    result[xIndex] = sum.toInt()
                    sum = (x[--xIndex].toLong() and LONG_MASK) + (highWord.toLong() and LONG_MASK) + sum.ushr(32)
                    result[xIndex] = sum.toInt()
                }
            }
            // Copy remainder of longer number while carry propagation is required
            var carry = sum.ushr(32) != 0L
            while (xIndex > 0 && carry) {
                result[xIndex - 1] = x[xIndex] + 1
                carry = result[--xIndex] == 0
            }
            // Copy remainder of longer number
            while (xIndex > 0) {
                result[--xIndex] = x[xIndex]
            }
            // Grow result if necessary
            if (carry) {
                val bigger = IntArray(result.size + 1)
                arrayCopy(result, 0, bigger, 1, result.size)
                bigger[0] = 0x01
                return bigger
            }
            return result
        }

        /**
         * Adds the contents of the int arrays x and y. This method allocates
         * a new int array to hold the answer and returns a reference to that
         * array.
         */
        private fun sum(
            x: IntArray,
            y: IntArray,
        ): IntArray {
            var x = x
            var y = y
            // If x is shorter, swap the two arrays
            if (x.size < y.size) {
                val tmp = x
                x = y
                y = tmp
            }

            var xIndex = x.size
            var yIndex = y.size
            val result = IntArray(xIndex)
            var sum: Long = 0
            if (yIndex == 1) {
                xIndex--
                sum = (x[xIndex].toLong() and LONG_MASK) + (y[0].toLong() and LONG_MASK)
                result[xIndex] = sum.toInt()
            } else {
                // Add common parts of both numbers
                while (yIndex > 0) {
                    sum = (x[--xIndex].toLong() and LONG_MASK) +
                        (y[--yIndex].toLong() and LONG_MASK) + sum.ushr(32)
                    result[xIndex] = sum.toInt()
                }
            }
            // Copy remainder of longer number while carry propagation is required
            var carry = sum.ushr(32) != 0L
            while (xIndex > 0 && carry) {
                // carry = ((result[--xIndex] = x[xIndex] + 1) == 0)
                result[--xIndex] = x[xIndex] + 1
                carry = result[xIndex] == 0
            }

            // Copy remainder of longer number
            while (xIndex > 0) {
                result[--xIndex] = x[xIndex]
            }

            // Grow result if necessary
            if (carry) {
                val bigger = IntArray(result.size + 1)
                arrayCopy(result, 0, bigger, 1, result.size)
                bigger[0] = 0x01
                return bigger
            }
            return result
        }

        private fun subtract(
            other: Long,
            little: IntArray,
        ): IntArray {
            val highWord = other.ushr(32).toInt()
            if (highWord == 0) {
                val result = IntArray(1)
                result[0] = (other - (little[0].toLong() and LONG_MASK)).toInt()
                return result
            } else {
                val result = IntArray(2)
                if (little.size == 1) {
                    val difference = (other.toInt().toLong() and LONG_MASK) - (little[0].toLong() and LONG_MASK)
                    result[1] = difference.toInt()
                    // Subtract remainder of longer number while borrow propagates
                    val borrow = difference shr 32 != 0L
                    if (borrow) {
                        result[0] = highWord - 1
                    } else { // Copy remainder of longer number
                        result[0] = highWord
                    }
                    return result
                } else { // little.length == 2
                    var difference = (other.toInt().toLong() and LONG_MASK) - (little[1].toLong() and LONG_MASK)
                    result[1] = difference.toInt()
                    difference =
                        (highWord.toLong() and LONG_MASK) - (little[0].toLong() and LONG_MASK) + (difference shr 32)
                    result[0] = difference.toInt()
                    return result
                }
            }
        }

        /**
         * Subtracts the contents of the second argument (val) from the
         * first (big).  The first int array (big) must represent a larger number
         * than the second.  This method allocates the space necessary to hold the
         * answer.
         * assumes val &gt;= 0
         */
        private fun subtract(
            big: IntArray,
            other: Long,
        ): IntArray {
            val highWord = other.ushr(32).toInt()
            var bigIndex = big.size
            val result = IntArray(bigIndex)
            var difference: Long = 0

            if (highWord == 0) {
                difference = (big[--bigIndex].toLong() and LONG_MASK) - other
                result[bigIndex] = difference.toInt()
            } else {
                difference = (big[--bigIndex].toLong() and LONG_MASK) - (other and LONG_MASK)
                result[bigIndex] = difference.toInt()
                difference =
                    (big[--bigIndex].toLong() and LONG_MASK) - (highWord.toLong() and LONG_MASK) + (difference shr 32)
                result[bigIndex] = difference.toInt()
            }

            // Subtract remainder of longer number while borrow propagates
            var borrow = difference shr 32 != 0L
            while (bigIndex > 0 && borrow) {
                result[--bigIndex] = big[bigIndex] - 1
                borrow = result[bigIndex] == -1
            }

            // Copy remainder of longer number
            while (bigIndex > 0) {
                result[--bigIndex] = big[bigIndex]
            }

            return result
        }

        /**
         * Subtracts the contents of the second int arrays (little) from the
         * first (big).  The first int array (big) must represent a larger number
         * than the second.  This method allocates the space necessary to hold the
         * answer.
         */
        private fun subtract(
            big: IntArray,
            little: IntArray,
        ): IntArray {
            var bigIndex = big.size
            val result = IntArray(bigIndex)
            var littleIndex = little.size
            var difference: Long = 0

            // Subtract common parts of both numbers
            while (littleIndex > 0) {
                difference = (big[--bigIndex].toLong() and LONG_MASK) -
                    (little[--littleIndex].toLong() and LONG_MASK) +
                    (difference shr 32)
                result[bigIndex] = difference.toInt()
            }

            // Subtract remainder of longer number while borrow propagates
            var borrow = difference shr 32 != 0L
            while (bigIndex > 0 && borrow) {
                result[--bigIndex] = big[bigIndex] - 1
                borrow = result[bigIndex] == -1
            }

            // Copy remainder of longer number
            while (bigIndex > 0) {
                result[--bigIndex] = big[bigIndex]
            }

            return result
        }

        private fun multiplyByInt(
            x: IntArray,
            y: Int,
            sign: Int,
        ): CommonBigInteger {
            if (y.bitCount() == 1) {
                return CommonBigInteger(
                    shl(
                        x,
                        y.numberOfTrailingZeros(),
                    ),
                    sign,
                )
            }
            val xlen = x.size
            var rmag = IntArray(xlen + 1)
            var carry: Long = 0
            val yl = y.toLong() and LONG_MASK
            var rstart = rmag.size - 1
            for (i in xlen - 1 downTo 0) {
                val product = (x[i].toLong() and LONG_MASK) * yl + carry
                rmag[rstart--] = product.toInt()
                carry = product.ushr(32)
            }
            if (carry == 0L) {
                rmag = rmag.copyOfRange(1, rmag.size)
            } else {
                rmag[rstart] = carry.toInt()
            }
            return CommonBigInteger(rmag, sign)
        }

        /**
         * Multiplies int arrays x and y to the specified lengths and places
         * the result into z. There will be no leading zeros in the resultant array.
         */
        private fun multiplyToLen(
            x: IntArray,
            xlen: Int,
            y: IntArray,
            ylen: Int,
            z: IntArray?,
        ): IntArray {
            multiplyToLenCheck(x, xlen)
            multiplyToLenCheck(y, ylen)
            return implMultiplyToLen(x, xlen, y, ylen, z)
        }

        private fun implMultiplyToLen(
            x: IntArray,
            xlen: Int,
            y: IntArray,
            ylen: Int,
            z: IntArray?,
        ): IntArray {
            var z = z
            val xstart = xlen - 1
            val ystart = ylen - 1

            if (z == null || z.size < xlen + ylen) {
                z = IntArray(xlen + ylen)
            }

            var carry: Long = 0
            run {
                var j = ystart
                var k = ystart + 1 + xstart
                while (j >= 0) {
                    val product = (y[j].toLong() and LONG_MASK) * (x[xstart].toLong() and LONG_MASK) + carry
                    z[k] = product.toInt()
                    carry = product.ushr(32)
                    j--
                    k--
                }
            }
            z[xstart] = carry.toInt()

            for (i in xstart - 1 downTo 0) {
                carry = 0
                var j = ystart
                var k = ystart + 1 + i
                while (j >= 0) {
                    val product =
                        (y[j].toLong() and LONG_MASK) * (x[i].toLong() and LONG_MASK) +
                            (z[k].toLong() and LONG_MASK) + carry
                    z[k] = product.toInt()
                    carry = product.ushr(32)
                    j--
                    k--
                }
                z[i] = carry.toInt()
            }
            return z
        }

        private fun multiplyToLenCheck(
            array: IntArray,
            length: Int,
        ) {
            if (length <= 0) {
                return // not an error because multiplyToLen won't execute if len <= 0
            }

            if (length > array.size) {
                throw IndexOutOfBoundsException("${length - 1}")
            }
        }

        /**
         * Multiplies two BigIntegers using the Karatsuba multiplication
         * algorithm.  This is a recursive div-and-conquer algorithm which is
         * more efficient for large numbers than what is commonly called the
         * "grade-school" algorithm used in multiplyToLen.  If the numbers to be
         * multiplied have length n, the "grade-school" algorithm has an
         * asymptotic complexity of O(n^2).  In contrast, the Karatsuba algorithm
         * has complexity of O(n^(log2(3))), or O(n^1.585).  It achieves this
         * increased performance by doing 3 multiplies instead of 4 when
         * evaluating the product.  As it has some overhead, should be used when
         * both numbers are larger than a certain threshold (found
         * experimentally).
         *
         * See:  http://en.wikipedia.org/wiki/Karatsuba_algorithm
         */
        private fun multiplyKaratsuba(
            x: CommonBigInteger,
            y: CommonBigInteger,
        ): CommonBigInteger {
            val xlen = x._mag.size
            val ylen = y._mag.size

            // The number of ints in each half of the number.
            val half = (max(xlen, ylen) + 1) / 2

            // xl and yl are the lower halves of x and y respectively,
            // xh and yh are the upper halves.
            val xl = x.getLower(half)
            val xh = x.getUpper(half)
            val yl = y.getLower(half)
            val yh = y.getUpper(half)

            val p1 = xh.times(yh) // p1 = xh*yh
            val p2 = xl.times(yl) // p2 = xl*yl

            // p3=(xh+xl)*(yh+yl)
            val p3 = xh.plus(xl).times(yh.plus(yl))

            // result = p1 * 2^(32*2*half) + (p3 - p1 - p2) * 2^(32*half) + p2
            val result =
                p1
                    .shl(32 * half)
                    .plus(p3.minus(p1).minus(p2))
                    .shl(32 * half)
                    .plus(p2)

            return if (x._signum != y._signum) {
                result.unaryMinus()
            } else {
                result
            }
        }

        /**
         * Multiplies two BigIntegers using a 3-way Toom-Cook multiplication
         * algorithm.  This is a recursive div-and-conquer algorithm which is
         * more efficient for large numbers than what is commonly called the
         * "grade-school" algorithm used in multiplyToLen.  If the numbers to be
         * multiplied have length n, the "grade-school" algorithm has an
         * asymptotic complexity of O(n^2).  In contrast, 3-way Toom-Cook has a
         * complexity of about O(n^1.465).  It achieves this increased asymptotic
         * performance by breaking each number into three parts and by doing 5
         * multiplies instead of 9 when evaluating the product.  Due to overhead
         * (additions, shifts, and one division) in the Toom-Cook algorithm, it
         * should only be used when both numbers are larger than a certain
         * threshold (found experimentally).  This threshold is generally larger
         * than that for Karatsuba multiplication, so this algorithm is generally
         * only used when numbers become significantly larger.
         *
         * The algorithm used is the "optimal" 3-way Toom-Cook algorithm outlined
         * by Marco Bodrato.
         *
         * See: http://bodrato.it/toom-cook/
         * http://bodrato.it/papers/#WAIFI2007
         *
         * "Towards Optimal Toom-Cook Multiplication for Univariate and
         * Multivariate Polynomials in Characteristic 2 and 0." by Marco BODRATO;
         * In C.Carlet and B.Sunar, Eds., "WAIFI'07 proceedings", p. 116-133,
         * LNCS #4547. Springer, Madrid, Spain, June 21-22, 2007.
         *
         */
        private fun multiplyToomCook3(
            a: CommonBigInteger,
            b: CommonBigInteger,
        ): CommonBigInteger {
            val alen = a._mag.size
            val blen = b._mag.size

            val largest = max(alen, blen)

            // k is the size (in ints) of the lower-order slices.
            val k = (largest + 2) / 3 // Equal to ceil(largest/3)

            // r is the size (in ints) of the highest-order slice.
            val r = largest - 2 * k

            // Obtain slices of the numbers. a2 and b2 are the most significant
            // bits of the numbers a and b, and a0 and b0 the least significant.
            val a0: CommonBigInteger
            val a1: CommonBigInteger
            val a2: CommonBigInteger
            val b0: CommonBigInteger
            val b1: CommonBigInteger
            val b2: CommonBigInteger
            a2 = a.getToomSlice(k, r, 0, largest)
            a1 = a.getToomSlice(k, r, 1, largest)
            a0 = a.getToomSlice(k, r, 2, largest)
            b2 = b.getToomSlice(k, r, 0, largest)
            b1 = b.getToomSlice(k, r, 1, largest)
            b0 = b.getToomSlice(k, r, 2, largest)

            val v0: CommonBigInteger
            val v1: CommonBigInteger
            val v2: CommonBigInteger
            val vm1: CommonBigInteger
            val vinf: CommonBigInteger
            var t1: CommonBigInteger
            var t2: CommonBigInteger
            var tm1: CommonBigInteger
            var da1: CommonBigInteger
            var db1: CommonBigInteger

            v0 = a0.times(b0)
            da1 = a2.plus(a0)
            db1 = b2.plus(b0)
            vm1 = da1.minus(a1).times(db1.minus(b1))
            da1 = da1.plus(a1)
            db1 = db1.plus(b1)
            v1 = da1.times(db1)
            v2 =
                da1.plus(a2).shl(1).minus(a0).times(
                    db1.plus(b2).shl(1).minus(b0),
                )
            vinf = a2.times(b2)

            // The algorithm requires two divisions by 2 and one by 3.
            // All divisions are known to be exact, that is, they do not produce
            // remainders, and all results are positive.  The divisions by 2 are
            // implemented as right shifts which are relatively efficient, leaving
            // only an exact division by 3, which is done by a specialized
            // linear-time algorithm.
            t2 = v2.minus(vm1).exactDivideBy3()
            tm1 = v1.minus(vm1).shr(1)
            t1 = v1.minus(v0)
            t2 = t2.minus(t1).shr(1)
            t1 = t1.minus(tm1).minus(vinf)
            t2 = t2.minus(vinf.shl(1))
            tm1 = tm1.minus(t2)

            // Number of bits to shift left.
            val ss = k * 32

            val result =
                vinf
                    .shl(ss)
                    .plus(t2)
                    .shl(ss)
                    .plus(t1)
                    .shl(ss)
                    .plus(tm1)
                    .shl(ss)
                    .plus(v0)

            return if (a._signum != b._signum) {
                result.unaryMinus()
            } else {
                result
            }
        }

        /**
         * Squares the contents of the int array x. The result is placed into the
         * int array z.  The contents of x are not changed.
         */
        private fun squareToLen(
            x: IntArray,
            len: Int,
            z: IntArray?,
        ): IntArray {
            var z = z
            val zlen = len shl 1
            if (z == null || z.size < zlen) {
                z = IntArray(zlen)
            }

            // Execute checks before calling intrinsified method.
            implSquareToLenChecks(x, len, z, zlen)
            return implSquareToLen(x, len, z, zlen)
        }

        /**
         * Parameters validation.
         */
        private fun implSquareToLenChecks(
            x: IntArray,
            len: Int,
            z: IntArray,
            zlen: Int,
        ) {
            if (len < 1) {
                throw IllegalArgumentException("invalid input length: $len")
            }
            if (len > x.size) {
                throw IllegalArgumentException(
                    "input length out of bound: " +
                        len + " > " + x.size,
                )
            }
            if (len * 2 > z.size) {
                throw IllegalArgumentException(
                    "input length out of bound: " +
                        len * 2 + " > " + z.size,
                )
            }
            if (zlen < 1) {
                throw IllegalArgumentException("invalid input length: $zlen")
            }
            if (zlen > z.size) {
                throw IllegalArgumentException(
                    "input length out of bound: " +
                        len + " > " + z.size,
                )
            }
        }

        /**
         * Java Runtime may use intrinsic for this method.
         */
        private fun implSquareToLen(
            x: IntArray,
            len: Int,
            z: IntArray,
            zlen: Int,
        ): IntArray {
            /*
             * The algorithm used here is adapted from Colin Plumb's C library.
             * Technique: Consider the partial products in the multiplication
             * of "abcde" by itself:
             *
             *               a  b  c  d  e
             *            *  a  b  c  d  e
             *          ==================
             *              ae be ce de ee
             *           ad bd cd dd de
             *        ac bc cc cd ce
             *     ab bb bc bd be
             *  aa ab ac ad ae
             *
             * Note that everything above the main diagonal:
             *              ae be ce de = (abcd) * e
             *           ad bd cd       = (abc) * d
             *        ac bc             = (ab) * c
             *     ab                   = (a) * b
             *
             * is a copy of everything below the main diagonal:
             *                       de
             *                 cd ce
             *           bc bd be
             *     ab ac ad ae
             *
             * Thus, the sum is 2 * (off the diagonal) + diagonal.
             *
             * This is accumulated beginning with the diagonal (which
             * consist of the squares of the digits of the input), which is then
             * divided by two, the off-diagonal added, and multiplied by two
             * again.  The low bit is simply a copy of the low bit of the
             * input, so it doesn't need special care.
             */

            // Store the squares, right shifted one bit (i.e., divided by 2)
            var lastProductLowWord = 0
            run {
                var j = 0
                var i = 0
                while (j < len) {
                    val piece = x[j].toLong() and LONG_MASK
                    val product = piece * piece
                    z[i++] = lastProductLowWord shl 31 or product.ushr(33).toInt()
                    z[i++] = product.ushr(1).toInt()
                    lastProductLowWord = product.toInt()
                    j++
                }
            }

            // Add in off-diagonal sums
            var i = len
            var offset = 1
            while (i > 0) {
                var t = x[i - 1]
                t = mulAdd(z, x, offset, i - 1, t)
                addOne(z, offset - 1, i, t)
                i--
                offset += 2
            }

            // Shift back up and set low bit
            primitiveLeftShift(z, zlen, 1)
            z[zlen - 1] = z[zlen - 1] or (x[len - 1] and 1)

            return z
        }

        /**
         * Package private method to return bit length for an integer.
         */
        internal fun bitLengthForInt(n: Int): Int = 32 - n.numberOfLeadingZeros()

        /**
         * Left shift int array a up to len by n bits. Returns the array that
         * results from the shift since space may have to be reallocated.
         */
        private fun leftShift(
            a: IntArray,
            len: Int,
            n: Int,
        ): IntArray {
            val nInts = n.ushr(5)
            val nBits = n and 0x1F
            val bitsInHighWord = bitLengthForInt(a[0])

            // If shift can be done without recopy, do so
            if (n <= 32 - bitsInHighWord) {
                primitiveLeftShift(a, len, nBits)
                return a
            } else { // Array must be resized
                if (nBits <= 32 - bitsInHighWord) {
                    val result = IntArray(nInts + len)
                    arrayCopy(a, 0, result, 0, len)
                    primitiveLeftShift(result, result.size, nBits)
                    return result
                } else {
                    val result = IntArray(nInts + len + 1)
                    arrayCopy(a, 0, result, 0, len)
                    primitiveRightShift(result, result.size, 32 - nBits)
                    return result
                }
            }
        }

        // shifts a up to len right n bits assumes no leading zeros, 0<n<32
        private fun primitiveRightShift(
            a: IntArray,
            len: Int,
            n: Int,
        ) {
            val n2 = 32 - n
            var i = len - 1
            var c = a[i]
            while (i > 0) {
                val b = c
                c = a[i - 1]
                a[i] = c shl n2 or b.ushr(n)
                i--
            }
            a[0] = a[0] ushr n
        }

        // shifts a up to len left n bits assumes no leading zeros, 0<=n<32
        private fun primitiveLeftShift(
            a: IntArray,
            len: Int,
            n: Int,
        ) {
            if (len == 0 || n == 0) {
                return
            }

            val n2 = 32 - n
            var i = 0
            var c = a[i]
            val m = i + len - 1
            while (i < m) {
                val b = c
                c = a[i + 1]
                a[i] = b shl n or c.ushr(n2)
                i++
            }
            a[len - 1] = a[len - 1] shl n
        }

        /**
         * Calculate bitlength of contents of the first len elements an int array,
         * assuming there are no leading zero ints.
         */
        private fun bitLength(
            other: IntArray,
            len: Int,
        ): Int = if (len == 0) 0 else (len - 1 shl 5) + bitLengthForInt(other[0])

        // Montgomery multiplication.  These are wrappers for
        // implMontgomeryXX routines which are expected to be replaced by
        // virtual machine intrinsics.  We don't use the intrinsics for
        // very large operands: MONTGOMERY_INTRINSIC_THRESHOLD should be
        // larger than any reasonable crypto key.
        private fun montgomeryMultiply(
            a: IntArray,
            b: IntArray,
            n: IntArray,
            len: Int,
            inv: Long,
            product: IntArray?,
        ): IntArray {
            var product = product
            implMontgomeryMultiplyChecks(a, b, n, len, product)
            if (len > MONTGOMERY_INTRINSIC_THRESHOLD) {
                // Very long argument: do not use an intrinsic
                product = multiplyToLen(a, len, b, len, product)
                return montReduce(product, n, len, inv.toInt())
            } else {
                return implMontgomeryMultiply(
                    a,
                    b,
                    n,
                    len,
                    inv,
                    materialize(product, len),
                )
            }
        }

        private fun montgomerySquare(
            a: IntArray,
            n: IntArray,
            len: Int,
            inv: Long,
            product: IntArray?,
        ): IntArray {
            var product = product
            implMontgomeryMultiplyChecks(a, a, n, len, product)
            if (len > MONTGOMERY_INTRINSIC_THRESHOLD) {
                // Very long argument: do not use an intrinsic
                product = squareToLen(a, len, product)
                return montReduce(product, n, len, inv.toInt())
            } else {
                return implMontgomerySquare(
                    a,
                    n,
                    len,
                    inv,
                    materialize(product, len),
                )
            }
        }

        private fun implMontgomeryMultiplyChecks(
            a: IntArray,
            b: IntArray,
            n: IntArray,
            len: Int,
            product: IntArray?,
        ) {
            if (len % 2 != 0) {
                throw IllegalArgumentException("input array length must be even: $len")
            }

            if (len < 1) {
                throw IllegalArgumentException("invalid input length: $len")
            }

            if (len > a.size ||
                len > b.size ||
                len > n.size ||
                product != null &&
                len > product.size
            ) {
                throw IllegalArgumentException("input array length out of bound: $len")
            }
        }

        // Make sure that the int array z (which is expected to contain
        // the result of a Montgomery multiplication) is present and
        // sufficiently large.
        private fun materialize(
            z: IntArray?,
            len: Int,
        ): IntArray {
            var z = z
            if (z == null || z.size < len) {
                z = IntArray(len)
            }
            return z
        }

        // These methods are intended to be be replaced by virtual machine
        // intrinsics.
        private fun implMontgomeryMultiply(
            a: IntArray,
            b: IntArray,
            n: IntArray,
            len: Int,
            inv: Long,
            product: IntArray,
        ): IntArray {
            var product = product
            product = multiplyToLen(a, len, b, len, product)
            return montReduce(product, n, len, inv.toInt())
        }

        private fun implMontgomerySquare(
            a: IntArray,
            n: IntArray,
            len: Int,
            inv: Long,
            product: IntArray,
        ): IntArray {
            var product = product
            product = squareToLen(a, len, product)
            return montReduce(product, n, len, inv.toInt())
        }

        internal var bnExpModThreshTable = intArrayOf(7, 25, 81, 241, 673, 1793, Int.MAX_VALUE) // Sentinel

        /**
         * Montgomery reduce n, modulo rem.  This reduces modulo rem and divides
         * by 2^(32*mlen). Adapted from Colin Plumb's C library.
         */
        private fun montReduce(
            n: IntArray,
            mod: IntArray,
            mlen: Int,
            inv: Int,
        ): IntArray {
            var c = 0
            var len = mlen
            var offset = 0

            do {
                val nEnd = n[n.size - 1 - offset]
                val carry = mulAdd(n, mod, offset, mlen, inv * nEnd)
                c += addOne(n, offset, mlen, carry)
                offset++
            } while (--len > 0)

            while (c > 0) {
                c += subN(n, mod, mlen)
            }

            while (intArrayCmpToLen(n, mod, mlen) >= 0) {
                subN(n, mod, mlen)
            }

            return n
        }

        /*
         * Returns -1, 0 or +1 as big-endian unsigned int array arg1 is less than,
         * equal to, or greater than arg2 up to length len.
         */
        private fun intArrayCmpToLen(
            arg1: IntArray,
            arg2: IntArray,
            len: Int,
        ): Int {
            for (i in 0 until len) {
                val b1 = arg1[i].toLong() and LONG_MASK
                val b2 = arg2[i].toLong() and LONG_MASK
                if (b1 < b2) {
                    return -1
                }
                if (b1 > b2) {
                    return 1
                }
            }
            return 0
        }

        /**
         * Subtracts two numbers of same length, returning borrow.
         */
        private fun subN(
            a: IntArray,
            b: IntArray,
            len: Int,
        ): Int {
            var len = len
            var sum: Long = 0

            while (--len >= 0) {
                sum = (a[len].toLong() and LONG_MASK) - (b[len].toLong() and LONG_MASK) + (sum shr 32)
                a[len] = sum.toInt()
            }

            return (sum shr 32).toInt()
        }

        /**
         * Multiply an array by one word k and plus to result, return the carry
         */
        private fun mulAdd(
            out: IntArray,
            `in`: IntArray,
            offset: Int,
            len: Int,
            k: Int,
        ): Int {
            implMulAddCheck(out, `in`, offset, len, k)
            return implMulAdd(out, `in`, offset, len, k)
        }

        /**
         * Parameters validation.
         */
        @Suppress("UNUSED_PARAMETER")
        private fun implMulAddCheck(
            out: IntArray,
            `in`: IntArray,
            offset: Int,
            len: Int,
            k: Int,
        ) {
            if (len > `in`.size) {
                throw IllegalArgumentException("input length is out of bound: " + len + " > " + `in`.size)
            }
            if (offset < 0) {
                throw IllegalArgumentException("input offset is invalid: $offset")
            }
            if (offset > out.size - 1) {
                throw IllegalArgumentException("input offset is out of bound: " + offset + " > " + (out.size - 1))
            }
            if (len > out.size - offset) {
                throw IllegalArgumentException("input len is out of bound: " + len + " > " + (out.size - offset))
            }
        }

        /**
         * Java Runtime may use intrinsic for this method.
         */
        private fun implMulAdd(
            out: IntArray,
            `in`: IntArray,
            offset: Int,
            len: Int,
            k: Int,
        ): Int {
            var offset = offset
            val kLong = k.toLong() and LONG_MASK
            var carry: Long = 0

            offset = out.size - offset - 1
            for (j in len - 1 downTo 0) {
                val product =
                    (`in`[j].toLong() and LONG_MASK) * kLong +
                        (out[offset].toLong() and LONG_MASK) + carry
                out[offset--] = product.toInt()
                carry = product.ushr(32)
            }
            return carry.toInt()
        }

        /**
         * Add one word to the number a mlen words into a. Return the resulting
         * carry.
         */
        private fun addOne(
            a: IntArray,
            offset: Int,
            mlen: Int,
            carry: Int,
        ): Int {
            var offset = offset
            var mlen = mlen
            offset = a.size - 1 - mlen - offset
            val t = (a[offset].toLong() and LONG_MASK) + (carry.toLong() and LONG_MASK)

            a[offset] = t.toInt()
            if (t.ushr(32) == 0L) {
                return 0
            }
            while (--mlen >= 0) {
                if (--offset < 0) { // Carry out of number
                    return 1
                } else {
                    a[offset]++
                    if (a[offset] != 0) {
                        return 0
                    }
                }
            }
            return 1
        }

        /**
         * Returns a magnitude array whose value is `(_mag << n)`.
         * The shift distance, `n`, is considered unnsigned.
         * (Computes `this * 2<sup>n</sup>`.)
         *
         * @param mag magnitude, the most-significant int (`_mag[0]`) must be non-zero.
         * @param n unsigned shift distance, in bits.
         * @return `_mag << n`
         */
        private fun shl(
            mag: IntArray,
            n: Int,
        ): IntArray {
            val nInts = n.ushr(5)
            val nBits = n and 0x1f
            val magLen = mag.size
            var newMag: IntArray? = null

            if (nBits == 0) {
                newMag = IntArray(magLen + nInts)
                arrayCopy(mag, 0, newMag, 0, magLen)
            } else {
                var i = 0
                val nBits2 = 32 - nBits
                val highBits = mag[0].ushr(nBits2)
                if (highBits != 0) {
                    newMag = IntArray(magLen + nInts + 1)
                    newMag[i++] = highBits
                } else {
                    newMag = IntArray(magLen + nInts)
                }
                var j = 0
                while (j < magLen - 1) {
                    newMag[i++] = mag[j++] shl nBits or mag[j].ushr(nBits2)
                }
                newMag[i] = mag[j] shl nBits
            }
            return newMag
        }

        /**
         * Converts the specified BigInteger to a string and appends to
         * `sb`.  This implements the recursive Schoenhage algorithm
         * for base conversions.
         *
         *
         * See Knuth, Donald,  _The Art of Computer Programming_, Vol. 2,
         * Answers to Exercises (4.4) Question 14.
         *
         * @param u      The number to convert to a string.
         * @param sb     The StringBuilder that will be appended to in place.
         * @param radix  The base to convert to.
         * @param digits The minimum number of digits to pad to.
         */
        private fun toString(
            u: CommonBigInteger,
            sb: StringBuilder,
            radix: Int,
            digits: Int,
        ) {
            // If we're smaller than a certain threshold, use the smallToString
            // method, padding with leading zeroes when necessary.
            if (u._mag.size <= SCHOENHAGE_BASE_CONVERSION_THRESHOLD) {
                val s = u.smallToString(radix)

                // Pad with internal zeros if necessary.
                // Don't pad if we're at the beginning of the string.
                if (s.length < digits && sb.length > 0) {
                    for (i in s.length until digits) {
                        sb.append('0')
                    }
                }

                sb.append(s)
                return
            }

            val b: Int
            val n: Int
            b = u.bitLength

            // Calculate a value for n in the equation radix^(2^n) = u
            // and subtract 1 from that value.  This is used to find the
            // cache index that contains the best value to div u.
            n = round(ln(b * LOG_TWO / logCache[radix]) / LOG_TWO - 1.0).toInt()
            val v = getRadixConversionCache(radix, n)
            val results: Array<CommonBigInteger>
            results = u.divideAndRemainder(v)

            val expectedDigits = 1 shl n

            // Now recursively build the two halves of each number.
            toString(results[0], sb, radix, digits - expectedDigits)
            toString(results[1], sb, radix, expectedDigits)
        }

        /**
         * Returns the value radix^(2^exponent) from the cache.
         * If this value doesn't already exist in the cache, it is added.
         *
         *
         * This could be changed to a more complicated caching method using
         * `Future`.
         */
        private fun getRadixConversionCache(
            radix: Int,
            exponent: Int,
        ): CommonBigInteger {
            // volatile read
            val cacheLine: Array<CommonBigInteger>? = powerCache[radix]
            if (exponent < cacheLine!!.size) {
                return cacheLine[exponent]
            }

            val oldLength = cacheLine.size
            val cacheLine2 = cacheLine.copyOf(exponent + 1)
            for (i in oldLength..exponent) {
                cacheLine2[i] = cacheLine2[i - 1]!!.pow(2)
            }

            var pc = powerCache // volatile read again
            if (exponent >= pc[radix]!!.size) {
                pc = pc.cloneArray()
                pc[radix] = cacheLine2.requireNoNulls()
                powerCache = pc // volatile write, publish
            }
            return cacheLine2[exponent]!!
        }

        // zero[i] is a string of i consecutive zeros.
        private val zeros = arrayOfNulls<String>(64)

        init {
            zeros[63] = "000000000000000000000000000000000000000000000000000000000000000"
            for (i in 0..62) {
                zeros[i] = zeros[63]!!.substring(0, i)
            }
        }

        /**
         * Returns a copy of the input array stripped of any leading zero bytes.
         */
        private fun stripLeadingZeroInts(other: IntArray): IntArray {
            val vlen = other.size
            var keep: Int

            // Find first nonzero byte
            keep = 0
            while (keep < vlen && other[keep] == 0) {
                keep++
            }
            return other.copyOfRange(keep, vlen)
        }

        /**
         * Returns the input array stripped of any leading zero bytes.
         * Since the source is trusted the copying may be skipped.
         */
        private fun trustedStripLeadingZeroInts(other: IntArray): IntArray {
            val vlen = other.size
            var keep: Int

            // Find first nonzero byte
            keep = 0
            while (keep < vlen && other[keep] == 0) {
                keep++
            }
            return if (keep == 0) other else other.copyOfRange(keep, vlen)
        }

        /**
         * Returns a copy of the input array stripped of any leading zero bytes.
         */
        private fun stripLeadingZeroBytes(
            a: ByteArray,
            off: Int,
            len: Int,
        ): IntArray {
            val indexBound = off + len
            var keep: Int

            // Find first nonzero byte
            keep = off
            while (keep < indexBound && a[keep].toInt() == 0) {
                keep++
            }

            // Allocate new array and copy relevant part of input array
            val intLength = (indexBound - keep + 3).ushr(2)
            val result = IntArray(intLength)
            var b = indexBound - 1
            for (i in intLength - 1 downTo 0) {
                result[i] = a[b--].toInt() and 0xff
                val bytesRemaining = b - keep + 1
                val bytesToTransfer = min(3, bytesRemaining)
                var j = 8
                while (j <= bytesToTransfer shl 3) {
                    result[i] = result[i] or (a[b--].toInt() and 0xff shl j)
                    j += 8
                }
            }
            return result
        }

        /**
         * Takes an array a representing a negative 2's-complement number and
         * returns the minimal (no leading zero bytes) unsigned whose value is -a.
         */
        private fun makePositive(
            a: ByteArray,
            off: Int,
            len: Int,
        ): IntArray {
            var keep: Int
            var k: Int
            val indexBound = off + len

            // Find first non-sign (0xff) byte of input
            keep = off
            while (keep < indexBound && a[keep].toInt() == -1) {
                keep++
            }

            /* Allocate output array.  If all non-sign bytes are 0x00, we must
             * allocate space for one extra output byte. */
            k = keep
            while (k < indexBound && a[k].toInt() == 0) {
                k++
            }

            val extraByte = if (k == indexBound) 1 else 0
            val intLength = (indexBound - keep + extraByte + 3).ushr(2)
            val result = IntArray(intLength)

            /* Copy one's complement of input into output, leaving extra
             * byte (if it exists) == 0x00 */
            var b = indexBound - 1
            for (i in intLength - 1 downTo 0) {
                result[i] = a[b--].toInt() and 0xff
                var numBytesToTransfer = min(3, b - keep + 1)
                if (numBytesToTransfer < 0) {
                    numBytesToTransfer = 0
                }
                var j = 8
                while (j <= 8 * numBytesToTransfer) {
                    result[i] = result[i] or (a[b--].toInt() and 0xff shl j)
                    j += 8
                }

                // Mask indicates which bits must be complemented
                val mask = (-1).ushr(8 * (3 - numBytesToTransfer))
                result[i] = result[i].inv() and mask
            }

            // Add one to one's complement to generate two's complement
            for (i in result.indices.reversed()) {
                result[i] = ((result[i].toLong() and LONG_MASK) + 1L).toInt()
                if (result[i] != 0) {
                    break
                }
            }

            return result
        }

        /**
         * Takes an array a representing a negative 2's-complement number and
         * returns the minimal (no leading zero ints) unsigned whose value is -a.
         */
        private fun makePositive(a: IntArray): IntArray {
            var keep: Int
            var j: Int

            // Find first non-sign (0xffffffff) int of input
            keep = 0
            while (keep < a.size && a[keep] == -1) {
                keep++
            }

            /* Allocate output array.  If all non-sign ints are 0x00, we must
             * allocate space for one extra output int. */
            j = keep
            while (j < a.size && a[j] == 0) {
                j++
            }
            val extraInt = if (j == a.size) 1 else 0
            val result = IntArray(a.size - keep + extraInt)

            /* Copy one's complement of input into output, leaving extra
             * int (if it exists) == 0x00 */
            for (i in keep until a.size) {
                result[i - keep + extraInt] = a[i].inv()
            }

            // Add one to one's complement to generate two's complement
            var i = result.size - 1
            while (++result[i] == 0) {
                i--
            }

            return result
        }

        /*
         * The following two arrays are used for fast String conversions.  Both
         * are indexed by radix.  The first is the number of digits of the given
         * radix that can fit in a Java long without "going negative", i.e., the
         * highest integer n such that radix**n < 2**63.  The second is the
         * "long radix" that tears each number into "long digits", each of which
         * consists of the number of digits in the corresponding element in
         * digitsPerLong (longRadix[i] = i**digitPerLong[i]).  Both arrays have
         * nonsense values in their 0 and 1 elements, as radixes 0 and 1 are not
         * used.
         */
        private val digitsPerLong =
            intArrayOf(
                0,
                0,
                62,
                39,
                31,
                27,
                24,
                22,
                20,
                19,
                18,
                18,
                17,
                17,
                16,
                16,
                15,
                15,
                15,
                14,
                14,
                14,
                14,
                13,
                13,
                13,
                13,
                13,
                13,
                12,
                12,
                12,
                12,
                12,
                12,
                12,
                12,
            )

        private val longRadix =
            arrayOf(
                null,
                null,
                of(0x4000000000000000L),
                of(0x383d9170b85ff80bL),
                of(0x4000000000000000L),
                of(0x6765c793fa10079dL),
                of(0x41c21cb8e1000000L),
                of(0x3642798750226111L),
                of(0x1000000000000000L),
                of(0x12bf307ae81ffd59L),
                of(0xde0b6b3a7640000L),
                of(0x4d28cb56c33fa539L),
                of(0x1eca170c00000000L),
                of(0x780c7372621bd74dL),
                of(0x1e39a5057d810000L),
                of(0x5b27ac993df97701L),
                of(0x1000000000000000L),
                of(0x27b95e997e21d9f1L),
                of(0x5da0e1e53c5c8000L),
                of(0xb16a458ef403f19L),
                of(0x16bcc41e90000000L),
                of(0x2d04b7fdd9c0ef49L),
                of(0x5658597bcaa24000L),
                of(0x6feb266931a75b7L),
                of(0xc29e98000000000L),
                of(0x14adf4b7320334b9L),
                of(0x226ed36478bfa000L),
                of(0x383d9170b85ff80bL),
                of(0x5a3c23e39c000000L),
                of(0x4e900abb53e6b71L),
                of(0x7600ec618141000L),
                of(0xaee5720ee830681L),
                of(0x1000000000000000L),
                of(0x172588ad4f5f0981L),
                of(0x211e44f7d02c1000L),
                of(0x2ee56725f06e5c71L),
                of(0x41c21cb8e1000000L),
            )

        /*
         * These two arrays are the integer analogue of above.
         */
        private val digitsPerInt =
            intArrayOf(
                0,
                0,
                30,
                19,
                15,
                13,
                11,
                11,
                10,
                9,
                9,
                8,
                8,
                8,
                8,
                7,
                7,
                7,
                7,
                7,
                7,
                7,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                6,
                5,
            )

        private val intRadix =
            intArrayOf(
                0,
                0,
                0x40000000,
                0x4546b3db,
                0x40000000,
                0x48c27395,
                0x159fd800,
                0x75db9c97,
                0x40000000,
                0x17179149,
                0x3b9aca00,
                0xcc6db61,
                0x19a10000,
                0x309f1021,
                0x57f6c100,
                0xa2f1b6f,
                0x10000000,
                0x18754571,
                0x247dbc80,
                0x3547667b,
                0x4c4b4000,
                0x6b5a6e1d,
                0x6c20a40,
                0x8d2d931,
                0xb640000,
                0xe8d4a51,
                0x1269ae40,
                0x17179149,
                0x1cb91000,
                0x23744899,
                0x2b73a840,
                0x34e63b41,
                0x40000000,
                0x4cfa3cc1,
                0x5c13d840,
                0x6d91b519,
                0x39aa400,
            )
    }
}
