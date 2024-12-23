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

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isExpression
import com.sadellie.unitto.core.common.toFormattedString
import io.github.sadellie.evaluatto.Expression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTokenAction : ActionCallback {
  companion object {
    val tokenToAddParam = ActionParameters.Key<String>("tokenToAdd")

    fun create(tokenToAdd: String) =
      actionRunCallback<AddTokenAction>(actionParametersOf(tokenToAddParam to tokenToAdd))
  }

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    val tokenToAdd = parameters[tokenToAddParam] ?: ""
    updateAppWidgetState(context, glanceId) { state ->
      val isEqualClicked = state[CalculatorWidget.equalClickedStateKey] ?: false
      // Clear input if equal is clicked and new token is a Digit
      if (isEqualClicked && tokenToAdd in Token.Digit.allWithDot) {
        state[CalculatorWidget.inputStateKey] = ""
        state[CalculatorWidget.outputStateKey] = ""
      }

      val currentInput = state[CalculatorWidget.inputStateKey] ?: ""
      val newInput = currentInput.addToken(tokenToAdd)
      val precision = state[CalculatorWidget.precisionStateKey] ?: DEFAULT_PRECISION
      val outputFormat = state[CalculatorWidget.outputFormatStateKey] ?: DEFAULT_OUTPUT_FORMAT
      val newOutput = calculate(newInput, precision, outputFormat)
      state[CalculatorWidget.inputStateKey] = newInput
      state[CalculatorWidget.outputStateKey] = newOutput
      state[CalculatorWidget.equalClickedStateKey] = false
    }
    CalculatorWidget().update(context, glanceId)
  }
}

internal class AddBracketAction : ActionCallback {
  companion object {
    fun create() = actionRunCallback<AddBracketAction>()
  }

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    updateAppWidgetState(context, glanceId) { state ->
      val currentInput = state[CalculatorWidget.inputStateKey] ?: ""
      val newInput = currentInput.addBracket()
      val precision = state[CalculatorWidget.precisionStateKey] ?: DEFAULT_PRECISION
      val outputFormat = state[CalculatorWidget.outputFormatStateKey] ?: DEFAULT_OUTPUT_FORMAT
      val newOutput = calculate(newInput, precision, outputFormat)
      state[CalculatorWidget.inputStateKey] = newInput
      state[CalculatorWidget.outputStateKey] = newOutput
      state[CalculatorWidget.equalClickedStateKey] = false
    }
    CalculatorWidget().update(context, glanceId)
  }
}

internal class DeleteTokenAction : ActionCallback {
  companion object {
    fun create() = actionRunCallback<DeleteTokenAction>()
  }

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    updateAppWidgetState(context, glanceId) { state ->
      val currentInput = state[CalculatorWidget.inputStateKey] ?: ""
      val newInput = currentInput.dropLast(1)
      val precision = state[CalculatorWidget.precisionStateKey] ?: DEFAULT_PRECISION
      val outputFormat = state[CalculatorWidget.outputFormatStateKey] ?: DEFAULT_OUTPUT_FORMAT
      val newOutput = calculate(newInput, precision, outputFormat)
      state[CalculatorWidget.inputStateKey] = newInput
      state[CalculatorWidget.outputStateKey] = newOutput
      state[CalculatorWidget.equalClickedStateKey] = false
    }
    CalculatorWidget().update(context, glanceId)
  }
}

internal class EqualAction : ActionCallback {
  companion object {
    fun create() = actionRunCallback<EqualAction>()
  }

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    updateAppWidgetState(context, glanceId) { state ->
      val currentInput = state[CalculatorWidget.inputStateKey] ?: ""
      val precision = state[CalculatorWidget.precisionStateKey] ?: DEFAULT_PRECISION
      val outputFormat = state[CalculatorWidget.outputFormatStateKey] ?: DEFAULT_OUTPUT_FORMAT
      val calculation = calculate(currentInput, precision, outputFormat)
      if (calculation == "") {
        state[CalculatorWidget.equalClickedStateKey] = false
      } else {
        state[CalculatorWidget.inputStateKey] = calculation
        state[CalculatorWidget.outputStateKey] = ""
        state[CalculatorWidget.equalClickedStateKey] = true
      }
    }
    CalculatorWidget().update(context, glanceId)
  }
}

internal class ClearInputAction : ActionCallback {
  companion object {
    fun create() = actionRunCallback<ClearInputAction>()
  }

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    updateAppWidgetState(context, glanceId) { state ->
      state[CalculatorWidget.inputStateKey] = ""
      state[CalculatorWidget.outputStateKey] = ""
      state[CalculatorWidget.equalClickedStateKey] = false
    }
    CalculatorWidget().update(context, glanceId)
  }
}

internal class CopyResultAction : ActionCallback {
  companion object {
    internal val outputParamKey = ActionParameters.Key<String>("outputParam")

    fun create(output: String, grouping: String): Action =
      actionRunCallback<CopyResultAction>(
        actionParametersOf(outputParamKey to output.replace(grouping, ""))
      )
  }

  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    val output = parameters[outputParamKey]
    if (output.isNullOrEmpty()) return

    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cm.setPrimaryClip(ClipData.newPlainText("Unitto", output))
  }
}

internal class RestartWidget : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters,
  ) {
    CalculatorWidget().update(context, glanceId)
  }
}

internal fun launchAction(mContext: Context): Action =
  actionStartActivity(ComponentName(mContext, "com.sadellie.unitto.MainActivity"))

internal const val DEFAULT_PRECISION = 3
internal const val DEFAULT_OUTPUT_FORMAT = OutputFormat.PLAIN

private suspend fun calculate(input: String, precision: Int, outputFormat: Int): String =
  withContext(Dispatchers.Default) {
    if (input.isEmpty()) return@withContext ""
    if (!input.isExpression()) return@withContext ""

    return@withContext try {
      Expression(input).calculate().toFormattedString(precision, outputFormat)
    } catch (e: Exception) {
      ""
    }
  }
