/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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
import com.sadellie.unitto.core.datastore.UserPreferencesRepository.Defaults
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.navigation.graphRoutes
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class UserPreferencesRepositoryImpl : UserPreferencesRepository {
  private val _data = MutableStateFlow(emptyMap<String, Any>())
  override val appPrefs: Flow<AppPreferences> =
    _data.map { preferences ->
      AppPreferences(
        themingMode = preferences.getThemingMode(),
        enableDynamicTheme = preferences.getEnableDynamicTheme(),
        enableAmoledTheme = preferences.getEnableAmoledTheme(),
        customColor = preferences.getCustomColor(),
        monetMode = preferences.getMonetMode(),
        startingScreen = preferences.getStartingScreen(),
        enableToolsExperiment = preferences.getEnableToolsExperiment(),
        enableVibrations = preferences.getEnableVibrations(),
        enableKeepScreenOn = preferences.getEnableKeepScreenOn(),
      )
    }

  override val generalPrefs: Flow<GeneralPreferences> =
    _data.map { preferences ->
      GeneralPreferences(
        lastReadChangelog = preferences.getLastReadChangelog(),
        enableVibrations = preferences.getEnableVibrations(),
        enableKeepScreenOn = preferences.getEnableKeepScreenOn(),
      )
    }

  override val calculatorPrefs: Flow<CalculatorPreferences> =
    _data.map { preferences ->
      CalculatorPreferences(
        radianMode = preferences.getRadianMode(),
        formatterSymbols = preferences.getFormatterSymbols(),
        middleZero = preferences.getMiddleZero(),
        partialHistoryView = preferences.getPartialHistoryView(),
        steppedPartialHistoryView = preferences.getSteppedPartialHistoryView(),
        initialPartialHistoryView = preferences.getInitialPartialHistoryView(),
        openHistoryViewButton = preferences.getOpenHistoryViewButton(),
        precision = preferences.getDigitsPrecision(),
        outputFormat = preferences.getOutputFormat(),
        acButton = preferences.getAcButton(),
        additionalButtons = preferences.getAdditionalButtons(),
        inverseMode = preferences.getInverseMode(),
        fractionalOutput = preferences.getFractionalOutput(),
      )
    }

  override val converterPrefs: Flow<ConverterPreferences> =
    _data.map { preferences ->
      ConverterPreferences(
        formatterSymbols = preferences.getFormatterSymbols(),
        middleZero = preferences.getMiddleZero(),
        precision = preferences.getDigitsPrecision(),
        outputFormat = preferences.getOutputFormat(),
        unitConverterFormatTime = preferences.getUnitConverterFormatTime(),
        unitConverterSorting = preferences.getUnitConverterSorting(),
        shownUnitGroups = preferences.getShownUnitGroups(),
        unitConverterFavoritesOnly = preferences.getUnitConverterFavoritesOnly(),
        enableToolsExperiment = preferences.getEnableToolsExperiment(),
        latestLeftSideUnit = preferences.getLatestLeftSide(),
        latestRightSideUnit = preferences.getLatestRightSide(),
        acButton = preferences.getAcButton(),
      )
    }

  override val displayPrefs: Flow<DisplayPreferences> =
    _data.map { preferences ->
      DisplayPreferences(
        middleZero = preferences.getMiddleZero(),
        acButton = preferences.getAcButton(),
      )
    }

  override val formattingPrefs: Flow<FormattingPreferences> =
    _data.map { preferences ->
      FormattingPreferences(
        digitsPrecision = preferences.getDigitsPrecision(),
        formatterSymbols = preferences.getFormatterSymbols(),
        outputFormat = preferences.getOutputFormat(),
      )
    }

  override val unitGroupsPrefs: Flow<UnitGroupsPreferences> =
    _data.map { preferences ->
      UnitGroupsPreferences(shownUnitGroups = preferences.getShownUnitGroups())
    }

  override val addSubtractPrefs: Flow<AddSubtractPreferences> =
    _data.map { preferences ->
      AddSubtractPreferences(formatterSymbols = preferences.getFormatterSymbols())
    }

  override val bodyMassPrefs: Flow<BodyMassPreferences> =
    _data.map { preferences ->
      BodyMassPreferences(formatterSymbols = preferences.getFormatterSymbols())
    }

  override val aboutPrefs: Flow<AboutPreferences> =
    _data.map { preferences ->
      AboutPreferences(enableToolsExperiment = preferences.getEnableToolsExperiment())
    }

  override val startingScreenPrefs: Flow<StartingScreenPreferences> =
    _data.map { preferences ->
      StartingScreenPreferences(startingScreen = preferences.getStartingScreen())
    }

  override suspend fun updateDigitsPrecision(precision: Int) =
    updateData(PrefKeys.DIGITS_PRECISION_PREF_KEY, precision)

  override suspend fun updateFormatterSymbols(grouping: String, fractional: String) {
    if (grouping == fractional) return
    updateData(PrefKeys.FORMATTER_GROUPING_PREF_KEY, grouping)
    updateData(PrefKeys.FORMATTER_FRACTIONAL_PREF_KEY, fractional)
  }

  override suspend fun updateOutputFormat(outputFormat: Int) =
    updateData(PrefKeys.OUTPUT_FORMAT_PREF_KEY, outputFormat)

  override suspend fun updateLatestPairOfUnits(unitFrom: String, unitTo: String) {
    updateData(PrefKeys.LATEST_LEFT_SIDE_PREF_KEY, unitFrom)
    updateData(PrefKeys.LATEST_RIGHT_SIDE_PREF_KEY, unitTo)
  }

  override suspend fun updateThemingMode(themingMode: ThemingMode) =
    updateData(PrefKeys.THEMING_MODE_PREF_KEY, themingMode.name)

  override suspend fun updateDynamicTheme(enabled: Boolean) =
    updateData(PrefKeys.ENABLE_DYNAMIC_THEME_PREF_KEY, enabled)

  override suspend fun updateAmoledTheme(enabled: Boolean) =
    updateData(PrefKeys.ENABLE_AMOLED_THEME_PREF_KEY, enabled)

  override suspend fun updateCustomColor(color: Long) =
    updateData(PrefKeys.CUSTOM_COLOR_PREF_KEY, color)

  override suspend fun updateMonetMode(monetMode: MonetMode) =
    updateData(PrefKeys.MONET_MODE_PREF_KEY, monetMode.name)

  override suspend fun updateStartingScreen(graphId: String) =
    updateData(PrefKeys.STARTING_SCREEN_PREF_KEY, graphId)

  override suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>) =
    updateData(PrefKeys.SHOWN_UNIT_GROUPS_PREF_KEY, shownUnitGroups.packToString())

  override suspend fun addShownUnitGroup(unitGroup: UnitGroup) =
    _data.update { oldData ->
      val newData = oldData.toMutableMap()
      newData[PrefKeys.SHOWN_UNIT_GROUPS_PREF_KEY] =
        oldData.getShownUnitGroups().plus(unitGroup).packToString()
      newData
    }

  override suspend fun removeShownUnitGroup(unitGroup: UnitGroup) =
    _data.update { oldData ->
      val newData = oldData.toMutableMap()
      newData[PrefKeys.SHOWN_UNIT_GROUPS_PREF_KEY] =
        oldData.getShownUnitGroups().minus(unitGroup).packToString()
      newData
    }

  override suspend fun updateLastReadChangelog(value: String) =
    updateData(PrefKeys.LAST_READ_CHANGELOG_PREF_KEY, value)

  override suspend fun updateVibrations(enabled: Boolean) =
    updateData(PrefKeys.ENABLE_VIBRATIONS_PREF_KEY, enabled)

  override suspend fun updateEnableKeepScreenOn(enabled: Boolean) =
    updateData(PrefKeys.ENABLE_KEEP_SCREEN_ON_PREF_KEY, enabled)

  override suspend fun updateMiddleZero(enabled: Boolean) =
    updateData(PrefKeys.MIDDLE_ZERO_PREF_KEY, enabled)

  override suspend fun updateToolsExperiment(enabled: Boolean) =
    updateData(PrefKeys.ENABLE_TOOLS_EXPERIMENT_PREF_KEY, enabled)

  override suspend fun updateRadianMode(radianMode: Boolean) =
    updateData(PrefKeys.RADIAN_MODE_PREF_KEY, radianMode)

  override suspend fun updateUnitConverterFavoritesOnly(enabled: Boolean) =
    updateData(PrefKeys.UNIT_CONVERTER_FAVORITES_ONLY_PREF_KEY, enabled)

  override suspend fun updateUnitConverterFormatTime(enabled: Boolean) =
    updateData(PrefKeys.UNIT_CONVERTER_FORMAT_TIME_PREF_KEY, enabled)

  override suspend fun updateUnitConverterSorting(sorting: UnitsListSorting) =
    updateData(PrefKeys.UNIT_CONVERTER_SORTING_PREF_KEY, sorting.name)

  override suspend fun updatePartialHistoryView(enabled: Boolean) =
    updateData(PrefKeys.PARTIAL_HISTORY_VIEW_PREF_KEY, enabled)

  override suspend fun updateSteppedPartialHistoryView(enabled: Boolean) =
    updateData(PrefKeys.STEPPED_PARTIAL_HISTORY_VIEW_PREF_KEY, enabled)

  override suspend fun updateInitialPartialHistoryView(enabled: Boolean) =
    updateData(PrefKeys.INITIAL_PARTIAL_HISTORY_VIEW_PREF_KEY, enabled)

  override suspend fun updateOpenHistoryViewButton(enabled: Boolean) =
    updateData(PrefKeys.OPEN_HISTORY_VIEW_BUTTON_PREF_KEY, enabled)

  override suspend fun updateFractionalOutput(enabled: Boolean) =
    updateData(PrefKeys.FRACTIONAL_OUTPUT_PREF_KEY, enabled)

  override suspend fun updateAcButton(enabled: Boolean) =
    updateData(PrefKeys.AC_BUTTON_PREF_KEY, enabled)

  override suspend fun updateAdditionalButtons(enabled: Boolean) =
    updateData(PrefKeys.ADDITIONAL_BUTTONS_PREF_KEY, enabled)

  override suspend fun updateInverseMode(enabled: Boolean) =
    updateData(PrefKeys.INVERSE_MODE_PREF_KEY, enabled)

  private fun Preferences.getEnableDynamicTheme() =
    this.getTyped<Boolean>(PrefKeys.ENABLE_DYNAMIC_THEME_PREF_KEY) ?: Defaults.enableDynamicTheme

  private fun Preferences.getThemingMode() =
    this.getTyped<String>(PrefKeys.THEMING_MODE_PREF_KEY)?.letTryOrNull { ThemingMode.valueOf(it) }
      ?: Defaults.themingMode

  private fun Preferences.getEnableAmoledTheme() =
    this.getTyped<Boolean>(PrefKeys.ENABLE_AMOLED_THEME_PREF_KEY) ?: Defaults.enableAmoledTheme

  private fun Preferences.getCustomColor() =
    this.getTyped<Long>(PrefKeys.CUSTOM_COLOR_PREF_KEY) ?: Defaults.customColor

  private fun Preferences.getMonetMode() =
    this.getTyped<String>(PrefKeys.MONET_MODE_PREF_KEY)?.letTryOrNull { MonetMode.valueOf(it) }
      ?: Defaults.monetMode

  private fun Preferences.getStartingScreen() =
    graphRoutes.firstOrNull {
      it.routeId == this.getTyped<String>(PrefKeys.STARTING_SCREEN_PREF_KEY)
    } ?: Defaults.startingScreen

  @Suppress("UnusedReceiverParameter") private fun Preferences.getEnableToolsExperiment() = false

  private fun Preferences.getLastReadChangelog() =
    this.getTyped<String>(PrefKeys.LAST_READ_CHANGELOG_PREF_KEY) ?: Defaults.lastReadChangelog

  @Suppress("UnusedReceiverParameter") private fun Preferences.getEnableVibrations() = false

  @Suppress("UnusedReceiverParameter") private fun Preferences.getEnableKeepScreenOn() = false

  private fun Preferences.getRadianMode() =
    this.getTyped<Boolean>(PrefKeys.RADIAN_MODE_PREF_KEY) ?: Defaults.radianMode

  private fun Preferences.getFormatterSymbols(): FormatterSymbols {
    val grouping = this.getTyped<String>(PrefKeys.FORMATTER_GROUPING_PREF_KEY)
    val fractional = this.getTyped<String>(PrefKeys.FORMATTER_FRACTIONAL_PREF_KEY)
    if (grouping == null || fractional == null) {
      return Defaults.formatterSymbols
    }
    return FormatterSymbols(grouping, fractional)
  }

  private fun Preferences.getMiddleZero() =
    this.getTyped<Boolean>(PrefKeys.MIDDLE_ZERO_PREF_KEY) ?: Defaults.middleZero

  private fun Preferences.getPartialHistoryView() =
    this.getTyped<Boolean>(PrefKeys.PARTIAL_HISTORY_VIEW_PREF_KEY) ?: Defaults.partialHistoryView

  private fun Preferences.getSteppedPartialHistoryView() =
    this.getTyped<Boolean>(PrefKeys.STEPPED_PARTIAL_HISTORY_VIEW_PREF_KEY)
      ?: Defaults.steppedPartialHistoryView

  private fun Preferences.getInitialPartialHistoryView() =
    this.getTyped<Boolean>(PrefKeys.INITIAL_PARTIAL_HISTORY_VIEW_PREF_KEY)
      ?: Defaults.initialPartialHistoryView

  private fun Preferences.getOpenHistoryViewButton() =
    this.getTyped<Boolean>(PrefKeys.OPEN_HISTORY_VIEW_BUTTON_PREF_KEY)
      ?: Defaults.openHistoryViewButton

  private fun Preferences.getDigitsPrecision() =
    this.getTyped<Int>(PrefKeys.DIGITS_PRECISION_PREF_KEY) ?: Defaults.digitsPrecision

  private fun Preferences.getOutputFormat() =
    this.getTyped<Int>(PrefKeys.OUTPUT_FORMAT_PREF_KEY) ?: Defaults.outputFormat

  private fun Preferences.getUnitConverterFormatTime() =
    this.getTyped<Boolean>(PrefKeys.UNIT_CONVERTER_FORMAT_TIME_PREF_KEY)
      ?: Defaults.unitConverterFormatTime

  private fun Preferences.getUnitConverterSorting() =
    this.getTyped<String>(PrefKeys.UNIT_CONVERTER_SORTING_PREF_KEY)?.let {
      UnitsListSorting.valueOf(it)
    } ?: Defaults.unitConverterSorting

  private fun Preferences.getShownUnitGroups(): List<UnitGroup> {
    return this.getTyped<String>(PrefKeys.SHOWN_UNIT_GROUPS_PREF_KEY)?.letTryOrNull { list ->
      list
        .ifEmpty {
          return@letTryOrNull listOf()
        }
        .split(",")
        .map { UnitGroup.valueOf(it) }
    } ?: Defaults.shownUnitGroups
  }

  private fun Preferences.getUnitConverterFavoritesOnly() =
    this.getTyped<Boolean>(PrefKeys.UNIT_CONVERTER_FAVORITES_ONLY_PREF_KEY)
      ?: Defaults.unitConverterFavoritesOnly

  private fun Preferences.getLatestLeftSide() =
    this.getTyped<String>(PrefKeys.LATEST_LEFT_SIDE_PREF_KEY) ?: Defaults.latestLeftSide

  private fun Preferences.getLatestRightSide() =
    this.getTyped<String>(PrefKeys.LATEST_RIGHT_SIDE_PREF_KEY) ?: Defaults.latestRightSide

  private fun Preferences.getAcButton() =
    this.getTyped<Boolean>(PrefKeys.AC_BUTTON_PREF_KEY) ?: Defaults.acButton

  private fun Preferences.getAdditionalButtons() =
    this.getTyped<Boolean>(PrefKeys.ADDITIONAL_BUTTONS_PREF_KEY) ?: Defaults.additionButtons

  private fun Preferences.getFractionalOutput() =
    this.getTyped<Boolean>(PrefKeys.FRACTIONAL_OUTPUT_PREF_KEY) ?: Defaults.fractionalOutput

  private fun Preferences.getInverseMode() =
    this.getTyped<Boolean>(PrefKeys.INVERSE_MODE_PREF_KEY) ?: Defaults.inverseMode

  private inline fun <reified T> Preferences.getTyped(key: String) = this[key] as? T

  private fun updateData(key: String, newValue: Any) =
    _data.update { oldData ->
      val newData = oldData.toMutableMap()
      newData[key] = newValue
      newData
    }
}

private typealias Preferences = Map<String, Any>
