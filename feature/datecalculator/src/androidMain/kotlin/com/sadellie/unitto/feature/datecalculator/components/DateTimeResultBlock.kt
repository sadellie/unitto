/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.datecalculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.ui.PagedIsland
import com.sadellie.unitto.core.ui.textfield.formatExpression
import com.sadellie.unitto.feature.datecalculator.difference.ZonedDateTimeDifference
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.date_calculator_days
import unitto.core.common.generated.resources.date_calculator_difference
import unitto.core.common.generated.resources.date_calculator_hours
import unitto.core.common.generated.resources.date_calculator_minutes
import unitto.core.common.generated.resources.date_calculator_months
import unitto.core.common.generated.resources.date_calculator_years

@Composable
internal fun DateTimeResultBlock(
  modifier: Modifier = Modifier,
  diff: ZonedDateTimeDifference.Default,
  precision: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
) {
  val focusManager = LocalFocusManager.current

  PagedIsland(
    modifier = modifier,
    pageCount = 6,
    onClick = { focusManager.clearFocus() },
    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
  ) { currentPage ->
    when (currentPage) {
      0 ->
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = stringResource(Res.string.date_calculator_difference),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer {
            Column {
              if (diff.years > 0) {
                PartialDateText(
                  Res.string.date_calculator_years,
                  diff.years,
                  precision,
                  outputFormat,
                  formatterSymbols,
                )
              }

              if (diff.months > 0) {
                PartialDateText(
                  Res.string.date_calculator_months,
                  diff.months,
                  precision,
                  outputFormat,
                  formatterSymbols,
                )
              }

              if (diff.days > 0) {
                PartialDateText(
                  Res.string.date_calculator_days,
                  diff.days,
                  precision,
                  outputFormat,
                  formatterSymbols,
                )
              }

              if (diff.hours > 0) {
                PartialDateText(
                  Res.string.date_calculator_hours,
                  diff.hours,
                  precision,
                  outputFormat,
                  formatterSymbols,
                )
              }

              if (diff.minutes > 0) {
                PartialDateText(
                  Res.string.date_calculator_minutes,
                  diff.minutes,
                  precision,
                  outputFormat,
                  formatterSymbols,
                )
              }
            }
          }
        }

      1 ->
        SingleDateText(
          Res.string.date_calculator_years,
          diff.sumYears,
          precision,
          outputFormat,
          formatterSymbols,
        )
      2 ->
        SingleDateText(
          Res.string.date_calculator_months,
          diff.sumMonths,
          precision,
          outputFormat,
          formatterSymbols,
        )
      3 ->
        SingleDateText(
          Res.string.date_calculator_days,
          diff.sumDays,
          precision,
          outputFormat,
          formatterSymbols,
        )
      4 ->
        SingleDateText(
          Res.string.date_calculator_hours,
          diff.sumHours,
          precision,
          outputFormat,
          formatterSymbols,
        )
      5 ->
        SingleDateText(
          Res.string.date_calculator_minutes,
          diff.sumMinutes,
          precision,
          outputFormat,
          formatterSymbols,
        )
    }
  }
}

@Composable
private fun PartialDateText(
  text: StringResource,
  value: Long,
  precision: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
) {
  val formattedValue =
    remember(value, precision, outputFormat, formatterSymbols) {
      formatDateValue(KBigDecimal.valueOf(value), precision, outputFormat, formatterSymbols)
    }
  Text(
    text = "${stringResource(text)}: $formattedValue",
    style = MaterialTheme.typography.displaySmall,
  )
}

@Composable
private fun SingleDateText(
  headerText: StringResource,
  value: KBigDecimal,
  precision: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
) {
  Column {
    Text(text = stringResource(headerText), style = MaterialTheme.typography.labelMedium)
    SelectionContainer {
      val formattedValue =
        remember(value, precision, outputFormat, formatterSymbols) {
          formatDateValue(value, precision, outputFormat, formatterSymbols)
        }
      Text(text = formattedValue, style = MaterialTheme.typography.displaySmall)
    }
  }
}

private fun formatDateValue(
  value: KBigDecimal,
  precision: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
): String = value.toFormattedString(precision, outputFormat).formatExpression(formatterSymbols)

@Preview
@Composable
private fun DateTimeResultBlockPreview() {
  DateTimeResultBlock(
    diff =
      ZonedDateTimeDifference.Default(
        years = 0,
        months = 1,
        days = 1,
        hours = 0,
        minutes = 0,
        sumYears = KBigDecimal.ZERO,
        sumMonths = KBigDecimal.ZERO,
        sumDays = KBigDecimal.ZERO,
        sumHours = KBigDecimal.ZERO,
        sumMinutes = KBigDecimal("46080"),
      ),
    precision = 3,
    outputFormat = OutputFormat.PLAIN,
    formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
  )
}
