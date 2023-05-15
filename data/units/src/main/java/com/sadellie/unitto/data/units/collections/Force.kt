/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

val forceCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.attonewton,            BigDecimal.valueOf(1),                      UnitGroup.FORCE,    R.string.attonewton,            R.string.attonewton_short),
        DefaultUnit(MyUnitIDS.dyne,                  BigDecimal.valueOf(1E+13),                  UnitGroup.FORCE,    R.string.dyne,                  R.string.dyne_short),
        DefaultUnit(MyUnitIDS.millinewton,           BigDecimal.valueOf(1E+15),                  UnitGroup.FORCE,    R.string.millinewton,           R.string.millinewton_short),
        DefaultUnit(MyUnitIDS.joule_per_centimeter,  BigDecimal.valueOf(1E+16),                  UnitGroup.FORCE,    R.string.joule_per_centimeter,  R.string.joule_per_centimeter_short),
        DefaultUnit(MyUnitIDS.newton,                BigDecimal.valueOf(1E+18),                  UnitGroup.FORCE,    R.string.newton,                R.string.newton_short),
        DefaultUnit(MyUnitIDS.joule_per_meter,       BigDecimal.valueOf(1E+18),                  UnitGroup.FORCE,    R.string.joule_per_meter,       R.string.joule_per_meter_short),
        DefaultUnit(MyUnitIDS.kilonewton,            BigDecimal.valueOf(1E+21),                  UnitGroup.FORCE,    R.string.kilonewton,            R.string.kilonewton_short),
        DefaultUnit(MyUnitIDS.gram_force,            BigDecimal.valueOf(9.80665E+15),            UnitGroup.FORCE,    R.string.gram_force,            R.string.gram_force_short),
        DefaultUnit(MyUnitIDS.kilogram_force,        BigDecimal.valueOf(9.80665E+18),            UnitGroup.FORCE,    R.string.kilogram_force,        R.string.kilogram_force_short),
        DefaultUnit(MyUnitIDS.ton_force,             BigDecimal.valueOf(9.80665E+21),            UnitGroup.FORCE,    R.string.ton_force,             R.string.ton_force_short),
        DefaultUnit(MyUnitIDS.ounce_force,           BigDecimal.valueOf(2.78013850953423008E17), UnitGroup.FORCE,    R.string.ounce_force,           R.string.ounce_force_short),
        DefaultUnit(MyUnitIDS.pound_force,           BigDecimal.valueOf(4.4482216152550001E18),  UnitGroup.FORCE,    R.string.pound_force,           R.string.pound_force_short),
        DefaultUnit(MyUnitIDS.kilopound_force,       BigDecimal.valueOf(4.448221615255E+21),     UnitGroup.FORCE,    R.string.kilopound_force,       R.string.kilopound_force_short),
        DefaultUnit(MyUnitIDS.pond,                  BigDecimal.valueOf(9.80665E+15),            UnitGroup.FORCE,    R.string.pond,                  R.string.pond_short),
        DefaultUnit(MyUnitIDS.kilopond,              BigDecimal.valueOf(9.80665E+18),            UnitGroup.FORCE,    R.string.kilopond,              R.string.kilopond_short),
    )
}
