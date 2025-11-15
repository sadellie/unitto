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
import unitto.core.common.generated.resources.unit_gigaweber
import unitto.core.common.generated.resources.unit_gigaweber_short
import unitto.core.common.generated.resources.unit_kiloweber
import unitto.core.common.generated.resources.unit_kiloweber_short
import unitto.core.common.generated.resources.unit_maxwell
import unitto.core.common.generated.resources.unit_maxwell_short
import unitto.core.common.generated.resources.unit_megaweber
import unitto.core.common.generated.resources.unit_megaweber_short
import unitto.core.common.generated.resources.unit_microweber
import unitto.core.common.generated.resources.unit_microweber_short
import unitto.core.common.generated.resources.unit_milliweber
import unitto.core.common.generated.resources.unit_milliweber_short
import unitto.core.common.generated.resources.unit_weber
import unitto.core.common.generated.resources.unit_weber_short

internal val fluxCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.maxwell,
      KBigDecimal("1"),
      UnitGroup.FLUX,
      Res.string.unit_maxwell,
      Res.string.unit_maxwell_short,
    ),
    NormalUnit(
      UnitID.microweber,
      KBigDecimal("100"),
      UnitGroup.FLUX,
      Res.string.unit_microweber,
      Res.string.unit_microweber_short,
    ),
    NormalUnit(
      UnitID.milliweber,
      KBigDecimal("100000"),
      UnitGroup.FLUX,
      Res.string.unit_milliweber,
      Res.string.unit_milliweber_short,
    ),
    NormalUnit(
      UnitID.weber,
      KBigDecimal("100000000"),
      UnitGroup.FLUX,
      Res.string.unit_weber,
      Res.string.unit_weber_short,
    ),
    NormalUnit(
      UnitID.kiloweber,
      KBigDecimal("100000000000"),
      UnitGroup.FLUX,
      Res.string.unit_kiloweber,
      Res.string.unit_kiloweber_short,
    ),
    NormalUnit(
      UnitID.megaweber,
      KBigDecimal("100000000000000"),
      UnitGroup.FLUX,
      Res.string.unit_megaweber,
      Res.string.unit_megaweber_short,
    ),
    NormalUnit(
      UnitID.gigaweber,
      KBigDecimal("100000000000000000"),
      UnitGroup.FLUX,
      Res.string.unit_gigaweber,
      Res.string.unit_gigaweber_short,
    ),
  )
}
