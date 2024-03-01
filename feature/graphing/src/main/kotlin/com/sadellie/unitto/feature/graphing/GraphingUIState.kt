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
import androidx.compose.ui.graphics.Color
import com.sadellie.unitto.core.common.FormatterSymbols
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal data class GraphingUIState(
  val functions: List<GraphFunction>,
  val graphs: Set<GraphLine>,
  val tileZoom: Float,
  val middleZero: Boolean,
  val showAcButton: Boolean,
  val formatterSymbols: FormatterSymbols,
  val inverseMode: Boolean,
)

internal sealed class FunctionUIState {
  data object Loading : FunctionUIState()

  data class Creator(
    val middleZero: Boolean,
    val showAcButton: Boolean,
    val formatterSymbols: FormatterSymbols,
    val inverseMode: Boolean,
  ) : FunctionUIState()

  data class Editor(
    val function: GraphFunction,
    val middleZero: Boolean,
    val showAcButton: Boolean,
    val formatterSymbols: FormatterSymbols,
    val inverseMode: Boolean,
  ) : FunctionUIState()
}

@Serializable
internal data class GraphFunction(
  val id: Int,
  val expression: String,
  @Serializable(ColorSerializer::class) val color: Color,
)

internal data class GraphLine(val graphFunction: GraphFunction, val offsets: List<Offset>)

internal class ColorSerializer : KSerializer<Color> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Color", PrimitiveKind.LONG)

  override fun serialize(encoder: Encoder, value: Color) = encoder.encodeLong(value.value.toLong())

  override fun deserialize(decoder: Decoder): Color = Color(decoder.decodeLong().toULong())
}

// how many pixels each tile takes. Tile is distance between 0 and 1
internal const val DEFAULT_ZOOM = 100f
