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

package io.github.sadellie.evaluatto

import io.github.sadellie.evaluatto.ast.ASTNode

internal interface Simplify {
  val input: ASTNode

  fun simplifyRecursively(): ASTNode {
    var resultNode: ASTNode = input

    // restart loop if simplification was not performed to avoid missed matches
    // this ensures better control over order of simplifications
    while (true) {
      // null when failed to match a pattern
      resultNode = simplify(resultNode) ?: break
    }

    return resultNode
  }

  fun simplify(tree: ASTNode): ASTNode?
}
