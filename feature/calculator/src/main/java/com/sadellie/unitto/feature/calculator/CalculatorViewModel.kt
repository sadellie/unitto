/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_MINUS
import com.sadellie.unitto.core.base.KEY_MINUS_DISPLAY
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.data.setMinimumRequiredScale
import com.sadellie.unitto.data.toStringWith
import com.sadellie.unitto.data.trimZeros
import com.sadellie.unitto.data.userprefs.UserPreferences
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression
import java.math.BigDecimal
import javax.inject.Inject
import org.mariuszgromada.math.mxparser.mXparser as MathParser

@HiltViewModel
internal class CalculatorViewModel @Inject constructor(
    userPrefsRepository: UserPreferencesRepository
) : ViewModel() {
    private val _userPrefs: StateFlow<UserPreferences> =
        userPrefsRepository.userPreferencesFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UserPreferences()
        )

    private val _input: MutableStateFlow<String> = MutableStateFlow("")
    private val _output: MutableStateFlow<String> = MutableStateFlow("")
    private val _selection: MutableStateFlow<IntRange> = MutableStateFlow(IntRange(0, 0))
    private val _angleMode: MutableStateFlow<AngleMode> = MutableStateFlow(AngleMode.RAD)

    val uiState = combine(
        _input, _output, _selection, _angleMode
    ) { input, output, selection, angleMode ->
        return@combine CalculatorUIState(
            input = input,
            output = output,
            selection = selection,
            angleMode = angleMode
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), CalculatorUIState()
    )

    fun addSymbol(symbol: String) {
        val selection = _selection.value
        _input.update {
            if (it.isEmpty()) symbol else it.replaceRange(selection.first, selection.last, symbol)
        }
        _selection.update { it.first + symbol.length..it.first + symbol.length }
    }

    fun deleteSymbol() {
        val selection = _selection.value
        val newSelectionStart = when (selection.last) {
            0 -> return
            selection.first -> _selection.value.first - 1
            else -> _selection.value.first
        }

        _selection.update { newSelectionStart..newSelectionStart }
        _input.update { it.removeRange(newSelectionStart, selection.last) }
    }

    fun clearSymbols() {
        _selection.update { 0..0 }
        _input.update { "" }
    }

    fun toggleCalculatorMode() {
        _angleMode.update {
            if (it == AngleMode.DEG) {
                MathParser.setRadiansMode()
                AngleMode.RAD
            } else {
                MathParser.setDegreesMode()
                AngleMode.DEG
            }
        }
    }

    // Called when user clicks "=" on a keyboard
    fun evaluate() {
        if (!Expression(_input.value.clean).checkSyntax()) return

        _input.update { _output.value }
        _selection.update { _input.value.length.._input.value.length }
        _output.update { "" }
    }

    fun onCursorChange(selection: IntRange) {
        // When we paste, selection is set to the length of the pasted text (start and end)
        if (selection.first > _input.value.length) return
        _selection.update { selection }
    }

    private fun calculateInput() {
        // Input is empty, don't calculate
        if (_input.value.isEmpty()) {
            _output.update { "" }
            return
        }

        val calculated = Expression(_input.value.clean).calculate()

        // Calculation error, return NaN
        if (calculated.isNaN() or calculated.isInfinite()) {
            _output.update { calculated.toString() }
            return
        }

        val calculatedBigDecimal = calculated
            .toBigDecimal()
            .setMinimumRequiredScale(_userPrefs.value.digitsPrecision)
            .trimZeros()

        try {
            val inputBigDecimal = BigDecimal(_input.value)

            // Input and output are identical values
            if (inputBigDecimal.compareTo(calculatedBigDecimal) == 0) {
                _output.update { "" }
                return
            }
        } catch (e: NumberFormatException) {
            // Cannot compare input and output
        }
        _output.update {
            calculatedBigDecimal.toStringWith(_userPrefs.value.outputFormat)
        }
        return
    }

    /**
     * Clean input so that there are no syntax errors
     */
    private val String.clean: String
        get() {
            val leftBrackets = count { it.toString() == KEY_LEFT_BRACKET }
            val rightBrackets = count { it.toString() == KEY_RIGHT_BRACKET }
            val neededBrackets = leftBrackets - rightBrackets
            return this
                .replace(KEY_MINUS_DISPLAY, KEY_MINUS)
                .plus(KEY_RIGHT_BRACKET.repeat(neededBrackets.coerceAtLeast(0)))
        }

    init {
        /**
         * mxParser uses some unnecessary rounding for doubles. It causes expressions like 9999^9999
         * to load CPU very much. We use BigDecimal to achieve same result without CPU overload.
         */
        MathParser.setCanonicalRounding(false)

        // Observe and invoke calculation without UI lag.
        viewModelScope.launch(Dispatchers.Default) {
            merge(_userPrefs, _input, _angleMode).collectLatest {
                calculateInput()
            }
        }
    }
}
