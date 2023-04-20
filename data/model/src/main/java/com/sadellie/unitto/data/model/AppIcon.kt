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

package com.sadellie.unitto.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

private const val unitto = 0xFF186c31
private const val white = 0xFFFFFFFF
private const val red = 0xFFE91E63
private const val orange = 0xFFFF9800

enum class LauncherIcon(
    val component: String,
    @StringRes val labelString: Int = R.string.app_name,
    @DrawableRes val foregroundDrawable: Int,
    val foregroundColor: Long,
    val backgroundColor: Long,
) {
    MAIN_DEFAULT(   "com.sadellie.unitto.MainActivity",         R.string.app_name, R.drawable.ic_launcher_1_foreground,    white,  unitto),
    MAIN_RED(       "com.sadellie.unitto.custom.MainActivity2", R.string.app_name, R.drawable.ic_launcher_1_foreground,    white,  red),
    MAIN_ORANGE(    "com.sadellie.unitto.custom.MainActivity3", R.string.app_name, R.drawable.ic_launcher_1_foreground,    white,  orange),

    CALC1_DEFAULT(  "com.sadellie.unitto.custom.MainActivity4", R.string.calculator, R.drawable.ic_launcher_2_foreground,    white,  unitto),
    CALC1_RED(      "com.sadellie.unitto.custom.MainActivity5", R.string.calculator, R.drawable.ic_launcher_2_foreground,    white,  red),
    CALC1_ORANGE(   "com.sadellie.unitto.custom.MainActivity6", R.string.calculator, R.drawable.ic_launcher_2_foreground,    white,  orange),

    CALC2_DEFAULT(  "com.sadellie.unitto.custom.MainActivity7", R.string.calculator, R.drawable.ic_launcher_3_foreground,    white,  unitto),
    CALC2_RED(      "com.sadellie.unitto.custom.MainActivity8", R.string.calculator, R.drawable.ic_launcher_3_foreground,    white,  red),
    CALC2_ORANGE(   "com.sadellie.unitto.custom.MainActivity9", R.string.calculator, R.drawable.ic_launcher_3_foreground,    white,  orange),
}
