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

package com.sadellie.unitto.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.datastore.UserPreferencesRepository.Defaults
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.navigation.graphRoutes
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepositoryImpl(private val dataStore: DataStore<Preferences>) :
  UserPreferencesRepository {
  private val data =
    dataStore.data.catch { if (it is IOException) emit(emptyPreferences()) else throw it }

  override val appPrefs: Flow<AppPreferences> =
    data.map { preferences ->
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
    data.map { preferences ->
      GeneralPreferences(
        lastReadChangelog = preferences.getLastReadChangelog(),
        enableVibrations = preferences.getEnableVibrations(),
        enableKeepScreenOn = preferences.getEnableKeepScreenOn(),
      )
    }

  override val calculatorPrefs: Flow<CalculatorPreferences> =
    data.map { preferences ->
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
    data.map { preferences ->
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
    data.map { preferences ->
      DisplayPreferences(
        middleZero = preferences.getMiddleZero(),
        acButton = preferences.getAcButton(),
      )
    }

  override val formattingPrefs: Flow<FormattingPreferences> =
    data.map { preferences ->
      FormattingPreferences(
        digitsPrecision = preferences.getDigitsPrecision(),
        formatterSymbols = preferences.getFormatterSymbols(),
        outputFormat = preferences.getOutputFormat(),
      )
    }

  override val unitGroupsPrefs: Flow<UnitGroupsPreferences> =
    data.map { preferences ->
      UnitGroupsPreferences(shownUnitGroups = preferences.getShownUnitGroups())
    }

  override val addSubtractPrefs: Flow<AddSubtractPreferences> =
    data.map { preferences ->
      AddSubtractPreferences(formatterSymbols = preferences.getFormatterSymbols())
    }

  override val bodyMassPrefs: Flow<BodyMassPreferences> =
    data.map { preferences ->
      BodyMassPreferences(formatterSymbols = preferences.getFormatterSymbols())
    }

  override val aboutPrefs: Flow<AboutPreferences> =
    data.map { preferences ->
      AboutPreferences(enableToolsExperiment = preferences.getEnableToolsExperiment())
    }

  override val startingScreenPrefs: Flow<StartingScreenPreferences> =
    data.map { preferences ->
      StartingScreenPreferences(startingScreen = preferences.getStartingScreen())
    }

  override suspend fun updateDigitsPrecision(precision: Int) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.DIGITS_PRECISION] = precision }
  }

  override suspend fun updateFormatterSymbols(grouping: String, fractional: String) {
    // Grouping and fractional symbols are always different
    if (grouping == fractional) return

    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.FORMATTER_GROUPING] = grouping
      preferences[DatastorePrefKeys.FORMATTER_FRACTIONAL] = fractional
    }
  }

  override suspend fun updateOutputFormat(outputFormat: Int) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.OUTPUT_FORMAT] = outputFormat }
  }

  override suspend fun updateLatestPairOfUnits(unitFrom: String, unitTo: String) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.LATEST_LEFT_SIDE] = unitFrom
      preferences[DatastorePrefKeys.LATEST_RIGHT_SIDE] = unitTo
    }
  }

  override suspend fun updateThemingMode(themingMode: ThemingMode) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.THEMING_MODE] = themingMode.name }
  }

  override suspend fun updateDynamicTheme(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.ENABLE_DYNAMIC_THEME] = enabled }
  }

  override suspend fun updateAmoledTheme(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.ENABLE_AMOLED_THEME] = enabled }
  }

  override suspend fun updateCustomColor(color: Long) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.CUSTOM_COLOR] = color }
  }

  override suspend fun updateMonetMode(monetMode: MonetMode) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.MONET_MODE] = monetMode.name }
  }

  override suspend fun updateStartingScreen(graphId: String) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.STARTING_SCREEN] = graphId }
  }

  override suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.SHOWN_UNIT_GROUPS] = shownUnitGroups.packToString()
    }
  }

  override suspend fun addShownUnitGroup(unitGroup: UnitGroup) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.SHOWN_UNIT_GROUPS] =
        preferences.getShownUnitGroups().plus(unitGroup).packToString()
    }
  }

  override suspend fun removeShownUnitGroup(unitGroup: UnitGroup) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.SHOWN_UNIT_GROUPS] =
        preferences.getShownUnitGroups().minus(unitGroup).packToString()
    }
  }

  override suspend fun updateLastReadChangelog(value: String) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.LAST_READ_CHANGELOG] = value }
  }

  override suspend fun updateVibrations(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.ENABLE_VIBRATIONS] = enabled }
  }

  override suspend fun updateEnableKeepScreenOn(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.ENABLE_KEEP_SCREEN_ON] = enabled }
  }

  override suspend fun updateMiddleZero(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.MIDDLE_ZERO] = enabled }
  }

  override suspend fun updateToolsExperiment(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.ENABLE_TOOLS_EXPERIMENT] = enabled
    }
  }

  override suspend fun updateRadianMode(radianMode: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.RADIAN_MODE] = radianMode }
  }

  override suspend fun updateUnitConverterFavoritesOnly(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.UNIT_CONVERTER_FAVORITES_ONLY] = enabled
    }
  }

  override suspend fun updateUnitConverterFormatTime(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.UNIT_CONVERTER_FORMAT_TIME] = enabled
    }
  }

  override suspend fun updateUnitConverterSorting(sorting: UnitsListSorting) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.UNIT_CONVERTER_SORTING] = sorting.name
    }
  }

  override suspend fun updatePartialHistoryView(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.PARTIAL_HISTORY_VIEW] = enabled }
  }

  override suspend fun updateSteppedPartialHistoryView(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.STEPPED_PARTIAL_HISTORY_VIEW] = enabled
    }
  }

  override suspend fun updateInitialPartialHistoryView(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.INITIAL_PARTIAL_HISTORY_VIEW] = enabled
    }
  }

  override suspend fun updateOpenHistoryViewButton(enabled: Boolean) {
    dataStore.edit { preferences ->
      preferences[DatastorePrefKeys.OPEN_HISTORY_VIEW_BUTTON] = enabled
    }
  }

  override suspend fun updateFractionalOutput(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.FRACTIONAL_OUTPUT] = enabled }
  }

  override suspend fun updateAcButton(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.AC_BUTTON] = enabled }
  }

  override suspend fun updateAdditionalButtons(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.ADDITIONAL_BUTTONS] = enabled }
  }

  override suspend fun updateInverseMode(enabled: Boolean) {
    dataStore.edit { preferences -> preferences[DatastorePrefKeys.INVERSE_MODE] = enabled }
  }

  private fun Preferences.getEnableDynamicTheme() =
    this[DatastorePrefKeys.ENABLE_DYNAMIC_THEME] ?: Defaults.enableDynamicTheme

  private fun Preferences.getThemingMode() =
    this[DatastorePrefKeys.THEMING_MODE]?.letTryOrNull { ThemingMode.valueOf(it) }
      ?: Defaults.themingMode

  private fun Preferences.getEnableAmoledTheme() =
    this[DatastorePrefKeys.ENABLE_AMOLED_THEME] ?: Defaults.enableAmoledTheme

  private fun Preferences.getCustomColor() =
    this[DatastorePrefKeys.CUSTOM_COLOR] ?: Defaults.customColor

  private fun Preferences.getMonetMode() =
    this[DatastorePrefKeys.MONET_MODE]?.letTryOrNull { MonetMode.valueOf(it) } ?: Defaults.monetMode

  private fun Preferences.getStartingScreen() =
    graphRoutes.firstOrNull { it.id == this[DatastorePrefKeys.STARTING_SCREEN] }
      ?: Defaults.startingScreen

  private fun Preferences.getEnableToolsExperiment() =
    this[DatastorePrefKeys.ENABLE_TOOLS_EXPERIMENT] ?: Defaults.enableToolsExperiment

  private fun Preferences.getLastReadChangelog() =
    this[DatastorePrefKeys.LAST_READ_CHANGELOG] ?: Defaults.lastReadChangelog

  private fun Preferences.getEnableVibrations() =
    this[DatastorePrefKeys.ENABLE_VIBRATIONS] ?: Defaults.enableVibrations

  private fun Preferences.getEnableKeepScreenOn() =
    this[DatastorePrefKeys.ENABLE_KEEP_SCREEN_ON] ?: Defaults.enableKeepScreenOn

  private fun Preferences.getRadianMode() =
    this[DatastorePrefKeys.RADIAN_MODE] ?: Defaults.radianMode

  private fun Preferences.getFormatterSymbols(): FormatterSymbols {
    val grouping = this[DatastorePrefKeys.FORMATTER_GROUPING]
    val fractional = this[DatastorePrefKeys.FORMATTER_FRACTIONAL]
    if (grouping == null || fractional == null) {
      return Defaults.formatterSymbols
    }
    return FormatterSymbols(grouping, fractional)
  }

  private fun Preferences.getMiddleZero() =
    this[DatastorePrefKeys.MIDDLE_ZERO] ?: Defaults.middleZero

  private fun Preferences.getPartialHistoryView() =
    this[DatastorePrefKeys.PARTIAL_HISTORY_VIEW] ?: Defaults.partialHistoryView

  private fun Preferences.getSteppedPartialHistoryView() =
    this[DatastorePrefKeys.STEPPED_PARTIAL_HISTORY_VIEW] ?: Defaults.steppedPartialHistoryView

  private fun Preferences.getInitialPartialHistoryView() =
    this[DatastorePrefKeys.INITIAL_PARTIAL_HISTORY_VIEW] ?: Defaults.initialPartialHistoryView

  private fun Preferences.getOpenHistoryViewButton() =
    this[DatastorePrefKeys.OPEN_HISTORY_VIEW_BUTTON] ?: Defaults.openHistoryViewButton

  private fun Preferences.getDigitsPrecision() =
    this[DatastorePrefKeys.DIGITS_PRECISION] ?: Defaults.digitsPrecision

  private fun Preferences.getOutputFormat() =
    this[DatastorePrefKeys.OUTPUT_FORMAT] ?: Defaults.outputFormat

  private fun Preferences.getUnitConverterFormatTime() =
    this[DatastorePrefKeys.UNIT_CONVERTER_FORMAT_TIME] ?: Defaults.unitConverterFormatTime

  private fun Preferences.getUnitConverterSorting() =
    this[DatastorePrefKeys.UNIT_CONVERTER_SORTING]?.let { UnitsListSorting.valueOf(it) }
      ?: Defaults.unitConverterSorting

  private fun Preferences.getShownUnitGroups(): List<UnitGroup> {
    return this[DatastorePrefKeys.SHOWN_UNIT_GROUPS]?.letTryOrNull { list ->
      list
        .ifEmpty {
          return@letTryOrNull listOf()
        }
        .split(",")
        .map { UnitGroup.valueOf(it) }
    } ?: Defaults.shownUnitGroups
  }

  private fun Preferences.getUnitConverterFavoritesOnly() =
    this[DatastorePrefKeys.UNIT_CONVERTER_FAVORITES_ONLY] ?: Defaults.unitConverterFavoritesOnly

  private fun Preferences.getLatestLeftSide() =
    this[DatastorePrefKeys.LATEST_LEFT_SIDE] ?: Defaults.latestLeftSide

  private fun Preferences.getLatestRightSide() =
    this[DatastorePrefKeys.LATEST_RIGHT_SIDE] ?: Defaults.latestRightSide

  private fun Preferences.getAcButton() = this[DatastorePrefKeys.AC_BUTTON] ?: Defaults.acButton

  private fun Preferences.getAdditionalButtons() =
    this[DatastorePrefKeys.ADDITIONAL_BUTTONS] ?: Defaults.additionButtons

  private fun Preferences.getFractionalOutput() =
    this[DatastorePrefKeys.FRACTIONAL_OUTPUT] ?: Defaults.fractionalOutput

  private fun Preferences.getInverseMode() =
    this[DatastorePrefKeys.INVERSE_MODE] ?: Defaults.inverseMode
}
