/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

const val KEY_1 = "1"
const val KEY_2 = "2"
const val KEY_3 = "3"
const val KEY_4 = "4"
const val KEY_5 = "5"
const val KEY_6 = "6"
const val KEY_7 = "7"
const val KEY_8 = "8"
const val KEY_9 = "9"
const val KEY_0 = "0"

const val KEY_DOT = "."
const val KEY_COMMA = ","
const val KEY_CLEAR = "<"
const val KEY_E = "E"
const val KEY_PLUS = "+"

const val KEY_MINUS = "-"
const val KEY_MINUS_DISPLAY = "–"

const val KEY_DIVIDE = "/"
const val KEY_DIVIDE_DISPLAY = "÷"

const val KEY_MULTIPLY = "*"
const val KEY_MULTIPLY_DISPLAY = "×"

const val KEY_LEFT_BRACKET = "("
const val KEY_RIGHT_BRACKET = ")"

const val KEY_EXPONENT = "^"

const val KEY_SQRT = "√"

val OPERATORS = listOf(
    KEY_PLUS,
    KEY_MINUS,
    KEY_MINUS_DISPLAY,
    KEY_MULTIPLY,
    KEY_MULTIPLY_DISPLAY,
    KEY_DIVIDE,
    KEY_DIVIDE_DISPLAY,
    KEY_SQRT,
    KEY_EXPONENT,
)

val INTERNAL_DISPLAY: Map<String, String> = hashMapOf(
    KEY_MINUS to KEY_MINUS_DISPLAY,
    KEY_MULTIPLY to KEY_MULTIPLY_DISPLAY,
    KEY_DIVIDE to KEY_DIVIDE_DISPLAY
)