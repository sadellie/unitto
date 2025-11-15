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
import unitto.core.common.generated.resources.unit_dyne_centimeter
import unitto.core.common.generated.resources.unit_dyne_centimeter_short
import unitto.core.common.generated.resources.unit_dyne_meter
import unitto.core.common.generated.resources.unit_dyne_meter_short
import unitto.core.common.generated.resources.unit_dyne_millimeter
import unitto.core.common.generated.resources.unit_dyne_millimeter_short
import unitto.core.common.generated.resources.unit_gram_force_centimeter
import unitto.core.common.generated.resources.unit_gram_force_centimeter_short
import unitto.core.common.generated.resources.unit_gram_force_meter
import unitto.core.common.generated.resources.unit_gram_force_meter_short
import unitto.core.common.generated.resources.unit_gram_force_millimeter
import unitto.core.common.generated.resources.unit_gram_force_millimeter_short
import unitto.core.common.generated.resources.unit_kilogram_force_centimeter
import unitto.core.common.generated.resources.unit_kilogram_force_centimeter_short
import unitto.core.common.generated.resources.unit_kilogram_force_meter
import unitto.core.common.generated.resources.unit_kilogram_force_meter_short
import unitto.core.common.generated.resources.unit_kilogram_force_millimeter
import unitto.core.common.generated.resources.unit_kilogram_force_millimeter_short
import unitto.core.common.generated.resources.unit_kilonewton_meter
import unitto.core.common.generated.resources.unit_kilonewton_meter_short
import unitto.core.common.generated.resources.unit_newton_centimeter
import unitto.core.common.generated.resources.unit_newton_centimeter_short
import unitto.core.common.generated.resources.unit_newton_meter
import unitto.core.common.generated.resources.unit_newton_meter_short
import unitto.core.common.generated.resources.unit_newton_millimeter
import unitto.core.common.generated.resources.unit_newton_millimeter_short
import unitto.core.common.generated.resources.unit_ounce_force_foot
import unitto.core.common.generated.resources.unit_ounce_force_foot_short
import unitto.core.common.generated.resources.unit_ounce_force_inch
import unitto.core.common.generated.resources.unit_ounce_force_inch_short
import unitto.core.common.generated.resources.unit_pound_force_foot
import unitto.core.common.generated.resources.unit_pound_force_foot_short
import unitto.core.common.generated.resources.unit_pound_force_inch
import unitto.core.common.generated.resources.unit_pound_force_inch_short

val torqueCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.dyne_millimeter,
      KBigDecimal("1"),
      UnitGroup.TORQUE,
      Res.string.unit_dyne_millimeter,
      Res.string.unit_dyne_millimeter_short,
    ),
    NormalUnit(
      UnitID.dyne_centimeter,
      KBigDecimal("10"),
      UnitGroup.TORQUE,
      Res.string.unit_dyne_centimeter,
      Res.string.unit_dyne_centimeter_short,
    ),
    NormalUnit(
      UnitID.dyne_meter,
      KBigDecimal("1000"),
      UnitGroup.TORQUE,
      Res.string.unit_dyne_meter,
      Res.string.unit_dyne_meter_short,
    ),
    NormalUnit(
      UnitID.newton_millimeter,
      KBigDecimal("100000"),
      UnitGroup.TORQUE,
      Res.string.unit_newton_millimeter,
      Res.string.unit_newton_millimeter_short,
    ),
    NormalUnit(
      UnitID.newton_centimeter,
      KBigDecimal("1000000"),
      UnitGroup.TORQUE,
      Res.string.unit_newton_centimeter,
      Res.string.unit_newton_centimeter_short,
    ),
    NormalUnit(
      UnitID.newton_meter,
      KBigDecimal("100000000"),
      UnitGroup.TORQUE,
      Res.string.unit_newton_meter,
      Res.string.unit_newton_meter_short,
    ),
    NormalUnit(
      UnitID.kilonewton_meter,
      KBigDecimal("100000000000"),
      UnitGroup.TORQUE,
      Res.string.unit_kilonewton_meter,
      Res.string.unit_kilonewton_meter_short,
    ),
    NormalUnit(
      UnitID.gram_force_millimeter,
      KBigDecimal("980.665"),
      UnitGroup.TORQUE,
      Res.string.unit_gram_force_millimeter,
      Res.string.unit_gram_force_millimeter_short,
    ),
    NormalUnit(
      UnitID.gram_force_centimeter,
      KBigDecimal("9806.65"),
      UnitGroup.TORQUE,
      Res.string.unit_gram_force_centimeter,
      Res.string.unit_gram_force_centimeter_short,
    ),
    NormalUnit(
      UnitID.kilogram_force_millimeter,
      KBigDecimal("980665"),
      UnitGroup.TORQUE,
      Res.string.unit_kilogram_force_millimeter,
      Res.string.unit_kilogram_force_millimeter_short,
    ),
    NormalUnit(
      UnitID.gram_force_meter,
      KBigDecimal("980665"),
      UnitGroup.TORQUE,
      Res.string.unit_gram_force_meter,
      Res.string.unit_gram_force_meter_short,
    ),
    NormalUnit(
      UnitID.kilogram_force_centimeter,
      KBigDecimal("9806650"),
      UnitGroup.TORQUE,
      Res.string.unit_kilogram_force_centimeter,
      Res.string.unit_kilogram_force_centimeter_short,
    ),
    NormalUnit(
      UnitID.kilogram_force_meter,
      KBigDecimal("980665000"),
      UnitGroup.TORQUE,
      Res.string.unit_kilogram_force_meter,
      Res.string.unit_kilogram_force_meter_short,
    ),
    NormalUnit(
      UnitID.ounce_force_foot,
      KBigDecimal("8473862.4"),
      UnitGroup.TORQUE,
      Res.string.unit_ounce_force_foot,
      Res.string.unit_ounce_force_foot_short,
    ),
    NormalUnit(
      UnitID.ounce_force_inch,
      KBigDecimal("706155.2"),
      UnitGroup.TORQUE,
      Res.string.unit_ounce_force_inch,
      Res.string.unit_ounce_force_inch_short,
    ),
    NormalUnit(
      UnitID.pound_force_foot,
      KBigDecimal("135581800"),
      UnitGroup.TORQUE,
      Res.string.unit_pound_force_foot,
      Res.string.unit_pound_force_foot_short,
    ),
    NormalUnit(
      UnitID.pound_force_inch,
      KBigDecimal("11298483.333333334"),
      UnitGroup.TORQUE,
      Res.string.unit_pound_force_inch,
      Res.string.unit_pound_force_inch_short,
    ),
  )
}
