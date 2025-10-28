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

package com.sadellie.unitto.feature.glance.calculator

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.datastore.CalculatorPreferences
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.ui.textfield.formatExpression
import com.sadellie.unitto.feature.glance.R
import com.sadellie.unitto.feature.glance.common.IconButton
import com.sadellie.unitto.feature.glance.common.LoadingUI
import com.sadellie.unitto.feature.glance.common.WidgetTheme
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

class CalculatorWidget : GlanceAppWidget() {

  @EntryPoint
  @InstallIn(SingletonComponent::class)
  interface UserPrefEntryPoint {
    fun userPrefRep(): UserPreferencesRepository
  }

  override val sizeMode = SizeMode.Responsive(setOf(SMALL, BIG))

  override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

  companion object {
    internal val inputStateKey = stringPreferencesKey("GLANCE_INPUT")
    internal val outputStateKey = stringPreferencesKey("GLANCE_OUTPUT")
    internal val equalClickedStateKey = booleanPreferencesKey("GLANCE_EQUAL_CLICKED")
    internal val precisionStateKey = intPreferencesKey("GLANCE_PRECISION")
    internal val outputFormatStateKey = intPreferencesKey("GLANCE_OUTPUT_FORMAT")

    internal val SMALL = DpSize(200.dp, 250.dp)
    internal val BIG = DpSize(250.dp, 400.dp)
  }

  override suspend fun provideGlance(context: Context, id: GlanceId) {
    try {
      val userPrefsRepository =
        EntryPoints.get(context, UserPrefEntryPoint::class.java).userPrefRep()
      provideContent {
        val appPrefs = userPrefsRepository.calculatorPrefs.collectAsState(null).value

        LaunchedEffect(appPrefs) {
          updateAppWidgetState(context, id) { state ->
            state[precisionStateKey] = appPrefs?.precision ?: DEFAULT_PRECISION
            state[outputFormatStateKey] = appPrefs?.outputFormat ?: DEFAULT_OUTPUT_FORMAT
          }
          this@CalculatorWidget.update(context, id)
        }

        WidgetTheme {
          if (appPrefs == null) {
            LoadingUI(actionRunCallback<RestartCalculatorWidget>())
          } else {
            val state = currentState<Preferences>()
            ReadyUI(
              appPrefs = appPrefs,
              input = state[inputStateKey] ?: "",
              output = state[outputStateKey] ?: "",
            )
          }
        }
      }
    } catch (e: Exception) {
      Log.e("CalculatorWidget", "Error: $e")
      provideContent { LoadingUI(actionRunCallback<RestartCalculatorWidget>()) }
    }
  }
}

@Composable
private fun ReadyUI(appPrefs: CalculatorPreferences, input: String, output: String) {
  Column(
    modifier =
      GlanceModifier.appWidgetBackground().background(GlanceTheme.colors.background).fillMaxSize()
  ) {
    val formatterSymbols = appPrefs.formatterSymbols
    val uiSectionModifier = GlanceModifier.fillMaxWidth()

    if (LocalSize.current != CalculatorWidget.SMALL) {
      ActionButtons(
        modifier = uiSectionModifier.background(GlanceTheme.colors.surfaceVariant),
        output = output,
        formatterSymbols = formatterSymbols,
      )
    }

    TextFields(
      modifier = uiSectionModifier.background(GlanceTheme.colors.surfaceVariant).defaultWeight(),
      input = input,
      formatterSymbols = formatterSymbols,
      output = output,
    )

    CalculatorKeyboard(
      modifier = uiSectionModifier,
      addTokenAction = AddTokenAction.Companion::create,
      clearInputAction = ClearInputAction.Companion::create,
      addBracketAction = AddBracketAction.Companion::create,
      deleteTokenAction = DeleteTokenAction.Companion::create,
      equalAction = EqualAction.Companion::create,
      useDot = formatterSymbols.fractional == Token.Digit.DOT,
      middleZero = appPrefs.middleZero,
    )
  }
}

@Composable
private fun ActionButtons(
  modifier: GlanceModifier,
  output: String,
  formatterSymbols: FormatterSymbols,
) {
  Row(modifier = modifier) {
    val buttonModifier = GlanceModifier.fillMaxWidth().defaultWeight()

    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.primary,
      iconRes = R.drawable.content_copy,
      onClick = CopyResultAction.create(output, formatterSymbols.grouping),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.primary,
      iconRes = R.drawable.open_in_new,
      onClick = launchAction(LocalContext.current),
    )
  }
}

@Composable
private fun TextFields(
  modifier: GlanceModifier,
  input: String,
  formatterSymbols: FormatterSymbols,
  output: String,
) {
  Column(modifier = modifier, verticalAlignment = Alignment.Bottom) {
    val textModifier = GlanceModifier.fillMaxWidth()

    Text(
      text = input.formatExpression(formatterSymbols),
      modifier = textModifier,
      maxLines = 1,
      style =
        TextStyle(
          fontSize = 36.sp,
          textAlign = TextAlign.End,
          color = GlanceTheme.colors.onSurfaceVariant,
        ),
    )
    Text(
      text = output.formatExpression(formatterSymbols),
      modifier = textModifier,
      maxLines = 1,
      style =
        TextStyle(
          fontSize = 36.sp,
          textAlign = TextAlign.End,
          color = GlanceTheme.colors.onSurfaceVariant.withAlpha(alpha = 0.5f),
        ),
    )
  }
}

@SuppressLint("RestrictedApi") // rip glance
@Composable
private fun ColorProvider.withAlpha(alpha: Float): ColorProvider =
  ColorProvider(this.getColor(LocalContext.current).copy(alpha = alpha))

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview
private fun PreviewWidget() {
  ReadyUI(
    appPrefs =
      CalculatorPreferences(
        radianMode = false,
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        fractionalOutput = false,
        middleZero = true,
        inverseMode = false,
        acButton = true,
        precision = 3,
        outputFormat = OutputFormat.PLAIN,
        additionalButtons = false,
        partialHistoryView = false,
        initialPartialHistoryView = false,
        openHistoryViewButton = false,
      ),
    input = "123+456",
    output = "789.012",
  )
}
