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

package com.sadellie.unitto.feature.settings

import com.sadellie.unitto.feature.settings.backup.BackupViewModel
import com.sadellie.unitto.feature.settings.calculator.CalculatorSettingsViewModel
import com.sadellie.unitto.feature.settings.converter.ConverterSettingsViewModel
import com.sadellie.unitto.feature.settings.display.DisplayViewModel
import com.sadellie.unitto.feature.settings.formatting.FormattingViewModel
import com.sadellie.unitto.feature.settings.startingscreen.StartingScreenViewModel
import com.sadellie.unitto.feature.settings.unitgroups.UnitGroupsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.lazyModule

val settingsModule = lazyModule {
  viewModelOf(::BackupViewModel)
  viewModelOf(::CalculatorSettingsViewModel)
  viewModelOf(::ConverterSettingsViewModel)
  viewModelOf(::DisplayViewModel)
  viewModelOf(::FormattingViewModel)
  viewModelOf(::StartingScreenViewModel)
  viewModelOf(::UnitGroupsViewModel)
  viewModelOf(::SettingsViewModel)
}
