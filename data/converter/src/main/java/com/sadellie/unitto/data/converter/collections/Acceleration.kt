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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import java.math.BigDecimal

internal val accelerationCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.attometer_per_square_second,  BigDecimal.valueOf(1),           UnitGroup.ACCELERATION, R.string.unit_attometer_per_square_second,  R.string.unit_attometer_per_square_second_short),
        NormalUnit(UnitID.femtometer_per_square_second, BigDecimal.valueOf(1E+3),        UnitGroup.ACCELERATION, R.string.unit_femtometer_per_square_second, R.string.unit_femtometer_per_square_second_short),
        NormalUnit(UnitID.picometer_per_square_second,  BigDecimal.valueOf(1E+6),        UnitGroup.ACCELERATION, R.string.unit_picometer_per_square_second,  R.string.unit_picometer_per_square_second_short),
        NormalUnit(UnitID.nanometer_per_square_second,  BigDecimal.valueOf(1E+9),        UnitGroup.ACCELERATION, R.string.unit_nanometer_per_square_second,  R.string.unit_nanometer_per_square_second_short),
        NormalUnit(UnitID.micrometer_per_square_second, BigDecimal.valueOf(1E+12),       UnitGroup.ACCELERATION, R.string.unit_micrometer_per_square_second, R.string.unit_micrometer_per_square_second_short),
        NormalUnit(UnitID.millimeter_per_square_second, BigDecimal.valueOf(1E+15),       UnitGroup.ACCELERATION, R.string.unit_millimeter_per_square_second, R.string.unit_millimeter_per_square_second_short),
        NormalUnit(UnitID.centimeter_per_square_second, BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.unit_centimeter_per_square_second, R.string.unit_centimeter_per_square_second_short),
        NormalUnit(UnitID.decimeter_per_square_second,  BigDecimal.valueOf(1E+17),       UnitGroup.ACCELERATION, R.string.unit_decimeter_per_square_second,  R.string.unit_decimeter_per_square_second_short),
        NormalUnit(UnitID.meter_per_square_second,      BigDecimal.valueOf(1E+18),       UnitGroup.ACCELERATION, R.string.unit_meter_per_square_second,      R.string.unit_meter_per_square_second_short),
        NormalUnit(UnitID.kilometer_per_square_second,  BigDecimal.valueOf(1E+21),       UnitGroup.ACCELERATION, R.string.unit_kilometer_per_square_second,  R.string.unit_kilometer_per_square_second_short),
        NormalUnit(UnitID.dekameter_per_square_second,  BigDecimal.valueOf(1E+19),       UnitGroup.ACCELERATION, R.string.unit_dekameter_per_square_second,  R.string.unit_dekameter_per_square_second_short),
        NormalUnit(UnitID.hectometer_per_square_second, BigDecimal.valueOf(1E+20),       UnitGroup.ACCELERATION, R.string.unit_hectometer_per_square_second, R.string.unit_hectometer_per_square_second_short),
        NormalUnit(UnitID.gal,                          BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.unit_gal,                          R.string.unit_gal_short),
        NormalUnit(UnitID.mercury_surface_gravity,      BigDecimal.valueOf(3.7E+18),     UnitGroup.ACCELERATION, R.string.unit_mercury_surface_gravity,      R.string.unit_mercury_surface_gravity_short),
        NormalUnit(UnitID.venus_surface_gravity,        BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.unit_venus_surface_gravity,        R.string.unit_venus_surface_gravity_short),
        NormalUnit(UnitID.earth_surface_gravity,        BigDecimal.valueOf(9.80655E+18), UnitGroup.ACCELERATION, R.string.unit_earth_surface_gravity,        R.string.unit_earth_surface_gravity_short),
        NormalUnit(UnitID.mars_surface_gravity,         BigDecimal.valueOf(3.71E+18),    UnitGroup.ACCELERATION, R.string.unit_mars_surface_gravity,         R.string.unit_mars_surface_gravity_short),
        NormalUnit(UnitID.jupiter_surface_gravity,      BigDecimal.valueOf(2.479E+19),   UnitGroup.ACCELERATION, R.string.unit_jupiter_surface_gravity,      R.string.unit_jupiter_surface_gravity_short),
        NormalUnit(UnitID.saturn_surface_gravity,       BigDecimal.valueOf(1.044E+19),   UnitGroup.ACCELERATION, R.string.unit_saturn_surface_gravity,       R.string.unit_saturn_surface_gravity_short),
        NormalUnit(UnitID.uranus_surface_gravity,       BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.unit_uranus_surface_gravity,       R.string.unit_uranus_surface_gravity_short),
        NormalUnit(UnitID.neptune_surface_gravity,      BigDecimal.valueOf(1.115E+19),   UnitGroup.ACCELERATION, R.string.unit_neptune_surface_gravity,      R.string.unit_neptune_surface_gravity_short),
        NormalUnit(UnitID.sun_surface_gravity,          BigDecimal.valueOf(2.74E+20),    UnitGroup.ACCELERATION, R.string.unit_sun_surface_gravity,          R.string.unit_sun_surface_gravity_short),
    )
}
