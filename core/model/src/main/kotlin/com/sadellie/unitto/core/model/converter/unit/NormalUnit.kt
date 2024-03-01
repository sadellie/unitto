/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.core.model.converter.unit

import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.model.converter.UnitGroup
import java.math.BigDecimal

data class NormalUnit(
  override val id: String,
  override val factor: BigDecimal,
  override val group: UnitGroup,
  override val displayName: Int,
  override val shortName: Int,
  override val backward: Boolean = false,
) : BasicUnit.Default {
  override fun convert(unitTo: BasicUnit.Default, value: BigDecimal): BigDecimal {
    if (value.isEqualTo(BigDecimal.ZERO)) return BigDecimal.ZERO
    return when {
      // BACKWARD -> BACKWARD
      backward and unitTo.backward -> unitTo.factor.setMaxScale().multiply(value).div(this.factor)

      // BACKWARD -> FORWARD
      backward and !unitTo.backward -> this.factor.setMaxScale().div(unitTo.factor).div(value)

      // FORWARD -> BACKWARD
      !backward and unitTo.backward -> unitTo.factor.setMaxScale().div(this.factor).div(value)

      // FORWARD -> FORWARD
      else -> this.factor.setMaxScale().multiply(value).div(unitTo.factor)
    }
    // multiplication increases scale
    .setMaxScale()
  }
}
