/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.glance

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.Action
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import com.sadellie.unitto.core.common.Token

@Composable
internal fun GlanceKeyboard(
  modifier: GlanceModifier,
  addTokenAction: (String) -> Action,
  clearInputAction: () -> Action,
  addBracketAction: () -> Action,
  deleteTokenAction: () -> Action,
  equalAction: () -> Action,
  useDot: Boolean,
  middleZero: Boolean,
) =
  Column(modifier = modifier) {
    val rowModifier = GlanceModifier.fillMaxWidth()

    GlanceKeyboardRow1(
      rowModifier = rowModifier,
      clearInputAction = clearInputAction,
      addBracketAction = addBracketAction,
      addTokenAction = addTokenAction,
    )
    GlanceKeyboardRow2(rowModifier = rowModifier, addTokenAction = addTokenAction)
    GlanceKeyboardRow3(rowModifier = rowModifier, addTokenAction = addTokenAction)
    GlanceKeyboardRow4(rowModifier = rowModifier, addTokenAction = addTokenAction)
    GlanceKeyboardRow5(
      rowModifier = rowModifier,
      middleZero = middleZero,
      useDot = useDot,
      addTokenAction = addTokenAction,
      deleteTokenAction = deleteTokenAction,
      equalAction = equalAction,
    )
  }

@Composable
private fun GlanceKeyboardRow1(
  rowModifier: GlanceModifier,
  clearInputAction: () -> Action,
  addBracketAction: () -> Action,
  addTokenAction: (String) -> Action,
) {
  Row(rowModifier) {
    val buttonModifier = GlanceModifier.defaultWeight()

    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.tertiaryContainer,
      iconRes = R.drawable.clear,
      onClick = clearInputAction(),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.secondaryContainer,
      iconRes = R.drawable.brackets,
      onClick = addBracketAction(),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.secondaryContainer,
      iconRes = R.drawable.percent,
      onClick = addTokenAction(Token.Operator.PERCENT),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.secondaryContainer,
      iconRes = R.drawable.divide,
      onClick = addTokenAction(Token.Operator.DIVIDE),
    )
  }
}

@Composable
private fun GlanceKeyboardRow2(rowModifier: GlanceModifier, addTokenAction: (String) -> Action) {
  Row(rowModifier) {
    val buttonModifier = GlanceModifier.defaultWeight()

    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key7,
      onClick = addTokenAction(Token.Digit.DIGIT_7),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key8,
      onClick = addTokenAction(Token.Digit.DIGIT_8),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key9,
      onClick = addTokenAction(Token.Digit.DIGIT_9),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.secondaryContainer,
      iconRes = R.drawable.multiply,
      onClick = addTokenAction(Token.Operator.MULTIPLY),
    )
  }
}

@Composable
private fun GlanceKeyboardRow3(rowModifier: GlanceModifier, addTokenAction: (String) -> Action) {
  Row(rowModifier) {
    val buttonModifier = GlanceModifier.defaultWeight()

    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key4,
      onClick = addTokenAction(Token.Digit.DIGIT_4),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key5,
      onClick = addTokenAction(Token.Digit.DIGIT_5),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key6,
      onClick = addTokenAction(Token.Digit.DIGIT_6),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.secondaryContainer,
      iconRes = R.drawable.minus,
      onClick = addTokenAction(Token.Operator.MINUS),
    )
  }
}

@Composable
private fun GlanceKeyboardRow4(rowModifier: GlanceModifier, addTokenAction: (String) -> Action) {
  Row(rowModifier) {
    val buttonModifier = GlanceModifier.defaultWeight()

    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key1,
      onClick = addTokenAction(Token.Digit.DIGIT_1),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key2,
      onClick = addTokenAction(Token.Digit.DIGIT_2),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.key3,
      onClick = addTokenAction(Token.Digit.DIGIT_3),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.secondaryContainer,
      iconRes = R.drawable.plus,
      onClick = addTokenAction(Token.Operator.PLUS),
    )
  }
}

@Composable
private fun GlanceKeyboardRow5(
  rowModifier: GlanceModifier,
  middleZero: Boolean,
  useDot: Boolean,
  addTokenAction: (String) -> Action,
  deleteTokenAction: () -> Action,
  equalAction: () -> Action,
) {
  Row(rowModifier) {
    val buttonModifier = GlanceModifier.defaultWeight()

    if (middleZero) {
      IconButton(
        glanceModifier = buttonModifier,
        containerColor = GlanceTheme.colors.inverseOnSurface,
        iconRes = if (useDot) R.drawable.dot else R.drawable.comma,
        onClick = addTokenAction(Token.Digit.DOT),
      )
      IconButton(
        glanceModifier = buttonModifier,
        containerColor = GlanceTheme.colors.inverseOnSurface,
        iconRes = R.drawable.key0,
        onClick = addTokenAction(Token.Digit.DIGIT_0),
      )
    } else {
      IconButton(
        glanceModifier = buttonModifier,
        containerColor = GlanceTheme.colors.inverseOnSurface,
        iconRes = R.drawable.key0,
        onClick = addTokenAction(Token.Digit.DIGIT_0),
      )
      IconButton(
        glanceModifier = buttonModifier,
        containerColor = GlanceTheme.colors.inverseOnSurface,
        iconRes = if (useDot) R.drawable.dot else R.drawable.comma,
        onClick = addTokenAction(Token.Digit.DOT),
      )
    }
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.inverseOnSurface,
      iconRes = R.drawable.backspace,
      onClick = deleteTokenAction(),
    )
    IconButton(
      glanceModifier = buttonModifier,
      containerColor = GlanceTheme.colors.primaryContainer,
      iconRes = R.drawable.equal,
      onClick = equalAction(),
    )
  }
}
