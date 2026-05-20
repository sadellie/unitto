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
import io.github.sadellie.evaluatto.ExpressionException

internal interface ASTBuilder<T : Token, C : ScriptContext> {
  val tokens: List<T>
  val operatorStack: MutableList<T>
  val outputTree: MutableList<ASTNode>

  fun buildTreeAndCollapse(context: C): ASTNode? = build(context).firstOrNull()?.collapse(context)

  fun build(context: C): List<ASTNode>

  fun handleOperator(operator1: Token.Operator) {
    var operator2 = operatorStack.lastOrNull()
    while (
      operator2 is Token.Operator &&
        (operator2.precedence > operator1.precedence ||
          operator2.precedence == operator1.precedence &&
            operator1.associativity == Token.Operator.Associativity.LEFT)
    ) {
      // pop operators from stack to output
      handlePopAndAddToOutput()
      operator2 = operatorStack.lastOrNull()
    }
    // other operators were popped, now add this operator on top of stack
    @Suppress("UNCHECKED_CAST") operatorStack.add(operator1 as T)
  }

  fun handleRightBracket() {
    // end of expression in bracket, build tree from whatever is in stack
    var topOfStack = operatorStack.lastOrNull()
    while (topOfStack != Token.LeftBracket) {
      // null only when left parent, but should not be possible in this loop
      handlePopAndAddToOutput()
      topOfStack = operatorStack.lastOrNull()
    }
    // used all nodes in stack in reached start of the expression in brackets (walked backwards)
    require(operatorStack.last() == Token.LeftBracket)
    handlePopAndAddToOutput()
    // pushed everything in brackets, now inline bracket's children in function if needed
    topOfStack = operatorStack.lastOrNull()
    if (topOfStack is Token.Func) {
      require(outputTree.last() is BracketsNode) { "Function is missing brackets" }
      handlePopAndAddToOutput()
    }
  }

  fun handlePopAndAddToOutput()

  fun popLastFromOperatorStack(): T = operatorStack.removeAt(operatorStack.lastIndex)

  fun popLastFromOutputTree(): ASTNode =
    try {
      outputTree.removeAt(outputTree.lastIndex)
    } catch (_: IndexOutOfBoundsException) {
      throw ExpressionException.BadExpression()
    }
}
