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

package com.sadellie.unitto.feature.graphing.components

import androidx.compose.ui.geometry.Offset

/**
 * Helper function to properly zoom in/out and adjust offset.
 *
 * [newZoom] is limited between [MIN_ZOOM] and [MAX_ZOOM].
 */
internal fun naturalZoom(currentZoom: Float, newZoom: Float, offset: Offset): Pair<Offset, Float> {
  val limitedNewZoom = newZoom.coerceIn(MIN_ZOOM, MAX_ZOOM)
  // how much tile size changed
  val tileScaleChange = limitedNewZoom - currentZoom
  // how much canvas was shifted in tiles
  val offsetInTiles = offset / currentZoom
  // how much offset to add to keep center in place (taking tile sizes into account)
  val adjustForScale = offsetInTiles * tileScaleChange
  val newOffset = offset + adjustForScale

  return newOffset to limitedNewZoom
}

internal const val MIN_ZOOM = 10f
internal const val MAX_ZOOM = 300f
