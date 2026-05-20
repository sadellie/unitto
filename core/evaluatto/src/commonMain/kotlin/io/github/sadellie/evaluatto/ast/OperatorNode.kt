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

internal sealed interface OperatorNode : ASTNode {
  override val token: Token.Operator
}

internal data class PlusNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Plus

  constructor(vararg children: ASTNode) : this(children.asList())

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)

  /**
   * For example (very silly example), both trees represent same expression: "1 + 2 + 3"
   *
   *         +           +    |   +           +
   *        / \         /|\   |  / \         /|\
   *       +   3  ==>  1 2 3  | 1   +  ==>  1 2 3
   *      / \                 |    / \
   *     1   2                |   2   3
   */
  override fun collapse(scriptContext: ScriptContext): ASTNode {
    val collapsedChildren = children.map { it.collapse(scriptContext) }
    // inline child if only one
    if (collapsedChildren.size == 1) return collapsedChildren.first()

    val newChildren = mutableListOf<ASTNode>()
    collapsedChildren.forEach { collapsedChild ->
      if (collapsedChild is PlusNode) {
        // inline if child is plus node like parent
        // kill child and adopt grand children
        newChildren.addAll(collapsedChild.children)
      } else {
        newChildren.add(collapsedChild)
      }
    }

    return this.withNewChildren(newChildren)
  }
}

internal data class MultiplyNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Multiply

  constructor(vararg children: ASTNode) : this(children.asList())

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)

  override fun collapse(scriptContext: ScriptContext): ASTNode {
    val collapsedChildren = children.map { it.collapse(scriptContext) }
    // inline child if only one
    if (collapsedChildren.size == 1) return collapsedChildren.first()

    val newChildren = mutableListOf<ASTNode>()
    collapsedChildren.forEach { collapsedChild ->
      if (collapsedChild is MultiplyNode) {
        // inline if child is multiplication node like parent
        // kill child and adopt grand children
        newChildren.addAll(collapsedChild.children)
      } else {
        newChildren.add(collapsedChild)
      }
    }

    return this.withNewChildren(newChildren)
  }
}

internal data class DivideNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Divide

  constructor(left: ASTNode, right: ASTNode) : this(listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class MathModuloNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Modulo

  constructor(left: ASTNode, right: ASTNode) : this(listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class FactorialNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Factorial

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class SqrtNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Sqrt

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class PowerNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token.Power

  constructor(left: ASTNode, right: ASTNode) : this(listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

// programmer operators are all binary and their implementations can be grouped into one node
internal data class ProgrammerOperatorNode(
  override val token: Token.ProgrammerOperator,
  override val children: List<ASTNode>,
) : OperatorNode {
  constructor(
    token: Token.ProgrammerOperator,
    left: ASTNode,
    right: ASTNode,
  ) : this(token, listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}
