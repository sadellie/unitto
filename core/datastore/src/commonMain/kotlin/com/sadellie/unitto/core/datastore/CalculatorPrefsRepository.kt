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

import androidx.datastore.preferences.core.booleanPreferencesKey
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
class CalculatorPrefsRepository(private val source: UserPrefDataSource) {
  val prefs: Flow<CalculatorPreferences> =
    source.rawData.map {
      CalculatorPreferences(
        radianMode = it[radianModeKey] ?: CalculatorPreferences.DEFAULT_RADIAN_MODE,
        inverseMode = it[inverseModeKey] ?: CalculatorPreferences.DEFAULT_INVERSE_MODE,
        additionalButtons =
          it[additionalButtonsKey] ?: CalculatorPreferences.DEFAULT_ADDITIONAL_BUTTONS,
        initialPartialHistoryView =
          it[initialPartialHistoryViewKey]
            ?: CalculatorPreferences.DEFAULT_INITIAL_PARTIAL_HISTORY_VIEW,
        constantCalculation =
          it[constantCalculationKey] ?: CalculatorPreferences.DEFAULT_CONSTANT_CALCULATION,
        fractionalOutput =
          it[fractionalOutputKey] ?: CalculatorPreferences.DEFAULT_FRACTIONAL_OUTPUT,
      )
    }

  suspend fun updateRadianMode(radianMode: Boolean) = source.edit {
    it[radianModeKey] = radianMode
  }

  suspend fun updateAdditionalButtons(additionalButtons: Boolean) = source.edit {
    it[additionalButtonsKey] = additionalButtons
  }

  suspend fun updateInverseMode(inverseMode: Boolean) = source.edit {
    it[inverseModeKey] = inverseMode
  }

  suspend fun updateInitialPartialHistoryView(initialPartialHistoryView: Boolean) = source.edit {
    it[initialPartialHistoryViewKey] = initialPartialHistoryView
  }

  suspend fun updateFractionalOutput(enabled: Boolean) = source.edit { preferences ->
    preferences[fractionalOutputKey] = enabled
  }

  suspend fun updateConstantCalculation(enabled: Boolean) = source.edit { preferences ->
    preferences[constantCalculationKey] = enabled
  }

  private val radianModeKey = booleanPreferencesKey(PrefKeys.RADIAN_MODE_PREF_KEY)
  private val fractionalOutputKey = booleanPreferencesKey(PrefKeys.FRACTIONAL_OUTPUT_PREF_KEY)
  private val additionalButtonsKey = booleanPreferencesKey(PrefKeys.ADDITIONAL_BUTTONS_PREF_KEY)
  private val inverseModeKey = booleanPreferencesKey(PrefKeys.INVERSE_MODE_PREF_KEY)
  private val constantCalculationKey = booleanPreferencesKey(PrefKeys.CONSTANT_CALCULATION_PREF_KEY)
  private val initialPartialHistoryViewKey =
    booleanPreferencesKey(PrefKeys.INITIAL_PARTIAL_HISTORY_VIEW_PREF_KEY)
}

data class CalculatorPreferences(
  val radianMode: Boolean,
  val inverseMode: Boolean,
  val additionalButtons: Boolean,
  val initialPartialHistoryView: Boolean,
  val constantCalculation: Boolean,
  val fractionalOutput: Boolean,
) {
  internal companion object Defaults {
    const val DEFAULT_RADIAN_MODE: Boolean = true
    const val DEFAULT_INVERSE_MODE: Boolean = false
    const val DEFAULT_ADDITIONAL_BUTTONS: Boolean = false
    const val DEFAULT_INITIAL_PARTIAL_HISTORY_VIEW: Boolean = false
    const val DEFAULT_CONSTANT_CALCULATION: Boolean = false
    const val DEFAULT_FRACTIONAL_OUTPUT: Boolean = true
  }
}
