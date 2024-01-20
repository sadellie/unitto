/*
 * Unitto is a unit converter for Android
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

internal val areaCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.cent,                      BigDecimal.valueOf(6.083246572E+33),        UnitGroup.AREA, R.string.unit_cent,                      R.string.unit_cent_short),
        NormalUnit(UnitID.acre,                      BigDecimal.valueOf(6.083246572E+31),        UnitGroup.AREA, R.string.unit_acre,                      R.string.unit_acre_short),
        NormalUnit(UnitID.hectare,                   BigDecimal.valueOf(1.503202964E+32),        UnitGroup.AREA, R.string.unit_hectare,                   R.string.unit_hectare_short ),
        NormalUnit(UnitID.square_foot,               BigDecimal.valueOf(1.396521251E+27),        UnitGroup.AREA, R.string.unit_square_foot,               R.string.unit_square_foot_short),
        NormalUnit(UnitID.square_mile,               BigDecimal.valueOf(3.893277806E+34),        UnitGroup.AREA, R.string.unit_square_mile,               R.string.unit_square_mile_short),
        NormalUnit(UnitID.square_yard,               BigDecimal.valueOf(1.256869126E+28),        UnitGroup.AREA, R.string.unit_square_yard,               R.string.unit_square_yard_short),
        NormalUnit(UnitID.square_inch,               BigDecimal.valueOf(9.698064247E+24),        UnitGroup.AREA, R.string.unit_square_inch,               R.string.unit_square_inch_short),
        NormalUnit(UnitID.square_micrometer,         BigDecimal.valueOf(1.503202964E+16),        UnitGroup.AREA, R.string.unit_square_micrometer,         R.string.unit_square_micrometer_short),
        NormalUnit(UnitID.square_millimeter,         BigDecimal.valueOf(1.503202964E+22),        UnitGroup.AREA, R.string.unit_square_millimeter,         R.string.unit_square_millimeter_short),
        NormalUnit(UnitID.square_centimeter,         BigDecimal.valueOf(1.503202964E+24),        UnitGroup.AREA, R.string.unit_square_centimeter,         R.string.unit_square_centimeter_short),
        NormalUnit(UnitID.square_decimeter,          BigDecimal.valueOf(1.503202964E+26),        UnitGroup.AREA, R.string.unit_square_decimeter,          R.string.unit_square_decimeter_short),
        NormalUnit(UnitID.square_meter,              BigDecimal.valueOf(1.503202964E+28),        UnitGroup.AREA, R.string.unit_square_meter,              R.string.unit_square_meter_short),
        NormalUnit(UnitID.square_kilometer,          BigDecimal.valueOf(1.503202964E+34),        UnitGroup.AREA, R.string.unit_square_kilometer,          R.string.unit_square_kilometer_short),
        NormalUnit(UnitID.electron_cross_section,    BigDecimal.valueOf(1.0),                    UnitGroup.AREA, R.string.unit_electron_cross_section,    R.string.unit_electron_cross_section_short),
    )
}
