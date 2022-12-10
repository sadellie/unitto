/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens

import androidx.room.Room
import com.sadellie.unitto.CoroutineTestRule
import com.sadellie.unitto.data.KEY_0
import com.sadellie.unitto.data.KEY_1
import com.sadellie.unitto.data.KEY_2
import com.sadellie.unitto.data.KEY_3
import com.sadellie.unitto.data.KEY_4
import com.sadellie.unitto.data.KEY_5
import com.sadellie.unitto.data.KEY_6
import com.sadellie.unitto.data.KEY_7
import com.sadellie.unitto.data.KEY_8
import com.sadellie.unitto.data.KEY_9
import com.sadellie.unitto.data.KEY_COMMA
import com.sadellie.unitto.data.KEY_DIVIDE
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_EXPONENT
import com.sadellie.unitto.data.KEY_LEFT_BRACKET
import com.sadellie.unitto.data.KEY_MINUS
import com.sadellie.unitto.data.KEY_MULTIPLY
import com.sadellie.unitto.data.KEY_PLUS
import com.sadellie.unitto.data.KEY_RIGHT_BRACKET
import com.sadellie.unitto.data.KEY_SQRT
import com.sadellie.unitto.data.preferences.DataStoreModule
import com.sadellie.unitto.data.preferences.UserPreferencesRepository
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.data.units.database.MyBasedUnitDatabase
import com.sadellie.unitto.data.units.database.MyBasedUnitsRepository
import com.sadellie.unitto.screens.main.MainViewModel
import com.sadellie.unitto.testInViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@OptIn(ExperimentalCoroutinesApi::class)
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel
    private val allUnitsRepository = AllUnitsRepository()

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            UserPreferencesRepository(
                DataStoreModule().provideUserPreferencesDataStore(
                    RuntimeEnvironment.getApplication()
                )
            ), MyBasedUnitsRepository(
                Room.inMemoryDatabaseBuilder(
                    RuntimeEnvironment.getApplication(), MyBasedUnitDatabase::class.java
                ).build().myBasedUnitDao()
            ), RuntimeEnvironment.getApplication(), allUnitsRepository
        )
    }

    @Test
    fun processInputTest() = testInViewModel { coroutineScope ->
        viewModel.mainFlow.launchIn(coroutineScope)

        /**
         * For simplicity comments will have structure:
         * currentInput | userInput | processed internal input | processed display output
         */
        `test 0`()
        `test digits from 1 to 9`()
        `test plus, divide, multiply and exponent operators`()
        `test dot`()
        `test minus`()
        `test brackets`()
        `test square root`()
    }

    private fun `test 0`() {
        inputOutputTest("0", "0", "0")
        inputOutputTest("123000", "123000", "123000")
        inputOutputTest("123.000", "123.000", "123.000")
        inputOutputTest("-000", "-0", "–0")
        inputOutputTest("12+000", "12+0", "12+0")
        inputOutputTest("√000", "√0", "√0")
        inputOutputTest("(000", "(0", "(0")
        inputOutputTest("(1+12)000", "(1+12)*0", "(1+12)×0")
        inputOutputTest("(1.002+120)000", "(1.002+120)*0", "(1.002+120)×0")
    }

    private fun `test digits from 1 to 9`() {
        inputOutputTest("123456789", "123456789", "123456789")
        inputOutputTest("(1+1)111", "(1+1)*111", "(1+1)×111")
    }

    private fun `test plus, divide, multiply and exponent operators`() {
        inputOutputTest("0+++", "0", "0")
        inputOutputTest("123+++", "123+", "123+")
        inputOutputTest("1-***", "1*", "1×")
        inputOutputTest("1/-+++", "1+", "1+")
        inputOutputTest("0^^^", "0", "0")
        inputOutputTest("12^^^", "12^", "12^")
        inputOutputTest("(^^^", "(", "(")
        inputOutputTest("(8+9)^^^", "(8+9)^", "(8+9)^")
    }

    private fun `test dot`() {
        inputOutputTest("0...", "0.", "0.")
        inputOutputTest("1...", "1.", "1.")
        inputOutputTest("1+...", "1+.", "1+.")
        inputOutputTest("√...", "√.", "√.")
        inputOutputTest("√21...", "√21.", "√21.")
        inputOutputTest("√21+1.01-.23...", "√21+1.01-.23", "√21+1.01–.23")
    }

    private fun `test minus`() {
        inputOutputTest("0---", "-", "–")
        inputOutputTest("12---", "12-", "12–")
        inputOutputTest("12+---", "12-", "12–")
        inputOutputTest("12/---", "12/-", "12÷–")
        inputOutputTest("√---", "√-", "√–")
        inputOutputTest("√///", "√", "√")
        inputOutputTest("12^----", "12^-", "12^–")
    }

    private fun `test brackets`() {
        inputOutputTest("0)))", "0", "0")
        inputOutputTest("0(((", "(((", "(((")
        inputOutputTest("√(10+2)(", "√(10+2)*(", "√(10+2)×(")
        inputOutputTest("√(10+2./(", "√(10+2./(", "√(10+2.÷(")
        inputOutputTest("0()()))((", "((((", "((((")
        inputOutputTest("√(10+2)^(", "√(10+2)^(", "√(10+2)^(")
    }

    private fun `test square root`() {
        inputOutputTest("0√√√", "√√√", "√√√")
        inputOutputTest("123√√√", "123*√√√", "123×√√√")
    }

    @Test
    fun deleteSymbolTest() = testInViewModel { coroutineScope ->
        viewModel.mainFlow.launchIn(coroutineScope)

        listOf(
            KEY_1, KEY_2, KEY_3, KEY_4, KEY_5, KEY_6, KEY_7, KEY_8, KEY_9, KEY_0,
            KEY_DOT, KEY_COMMA, KEY_LEFT_BRACKET, KEY_RIGHT_BRACKET,
            KEY_PLUS, KEY_MINUS, KEY_DIVIDE, KEY_MULTIPLY, KEY_EXPONENT, KEY_SQRT,
        ).forEach {
            // We enter one symbol and delete it, should be default as a result
            viewModel.processInput(it)
            viewModel.deleteDigit()
            assertEquals("0", viewModel.mainFlow.value.inputValue)
            assertEquals("0", viewModel.inputValue.value)
        }
        viewModel.clearInput()

        // This should not delete default input (0)
        viewModel.deleteDigit()

        // Now we check that we can delete multiple values
        viewModel.processInput(KEY_3)
        viewModel.processInput(KEY_SQRT)
        viewModel.processInput(KEY_9)
        viewModel.deleteDigit()
        assertEquals("3*√", viewModel.inputValue.value)
        assertEquals("3×√", viewModel.mainFlow.value.inputValue)
    }

    @Test
    fun clearInputTest() = testInViewModel { coroutineScope ->
        viewModel.mainFlow.launchIn(coroutineScope)

        viewModel.processInput(KEY_3)
        viewModel.clearInput()
        assertEquals(null, viewModel.mainFlow.value.calculatedValue)

        viewModel.mainFlow.launchIn(coroutineScope)
        viewModel.processInput(KEY_3)
        viewModel.processInput(KEY_MULTIPLY)
        viewModel.clearInput()
        assertEquals(null, viewModel.mainFlow.value.calculatedValue)
    }

    /**
     * Takes [input] sequence as a single string (e.g. "123-23") and compares it with [output]
     * (internal) and [outputDisplay] (the that user sees).
     */
    private fun inputOutputTest(input: String, output: String, outputDisplay: String) {
        // Enter everything
        input.forEach {
            viewModel.processInput(it.toString())
        }
        assertEquals(output, viewModel.inputValue.value)
        assertEquals(outputDisplay, viewModel.mainFlow.value.inputValue)
        viewModel.clearInput()
    }
}
