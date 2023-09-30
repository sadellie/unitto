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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import java.math.BigDecimal

internal val massCollection: List<AbstractUnit> by lazy {
    listOf(
        NormalUnit(MyUnitIDS.electron_mass_rest, BigDecimal.valueOf(9.1093897E-28), UnitGroup.MASS, R.string.electron_mass_rest, R.string.electron_mass_rest_short),
        NormalUnit(MyUnitIDS.atomic_mass_unit,   BigDecimal.valueOf(1.6605402E-24), UnitGroup.MASS, R.string.atomic_mass_unit,   R.string.atomic_mass_unit_short),
        NormalUnit(MyUnitIDS.microgram,          BigDecimal(1E-6),                  UnitGroup.MASS, R.string.microgram,          R.string.microgram_short),
        NormalUnit(MyUnitIDS.milligram,          BigDecimal.valueOf(1E-3),          UnitGroup.MASS, R.string.milligram,          R.string.milligram_short),
        NormalUnit(MyUnitIDS.gram,               BigDecimal.valueOf(1),             UnitGroup.MASS, R.string.gram,               R.string.gram_short),
        NormalUnit(MyUnitIDS.kilogram,           BigDecimal.valueOf(1E+3),          UnitGroup.MASS, R.string.kilogram,           R.string.kilogram_short),
        NormalUnit(MyUnitIDS.metric_ton,         BigDecimal.valueOf(1E+6),          UnitGroup.MASS, R.string.metric_ton,         R.string.metric_ton_short),
        NormalUnit(MyUnitIDS.imperial_ton,       BigDecimal.valueOf(1016046.9088),  UnitGroup.MASS, R.string.imperial_ton,       R.string.imperial_ton_short),
        NormalUnit(MyUnitIDS.ounce,              BigDecimal.valueOf(28.349523125),  UnitGroup.MASS, R.string.ounce,              R.string.ounce_short),
        NormalUnit(MyUnitIDS.carat,              BigDecimal.valueOf(0.2),           UnitGroup.MASS, R.string.carat,              R.string.carat_short),
        NormalUnit(MyUnitIDS.pound,              BigDecimal.valueOf(453.59237),     UnitGroup.MASS, R.string.pound,              R.string.pound_short),
        NormalUnit(MyUnitIDS.mercury_mass,       BigDecimal.valueOf(3.30104E+26),   UnitGroup.MASS, R.string.mercury_mass,       R.string.mercury_mass_short),
        NormalUnit(MyUnitIDS.venus_mass,         BigDecimal.valueOf(4.86732E+27),   UnitGroup.MASS, R.string.venus_mass,         R.string.venus_mass_short),
        NormalUnit(MyUnitIDS.earth_mass,         BigDecimal.valueOf(5.97219E+27),   UnitGroup.MASS, R.string.earth_mass,         R.string.earth_mass_short),
        NormalUnit(MyUnitIDS.mars_mass,          BigDecimal.valueOf(6.41693E+26),   UnitGroup.MASS, R.string.mars_mass,          R.string.mars_mass_short),
        NormalUnit(MyUnitIDS.jupiter_mass,       BigDecimal.valueOf(1.89813E+30),   UnitGroup.MASS, R.string.jupiter_mass,       R.string.jupiter_mass_short),
        NormalUnit(MyUnitIDS.saturn_mass,        BigDecimal.valueOf(5.68319E+29),   UnitGroup.MASS, R.string.saturn_mass,        R.string.saturn_mass_short),
        NormalUnit(MyUnitIDS.uranus_mass,        BigDecimal.valueOf(8.68103E+28),   UnitGroup.MASS, R.string.uranus_mass,        R.string.uranus_mass_short),
        NormalUnit(MyUnitIDS.neptune_mass,       BigDecimal.valueOf(1.0241E+29),    UnitGroup.MASS, R.string.neptune_mass,       R.string.neptune_mass_short),
        NormalUnit(MyUnitIDS.sun_mass,           BigDecimal.valueOf(1.9891E+33),    UnitGroup.MASS, R.string.sun_mass,           R.string.sun_mass_short),
    )
}
