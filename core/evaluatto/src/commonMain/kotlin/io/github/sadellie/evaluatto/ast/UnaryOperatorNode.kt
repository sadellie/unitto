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

internal sealed interface UnaryOperatorNode : ASTNode {
  override val token: Token.Operator
}

internal data class UnaryMinusNode(override val children: List<ASTNode>) : UnaryOperatorNode {
  override val token: Token.Operator = Token.UnaryMinus

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class UnaryNotNode(override val children: List<ASTNode>) : UnaryOperatorNode {
  override val token: Token.ProgrammerOperator = Token.Not

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}
