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
import unitto.core.common.generated.resources.unit_attonewton
import unitto.core.common.generated.resources.unit_attonewton_short
import unitto.core.common.generated.resources.unit_dyne
import unitto.core.common.generated.resources.unit_dyne_short
import unitto.core.common.generated.resources.unit_gram_force
import unitto.core.common.generated.resources.unit_gram_force_short
import unitto.core.common.generated.resources.unit_joule_per_centimeter
import unitto.core.common.generated.resources.unit_joule_per_centimeter_short
import unitto.core.common.generated.resources.unit_joule_per_meter
import unitto.core.common.generated.resources.unit_joule_per_meter_short
import unitto.core.common.generated.resources.unit_kilogram_force
import unitto.core.common.generated.resources.unit_kilogram_force_short
import unitto.core.common.generated.resources.unit_kilonewton
import unitto.core.common.generated.resources.unit_kilonewton_short
import unitto.core.common.generated.resources.unit_kilopond
import unitto.core.common.generated.resources.unit_kilopond_short
import unitto.core.common.generated.resources.unit_kilopound_force
import unitto.core.common.generated.resources.unit_kilopound_force_short
import unitto.core.common.generated.resources.unit_millinewton
import unitto.core.common.generated.resources.unit_millinewton_short
import unitto.core.common.generated.resources.unit_newton
import unitto.core.common.generated.resources.unit_newton_short
import unitto.core.common.generated.resources.unit_ounce_force
import unitto.core.common.generated.resources.unit_ounce_force_short
import unitto.core.common.generated.resources.unit_pond
import unitto.core.common.generated.resources.unit_pond_short
import unitto.core.common.generated.resources.unit_pound_force
import unitto.core.common.generated.resources.unit_pound_force_short
import unitto.core.common.generated.resources.unit_ton_force
import unitto.core.common.generated.resources.unit_ton_force_short

val forceCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attonewton,
      KBigDecimal("1"),
      UnitGroup.FORCE,
      Res.string.unit_attonewton,
      Res.string.unit_attonewton_short,
    ),
    NormalUnit(
      UnitID.dyne,
      KBigDecimal("10000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_dyne,
      Res.string.unit_dyne_short,
    ),
    NormalUnit(
      UnitID.millinewton,
      KBigDecimal("1000000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_millinewton,
      Res.string.unit_millinewton_short,
    ),
    NormalUnit(
      UnitID.joule_per_centimeter,
      KBigDecimal("10000000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_joule_per_centimeter,
      Res.string.unit_joule_per_centimeter_short,
    ),
    NormalUnit(
      UnitID.newton,
      KBigDecimal("1000000000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_newton,
      Res.string.unit_newton_short,
    ),
    NormalUnit(
      UnitID.joule_per_meter,
      KBigDecimal("1000000000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_joule_per_meter,
      Res.string.unit_joule_per_meter_short,
    ),
    NormalUnit(
      UnitID.kilonewton,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_kilonewton,
      Res.string.unit_kilonewton_short,
    ),
    NormalUnit(
      UnitID.gram_force,
      KBigDecimal("9806650000000000"),
      UnitGroup.FORCE,
      Res.string.unit_gram_force,
      Res.string.unit_gram_force_short,
    ),
    NormalUnit(
      UnitID.kilogram_force,
      KBigDecimal("9806650000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_kilogram_force,
      Res.string.unit_kilogram_force_short,
    ),
    NormalUnit(
      UnitID.ton_force,
      KBigDecimal("9806650000000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_ton_force,
      Res.string.unit_ton_force_short,
    ),
    NormalUnit(
      UnitID.ounce_force,
      KBigDecimal("278013850953423000"),
      UnitGroup.FORCE,
      Res.string.unit_ounce_force,
      Res.string.unit_ounce_force_short,
    ),
    NormalUnit(
      UnitID.pound_force,
      KBigDecimal("4448221615255000000"),
      UnitGroup.FORCE,
      Res.string.unit_pound_force,
      Res.string.unit_pound_force_short,
    ),
    NormalUnit(
      UnitID.kilopound_force,
      KBigDecimal("4448221615255000000000"),
      UnitGroup.FORCE,
      Res.string.unit_kilopound_force,
      Res.string.unit_kilopound_force_short,
    ),
    NormalUnit(
      UnitID.pond,
      KBigDecimal("9806650000000000"),
      UnitGroup.FORCE,
      Res.string.unit_pond,
      Res.string.unit_pond_short,
    ),
    NormalUnit(
      UnitID.kilopond,
      KBigDecimal("9806650000000000000"),
      UnitGroup.FORCE,
      Res.string.unit_kilopond,
      Res.string.unit_kilopond_short,
    ),
  )
}
