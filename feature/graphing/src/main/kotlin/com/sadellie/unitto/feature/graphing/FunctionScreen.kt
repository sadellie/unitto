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

package com.sadellie.unitto.feature.graphing

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.compact
import com.sadellie.unitto.core.designsystem.expanded
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.iconpack.KeyX
import com.sadellie.unitto.core.designsystem.icons.symbols.Close
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.medium
import com.sadellie.unitto.core.ui.ColorSelector
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.KeyboardButtonToken
import com.sadellie.unitto.core.ui.KeypadButton.Companion.AcTanKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ArCosKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ArSinKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BackspaceKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BracketsKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ClearKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.CommaKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.CosKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DivideKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DotKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.EulerKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ExKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.FactorialKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.InvKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key0
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key1
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key2
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key3
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key4
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key5
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key6
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key7
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key8
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Key9
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LeftBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LnKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LogKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MinusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ModuloKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MultiplyKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PercentKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PiKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PlusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.Power10Key
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PowerKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RightBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RootKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.SinKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.TanKey
import com.sadellie.unitto.core.ui.KeypadButton.KeypadButtonAdd
import com.sadellie.unitto.core.ui.KeypadButtonFilled
import com.sadellie.unitto.core.ui.KeypadButtonFilledPrimary
import com.sadellie.unitto.core.ui.KeypadButtonTertiary
import com.sadellie.unitto.core.ui.KeypadButtonTransparent
import com.sadellie.unitto.core.ui.KeypadFlow
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.textfield.addBracket
import com.sadellie.unitto.core.ui.textfield.addTokens
import com.sadellie.unitto.core.ui.textfield.deleteTokens
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.awaitCancellation

@Composable
internal fun FunctionCreatorRoute(
  navigateUp: () -> Unit,
  viewModel: FunctionCreatorViewModel,
  onConfirm: (GraphFunction) -> Unit,
) {
  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    is FunctionUIState.Creator ->
      FunctionScreen(
        title = stringResource(R.string.graphing_add_function),
        showAcButton = uiState.showAcButton,
        onConfirm = onConfirm,
        middleZero = uiState.middleZero,
        onDismiss = navigateUp,
        inverseMode = uiState.inverseMode,
        formatterSymbols = uiState.formatterSymbols,
        graphFunction = remember { GraphFunction(0, "", graphLineColors.first()) },
        onInverseModeClick = viewModel::onInverseModeClick,
      )
    else -> EmptyScreen()
  }
}

@Composable
internal fun FunctionEditorRoute(
  navigateUp: () -> Unit,
  viewModel: FunctionEditorViewModel,
  onConfirm: (GraphFunction) -> Unit,
) {
  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    is FunctionUIState.Editor ->
      FunctionScreen(
        title = stringResource(R.string.graphing_edit_function),
        showAcButton = uiState.showAcButton,
        onConfirm = onConfirm,
        middleZero = uiState.middleZero,
        onDismiss = navigateUp,
        inverseMode = uiState.inverseMode,
        formatterSymbols = uiState.formatterSymbols,
        graphFunction = uiState.function,
        onInverseModeClick = viewModel::onInverseModeClick,
      )
    else -> EmptyScreen()
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun FunctionScreen(
  title: String,
  graphFunction: GraphFunction,
  onDismiss: () -> Unit,
  onConfirm: (GraphFunction) -> Unit,
  formatterSymbols: FormatterSymbols,
  middleZero: Boolean,
  showAcButton: Boolean,
  inverseMode: Boolean,
  onInverseModeClick: (Boolean) -> Unit,
) {
  ScaffoldWithTopBar(
    modifier = Modifier.clip(AlertDialogDefaults.shape),
    title = { Text(title) },
    navigationIcon = { IconButton(onDismiss) { Icon(Symbols.Close, null) } },
  ) { paddingValues ->
    var showKeypad by remember { mutableStateOf(false) }
    BackHandler(showKeypad) { showKeypad = false }

    Box(Modifier.padding(paddingValues).fillMaxSize()) {
      val textFieldState =
        remember(graphFunction.expression) { TextFieldState(graphFunction.expression) }
      var color by remember(graphFunction.color) { mutableStateOf(graphFunction.color) }
      val isExpressionValid =
        remember(textFieldState.text) { isValidFunctionExpression(textFieldState.text.toString()) }
      val scrollState = rememberScrollState()

      Column(
        modifier = Modifier.verticalScroll(scrollState).padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() }) {
          val focusRequester = remember { FocusRequester() }
          LaunchedEffect(Unit) { focusRequester.requestFocus() }

          val textFieldInteractionSource = remember { MutableInteractionSource() }
          val isTextFieldFocused = textFieldInteractionSource.collectIsFocusedAsState()
          val isTextFieldPressed = textFieldInteractionSource.collectIsPressedAsState()
          LaunchedEffect(isTextFieldFocused.value, isTextFieldPressed.value) {
            showKeypad = isTextFieldFocused.value
          }

          OutlinedTextField(
            state = textFieldState,
            modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
            interactionSource = textFieldInteractionSource,
          )
        }

        ColorSelector(
          modifier = Modifier,
          currentColor = color,
          onColorClick = { color = it },
          colors = graphLineColors,
        )

        Button(
          onClick = {
            // do not rely on isExpressionValid (can be outdated)
            val expressionToSubmit = textFieldState.text.toString()
            val isValid = isValidFunctionExpression(expressionToSubmit)

            if (isValid) {
              onConfirm(graphFunction.copy(expression = expressionToSubmit, color = color))
            }
          },
          modifier = Modifier.fillMaxWidth(),
          enabled = isExpressionValid,
        ) {
          Text(stringResource(R.string.common_confirm))
        }

        // Fake padding to enable vertical scrolling when keypad is shown
        if (showKeypad) {
          val keypadHeight =
            if (LocalWindowSize.current.heightSizeClass == WindowHeightSizeClass.Compact) {
              KeypadCompactHeight
            } else {
              KeypadMediumHeight
            }
          Spacer(Modifier.heightIn(max = keypadHeight))
        }
      }
      AnimatedVisibility(
        visible = showKeypad,
        modifier = Modifier.align(Alignment.BottomCenter),
        enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
        label = "Keypad",
      ) {
        FunctionKeypad(
          modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
          showAcButton = showAcButton,
          formatterSymbols = formatterSymbols,
          middleZero = middleZero,
          inverseMode = inverseMode,
          onInverseModeClick = onInverseModeClick,
          addTokens = textFieldState::addTokens,
          deleteTokens = textFieldState::deleteTokens,
          addBracket = textFieldState::addBracket,
          clearText = textFieldState::clearText,
        )
      }
    }
  }
}

@Composable
private fun FunctionKeypad(
  modifier: Modifier,
  onInverseModeClick: (Boolean) -> Unit,
  addTokens: (String) -> Unit,
  clearText: () -> Unit,
  deleteTokens: () -> Unit,
  addBracket: () -> Unit,
  showAcButton: Boolean,
  formatterSymbols: FormatterSymbols,
  middleZero: Boolean,
  inverseMode: Boolean,
) {
  val fractionalKey =
    remember(formatterSymbols.fractional) {
      if (formatterSymbols.fractional == Token.PERIOD) DotKey else CommaKey
    }
  if (LocalWindowSize.current.heightSizeClass == WindowHeightSizeClass.Compact) {
    KeypadFlow(
      modifier = modifier.heightIn(max = KeypadCompactHeight).fillMaxWidth(),
      rows = 4,
      columns = 8,
    ) { width, height ->
      val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)
      val iconHeight = KeyboardButtonToken.ICON_HEIGHT_SHORT
      val iconHeightSecondary = KeyboardButtonToken.ICON_HEIGHT_SHORT_SECONDARY

      if (inverseMode) {
        KeypadButtonTransparent(bModifier, ExKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, ModuloKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, PiKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Key7, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key8, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key9, iconHeight, addTokens)
        if (showAcButton) {
          KeypadButtonTertiary(bModifier, ClearKey, iconHeight, clearText)
          KeypadButtonFilled(bModifier, BracketsKey, iconHeight, addBracket)
        } else {
          KeypadButtonFilled(bModifier, LeftBracketKey, iconHeight, addTokens)
          KeypadButtonFilled(bModifier, RightBracketKey, iconHeight, addTokens)
        }

        KeypadButtonTransparent(bModifier, InvKey, iconHeightSecondary) {
          onInverseModeClick(false)
        }
        KeypadButtonTransparent(bModifier, PowerKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, FactorialKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Key4, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key5, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key6, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MultiplyKey, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, DivideKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, ArSinKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, ArCosKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, AcTanKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Key1, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key2, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key3, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MinusKey, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MinusKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, EulerKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, ExKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Power10Key, iconHeightSecondary, addTokens)
        if (middleZero) {
          KeypadButtonTransparent(bModifier, fractionalKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, Key0, iconHeight, addTokens)
        } else {
          KeypadButtonTransparent(bModifier, Key0, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, fractionalKey, iconHeight, addTokens)
        }
        KeypadButtonTransparent(bModifier, BackspaceKey, iconHeight, clearText, deleteTokens)
        KeypadButtonFilled(bModifier, PlusKey, iconHeight, addTokens)
        KeypadButtonFilledPrimary(bModifier, KeyX, iconHeight, addTokens)
      } else {
        KeypadButtonTransparent(bModifier, ExKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, RootKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, PiKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Key7, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key8, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key9, iconHeight, addTokens)
        if (showAcButton) {
          KeypadButtonTertiary(bModifier, ClearKey, iconHeight, clearText)
          KeypadButtonFilled(bModifier, BracketsKey, iconHeight, addBracket)
        } else {
          KeypadButtonFilled(bModifier, LeftBracketKey, iconHeight, addTokens)
          KeypadButtonFilled(bModifier, RightBracketKey, iconHeight, addTokens)
        }

        KeypadButtonTransparent(bModifier, InvKey, iconHeightSecondary) { onInverseModeClick(true) }
        KeypadButtonTransparent(bModifier, PowerKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, FactorialKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Key4, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key5, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key6, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MultiplyKey, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, DivideKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, SinKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, CosKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, TanKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, Key1, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key2, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key3, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MinusKey, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, PercentKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, EulerKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, LnKey, iconHeightSecondary, addTokens)
        KeypadButtonTransparent(bModifier, LogKey, iconHeightSecondary, addTokens)
        if (middleZero) {
          KeypadButtonTransparent(bModifier, fractionalKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, Key0, iconHeight, addTokens)
        } else {
          KeypadButtonTransparent(bModifier, Key0, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, fractionalKey, iconHeight, addTokens)
        }
        KeypadButtonTransparent(bModifier, BackspaceKey, iconHeight, clearText, deleteTokens)
        KeypadButtonFilled(bModifier, PlusKey, iconHeight, addTokens)
        KeypadButtonFilledPrimary(bModifier, KeyX, iconHeight, addTokens)
      }
    }
  } else {
    Column(
      modifier = modifier.heightIn(max = KeypadMediumHeight).widthIn(400.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      KeypadFlow(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f), rows = 3, columns = 4) {
        width,
        height ->
        val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)
        val iconHeight = KeyboardButtonToken.ICON_HEIGHT_TALL_SECONDARY

        if (inverseMode) {
          KeypadButtonTransparent(bModifier, ModuloKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, PiKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, PowerKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, FactorialKey, iconHeight, addTokens)

          KeypadButtonTransparent(bModifier, ExKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, ArSinKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, ArCosKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, AcTanKey, iconHeight, addTokens)

          KeypadButtonTransparent(bModifier, InvKey, iconHeight) { onInverseModeClick(false) }
          KeypadButtonTransparent(bModifier, EulerKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, ExKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, Power10Key, iconHeight, addTokens)
        } else {
          KeypadButtonTransparent(bModifier, RootKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, PiKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, PowerKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, FactorialKey, iconHeight, addTokens)

          KeypadButtonTransparent(bModifier, ExKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, SinKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, CosKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, TanKey, iconHeight, addTokens)

          KeypadButtonTransparent(bModifier, InvKey, iconHeight) { onInverseModeClick(true) }
          KeypadButtonTransparent(bModifier, EulerKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, LnKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, LogKey, iconHeight, addTokens)
        }
      }

      KeypadFlow(modifier = Modifier.fillMaxWidth().weight(1f), rows = 5, columns = 4) {
        width,
        height ->
        val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)
        val iconHeight = KeyboardButtonToken.ICON_HEIGHT_TALL

        if (showAcButton) {
          KeypadButtonTertiary(bModifier, ClearKey, iconHeight, clearText)
          KeypadButtonFilled(bModifier, BracketsKey, iconHeight, addBracket)
        } else {
          KeypadButtonFilled(bModifier, LeftBracketKey, iconHeight, addTokens)
          KeypadButtonFilled(bModifier, RightBracketKey, iconHeight, addTokens)
        }
        KeypadButtonFilled(bModifier, PercentKey, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, DivideKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, Key7, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key8, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key9, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MultiplyKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, Key4, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key5, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key6, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, MinusKey, iconHeight, addTokens)

        KeypadButtonTransparent(bModifier, Key1, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key2, iconHeight, addTokens)
        KeypadButtonTransparent(bModifier, Key3, iconHeight, addTokens)
        KeypadButtonFilled(bModifier, PlusKey, iconHeight, addTokens)

        if (middleZero) {
          KeypadButtonTransparent(bModifier, fractionalKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, Key0, iconHeight, addTokens)
        } else {
          KeypadButtonTransparent(bModifier, fractionalKey, iconHeight, addTokens)
          KeypadButtonTransparent(bModifier, Key0, iconHeight, addTokens)
        }
        KeypadButtonTransparent(bModifier, BackspaceKey, iconHeight, clearText, deleteTokens)
        KeypadButtonFilledPrimary(bModifier, KeyX, iconHeight, addTokens)
      }
    }
  }
}

internal fun isValidFunctionExpression(input: String): Boolean {
  // sample input where x is replaced with some value
  val sampleInput =
    input
      // make sure "exp" token is left untouched
      .replace(Token.Func.EXP, "EXP")
      .replace("x", "1.0")
      // replace back
      .replace("EXP", Token.Func.EXP)

  val isValid =
    try {
      // This will tokenize sample input
      Expression(sampleInput, 10).calculate()
      true
    } catch (e: Exception) {
      // can be invalid for this sample x, but valid for others
      // false means that expression is very broken
      e is ExpressionException.DivideByZero ||
        e is ExpressionException.FactorialCalculation ||
        e is ExpressionException.TooBig
    }

  return isValid
}

private val KeyX = KeypadButtonAdd(IconPack.KeyX, null, "x")
private val KeypadCompactHeight = 200.dp
private val KeypadMediumHeight = 300.dp

@Composable
@Preview
private fun FunctionKeypadPortraitPreview() {
  CompositionLocalProvider(
    LocalWindowSize provides
      WindowSizeClass.calculateFromSize(
        DpSize(WindowWidthSizeClass.medium, WindowHeightSizeClass.expanded)
      )
  ) {
    FunctionKeypad(
      modifier = Modifier,
      onInverseModeClick = {},
      addTokens = {},
      clearText = {},
      deleteTokens = {},
      addBracket = {},
      showAcButton = false,
      formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      middleZero = false,
      inverseMode = false,
    )
  }
}

@Composable
@Preview
private fun FunctionKeypadLandscapePreview() {
  CompositionLocalProvider(
    LocalWindowSize provides
      WindowSizeClass.calculateFromSize(
        DpSize(WindowWidthSizeClass.expanded, WindowHeightSizeClass.compact)
      )
  ) {
    FunctionKeypad(
      modifier = Modifier,
      onInverseModeClick = {},
      addTokens = {},
      clearText = {},
      deleteTokens = {},
      addBracket = {},
      showAcButton = false,
      formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      middleZero = false,
      inverseMode = false,
    )
  }
}

@Composable
@Preview
private fun FunctionScreenPreview() {
  FunctionScreen(
    graphFunction = GraphFunction(0, "", Color.Unspecified),
    onDismiss = {},
    formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
    middleZero = true,
    onConfirm = {},
    title = "title",
    showAcButton = false,
    inverseMode = false,
    onInverseModeClick = {},
  )
}
