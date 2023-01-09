/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.core.base

/**
 * Separators mean symbols that separate fractional part
 */
object Separator {
    const val SPACES = 0
    const val PERIOD = 1
    const val COMMA = 2
}

/**
 * Map of separators that is used in settings
 */
val SEPARATORS: Map<Int, Int> by lazy {
    mapOf(
        Separator.SPACES to R.string.spaces,
        Separator.PERIOD to R.string.period,
        Separator.COMMA to R.string.comma
    )
}
