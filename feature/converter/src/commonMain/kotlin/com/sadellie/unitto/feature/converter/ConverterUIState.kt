/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import androidx.compose.foundation.text.input.TextFieldState
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isGreaterThan
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.converter.ConverterResult
import com.sadellie.unitto.core.data.converter.CurrencyRateUpdateState
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.ui.textfield.formatExpression

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
  formatterSymbols: FormatterSymbols,
  dayLabel: String,
  hourLabel: String,
  minuteLabel: String,
  secondLabel: String,
  millisecondLabel: String,
  microsecondLabel: String,
  nanosecondLabel: String,
  attosecondLabel: String,
): String {
  val result = mutableListOf<String>()

  if (day.isGreaterThan(KBigDecimal.ZERO)) {
    val value = day.toPlainString().formatExpression(formatterSymbols)
    result += "$value$dayLabel"
  }

  if (hour.isGreaterThan(KBigDecimal.ZERO)) {
    val value = hour.toPlainString().formatExpression(formatterSymbols)
    result += "$value$hourLabel"
  }

  if (minute.isGreaterThan(KBigDecimal.ZERO)) {
    val value = minute.toPlainString().formatExpression(formatterSymbols)
    result += "$value$minuteLabel"
  }

  if (second.isGreaterThan(KBigDecimal.ZERO)) {
    val value = second.toPlainString().formatExpression(formatterSymbols)
    result += "$value$secondLabel"
  }

  if (millisecond.isGreaterThan(KBigDecimal.ZERO)) {
    val value = millisecond.toPlainString().formatExpression(formatterSymbols)
    result += "$value$millisecondLabel"
  }

  if (microsecond.isGreaterThan(KBigDecimal.ZERO)) {
    val value = microsecond.toPlainString().formatExpression(formatterSymbols)
    result += "$value$microsecondLabel"
  }

  if (nanosecond.isGreaterThan(KBigDecimal.ZERO)) {
    val value = nanosecond.toPlainString().formatExpression(formatterSymbols)
    result += "$value$nanosecondLabel"
  }

  if (attosecond.isGreaterThan(KBigDecimal.ZERO)) {
    val value = attosecond.toPlainString().formatExpression(formatterSymbols)
    result += "$value$attosecondLabel"
  }

  if (result.isEmpty()) return Token.Digit.DIGIT_0
  val resultString = result.joinToString(" ")

  return if (negative) "${Token.Operator.MINUS}$resultString" else resultString
}

internal fun ConverterResult.FootInch.format(
  footLabel: String,
  inchLabel: String,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
): String {
  return formatConverterResultSplit(
    scale = scale,
    outputFormat = outputFormat,
    formatterSymbols = formatterSymbols,
    output1 = foot,
    output1Label = footLabel,
    output2 = inch,
    output2Label = inchLabel,
  )
}

internal fun ConverterResult.PoundOunce.format(
  poundLabel: String,
  ounceLabel: String,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
): String {
  return formatConverterResultSplit(
    scale = scale,
    outputFormat = outputFormat,
    formatterSymbols = formatterSymbols,
    output1 = pound,
    output1Label = poundLabel,
    output2 = ounce,
    output2Label = ounceLabel,
  )
}

private fun formatConverterResultSplit(
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
  output1: KBigDecimal,
  output1Label: String,
  output2: KBigDecimal,
  output2Label: String,
): String {
  var result = ""
  result += output1.toFormattedString(scale, outputFormat).formatExpression(formatterSymbols)

  if (!output2.isEqualTo(KBigDecimal.ZERO)) {
    result += output1Label
    result += " "
    result += output2.toFormattedString(scale, outputFormat).formatExpression(formatterSymbols)
    result += output2Label
  }

  return result
}
