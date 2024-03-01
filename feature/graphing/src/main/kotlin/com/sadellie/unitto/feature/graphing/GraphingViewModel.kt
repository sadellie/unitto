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

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.floor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class GraphingViewModel
@Inject
constructor(userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
  private var calculationJob: Job? = null
  private val _functions = MutableStateFlow(emptyList<GraphFunction>())
  private val _graphs = MutableStateFlow(emptySet<GraphLine>())
  private val _minPx = MutableStateFlow(0)
  private val _maxPx = MutableStateFlow(0)
  private val _tileZoom = MutableStateFlow(DEFAULT_ZOOM)

  val mainUiState =
    combine(_functions, _graphs, _tileZoom, userPreferencesRepository.calculatorPrefs) {
        functions,
        graphs,
        tileZoom,
        calculatorPrefs ->
        return@combine GraphingUIState(
          functions = functions,
          graphs = graphs,
          tileZoom = tileZoom,
          middleZero = calculatorPrefs.middleZero,
          showAcButton = calculatorPrefs.acButton,
          formatterSymbols = calculatorPrefs.formatterSymbols,
          inverseMode = calculatorPrefs.inverseMode,
        )
      }
      .stateIn(viewModelScope, null)

  fun onAddFunction(newFunction: GraphFunction) =
    _functions.update { currentFunctions ->
      val maxId = currentFunctions.maxOfOrNull { function -> function.id } ?: 0
      currentFunctions + newFunction.copy(id = maxId + 1)
    }

  fun onEditFunction(updatedFunction: GraphFunction) =
    _functions.update { currentFunctions ->
      currentFunctions.map { function ->
        // replace only if matches by id
        if (function.id == updatedFunction.id) updatedFunction else function
      }
    }

  fun onRemoveFunction(removedFunction: GraphFunction) =
    _functions.update { currentFunctions ->
      currentFunctions.filter { function -> function.id != removedFunction.id }
    }

  fun onCanvasUpdate(minX: Int, maxX: Int) {
    _minPx.update { minX }
    _maxPx.update { maxX }
  }

  fun updateTileZoom(newTileZoom: Float) = _tileZoom.update { newTileZoom }

  suspend fun observeInput() {
    combine(_functions, _minPx, _maxPx, _tileZoom) { functions, minPx, maxPx, tileZoom ->
        recalculateGraphs(functions, minPx, maxPx, tileZoom)
      }
      .collectLatest {}
  }

  private fun recalculateGraphs(
    functions: List<GraphFunction>,
    minPx: Int,
    maxPx: Int,
    tileZoom: Float,
  ) {
    calculationJob?.cancel()
    calculationJob =
      viewModelScope.launch(Dispatchers.Default) {
        // - because offset goes the opposite way
        // floor avoid rounding on the left
        var minX = floor(minPx / tileZoom)
        // ceil avoid rounding on the right
        var maxX = ceil(maxPx / tileZoom)
        // precalculate coordinates for nearby screens
        val betweenX = (maxX - minX) * X_PADDING
        minX -= betweenX
        maxX += betweenX

        // calculate 8 y coordinates for each tile
        val stepInX = DEFAULT_ZOOM / tileZoom / X_STEP_PRECISION

        val graphs = mutableSetOf<GraphLine>()
        for (function in functions) {
          var currentX = minX
          val offsets = mutableListOf<Offset>()

          try {
            while (currentX <= maxX) {
              val xValue = currentX
                // Avoid engineering string
                .toBigDecimal()
                .toPlainString()
                // Fix minus sign for evaluatto
                .replace("-", Token.Operator.MINUS)
              val replacedX =
                function.expression
                  .replace(Token.Func.EXP, "EXP")
                  .replace("x", xValue)
                  .replace("EXP", Token.Func.EXP)

              val y = Expression(replacedX, scale = Y_COORDINATE_PRECISION).calculate()
              offsets.add(Offset(currentX, y.toFloat()))

              currentX += stepInX
            }
          } catch (e: Exception) {
            // can be invalid for this sample x, but valid for others
            val isSkippableExpressionException =
              e is ExpressionException.DivideByZero ||
                e is ExpressionException.FactorialCalculation ||
                e is ExpressionException.TooBig

            // really broken, drop everything for this function
            if (!isSkippableExpressionException) {
              offsets.clear()
            }
          }

          graphs.add(GraphLine(function, offsets))
        }

        _graphs.update { graphs }
      }
  }
}

// How many additional Xs to precalculate. 3 screens to the left and 3 to the right + 1 visible
private const val X_PADDING = 3
// How many Ys per tile to calculate. Higher is more accurate, but lowers performance
private const val X_STEP_PRECISION = 8f
// How accurate Y coordinate is. Higher is more accurate, but lowers performance really quick
private const val Y_COORDINATE_PRECISION = 5
