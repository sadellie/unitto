/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.feature.glance.converter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.action
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.database.ConverterWidgetUnitPairDao
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.navigation.ConverterStartRoute
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.feature.glance.R
import com.sadellie.unitto.feature.glance.common.FloatingActionButton
import com.sadellie.unitto.feature.glance.common.UnittoGlanceTheme
import com.sadellie.unitto.feature.glance.common.WidgetTheme
import kotlin.getValue
import kotlinx.coroutines.flow.mapLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.converter_title
import unitto.core.common.generated.resources.unit_kilometer
import unitto.core.common.generated.resources.unit_kilometer_short
import unitto.core.common.generated.resources.unit_meter
import unitto.core.common.generated.resources.unit_meter_short

internal class ConverterWidget : GlanceAppWidget(), KoinComponent {
  override val sizeMode: SizeMode = SizeMode.Responsive(setOf(smallSizeMode, mediumSizeMode))
  override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

  companion object {
    internal const val LOG_TAG = "ConverterWidget"
    internal val smallSizeMode = DpSize(0.dp, 0.dp)
    internal val mediumSizeMode = DpSize(200.dp, 0.dp)
  }

  override suspend fun onDelete(context: Context, glanceId: GlanceId) {
    val dao by inject<ConverterWidgetUnitPairDao>()
    val manager = GlanceAppWidgetManager(context)
    val appWidgetId = manager.getAppWidgetId(glanceId)
    dao.deleteByAppWidgetId(appWidgetId)

    super.onDelete(context, glanceId)
  }

  override suspend fun provideGlance(context: Context, id: GlanceId) {
    val dao by inject<ConverterWidgetUnitPairDao>()
    val unitsRepo by inject<UnitsRepository>()
    val manager = GlanceAppWidgetManager(context)
    val appWidgetId = manager.getAppWidgetId(id)

    val unitPairsFlow =
      dao.getByAppWidgetId(appWidgetId).mapLatest { entities ->
        entityListToDomainList(unitsRepo, entities)
      }
    val userPrefsRepository by inject<UserPreferencesRepository>()
    provideContent {
      val appPrefs = userPrefsRepository.appPrefs.collectAsState(null).value
      WidgetTheme(appPrefs?.enableAmoledTheme ?: false) {
        CompositionLocalProvider(
          LocalConfiguration provides Configuration(context.resources.configuration),
          LocalDensity provides Density(context),
        ) {
          val unitPairs = unitPairsFlow.collectAsState(emptyList())
          ReadyUI(
            modifier = GlanceModifier.fillMaxSize(),
            size = LocalSize.current,
            onUnitPairClick = { unitFromId, unitToId ->
              launchConverterAction(context, unitFromId, unitToId)
            },
            onLaunchConverter = launchConverterAction(context, null, null),
            units = unitPairs.value,
          )
        }
      }
    }
  }
}

private fun launchConverterAction(
  mContext: Context,
  unitFromId: String?,
  unitToId: String?,
): Action {
  val componentName = ComponentName(mContext, "com.sadellie.unitto.MainActivity")
  val deepLinkIntent =
    Intent.makeMainActivity(componentName)
      .setData(ConverterStartRoute.generateRoute(unitFromId, unitToId).toUri())
      .setAction(Intent.ACTION_VIEW)

  return actionStartActivity(deepLinkIntent)
}

@Composable
private fun ReadyUI(
  modifier: GlanceModifier,
  size: DpSize,
  onUnitPairClick: (unitFromId: String, unitToId: String) -> Action,
  onLaunchConverter: Action,
  units: List<SelectedUnitPair>,
) {
  Column(modifier = modifier.background(UnittoGlanceTheme.colors.surfaceContainer)) {
    Row(
      modifier = GlanceModifier.fillMaxWidth().padding(Sizes.small),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = GlanceModifier.defaultWeight(),
        style =
          TextStyle(color = UnittoGlanceTheme.colors.onBackground, fontWeight = FontWeight.Medium),
        text = stringResource(Res.string.converter_title),
      )
      FloatingActionButton(
        glanceModifier = GlanceModifier,
        iconRes = R.drawable.open_in_new,
        onClick = onLaunchConverter,
      )
    }
    val indexedUnits = remember(units) { units.withIndex().toList() }

    LazyColumn(
      modifier = GlanceModifier.fillMaxWidth().defaultWeight().padding(horizontal = Sizes.small)
    ) {
      items(items = indexedUnits, itemId = { (_, unitPair) -> unitPair.id.toLong() }) {
        (index, unitPair) ->
        // outer padding
        Box(GlanceModifier.padding(vertical = ListItemExpressiveDefaults.ListArrangement.spacing)) {
          UnitPairItem(
            modifier =
              GlanceModifier.clickable(onUnitPairClick(unitPair.from.id, unitPair.to.id))
                .fillMaxWidth(),
            shortNames = size == ConverterWidget.smallSizeMode,
            unitPair = unitPair,
            indexInList = index,
            listSize = indexedUnits.size,
          )
        }
      }
      item { Spacer(modifier = GlanceModifier.height(Sizes.small)) }
    }
  }
}

@Composable
private fun UnitPairItem(
  modifier: GlanceModifier = GlanceModifier,
  shortNames: Boolean,
  unitPair: SelectedUnitPair,
  indexInList: Int,
  listSize: Int,
) {
  val unitTextStyle =
    TextStyle(
      color = UnittoGlanceTheme.colors.onSecondaryContainer,
      textAlign = TextAlign.Center,
      fontSize = 14.sp,
      fontWeight = FontWeight.Normal,
    )
  Row(
    modifier =
      modifier
        .padding(Sizes.small)
        .background(
          imageProvider = ImageProvider(listedShaped(indexInList, listSize)),
          colorFilter = ColorFilter.tint(UnittoGlanceTheme.colors.secondaryContainer),
        ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      modifier = GlanceModifier.defaultWeight(),
      style = unitTextStyle,
      text = stringResource(if (shortNames) unitPair.from.shortName else unitPair.from.displayName),
    )
    Spacer(
      modifier =
        GlanceModifier.background(UnittoGlanceTheme.colors.outline).width(1.dp).height(Sizes.medium)
    )
    Text(
      modifier = GlanceModifier.defaultWeight(),
      style = unitTextStyle,
      text = stringResource(if (shortNames) unitPair.to.shortName else unitPair.to.displayName),
    )
  }
}

@Stable
private fun listedShaped(indexInList: Int, listSize: Int): Int {
  if (listSize == 1) return R.drawable.rounded_corners_rectangle_shape_single
  val isFirst = indexInList == 0
  val isLast = indexInList == listSize - 1
  return when {
    isFirst -> R.drawable.rounded_corners_rectangle_shape_first
    isLast -> R.drawable.rounded_corners_rectangle_shape_last
    else -> R.drawable.rounded_corners_rectangle_shape_middle
  }
}

@Suppress("unused")
@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview
private fun PreviewReadyUICompact() {
  val units = remember {
    listOf(
      SelectedUnitPair(
        id = 0,
        order = 0,
        from =
          NormalUnit(
            UnitID.meter,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_meter,
            Res.string.unit_meter_short,
          ),
        to =
          NormalUnit(
            UnitID.kilometer,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_kilometer,
            Res.string.unit_kilometer_short,
          ),
      ),
      SelectedUnitPair(
        id = 1,
        order = 1,
        from =
          NormalUnit(
            UnitID.meter,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_meter,
            Res.string.unit_meter_short,
          ),
        to =
          NormalUnit(
            UnitID.kilometer,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_kilometer,
            Res.string.unit_kilometer_short,
          ),
      ),
    )
  }
  ReadyUI(
    modifier = GlanceModifier.size(200.dp, 400.dp),
    size = ConverterWidget.smallSizeMode,
    onUnitPairClick = { _, _ -> object : Action {} },
    onLaunchConverter = action {},
    units = units,
  )
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview
private fun PreviewReadyUIMedium() {
  val units = remember {
    listOf(
      SelectedUnitPair(
        id = 0,
        order = 0,
        from =
          NormalUnit(
            UnitID.meter,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_meter,
            Res.string.unit_meter_short,
          ),
        to =
          NormalUnit(
            UnitID.kilometer,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_kilometer,
            Res.string.unit_kilometer_short,
          ),
      ),
      SelectedUnitPair(
        id = 1,
        order = 1,
        from =
          NormalUnit(
            UnitID.meter,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_meter,
            Res.string.unit_meter_short,
          ),
        to =
          NormalUnit(
            UnitID.kilometer,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_kilometer,
            Res.string.unit_kilometer_short,
          ),
      ),
    )
  }
  ReadyUI(
    modifier = GlanceModifier.size(300.dp, 400.dp),
    size = ConverterWidget.mediumSizeMode,
    onUnitPairClick = { _, _ -> object : Action {} },
    onLaunchConverter = action {},
    units = units,
  )
}
