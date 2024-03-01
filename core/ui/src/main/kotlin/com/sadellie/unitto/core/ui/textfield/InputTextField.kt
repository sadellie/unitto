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

package com.sadellie.unitto.core.ui.textfield

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.ui.autosize.AutoSizeTextStyleBox
import kotlinx.coroutines.awaitCancellation

@Composable
fun ExpressionTextField(
  modifier: Modifier,
  state: TextFieldState,
  minRatio: Float = 1f,
  textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
  formatterSymbols: FormatterSymbols,
  readOnly: Boolean = false,
  placeholder: String = "",
) {
  val context = LocalContext.current
  val clipboardManager =
    remember(formatterSymbols) {
      ExpressionClipboardManager(
        formatterSymbols,
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager,
      )
    }

  CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
    val displayedText =
      remember(state.text) {
        AnnotatedString(state.text.toString().formatExpression(formatterSymbols))
      }

    AutoSizeTextField(
      state = state,
      modifier = modifier,
      readOnly = readOnly,
      inputTransformation = ExpressionInputTransformation(formatterSymbols),
      textStyle = LocalNumberTypography.current.displayLarge.copy(textColor),
      lineLimits = TextFieldLineLimits.SingleLine,
      cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
      outputTransformation = ExpressionOutputTransformation(formatterSymbols),
      minRatio = minRatio,
      placeholder = placeholder,
      displayedText = displayedText,
    )
  }
}

@Composable
fun NumberBaseTextField(
  modifier: Modifier,
  state: TextFieldState,
  minRatio: Float = 1f,
  textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
  readOnly: Boolean = false,
  placeholder: String = "",
) {
  AutoSizeTextField(
    state = state,
    modifier = modifier,
    readOnly = readOnly,
    inputTransformation = NumberBaseInputTransformation,
    outputTransformation = NumberBaseOutputTransformation,
    textStyle = LocalNumberTypography.current.displayLarge.copy(textColor),
    lineLimits = TextFieldLineLimits.SingleLine,
    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
    minRatio = minRatio,
    placeholder = placeholder,
  )
}

@Composable
fun SimpleTextField(
  modifier: Modifier,
  state: TextFieldState,
  minRatio: Float = 1f,
  textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
  readOnly: Boolean = false,
  placeholder: String = "",
) {
  AutoSizeTextField(
    state = state,
    modifier = modifier,
    readOnly = readOnly,
    textStyle = LocalNumberTypography.current.displayLarge.copy(textColor),
    lineLimits = TextFieldLineLimits.SingleLine,
    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
    minRatio = minRatio,
    placeholder = placeholder,
  )
}

/**
 * BasicTextField that adapts font size to fit it's container.
 *
 * @param placeholder Placeholder text, shown when [TextFieldState.text] is empty.
 * @param alignment The alignment of the text within its container.
 * @see [BasicTextField]
 * @see [AutoSizeTextStyleBox]
 * @author https://gist.github.com/inidamleader/b594d35362ebcf3cedf81055df519300
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AutoSizeTextField(
  state: TextFieldState,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  inputTransformation: InputTransformation? = null,
  textStyle: TextStyle = TextStyle.Default,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  onKeyboardAction: KeyboardActionHandler? = null,
  lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
  onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
  interactionSource: MutableInteractionSource? = null,
  cursorBrush: Brush,
  outputTransformation: OutputTransformation? = null,
  scrollState: ScrollState = rememberScrollState(),
  maxTextSize: TextUnit = TextUnit.Unspecified,
  minRatio: Float = 1f,
  alignment: Alignment = Alignment.BottomEnd,
  placeholder: String? = null,
  enableSoftwareKeyboard: Boolean = false,
  displayedText: AnnotatedString = remember(state.text) { AnnotatedString(state.text.toString()) },
) =
  AutoSizeTextStyleBox(
    modifier = modifier,
    text = displayedText,
    maxTextSize = maxTextSize,
    lineLimits = lineLimits,
    softWrap = false,
    style = textStyle,
    minRatio = minRatio,
    alignment = alignment,
  ) {
    InterceptPlatformTextInput(
      interceptor = { request, nextHandler ->
        if (enableSoftwareKeyboard) nextHandler.startInputMethod(request) else awaitCancellation()
      }
    ) {
      val currentTextToolbar = LocalTextToolbar.current
      val style = LocalTextStyle.current
      val focusRequester = remember { FocusRequester() }

      BasicTextField(
        state = state,
        modifier =
          Modifier.fillMaxWidth()
            .focusRequester(focusRequester)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null,
              onClick = {
                currentTextToolbar.hide()
                focusRequester.requestFocus()
                state.edit { selection = TextRange.Zero }
                currentTextToolbar.showMenu(Rect(Offset.Zero, 0f))
              },
            ),
        enabled = enabled,
        readOnly = readOnly,
        inputTransformation = inputTransformation,
        textStyle = style,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        outputTransformation = outputTransformation,
        decorator = { innerTextField ->
          if (state.text.isEmpty() and !placeholder.isNullOrEmpty()) {
            Text(
              text = placeholder!!,
              style =
                style.copy(
                  textAlign = TextAlign.End,
                  color = MaterialTheme.colorScheme.onSurface.copy(TEXT_FIELD_PLACEHOLDER_ALPHA),
                ),
            )
          }
          innerTextField()
        },
        scrollState = scrollState,
      )
    }
  }

private const val TEXT_FIELD_PLACEHOLDER_ALPHA = 0.5f

@Preview
@Composable
private fun ExpressionTextFieldPreview() {
  ExpressionTextField(
    modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline).height(172.dp),
    state = remember { TextFieldState() },
    formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
    minRatio = 0.5f,
  )
}
