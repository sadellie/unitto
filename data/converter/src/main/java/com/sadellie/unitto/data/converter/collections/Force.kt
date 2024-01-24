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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.converter.UnitID
import java.math.BigDecimal

val forceCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.attonewton,            BigDecimal.valueOf(1),                      UnitGroup.FORCE,    R.string.unit_attonewton,            R.string.unit_attonewton_short),
        NormalUnit(UnitID.dyne,                  BigDecimal.valueOf(1E+13),                  UnitGroup.FORCE,    R.string.unit_dyne,                  R.string.unit_dyne_short),
        NormalUnit(UnitID.millinewton,           BigDecimal.valueOf(1E+15),                  UnitGroup.FORCE,    R.string.unit_millinewton,           R.string.unit_millinewton_short),
        NormalUnit(UnitID.joule_per_centimeter,  BigDecimal.valueOf(1E+16),                  UnitGroup.FORCE,    R.string.unit_joule_per_centimeter,  R.string.unit_joule_per_centimeter_short),
        NormalUnit(UnitID.newton,                BigDecimal.valueOf(1E+18),                  UnitGroup.FORCE,    R.string.unit_newton,                R.string.unit_newton_short),
        NormalUnit(UnitID.joule_per_meter,       BigDecimal.valueOf(1E+18),                  UnitGroup.FORCE,    R.string.unit_joule_per_meter,       R.string.unit_joule_per_meter_short),
        NormalUnit(UnitID.kilonewton,            BigDecimal.valueOf(1E+21),                  UnitGroup.FORCE,    R.string.unit_kilonewton,            R.string.unit_kilonewton_short),
        NormalUnit(UnitID.gram_force,            BigDecimal.valueOf(9.80665E+15),            UnitGroup.FORCE,    R.string.unit_gram_force,            R.string.unit_gram_force_short),
        NormalUnit(UnitID.kilogram_force,        BigDecimal.valueOf(9.80665E+18),            UnitGroup.FORCE,    R.string.unit_kilogram_force,        R.string.unit_kilogram_force_short),
        NormalUnit(UnitID.ton_force,             BigDecimal.valueOf(9.80665E+21),            UnitGroup.FORCE,    R.string.unit_ton_force,             R.string.unit_ton_force_short),
        NormalUnit(UnitID.ounce_force,           BigDecimal.valueOf(2.78013850953423008E17), UnitGroup.FORCE,    R.string.unit_ounce_force,           R.string.unit_ounce_force_short),
        NormalUnit(UnitID.pound_force,           BigDecimal.valueOf(4.4482216152550001E18),  UnitGroup.FORCE,    R.string.unit_pound_force,           R.string.unit_pound_force_short),
        NormalUnit(UnitID.kilopound_force,       BigDecimal.valueOf(4.448221615255E+21),     UnitGroup.FORCE,    R.string.unit_kilopound_force,       R.string.unit_kilopound_force_short),
        NormalUnit(UnitID.pond,                  BigDecimal.valueOf(9.80665E+15),            UnitGroup.FORCE,    R.string.unit_pond,                  R.string.unit_pond_short),
        NormalUnit(UnitID.kilopond,              BigDecimal.valueOf(9.80665E+18),            UnitGroup.FORCE,    R.string.unit_kilopond,              R.string.unit_kilopond_short),
    )
}
