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

package io.github.sadellie.evaluatto.programmer

import io.github.sadellie.evaluatto.ast.ProgrammerNumberNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun programmerCalculateExpression(input: String, base: Int, dataUnit: DataUnit): String =
  withContext(Dispatchers.Default) {
    val tokens = input.tokenizeProgrammer()
    val context = ScriptContext.Programmer(base = base, dataUnit = dataUnit)
    val tree =
      ProgrammerASTBuilder(tokens).buildTreeAndCollapse(context) ?: error("Failed to build a tree")
    val simplified = ProgrammerSimplify(tree, context).simplifyRecursively()
    if (simplified !is ProgrammerNumberNode) error("Result is not a number")
    return@withContext simplified.token.symbol
  }
