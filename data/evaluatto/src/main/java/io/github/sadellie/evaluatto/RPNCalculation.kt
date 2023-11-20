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

package io.github.sadellie.evaluatto

sealed class RPNCalculation {
    data object Negate : RPNCalculation()

    data object Clear : RPNCalculation()
    data object Enter : RPNCalculation()
    data object RotateUp : RPNCalculation()
    data object RotateDown : RPNCalculation()
    data object Swap : RPNCalculation()
    data object Pop : RPNCalculation()

    data object Plus : RPNCalculation()
    data object Minus : RPNCalculation()
    data object Multiply : RPNCalculation()
    data object Divide : RPNCalculation()
    data object Percent : RPNCalculation()
    data object Power : RPNCalculation() // unused
}
