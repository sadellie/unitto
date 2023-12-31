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

import android.content.ComponentName
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProviders
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ColumnScope
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
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

        val userPrefsRepository = EntryPoints.get(context, UserPrefEntryPoint::class.java).userPrefRep()

        provideContent {
            val appPrefs = userPrefsRepository.calculatorPrefs.collectAsState(initial = null).value

            if (appPrefs == null) {
                LoadingUI()
                return@provideContent
            }

            ReadyUI(appPrefs)
        }
    }

    @Composable
    private fun ReadyUI(
        appPrefs: CalculatorPreferences,
    ) {
        val glancePrefs = currentState<Preferences>()
        val input = glancePrefs[inputPrefKey] ?: ""
        val output = glancePrefs[outputPrefKey] ?: ""
        val formatterSymbols = AllFormatterSymbols.getById(appPrefs.separator)

        fun runCalculateAction(input: String): Action = actionRunCallback<UpdateInputAction>(
            actionParametersOf(
                inputKey to input,
                precisionKey to appPrefs.precision,
                outputFormatKey to appPrefs.outputFormat
            )
        )

        fun runCopyAction(): Action = actionRunCallback<CopyResultAction>(
            actionParametersOf(
                outputKey to output.replace(Token.Digit.dot, formatterSymbols.fractional)
            )
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
                Row(modifier = GlanceModifier.fillMaxWidth().padding(8.dp)) {
                    val boxModifier = GlanceModifier.fillMaxWidth().defaultWeight()

                    GlanceKeyboardButton(boxModifier, GlanceTheme.colors.primary, R.drawable.content_copy, onClick = runCopyAction())
                    GlanceKeyboardButton(boxModifier, GlanceTheme.colors.primary, R.drawable.open_in_new, onClick = actionStartActivity(ComponentName(LocalContext.current, "com.sadellie.unitto.MainActivity")))
                }

                Text(
                    text = input.formatExpression(formatterSymbols),
                    modifier = GlanceModifier.fillMaxWidth(),
                    maxLines = 2,
                    style = TextStyle(fontSize = 36.sp, textAlign = TextAlign.End)
                )
                Text(
                    text = output.formatExpression(formatterSymbols),
                    modifier = GlanceModifier.fillMaxWidth(),
                    maxLines = 1,
                    style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.End)
                )
            }

            Column(
                modifier = GlanceModifier
                    .padding(8.dp)
            ) {
                GlanceKeyboard(
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
                        if (input.isEmpty()) return@equal actionRunCallback<UpdateInputAction>()

                        runCalculateAction(output)
                    },
                    useDot = formatterSymbols.fractional == Token.Digit.dot,
                    middleZero = appPrefs.middleZero
                )
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
            Button(
                text = LocalContext.current.resources.getString(com.sadellie.unitto.core.base.R.string.loading_label),
                onClick = {},
                style = TextStyle(GlanceTheme.colors.onBackground, fontSize = 36.sp)
            )
        }
    }

    @Composable
    private fun ColumnScope.GlanceKeyboard(
        addTokenAction: (String) -> Action,
        replaceInputAction: (String) -> Action,
        addBracketAction: () -> Action,
        deleteTokenAction: () -> Action,
        equalAction: () -> Action,
        useDot: Boolean,
        middleZero: Boolean,
    ) {
        val rowModifier = GlanceModifier.defaultWeight().fillMaxWidth()

        Row(
            modifier = rowModifier
        ) {
            val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.tertiaryContainer, R.drawable.clear, onClick = replaceInputAction(""))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.brackets, onClick = addBracketAction())
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.percent, onClick = addTokenAction(Token.Operator.percent))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.divide, onClick = addTokenAction(Token.Operator.divide))
        }

        Row(
            modifier = rowModifier
        ) {
            val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key7, onClick = addTokenAction(Token.Digit._7))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key8, onClick = addTokenAction(Token.Digit._8))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key9, onClick = addTokenAction(Token.Digit._9))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.multiply, onClick = addTokenAction(Token.Operator.multiply))
        }

        Row(
            modifier = rowModifier
        ) {
            val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key4, onClick = addTokenAction(Token.Digit._4))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key5, onClick = addTokenAction(Token.Digit._5))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key6, onClick = addTokenAction(Token.Digit._6))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.minus, onClick = addTokenAction(Token.Operator.minus))
        }

        Row(
            modifier = rowModifier
        ) {
            val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key1, onClick = addTokenAction(Token.Digit._1))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key2, onClick = addTokenAction(Token.Digit._2))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key3, onClick = addTokenAction(Token.Digit._3))
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.plus, onClick = addTokenAction(Token.Operator.plus))
        }

        Row(
            modifier = rowModifier
        ) {
            val buttonModifier = GlanceModifier.fillMaxSize().defaultWeight()

            if (middleZero) {
                GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, if (useDot) R.drawable.dot else R.drawable.comma, onClick = addTokenAction(Token.Digit.dot))
                GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key0, onClick = addTokenAction(Token.Digit._0))
            } else {
                GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.key0, onClick = addTokenAction(Token.Digit._0))
                GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, if (useDot) R.drawable.dot else R.drawable.comma, onClick = addTokenAction(Token.Digit.dot))
            }
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.inverseOnSurface, R.drawable.backspace, onClick = deleteTokenAction())
            GlanceKeyboardButton(buttonModifier, GlanceTheme.colors.primaryContainer, R.drawable.equal, onClick = equalAction())
        }
    }

    @Composable
    private fun GlanceKeyboardButton(
        glanceModifier: GlanceModifier,
        containerColor: ColorProvider,
        @DrawableRes iconRes: Int,
        contentColor: ColorProvider = GlanceTheme.colors.contentColorFor(containerColor),
        onClickKey: String = iconRes.toString(),
        onClick: Action,
    ) {
        Box(
            modifier = glanceModifier
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .clickable(onClick)
                    .cornerRadius(100.dp)
                    .background(containerColor)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                provider = ImageProvider(iconRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(contentColor)
            )
        }
    }
}

private fun String.addToken(token: String): String =
    TextFieldValue(this, TextRange(length)).addTokens(token).text

private fun String.addBracket(): String =
    TextFieldValue(this, TextRange(length)).addBracket().text

private fun ColorProviders.contentColorFor(backgroundColor: ColorProvider): ColorProvider =
    when (backgroundColor) {
        primary -> onPrimary
        primaryContainer -> onPrimaryContainer
        inverseOnSurface -> onSurfaceVariant
        tertiaryContainer -> onTertiaryContainer
        else -> onBackground
    }
