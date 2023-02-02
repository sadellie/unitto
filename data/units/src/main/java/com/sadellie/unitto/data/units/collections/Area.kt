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

import com.sadellie.unitto.data.units.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.unitgroups.UnitGroup
import java.math.BigDecimal

internal val areaCollection: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.cent,                      BigDecimal.valueOf(6.083246572E+33),        UnitGroup.AREA, R.string.cent,                      R.string.cent_short),
        MyUnit(MyUnitIDS.acre,                      BigDecimal.valueOf(6.083246572E+31),        UnitGroup.AREA, R.string.acre,                      R.string.acre_short),
        MyUnit(MyUnitIDS.hectare,                   BigDecimal.valueOf(1.503202964E+32),        UnitGroup.AREA, R.string.hectare,                   R.string.hectare_short ),
        MyUnit(MyUnitIDS.square_foot,               BigDecimal.valueOf(1.396521251E+27),        UnitGroup.AREA, R.string.square_foot,               R.string.square_foot_short),
        MyUnit(MyUnitIDS.square_mile,               BigDecimal.valueOf(3.893277806E+34),        UnitGroup.AREA, R.string.square_mile,               R.string.square_mile_short),
        MyUnit(MyUnitIDS.square_yard,               BigDecimal.valueOf(1.256869126E+28),        UnitGroup.AREA, R.string.square_yard,               R.string.square_yard_short),
        MyUnit(MyUnitIDS.square_inch,               BigDecimal.valueOf(9.698064247E+24),        UnitGroup.AREA, R.string.square_inch,               R.string.square_inch_short),
        MyUnit(MyUnitIDS.square_micrometer,         BigDecimal.valueOf(1.503202964E+16),        UnitGroup.AREA, R.string.square_micrometer,         R.string.square_micrometer_short),
        MyUnit(MyUnitIDS.square_millimeter,         BigDecimal.valueOf(1.503202964E+22),        UnitGroup.AREA, R.string.square_millimeter,         R.string.square_millimeter_short),
        MyUnit(MyUnitIDS.square_centimeter,         BigDecimal.valueOf(1.503202964E+24),        UnitGroup.AREA, R.string.square_centimeter,         R.string.square_centimeter_short),
        MyUnit(MyUnitIDS.square_decimeter,          BigDecimal.valueOf(1.503202964E+26),        UnitGroup.AREA, R.string.square_decimeter,          R.string.square_decimeter_short),
        MyUnit(MyUnitIDS.square_meter,              BigDecimal.valueOf(1.503202964E+28),        UnitGroup.AREA, R.string.square_meter,              R.string.square_meter_short),
        MyUnit(MyUnitIDS.square_kilometer,          BigDecimal.valueOf(1.503202964E+34),        UnitGroup.AREA, R.string.square_kilometer,          R.string.square_kilometer_short),
        MyUnit(MyUnitIDS.electron_cross_section,    BigDecimal.valueOf(1.0),                    UnitGroup.AREA, R.string.electron_cross_section,    R.string.electron_cross_section_short),
    )
}