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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import java.math.BigDecimal

val prefixCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.prefix_quetta,
      BigDecimal("1000000000000000000000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_quetta,
      R.string.unit_prefix_quetta_short,
    ),
    NormalUnit(
      UnitID.prefix_ronna,
      BigDecimal("1000000000000000000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_ronna,
      R.string.unit_prefix_ronna_short,
    ),
    NormalUnit(
      UnitID.prefix_yotta,
      BigDecimal("1000000000000000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_yotta,
      R.string.unit_prefix_yotta_short,
    ),
    NormalUnit(
      UnitID.prefix_zetta,
      BigDecimal("1000000000000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_zetta,
      R.string.unit_prefix_zetta_short,
    ),
    NormalUnit(
      UnitID.prefix_exa,
      BigDecimal("1000000000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_exa,
      R.string.unit_prefix_exa_short,
    ),
    NormalUnit(
      UnitID.prefix_peta,
      BigDecimal("1000000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_peta,
      R.string.unit_prefix_peta_short,
    ),
    NormalUnit(
      UnitID.prefix_tera,
      BigDecimal("1000000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_tera,
      R.string.unit_prefix_tera_short,
    ),
    NormalUnit(
      UnitID.prefix_giga,
      BigDecimal("1000000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_giga,
      R.string.unit_prefix_giga_short,
    ),
    NormalUnit(
      UnitID.prefix_mega,
      BigDecimal("1000000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_mega,
      R.string.unit_prefix_mega_short,
    ),
    NormalUnit(
      UnitID.prefix_kilo,
      BigDecimal("1000"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_kilo,
      R.string.unit_prefix_kilo_short,
    ),
    NormalUnit(
      UnitID.prefix_hecto,
      BigDecimal("100"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_hecto,
      R.string.unit_prefix_hecto_short,
    ),
    NormalUnit(
      UnitID.prefix_deca,
      BigDecimal("10"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_deca,
      R.string.unit_prefix_deca_short,
    ),
    NormalUnit(
      UnitID.prefix_base,
      BigDecimal("1"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_base,
      R.string.unit_prefix_base_short,
    ),
    NormalUnit(
      UnitID.prefix_deci,
      BigDecimal("0.1"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_deci,
      R.string.unit_prefix_deci_short,
    ),
    NormalUnit(
      UnitID.prefix_centi,
      BigDecimal("0.01"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_centi,
      R.string.unit_prefix_centi_short,
    ),
    NormalUnit(
      UnitID.prefix_milli,
      BigDecimal("0.001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_milli,
      R.string.unit_prefix_milli_short,
    ),
    NormalUnit(
      UnitID.prefix_micro,
      BigDecimal("0.000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_micro,
      R.string.unit_prefix_micro_short,
    ),
    NormalUnit(
      UnitID.prefix_nano,
      BigDecimal("0.000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_nano,
      R.string.unit_prefix_nano_short,
    ),
    NormalUnit(
      UnitID.prefix_pico,
      BigDecimal("0.000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_pico,
      R.string.unit_prefix_pico_short,
    ),
    NormalUnit(
      UnitID.prefix_femto,
      BigDecimal("0.000000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_femto,
      R.string.unit_prefix_femto_short,
    ),
    NormalUnit(
      UnitID.prefix_atto,
      BigDecimal("0.000000000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_atto,
      R.string.unit_prefix_atto_short,
    ),
    NormalUnit(
      UnitID.prefix_zepto,
      BigDecimal("0.000000000000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_zepto,
      R.string.unit_prefix_zepto_short,
    ),
    NormalUnit(
      UnitID.prefix_yocto,
      BigDecimal("0.000000000000000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_yocto,
      R.string.unit_prefix_yocto_short,
    ),
    NormalUnit(
      UnitID.prefix_ronto,
      BigDecimal("0.000000000000000000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_ronto,
      R.string.unit_prefix_ronto_short,
    ),
    NormalUnit(
      UnitID.prefix_quecto,
      BigDecimal("0.000000000000000000000000000001"),
      UnitGroup.PREFIX,
      R.string.unit_prefix_quecto,
      R.string.unit_prefix_quecto_short,
    ),
  )
}
