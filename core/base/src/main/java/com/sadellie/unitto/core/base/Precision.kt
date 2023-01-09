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
 * Current maximum scale that will be used in app. Used in various place in code
 */
const val MAX_PRECISION: Int = 1_000

/**
 * Currently available scale options
 */
val PRECISIONS: Map<Int, Int> by lazy {
    mapOf(
        0 to R.string.precision_zero,
        1 to R.string.precision_one,
        2 to R.string.precision_two,
        3 to R.string.precision_three,
        4 to R.string.precision_four,
        5 to R.string.precision_five,
        6 to R.string.precision_six,
        7 to R.string.precision_seven,
        8 to R.string.precision_eight,
        9 to R.string.precision_nine,
        10 to R.string.precision_ten,
        11 to R.string.precision_eleven,
        12 to R.string.precision_twelve,
        13 to R.string.precision_thirteen,
        14 to R.string.precision_fourteen,
        15 to R.string.precision_fifteen,
        MAX_PRECISION to R.string.max_precision
    )
}
