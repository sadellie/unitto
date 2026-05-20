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

package com.sadellie.unitto.core.data.calculator

import com.sadellie.unitto.core.database.CalculatorHistoryDao
import com.sadellie.unitto.core.database.DatabaseBindings
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides

@BindingContainer(includes = [DatabaseBindings::class])
object CalculatorDataBindings {
  @Provides
  fun provideCalculatorHistoryRepository(
    calculatorHistoryDao: CalculatorHistoryDao
  ): CalculatorHistoryRepository =
    CalculatorHistoryRepositoryImpl(calculatorHistoryDao = calculatorHistoryDao)
}
