/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.sadellie.evaluatto.programmer

import com.sadellie.unitto.core.common.KBigInteger

/**
 * @property modulus Max value for unsigned `KBigInteger.ONE.shl([bitWidth])`
 * @property half Positive/negative boundary `KBigInteger.ONE.shl([bitWidth] - 1)`
 */
enum class DataUnit(val bitWidth: Int, val modulus: KBigInteger, val half: KBigInteger) {
  QWORD(64, KBigInteger("18446744073709551616"), KBigInteger("9223372036854775808")),
  WORD(16, KBigInteger("65536"), KBigInteger("32768")),
  BYTE(8, KBigInteger("256"), KBigInteger("128")),
}

data class BaseNumber(val value: KBigInteger, private val dataUnit: DataUnit) {

  /**
   * Secondary constructor: parses a signed string in the given base, then constrains the result to
   * the specified data unit.
   */
  constructor(
    symbolic: String,
    base: Int,
    dataUnit: DataUnit,
  ) : this(parseWithWidth(symbolic, base, dataUnit), dataUnit)

  private companion object {
    private fun parseWithWidth(symbolic: String, base: Int, dataUnit: DataUnit): KBigInteger {
      val parsed = KBigInteger(symbolic, base)
      return parsed.toggleSignedUnsigned(dataUnit)
    }

    fun KBigInteger.toggleSignedUnsigned(dataUnit: DataUnit): KBigInteger {
      // Fixed width: wrap to [0, 2^width) and reinterpret as signed
      val masked = this.mod(dataUnit.modulus)
      return if (masked >= dataUnit.half) masked - dataUnit.modulus else masked
    }
  }

  /**
   * Returns a string representation of the number in the requested radix. The output corresponds to
   * the **unsigned** bit pattern (no minus sign)
   */
  fun toString(radix: Int): String {
    // Convert to unsigned value (bit pattern)
    val unsigned = if (value < KBigInteger.ZERO) value + dataUnit.modulus else value
    return unsigned.toString(radix).uppercase()
  }

  /** Two's complement negation within the fixed width (if any) */
  fun negate(): BaseNumber {
    val neg = value.negate()
    val newValue = neg.toggleSignedUnsigned(dataUnit)
    return BaseNumber(newValue, dataUnit)
  }

  operator fun plus(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val sum = value.plus(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(sum, dataUnit)
  }

  operator fun times(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.multiply(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  operator fun div(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.div(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun or(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.or(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun and(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.and(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun not(): BaseNumber {
    val result = value.not().toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun nand(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val aUnsigned = value.toggleSignedUnsigned(dataUnit)
    val bUnsigned = other.value.toggleSignedUnsigned(dataUnit)
    val nandUnsigned = (aUnsigned.and(bUnsigned)).not().and(dataUnit.modulus - KBigInteger.ONE)
    val signedResult = nandUnsigned.toggleSignedUnsigned(dataUnit)
    return BaseNumber(signedResult, dataUnit)
  }

  fun xor(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.xor(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun nor(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.nor(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun lsh(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.shl(other.value.intValueExact()).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun rsh(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    val result = value.shr(other.value.intValueExact()).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }

  fun mod(other: BaseNumber): BaseNumber {
    require(dataUnit == other.dataUnit) { "Different data units" }
    require(other.value != KBigInteger.ZERO) { "Modulo by zero" }
    val result = value.remainder(other.value).toggleSignedUnsigned(dataUnit)
    return BaseNumber(result, dataUnit)
  }
}
