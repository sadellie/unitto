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
import unitto.core.common.generated.resources.unit_prefix_atto
import unitto.core.common.generated.resources.unit_prefix_atto_short
import unitto.core.common.generated.resources.unit_prefix_base
import unitto.core.common.generated.resources.unit_prefix_base_short
import unitto.core.common.generated.resources.unit_prefix_centi
import unitto.core.common.generated.resources.unit_prefix_centi_short
import unitto.core.common.generated.resources.unit_prefix_deca
import unitto.core.common.generated.resources.unit_prefix_deca_short
import unitto.core.common.generated.resources.unit_prefix_deci
import unitto.core.common.generated.resources.unit_prefix_deci_short
import unitto.core.common.generated.resources.unit_prefix_exa
import unitto.core.common.generated.resources.unit_prefix_exa_short
import unitto.core.common.generated.resources.unit_prefix_femto
import unitto.core.common.generated.resources.unit_prefix_femto_short
import unitto.core.common.generated.resources.unit_prefix_giga
import unitto.core.common.generated.resources.unit_prefix_giga_short
import unitto.core.common.generated.resources.unit_prefix_hecto
import unitto.core.common.generated.resources.unit_prefix_hecto_short
import unitto.core.common.generated.resources.unit_prefix_kilo
import unitto.core.common.generated.resources.unit_prefix_kilo_short
import unitto.core.common.generated.resources.unit_prefix_mega
import unitto.core.common.generated.resources.unit_prefix_mega_short
import unitto.core.common.generated.resources.unit_prefix_micro
import unitto.core.common.generated.resources.unit_prefix_micro_short
import unitto.core.common.generated.resources.unit_prefix_milli
import unitto.core.common.generated.resources.unit_prefix_milli_short
import unitto.core.common.generated.resources.unit_prefix_nano
import unitto.core.common.generated.resources.unit_prefix_nano_short
import unitto.core.common.generated.resources.unit_prefix_peta
import unitto.core.common.generated.resources.unit_prefix_peta_short
import unitto.core.common.generated.resources.unit_prefix_pico
import unitto.core.common.generated.resources.unit_prefix_pico_short
import unitto.core.common.generated.resources.unit_prefix_quecto
import unitto.core.common.generated.resources.unit_prefix_quecto_short
import unitto.core.common.generated.resources.unit_prefix_quetta
import unitto.core.common.generated.resources.unit_prefix_quetta_short
import unitto.core.common.generated.resources.unit_prefix_ronna
import unitto.core.common.generated.resources.unit_prefix_ronna_short
import unitto.core.common.generated.resources.unit_prefix_ronto
import unitto.core.common.generated.resources.unit_prefix_ronto_short
import unitto.core.common.generated.resources.unit_prefix_tera
import unitto.core.common.generated.resources.unit_prefix_tera_short
import unitto.core.common.generated.resources.unit_prefix_yocto
import unitto.core.common.generated.resources.unit_prefix_yocto_short
import unitto.core.common.generated.resources.unit_prefix_yotta
import unitto.core.common.generated.resources.unit_prefix_yotta_short
import unitto.core.common.generated.resources.unit_prefix_zepto
import unitto.core.common.generated.resources.unit_prefix_zepto_short
import unitto.core.common.generated.resources.unit_prefix_zetta
import unitto.core.common.generated.resources.unit_prefix_zetta_short

val prefixCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.prefix_quetta,
      KBigDecimal("1000000000000000000000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_quetta,
      Res.string.unit_prefix_quetta_short,
    ),
    NormalUnit(
      UnitID.prefix_ronna,
      KBigDecimal("1000000000000000000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_ronna,
      Res.string.unit_prefix_ronna_short,
    ),
    NormalUnit(
      UnitID.prefix_yotta,
      KBigDecimal("1000000000000000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_yotta,
      Res.string.unit_prefix_yotta_short,
    ),
    NormalUnit(
      UnitID.prefix_zetta,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_zetta,
      Res.string.unit_prefix_zetta_short,
    ),
    NormalUnit(
      UnitID.prefix_exa,
      KBigDecimal("1000000000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_exa,
      Res.string.unit_prefix_exa_short,
    ),
    NormalUnit(
      UnitID.prefix_peta,
      KBigDecimal("1000000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_peta,
      Res.string.unit_prefix_peta_short,
    ),
    NormalUnit(
      UnitID.prefix_tera,
      KBigDecimal("1000000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_tera,
      Res.string.unit_prefix_tera_short,
    ),
    NormalUnit(
      UnitID.prefix_giga,
      KBigDecimal("1000000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_giga,
      Res.string.unit_prefix_giga_short,
    ),
    NormalUnit(
      UnitID.prefix_mega,
      KBigDecimal("1000000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_mega,
      Res.string.unit_prefix_mega_short,
    ),
    NormalUnit(
      UnitID.prefix_kilo,
      KBigDecimal("1000"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_kilo,
      Res.string.unit_prefix_kilo_short,
    ),
    NormalUnit(
      UnitID.prefix_hecto,
      KBigDecimal("100"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_hecto,
      Res.string.unit_prefix_hecto_short,
    ),
    NormalUnit(
      UnitID.prefix_deca,
      KBigDecimal("10"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_deca,
      Res.string.unit_prefix_deca_short,
    ),
    NormalUnit(
      UnitID.prefix_base,
      KBigDecimal("1"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_base,
      Res.string.unit_prefix_base_short,
    ),
    NormalUnit(
      UnitID.prefix_deci,
      KBigDecimal("0.1"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_deci,
      Res.string.unit_prefix_deci_short,
    ),
    NormalUnit(
      UnitID.prefix_centi,
      KBigDecimal("0.01"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_centi,
      Res.string.unit_prefix_centi_short,
    ),
    NormalUnit(
      UnitID.prefix_milli,
      KBigDecimal("0.001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_milli,
      Res.string.unit_prefix_milli_short,
    ),
    NormalUnit(
      UnitID.prefix_micro,
      KBigDecimal("0.000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_micro,
      Res.string.unit_prefix_micro_short,
    ),
    NormalUnit(
      UnitID.prefix_nano,
      KBigDecimal("0.000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_nano,
      Res.string.unit_prefix_nano_short,
    ),
    NormalUnit(
      UnitID.prefix_pico,
      KBigDecimal("0.000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_pico,
      Res.string.unit_prefix_pico_short,
    ),
    NormalUnit(
      UnitID.prefix_femto,
      KBigDecimal("0.000000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_femto,
      Res.string.unit_prefix_femto_short,
    ),
    NormalUnit(
      UnitID.prefix_atto,
      KBigDecimal("0.000000000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_atto,
      Res.string.unit_prefix_atto_short,
    ),
    NormalUnit(
      UnitID.prefix_zepto,
      KBigDecimal("0.000000000000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_zepto,
      Res.string.unit_prefix_zepto_short,
    ),
    NormalUnit(
      UnitID.prefix_yocto,
      KBigDecimal("0.000000000000000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_yocto,
      Res.string.unit_prefix_yocto_short,
    ),
    NormalUnit(
      UnitID.prefix_ronto,
      KBigDecimal("0.000000000000000000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_ronto,
      Res.string.unit_prefix_ronto_short,
    ),
    NormalUnit(
      UnitID.prefix_quecto,
      KBigDecimal("0.000000000000000000000000000001"),
      UnitGroup.PREFIX,
      Res.string.unit_prefix_quecto,
      Res.string.unit_prefix_quecto_short,
    ),
  )
}
