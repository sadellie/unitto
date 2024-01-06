/*
 * Unitto is a unit converter for Android
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
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
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
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

class UnittoCalculatorWidget : GlanceAppWidget() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface UserPrefEntryPoint {
        fun userPrefRep(): UserPreferencesRepository
    }

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    companion object {
        val inputKey = ActionParameters.Key<String>("inputKey")
        val outputKey = ActionParameters.Key<String>("outputKey")
        val precisionKey = ActionParameters.Key<Int>("precisionKey")
        val outputFormatKey = ActionParameters.Key<Int>("outputFormatKey")

        val inputPrefKey = stringPreferencesKey("GLANCE_INPUT")
        val outputPrefKey = stringPreferencesKey("GLANCE_OUTPUT")
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val userPrefsRepository =
            EntryPoints.get(context, UserPrefEntryPoint::class.java).userPrefRep()

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
    val input = glancePrefs[UnittoCalculatorWidget.inputPrefKey] ?: ""
    val output = glancePrefs[UnittoCalculatorWidget.outputPrefKey] ?: ""
    val formatterSymbols = AllFormatterSymbols.getById(appPrefs.separator)

    fun runCalculateAction(input: String): Action = updateInputAction(
        input = input,
        precision = appPrefs.precision,
        outputFormat = appPrefs.outputFormat
    )

    Column(
        modifier = GlanceModifier
            .appWidgetBackground()
            .background(GlanceTheme.colors.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = GlanceModifier
                .background(GlanceTheme.colors.surfaceVariant)
        ) {
            ActionButtons(
                modifier = GlanceModifier.fillMaxWidth().padding(8.dp),
                onCopyClick = copyAction(
                    output = output,
                    fractional = formatterSymbols.fractional
                ),
                onLaunchClick = launchAction(LocalContext.current)
            )
            TextField(
                modifier = GlanceModifier.fillMaxWidth(),
                input = input,
                formatterSymbols = formatterSymbols,
                fontSize = 36.sp,
                maxLines = 2
            )
            TextField(
                modifier = GlanceModifier.fillMaxWidth(),
                input = output,
                formatterSymbols = formatterSymbols,
                fontSize = 28.sp,
                maxLines = 1
            )
        }

        GlanceKeyboard(
            modifier = GlanceModifier
                .padding(8.dp),
            addTokenAction = {
                runCalculateAction(input.addToken(it))
            },
            replaceInputAction = {
                runCalculateAction(it)
            },
            addBracketAction = {
                runCalculateAction(input.addBracket())
            },
            deleteTokenAction = {
                runCalculateAction(input.dropLast(1))
            },
            equalAction = equal@{
                if (output.isEmpty()) return@equal actionRunCallback<UpdateInputAction>()

                runCalculateAction(output)
            },
            useDot = formatterSymbols.fractional == Token.Digit.dot,
            middleZero = appPrefs.middleZero
        )
    }
}

@Composable
private fun ActionButtons(
    modifier: GlanceModifier,
    onCopyClick: Action,
    onLaunchClick: Action,
) {
    Row(
        modifier = modifier
    ) {
        val boxModifier = GlanceModifier.fillMaxWidth().defaultWeight()

        IconButton(
            glanceModifier = boxModifier,
            containerColor = GlanceTheme.colors.primary,
            iconRes = R.drawable.content_copy,
            onClick = onCopyClick
        )
        IconButton(
            glanceModifier = boxModifier,
            containerColor = GlanceTheme.colors.primary,
            iconRes = R.drawable.open_in_new,
            onClick = onLaunchClick
        )
    }
}

@Composable
private fun TextField(
    modifier: GlanceModifier,
    input: String,
    formatterSymbols: FormatterSymbols,
    fontSize: TextUnit,
    maxLines: Int,
) {
    Text(
        text = input.formatExpression(formatterSymbols),
        modifier = modifier,
        maxLines = maxLines,
        style = TextStyle(
            fontSize = fontSize,
            textAlign = TextAlign.End,
            color = GlanceTheme.colors.onSurfaceVariant
        )
    )
}

@Composable
private fun GlanceKeyboard(
    modifier: GlanceModifier,
    addTokenAction: (String) -> Action,
    replaceInputAction: (String) -> Action,
    addBracketAction: () -> Action,
    deleteTokenAction: () -> Action,
    equalAction: () -> Action,
    useDot: Boolean,
    middleZero: Boolean,
) = Column(modifier = modifier) {
    val rowModifier = GlanceModifier.defaultWeight().fillMaxWidth()

    Row(
        modifier = rowModifier
    ) {
        val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

        IconButton(
            glanceModifier = buttonModifier,
            containerColor = GlanceTheme.colors.tertiaryContainer,
            iconRes = R.drawable.clear,
            onClick = replaceInputAction("")
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

    Row(
        modifier = rowModifier
    ) {
        val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

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

    Row(
        modifier = rowModifier
    ) {
        val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

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

    Row(
        modifier = rowModifier
    ) {
        val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

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

    Row(
        modifier = rowModifier
    ) {
        val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

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
