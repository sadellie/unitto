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

package com.sadellie.unitto.feature.settings.navigation

import androidx.navigation3.ui.NavDisplay
import com.sadellie.unitto.core.designsystem.stackedTransition
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.feature.settings.backup.BackupRoute
import com.sadellie.unitto.feature.settings.language.LanguageRoute
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module

@OptIn(KoinExperimentalAPI::class)
internal actual fun Module.backupNavigation() {
  navigation<BackupRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    BackupRoute(navigateUpAction = navigator::goBack)
  }
}

@OptIn(KoinExperimentalAPI::class)
internal actual fun Module.languageNavigation() {
  navigation<LanguageRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    LanguageRoute(navigateUp = navigator::goBack)
  }
}
