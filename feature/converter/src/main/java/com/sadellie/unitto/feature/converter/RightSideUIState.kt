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

package com.sadellie.unitto.feature.converter

import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit

internal data class RightSideUIState(
    val unitFrom: AbstractUnit? = null,
    val unitTo: AbstractUnit? = null,
    val query: TextFieldValue = TextFieldValue(),
    val units: Map<UnitGroup, List<AbstractUnit>>? = null,
    val favorites: Boolean = false,
    val unitGroup: UnitGroup? = unitFrom?.group,
    val sorting: UnitsListSorting = UnitsListSorting.USAGE,
    val input: String = "",
    val scale: Int = 3,
    val outputFormat: Int = OutputFormat.PLAIN,
)
