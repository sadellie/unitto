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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.converter.MyUnitIDS
import java.math.BigDecimal

internal val accelerationCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.attometer_per_square_second,  BigDecimal.valueOf(1),           UnitGroup.ACCELERATION, R.string.unit_attometer_per_square_second,  R.string.unit_attometer_per_square_second_short),
        NormalUnit(MyUnitIDS.femtometer_per_square_second, BigDecimal.valueOf(1E+3),        UnitGroup.ACCELERATION, R.string.unit_femtometer_per_square_second, R.string.unit_femtometer_per_square_second_short),
        NormalUnit(MyUnitIDS.picometer_per_square_second,  BigDecimal.valueOf(1E+6),        UnitGroup.ACCELERATION, R.string.unit_picometer_per_square_second,  R.string.unit_picometer_per_square_second_short),
        NormalUnit(MyUnitIDS.nanometer_per_square_second,  BigDecimal.valueOf(1E+9),        UnitGroup.ACCELERATION, R.string.unit_nanometer_per_square_second,  R.string.unit_nanometer_per_square_second_short),
        NormalUnit(MyUnitIDS.micrometer_per_square_second, BigDecimal.valueOf(1E+12),       UnitGroup.ACCELERATION, R.string.unit_micrometer_per_square_second, R.string.unit_micrometer_per_square_second_short),
        NormalUnit(MyUnitIDS.millimeter_per_square_second, BigDecimal.valueOf(1E+15),       UnitGroup.ACCELERATION, R.string.unit_millimeter_per_square_second, R.string.unit_millimeter_per_square_second_short),
        NormalUnit(MyUnitIDS.centimeter_per_square_second, BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.unit_centimeter_per_square_second, R.string.unit_centimeter_per_square_second_short),
        NormalUnit(MyUnitIDS.decimeter_per_square_second,  BigDecimal.valueOf(1E+17),       UnitGroup.ACCELERATION, R.string.unit_decimeter_per_square_second,  R.string.unit_decimeter_per_square_second_short),
        NormalUnit(MyUnitIDS.meter_per_square_second,      BigDecimal.valueOf(1E+18),       UnitGroup.ACCELERATION, R.string.unit_meter_per_square_second,      R.string.unit_meter_per_square_second_short),
        NormalUnit(MyUnitIDS.kilometer_per_square_second,  BigDecimal.valueOf(1E+21),       UnitGroup.ACCELERATION, R.string.unit_kilometer_per_square_second,  R.string.unit_kilometer_per_square_second_short),
        NormalUnit(MyUnitIDS.dekameter_per_square_second,  BigDecimal.valueOf(1E+19),       UnitGroup.ACCELERATION, R.string.unit_dekameter_per_square_second,  R.string.unit_dekameter_per_square_second_short),
        NormalUnit(MyUnitIDS.hectometer_per_square_second, BigDecimal.valueOf(1E+20),       UnitGroup.ACCELERATION, R.string.unit_hectometer_per_square_second, R.string.unit_hectometer_per_square_second_short),
        NormalUnit(MyUnitIDS.gal,                          BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.unit_gal,                          R.string.unit_gal_short),
        NormalUnit(MyUnitIDS.mercury_surface_gravity,      BigDecimal.valueOf(3.7E+18),     UnitGroup.ACCELERATION, R.string.unit_mercury_surface_gravity,      R.string.unit_mercury_surface_gravity_short),
        NormalUnit(MyUnitIDS.venus_surface_gravity,        BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.unit_venus_surface_gravity,        R.string.unit_venus_surface_gravity_short),
        NormalUnit(MyUnitIDS.earth_surface_gravity,        BigDecimal.valueOf(9.80655E+18), UnitGroup.ACCELERATION, R.string.unit_earth_surface_gravity,        R.string.unit_earth_surface_gravity_short),
        NormalUnit(MyUnitIDS.mars_surface_gravity,         BigDecimal.valueOf(3.71E+18),    UnitGroup.ACCELERATION, R.string.unit_mars_surface_gravity,         R.string.unit_mars_surface_gravity_short),
        NormalUnit(MyUnitIDS.jupiter_surface_gravity,      BigDecimal.valueOf(2.479E+19),   UnitGroup.ACCELERATION, R.string.unit_jupiter_surface_gravity,      R.string.unit_jupiter_surface_gravity_short),
        NormalUnit(MyUnitIDS.saturn_surface_gravity,       BigDecimal.valueOf(1.044E+19),   UnitGroup.ACCELERATION, R.string.unit_saturn_surface_gravity,       R.string.unit_saturn_surface_gravity_short),
        NormalUnit(MyUnitIDS.uranus_surface_gravity,       BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.unit_uranus_surface_gravity,       R.string.unit_uranus_surface_gravity_short),
        NormalUnit(MyUnitIDS.neptune_surface_gravity,      BigDecimal.valueOf(1.115E+19),   UnitGroup.ACCELERATION, R.string.unit_neptune_surface_gravity,      R.string.unit_neptune_surface_gravity_short),
        NormalUnit(MyUnitIDS.sun_surface_gravity,          BigDecimal.valueOf(2.74E+20),    UnitGroup.ACCELERATION, R.string.unit_sun_surface_gravity,          R.string.unit_sun_surface_gravity_short),
    )
}
