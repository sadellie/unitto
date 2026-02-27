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

internal data class MathFunctionNode(
  override val token: Token.MathFunc,
  override val children: List<ASTNode>,
) : ASTNode {
  constructor(token: Token.MathFunc, vararg children: ASTNode) : this(token, children.asList())

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}
