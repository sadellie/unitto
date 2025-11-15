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
import unitto.core.common.generated.resources.unit_attofarad
import unitto.core.common.generated.resources.unit_attofarad_short
import unitto.core.common.generated.resources.unit_exafarad
import unitto.core.common.generated.resources.unit_exafarad_short
import unitto.core.common.generated.resources.unit_farad
import unitto.core.common.generated.resources.unit_farad_short
import unitto.core.common.generated.resources.unit_gigafarad
import unitto.core.common.generated.resources.unit_gigafarad_short
import unitto.core.common.generated.resources.unit_kilofarad
import unitto.core.common.generated.resources.unit_kilofarad_short
import unitto.core.common.generated.resources.unit_megafarad
import unitto.core.common.generated.resources.unit_megafarad_short
import unitto.core.common.generated.resources.unit_microfarad
import unitto.core.common.generated.resources.unit_microfarad_short
import unitto.core.common.generated.resources.unit_millifarad
import unitto.core.common.generated.resources.unit_millifarad_short
import unitto.core.common.generated.resources.unit_nanofarad
import unitto.core.common.generated.resources.unit_nanofarad_short
import unitto.core.common.generated.resources.unit_petafarad
import unitto.core.common.generated.resources.unit_petafarad_short
import unitto.core.common.generated.resources.unit_picofarad
import unitto.core.common.generated.resources.unit_picofarad_short
import unitto.core.common.generated.resources.unit_statfarad
import unitto.core.common.generated.resources.unit_statfarad_short

internal val electrostaticCapacitance: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.attofarad,
      KBigDecimal("1"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_attofarad,
      Res.string.unit_attofarad_short,
    ),
    NormalUnit(
      UnitID.picofarad,
      KBigDecimal("1000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_picofarad,
      Res.string.unit_picofarad_short,
    ),
    NormalUnit(
      UnitID.statfarad,
      KBigDecimal("1112650.0561"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_statfarad,
      Res.string.unit_statfarad_short,
    ),
    NormalUnit(
      UnitID.nanofarad,
      KBigDecimal("1000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_nanofarad,
      Res.string.unit_nanofarad_short,
    ),
    NormalUnit(
      UnitID.microfarad,
      KBigDecimal("1000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_microfarad,
      Res.string.unit_microfarad_short,
    ),
    NormalUnit(
      UnitID.millifarad,
      KBigDecimal("1000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_millifarad,
      Res.string.unit_millifarad_short,
    ),
    NormalUnit(
      UnitID.farad,
      KBigDecimal("1000000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_farad,
      Res.string.unit_farad_short,
    ),
    NormalUnit(
      UnitID.kilofarad,
      KBigDecimal("1000000000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_kilofarad,
      Res.string.unit_kilofarad_short,
    ),
    NormalUnit(
      UnitID.megafarad,
      KBigDecimal("1000000000000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_megafarad,
      Res.string.unit_megafarad_short,
    ),
    NormalUnit(
      UnitID.gigafarad,
      KBigDecimal("1000000000000000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_gigafarad,
      Res.string.unit_gigafarad_short,
    ),
    NormalUnit(
      UnitID.petafarad,
      KBigDecimal("1000000000000000000000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_petafarad,
      Res.string.unit_petafarad_short,
    ),
    NormalUnit(
      UnitID.exafarad,
      KBigDecimal("1000000000000000000000000000000000000"),
      UnitGroup.ELECTROSTATIC_CAPACITANCE,
      Res.string.unit_exafarad,
      Res.string.unit_exafarad_short,
    ),
  )
}
