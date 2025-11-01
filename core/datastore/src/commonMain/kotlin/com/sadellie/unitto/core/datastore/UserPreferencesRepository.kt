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

package com.sadellie.unitto.core.datastore

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.navigation.CalculatorGraphRoute
import com.sadellie.unitto.core.navigation.Route
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.flow.Flow

// TODO refactor and merge implementations (this is hell)
interface UserPreferencesRepository {
  val appPrefs: Flow<AppPreferences>
  val generalPrefs: Flow<GeneralPreferences>
  val calculatorPrefs: Flow<CalculatorPreferences>
  val converterPrefs: Flow<ConverterPreferences>
  val displayPrefs: Flow<DisplayPreferences>
  val formattingPrefs: Flow<FormattingPreferences>
  val unitGroupsPrefs: Flow<UnitGroupsPreferences>
  val addSubtractPrefs: Flow<AddSubtractPreferences>
  val bodyMassPrefs: Flow<BodyMassPreferences>
  val aboutPrefs: Flow<AboutPreferences>
  val startingScreenPrefs: Flow<StartingScreenPreferences>

  companion object Defaults {
    val enableDynamicTheme: Boolean
      get() = true

    val themingMode: ThemingMode
      get() = ThemingMode.AUTO

    val enableAmoledTheme: Boolean
      get() = false

    val customColor: Long
      get() = 16L

    val monetMode: MonetMode
      get() = MonetMode.TonalSpot

    val startingScreen: Route
      get() = CalculatorGraphRoute

    val enableToolsExperiment: Boolean
      get() = false

    val lastReadChangelog: String
      get() = ""

    val enableVibrations: Boolean
      get() = true

    val enableKeepScreenOn: Boolean
      get() = false

    val radianMode: Boolean
      get() = true

    val formatterSymbols: FormatterSymbols
      get() = FormatterSymbols(Token.SPACE, Token.PERIOD)

    val middleZero: Boolean
      get() = true

    val partialHistoryView: Boolean
      get() = true

    val steppedPartialHistoryView: Boolean
      get() = true

    val initialPartialHistoryView: Boolean
      get() = false

    val openHistoryViewButton: Boolean
      get() = false

    val digitsPrecision: Int
      get() = 3

    val outputFormat: Int
      get() = OutputFormat.PLAIN

    val unitConverterFormatTime: Boolean
      get() = false

    val unitConverterSorting: UnitsListSorting
      get() = UnitsListSorting.USAGE

    val shownUnitGroups: List<UnitGroup>
      get() = UnitGroup.entries

    val unitConverterFavoritesOnly: Boolean
      get() = false

    val latestLeftSide: String
      get() = UnitID.kilometer

    val latestRightSide: String
      get() = UnitID.mile

    val acButton: Boolean
      get() = true

    val additionButtons: Boolean
      get() = false

    val fractionalOutput: Boolean
      get() = true

    val inverseMode: Boolean
      get() = false
  }

  suspend fun updateDigitsPrecision(precision: Int)

  suspend fun updateFormatterSymbols(grouping: String, fractional: String)

  suspend fun updateOutputFormat(outputFormat: Int)

  suspend fun updateLatestPairOfUnits(unitFrom: String, unitTo: String)

  suspend fun updateThemingMode(themingMode: ThemingMode)

  suspend fun updateDynamicTheme(enabled: Boolean)

  suspend fun updateAmoledTheme(enabled: Boolean)

  suspend fun updateCustomColor(color: Long)

  suspend fun updateMonetMode(monetMode: MonetMode)

  suspend fun updateStartingScreen(graphId: String)

  suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>)

  suspend fun addShownUnitGroup(unitGroup: UnitGroup)

  suspend fun removeShownUnitGroup(unitGroup: UnitGroup)

  suspend fun updateLastReadChangelog(value: String)

  suspend fun updateVibrations(enabled: Boolean)

  suspend fun updateEnableKeepScreenOn(enabled: Boolean)

  suspend fun updateMiddleZero(enabled: Boolean)

  suspend fun updateToolsExperiment(enabled: Boolean)

  suspend fun updateRadianMode(radianMode: Boolean)

  suspend fun updateUnitConverterFavoritesOnly(enabled: Boolean)

  suspend fun updateUnitConverterFormatTime(enabled: Boolean)

  suspend fun updateUnitConverterSorting(sorting: UnitsListSorting)

  suspend fun updatePartialHistoryView(enabled: Boolean)

  suspend fun updateSteppedPartialHistoryView(enabled: Boolean)

  suspend fun updateInitialPartialHistoryView(enabled: Boolean)

  suspend fun updateOpenHistoryViewButton(enabled: Boolean)

  suspend fun updateFractionalOutput(enabled: Boolean)

  suspend fun updateAcButton(enabled: Boolean)

  suspend fun updateAdditionalButtons(enabled: Boolean)

  suspend fun updateInverseMode(enabled: Boolean)
}

internal inline fun <T, R> T.letTryOrNull(block: (T) -> R): R? =
  try {
    this?.let(block)
  } catch (_: Exception) {
    null
  }

internal fun List<UnitGroup>.packToString(): String = this.joinToString(",")
