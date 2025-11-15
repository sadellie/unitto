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
import unitto.core.common.generated.resources.unit_attoliter
import unitto.core.common.generated.resources.unit_attoliter_short
import unitto.core.common.generated.resources.unit_cubic_centimeter
import unitto.core.common.generated.resources.unit_cubic_centimeter_short
import unitto.core.common.generated.resources.unit_cubic_foot
import unitto.core.common.generated.resources.unit_cubic_foot_short
import unitto.core.common.generated.resources.unit_cubic_inch
import unitto.core.common.generated.resources.unit_cubic_inch_short
import unitto.core.common.generated.resources.unit_cubic_kilometer
import unitto.core.common.generated.resources.unit_cubic_kilometer_short
import unitto.core.common.generated.resources.unit_cubic_meter
import unitto.core.common.generated.resources.unit_cubic_meter_short
import unitto.core.common.generated.resources.unit_cubic_mile
import unitto.core.common.generated.resources.unit_cubic_mile_short
import unitto.core.common.generated.resources.unit_cubic_millimeter
import unitto.core.common.generated.resources.unit_cubic_millimeter_short
import unitto.core.common.generated.resources.unit_cubic_yard
import unitto.core.common.generated.resources.unit_cubic_yard_short
import unitto.core.common.generated.resources.unit_imperial_cup
import unitto.core.common.generated.resources.unit_imperial_cup_short
import unitto.core.common.generated.resources.unit_imperial_fluid_ounce
import unitto.core.common.generated.resources.unit_imperial_fluid_ounce_short
import unitto.core.common.generated.resources.unit_imperial_gallon
import unitto.core.common.generated.resources.unit_imperial_gallon_short
import unitto.core.common.generated.resources.unit_imperial_pint
import unitto.core.common.generated.resources.unit_imperial_pint_short
import unitto.core.common.generated.resources.unit_imperial_quart
import unitto.core.common.generated.resources.unit_imperial_quart_short
import unitto.core.common.generated.resources.unit_imperial_tablespoon
import unitto.core.common.generated.resources.unit_imperial_tablespoon_short
import unitto.core.common.generated.resources.unit_imperial_teaspoon
import unitto.core.common.generated.resources.unit_imperial_teaspoon_short
import unitto.core.common.generated.resources.unit_liter
import unitto.core.common.generated.resources.unit_liter_short
import unitto.core.common.generated.resources.unit_milliliter
import unitto.core.common.generated.resources.unit_milliliter_short
import unitto.core.common.generated.resources.unit_us_fluid_ounce
import unitto.core.common.generated.resources.unit_us_fluid_ounce_short
import unitto.core.common.generated.resources.unit_us_legal_cup
import unitto.core.common.generated.resources.unit_us_legal_cup_short
import unitto.core.common.generated.resources.unit_us_liquid_gallon
import unitto.core.common.generated.resources.unit_us_liquid_gallon_short
import unitto.core.common.generated.resources.unit_us_liquid_pint
import unitto.core.common.generated.resources.unit_us_liquid_pint_short
import unitto.core.common.generated.resources.unit_us_liquid_quart
import unitto.core.common.generated.resources.unit_us_liquid_quart_short
import unitto.core.common.generated.resources.unit_us_tablespoon
import unitto.core.common.generated.resources.unit_us_tablespoon_short
import unitto.core.common.generated.resources.unit_us_teaspoon
import unitto.core.common.generated.resources.unit_us_teaspoon_short

internal val volumeCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attoliter,
      KBigDecimal("1"),
      UnitGroup.VOLUME,
      Res.string.unit_attoliter,
      Res.string.unit_attoliter_short,
    ),
    NormalUnit(
      UnitID.milliliter,
      KBigDecimal("1000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_milliliter,
      Res.string.unit_milliliter_short,
    ),
    NormalUnit(
      UnitID.centiliter,
      KBigDecimal("10000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_milliliter,
      Res.string.unit_milliliter_short,
    ),
    NormalUnit(
      UnitID.deciliter,
      KBigDecimal("100000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_milliliter,
      Res.string.unit_milliliter_short,
    ),
    NormalUnit(
      UnitID.liter,
      KBigDecimal("1000000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_liter,
      Res.string.unit_liter_short,
    ),
    NormalUnit(
      UnitID.us_liquid_gallon,
      KBigDecimal("3785411783999977000"),
      UnitGroup.VOLUME,
      Res.string.unit_us_liquid_gallon,
      Res.string.unit_us_liquid_gallon_short,
    ),
    NormalUnit(
      UnitID.us_liquid_quart,
      KBigDecimal("946352945999994200"),
      UnitGroup.VOLUME,
      Res.string.unit_us_liquid_quart,
      Res.string.unit_us_liquid_quart_short,
    ),
    NormalUnit(
      UnitID.us_liquid_pint,
      KBigDecimal("473176472999997100"),
      UnitGroup.VOLUME,
      Res.string.unit_us_liquid_pint,
      Res.string.unit_us_liquid_pint_short,
    ),
    NormalUnit(
      UnitID.us_legal_cup,
      KBigDecimal("236588236499998560"),
      UnitGroup.VOLUME,
      Res.string.unit_us_legal_cup,
      Res.string.unit_us_legal_cup_short,
    ),
    NormalUnit(
      UnitID.us_fluid_ounce,
      KBigDecimal("29573529562499996"),
      UnitGroup.VOLUME,
      Res.string.unit_us_fluid_ounce,
      Res.string.unit_us_fluid_ounce_short,
    ),
    NormalUnit(
      UnitID.us_tablespoon,
      KBigDecimal("14786764781249998"),
      UnitGroup.VOLUME,
      Res.string.unit_us_tablespoon,
      Res.string.unit_us_tablespoon_short,
    ),
    NormalUnit(
      UnitID.us_teaspoon,
      KBigDecimal("4928921593749952"),
      UnitGroup.VOLUME,
      Res.string.unit_us_teaspoon,
      Res.string.unit_us_teaspoon_short,
    ),
    NormalUnit(
      UnitID.imperial_gallon,
      KBigDecimal("4546089999999954400"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_gallon,
      Res.string.unit_imperial_gallon_short,
    ),
    NormalUnit(
      UnitID.imperial_quart,
      KBigDecimal("1136522500000001400"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_quart,
      Res.string.unit_imperial_quart_short,
    ),
    NormalUnit(
      UnitID.imperial_pint,
      KBigDecimal("568261250000000700"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_pint,
      Res.string.unit_imperial_pint_short,
    ),
    NormalUnit(
      UnitID.imperial_cup,
      KBigDecimal("284130625000000350"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_cup,
      Res.string.unit_imperial_cup_short,
    ),
    NormalUnit(
      UnitID.imperial_fluid_ounce,
      KBigDecimal("28413062500000036"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_fluid_ounce,
      Res.string.unit_imperial_fluid_ounce_short,
    ),
    NormalUnit(
      UnitID.imperial_tablespoon,
      KBigDecimal("17758164062500148"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_tablespoon,
      Res.string.unit_imperial_tablespoon_short,
    ),
    NormalUnit(
      UnitID.imperial_teaspoon,
      KBigDecimal("5919388020833314"),
      UnitGroup.VOLUME,
      Res.string.unit_imperial_teaspoon,
      Res.string.unit_imperial_teaspoon_short,
    ),
    NormalUnit(
      UnitID.cubic_millimeter,
      KBigDecimal("1000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_millimeter,
      Res.string.unit_cubic_millimeter_short,
    ),
    NormalUnit(
      UnitID.cubic_centimeter,
      KBigDecimal("1000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_centimeter,
      Res.string.unit_cubic_centimeter_short,
    ),
    NormalUnit(
      UnitID.cubic_meter,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_meter,
      Res.string.unit_cubic_meter_short,
    ),
    NormalUnit(
      UnitID.cubic_kilometer,
      KBigDecimal("1000000000000000000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_kilometer,
      Res.string.unit_cubic_kilometer_short,
    ),
    NormalUnit(
      UnitID.cubic_inch,
      KBigDecimal("16387064000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_inch,
      Res.string.unit_cubic_inch_short,
    ),
    NormalUnit(
      UnitID.cubic_foot,
      KBigDecimal("28316846590000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_foot,
      Res.string.unit_cubic_foot_short,
    ),
    NormalUnit(
      UnitID.cubic_yard,
      KBigDecimal("764554857983995500000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_yard,
      Res.string.unit_cubic_yard_short,
    ),
    NormalUnit(
      UnitID.cubic_mile,
      KBigDecimal("4168181825440539000000000000000"),
      UnitGroup.VOLUME,
      Res.string.unit_cubic_mile,
      Res.string.unit_cubic_mile_short,
    ),
  )
}
