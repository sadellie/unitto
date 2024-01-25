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

package com.sadellie.unitto.feature.glance.glance

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import com.sadellie.unitto.data.model.userprefs.CalculatorPreferences
import com.sadellie.unitto.feature.glance.R
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

    override val sizeMode = SizeMode.Responsive(
        setOf(SMALL, BIG)
    )

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    companion object {
        internal val inputKey = ActionParameters.Key<String>("inputKey")
        internal val outputKey = ActionParameters.Key<String>("outputKey")
        internal val equalClickedKey = ActionParameters.Key<Boolean>("equalClickedKey")
        internal val precisionKey = ActionParameters.Key<Int>("precisionKey")
        internal val outputFormatKey = ActionParameters.Key<Int>("outputFormatKey")

        internal val inputPrefKey = stringPreferencesKey("GLANCE_INPUT")
        internal val outputPrefKey = stringPreferencesKey("GLANCE_OUTPUT")
        internal val equalClickedPrefKey = booleanPreferencesKey("GLANCE_EQUAL_CLICKED")

        internal val SMALL = DpSize(200.dp, 250.dp)
        internal val BIG = DpSize(250.dp, 400.dp)
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val userPrefsRepository = try {
            EntryPoints.get(context, UserPrefEntryPoint::class.java).userPrefRep()
        } catch (e: Exception) {
            Log.e("CalculatorWidget", "Error: $e")
            provideContent { LoadingUI() } // everything after is unreachable, no need for return
        }

        provideContent {
            val appPrefs = userPrefsRepository.calculatorPrefs.collectAsState(initial = null).value

            WidgetTheme {
                if (appPrefs == null) LoadingUI() else ReadyUI(appPrefs)
            }
        }
    }
}

@Composable
private fun LoadingUI() {
    Box(
        modifier = GlanceModifier
            .appWidgetBackground()
            .background(GlanceTheme.colors.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            glanceModifier = GlanceModifier,
            containerColor = GlanceTheme.colors.primary,
            iconRes = R.drawable.refresh,
            onClick = actionRunCallback<RestartWidget>(),
        )
    }
}

@Composable
private fun ReadyUI(
    appPrefs: CalculatorPreferences,
) {
    val glancePrefs = currentState<Preferences>()
    val input = glancePrefs[CalculatorWidget.inputPrefKey] ?: ""
    val output = glancePrefs[CalculatorWidget.outputPrefKey] ?: ""
    val equalClicked = glancePrefs[CalculatorWidget.equalClickedPrefKey] ?: false
    val formatterSymbols = AllFormatterSymbols.getById(appPrefs.separator)

    fun runCalculateAction(
        input: String,
        equalClicked: Boolean = false
    ): Action = updateInputAction(
        input = input,
        equalClicked = equalClicked,
        precision = appPrefs.precision,
        outputFormat = appPrefs.outputFormat
    )

    Column(
        modifier = GlanceModifier
            .appWidgetBackground()
            .background(GlanceTheme.colors.background)
            .fillMaxSize()
    ) {
        val uiSectionModifier = GlanceModifier.fillMaxWidth()

        if (LocalSize.current != CalculatorWidget.SMALL) {
            ActionButtons(
                modifier = uiSectionModifier
                    .background(GlanceTheme.colors.surfaceVariant),
                output = output,
                formatterSymbols = formatterSymbols
            )
        }

        TextFields(
            modifier = uiSectionModifier
                .background(GlanceTheme.colors.surfaceVariant)
                .defaultWeight(),
            input = input,
            formatterSymbols = formatterSymbols,
            output = output
        )

        GlanceKeyboard(
            modifier = uiSectionModifier,
            addTokenAction = {
                runCalculateAction(
                    // Clear input if equal is clicked and new token is a Digit
                    (if (equalClicked and Token.Digit.allWithDot.contains(it)) "" else input)
                        .addToken(it)
                )
            },
            clearInputAction = {
                runCalculateAction("")
            },
            addBracketAction = {
                runCalculateAction(input.addBracket())
            },
            deleteTokenAction = {
                runCalculateAction(input.dropLast(1))
            },
            equalAction = equal@{
                if (output.isEmpty()) return@equal actionRunCallback<UpdateInputAction>()

                runCalculateAction(output, true)
            },
            useDot = formatterSymbols.fractional == Token.Digit.dot,
            middleZero = appPrefs.middleZero
        )
    }
}

@Composable
private fun ActionButtons(
    modifier: GlanceModifier,
    output: String,
    formatterSymbols: FormatterSymbols,
) {
    Row(
        modifier = modifier
    ) {
        val buttonModifier = GlanceModifier.fillMaxWidth().defaultWeight()

        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primary,
            iconRes = R.drawable.content_copy,
            onClick = copyAction(
                output = output,
                fractional = formatterSymbols.fractional
            )
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primary,
            iconRes = R.drawable.open_in_new,
            onClick = launchAction(LocalContext.current)
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
    Column(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        val textModifier = GlanceModifier.fillMaxWidth()

        Text(
            text = input.formatExpression(formatterSymbols),
            modifier = textModifier,
            maxLines = 1,
            style = TextStyle(
                fontSize = 36.sp,
                textAlign = TextAlign.End,
                color = GlanceTheme.colors.onSurfaceVariant
            )
        )
        Text(
            text = output.formatExpression(formatterSymbols),
            modifier = textModifier,
            maxLines = 1,
            style = TextStyle(
                fontSize = 36.sp,
                textAlign = TextAlign.End,
                color = GlanceTheme.colors.onSurfaceVariant.withAlpha(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun GlanceKeyboard(
    modifier: GlanceModifier,
    addTokenAction: (String) -> Action,
    clearInputAction: () -> Action,
    addBracketAction: () -> Action,
    deleteTokenAction: () -> Action,
    equalAction: () -> Action,
    useDot: Boolean,
    middleZero: Boolean,
) = Column(modifier = modifier) {
    val rowModifier = GlanceModifier.fillMaxWidth()

    Row(rowModifier) {
        val buttonModifier = GlanceModifier.defaultWeight()

        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.tertiaryContainer,
            iconRes = R.drawable.clear,
            onClick = clearInputAction()
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.brackets,
            onClick = addBracketAction()
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.percent,
            onClick = addTokenAction(Token.Operator.percent)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.divide,
            onClick = addTokenAction(Token.Operator.divide)
        )
    }
    Row(rowModifier) {
        val buttonModifier = GlanceModifier.defaultWeight()

        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key7,
            onClick = addTokenAction(Token.Digit._7)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key8,
            onClick = addTokenAction(Token.Digit._8)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key9,
            onClick = addTokenAction(Token.Digit._9)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.multiply,
            onClick = addTokenAction(Token.Operator.multiply)
        )
    }
    Row(rowModifier) {
        val buttonModifier = GlanceModifier.defaultWeight()

        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key4,
            onClick = addTokenAction(Token.Digit._4)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key5,
            onClick = addTokenAction(Token.Digit._5)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key6,
            onClick = addTokenAction(Token.Digit._6)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.minus,
            onClick = addTokenAction(Token.Operator.minus)
        )
    }
    Row(rowModifier) {
        val buttonModifier = GlanceModifier.defaultWeight()

        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key1,
            onClick = addTokenAction(Token.Digit._1)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key2,
            onClick = addTokenAction(Token.Digit._2)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.key3,
            onClick = addTokenAction(Token.Digit._3)
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.plus,
            onClick = addTokenAction(Token.Operator.plus)
        )
    }
    Row(rowModifier) {
        val buttonModifier = GlanceModifier.defaultWeight()

        if (middleZero) {
            IconButton(
                glanceModifier = buttonModifier,
                containerColor = GlanceTheme.colors.inverseOnSurface,
                iconRes = if (useDot) R.drawable.dot else R.drawable.comma,
                onClick = addTokenAction(Token.Digit.dot)
            )
            IconButton(
                glanceModifier = buttonModifier,
                containerColor = GlanceTheme.colors.inverseOnSurface,
                iconRes = R.drawable.key0,
                onClick = addTokenAction(Token.Digit._0)
            )
        } else {
            IconButton(
                glanceModifier = buttonModifier,
                containerColor = GlanceTheme.colors.inverseOnSurface,
                iconRes = R.drawable.key0,
                onClick = addTokenAction(Token.Digit._0)
            )
            IconButton(
                glanceModifier = buttonModifier,
                containerColor = GlanceTheme.colors.inverseOnSurface,
                iconRes = if (useDot) R.drawable.dot else R.drawable.comma,
                onClick = addTokenAction(Token.Digit.dot)
            )
        }
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.inverseOnSurface,
            iconRes = R.drawable.backspace,
            onClick = deleteTokenAction()
        )
        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.primaryContainer,
            iconRes = R.drawable.equal,
            onClick = equalAction()
        )
    }
}

@Composable
private fun ColorProvider.withAlpha(alpha: Float): ColorProvider =
    ColorProvider(
        this
            .getColor(LocalContext.current)
            .copy(alpha = alpha)
    )
