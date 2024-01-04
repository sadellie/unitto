/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.converter

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.isEqualTo
import com.sadellie.unitto.data.common.isGreaterThan
import com.sadellie.unitto.data.common.isLessThan
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import java.math.BigDecimal
import java.math.RoundingMode

internal sealed class UnitConverterUIState {
    data object Loading : UnitConverterUIState()

    data class Default(
        val input1: TextFieldValue,
        val input2: TextFieldValue,
        val calculation: BigDecimal?,
        val result: ConverterResult,
        val unitFrom: DefaultUnit,
        val unitTo: DefaultUnit,
        val enableHaptic: Boolean,
        val middleZero: Boolean,
        val formatterSymbols: FormatterSymbols,
        val scale: Int,
        val outputFormat: Int,
        val formatTime: Boolean,
        val currencyRateUpdateState: CurrencyRateUpdateState,
        val acButton: Boolean,
    ) : UnitConverterUIState()

    data class NumberBase(
        val input: TextFieldValue,
        val result: ConverterResult,
        val unitFrom: NumberBaseUnit,
        val unitTo: NumberBaseUnit,
        val enableHaptic: Boolean,
    ) : UnitConverterUIState()
}

internal sealed class ConverterResult {
    data class Default(val value: BigDecimal) : ConverterResult() {
        override fun equals(other: Any?): Boolean {
            if (other !is Default) return false
            return this.value.isEqualTo(other.value)
        }

        override fun hashCode(): Int = value.hashCode()
    }

    data class NumberBase(val value: String) : ConverterResult()

    data class Time(
        val negative: Boolean,
        val day: BigDecimal,
        val hour: BigDecimal,
        val minute: BigDecimal,
        val second: BigDecimal,
        val millisecond: BigDecimal,
        val microsecond: BigDecimal,
        val nanosecond: BigDecimal,
        val attosecond: BigDecimal,
    ) : ConverterResult()

    data class FootInch(
        val foot: BigDecimal,
        val inch: BigDecimal,
    ) : ConverterResult()

    data object Loading : ConverterResult()

    data object Error : ConverterResult()
}

internal fun ConverterResult.Time.format(mContext: Context, formatterSymbols: FormatterSymbols): String {
    val result = mutableListOf<String>()

    if (day.isGreaterThan(BigDecimal.ZERO)) {
        result += "${day.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_day_short)}"
    }

    if (hour.isGreaterThan(BigDecimal.ZERO)) {
        result += "${hour.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_hour_short)}"
    }

    if (minute.isGreaterThan(BigDecimal.ZERO)) {
        result += "${minute.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_minute_short)}"
    }

    if (second.isGreaterThan(BigDecimal.ZERO)) {
        result += "${second.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_second_short)}"
    }

    if (millisecond.isGreaterThan(BigDecimal.ZERO)) {
        result += "${millisecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_millisecond_short)}"
    }

    if (microsecond.isGreaterThan(BigDecimal.ZERO)) {
        result += "${microsecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_microsecond_short)}"
    }

    if (nanosecond.isGreaterThan(BigDecimal.ZERO)) {
        result += "${nanosecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_nanosecond_short)}"
    }

    if (attosecond.isGreaterThan(BigDecimal.ZERO)) {
        result += "${attosecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.unit_attosecond_short)}"
    }

    return (if (negative) Token.Operator.minus else "") + result.joinToString(" ").ifEmpty { Token.Digit._0 }
}

internal fun ConverterResult.FootInch.format(
    mContext: Context,
    scale: Int,
    outputFormat: Int,
    formatterSymbols: FormatterSymbols
): String {
    var result = ""
    result += foot.format(scale, outputFormat).formatExpression(formatterSymbols)

    if (inch.isGreaterThan(BigDecimal.ZERO)) {
        result += mContext.getString(R.string.unit_foot_short)
        result += " "
        result += inch.format(scale, outputFormat).formatExpression(formatterSymbols)
        result += mContext.getString(R.string.unit_inch_short)
    }

    return result
}

internal fun formatTime(
    input: BigDecimal,
): ConverterResult.Time {
    val negative = input < BigDecimal.ZERO
    val inputAbs = input.abs()

    if (inputAbs.isLessThan(attosecondBasicUnit)) return ConverterResult.Time(
        negative = negative,
        day = BigDecimal.ZERO,
        hour = BigDecimal.ZERO,
        minute = BigDecimal.ZERO,
        second = BigDecimal.ZERO,
        millisecond = BigDecimal.ZERO,
        microsecond = BigDecimal.ZERO,
        nanosecond = BigDecimal.ZERO,
        attosecond = inputAbs
    )

    if (inputAbs.isLessThan(nanosecondBasicUnit)) return ConverterResult.Time(
        negative = negative,
        day = BigDecimal.ZERO,
        hour = BigDecimal.ZERO,
        minute = BigDecimal.ZERO,
        second = BigDecimal.ZERO,
        millisecond = BigDecimal.ZERO,
        microsecond = BigDecimal.ZERO,
        nanosecond = BigDecimal.ZERO,
        attosecond = inputAbs.trimZeros()
    )

    // DAY
    var division = inputAbs.divideAndRemainder(dayBasicUnit)
    val day = division.component1().setScale(0, RoundingMode.HALF_EVEN)
    var remainingSeconds = division.component2().setScale(0, RoundingMode.HALF_EVEN)

    division = remainingSeconds.divideAndRemainder(hourBasicUnit)
    val hour = division.component1()
    remainingSeconds = division.component2()

    division = remainingSeconds.divideAndRemainder(minuteBasicUnit)
    val minute = division.component1()
    remainingSeconds = division.component2()

    division = remainingSeconds.divideAndRemainder(secondBasicUnit)
    val second = division.component1()
    remainingSeconds = division.component2()

    division = remainingSeconds.divideAndRemainder(millisecondBasicUnit)
    val millisecond = division.component1()
    remainingSeconds = division.component2()

    division = remainingSeconds.divideAndRemainder(microsecondBasicUnit)
    val microsecond = division.component1()
    remainingSeconds = division.component2()

    division = remainingSeconds.divideAndRemainder(nanosecondBasicUnit)
    val nanosecond = division.component1()
    remainingSeconds = division.component2()

    val attosecond = remainingSeconds

    return ConverterResult.Time(
        negative = negative,
        day = day,
        hour = hour,
        minute = minute,
        second = second,
        millisecond = millisecond,
        microsecond = microsecond,
        nanosecond = nanosecond,
        attosecond = attosecond
    )
}

/**
 * Creates an object for displaying formatted foot and inch output. Units are passed as objects so
 * that changes in basic units don't require modifying the method. Also this method can't access
 * units repository directly.
 *
 * @param input Input in feet.
 * @param footUnit Foot unit [DefaultUnit].
 * @param inchUnit Inch unit [DefaultUnit].
 * @return Result where decimal places are converter into inches.
 */
internal fun formatFootInch(
    input: BigDecimal,
    footUnit: DefaultUnit,
    inchUnit: DefaultUnit
): ConverterResult.FootInch {
    val (integral, fractional) = input.divideAndRemainder(BigDecimal.ONE)

    return ConverterResult.FootInch(integral, footUnit.convert(inchUnit, fractional))
}

private val dayBasicUnit by lazy { BigDecimal("86400000000000000000000") }
private val hourBasicUnit by lazy { BigDecimal("3600000000000000000000") }
private val minuteBasicUnit by lazy { BigDecimal("60000000000000000000") }
private val secondBasicUnit by lazy { BigDecimal("1000000000000000000") }
private val millisecondBasicUnit by lazy { BigDecimal("1000000000000000") }
private val microsecondBasicUnit by lazy { BigDecimal("1000000000000") }
private val nanosecondBasicUnit by lazy { BigDecimal("1000000000") }
private val attosecondBasicUnit by lazy { BigDecimal("1") }
