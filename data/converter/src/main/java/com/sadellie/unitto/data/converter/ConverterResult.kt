/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.data.converter

import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import java.math.BigDecimal

sealed class ConverterResult {

    sealed class Error : ConverterResult() {
        data object Currency : Error()

        data object BadInput : Error()

        data object ConversionError : Error()

        data object DivideByZero : Error()
    }

    data class Default(
        val value: BigDecimal,
        val calculation: BigDecimal,
    ) : ConverterResult()

    data class NumberBase(val value: String) : ConverterResult()

    data class Time(
        val negative: Boolean = false,
        val day: BigDecimal = BigDecimal.ZERO,
        val hour: BigDecimal = BigDecimal.ZERO,
        val minute: BigDecimal = BigDecimal.ZERO,
        val second: BigDecimal = BigDecimal.ZERO,
        val millisecond: BigDecimal = BigDecimal.ZERO,
        val microsecond: BigDecimal = BigDecimal.ZERO,
        val nanosecond: BigDecimal = BigDecimal.ZERO,
        val attosecond: BigDecimal = BigDecimal.ZERO,
    ) : ConverterResult()

    data class FootInch(
        val foot: BigDecimal,
        val inch: BigDecimal,
    ) : ConverterResult() {
        companion object {
            /**
             * Creates an object for displaying formatted foot and inch output. Units are passed as objects so
             * that changes in basic units don't require modifying the method. Also this method can't access
             * units repository directly.
             *
             * @param input Input in feet.
             * @param footUnit Foot unit [BasicUnit.Default].
             * @param inchUnit Inch unit [BasicUnit.Default].
             * @return Result where decimal places are converter into inches.
             */
            fun fromBigDecimal(
                input: BigDecimal,
                footUnit: BasicUnit.Default,
                inchUnit: BasicUnit.Default,
            ): FootInch {
                val (integral, fractional) = input.divideAndRemainder(BigDecimal.ONE)
                return FootInch(integral, footUnit.convert(inchUnit, fractional))
            }
        }
    }

    data object Loading : ConverterResult()
}
