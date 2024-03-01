/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import androidx.compose.foundation.text.input.TextFieldState
import com.sadellie.unitto.core.common.FormatterSymbols
import java.time.ZonedDateTime

internal sealed class AddSubtractUIState {
  data object Loading : AddSubtractUIState()

  data class Ready(
      val addition: Boolean,
      val start: ZonedDateTime,
      val result: ZonedDateTime,
      val years: TextFieldState,
      val months: TextFieldState,
      val days: TextFieldState,
      val hours: TextFieldState,
      val minutes: TextFieldState,
      val formatterSymbols: FormatterSymbols,
  ) : AddSubtractUIState()
}
