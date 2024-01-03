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

package com.sadellie.unitto.data.converter.collections

import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.converter.MyUnitIDS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.BackwardUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import java.math.BigDecimal

val fuelConsumptionCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(  MyUnitIDS.kilometer_per_liter,     BigDecimal.valueOf(1),              UnitGroup.FUEL_CONSUMPTION, R.string.unit_km_per_l,                 R.string.unit_km_per_l_short),
        BackwardUnit(MyUnitIDS.liter_per_kilometer,     BigDecimal.valueOf(1),              UnitGroup.FUEL_CONSUMPTION, R.string.unit_l_per_km,                 R.string.unit_l_per_km_short),
        BackwardUnit(MyUnitIDS.liter_per_100_kilometer, BigDecimal.valueOf(100),            UnitGroup.FUEL_CONSUMPTION, R.string.unit_l_per_100_km,             R.string.unit_l_per_100_km_short),
        NormalUnit(  MyUnitIDS.mile_per_gallon_uk,      BigDecimal.valueOf(0.35400619),     UnitGroup.FUEL_CONSUMPTION, R.string.unit_mi_per_gallon_uk,         R.string.unit_mi_per_gallon_uk_short),
        NormalUnit(  MyUnitIDS.mile_per_gallon_us,      BigDecimal.valueOf(0.4251437075),   UnitGroup.FUEL_CONSUMPTION, R.string.unit_mi_per_gallon_us,         R.string.unit_mi_per_gallon_us_short),
        NormalUnit(  MyUnitIDS.mile_us_per_liter,       BigDecimal.valueOf(1.609344),       UnitGroup.FUEL_CONSUMPTION, R.string.unit_mi_us_per_l,              R.string.unit_mi_us_per_l_short),
        BackwardUnit(MyUnitIDS.gallon_us_per_mile,      BigDecimal.valueOf(0.4251437075),   UnitGroup.FUEL_CONSUMPTION, R.string.unit_gallon_us_per_mile,       R.string.unit_gallon_us_per_mile_short),
        BackwardUnit(MyUnitIDS.gallon_uk_per_mile,      BigDecimal.valueOf(0.35400619),     UnitGroup.FUEL_CONSUMPTION, R.string.unit_gallon_uk_per_mile,       R.string.unit_gallon_uk_per_mile_short),
        BackwardUnit(MyUnitIDS.gallon_us_per_100_mile,  BigDecimal.valueOf(42.51437075),    UnitGroup.FUEL_CONSUMPTION, R.string.unit_gallon_us_per_100_mile,   R.string.unit_gallon_us_per_100_mile_short),
        BackwardUnit(MyUnitIDS.gallon_uk_per_100_mile,  BigDecimal.valueOf(35.400618996),   UnitGroup.FUEL_CONSUMPTION, R.string.unit_gallon_uk_per_100_mile,   R.string.unit_gallon_uk_per_100_mile_short),
    )
}
