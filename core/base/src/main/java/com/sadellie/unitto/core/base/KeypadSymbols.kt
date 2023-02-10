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

const val KEY_BASE_A = "A"
const val KEY_BASE_B = "B"
const val KEY_BASE_C = "C"
const val KEY_BASE_D = "D"
const val KEY_BASE_E = "E"
const val KEY_BASE_F = "F"

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
const val KEY_PI = "π"
const val KEY_FACTORIAL = "!"
const val KEY_SIN = "sin("
const val KEY_COS = "cos("
const val KEY_TAN = "tan("
const val KEY_E_SMALL = "e"
const val KEY_MODULO = "#"
const val KEY_LN = "ln("
const val KEY_LOG = "log("
const val KEY_PERCENT = "%"
const val KEY_EVALUATE = "="

val OPERATORS by lazy {
    listOf(
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
}

val DIGITS by lazy {
    listOf(
        KEY_1,
        KEY_2,
        KEY_3,
        KEY_4,
        KEY_5,
        KEY_6,
        KEY_7,
        KEY_8,
        KEY_9,
        KEY_0,
    )
}

val INTERNAL_DISPLAY: Map<String, String> = hashMapOf(
    KEY_MINUS to KEY_MINUS_DISPLAY,
    KEY_MULTIPLY to KEY_MULTIPLY_DISPLAY,
    KEY_DIVIDE to KEY_DIVIDE_DISPLAY
)