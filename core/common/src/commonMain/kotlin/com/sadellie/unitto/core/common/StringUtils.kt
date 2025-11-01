/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.core.common

/**
 * Compute Levenshtein Distance between this string and [secondString]. Doesn't matter which string
 * is first.
 *
 * @return The amount of changes that are needed to transform one string into another
 */
fun String.lev(secondString: String): Int {
  if (this.isEmpty()) return secondString.length
  if (secondString.isEmpty()) return this.length

  val stringA = this.lowercase()
  val stringB = secondString.lowercase()
  if (stringA == stringB) return 0

  var cost = IntArray(stringA.length + 1) { it }
  var newCost = IntArray(stringA.length + 1)

  for (i in 1..stringB.length) {
    // basically shifting this to the right by 1 each time
    newCost[0] = i

    for (j in 1..stringA.length) {
      newCost[j] =
        minOf(
          // Adding 1 if they don't match, i.e. need to replace
          cost[j - 1] + if (stringA[j - 1] == stringB[i - 1]) 0 else 1,
          // Insert
          cost[j] + 1,
          // Delete
          newCost[j - 1] + 1,
        )
    }

    // Swapping costs
    cost = newCost.also { newCost = cost }
  }

  return cost[this.length]
}

fun String.isExpression(): Boolean {
  if (isEmpty()) return false

  // Positive numbers and zero
  if (all { it.toString() in Token.Digit.allWithDot }) return false

  // Negative numbers
  // Needs to start with an negative
  if (this.first().toString() != Token.Operator.MINUS) return true

  // Rest of the string must be just like positive
  return this.drop(1).isExpression()
}

fun String.normalizeSuperscript(): String =
  buildString(length) {
    this@normalizeSuperscript.forEach { char ->
      val toAppend =
        when (char) {
          '⁰' -> '0'
          '¹' -> '1'
          '²' -> '2'
          '³' -> '3'
          '⁴' -> '4'
          '⁵' -> '5'
          '⁶' -> '6'
          '⁷' -> '7'
          '⁸' -> '8'
          '⁹' -> '9'
          else -> char
        }
      append(toAppend)
    }
  }
