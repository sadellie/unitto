/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.ui.PagedIsland
import com.sadellie.unitto.feature.datecalculator.difference.ZonedDateTimeDifference
import java.math.BigDecimal

@Composable
internal fun DateTimeResultBlock(
    modifier: Modifier = Modifier,
    diff: ZonedDateTimeDifference.Default,
    format: (BigDecimal) -> String,
) {
  val focusManager = LocalFocusManager.current

  PagedIsland(
    modifier = modifier,
    pageCount = 6,
    onClick = { focusManager.clearFocus() },
    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
  ) { currentPage ->
    when (currentPage) {
      0 -> {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = stringResource(R.string.date_calculator_difference),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer {
            Column {
              // Years
              if (diff.years > 0) {
                DateText(R.string.date_calculator_years, diff.years.toBigDecimal(), format)
              }

              // Months
              if (diff.months > 0) {
                DateText(R.string.date_calculator_months, diff.months.toBigDecimal(), format)
              }

              // Days
              if (diff.days > 0) {
                DateText(R.string.date_calculator_days, diff.days.toBigDecimal(), format)
              }

              // Hours
              if (diff.hours > 0) {
                DateText(R.string.date_calculator_hours, diff.hours.toBigDecimal(), format)
              }

              // Minutes
              if (diff.minutes > 0) {
                DateText(R.string.date_calculator_minutes, diff.minutes.toBigDecimal(), format)
              }
            }
          }
        }
      }

      1 -> {
        Column {
          Text(
            text = stringResource(R.string.date_calculator_years),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer { DateText(diff.sumYears, format) }
        }
      }

      2 -> {
        Column {
          Text(
            text = stringResource(R.string.date_calculator_months),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer { DateText(diff.sumMonths, format) }
        }
      }

      3 -> {
        Column {
          Text(
            text = stringResource(R.string.date_calculator_days),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer { DateText(diff.sumDays, format) }
        }
      }

      4 -> {
        Column {
          Text(
            text = stringResource(R.string.date_calculator_hours),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer { DateText(diff.sumHours, format) }
        }
      }

      5 -> {
        Column {
          Text(
            text = stringResource(R.string.date_calculator_minutes),
            style = MaterialTheme.typography.labelMedium,
          )
          SelectionContainer { DateText(diff.sumMinutes, format) }
        }
      }
    }
  }
}

@Composable
private fun DateText(@StringRes id: Int, value: BigDecimal, format: (BigDecimal) -> String) =
  Text(
    text = "${stringResource(id)}: ${format(value)}",
    style = MaterialTheme.typography.displaySmall,
  )

@Composable
private fun DateText(value: BigDecimal, format: (BigDecimal) -> String) =
  Text(text = format(value), style = MaterialTheme.typography.displaySmall)

@Preview
@Composable
private fun DateTimeResultBlockPreview() {
  DateTimeResultBlock(
    modifier = Modifier,
    diff =
      ZonedDateTimeDifference.Default(
        years = 0,
        months = 1,
        days = 1,
        hours = 0,
        minutes = 0,
        sumYears = BigDecimal.ZERO,
        sumMonths = BigDecimal.ZERO,
        sumDays = BigDecimal.ZERO,
        sumHours = BigDecimal.ZERO,
        sumMinutes = BigDecimal("46080"),
      ),
    format = { it.toPlainString() },
  )
}
