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

package io.github.sadellie.evaluatto.ast

import com.sadellie.unitto.core.common.Token

internal sealed interface ASTNode {
  val token: Token
  val children: List<ASTNode>

  fun withNewChildren(children: List<ASTNode>): ASTNode

  fun collapse(scriptContext: ScriptContext): ASTNode {
    val collapsedChildren = children.map { it.collapse(scriptContext) }
    val updatedNode = this.withNewChildren(collapsedChildren)
    return updatedNode
  }

  fun toPrettyString(indent: String = "", isLast: Boolean = true): String {
    val bob = StringBuilder()
    val marker = if (isLast) "└ " else "├ "
    val nodeToken = if (this is BracketsNode) "()" else this.token
    bob.append(indent).append(marker).append(nodeToken).append("\n")
    val childrenIndent = indent + if (isLast) "  " else "│ "
    children.indices.forEach { i ->
      val isLastChild = i == children.size - 1
      bob.append(children[i].toPrettyString(childrenIndent, isLastChild))
    }
    return bob.toString()
  }
}
