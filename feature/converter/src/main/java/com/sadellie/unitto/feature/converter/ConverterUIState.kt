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
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import java.math.BigDecimal
import java.math.RoundingMode

internal sealed class UnitConverterUIState {
    data object Loading : UnitConverterUIState()

    data class Default(
        val input: TextFieldValue = TextFieldValue(),
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
    ) : UnitConverterUIState()

    data class NumberBase(
        val input: TextFieldValue = TextFieldValue(),
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
            return this.value.compareTo(other.value) == 0
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

    data object Loading : ConverterResult()

    data object Error : ConverterResult()
}

internal fun ConverterResult.Time.format(mContext: Context, formatterSymbols: FormatterSymbols): String {
    val result = mutableListOf<String>()

    if (day.compareTo(BigDecimal.ZERO) == 1) {
        result += "${day.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.day_short)}"
    }

    if (hour.compareTo(BigDecimal.ZERO) == 1) {
        result += "${hour.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.hour_short)}"
    }

    if (minute.compareTo(BigDecimal.ZERO) == 1) {
        result += "${minute.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.minute_short)}"
    }

    if (second.compareTo(BigDecimal.ZERO) == 1) {
        result += "${second.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.second_short)}"
    }

    if (millisecond.compareTo(BigDecimal.ZERO) == 1) {
        result += "${millisecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.millisecond_short)}"
    }

    if (microsecond.compareTo(BigDecimal.ZERO) == 1) {
        result += "${microsecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.microsecond_short)}"
    }

    if (nanosecond.compareTo(BigDecimal.ZERO) == 1) {
        result += "${nanosecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.nanosecond_short)}"
    }

    if (attosecond.compareTo(BigDecimal.ZERO) == 1) {
        result += "${attosecond.toPlainString().formatExpression(formatterSymbols)}${mContext.getString(R.string.attosecond_short)}"
    }

    return (if (negative) Token.Operator.minus else "") + result.joinToString(" ").ifEmpty { Token.Digit._0 }
}

internal fun formatTime(
    input: BigDecimal,
): ConverterResult.Time {
    val negative = input < BigDecimal.ZERO
    val inputAbs = input.abs()

    if (inputAbs.compareTo(attosecondBasicUnit) == -1) return ConverterResult.Time(
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

    if (inputAbs.compareTo(nanosecondBasicUnit) == -1) return ConverterResult.Time(
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

private val dayBasicUnit by lazy { BigDecimal("86400000000000000000000") }
private val hourBasicUnit by lazy { BigDecimal("3600000000000000000000") }
private val minuteBasicUnit by lazy { BigDecimal("60000000000000000000") }
private val secondBasicUnit by lazy { BigDecimal("1000000000000000000") }
private val millisecondBasicUnit by lazy { BigDecimal("1000000000000000") }
private val microsecondBasicUnit by lazy { BigDecimal("1000000000000") }
private val nanosecondBasicUnit by lazy { BigDecimal("1000000000") }
private val attosecondBasicUnit by lazy { BigDecimal("1") }
