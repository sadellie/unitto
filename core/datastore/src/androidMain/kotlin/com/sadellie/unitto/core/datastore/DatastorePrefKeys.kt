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

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DatastorePrefKeys {
  // COMMON
  val THEMING_MODE = stringPreferencesKey(PrefKeys.THEMING_MODE_PREF_KEY)
  val ENABLE_DYNAMIC_THEME = booleanPreferencesKey(PrefKeys.ENABLE_DYNAMIC_THEME_PREF_KEY)
  val ENABLE_AMOLED_THEME = booleanPreferencesKey(PrefKeys.ENABLE_AMOLED_THEME_PREF_KEY)
  val CUSTOM_COLOR = longPreferencesKey(PrefKeys.CUSTOM_COLOR_PREF_KEY)
  val MONET_MODE = stringPreferencesKey(PrefKeys.MONET_MODE_PREF_KEY)
  val STARTING_SCREEN = stringPreferencesKey(PrefKeys.STARTING_SCREEN_PREF_KEY)
  val ENABLE_TOOLS_EXPERIMENT = booleanPreferencesKey(PrefKeys.ENABLE_TOOLS_EXPERIMENT_PREF_KEY)
  // val SYSTEM_FONT = booleanPreferencesKey(PrefKeys.SYSTEM_FONT_PREF_KEY)
  val LAST_READ_CHANGELOG = stringPreferencesKey(PrefKeys.LAST_READ_CHANGELOG_PREF_KEY)
  val ENABLE_VIBRATIONS = booleanPreferencesKey(PrefKeys.ENABLE_VIBRATIONS_PREF_KEY)
  val ENABLE_KEEP_SCREEN_ON = booleanPreferencesKey(PrefKeys.ENABLE_KEEP_SCREEN_ON_PREF_KEY)
  val MIDDLE_ZERO = booleanPreferencesKey(PrefKeys.MIDDLE_ZERO_PREF_KEY)
  val AC_BUTTON = booleanPreferencesKey(PrefKeys.AC_BUTTON_PREF_KEY)
  // val RPN_MODE = booleanPreferencesKey(PrefKeys.RPN_MODE_PREF_KEY)

  // FORMATTER
  val DIGITS_PRECISION = intPreferencesKey(PrefKeys.DIGITS_PRECISION_PREF_KEY)
  // val SEPARATOR = intPreferencesKey(PrefKeys.SEPARATOR_PREF_KEY)
  val FORMATTER_GROUPING = stringPreferencesKey(PrefKeys.FORMATTER_GROUPING_PREF_KEY)
  val FORMATTER_FRACTIONAL = stringPreferencesKey(PrefKeys.FORMATTER_FRACTIONAL_PREF_KEY)
  val OUTPUT_FORMAT = intPreferencesKey(PrefKeys.OUTPUT_FORMAT_PREF_KEY)

  // CALCULATOR
  val RADIAN_MODE = booleanPreferencesKey(PrefKeys.RADIAN_MODE_PREF_KEY)
  val PARTIAL_HISTORY_VIEW = booleanPreferencesKey(PrefKeys.PARTIAL_HISTORY_VIEW_PREF_KEY)
  val STEPPED_PARTIAL_HISTORY_VIEW = booleanPreferencesKey(PrefKeys.STEPPED_PARTIAL_HISTORY_VIEW_PREF_KEY)
  val OPEN_HISTORY_VIEW_BUTTON = booleanPreferencesKey(PrefKeys.OPEN_HISTORY_VIEW_BUTTON_PREF_KEY)
  val INITIAL_PARTIAL_HISTORY_VIEW = booleanPreferencesKey(PrefKeys.INITIAL_PARTIAL_HISTORY_VIEW_PREF_KEY)
  val FRACTIONAL_OUTPUT = booleanPreferencesKey(PrefKeys.FRACTIONAL_OUTPUT_PREF_KEY)
  val ADDITIONAL_BUTTONS = booleanPreferencesKey(PrefKeys.ADDITIONAL_BUTTONS_PREF_KEY)
  val INVERSE_MODE = booleanPreferencesKey(PrefKeys.INVERSE_MODE_PREF_KEY)

  // UNIT CONVERTER
  val LATEST_LEFT_SIDE = stringPreferencesKey(PrefKeys.LATEST_LEFT_SIDE_PREF_KEY)
  val LATEST_RIGHT_SIDE = stringPreferencesKey(PrefKeys.LATEST_RIGHT_SIDE_PREF_KEY)
  val SHOWN_UNIT_GROUPS = stringPreferencesKey(PrefKeys.SHOWN_UNIT_GROUPS_PREF_KEY)
  val UNIT_CONVERTER_FAVORITES_ONLY =
    booleanPreferencesKey(PrefKeys.UNIT_CONVERTER_FAVORITES_ONLY_PREF_KEY)
  val UNIT_CONVERTER_FORMAT_TIME = booleanPreferencesKey(PrefKeys.UNIT_CONVERTER_FORMAT_TIME_PREF_KEY)
  val UNIT_CONVERTER_SORTING = stringPreferencesKey(PrefKeys.UNIT_CONVERTER_SORTING_PREF_KEY)
}
