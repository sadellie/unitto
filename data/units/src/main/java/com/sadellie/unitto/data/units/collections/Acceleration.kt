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

import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.R
import java.math.BigDecimal

internal val accelerationCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.attometer_per_square_second,  BigDecimal.valueOf(1),           UnitGroup.ACCELERATION, R.string.attometer_per_square_second,  R.string.attometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.femtometer_per_square_second, BigDecimal.valueOf(1E+3),        UnitGroup.ACCELERATION, R.string.femtometer_per_square_second, R.string.femtometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.picometer_per_square_second,  BigDecimal.valueOf(1E+6),        UnitGroup.ACCELERATION, R.string.picometer_per_square_second,  R.string.picometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.nanometer_per_square_second,  BigDecimal.valueOf(1E+9),        UnitGroup.ACCELERATION, R.string.nanometer_per_square_second,  R.string.nanometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.micrometer_per_square_second, BigDecimal.valueOf(1E+12),       UnitGroup.ACCELERATION, R.string.micrometer_per_square_second, R.string.micrometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.millimeter_per_square_second, BigDecimal.valueOf(1E+15),       UnitGroup.ACCELERATION, R.string.millimeter_per_square_second, R.string.millimeter_per_square_second_short),
        DefaultUnit(MyUnitIDS.centimeter_per_square_second, BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.centimeter_per_square_second, R.string.centimeter_per_square_second_short),
        DefaultUnit(MyUnitIDS.decimeter_per_square_second,  BigDecimal.valueOf(1E+17),       UnitGroup.ACCELERATION, R.string.decimeter_per_square_second,  R.string.decimeter_per_square_second_short),
        DefaultUnit(MyUnitIDS.meter_per_square_second,      BigDecimal.valueOf(1E+18),       UnitGroup.ACCELERATION, R.string.meter_per_square_second,      R.string.meter_per_square_second_short),
        DefaultUnit(MyUnitIDS.kilometer_per_square_second,  BigDecimal.valueOf(1E+21),       UnitGroup.ACCELERATION, R.string.kilometer_per_square_second,  R.string.kilometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.dekameter_per_square_second,  BigDecimal.valueOf(1E+19),       UnitGroup.ACCELERATION, R.string.dekameter_per_square_second,  R.string.dekameter_per_square_second_short),
        DefaultUnit(MyUnitIDS.hectometer_per_square_second, BigDecimal.valueOf(1E+20),       UnitGroup.ACCELERATION, R.string.hectometer_per_square_second, R.string.hectometer_per_square_second_short),
        DefaultUnit(MyUnitIDS.gal,                          BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.gal,                          R.string.gal_short),
        DefaultUnit(MyUnitIDS.mercury_surface_gravity,      BigDecimal.valueOf(3.7E+18),     UnitGroup.ACCELERATION, R.string.mercury_surface_gravity,      R.string.mercury_surface_gravity_short),
        DefaultUnit(MyUnitIDS.venus_surface_gravity,        BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.venus_surface_gravity,        R.string.venus_surface_gravity_short),
        DefaultUnit(MyUnitIDS.earth_surface_gravity,        BigDecimal.valueOf(9.80655E+18), UnitGroup.ACCELERATION, R.string.earth_surface_gravity,        R.string.earth_surface_gravity_short),
        DefaultUnit(MyUnitIDS.mars_surface_gravity,         BigDecimal.valueOf(3.71E+18),    UnitGroup.ACCELERATION, R.string.mars_surface_gravity,         R.string.mars_surface_gravity_short),
        DefaultUnit(MyUnitIDS.jupiter_surface_gravity,      BigDecimal.valueOf(2.479E+19),   UnitGroup.ACCELERATION, R.string.jupiter_surface_gravity,      R.string.jupiter_surface_gravity_short),
        DefaultUnit(MyUnitIDS.saturn_surface_gravity,       BigDecimal.valueOf(1.044E+19),   UnitGroup.ACCELERATION, R.string.saturn_surface_gravity,       R.string.saturn_surface_gravity_short),
        DefaultUnit(MyUnitIDS.uranus_surface_gravity,       BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.uranus_surface_gravity,       R.string.uranus_surface_gravity_short),
        DefaultUnit(MyUnitIDS.neptune_surface_gravity,      BigDecimal.valueOf(1.115E+19),   UnitGroup.ACCELERATION, R.string.neptune_surface_gravity,      R.string.neptune_surface_gravity_short),
        DefaultUnit(MyUnitIDS.sun_surface_gravity,          BigDecimal.valueOf(2.74E+20),    UnitGroup.ACCELERATION, R.string.sun_surface_gravity,          R.string.sun_surface_gravity_short),
    )
}