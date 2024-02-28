/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.isGreaterThan
import com.sadellie.unitto.data.converter.ConverterResult
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import java.math.BigDecimal

internal sealed class UnitConverterUIState {
    data object Loading : UnitConverterUIState()

    data class Default(
        val input1: TextFieldValue,
        val input2: TextFieldValue,
        val result: ConverterResult,
        val unitFrom: BasicUnit.Default,
        val unitTo: BasicUnit.Default,
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
        val unitFrom: BasicUnit.NumberBase,
        val unitTo: BasicUnit.NumberBase,
    ) : UnitConverterUIState()
}

internal fun ConverterResult.Time.format(
    mContext: Context,
    formatterSymbols: FormatterSymbols,
): String {
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
    formatterSymbols: FormatterSymbols,
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
