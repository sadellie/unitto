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
        NormalUnit(UnitID.electron_mass_rest, BigDecimal("0.00000000000000000000000000091093897"), UnitGroup.MASS, R.string.unit_electron_mass_rest, R.string.unit_electron_mass_rest_short),
        NormalUnit(UnitID.atomic_mass_unit,   BigDecimal("0.0000000000000000000000016605402"), UnitGroup.MASS, R.string.unit_atomic_mass_unit,   R.string.unit_atomic_mass_unit_short),
        NormalUnit(UnitID.microgram,          BigDecimal("0.000001"),                  UnitGroup.MASS, R.string.unit_microgram,          R.string.unit_microgram_short),
        NormalUnit(UnitID.milligram,          BigDecimal("0.001"),          UnitGroup.MASS, R.string.unit_milligram,          R.string.unit_milligram_short),
        NormalUnit(UnitID.grain,              BigDecimal("0.06479891"),    UnitGroup.MASS, R.string.unit_grain,              R.string.unit_grain_short),
        NormalUnit(UnitID.gram,               BigDecimal("1"),             UnitGroup.MASS, R.string.unit_gram,               R.string.unit_gram_short),
        NormalUnit(UnitID.kilogram,           BigDecimal("1000"),          UnitGroup.MASS, R.string.unit_kilogram,           R.string.unit_kilogram_short),
        NormalUnit(UnitID.metric_ton,         BigDecimal("1000000"),          UnitGroup.MASS, R.string.unit_metric_ton,         R.string.unit_metric_ton_short),
        NormalUnit(UnitID.imperial_ton,       BigDecimal("1016046.9088"),  UnitGroup.MASS, R.string.unit_imperial_ton,       R.string.unit_imperial_ton_short),
        NormalUnit(UnitID.ounce,              BigDecimal("28.349523125"),  UnitGroup.MASS, R.string.unit_ounce,              R.string.unit_ounce_short),
        NormalUnit(UnitID.carat,              BigDecimal("0.2"),           UnitGroup.MASS, R.string.unit_carat,              R.string.unit_carat_short),
        NormalUnit(UnitID.pound,              BigDecimal("453.59237"),     UnitGroup.MASS, R.string.unit_pound,              R.string.unit_pound_short),
        NormalUnit(UnitID.mercury_mass,       BigDecimal("330104000000000000000000000"),   UnitGroup.MASS, R.string.unit_mercury_mass,       R.string.unit_mercury_mass_short),
        NormalUnit(UnitID.venus_mass,         BigDecimal("4867320000000000000000000000"),   UnitGroup.MASS, R.string.unit_venus_mass,         R.string.unit_venus_mass_short),
        NormalUnit(UnitID.earth_mass,         BigDecimal("5972190000000000000000000000"),   UnitGroup.MASS, R.string.unit_earth_mass,         R.string.unit_earth_mass_short),
        NormalUnit(UnitID.mars_mass,          BigDecimal("641693000000000000000000000"),   UnitGroup.MASS, R.string.unit_mars_mass,          R.string.unit_mars_mass_short),
        NormalUnit(UnitID.jupiter_mass,       BigDecimal("1898130000000000000000000000000"),   UnitGroup.MASS, R.string.unit_jupiter_mass,       R.string.unit_jupiter_mass_short),
        NormalUnit(UnitID.saturn_mass,        BigDecimal("568319000000000000000000000000"),   UnitGroup.MASS, R.string.unit_saturn_mass,        R.string.unit_saturn_mass_short),
        NormalUnit(UnitID.uranus_mass,        BigDecimal("86810300000000000000000000000"),   UnitGroup.MASS, R.string.unit_uranus_mass,        R.string.unit_uranus_mass_short),
        NormalUnit(UnitID.neptune_mass,       BigDecimal("102410000000000000000000000000"),    UnitGroup.MASS, R.string.unit_neptune_mass,       R.string.unit_neptune_mass_short),
        NormalUnit(UnitID.sun_mass,           BigDecimal("1989100000000000000000000000000000"),    UnitGroup.MASS, R.string.unit_sun_mass,           R.string.unit_sun_mass_short),
    )
}
