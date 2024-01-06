/*
 * Unitto is a unit converter for Android
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

package com.sadellie.unitto.feature.glance.glance

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.isExpression
import io.github.sadellie.evaluatto.Expression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UpdateInputAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) = withContext(Dispatchers.Default) {
        // Get new input
        val input = parameters[UnittoCalculatorWidget.inputKey] ?: return@withContext
        val precision = parameters[UnittoCalculatorWidget.precisionKey] ?: return@withContext
        val outputFormat = parameters[UnittoCalculatorWidget.outputFormatKey] ?: return@withContext

        // calculate
        val output = try {
            calculate(input, precision, outputFormat)
        } catch (e: Exception) {
            ""
        }

        updateAppWidgetState(
            context = context,
            glanceId = glanceId,
        ) { preferences ->
            preferences.apply {
                this[UnittoCalculatorWidget.inputPrefKey] = input
                this[UnittoCalculatorWidget.outputPrefKey] = output
            }
        }

        UnittoCalculatorWidget().update(context, glanceId)
    }
}

internal class CopyResultAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        val output = parameters[UnittoCalculatorWidget.outputKey]
        if (output.isNullOrEmpty()) return

        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText("Unitto", output))
    }
}

internal class RestartWidget : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        UnittoCalculatorWidget().update(context, glanceId)
    }
}

private fun calculate(
    input: String,
    precision: Int,
    outputFormat: Int
): String {
    if (input.isEmpty()) throw Exception()
    if (!input.isExpression()) throw Exception()

    return Expression(input)
        .calculate()
        .format(precision, outputFormat)
}
