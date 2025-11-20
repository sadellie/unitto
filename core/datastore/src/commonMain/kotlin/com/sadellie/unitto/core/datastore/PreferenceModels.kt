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
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.TopLevelRoute
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

data class AppPreferences(
  val themingMode: ThemingMode,
  val enableDynamicTheme: Boolean,
  val enableAmoledTheme: Boolean,
  val customColor: Long,
  val monetMode: MonetMode,
  val startingScreen: Route,
  val enableToolsExperiment: Boolean,
  val enableVibrations: Boolean,
  val enableKeepScreenOn: Boolean,
)

data class GeneralPreferences(
  val lastReadChangelog: String,
  val enableVibrations: Boolean,
  val enableKeepScreenOn: Boolean,
)

data class CalculatorPreferences(
  val radianMode: Boolean,
  val formatterSymbols: FormatterSymbols,
  val middleZero: Boolean,
  val acButton: Boolean,
  val additionalButtons: Boolean,
  val inverseMode: Boolean,
  val partialHistoryView: Boolean,
  val steppedPartialHistoryView: Boolean,
  val initialPartialHistoryView: Boolean,
  val openHistoryViewButton: Boolean,
  val fractionalOutput: Boolean,
  val precision: Int,
  val outputFormat: Int,
)

data class ConverterPreferences(
  val formatterSymbols: FormatterSymbols,
  val middleZero: Boolean,
  val acButton: Boolean,
  val precision: Int,
  val outputFormat: Int,
  val unitConverterFormatTime: Boolean,
  val unitConverterSorting: UnitsListSorting,
  val shownUnitGroups: List<UnitGroup>,
  val unitConverterFavoritesOnly: Boolean,
  val enableToolsExperiment: Boolean,
  val latestLeftSideUnit: String,
  val latestRightSideUnit: String,
)

data class DisplayPreferences(val middleZero: Boolean, val acButton: Boolean)

data class FormattingPreferences(
  val digitsPrecision: Int,
  val formatterSymbols: FormatterSymbols,
  val outputFormat: Int,
)

data class UnitGroupsPreferences(val shownUnitGroups: List<UnitGroup>)

data class AddSubtractPreferences(val formatterSymbols: FormatterSymbols)

data class BodyMassPreferences(val formatterSymbols: FormatterSymbols)

data class AboutPreferences(val enableToolsExperiment: Boolean)

data class StartingScreenPreferences(val startingScreen: TopLevelRoute)
