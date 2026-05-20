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

package io.github.sadellie.evaluatto.math

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KMathContext
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.MAX_SCALE
import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.ast.ASTNode
import io.github.sadellie.evaluatto.ast.DivideNode
import io.github.sadellie.evaluatto.ast.MathNumberNode
import io.github.sadellie.evaluatto.ast.MultiplyNode
import io.github.sadellie.evaluatto.ast.PlusNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import io.github.sadellie.evaluatto.ast.ScriptContext.Math.Companion.TRIG_SCALE
import io.github.sadellie.evaluatto.ast.UnaryMinusNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun calculateExpression(
  input: String,
  radianMode: Boolean = true,
  roundingMode: KRoundingMode = KRoundingMode.HALF_EVEN,
): KBigDecimal =
  calculateExpressionAndExtractRepeatableOperation(input, radianMode, roundingMode, false).first

suspend fun calculateExpressionAndExtractRepeatableOperation(
  input: String,
  radianMode: Boolean = true,
  roundingMode: KRoundingMode = KRoundingMode.HALF_EVEN,
  extractRepeatable: Boolean = false,
): Pair<KBigDecimal, Operation?> =
  withContext(Dispatchers.Default) {
    if (input.isEmpty()) return@withContext KBigDecimal.ZERO to null
    val scale = MAX_SCALE
    val mathContext = KMathContext(TRIG_SCALE, roundingMode)
    val tokens = input.tokenizeMath()
    val context =
      ScriptContext.Math(
        scale = scale,
        roundingMode = roundingMode,
        mathContext = mathContext,
        radianMode = radianMode,
      )
    val tree =
      ASTMathBuilder(tokens).buildTreeAndCollapse(context) ?: error("Failed to build a tree")
    val operation = if (extractRepeatable) extractRepeatableOperation(tree) else null
    val simplified = MathSimplify(tree, context).simplifyRecursively()
    if (simplified !is MathNumberNode) error("Result is not a number")
    return@withContext simplified.value to operation
  }

/** Extract [Operation] from [tree] BEFORE collapsing it. */
internal fun extractRepeatableOperation(tree: ASTNode): Operation? {
  if (tree.children.size != 2) return null
  extractNumber(tree.children[0]) ?: return null
  val child2: String = extractNumber(tree.children[1]) ?: return null
  return when (tree) {
    is MultiplyNode -> Operation.Multiply(child2)
    is DivideNode -> Operation.Divide(child2)
    is PlusNode ->
      if (child2.startsWith(Token.UnaryMinus.symbol))
        Operation.Minus(child2.removePrefix(Token.UnaryMinus.symbol))
      else Operation.Plus(child2)
    else -> null
  }
}

private fun extractNumber(node: ASTNode): String? {
  return when (node) {
    is MathNumberNode -> node.token.symbol
    is UnaryMinusNode -> {
      val unaryChild = node.children.firstOrNull() ?: return null
      if (unaryChild !is MathNumberNode) return null
      node.token.symbol + unaryChild.token.symbol
    }
    else -> null
  }
}

sealed interface Operation {
  val value2: String
  val operationToken: Token.Operator

  fun generateExpression(input: String): String = input + operationToken.symbol + value2

  data class Plus(override val value2: String) : Operation {
    override val operationToken = Token.Plus
  }

  data class Minus(override val value2: String) : Operation {
    override val operationToken = Token.Minus
  }

  data class Multiply(override val value2: String) : Operation {
    override val operationToken = Token.Multiply
  }

  data class Divide(override val value2: String) : Operation {
    override val operationToken = Token.Divide
  }
}
