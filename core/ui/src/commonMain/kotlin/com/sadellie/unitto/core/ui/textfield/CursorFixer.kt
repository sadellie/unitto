/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.TextRange
import kotlin.math.abs

fun CharSequence.fixCursor(pos: Int, longTokens: List<String>): Int {
  if (isEmpty()) return pos

  // Best position if we move cursor left
  var leftCursor = pos
  while (this.isPlacedIllegallyAt(leftCursor, longTokens)) leftCursor--

  // Best position if we move cursor right
  var rightCursor = pos
  while (this.isPlacedIllegallyAt(rightCursor, longTokens)) rightCursor++

  return listOf(leftCursor, rightCursor).minBy { abs(it - pos) }
}

fun TextFieldBuffer.fixTextRange(longTokens: List<String>): TextRange {
  val text = this.asCharSequence()
  return TextRange(
    text.fixCursor(selection.min, longTokens),
    text.fixCursor(selection.max, longTokens),
  )
}

/**
 * Same as [String.tokenAhead], but more efficient. We only need a number, not string. Don't
 * replace!
 *
 * @param longTokens Tokens longer than 1 symbol
 */
fun String.tokenLengthAhead(pos: Int, longTokens: List<String>): Int {
  longTokens.forEach { if (pos.isAfterToken(this, it)) return it.length }

  // We default to 1 here. It means that cursor is not placed after illegal token. Just a number
  // or a binary operator or something else, can delete by one symbol.
  return 1
}

fun String.tokenAhead(pos: Int, longTokens: List<String>): String {
  longTokens.forEach { if (pos.isAfterToken(this, it)) return it }

  return substring((pos - 1).coerceAtLeast(0), pos)
}

/**
 * - `123,456+c|os(8)` return `true`
 * - `123,456+|cos(8)` return `false`
 * - `123,|456+cos(8)` return `false` (impossible in UI. See [ExpressionOutputTransformation])
 */
private fun CharSequence.isPlacedIllegallyAt(pos: Int, longTokens: List<String>): Boolean {
  longTokens.forEach { if (pos.isAtToken(this, it)) return true }

  return false
}

private fun Int.isAtToken(str: CharSequence, token: String): Boolean {
  val checkBound = (token.length - 1).coerceAtLeast(1)
  return str
    .substring(
      startIndex = (this - checkBound).coerceAtLeast(0),
      endIndex = (this + checkBound).coerceAtMost(str.length),
    )
    .contains(token)
}

private fun Int.isAfterToken(str: String, token: String): Boolean {
  return str.substring((this - token.length).coerceAtLeast(0), this).contains(token)
}

// This can also make [TextFieldState.addTokens] better by checking tokens both ways. Needs more
// tests
fun String.tokenAfter(pos: Int, longTokens: List<String>): String {
  longTokens.forEach { if (pos.isBeforeToken(this, it)) return it }

  return substring(pos, (pos + 1).coerceAtMost(this.length))
}

private fun Int.isBeforeToken(str: String, token: String): Boolean {
  return str.substring(this, (this + token.length).coerceAtMost(str.length)).contains(token)
}
