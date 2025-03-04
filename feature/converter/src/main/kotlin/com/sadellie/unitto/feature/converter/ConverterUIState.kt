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
import androidx.annotation.StringRes
import androidx.compose.foundation.text.input.TextFieldState
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isGreaterThan
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.converter.ConverterResult
import com.sadellie.unitto.core.data.converter.CurrencyRateUpdateState
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.ui.textfield.formatExpression
import java.math.BigDecimal

internal sealed class ConverterUIState {
  data object Loading : ConverterUIState()

  data class Default(
    val input1: TextFieldState,
    val input2: TextFieldState,
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
  ) : ConverterUIState()

  data class NumberBase(
    val input: TextFieldState,
    val result: ConverterResult,
    val unitFrom: BasicUnit.NumberBase,
    val unitTo: BasicUnit.NumberBase,
  ) : ConverterUIState()
}

internal fun ConverterResult.Time.format(
  mContext: Context,
  formatterSymbols: FormatterSymbols,
): String {
  val result = mutableListOf<String>()

  if (day.isGreaterThan(BigDecimal.ZERO)) {
    val value = day.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_day_short)
    result += "$value$label"
  }

  if (hour.isGreaterThan(BigDecimal.ZERO)) {
    val value = hour.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_hour_short)
    result += "$value$label"
  }

  if (minute.isGreaterThan(BigDecimal.ZERO)) {
    val value = minute.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_minute_short)
    result += "$value$label"
  }

  if (second.isGreaterThan(BigDecimal.ZERO)) {
    val value = second.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_second_short)
    result += "$value$label"
  }

  if (millisecond.isGreaterThan(BigDecimal.ZERO)) {
    val value = millisecond.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_millisecond_short)
    result += "$value$label"
  }

  if (microsecond.isGreaterThan(BigDecimal.ZERO)) {
    val value = microsecond.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_microsecond_short)
    result += "$value$label"
  }

  if (nanosecond.isGreaterThan(BigDecimal.ZERO)) {
    val value = nanosecond.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_nanosecond_short)
    result += "$value$label"
  }

  if (attosecond.isGreaterThan(BigDecimal.ZERO)) {
    val value = attosecond.toPlainString().formatExpression(formatterSymbols)
    val label = mContext.getString(R.string.unit_attosecond_short)
    result += "$value$label"
  }

  if (result.isEmpty()) return Token.Digit.DIGIT_0
  val resultString = result.joinToString(" ")

  return if (negative) "${Token.Operator.MINUS}$resultString" else resultString
}

internal fun ConverterResult.FootInch.format(
  mContext: Context,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
): String {
  return formatConverterResultSplit(
    mContext = mContext,
    scale = scale,
    outputFormat = outputFormat,
    formatterSymbols = formatterSymbols,
    output1 = foot,
    output1Res = R.string.unit_foot_short,
    output2 = inch,
    output2Res = R.string.unit_inch_short,
  )
}

internal fun ConverterResult.PoundOunce.format(
  mContext: Context,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
): String {
  return formatConverterResultSplit(
    mContext = mContext,
    scale = scale,
    outputFormat = outputFormat,
    formatterSymbols = formatterSymbols,
    output1 = pound,
    output1Res = R.string.unit_pound_short,
    output2 = ounce,
    output2Res = R.string.unit_ounce_short,
  )
}

private fun formatConverterResultSplit(
  mContext: Context,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
  output1: BigDecimal,
  @StringRes output1Res: Int,
  output2: BigDecimal,
  @StringRes output2Res: Int,
): String {
  var result = ""
  result += output1.toFormattedString(scale, outputFormat).formatExpression(formatterSymbols)

  if (!output2.isEqualTo(BigDecimal.ZERO)) {
    result += mContext.getString(output1Res)
    result += " "
    result += output2.toFormattedString(scale, outputFormat).formatExpression(formatterSymbols)
    result += mContext.getString(output2Res)
  }

  return result
}
