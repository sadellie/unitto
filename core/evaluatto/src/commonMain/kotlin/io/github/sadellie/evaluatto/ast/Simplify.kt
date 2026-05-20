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

internal fun simplifyBottomToTop(
  context: ScriptContext,
  parentNode: ASTNode,
  onVisitParent: (currentNode: ASTNode) -> ASTNode?,
): ASTNode? {
  // null if no pattern match report to caller
  val simplifiedTree = walkBottomToTop(context, parentNode, onVisitParent) ?: return null
  // found a pattern and simplified
  return simplifiedTree
}

/**
 * Start visiting nodes from the deepest bracket in expression. Always collapses before returning
 * result.
 */
internal fun walkBottomToTop(
  context: ScriptContext,
  parentNode: ASTNode,
  onVisitParent: (node: ASTNode) -> ASTNode?,
): ASTNode? {
  val sortedChildrenWithIndex =
    parentNode.children
      // index before sorting to keep indexes to modify list correctly
      .withIndex()
      // visit brackets first
      .sortedByDescending { it.value is BracketsNode }

  for ((index, child) in sortedChildrenWithIndex) {
    val simplified = walkBottomToTop(context, child, onVisitParent)
    if (simplified != null) {
      val updatedChildren = parentNode.children.toMutableList()
      updatedChildren[index] = simplified
      return parentNode.withNewChildren(updatedChildren).collapse(context)
    }
  }

  // visited all children and made no modifications, now visit parent
  val visitedParentNode = onVisitParent(parentNode)?.collapse(context)
  if (visitedParentNode != null) return visitedParentNode

  return null
}
