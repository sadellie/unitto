/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter

import com.sadellie.unitto.core.common.KBigDecimal

sealed class ConverterResult {

  sealed class Error : ConverterResult() {
    data object CurrencyError : Error()

    data object DivideByZeroError : Error()
  }

  data class Default(val value: KBigDecimal, val calculation: KBigDecimal) : ConverterResult()

  data class NumberBase(val value: String) : ConverterResult()

  data class Time(
    val negative: Boolean = false,
    val day: KBigDecimal = KBigDecimal.ZERO,
    val hour: KBigDecimal = KBigDecimal.ZERO,
    val minute: KBigDecimal = KBigDecimal.ZERO,
    val second: KBigDecimal = KBigDecimal.ZERO,
    val millisecond: KBigDecimal = KBigDecimal.ZERO,
    val microsecond: KBigDecimal = KBigDecimal.ZERO,
    val nanosecond: KBigDecimal = KBigDecimal.ZERO,
    val attosecond: KBigDecimal = KBigDecimal.ZERO,
  ) : ConverterResult()

  data class FootInch(val foot: KBigDecimal, val inch: KBigDecimal) : ConverterResult()

  data class PoundOunce(val pound: KBigDecimal, val ounce: KBigDecimal) : ConverterResult()

  data object Loading : ConverterResult()
}
