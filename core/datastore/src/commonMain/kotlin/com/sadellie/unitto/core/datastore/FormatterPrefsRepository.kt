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

package com.sadellie.unitto.core.datastore

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.map

@Inject
class FormatterPrefsRepository(private val source: UserPrefDataSource) {
  val prefs =
    source.rawData.map {
      val grouping =
        it[DatastorePrefKeys.FORMATTER_GROUPING]
          ?: FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_GROUPING.symbol
      val fractional =
        it[DatastorePrefKeys.FORMATTER_FRACTIONAL]
          ?: FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_FRACTIONAL.symbol
      val indian =
        it[DatastorePrefKeys.FORMATTER_INDIAN]
          ?: FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_INDIAN
      FormatterPreferences(
        formatterSymbols = produceFormatterSymbols(grouping, fractional, indian),
        digitsPrecision =
          it[DatastorePrefKeys.DIGITS_PRECISION] ?: FormatterPreferences.DEFAULT_DIGITS_PRECISION,
        outputFormat =
          it[DatastorePrefKeys.OUTPUT_FORMAT] ?: FormatterPreferences.DEFAULT_OUTPUT_FORMAT,
      )
    }

  suspend fun updateDigitsPrecision(precision: Int) = source.edit { preferences ->
    preferences[DatastorePrefKeys.DIGITS_PRECISION] = precision
  }

  suspend fun updateFormatterSymbols(
    grouping: Token.Formatter,
    fractional: Token.Formatter,
    indian: Boolean,
  ) {
    // Grouping and fractional symbols are always different
    if (grouping == fractional) return
    source.edit { preferences ->
      preferences[DatastorePrefKeys.FORMATTER_GROUPING] = grouping.symbol
      preferences[DatastorePrefKeys.FORMATTER_FRACTIONAL] = fractional.symbol
      preferences[DatastorePrefKeys.FORMATTER_INDIAN] = indian
    }
  }

  suspend fun updateOutputFormat(outputFormat: Int) = source.edit { preferences ->
    preferences[DatastorePrefKeys.OUTPUT_FORMAT] = outputFormat
  }
}

data class FormatterPreferences(
  val formatterSymbols: FormatterSymbols,
  val digitsPrecision: Int,
  val outputFormat: Int,
) {
  internal companion object Defaults {
    val DEFAULT_FORMATTER_SYMBOLS_GROUPING: Token.Formatter = Token.Space

    val DEFAULT_FORMATTER_SYMBOLS_FRACTIONAL: Token.Formatter = Token.Period

    const val DEFAULT_FORMATTER_SYMBOLS_INDIAN: Boolean = false

    const val DEFAULT_DIGITS_PRECISION: Int = 3

    const val DEFAULT_OUTPUT_FORMAT: Int = OutputFormat.PLAIN
  }
}

internal fun produceFormatterSymbols(grouping: String?, fractional: String?, indian: Boolean?) =
  if (grouping == null || fractional == null) {
    FormatterSymbols(
      FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_GROUPING,
      FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_FRACTIONAL,
      indian ?: FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_INDIAN,
    )
  } else {
    FormatterSymbols(
      Token.Formatter.from(grouping),
      Token.Formatter.from(fractional),
      indian ?: FormatterPreferences.DEFAULT_FORMATTER_SYMBOLS_INDIAN,
    )
  }
