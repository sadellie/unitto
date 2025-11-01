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
import unitto.core.common.generated.resources.unit_attopascal
import unitto.core.common.generated.resources.unit_attopascal_short
import unitto.core.common.generated.resources.unit_bar
import unitto.core.common.generated.resources.unit_bar_short
import unitto.core.common.generated.resources.unit_centipascal
import unitto.core.common.generated.resources.unit_centipascal_short
import unitto.core.common.generated.resources.unit_decipascal
import unitto.core.common.generated.resources.unit_decipascal_short
import unitto.core.common.generated.resources.unit_dekapascal
import unitto.core.common.generated.resources.unit_dekapascal_short
import unitto.core.common.generated.resources.unit_exapascal
import unitto.core.common.generated.resources.unit_exapascal_short
import unitto.core.common.generated.resources.unit_femtopascal
import unitto.core.common.generated.resources.unit_femtopascal_short
import unitto.core.common.generated.resources.unit_gigapascal
import unitto.core.common.generated.resources.unit_gigapascal_short
import unitto.core.common.generated.resources.unit_gram_force_per_square_centimeter
import unitto.core.common.generated.resources.unit_gram_force_per_square_centimeter_short
import unitto.core.common.generated.resources.unit_hectopascal
import unitto.core.common.generated.resources.unit_hectopascal_short
import unitto.core.common.generated.resources.unit_kilogram_force_per_square_centimeter
import unitto.core.common.generated.resources.unit_kilogram_force_per_square_centimeter_short
import unitto.core.common.generated.resources.unit_kilogram_force_per_square_meter
import unitto.core.common.generated.resources.unit_kilogram_force_per_square_meter_short
import unitto.core.common.generated.resources.unit_kilopascal
import unitto.core.common.generated.resources.unit_kilopascal_short
import unitto.core.common.generated.resources.unit_ksi
import unitto.core.common.generated.resources.unit_ksi_short
import unitto.core.common.generated.resources.unit_megapascal
import unitto.core.common.generated.resources.unit_megapascal_short
import unitto.core.common.generated.resources.unit_micron_of_mercury
import unitto.core.common.generated.resources.unit_micron_of_mercury_short
import unitto.core.common.generated.resources.unit_micropascal
import unitto.core.common.generated.resources.unit_micropascal_short
import unitto.core.common.generated.resources.unit_millibar
import unitto.core.common.generated.resources.unit_millibar_short
import unitto.core.common.generated.resources.unit_millimeter_of_mercury
import unitto.core.common.generated.resources.unit_millimeter_of_mercury_short
import unitto.core.common.generated.resources.unit_millipascal
import unitto.core.common.generated.resources.unit_millipascal_short
import unitto.core.common.generated.resources.unit_nanopascal
import unitto.core.common.generated.resources.unit_nanopascal_short
import unitto.core.common.generated.resources.unit_pascal
import unitto.core.common.generated.resources.unit_pascal_short
import unitto.core.common.generated.resources.unit_petapascal
import unitto.core.common.generated.resources.unit_petapascal_short
import unitto.core.common.generated.resources.unit_picopascal
import unitto.core.common.generated.resources.unit_picopascal_short
import unitto.core.common.generated.resources.unit_pound_force_per_square_foot
import unitto.core.common.generated.resources.unit_pound_force_per_square_foot_short
import unitto.core.common.generated.resources.unit_pound_force_per_square_inch
import unitto.core.common.generated.resources.unit_pound_force_per_square_inch_short
import unitto.core.common.generated.resources.unit_psi
import unitto.core.common.generated.resources.unit_psi_short
import unitto.core.common.generated.resources.unit_standard_atmosphere
import unitto.core.common.generated.resources.unit_standard_atmosphere_short
import unitto.core.common.generated.resources.unit_terapascal
import unitto.core.common.generated.resources.unit_terapascal_short
import unitto.core.common.generated.resources.unit_torr
import unitto.core.common.generated.resources.unit_torr_short

internal val pressureCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attopascal,
      KBigDecimal("1"),
      UnitGroup.PRESSURE,
      Res.string.unit_attopascal,
      Res.string.unit_attopascal_short,
    ),
    NormalUnit(
      UnitID.femtopascal,
      KBigDecimal("1000"),
      UnitGroup.PRESSURE,
      Res.string.unit_femtopascal,
      Res.string.unit_femtopascal_short,
    ),
    NormalUnit(
      UnitID.picopascal,
      KBigDecimal("1000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_picopascal,
      Res.string.unit_picopascal_short,
    ),
    NormalUnit(
      UnitID.nanopascal,
      KBigDecimal("1000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_nanopascal,
      Res.string.unit_nanopascal_short,
    ),
    NormalUnit(
      UnitID.micropascal,
      KBigDecimal("1000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_micropascal,
      Res.string.unit_micropascal_short,
    ),
    NormalUnit(
      UnitID.millipascal,
      KBigDecimal("1000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_millipascal,
      Res.string.unit_millipascal_short,
    ),
    NormalUnit(
      UnitID.centipascal,
      KBigDecimal("10000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_centipascal,
      Res.string.unit_centipascal_short,
    ),
    NormalUnit(
      UnitID.decipascal,
      KBigDecimal("100000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_decipascal,
      Res.string.unit_decipascal_short,
    ),
    NormalUnit(
      UnitID.pascal,
      KBigDecimal("1000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_pascal,
      Res.string.unit_pascal_short,
    ),
    NormalUnit(
      UnitID.dekapascal,
      KBigDecimal("10000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_dekapascal,
      Res.string.unit_dekapascal_short,
    ),
    NormalUnit(
      UnitID.hectopascal,
      KBigDecimal("100000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_hectopascal,
      Res.string.unit_hectopascal_short,
    ),
    NormalUnit(
      UnitID.millibar,
      KBigDecimal("100000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_millibar,
      Res.string.unit_millibar_short,
    ),
    NormalUnit(
      UnitID.bar,
      KBigDecimal("100000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_bar,
      Res.string.unit_bar_short,
    ),
    NormalUnit(
      UnitID.kilopascal,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_kilopascal,
      Res.string.unit_kilopascal_short,
    ),
    NormalUnit(
      UnitID.megapascal,
      KBigDecimal("1000000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_megapascal,
      Res.string.unit_megapascal_short,
    ),
    NormalUnit(
      UnitID.gigapascal,
      KBigDecimal("1000000000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_gigapascal,
      Res.string.unit_gigapascal_short,
    ),
    NormalUnit(
      UnitID.terapascal,
      KBigDecimal("1000000000000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_terapascal,
      Res.string.unit_terapascal_short,
    ),
    NormalUnit(
      UnitID.petapascal,
      KBigDecimal("1000000000000000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_petapascal,
      Res.string.unit_petapascal_short,
    ),
    NormalUnit(
      UnitID.exapascal,
      KBigDecimal("1000000000000000000000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_exapascal,
      Res.string.unit_exapascal_short,
    ),
    NormalUnit(
      UnitID.psi,
      KBigDecimal("6894757293178300000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_psi,
      Res.string.unit_psi_short,
    ),
    NormalUnit(
      UnitID.ksi,
      KBigDecimal("6894757293178300000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_ksi,
      Res.string.unit_ksi_short,
    ),
    NormalUnit(
      UnitID.standard_atmosphere,
      KBigDecimal("101325000000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_standard_atmosphere,
      Res.string.unit_standard_atmosphere_short,
    ),
    NormalUnit(
      UnitID.torr,
      KBigDecimal("133322368421082810000"),
      UnitGroup.PRESSURE,
      Res.string.unit_torr,
      Res.string.unit_torr_short,
    ),
    NormalUnit(
      UnitID.micron_of_mercury,
      KBigDecimal("133322368421082810"),
      UnitGroup.PRESSURE,
      Res.string.unit_micron_of_mercury,
      Res.string.unit_micron_of_mercury_short,
    ),
    NormalUnit(
      UnitID.millimeter_of_mercury,
      KBigDecimal("133322368421082810000"),
      UnitGroup.PRESSURE,
      Res.string.unit_millimeter_of_mercury,
      Res.string.unit_millimeter_of_mercury_short,
    ),
    NormalUnit(
      UnitID.kilogram_force_per_square_meter,
      KBigDecimal("9806650000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_kilogram_force_per_square_meter,
      Res.string.unit_kilogram_force_per_square_meter_short,
    ),
    NormalUnit(
      UnitID.kilogram_force_per_square_centimeter,
      KBigDecimal("98066500000000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_kilogram_force_per_square_centimeter,
      Res.string.unit_kilogram_force_per_square_centimeter_short,
    ),
    NormalUnit(
      UnitID.gram_force_per_square_centimeter,
      KBigDecimal("98066500000000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_gram_force_per_square_centimeter,
      Res.string.unit_gram_force_per_square_centimeter_short,
    ),
    NormalUnit(
      UnitID.pound_force_per_square_foot,
      KBigDecimal("47880258980000000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_pound_force_per_square_foot,
      Res.string.unit_pound_force_per_square_foot_short,
    ),
    NormalUnit(
      UnitID.pound_force_per_square_inch,
      KBigDecimal("6894757293178300000000"),
      UnitGroup.PRESSURE,
      Res.string.unit_pound_force_per_square_inch,
      Res.string.unit_pound_force_per_square_inch_short,
    ),
  )
}
