/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import io.github.sadellie.evaluatto.programmer.BaseNumber
import io.github.sadellie.evaluatto.programmer.DataUnit

@Suppress("EmptyRange")
internal fun convertExpressionBase(
  expression: String,
  fromRadix: Int,
  toRadix: Int,
  dataUnit: DataUnit,
): String {
  val result = StringBuilder()
  var i = 0
  while (i < expression.length) {
    val char = expression[i]
    if (char.isDigitForBase(fromRadix)) {
      val start = i
      while (i < expression.length && expression[i].isDigitForBase(fromRadix)) {
        i++
      }
      val numStr = expression.substring(start until i)
      val converted =
        runCatching {
            val baseNumber = BaseNumber(symbolic = numStr, base = fromRadix, dataUnit = dataUnit)
            baseNumber.toString(toRadix).uppercase()
          }
          .getOrDefault(numStr.uppercase())

      result.append(converted)
    } else {
      result.append(char)
      i++
    }
  }
  return result.toString()
}

private fun Char.isDigitForBase(radix: Int): Boolean =
  when (this) {
    in '0'..'9' -> this.digitToInt() < radix
    in 'A'..'F' -> this - 'A' + 10 < radix
    else -> false
  }
