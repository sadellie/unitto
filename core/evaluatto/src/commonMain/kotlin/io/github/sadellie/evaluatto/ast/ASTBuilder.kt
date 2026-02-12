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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KBigDecimalMath
import com.sadellie.unitto.core.common.KMathContext
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.MAX_SCALE
import com.sadellie.unitto.core.common.Token2
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isLessThan
import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.arcos
import io.github.sadellie.evaluatto.arsin
import io.github.sadellie.evaluatto.artan
import io.github.sadellie.evaluatto.cos
import io.github.sadellie.evaluatto.exp
import io.github.sadellie.evaluatto.factorial
import io.github.sadellie.evaluatto.ln
import io.github.sadellie.evaluatto.log
import io.github.sadellie.evaluatto.sin
import io.github.sadellie.evaluatto.tan
import io.github.sadellie.evaluatto.tokenize
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
    val tokens = input.tokenize()
    val context =
      ScriptContext(
        scale = scale,
        roundingMode = roundingMode,
        mathContext = mathContext,
        radianMode = radianMode,
      )
    val tree = ASTBuilder(tokens).buildTreeAndCollapse(context) ?: error("Failed to build a tree")
    val operation = if (extractRepeatable) extractRepeatableOperation(tree) else null
    val simplified = simplifyRecursively(context, tree)
    if (simplified !is NumberNode) error("Result is not a number")
    return@withContext simplified.value to operation
  }

/** Extract [Operation] from [tree] BEFORE collapsing it. */
internal fun extractRepeatableOperation(tree: ASTNode): Operation? {
  if (tree.children.size != 2) return null
  if (tree.children[0] !is NumberNode) return null
  val child2 = tree.children[1] as? NumberNode ?: return null
  return when (tree) {
    is MultiplyNode -> Operation.Multiply(child2.token)
    is DivideNode -> Operation.Divide(child2.token)
    is PlusNode ->
      if (child2.value.isLessThan(KBigDecimal.ZERO))
        Operation.Minus(
          child2.token.copy(symbol = child2.token.symbol.removePrefix(Token2.UnaryMinus.symbol))
        )
      else Operation.Plus(child2.token)
    else -> null
  }
}

sealed interface Operation {
  val value2: Token2.Number
  val operationToken: Token2.Operator

  fun generateExpression(input: String): String = input + operationToken.symbol + value2.symbol

  data class Plus(override val value2: Token2.Number) : Operation {
    override val operationToken = Token2.Plus
  }

  data class Minus(override val value2: Token2.Number) : Operation {
    override val operationToken = Token2.Minus
  }

  data class Multiply(override val value2: Token2.Number) : Operation {
    override val operationToken = Token2.Multiply
  }

  data class Divide(override val value2: Token2.Number) : Operation {
    override val operationToken = Token2.Divide
  }
}

internal class ASTBuilder(private val tokens: List<Token2>) {
  private val operatorStack by lazy { mutableListOf<Token2>() }
  private val outputTree by lazy { mutableListOf<ASTNode>() }

  fun buildTreeAndCollapse(context: ScriptContext) = build(context).firstOrNull()?.collapse(context)

  fun build(context: ScriptContext): List<ASTNode> {
    if (tokens.isEmpty()) return emptyList()
    for (token in tokens) {
      when (token) {
        is Token2.Number -> outputTree.add(NumberNode(token, context))
        is Token2.Const -> outputTree.add(ConstantNode(token))
        is Token2.Func -> operatorStack.add(token)
        is Token2.Operator -> handleOperator(token)
        Token2.LeftBracket -> operatorStack.add(token)
        Token2.RightBracket -> handleRightParen()
        // explicit only
        Token2.Comma,
        Token2.Digit0,
        Token2.Digit1,
        Token2.Digit2,
        Token2.Digit3,
        Token2.Digit4,
        Token2.Digit5,
        Token2.Digit6,
        Token2.Digit7,
        Token2.Digit8,
        Token2.Digit9,
        Token2.Dot,
        Token2.Fraction,
        Token2.Period,
        Token2.Space,
        Token2.LetterA,
        Token2.LetterB,
        Token2.LetterC,
        Token2.LetterD,
        Token2.LetterE,
        Token2.LetterF,
        Token2.EngineeringE -> error("Unexpected: $token")
      }
    }

    while (operatorStack.isNotEmpty()) {
      require(operatorStack.lastOrNull() !is Token2.LeftBracket) { "Missing right bracket" }
      handlePopAndAddToOutput()
    }

    return outputTree
  }

  private fun popLastFromOperatorStack(): Token2 = operatorStack.removeAt(operatorStack.lastIndex)

  private fun popLastFromOutputTree(): ASTNode =
    try {
      outputTree.removeAt(outputTree.lastIndex)
    } catch (_: IndexOutOfBoundsException) {
      throw ExpressionException.BadExpression()
    }

  private fun handleRightParen() {
    // end of expression in bracket, build tree from whatever is in stack
    var topOfStack = operatorStack.lastOrNull()
    while (topOfStack != Token2.LeftBracket) {
      // null only when left parent, but should not be possible in this loop
      handlePopAndAddToOutput()
      topOfStack = operatorStack.lastOrNull()
    }
    // used all nodes in stack in reached start of the expression in brackets (walked backwards)
    require(operatorStack.last() == Token2.LeftBracket)
    handlePopAndAddToOutput()
    // pushed everything in brackets, now inline bracket's children in function if needed
    topOfStack = operatorStack.lastOrNull()
    if (topOfStack is Token2.Func) {
      require(outputTree.last() is BracketsNode) { "Function is missing brackets" }
      handlePopAndAddToOutput()
    }
  }

  private fun handleOperator(operator1: Token2.Operator) {
    var operator2 = operatorStack.lastOrNull()
    while (
      operator2 is Token2.Operator &&
        (operator2.precedence > operator1.precedence ||
          operator2.precedence == operator1.precedence &&
            operator1.associativity == Token2.Operator.Associativity.LEFT)
    ) {
      // pop operators from stack to output
      handlePopAndAddToOutput()
      operator2 = operatorStack.lastOrNull()
    }
    // other operators were popped, now add this operator on top of stack
    operatorStack.add(operator1)
  }

  private fun handlePopAndAddToOutput() {
    val popped =
      when (val parentOperator = popLastFromOperatorStack()) {
        is Token2.Func -> {
          // parameter is always a child in brackets
          val param = popLastFromOutputTree().children.first()
          FunctionNode(parentOperator, param)
        }
        is Token2.UnaryMinus -> {
          val child = popLastFromOutputTree()
          UnaryOperatorNode(parentOperator, child)
        }
        is Token2.Minus -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          // 1 - 2 = 1 + -2
          PlusNode(left, UnaryOperatorNode(Token2.UnaryMinus, right))
        }
        is Token2.Plus -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          PlusNode(listOf(left, right))
        }
        is Token2.Multiply -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          MultiplyNode(left, right)
        }
        is Token2.Divide -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          DivideNode(left, right)
        }
        is Token2.LeftBracket -> {
          val child = popLastFromOutputTree()
          BracketsNode(child)
        }
        Token2.Factorial -> {
          val child = popLastFromOutputTree()
          FactorialNode(child)
        }
        Token2.Modulo -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          ModuloNode(left, right)
        }
        Token2.Power -> {
          val right = popLastFromOutputTree()
          val left = popLastFromOutputTree()
          PowerNode(left, right)
        }
        Token2.Sqrt -> {
          val child = popLastFromOutputTree()
          SqrtNode(child)
        }
        Token2.Percent,
        Token2.RightBracket,
        is Token2.Const,
        is Token2.Number,
        Token2.Digit0,
        Token2.Digit1,
        Token2.Digit2,
        Token2.Digit3,
        Token2.Digit4,
        Token2.Digit5,
        Token2.Digit6,
        Token2.Digit7,
        Token2.Digit8,
        Token2.Digit9,
        Token2.EngineeringE,
        Token2.Fraction,
        Token2.Dot,
        Token2.Period,
        Token2.Space,
        Token2.LetterA,
        Token2.LetterB,
        Token2.LetterC,
        Token2.LetterD,
        Token2.LetterE,
        Token2.LetterF,
        Token2.Comma -> error("Not allowed to pop: $parentOperator")
      }

    outputTree.add(popped)
  }
}

internal sealed interface ASTNode {
  val token: Token2
  val children: List<ASTNode>

  fun withNewChildren(children: List<ASTNode>): ASTNode

  fun collapse(scriptContext: ScriptContext): ASTNode {
    val collapsedChildren = children.map { it.collapse(scriptContext) }
    val updatedNode = this.withNewChildren(collapsedChildren)
    return updatedNode
  }
}

/** Nodes without children, can not be simplified. */
internal sealed interface AtomicNode : ASTNode {
  override val children: List<ASTNode>
    get() = emptyList()

  // no children
  override fun withNewChildren(children: List<ASTNode>) = this
}

internal data class NumberNode(
  val value: KBigDecimal,
  override val token: Token2.Number = Token2.Number(value.toPlainString()),
) : AtomicNode {
  constructor(
    token: Token2.Number,
    context: ScriptContext,
  ) : this(
    (if (token.symbol == ".") KBigDecimal.ZERO else KBigDecimal(token.symbol)).setScale(
      context.scale,
      context.roundingMode,
    ),
    token,
  )

  fun negate(): NumberNode {
    val newValue = value.negate()
    val isNegative = value.isLessThan(KBigDecimal.ZERO)
    val newTokenSymbol =
      if (isNegative) {
        token.symbol.removePrefix(Token2.UnaryMinus.symbol)
      } else {
        Token2.UnaryMinus.symbol + token.symbol
      }
    return NumberNode(value = newValue, token = token.copy(symbol = newTokenSymbol))
  }
}

internal data class ConstantNode(override val token: Token2.Const) : AtomicNode

internal data class FunctionNode(
  override val token: Token2.Func,
  override val children: List<ASTNode>,
) : ASTNode {
  constructor(token: Token2.Func, vararg children: ASTNode) : this(token, children.asList())

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class UnaryOperatorNode(
  override val token: Token2.Operator,
  override val children: List<ASTNode>,
) : ASTNode {
  constructor(token: Token2.Operator, child: ASTNode) : this(token, listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)

  override fun collapse(scriptContext: ScriptContext): ASTNode {
    val child = children.firstOrNull()?.collapse(scriptContext)
    if (child is NumberNode) {
      if (token !is Token2.UnaryMinus) throw ScriptException.WrongUnary(child, token)
      // negate numbers and omit this unary minus
      return child.negate()
    }

    return super.collapse(scriptContext)
  }
}

internal data class BracketsNode(override val children: List<ASTNode>) : ASTNode {
  override val token: Token2
    get() = error("Do not use token from this node, use toFormattedString instead")

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)

  override fun collapse(scriptContext: ScriptContext): ASTNode {
    val child = children.firstOrNull()?.collapse(scriptContext)
    // extract atomic children to remove unnecessary bracket
    if (child is AtomicNode) return child
    return super.collapse(scriptContext)
  }
}

internal sealed interface OperatorNode : ASTNode {
  override val token: Token2.Operator
}

internal data class PlusNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token2.Plus

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
  override val token = Token2.Multiply

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
  override val token = Token2.Divide

  constructor(left: ASTNode, right: ASTNode) : this(listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class FactorialNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token2.Factorial

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class ModuloNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token2.Modulo

  constructor(left: ASTNode, right: ASTNode) : this(listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class PowerNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token2.Power

  constructor(left: ASTNode, right: ASTNode) : this(listOf(left, right))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class SqrtNode(override val children: List<ASTNode>) : OperatorNode {
  override val token = Token2.Sqrt

  constructor(child: ASTNode) : this(listOf(child))

  override fun withNewChildren(children: List<ASTNode>) = this.copy(children = children)
}

internal data class ScriptContext(
  val scale: Int = MAX_SCALE,
  val roundingMode: KRoundingMode = KRoundingMode.HALF_EVEN,
  val mathContext: KMathContext = KMathContext(TRIG_SCALE, roundingMode),
  val radianMode: Boolean = true,
)

internal sealed class ScriptException : Exception() {
  internal class WrongUnary(value: ASTNode?, operator: Token2.Operator) : ScriptException() {
    override val message = "Can not apply unary ($operator) collapse to $value"
  }
}

private fun simplifyRecursively(context: ScriptContext, input: ASTNode): ASTNode {
  var resultNode: ASTNode = input

  // restart loop if simplification was not performed to avoid missed matches
  // this ensures better control over order of simplifications
  while (true) {
    // null when failed to match a pattern
    resultNode = SimplificationRule(context).simplify(resultNode) ?: break
  }

  return resultNode
}

private class SimplificationRule(val context: ScriptContext) {
  fun simplify(tree: ASTNode): ASTNode? =
    simplifyBottomToTop(context, tree) { currentNode ->
      when (currentNode) {
        is ConstantNode -> simplifyConstant(currentNode)
        is PlusNode -> simplifyPlus(currentNode)
        is MultiplyNode -> simplifyMultiply(currentNode)
        is DivideNode -> simplifyDivide(currentNode)
        is ModuloNode -> simplifyModulo(currentNode)
        is PowerNode -> simplifyPower(currentNode)
        is SqrtNode -> simplifySqrt(currentNode)
        is FactorialNode -> simplifyFactorial(currentNode)
        is FunctionNode -> simplifyFunction(currentNode)
        is NumberNode,
        is BracketsNode,
        is UnaryOperatorNode -> return@simplifyBottomToTop null
      }
    }

  private fun simplifyConstant(node: ConstantNode): NumberNode {
    val result =
      when (node.token) {
        Token2.Pi -> KBigDecimalMath.pi(context.mathContext)
        Token2.E -> KBigDecimalMath.e(context.mathContext)
      }
    return NumberNode(result)
  }

  private fun simplifyPlus(node: PlusNode): PlusNode? {
    val numberNodes = node.children.filterIsInstance<NumberNode>()
    if (numberNodes.size < 2) return null
    var sumOfNodes = KBigDecimal.ZERO
    numberNodes.forEach { node -> sumOfNodes += node.value }
    // kill all children and place their sum instead
    val updatedChildren =
      node.children.filter { child -> child !is NumberNode }.plus(NumberNode(sumOfNodes))
    val updatedNode = node.withNewChildren(updatedChildren)
    return updatedNode
  }

  private fun simplifyMultiply(node: MultiplyNode): MultiplyNode? {
    val numberNodes = node.children.filterIsInstance<NumberNode>()
    if (numberNodes.size < 2) return null
    var productOfNodes = KBigDecimal.ONE.setScale(context.scale, context.roundingMode)
    numberNodes.forEach { node -> productOfNodes *= node.value }
    // kill all children and place their product instead
    val updatedChildren =
      node.children.filter { child -> child !is NumberNode }.plus(NumberNode(productOfNodes))
    val updatedNode = node.withNewChildren(updatedChildren)
    return updatedNode
  }

  private fun simplifyDivide(node: DivideNode): NumberNode? {
    if (node.children.size != 2) return null
    val child1 = (node.children[0] as? NumberNode)?.value ?: return null
    val child2 = (node.children[1] as? NumberNode)?.value ?: return null
    if (child2.isEqualTo(KBigDecimal.ZERO)) throw ExpressionException.DivideByZero()
    val result = child1.divide(child2, context.scale, context.roundingMode)
    return NumberNode(result)
  }

  private fun simplifyModulo(node: ModuloNode): NumberNode? {
    if (node.children.size != 2) return null
    val child1 = (node.children[0] as? NumberNode)?.value ?: return null
    val child2 = (node.children[1] as? NumberNode)?.value ?: return null
    val result = child1.remainder(child2)
    return NumberNode(result)
  }

  private fun simplifyPower(node: PowerNode): NumberNode? {
    if (node.children.size != 2) return null
    val child1 = (node.children[0] as? NumberNode)?.value ?: return null
    val child2 = (node.children[1] as? NumberNode)?.value ?: return null
    // mathematicians made up this controversy because reasons
    if (child1.isEqualTo(KBigDecimal.ZERO) && child2.isEqualTo(KBigDecimal.ZERO)) {
      throw ExpressionException.BadExpression()
    }
    val result = KBigDecimalMath.pow(child1, child2, context.mathContext)
    return NumberNode(result)
  }

  private fun simplifySqrt(node: SqrtNode): NumberNode? {
    if (node.children.size != 1) return null
    val child1 = (node.children[0] as? NumberNode)?.value ?: return null
    val result = KBigDecimalMath.sqrt(child1, context.mathContext)
    return NumberNode(result)
  }

  private fun simplifyFactorial(node: FactorialNode): NumberNode? {
    if (node.children.size != 1) return null
    val child1 = (node.children[0] as? NumberNode)?.value ?: return null
    if (child1.isLessThan(KBigDecimal.ZERO)) throw ExpressionException.FactorialCalculation()
    val result = child1.factorial()
    return NumberNode(result)
  }

  private fun simplifyFunction(node: FunctionNode): NumberNode? {
    if (node.children.size != 1) error("No parameter in FunctionNode")
    val parameter = node.children.first()
    if (parameter !is NumberNode) return null
    val parameterBigDecimal = parameter.value
    val result =
      when (node.token) {
        Token2.ArCos ->
          parameterBigDecimal
            .arcos(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token2.ArSin ->
          parameterBigDecimal
            .arsin(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token2.ArTan ->
          parameterBigDecimal
            .artan(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token2.Cos ->
          parameterBigDecimal
            .cos(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token2.Sin ->
          parameterBigDecimal
            .sin(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token2.Tan ->
          parameterBigDecimal
            .tan(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token2.Exp -> parameterBigDecimal.exp(context.mathContext)
        Token2.Ln -> parameterBigDecimal.ln(context.mathContext)
        Token2.Log -> parameterBigDecimal.log(context.mathContext)
        is Token2.Func.WithBracket -> error("Unexpected token WithBracket: $node")
      }
    return NumberNode(result)
  }

  // rescale to avoid precision loss when evaluating special cases in trigonometry
  private fun KBigDecimal.rescaleTrig(mathContext: KMathContext): KBigDecimal =
    this.setScale(mathContext.precision, KRoundingMode.HALF_EVEN)
}

private fun simplifyBottomToTop(
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
private fun walkBottomToTop(
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

private const val TRIG_SCALE = 100
