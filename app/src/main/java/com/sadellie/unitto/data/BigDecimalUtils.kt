/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022 Elshan Agaev
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

package com.sadellie.unitto.data

import com.sadellie.unitto.data.preferences.OutputFormat
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.log10

/**
 * Shorthand function to use correct `toString` method according to [outputFormat].
 */
fun BigDecimal.toStringWith(outputFormat: Int): String {
    // Setting result value using a specified OutputFormat
    return when (outputFormat) {
        OutputFormat.ALLOW_ENGINEERING -> this.toString()
        OutputFormat.FORCE_ENGINEERING -> this.toEngineeringString()
        else -> this.toPlainString()
    }
}

/**
 * Sets the minimum scale that is required to get first non zero value in fractional part
 *
 * @param[prefScale] Is the preferred scale, the one which will be compared against
 */
fun BigDecimal.setMinimumRequiredScale(prefScale: Int): BigDecimal {
    /**
     * Here we are getting the amount of zeros in fractional part before non zero value
     * For example, for 0.00000123456 we need the length of 00000
     * Next we add one to get the position of the first non zero value
     * Also, this block is only for VERY small numbers
     */
    return this.setScale(
        kotlin.math.max(
            prefScale,
            if (this.abs() < BigDecimal.ONE) {
                // https://stackoverflow.com/a/46136593
                -floor(log10(this.abs().remainder(BigDecimal.ONE).toDouble())).toInt()
            } else {
                0
            }
        ),
        RoundingMode.HALF_EVEN
    )
}

/**
 * Removes all trailing zeroes.
 *
 * @throws NumberFormatException if value is bigger than [Double.MAX_VALUE] to avoid memory overflow.
 */
fun BigDecimal.trimZeros(): BigDecimal {
    if (this.abs() > BigDecimal.valueOf(Double.MAX_VALUE)) throw NumberFormatException()

    return if (this.compareTo(BigDecimal.ZERO) == 0) BigDecimal.ZERO else this.stripTrailingZeros()
}
