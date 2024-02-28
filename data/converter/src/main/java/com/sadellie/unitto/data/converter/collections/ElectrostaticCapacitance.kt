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
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.unit.BasicUnit
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import java.math.BigDecimal

internal val electrostaticCapacitance: List<BasicUnit> by lazy {
    listOf(
        NormalUnit(UnitID.attofarad,     BigDecimal("1"),              UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_attofarad,     R.string.unit_attofarad_short),
        NormalUnit(UnitID.picofarad,     BigDecimal("1000000"),           UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_picofarad,     R.string.unit_picofarad_short),
        NormalUnit(UnitID.statfarad,     BigDecimal("1112650.0561"),   UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_statfarad,     R.string.unit_statfarad_short),
        NormalUnit(UnitID.nanofarad,     BigDecimal("1000000000"),           UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_nanofarad,     R.string.unit_nanofarad_short),
        NormalUnit(UnitID.microfarad,    BigDecimal("1000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_microfarad,    R.string.unit_microfarad_short),
        NormalUnit(UnitID.millifarad,    BigDecimal("1000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_millifarad,    R.string.unit_millifarad_short),
        NormalUnit(UnitID.farad,         BigDecimal("1000000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_farad,         R.string.unit_farad_short),
        NormalUnit(UnitID.kilofarad,     BigDecimal("1000000000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_kilofarad,     R.string.unit_kilofarad_short),
        NormalUnit(UnitID.megafarad,     BigDecimal("1000000000000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_megafarad,     R.string.unit_megafarad_short),
        NormalUnit(UnitID.gigafarad,     BigDecimal("1000000000000000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_gigafarad,     R.string.unit_gigafarad_short),
        NormalUnit(UnitID.petafarad,     BigDecimal("1000000000000000000000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_petafarad,     R.string.unit_petafarad_short),
        NormalUnit(UnitID.exafarad,      BigDecimal("1000000000000000000000000000000000000"),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.unit_exafarad,      R.string.unit_exafarad_short),
    )
}
