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
  rootNode: ASTNode,
  onVisitParent: (currentNode: ASTNode, parent: ASTNode?) -> ASTNode?,
): ASTNode? {
  // null if no pattern match report to caller
  val simplifiedTree = walkBottomToTop(context, rootNode, null, onVisitParent) ?: return null
  // found a pattern and simplified
  return simplifiedTree
}

/**
 * Start visiting nodes from the deepest bracket in expression. Always collapses before returning
 * result.
 *
 * @param node Node to visit
 * @param parentNode Parent of node
 */
internal fun walkBottomToTop(
  context: ScriptContext,
  node: ASTNode,
  parentNode: ASTNode?,
  onVisitParent: (node: ASTNode, parent: ASTNode?) -> ASTNode?,
): ASTNode? {
  val sortedChildrenWithIndex =
    node.children
      // index before sorting to keep indexes to modify list correctly
      .withIndex()
      // visit brackets first
      .sortedByDescending { it.value is BracketsNode }

  for ((index, child) in sortedChildrenWithIndex) {
    val simplified = walkBottomToTop(context, child, node, onVisitParent)
    if (simplified != null) {
      val updatedChildren = node.children.toMutableList()
      updatedChildren[index] = simplified
      return node.withNewChildren(updatedChildren).collapse(context)
    }
  }

  // visited all children and made no modifications, now visit node
  val visitedNode = onVisitParent(node, parentNode)?.collapse(context)
  if (visitedNode != null) return visitedNode

  return null
}
