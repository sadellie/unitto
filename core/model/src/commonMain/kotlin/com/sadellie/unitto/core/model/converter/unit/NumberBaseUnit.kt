/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KBigInteger
import com.sadellie.unitto.core.model.converter.UnitGroup
import org.jetbrains.compose.resources.StringResource

data class NumberBaseUnit(
  override val id: String,
  override val factor: KBigDecimal,
  override val group: UnitGroup,
  override val displayName: StringResource,
  override val shortName: StringResource,
) : BasicUnit.NumberBase {
  override fun convert(unitTo: BasicUnit.NumberBase, value: String): String =
    KBigInteger(value, factor.intValueExact()).toString(unitTo.factor.intValueExact())
}
