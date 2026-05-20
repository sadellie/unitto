/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.designsystem.icons.symbols.ActivityZone
import com.sadellie.unitto.core.designsystem.icons.symbols.ActivityZoneFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Airwave
import com.sadellie.unitto.core.designsystem.icons.symbols.Asterisk
import com.sadellie.unitto.core.designsystem.icons.symbols.AttachMoney
import com.sadellie.unitto.core.designsystem.icons.symbols.BacklightHigh
import com.sadellie.unitto.core.designsystem.icons.symbols.Code
import com.sadellie.unitto.core.designsystem.icons.symbols.Compress
import com.sadellie.unitto.core.designsystem.icons.symbols.DeviceThermostat
import com.sadellie.unitto.core.designsystem.icons.symbols.DiagonalLine
import com.sadellie.unitto.core.designsystem.icons.symbols.Download
import com.sadellie.unitto.core.designsystem.icons.symbols.ElectricBolt
import com.sadellie.unitto.core.designsystem.icons.symbols.ElectricBoltFill
import com.sadellie.unitto.core.designsystem.icons.symbols.ElectricRickshaw
import com.sadellie.unitto.core.designsystem.icons.symbols.ElectricRickshawFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Energy
import com.sadellie.unitto.core.designsystem.icons.symbols.FastForward
import com.sadellie.unitto.core.designsystem.icons.symbols.FastForwardFill
import com.sadellie.unitto.core.designsystem.icons.symbols.FitnessCenter
import com.sadellie.unitto.core.designsystem.icons.symbols.HardDrive2
import com.sadellie.unitto.core.designsystem.icons.symbols.HardDrive2Fill
import com.sadellie.unitto.core.designsystem.icons.symbols.KeyboardArrowDown
import com.sadellie.unitto.core.designsystem.icons.symbols.KeyboardArrowUp
import com.sadellie.unitto.core.designsystem.icons.symbols.Lightbulb
import com.sadellie.unitto.core.designsystem.icons.symbols.LightbulbFill
import com.sadellie.unitto.core.designsystem.icons.symbols.LocalGasStation
import com.sadellie.unitto.core.designsystem.icons.symbols.LocalGasStationFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Power
import com.sadellie.unitto.core.designsystem.icons.symbols.PowerFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Schedule
import com.sadellie.unitto.core.designsystem.icons.symbols.ScheduleFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Settings
import com.sadellie.unitto.core.designsystem.icons.symbols.Speed
import com.sadellie.unitto.core.designsystem.icons.symbols.SpeedFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Straighten
import com.sadellie.unitto.core.designsystem.icons.symbols.StraightenFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.WaterMedium
import com.sadellie.unitto.core.designsystem.icons.symbols.WaterMediumFill
import com.sadellie.unitto.core.designsystem.icons.symbols.Weight
import com.sadellie.unitto.core.designsystem.icons.symbols.WeightFill
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.AssistChip
import com.sadellie.unitto.core.ui.FilterChip
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_expand_less_description
import unitto.core.common.generated.resources.common_expand_more_description
import unitto.core.common.generated.resources.common_open_settings

/**
 * Row of chips with [UnitGroup]s. Temporary solution
 *
 * @param items All [UnitGroup]s
 * @param chosenUnitGroup Currently selected [UnitGroup]
 * @param selectAction Action to perform when a chip is clicked
 * @param navigateToSettingsAction Action to perform when clicking settings chip at the end
 * @param showIcons Show [UnitGroup.icons] in front of chips
 */
@Composable
internal fun UnitGroupFilter(
  modifier: Modifier,
  items: List<UnitGroup>,
  chosenUnitGroup: UnitGroup?,
  selectAction: (UnitGroup?) -> Unit,
  navigateToSettingsAction: () -> Unit,
  showIcons: Boolean,
) {
  var expanded by remember { mutableStateOf(false) }
  val chipModifier = Modifier.padding(horizontal = Sizes.extraSmall)

  AnimatedContent(
    targetState = expanded,
    transitionSpec = {
      expandVertically(expandFrom = Alignment.Top) { it } + fadeIn() togetherWith
        shrinkVertically(shrinkTowards = Alignment.Top) { it } + fadeOut()
    },
    label = "Expand chips row",
  ) { isExpanded ->
    FlexRow(
      modifier = modifier,
      maxRows = if (isExpanded) Int.MAX_VALUE else 2,
      mainContent = {
        items.forEach { item ->
          val selected = item == chosenUnitGroup
          val icons = remember(showIcons) { if (showIcons) item.icons else null }
          FilterChip(
            modifier = chipModifier,
            isSelected = selected,
            onClick = {
              selectAction(if (selected) null else item)
              expanded = false
            },
            label = stringResource(item.res),
            unselectedIcon = icons?.first,
            selectedIcon = icons?.second,
          )
        }

        AssistChip(
          modifier = chipModifier,
          onClick = navigateToSettingsAction,
          imageVector = Symbols.Settings,
          contentDescription = stringResource(Res.string.common_open_settings),
        )

        if (expanded) {
          AssistChip(
            modifier = chipModifier,
            onClick = { expanded = false },
            imageVector = Symbols.KeyboardArrowUp,
            contentDescription = stringResource(Res.string.common_expand_less_description),
          )
        }
      },
      expandContent = {
        AssistChip(
          modifier = chipModifier,
          onClick = { expanded = true },
          imageVector = Symbols.KeyboardArrowDown,
          contentDescription = stringResource(Res.string.common_expand_more_description),
        )
      },
    )
  }
}

/**
 * Foldable row that places a specified element if overflown.
 *
 * @param modifier [Modifier] to be applied to this layout.
 * @param maxRows Max amount of rows including with [expandContent].
 * @param mainContent Main content (list of items) that will be folded.
 * @param expandContent Item that will be placed at the end if given [maxRows] wasn't high enough to
 *   place all [mainContent] items.
 */
@Composable
private fun FlexRow(
  modifier: Modifier = Modifier,
  maxRows: Int = Int.MAX_VALUE,
  mainContent: @Composable () -> Unit,
  expandContent: @Composable () -> Unit,
) {
  SubcomposeLayout(modifier = modifier) { constraints ->
    val localConstraints = constraints.copy(minWidth = 0, minHeight = 0)
    val layoutWidth = localConstraints.maxWidth

    val mainMeasurables = subcompose(FlexRowSlots.Main, mainContent)
    val expandMeasurables =
      subcompose(slotId = FlexRowSlots.Expand, content = { expandContent() }).map {
        it.measure(localConstraints)
      }
    val expandContentWidth = expandMeasurables.sumOf { it.measuredWidth }

    val placeables = mutableListOf<MutableList<Placeable>>(mutableListOf())

    var widthLeft = layoutWidth
    var index = 0
    for (measurable in mainMeasurables) {
      val mainPlaceable = measurable.measure(localConstraints)

      val lastAvailableRow = placeables.size >= maxRows
      val notLastItem = index < mainMeasurables.lastIndex

      // count expandContent width only for last row and not last main placeable
      val measuredWidth =
        if (lastAvailableRow and notLastItem) {
          mainPlaceable.measuredWidth + expandContentWidth
        } else {
          mainPlaceable.measuredWidth
        }

      // need new row
      if (widthLeft <= measuredWidth) {
        // Can't add more rows, add expandContent
        if (lastAvailableRow) {
          expandMeasurables.forEach { placeables.last().add(it) }
          break
        }

        placeables.add(mutableListOf())
        widthLeft = layoutWidth
      }
      placeables.last().add(mainPlaceable)
      index++
      widthLeft -= mainPlaceable.measuredWidth
    }

    val flattenPlaceables = placeables.flatten()
    val layoutHeight = placeables.size * (flattenPlaceables.maxByOrNull { it.height }?.height ?: 0)

    layout(layoutWidth, layoutHeight) {
      var yPos = 0
      placeables.forEach { row ->
        var xPos = 0
        row.forEach { placeable ->
          placeable.place(x = xPos, y = yPos)
          xPos += placeable.width
        }
        yPos += row.maxByOrNull { it.height }?.height ?: 0
      }
    }
  }
}

private enum class FlexRowSlots {
  Main,
  Expand,
}

/** Pair of unselected and selected icons */
private val UnitGroup.icons: Pair<ImageVector, ImageVector>
  get() =
    when (this) {
      UnitGroup.LENGTH -> Symbols.Straighten to Symbols.StraightenFill
      UnitGroup.CURRENCY -> Symbols.AttachMoney to Symbols.AttachMoney
      UnitGroup.MASS -> Symbols.Weight to Symbols.WeightFill
      UnitGroup.SPEED -> Symbols.Speed to Symbols.SpeedFill
      UnitGroup.TEMPERATURE -> Symbols.DeviceThermostat to Symbols.DeviceThermostat
      UnitGroup.AREA -> Symbols.ActivityZone to Symbols.ActivityZoneFill
      UnitGroup.TIME -> Symbols.Schedule to Symbols.ScheduleFill
      UnitGroup.VOLUME -> Symbols.WaterMedium to Symbols.WaterMediumFill
      UnitGroup.DATA -> Symbols.HardDrive2 to Symbols.HardDrive2Fill
      UnitGroup.PRESSURE -> Symbols.Compress to Symbols.Compress
      UnitGroup.ACCELERATION -> Symbols.FastForward to Symbols.FastForwardFill
      UnitGroup.ENERGY -> Symbols.Energy to Symbols.Energy
      UnitGroup.POWER -> Symbols.Power to Symbols.PowerFill
      UnitGroup.ANGLE -> Symbols.DiagonalLine to Symbols.DiagonalLine
      UnitGroup.DATA_TRANSFER -> Symbols.Download to Symbols.Download
      UnitGroup.FLUX -> Symbols.Lightbulb to Symbols.LightbulbFill
      UnitGroup.NUMBER_BASE -> Symbols.Code to Symbols.Code
      UnitGroup.ELECTROSTATIC_CAPACITANCE -> Symbols.ElectricBolt to Symbols.ElectricBoltFill
      UnitGroup.PREFIX -> Symbols.Asterisk to Symbols.Asterisk
      UnitGroup.FORCE -> Symbols.FitnessCenter to Symbols.FitnessCenter
      UnitGroup.TORQUE -> Symbols.ElectricRickshaw to Symbols.ElectricRickshawFill
      UnitGroup.FLOW_RATE -> Symbols.Airwave to Symbols.Airwave
      UnitGroup.LUMINANCE -> Symbols.BacklightHigh to Symbols.BacklightHigh
      UnitGroup.FUEL_CONSUMPTION -> Symbols.LocalGasStation to Symbols.LocalGasStationFill
    }

@Preview(device = "spec:width=380dp,height=850.9dp,dpi=440")
@Composable
fun PreviewUnitGroupFilter() {
  Column {
    var selected by remember { mutableStateOf<UnitGroup?>(UnitGroup.LENGTH) }
    UnitGroupFilter(
      modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth(),
      items = UnitGroup.entries.take(7),
      chosenUnitGroup = selected,
      selectAction = { selected = it },
      navigateToSettingsAction = {},
      showIcons = false,
    )

    UnitGroupFilter(
      modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth(),
      items = UnitGroup.entries.take(10),
      chosenUnitGroup = selected,
      selectAction = { selected = it },
      navigateToSettingsAction = {},
      showIcons = true,
    )
  }
}
