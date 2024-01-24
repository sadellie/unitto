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

internal val massCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(UnitID.electron_mass_rest, BigDecimal.valueOf(9.1093897E-28), UnitGroup.MASS, R.string.unit_electron_mass_rest, R.string.unit_electron_mass_rest_short),
        NormalUnit(UnitID.atomic_mass_unit,   BigDecimal.valueOf(1.6605402E-24), UnitGroup.MASS, R.string.unit_atomic_mass_unit,   R.string.unit_atomic_mass_unit_short),
        NormalUnit(UnitID.microgram,          BigDecimal(1E-6),                  UnitGroup.MASS, R.string.unit_microgram,          R.string.unit_microgram_short),
        NormalUnit(UnitID.milligram,          BigDecimal.valueOf(1E-3),          UnitGroup.MASS, R.string.unit_milligram,          R.string.unit_milligram_short),
        NormalUnit(UnitID.grain,              BigDecimal.valueOf(0.06479891),    UnitGroup.MASS, R.string.unit_grain,              R.string.unit_grain_short),
        NormalUnit(UnitID.gram,               BigDecimal.valueOf(1),             UnitGroup.MASS, R.string.unit_gram,               R.string.unit_gram_short),
        NormalUnit(UnitID.kilogram,           BigDecimal.valueOf(1E+3),          UnitGroup.MASS, R.string.unit_kilogram,           R.string.unit_kilogram_short),
        NormalUnit(UnitID.metric_ton,         BigDecimal.valueOf(1E+6),          UnitGroup.MASS, R.string.unit_metric_ton,         R.string.unit_metric_ton_short),
        NormalUnit(UnitID.imperial_ton,       BigDecimal.valueOf(1016046.9088),  UnitGroup.MASS, R.string.unit_imperial_ton,       R.string.unit_imperial_ton_short),
        NormalUnit(UnitID.ounce,              BigDecimal.valueOf(28.349523125),  UnitGroup.MASS, R.string.unit_ounce,              R.string.unit_ounce_short),
        NormalUnit(UnitID.carat,              BigDecimal.valueOf(0.2),           UnitGroup.MASS, R.string.unit_carat,              R.string.unit_carat_short),
        NormalUnit(UnitID.pound,              BigDecimal.valueOf(453.59237),     UnitGroup.MASS, R.string.unit_pound,              R.string.unit_pound_short),
        NormalUnit(UnitID.mercury_mass,       BigDecimal.valueOf(3.30104E+26),   UnitGroup.MASS, R.string.unit_mercury_mass,       R.string.unit_mercury_mass_short),
        NormalUnit(UnitID.venus_mass,         BigDecimal.valueOf(4.86732E+27),   UnitGroup.MASS, R.string.unit_venus_mass,         R.string.unit_venus_mass_short),
        NormalUnit(UnitID.earth_mass,         BigDecimal.valueOf(5.97219E+27),   UnitGroup.MASS, R.string.unit_earth_mass,         R.string.unit_earth_mass_short),
        NormalUnit(UnitID.mars_mass,          BigDecimal.valueOf(6.41693E+26),   UnitGroup.MASS, R.string.unit_mars_mass,          R.string.unit_mars_mass_short),
        NormalUnit(UnitID.jupiter_mass,       BigDecimal.valueOf(1.89813E+30),   UnitGroup.MASS, R.string.unit_jupiter_mass,       R.string.unit_jupiter_mass_short),
        NormalUnit(UnitID.saturn_mass,        BigDecimal.valueOf(5.68319E+29),   UnitGroup.MASS, R.string.unit_saturn_mass,        R.string.unit_saturn_mass_short),
        NormalUnit(UnitID.uranus_mass,        BigDecimal.valueOf(8.68103E+28),   UnitGroup.MASS, R.string.unit_uranus_mass,        R.string.unit_uranus_mass_short),
        NormalUnit(UnitID.neptune_mass,       BigDecimal.valueOf(1.0241E+29),    UnitGroup.MASS, R.string.unit_neptune_mass,       R.string.unit_neptune_mass_short),
        NormalUnit(UnitID.sun_mass,           BigDecimal.valueOf(1.9891E+33),    UnitGroup.MASS, R.string.unit_sun_mass,           R.string.unit_sun_mass_short),
    )
}
