/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.data.userprefs

import androidx.datastore.preferences.core.Preferences
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.units.MyUnitIDS

fun Preferences.getEnableDynamicTheme(): Boolean {
    return this[PrefsKeys.ENABLE_DYNAMIC_THEME] ?: true
}

fun Preferences.getThemingMode(): String {
    return this[PrefsKeys.THEMING_MODE] ?: ""
}

fun Preferences.getEnableAmoledTheme(): Boolean {
    return this[PrefsKeys.ENABLE_AMOLED_THEME] ?: false
}

fun Preferences.getCustomColor(): Long {
    return this[PrefsKeys.CUSTOM_COLOR] ?: 16L // From Color.Unspecified
}

fun Preferences.getMonetMode(): String {
    return this[PrefsKeys.MONET_MODE] ?: ""
}

fun Preferences.getStartingScreen(): String {
    return this[PrefsKeys.STARTING_SCREEN] ?: TopLevelDestinations.Calculator.graph
}

fun Preferences.getEnableToolsExperiment(): Boolean {
    return this[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] ?: false
}

fun Preferences.getEnableVibrations(): Boolean {
    return this[PrefsKeys.ENABLE_VIBRATIONS] ?: true
}

fun Preferences.getRadianMode(): Boolean {
    return this[PrefsKeys.RADIAN_MODE] ?: true
}

fun Preferences.getSeparator(): Int {
    return this[PrefsKeys.SEPARATOR] ?: Separator.SPACE
}

fun Preferences.getMiddleZero(): Boolean {
    return this[PrefsKeys.MIDDLE_ZERO] ?: true
}

fun Preferences.getPartialHistoryView(): Boolean {
    return this[PrefsKeys.PARTIAL_HISTORY_VIEW] ?: true
}

fun Preferences.getDigitsPrecision(): Int {
    return this[PrefsKeys.DIGITS_PRECISION] ?: 3
}

fun Preferences.getOutputFormat(): Int {
    return this[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN
}

fun Preferences.getUnitConverterFormatTime(): Boolean {
    return this[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] ?: false
}

fun Preferences.getUnitConverterSorting(): UnitsListSorting {
    return this[PrefsKeys.UNIT_CONVERTER_SORTING]
        ?.let { UnitsListSorting.valueOf(it) } ?: UnitsListSorting.USAGE
}

fun Preferences.getShownUnitGroups(): List<UnitGroup> {
    return this[PrefsKeys.SHOWN_UNIT_GROUPS]
        ?.letTryOrNull { list ->
            list
                .ifEmpty { return@letTryOrNull listOf() }
                .split(",")
                .map { UnitGroup.valueOf(it) }
        }
        ?: ALL_UNIT_GROUPS
}

fun Preferences.getUnitConverterFavoritesOnly(): Boolean {
    return this[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY]
        ?: false
}

fun Preferences.getLatestLeftSide(): String {
    return this[PrefsKeys.LATEST_LEFT_SIDE] ?: MyUnitIDS.kilometer
}

fun Preferences.getLatestRightSide(): String {
    return this[PrefsKeys.LATEST_RIGHT_SIDE] ?: MyUnitIDS.mile
}

fun Preferences.getAcButton(): Boolean {
    return this[PrefsKeys.AC_BUTTON] ?: true
}

fun Preferences.getClearInputAfterEquals(): Boolean {
    return this[PrefsKeys.CLEAR_INPUT_AFTER_EQUALS] ?: true
}

fun Preferences.getRpnMode(): Boolean {
    return this[PrefsKeys.RPN_MODE] ?: false
}

private inline fun <T, R> T.letTryOrNull(block: (T) -> R): R? = try {
    this?.let(block)
} catch (e: Exception) {
    null
}