/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.icons.iconpack.And
import com.sadellie.unitto.core.designsystem.icons.iconpack.Base
import com.sadellie.unitto.core.designsystem.icons.iconpack.IconPack
import com.sadellie.unitto.core.designsystem.icons.iconpack.Mod
import com.sadellie.unitto.core.designsystem.icons.iconpack.Nand
import com.sadellie.unitto.core.designsystem.icons.iconpack.Nor
import com.sadellie.unitto.core.designsystem.icons.iconpack.Not
import com.sadellie.unitto.core.designsystem.icons.iconpack.Or
import com.sadellie.unitto.core.designsystem.icons.iconpack.Shift
import com.sadellie.unitto.core.designsystem.icons.iconpack.ShiftLeft
import com.sadellie.unitto.core.designsystem.icons.iconpack.ShiftRight
import com.sadellie.unitto.core.designsystem.icons.iconpack.Size
import com.sadellie.unitto.core.designsystem.icons.iconpack.Xor
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.ProgrammerStartRoute
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.KeyboardButtonToken
import com.sadellie.unitto.core.ui.KeypadButton
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BackspaceKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.BracketsKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.ClearKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.DivideKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.EqualKey
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
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyA
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyB
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyC
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyD
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyE
import com.sadellie.unitto.core.ui.KeypadButton.Companion.KeyF
import com.sadellie.unitto.core.ui.KeypadButton.Companion.LeftBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MinusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.MultiplyKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.PlusKey
import com.sadellie.unitto.core.ui.KeypadButton.Companion.RightBracketKey
import com.sadellie.unitto.core.ui.KeypadButton.KeypadButtonAdd
import com.sadellie.unitto.core.ui.KeypadButtonFilled
import com.sadellie.unitto.core.ui.KeypadButtonFilledPrimary
import com.sadellie.unitto.core.ui.KeypadButtonLight
import com.sadellie.unitto.core.ui.KeypadButtonTertiary
import com.sadellie.unitto.core.ui.KeypadButtonTransparent
import com.sadellie.unitto.core.ui.KeypadFlow
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.textfield.AutoSizeTextField
import com.sadellie.unitto.core.ui.textfield.InputTransformationWithReplacement
import com.sadellie.unitto.core.ui.textfield.SimpleTextField
import com.sadellie.unitto.core.ui.textfield.getTextFieldState
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.core.ui.textfield.placeCursorAtTheEnd
import io.github.sadellie.evaluatto.programmer.DataUnit
import io.github.sadellie.evaluatto.programmer.programmerCalculateExpression
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.navigation3.navigation
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_error
import unitto.core.common.generated.resources.keyboard_percent

@OptIn(KoinExperimentalAPI::class)
fun Module.programmerNavigation() {
  navigation<ProgrammerStartRoute> {
    val navigator = LocalNavigator.current
    ProgrammerRoute(openDrawer = navigator::openDrawer)
  }
}

internal sealed interface ProgrammerScreenUIState {
  data class Ready(
    val input: TextFieldState,
    val output: ProgrammerCalculationResult,
    val showAcButton: Boolean,
    val formatterSymbols: FormatterSymbols,
    val middleZero: Boolean,
    val dataUnit: DataUnit,
    val base: Int,
  ) : ProgrammerScreenUIState

  data object Loading : ProgrammerScreenUIState
}

internal sealed interface ProgrammerCalculationResult {
  data object Empty : ProgrammerCalculationResult

  data class Success(val value: String) : ProgrammerCalculationResult

  sealed interface Error : ProgrammerCalculationResult {
    data object VisibleError : Error

    data object InvisibleError : Error
  }
}

internal class ProgrammerViewModel(
  private val userPrefsRepository: UserPreferencesRepository,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private var _calculationJob: Job? = null
  private val _inputKey = "PROGRAMMER_INPUT"
  private val _input = savedStateHandle.getTextFieldState(_inputKey)
  private val _result =
    MutableStateFlow<ProgrammerCalculationResult>(ProgrammerCalculationResult.Empty)
  private val _lastResult = MutableStateFlow("")
  private val _base = MutableStateFlow(10)
  private val _dataUnit = MutableStateFlow(DataUnit.QWORD)
  private val _prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

  val uiState =
    combine(_prefs, _result, _dataUnit, _base) { prefs, result, unit, base ->
        prefs ?: return@combine ProgrammerScreenUIState.Loading
        ProgrammerScreenUIState.Ready(
          input = _input,
          output = result,
          showAcButton = prefs.acButton,
          formatterSymbols = prefs.formatterSymbols,
          middleZero = prefs.middleZero,
          dataUnit = unit,
          base = base,
        )
      }
      .stateIn(viewModelScope, ProgrammerScreenUIState.Loading)

  suspend fun observe() {
    _input.observe().collectLatest { input ->
      val lastResult = _lastResult.value
      // skip
      if (lastResult == input && lastResult.isNotEmpty()) return@collectLatest
      savedStateHandle[_inputKey] = input.toString()
      calculate()
    }
  }

  fun onClear() {
    _input.clearText()
    _result.update { ProgrammerCalculationResult.Empty }
  }

  fun onBrackets() {
    val isEqualClicked = _lastResult.value.isNotEmpty()
    if (isEqualClicked) {
      _input.placeCursorAtTheEnd()
      _lastResult.update { "" }
    }
    with(TextFieldStateTokenExtensionsProgrammer) { _input.addBracket() }
  }

  fun onAddToken(token: String) {
    val isEqualClicked = _lastResult.value.isNotEmpty()
    if (isEqualClicked) {
      when {
        token in Token.digitsWithDotSymbols -> _input.clearText()
        else -> _input.placeCursorAtTheEnd()
      }
      _lastResult.update { "" }
    }
    with(TextFieldStateTokenExtensionsProgrammer) { _input.addTokens(token) }
  }

  fun onDelete() {
    val isEqualClicked = _lastResult.value.isNotEmpty()
    if (isEqualClicked) {
      _input.clearText()
      _lastResult.update { "" }
    } else {
      with(TextFieldStateTokenExtensionsProgrammer) { _input.deleteTokens() }
    }
  }

  fun onEqual() {
    val result = _result.value
    Logger.d(tag = TAG) { "onEqual: $result" }
    when (result) {
      is ProgrammerCalculationResult.Success -> {
        _lastResult.update { "" }
        _input.setTextAndPlaceCursorAtEnd(result.value)
        _result.update { ProgrammerCalculationResult.Empty }
      }
      is ProgrammerCalculationResult.Error ->
        _result.update { ProgrammerCalculationResult.Error.VisibleError }
      ProgrammerCalculationResult.Empty -> return
    }
  }

  fun toggleSize() {
    _dataUnit.update { unit ->
      when (unit) {
        DataUnit.QWORD -> DataUnit.WORD
        DataUnit.WORD -> DataUnit.BYTE
        DataUnit.BYTE -> DataUnit.QWORD
      }
    }
    _lastResult.update { "" }
    calculate()
  }

  fun toggleBase() {
    val oldRadix = _base.value
    val newRadix =
      when (oldRadix) {
        2 -> 8
        8 -> 10
        10 -> 16
        else -> 2
      }

    val currentExpression = _input.text.toString()
    if (currentExpression.isEmpty()) {
      _base.update { newRadix }
      return
    }

    val convertedExpression =
      convertExpressionBase(
        expression = currentExpression,
        fromRadix = oldRadix,
        toRadix = newRadix,
        dataUnit = _dataUnit.value,
      )

    _input.setTextAndPlaceCursorAtEnd(convertedExpression)
    _base.update { newRadix }
    _lastResult.update { "" }
    calculate()
  }

  private fun calculate() {
    _calculationJob?.cancel()
    _calculationJob =
      viewModelScope.launch {
        val prefs = _prefs.value ?: return@launch
        // TODO base and qword in prefs
        val newResult =
          try {
            ProgrammerCalculationResult.Success(
              programmerCalculateExpression(_input.text.toString(), _base.value, _dataUnit.value)
            )
          } catch (e: Exception) {
            Logger.e(throwable = e, tag = TAG) { "Failed to calculate" }
            ProgrammerCalculationResult.Error.InvisibleError
          }

        Logger.d(tag = TAG) { "Calculate: $newResult" }
        _result.update { newResult }
      }
  }

  companion object {
    private const val TAG = "ProgrammerViewModel"
  }
}

@Composable
internal fun ProgrammerRoute(openDrawer: () -> Unit) {
  val viewModel: ProgrammerViewModel = koinViewModel()
  LaunchedEffect(Unit) { viewModel.observe() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycleKMP().value) {
    ProgrammerScreenUIState.Loading -> EmptyScreen()
    is ProgrammerScreenUIState.Ready ->
      ProgrammerScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        onClearClick = viewModel::onClear,
        onBracketsClick = viewModel::onBrackets,
        onAddTokenClick = viewModel::onAddToken,
        onDeleteClick = viewModel::onDelete,
        onEqualClick = viewModel::onEqual,
        toggleSize = viewModel::toggleSize,
        toggleBase = viewModel::toggleBase,
      )
  }
}

@Composable
private fun ProgrammerScreen(
  uiState: ProgrammerScreenUIState.Ready,
  openDrawer: () -> Unit,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
) {
  ScaffoldWithTopBar(
    title = {
      Text(
        text = "${uiState.base} (${uiState.dataUnit.name})",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    },
    navigationIcon = { DrawerButton(onClick = openDrawer) },
    colors =
      TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
  ) { paddingValues ->
    // TODO Expanded width or compact height
    Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
      TextFieldsBox(
        modifier = Modifier.fillMaxHeight(0.25f).fillMaxWidth(),
        input = uiState.input,
        output = uiState.output,
        formatterSymbols = uiState.formatterSymbols,
      )

      ProgrammerKeyboard(
        modifier = Modifier.weight(1f).fillMaxWidth(),
        showAcButton = uiState.showAcButton,
        onClearClick = onClearClick,
        onBracketsClick = onBracketsClick,
        onAddTokenClick = onAddTokenClick,
        onDeleteClick = onDeleteClick,
        onEqualClick = onEqualClick,
        middleZero = uiState.middleZero,
        toggleSize = toggleSize,
        toggleBase = toggleBase,
        base = uiState.base,
      )
    }
  }
}

@Composable
private fun TextFieldsBox(
  modifier: Modifier,
  input: TextFieldState,
  output: ProgrammerCalculationResult,
  formatterSymbols: FormatterSymbols,
) {
  Column(modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
    Text("Preview. Backend test, not UI")
    ProgrammerTextField(
      modifier = Modifier.weight(3f).fillMaxWidth(),
      state = input,
      minRatio = 0.5f,
      textColor = MaterialTheme.colorScheme.onSurfaceVariant,
      readOnly = false,
      formatterSymbols = formatterSymbols,
    )
    ProgrammerResultTextField(
      modifier = Modifier.weight(2f).fillMaxWidth(),
      result = output,
      formatterSymbols = formatterSymbols,
    )
  }
}

@Composable
private fun ProgrammerResultTextField(
  modifier: Modifier,
  result: ProgrammerCalculationResult,
  formatterSymbols: FormatterSymbols,
) {
  LaunchedEffect(result) { Logger.d(tag = "TAGGER") { "Result: $result" } }
  when (result) {
    ProgrammerCalculationResult.Empty,
    ProgrammerCalculationResult.Error.InvisibleError -> Spacer(modifier)
    ProgrammerCalculationResult.Error.VisibleError -> {
      val error = stringResource(Res.string.common_error)
      SimpleTextField(
        modifier = modifier,
        state = remember(error) { TextFieldState(error) },
        minRatio = 0.5f,
        textColor = MaterialTheme.colorScheme.error,
        readOnly = true,
      )
    }
    is ProgrammerCalculationResult.Success ->
      ProgrammerTextField(
        modifier = modifier,
        state = remember(result.value) { TextFieldState(result.value) },
        minRatio = 0.5f,
        textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
        readOnly = true,
        formatterSymbols = formatterSymbols,
      )
  }
}

@Composable
private fun ProgrammerTextField(
  modifier: Modifier,
  state: TextFieldState,
  formatterSymbols: FormatterSymbols,
  readOnly: Boolean,
  textColor: Color,
  minRatio: Float,
) {
  AutoSizeTextField(
    state = state,
    modifier = modifier,
    readOnly = readOnly,
    inputTransformation = ProgrammerInputTransformation(formatterSymbols.grouping),
    textStyle = LocalNumberTypography.current.displayLarge.copy(textColor),
    lineLimits = TextFieldLineLimits.SingleLine,
    cursorBrush = SolidColor(textColor),
    minRatio = minRatio,
  )
}

@Composable
private fun ProgrammerKeyboard(
  modifier: Modifier,
  showAcButton: Boolean,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  middleZero: Boolean,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  base: Int,
) {
  KeypadFlow(modifier = modifier, rows = 9, columns = 4) { width, height ->
    val iconHeightSecondary = KeyboardButtonToken.ICON_HEIGHT_TALL
    val buttonModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)

    KeypadButtonTransparent(buttonModifier, KeyOr, iconHeightSecondary, onAddTokenClick)
    KeypadButtonTransparent(buttonModifier, KeyAnd, iconHeightSecondary, onAddTokenClick)
    KeypadButtonTransparent(buttonModifier, KeyNot, iconHeightSecondary, onAddTokenClick)
    KeypadButtonTransparent(buttonModifier, KeyMod, iconHeightSecondary, onAddTokenClick)

    KeypadButtonTransparent(buttonModifier, KeyNor, iconHeightSecondary, onAddTokenClick)
    KeypadButtonTransparent(buttonModifier, KeyNand, iconHeightSecondary, onAddTokenClick)
    KeypadButtonTransparent(buttonModifier, KeyXor, iconHeightSecondary, onAddTokenClick)
    KeypadButtonTransparent(buttonModifier, KeySize, iconHeightSecondary, toggleSize)

    val iconHeight = KeyboardButtonToken.ICON_HEIGHT_TALL
    if (showAcButton) {
      KeypadButtonTertiary(buttonModifier, ClearKey, iconHeight, onClearClick)
      KeypadButtonFilled(buttonModifier, BracketsKey, iconHeight, onBracketsClick)
    } else {
      KeypadButtonFilled(buttonModifier, LeftBracketKey, iconHeight, onAddTokenClick)
      KeypadButtonFilled(buttonModifier, RightBracketKey, iconHeight, onAddTokenClick)
    }
    KeypadButtonFilled(buttonModifier, KeyLsh, iconHeight, onAddTokenClick)
    KeypadButtonFilled(buttonModifier, KeyRsh, iconHeight, onAddTokenClick)

    KeypadButtonLight(buttonModifier, KeyD, iconHeight, onAddTokenClick, base >= 14)
    KeypadButtonLight(buttonModifier, KeyE, iconHeight, onAddTokenClick, base >= 15)
    KeypadButtonLight(buttonModifier, KeyF, iconHeight, onAddTokenClick, base >= 16)
    KeypadButtonFilled(buttonModifier, KeyShiftType, iconHeight, {}, false)

    KeypadButtonLight(buttonModifier, KeyA, iconHeight, onAddTokenClick, base >= 11)
    KeypadButtonLight(buttonModifier, KeyB, iconHeight, onAddTokenClick, base >= 12)
    KeypadButtonLight(buttonModifier, KeyC, iconHeight, onAddTokenClick, base >= 13)
    KeypadButtonFilled(buttonModifier, DivideKey, iconHeight, onAddTokenClick)

    KeypadButtonLight(buttonModifier, Key7, iconHeight, onAddTokenClick, base >= 8)
    KeypadButtonLight(buttonModifier, Key8, iconHeight, onAddTokenClick, base >= 9)
    KeypadButtonLight(buttonModifier, Key9, iconHeight, onAddTokenClick, base >= 10)
    KeypadButtonFilled(buttonModifier, MultiplyKey, iconHeight, onAddTokenClick)

    KeypadButtonLight(buttonModifier, Key4, iconHeight, onAddTokenClick, base >= 5)
    KeypadButtonLight(buttonModifier, Key5, iconHeight, onAddTokenClick, base >= 6)
    KeypadButtonLight(buttonModifier, Key6, iconHeight, onAddTokenClick, base >= 7)
    KeypadButtonFilled(buttonModifier, MinusKey, iconHeight, onAddTokenClick)

    KeypadButtonLight(buttonModifier, Key1, iconHeight, onAddTokenClick, base >= 2)
    KeypadButtonLight(buttonModifier, Key2, iconHeight, onAddTokenClick, base >= 3)
    KeypadButtonLight(buttonModifier, Key3, iconHeight, onAddTokenClick, base >= 4)
    KeypadButtonFilled(buttonModifier, PlusKey, iconHeight, onAddTokenClick)

    if (middleZero) {
      KeypadButtonLight(buttonModifier, KeyBaseSwitch, iconHeight, null, toggleBase)
      KeypadButtonLight(buttonModifier, Key0, iconHeight, onAddTokenClick)
    } else {
      KeypadButtonLight(buttonModifier, Key0, iconHeight, onAddTokenClick)
      KeypadButtonLight(buttonModifier, KeyBaseSwitch, iconHeight, null, toggleBase)
    }
    KeypadButtonLight(buttonModifier, BackspaceKey, iconHeight, onClearClick, onDeleteClick)
    KeypadButtonFilledPrimary(buttonModifier, EqualKey, iconHeight, onEqualClick)
  }
}

// TODO image descriptions
private val KeyOr = KeypadButtonAdd(IconPack.Or, Res.string.keyboard_percent, Token.Or.symbol)
private val KeyAnd = KeypadButtonAdd(IconPack.And, Res.string.keyboard_percent, Token.And.symbol)
private val KeyNot = KeypadButtonAdd(IconPack.Not, Res.string.keyboard_percent, Token.Not.symbol)
private val KeyNand = KeypadButtonAdd(IconPack.Nand, Res.string.keyboard_percent, Token.Nand.symbol)
private val KeyNor = KeypadButtonAdd(IconPack.Nor, Res.string.keyboard_percent, Token.Nor.symbol)
private val KeyXor = KeypadButtonAdd(IconPack.Xor, Res.string.keyboard_percent, Token.Xor.symbol)
private val KeyMod = KeypadButtonAdd(IconPack.Mod, Res.string.keyboard_percent, Token.Mod.symbol)
// TODO other shift types
private val KeyLsh =
  KeypadButtonAdd(IconPack.ShiftLeft, Res.string.keyboard_percent, Token.Lsh.symbol)
private val KeyRsh =
  KeypadButtonAdd(IconPack.ShiftRight, Res.string.keyboard_percent, Token.Rsh.symbol)
private val KeyBaseSwitch =
  KeypadButton.KeypadButtonSimple(IconPack.Base, Res.string.keyboard_percent)
private val KeyShiftType =
  KeypadButton.KeypadButtonSimple(IconPack.Shift, Res.string.keyboard_percent)
private val KeySize = KeypadButton.KeypadButtonSimple(IconPack.Size, Res.string.keyboard_percent)

@Stable
// TODO auto tests
internal data class ProgrammerInputTransformation(private val grouping: Token.Formatter) :
  InputTransformationWithReplacement {
  override val legalTokens: List<String> =
    listOf(
      Token.Digit0.symbol,
      Token.Digit1.symbol,
      Token.Digit2.symbol,
      Token.Digit3.symbol,
      Token.Digit4.symbol,
      Token.Digit5.symbol,
      Token.Digit6.symbol,
      Token.Digit7.symbol,
      Token.Digit8.symbol,
      Token.Digit9.symbol,
      Token.LetterA.symbol,
      Token.LetterB.symbol,
      Token.LetterC.symbol,
      Token.LetterD.symbol,
      Token.LetterE.symbol,
      Token.LetterF.symbol,
      Token.Minus.symbol,
      Token.Divide.symbol,
      Token.Multiply.symbol,
      Token.Plus.symbol,
      Token.LeftBracket.symbol,
      Token.RightBracket.symbol,
      Token.Or.symbol,
      Token.And.symbol,
      Token.Not.symbol,
      Token.Nand.symbol,
      Token.Nor.symbol,
      Token.Xor.symbol,
      Token.Lsh.symbol,
      Token.Rsh.symbol,
      Token.Mod.symbol,
    )

  override val replacementMap: Map<String, String> =
    mapOf(
      grouping.symbol to "",
      "-" to Token.Minus.symbol,
      "–" to Token.Minus.symbol,
      "—" to Token.Minus.symbol,
      "/" to Token.Divide.symbol,
      "*" to Token.Multiply.symbol,
      "•" to Token.Multiply.symbol,
      "a" to Token.LetterA.symbol,
      "b" to Token.LetterB.symbol,
      "c" to Token.LetterC.symbol,
      "d" to Token.LetterD.symbol,
      "e" to Token.LetterE.symbol,
      "f" to Token.LetterF.symbol,
    )

  private val longProgrammerTokens =
    listOf(
      Token.Or.symbol,
      Token.And.symbol,
      Token.Not.symbol,
      Token.Nand.symbol,
      Token.Nor.symbol,
      Token.Xor.symbol,
      Token.Lsh.symbol,
      Token.Rsh.symbol,
      Token.Mod.symbol,
    )

  override fun TextFieldBuffer.transformInput() =
    transformInputWithReplacements(longProgrammerTokens)
}

@Composable
@Preview
private fun PreviewProgrammerScreen() = ExpressivePreview {
  ProgrammerScreen(
    uiState =
      remember {
        ProgrammerScreenUIState.Ready(
          input = TextFieldState("123ABC"),
          output = ProgrammerCalculationResult.Success("789"),
          showAcButton = true,
          formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
          middleZero = true,
          dataUnit = DataUnit.QWORD,
          base = 10,
        )
      },
    openDrawer = {},
    onClearClick = {},
    onBracketsClick = {},
    onAddTokenClick = {},
    onDeleteClick = {},
    onEqualClick = {},
    toggleSize = {},
    toggleBase = {},
  )
}
