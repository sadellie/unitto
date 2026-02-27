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

package io.github.sadellie.evaluatto.ast

import com.sadellie.unitto.core.common.KMathContext
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.MAX_SCALE
import io.github.sadellie.evaluatto.programmer.DataUnit

internal sealed interface ScriptContext {
  data class Math(
    val scale: Int = MAX_SCALE,
    val roundingMode: KRoundingMode = KRoundingMode.HALF_EVEN,
    val mathContext: KMathContext = KMathContext(TRIG_SCALE, roundingMode),
    val radianMode: Boolean = true,
  ) : ScriptContext {
    companion object {
      const val TRIG_SCALE = 100
    }
  }

  data class Programmer(val base: Int, val dataUnit: DataUnit) : ScriptContext
}
