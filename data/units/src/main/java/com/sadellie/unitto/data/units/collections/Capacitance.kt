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

import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.R
import java.math.BigDecimal

internal val electrostaticCapacitance: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.attofarad,     BigDecimal.valueOf(1),              UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.attofarad,     R.string.attofarad_short),
        DefaultUnit(MyUnitIDS.picofarad,     BigDecimal.valueOf(1E+6),           UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.picofarad,     R.string.picofarad_short),
        DefaultUnit(MyUnitIDS.statfarad,     BigDecimal.valueOf(1112650.0561),   UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.statfarad,     R.string.statfarad_short),
        DefaultUnit(MyUnitIDS.nanofarad,     BigDecimal.valueOf(1E+9),           UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.nanofarad,     R.string.nanofarad_short),
        DefaultUnit(MyUnitIDS.microfarad,    BigDecimal.valueOf(1E+12),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.microfarad,    R.string.microfarad_short),
        DefaultUnit(MyUnitIDS.millifarad,    BigDecimal.valueOf(1E+15),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.millifarad,    R.string.millifarad_short),
        DefaultUnit(MyUnitIDS.farad,         BigDecimal.valueOf(1E+18),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.farad,         R.string.farad_short),
        DefaultUnit(MyUnitIDS.kilofarad,     BigDecimal.valueOf(1E+21),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.kilofarad,     R.string.kilofarad_short),
        DefaultUnit(MyUnitIDS.megafarad,     BigDecimal.valueOf(1E+24),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.megafarad,     R.string.megafarad_short),
        DefaultUnit(MyUnitIDS.gigafarad,     BigDecimal.valueOf(1E+27),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.gigafarad,     R.string.gigafarad_short),
        DefaultUnit(MyUnitIDS.petafarad,     BigDecimal.valueOf(1E+33),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.petafarad,     R.string.petafarad_short),
        DefaultUnit(MyUnitIDS.exafarad,      BigDecimal.valueOf(1E+36),          UnitGroup.ELECTROSTATIC_CAPACITANCE,    R.string.exafarad,      R.string.exafarad_short),
    )
}
