/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_atomic_mass_unit
import unitto.core.common.generated.resources.unit_atomic_mass_unit_short
import unitto.core.common.generated.resources.unit_carat
import unitto.core.common.generated.resources.unit_carat_short
import unitto.core.common.generated.resources.unit_earth_mass
import unitto.core.common.generated.resources.unit_earth_mass_short
import unitto.core.common.generated.resources.unit_electron_mass_rest
import unitto.core.common.generated.resources.unit_electron_mass_rest_short
import unitto.core.common.generated.resources.unit_grain
import unitto.core.common.generated.resources.unit_grain_short
import unitto.core.common.generated.resources.unit_gram
import unitto.core.common.generated.resources.unit_gram_short
import unitto.core.common.generated.resources.unit_imperial_ton
import unitto.core.common.generated.resources.unit_imperial_ton_short
import unitto.core.common.generated.resources.unit_jupiter_mass
import unitto.core.common.generated.resources.unit_jupiter_mass_short
import unitto.core.common.generated.resources.unit_kilogram
import unitto.core.common.generated.resources.unit_kilogram_short
import unitto.core.common.generated.resources.unit_mars_mass
import unitto.core.common.generated.resources.unit_mars_mass_short
import unitto.core.common.generated.resources.unit_mercury_mass
import unitto.core.common.generated.resources.unit_mercury_mass_short
import unitto.core.common.generated.resources.unit_metric_ton
import unitto.core.common.generated.resources.unit_metric_ton_short
import unitto.core.common.generated.resources.unit_microgram
import unitto.core.common.generated.resources.unit_microgram_short
import unitto.core.common.generated.resources.unit_milligram
import unitto.core.common.generated.resources.unit_milligram_short
import unitto.core.common.generated.resources.unit_neptune_mass
import unitto.core.common.generated.resources.unit_neptune_mass_short
import unitto.core.common.generated.resources.unit_ounce
import unitto.core.common.generated.resources.unit_ounce_short
import unitto.core.common.generated.resources.unit_pound
import unitto.core.common.generated.resources.unit_pound_short
import unitto.core.common.generated.resources.unit_saturn_mass
import unitto.core.common.generated.resources.unit_saturn_mass_short
import unitto.core.common.generated.resources.unit_stone_uk
import unitto.core.common.generated.resources.unit_stone_uk_short
import unitto.core.common.generated.resources.unit_sun_mass
import unitto.core.common.generated.resources.unit_sun_mass_short
import unitto.core.common.generated.resources.unit_uranus_mass
import unitto.core.common.generated.resources.unit_uranus_mass_short
import unitto.core.common.generated.resources.unit_venus_mass
import unitto.core.common.generated.resources.unit_venus_mass_short

internal val massCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.electron_mass_rest,
      KBigDecimal("0.00000000000000000000000000091093897"),
      UnitGroup.MASS,
      Res.string.unit_electron_mass_rest,
      Res.string.unit_electron_mass_rest_short,
    ),
    NormalUnit(
      UnitID.atomic_mass_unit,
      KBigDecimal("0.0000000000000000000000016605402"),
      UnitGroup.MASS,
      Res.string.unit_atomic_mass_unit,
      Res.string.unit_atomic_mass_unit_short,
    ),
    NormalUnit(
      UnitID.microgram,
      KBigDecimal("0.000001"),
      UnitGroup.MASS,
      Res.string.unit_microgram,
      Res.string.unit_microgram_short,
    ),
    NormalUnit(
      UnitID.milligram,
      KBigDecimal("0.001"),
      UnitGroup.MASS,
      Res.string.unit_milligram,
      Res.string.unit_milligram_short,
    ),
    NormalUnit(
      UnitID.grain,
      KBigDecimal("0.06479891"),
      UnitGroup.MASS,
      Res.string.unit_grain,
      Res.string.unit_grain_short,
    ),
    NormalUnit(
      UnitID.gram,
      KBigDecimal("1"),
      UnitGroup.MASS,
      Res.string.unit_gram,
      Res.string.unit_gram_short,
    ),
    NormalUnit(
      UnitID.kilogram,
      KBigDecimal("1000"),
      UnitGroup.MASS,
      Res.string.unit_kilogram,
      Res.string.unit_kilogram_short,
    ),
    NormalUnit(
      UnitID.metric_ton,
      KBigDecimal("1000000"),
      UnitGroup.MASS,
      Res.string.unit_metric_ton,
      Res.string.unit_metric_ton_short,
    ),
    NormalUnit(
      UnitID.imperial_ton,
      KBigDecimal("1016046.9088"),
      UnitGroup.MASS,
      Res.string.unit_imperial_ton,
      Res.string.unit_imperial_ton_short,
    ),
    NormalUnit(
      UnitID.ounce,
      KBigDecimal("28.349523125"),
      UnitGroup.MASS,
      Res.string.unit_ounce,
      Res.string.unit_ounce_short,
    ),
    NormalUnit(
      UnitID.carat,
      KBigDecimal("0.2"),
      UnitGroup.MASS,
      Res.string.unit_carat,
      Res.string.unit_carat_short,
    ),
    NormalUnit(
      UnitID.pound,
      KBigDecimal("453.59237"),
      UnitGroup.MASS,
      Res.string.unit_pound,
      Res.string.unit_pound_short,
    ),
    NormalUnit(
      UnitID.stone_uk,
      KBigDecimal("6350.29318"),
      UnitGroup.MASS,
      Res.string.unit_stone_uk,
      Res.string.unit_stone_uk_short,
    ),
    NormalUnit(
      UnitID.mercury_mass,
      KBigDecimal("330104000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_mercury_mass,
      Res.string.unit_mercury_mass_short,
    ),
    NormalUnit(
      UnitID.venus_mass,
      KBigDecimal("4867320000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_venus_mass,
      Res.string.unit_venus_mass_short,
    ),
    NormalUnit(
      UnitID.earth_mass,
      KBigDecimal("5972190000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_earth_mass,
      Res.string.unit_earth_mass_short,
    ),
    NormalUnit(
      UnitID.mars_mass,
      KBigDecimal("641693000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_mars_mass,
      Res.string.unit_mars_mass_short,
    ),
    NormalUnit(
      UnitID.jupiter_mass,
      KBigDecimal("1898130000000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_jupiter_mass,
      Res.string.unit_jupiter_mass_short,
    ),
    NormalUnit(
      UnitID.saturn_mass,
      KBigDecimal("568319000000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_saturn_mass,
      Res.string.unit_saturn_mass_short,
    ),
    NormalUnit(
      UnitID.uranus_mass,
      KBigDecimal("86810300000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_uranus_mass,
      Res.string.unit_uranus_mass_short,
    ),
    NormalUnit(
      UnitID.neptune_mass,
      KBigDecimal("102410000000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_neptune_mass,
      Res.string.unit_neptune_mass_short,
    ),
    NormalUnit(
      UnitID.sun_mass,
      KBigDecimal("1989100000000000000000000000000000"),
      UnitGroup.MASS,
      Res.string.unit_sun_mass,
      Res.string.unit_sun_mass_short,
    ),
  )
}
