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

import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.ast.ASTBuilder
import io.github.sadellie.evaluatto.ast.ASTNode
import io.github.sadellie.evaluatto.ast.BracketsNode
import io.github.sadellie.evaluatto.ast.DivideNode
import io.github.sadellie.evaluatto.ast.MultiplyNode
import io.github.sadellie.evaluatto.ast.PlusNode
import io.github.sadellie.evaluatto.ast.ProgrammerNumberNode
import io.github.sadellie.evaluatto.ast.ProgrammerOperatorNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import io.github.sadellie.evaluatto.ast.UnaryMinusNode
import io.github.sadellie.evaluatto.ast.UnaryNotNode

internal class ProgrammerASTBuilder(override val tokens: List<Token.Programmer>) :
  ASTBuilder<Token.Programmer, ScriptContext.Programmer> {
  override val operatorStack = mutableListOf<Token.Programmer>()
  override val outputTree = mutableListOf<ASTNode>()

  override fun build(context: ScriptContext.Programmer): List<ASTNode> {
    if (tokens.isEmpty()) return emptyList()
    for (token in tokens) {
      when (token) {
        is Token.Number -> outputTree.add(ProgrammerNumberNode(token, context))
        is Token.Operator -> handleOperator(token)
        Token.LeftBracket -> operatorStack.add(token)
        Token.RightBracket -> handleRightBracket()
      }
    }

    while (operatorStack.isNotEmpty()) {
      require(operatorStack.lastOrNull() !is Token.LeftBracket) { "Missing right bracket" }
      handlePopAndAddToOutput()
    }

    return outputTree
  }

  override fun handlePopAndAddToOutput() {
    val popped =
      when (val parentOperator = popLastFromOperatorStack()) {
        Token.Plus -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          PlusNode(listOf(left, right))
        }
        Token.Minus -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          // 1 - 2 = 1 + -2
          PlusNode(left, UnaryMinusNode(right))
        }
        Token.Multiply -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          MultiplyNode(left, right)
        }
        Token.Divide -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          DivideNode(left, right)
        }
        Token.UnaryMinus -> {
          val child = popLastFromOutputTree()
          UnaryMinusNode(child)
        }
        Token.Not -> {
          val child = popLastFromOutputTree()
          UnaryNotNode(child)
        }
        is Token.ProgrammerOperator -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          ProgrammerOperatorNode(parentOperator, left, right)
        }
        Token.LeftBracket -> {
          val child = popLastFromOutputTree()
          BracketsNode(child)
        }
        Token.RightBracket,
        is Token.Number -> error("Not allowed to pop: $parentOperator")
      }

    outputTree.add(popped)
  }
}
