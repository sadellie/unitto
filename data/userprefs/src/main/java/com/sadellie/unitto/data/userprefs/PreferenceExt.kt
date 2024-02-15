/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

internal fun Preferences.getEnableDynamicTheme(): Boolean {
    return this[PrefsKeys.ENABLE_DYNAMIC_THEME] ?: true
}

internal fun Preferences.getThemingMode(): ThemingMode {
    return this[PrefsKeys.THEMING_MODE]
        ?.letTryOrNull { ThemingMode.valueOf(it) }
        ?: ThemingMode.AUTO
}

internal fun Preferences.getEnableAmoledTheme(): Boolean {
    return this[PrefsKeys.ENABLE_AMOLED_THEME] ?: false
}

internal fun Preferences.getCustomColor(): Long {
    return this[PrefsKeys.CUSTOM_COLOR] ?: 16L // From Color.Unspecified
}

internal fun Preferences.getMonetMode(): MonetMode {
    return this[PrefsKeys.MONET_MODE]
        ?.letTryOrNull { MonetMode.valueOf(it) }
        ?: MonetMode.TonalSpot
}

internal fun Preferences.getStartingScreen(): String {
    return this[PrefsKeys.STARTING_SCREEN] ?: TopLevelDestinations.CALCULATOR_GRAPH
}

internal fun Preferences.getEnableToolsExperiment(): Boolean {
    return this[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] ?: false
}

internal fun Preferences.getSystemFont(): Boolean {
    return this[PrefsKeys.SYSTEM_FONT] ?: false
}

internal fun Preferences.getLastReadChangelog(): String {
    return this[PrefsKeys.LAST_READ_CHANGELOG] ?: ""
}

internal fun Preferences.getEnableVibrations(): Boolean {
    return this[PrefsKeys.ENABLE_VIBRATIONS] ?: true
}

internal fun Preferences.getRadianMode(): Boolean {
    return this[PrefsKeys.RADIAN_MODE] ?: true
}

internal fun Preferences.getFormatterSymbols(): FormatterSymbols {
    val grouping = this[PrefsKeys.FORMATTER_GROUPING]
    val fractional = this[PrefsKeys.FORMATTER_FRACTIONAL]

    // Updating from older version or fresh install
    // TODO Remove in the future
    if ((grouping == null) or (fractional == null)) {
        return when (this[PrefsKeys.SEPARATOR] ?: 0) {
            0 -> FormatterSymbols(Token.SPACE, Token.PERIOD)
            1 -> FormatterSymbols(Token.PERIOD, Token.COMMA)
            else -> FormatterSymbols(Token.COMMA, Token.PERIOD)
        }
    }

    return FormatterSymbols(grouping ?: Token.SPACE, fractional ?: Token.PERIOD)
}

internal fun Preferences.getMiddleZero(): Boolean {
    return this[PrefsKeys.MIDDLE_ZERO] ?: true
}

internal fun Preferences.getPartialHistoryView(): Boolean {
    return this[PrefsKeys.PARTIAL_HISTORY_VIEW] ?: true
}

internal fun Preferences.getDigitsPrecision(): Int {
    return this[PrefsKeys.DIGITS_PRECISION] ?: 3
}

internal fun Preferences.getOutputFormat(): Int {
    return this[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN
}

internal fun Preferences.getUnitConverterFormatTime(): Boolean {
    return this[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] ?: false
}

internal fun Preferences.getUnitConverterSorting(): UnitsListSorting {
    return this[PrefsKeys.UNIT_CONVERTER_SORTING]
        ?.let { UnitsListSorting.valueOf(it) } ?: UnitsListSorting.USAGE
}

internal fun Preferences.getShownUnitGroups(): List<UnitGroup> {
    return this[PrefsKeys.SHOWN_UNIT_GROUPS]
        ?.letTryOrNull { list ->
            list
                .ifEmpty { return@letTryOrNull listOf() }
                .split(",")
                .map { UnitGroup.valueOf(it) }
        }
        ?: UnitGroup.entries
}

internal fun Preferences.getUnitConverterFavoritesOnly(): Boolean {
    return this[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY]
        ?: false
}

internal fun Preferences.getLatestLeftSide(): String {
    return this[PrefsKeys.LATEST_LEFT_SIDE] ?: UnitID.kilometer
}

internal fun Preferences.getLatestRightSide(): String {
    return this[PrefsKeys.LATEST_RIGHT_SIDE] ?: UnitID.mile
}

internal fun Preferences.getAcButton(): Boolean {
    return this[PrefsKeys.AC_BUTTON] ?: true
}

internal fun Preferences.getAdditionalButtons(): Boolean {
    return this[PrefsKeys.ADDITIONAL_BUTTONS] ?: false
}

internal fun Preferences.getInverseMode(): Boolean {
    return this[PrefsKeys.INVERSE_MODE] ?: false
}

internal fun List<UnitGroup>.packToString(): String = this.joinToString(",")

private inline fun <T, R> T.letTryOrNull(block: (T) -> R): R? = try {
    this?.let(block)
} catch (e: Exception) {
    null
}
