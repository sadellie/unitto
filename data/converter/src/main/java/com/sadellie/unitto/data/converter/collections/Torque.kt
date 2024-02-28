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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import java.math.BigDecimal

val torqueCollection: List<BasicUnit> by lazy {
    listOf(
        NormalUnit(UnitID.dyne_millimeter,           BigDecimal("1"),                      UnitGroup.TORQUE,   R.string.unit_dyne_millimeter,           R.string.unit_dyne_millimeter_short),
        NormalUnit(UnitID.dyne_centimeter,           BigDecimal("10"),                     UnitGroup.TORQUE,   R.string.unit_dyne_centimeter,           R.string.unit_dyne_centimeter_short),
        NormalUnit(UnitID.dyne_meter,                BigDecimal("1000"),                   UnitGroup.TORQUE,   R.string.unit_dyne_meter,                R.string.unit_dyne_meter_short),
        NormalUnit(UnitID.newton_millimeter,         BigDecimal("100000"),                 UnitGroup.TORQUE,   R.string.unit_newton_millimeter,         R.string.unit_newton_millimeter_short),
        NormalUnit(UnitID.newton_centimeter,         BigDecimal("1000000"),                UnitGroup.TORQUE,   R.string.unit_newton_centimeter,         R.string.unit_newton_centimeter_short),
        NormalUnit(UnitID.newton_meter,              BigDecimal("100000000"),              UnitGroup.TORQUE,   R.string.unit_newton_meter,              R.string.unit_newton_meter_short),
        NormalUnit(UnitID.kilonewton_meter,          BigDecimal("100000000000"),           UnitGroup.TORQUE,   R.string.unit_kilonewton_meter,          R.string.unit_kilonewton_meter_short),
        NormalUnit(UnitID.gram_force_millimeter,     BigDecimal("980.665"),                UnitGroup.TORQUE,   R.string.unit_gram_force_millimeter,     R.string.unit_gram_force_millimeter_short),
        NormalUnit(UnitID.gram_force_centimeter,     BigDecimal("9806.65"),                UnitGroup.TORQUE,   R.string.unit_gram_force_centimeter,     R.string.unit_gram_force_centimeter_short),
        NormalUnit(UnitID.kilogram_force_millimeter, BigDecimal("980665"),                 UnitGroup.TORQUE,   R.string.unit_kilogram_force_millimeter, R.string.unit_kilogram_force_millimeter_short),
        NormalUnit(UnitID.gram_force_meter,          BigDecimal("980665"),                 UnitGroup.TORQUE,   R.string.unit_gram_force_meter,          R.string.unit_gram_force_meter_short),
        NormalUnit(UnitID.kilogram_force_centimeter, BigDecimal("9806650"),                UnitGroup.TORQUE,   R.string.unit_kilogram_force_centimeter, R.string.unit_kilogram_force_centimeter_short),
        NormalUnit(UnitID.kilogram_force_meter,      BigDecimal("980665000"),              UnitGroup.TORQUE,   R.string.unit_kilogram_force_meter,      R.string.unit_kilogram_force_meter_short),
        NormalUnit(UnitID.ounce_force_foot,          BigDecimal("8473862.4"),              UnitGroup.TORQUE,   R.string.unit_ounce_force_foot,          R.string.unit_ounce_force_foot_short),
        NormalUnit(UnitID.ounce_force_inch,          BigDecimal("706155.2"),               UnitGroup.TORQUE,   R.string.unit_ounce_force_inch,          R.string.unit_ounce_force_inch_short),
        NormalUnit(UnitID.pound_force_foot,          BigDecimal("135581800"),              UnitGroup.TORQUE,   R.string.unit_pound_force_foot,          R.string.unit_pound_force_foot_short),
        NormalUnit(UnitID.pound_force_inch,          BigDecimal("11298483.333333334"),   UnitGroup.TORQUE,   R.string.unit_pound_force_inch,          R.string.unit_pound_force_inch_short),
    )
}
