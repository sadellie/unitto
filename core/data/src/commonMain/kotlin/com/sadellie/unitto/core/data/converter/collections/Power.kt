/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_attowatt
import unitto.core.common.generated.resources.unit_attowatt_short
import unitto.core.common.generated.resources.unit_horse_power_mechanical
import unitto.core.common.generated.resources.unit_horse_power_mechanical_short
import unitto.core.common.generated.resources.unit_kilowatt
import unitto.core.common.generated.resources.unit_kilowatt_short
import unitto.core.common.generated.resources.unit_megawatt
import unitto.core.common.generated.resources.unit_megawatt_short
import unitto.core.common.generated.resources.unit_watt
import unitto.core.common.generated.resources.unit_watt_short

internal val powerCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attowatt,
      KBigDecimal("1"),
      UnitGroup.POWER,
      Res.string.unit_attowatt,
      Res.string.unit_attowatt_short,
    ),
    NormalUnit(
      UnitID.watt,
      KBigDecimal("1000000000000000000"),
      UnitGroup.POWER,
      Res.string.unit_watt,
      Res.string.unit_watt_short,
    ),
    NormalUnit(
      UnitID.kilowatt,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.POWER,
      Res.string.unit_kilowatt,
      Res.string.unit_kilowatt_short,
    ),
    NormalUnit(
      UnitID.megawatt,
      KBigDecimal("1000000000000000000000000"),
      UnitGroup.POWER,
      Res.string.unit_megawatt,
      Res.string.unit_megawatt_short,
    ),
    NormalUnit(
      UnitID.horse_power_mechanical,
      KBigDecimal("745699871582285700000"),
      UnitGroup.POWER,
      Res.string.unit_horse_power_mechanical,
      Res.string.unit_horse_power_mechanical_short,
    ),
  )
}
