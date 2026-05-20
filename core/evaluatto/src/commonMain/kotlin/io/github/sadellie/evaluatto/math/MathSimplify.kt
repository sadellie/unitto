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

import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KBigDecimalMath
import com.sadellie.unitto.core.common.KMathContext
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isLessThan
import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.Simplify
import io.github.sadellie.evaluatto.ast.ASTNode
import io.github.sadellie.evaluatto.ast.BracketsNode
import io.github.sadellie.evaluatto.ast.ConstantNode
import io.github.sadellie.evaluatto.ast.DivideNode
import io.github.sadellie.evaluatto.ast.FactorialNode
import io.github.sadellie.evaluatto.ast.MathFunctionNode
import io.github.sadellie.evaluatto.ast.MathModuloNode
import io.github.sadellie.evaluatto.ast.MathNumberNode
import io.github.sadellie.evaluatto.ast.MultiplyNode
import io.github.sadellie.evaluatto.ast.NumberNode
import io.github.sadellie.evaluatto.ast.PercentNode
import io.github.sadellie.evaluatto.ast.PlusNode
import io.github.sadellie.evaluatto.ast.PowerNode
import io.github.sadellie.evaluatto.ast.ProgrammerOperatorNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import io.github.sadellie.evaluatto.ast.SqrtNode
import io.github.sadellie.evaluatto.ast.UnaryMinusNode
import io.github.sadellie.evaluatto.ast.UnaryOperatorNode
import io.github.sadellie.evaluatto.ast.simplifyBottomToTop
import kotlin.collections.plus

internal class MathSimplify(override val input: ASTNode, private val context: ScriptContext.Math) :
  Simplify {
  override fun simplify(tree: ASTNode): ASTNode? =
    simplifyBottomToTop(context, tree) { currentNode, parentNode ->
      when (currentNode) {
        is ConstantNode -> simplifyConstant(currentNode)
        is PlusNode -> simplifyPlus(currentNode)
        is MultiplyNode -> simplifyMultiply(currentNode)
        is DivideNode -> simplifyDivide(currentNode)
        is MathModuloNode -> simplifyModulo(currentNode)
        is PowerNode -> simplifyPower(currentNode)
        is SqrtNode -> simplifySqrt(currentNode)
        is FactorialNode -> simplifyFactorial(currentNode)
        is MathFunctionNode -> simplifyFunction(currentNode)
        is UnaryMinusNode -> simplifyUnaryMinus(currentNode)
        is PercentNode -> simplifyPercent(currentNode, parentNode)
        is NumberNode,
        is BracketsNode,
        is UnaryOperatorNode,
        is ProgrammerOperatorNode -> return@simplifyBottomToTop null
      }
    }

  private fun simplifyConstant(node: ConstantNode): MathNumberNode {
    val result =
      when (node.token) {
        Token.Pi -> KBigDecimalMath.pi(context.mathContext)
        Token.E -> KBigDecimalMath.e(context.mathContext)
      }
    return MathNumberNode(result)
  }

  private fun simplifyUnaryMinus(node: UnaryMinusNode): MathNumberNode? {
    val child = node.children.firstOrNull()
    if (child is MathNumberNode) return child.negate()
    return null
  }

  private fun simplifyPlus(node: PlusNode): PlusNode? {
    // plus percentages apply special rule 1 + 2 + 3% + 4
    val simplifiedPlusPercent = simplifyPlusPercent(node)
    if (simplifiedPlusPercent != null) return simplifiedPlusPercent
    // minus percentages apply special rule 1 + 2 - 3% + 4
    val simplifiedMinusPercent = simplifyMinusPercent(node)
    if (simplifiedMinusPercent != null) return simplifiedMinusPercent

    // basic numbers sum
    val numberNodes = node.children.filterIsInstance<MathNumberNode>()
    if (numberNodes.size < 2) return null
    var sumOfNodes = KBigDecimal.ZERO
    numberNodes.forEach { node -> sumOfNodes += node.value }
    // kill all children and place their sum instead
    val updatedChildren =
      node.children.filter { child -> child !is MathNumberNode }.plus(MathNumberNode(sumOfNodes))
    val updatedNode = node.withNewChildren(updatedChildren)
    return updatedNode
  }

  private fun simplifyPercentInPlus(
    node: PlusNode,
    percentIndex: Int,
    percentNumber: KBigDecimal,
    applyPercent: (sumOfNodes: KBigDecimal, percentFraction: KBigDecimal) -> KBigDecimal,
  ): PlusNode {
    // 1 + 2 + 3% + 4
    // need to apply 3% to 1 + 2
    val numberNodesBeforePercent =
      node.children.take(percentIndex).filterIsInstance<MathNumberNode>()
    val sumOfNodes =
      numberNodesBeforePercent.fold(KBigDecimal.ZERO) { acc, numberNode ->
        acc.plus(numberNode.value)
      }

    // sumOfNodes + 3% -> 3 / 100 * sumOfNodes
    val percentFraction =
      percentNumber
        .divide(
          KBigDecimal("100"),
          context.scale,
          context.roundingMode,
        )
        .multiply(sumOfNodes)

    val updatedChildren =
      node.children.mapIndexedNotNull { index, child ->
        when {
          // replace percent node with new node
          index == percentIndex -> MathNumberNode(applyPercent(sumOfNodes, percentFraction))
          // remove all number nodes before percent
          index < percentIndex && child is MathNumberNode -> null
          // keep other nodes
          else -> child
        }
      }

    return node.withNewChildren(updatedChildren)
  }

  private fun simplifyPlusPercent(node: PlusNode): PlusNode? {
    // find percent node index, nodes before will be increased by percent
    val percentIndex = node.children.indexOfFirst { it is PercentNode }
    if (percentIndex == -1) {
      Logger.v { "Percent not found, skipped" }
      return null
    }

    val percentNode = node.children[percentIndex] as? PercentNode
    percentNode?.extractNumberValue(0)
    val percentNumber = percentNode?.extractNumberValue(0)
    if (percentNumber == null) {
      Logger.w { "Percent number not found" }
      return null
    }

    return simplifyPercentInPlus(node, percentIndex, percentNumber) { sumOfNodes, fraction ->
      sumOfNodes.plus(fraction)
    }
  }

  private fun simplifyMinusPercent(node: PlusNode): PlusNode? {
    // minus percent is in unary minus node
    val percentIndex =
      node.children.indexOfFirst {
        it is UnaryMinusNode && it.children.getOrNull(0) is PercentNode
      }
    if (percentIndex == -1) {
      Logger.v { "Percent not found, skipped" }
      return null
    }

    val minusNode = node.children[percentIndex] as? UnaryMinusNode
    val percentNode = minusNode?.children[0] as? PercentNode
    val percentNumber = percentNode?.extractNumberValue(0)
    if (percentNumber == null) {
      Logger.v { "Percent number not found" }
      return null
    }

    return simplifyPercentInPlus(node, percentIndex, percentNumber) { sumOfNodes, fraction ->
      sumOfNodes.minus(fraction)
    }
  }

  private fun simplifyPercent(node: PercentNode, parentNode: ASTNode?): MathNumberNode? {
    // let simplifyPlus handle it
    if (parentNode is PlusNode || parentNode is UnaryMinusNode) return null
    // replace with fraction
    val percentNumber = node.extractNumberValue(0)
    if (percentNumber == null) {
      Logger.v { "percentNumberNode is null" }
      return null
    }
    val fractionNode =
      MathNumberNode(percentNumber.divide(KBigDecimal("100"), context.scale, context.roundingMode))
    return fractionNode
  }

  private fun simplifyMultiply(node: MultiplyNode): MultiplyNode? {
    val numberNodes = node.children.filterIsInstance<MathNumberNode>()
    if (numberNodes.size < 2) return null
    var productOfNodes = KBigDecimal.ONE.setScale(context.scale, context.roundingMode)
    numberNodes.forEach { node -> productOfNodes *= node.value }
    // kill all children and place their product instead
    val updatedChildren =
      node.children
        .filter { child -> child !is MathNumberNode }
        .plus(MathNumberNode(productOfNodes))
    val updatedNode = node.withNewChildren(updatedChildren)
    return updatedNode
  }

  private fun simplifyDivide(node: DivideNode): MathNumberNode? {
    if (node.children.size != 2) return null
    val child1 = node.extractNumberValue(0) ?: return null
    val child2 = node.extractNumberValue(1) ?: return null
    if (child2.isEqualTo(KBigDecimal.ZERO)) throw ExpressionException.DivideByZero()
    val result = child1.divide(child2, context.scale, context.roundingMode)
    return MathNumberNode(result)
  }

  private fun simplifyModulo(node: MathModuloNode): MathNumberNode? {
    if (node.children.size != 2) return null
    val child1 = node.extractNumberValue(0) ?: return null
    val child2 = node.extractNumberValue(1) ?: return null
    val result = child1.remainder(child2)
    return MathNumberNode(result)
  }

  private fun simplifyPower(node: PowerNode): MathNumberNode? {
    if (node.children.size != 2) return null
    val child1 = node.extractNumberValue(0) ?: return null
    val child2 = node.extractNumberValue(1) ?: return null
    // mathematicians made up this controversy because reasons
    if (child1.isEqualTo(KBigDecimal.ZERO) && child2.isEqualTo(KBigDecimal.ZERO)) {
      throw ExpressionException.BadExpression()
    }
    val result = KBigDecimalMath.pow(child1, child2, context.mathContext)
    return MathNumberNode(result)
  }

  private fun simplifySqrt(node: SqrtNode): MathNumberNode? {
    if (node.children.size != 1) return null
    val child1 = node.extractNumberValue(0) ?: return null
    val result = KBigDecimalMath.sqrt(child1, context.mathContext)
    return MathNumberNode(result)
  }

  private fun simplifyFactorial(node: FactorialNode): MathNumberNode? {
    if (node.children.size != 1) return null
    val child1 = node.extractNumberValue(0) ?: return null
    if (child1.isLessThan(KBigDecimal.ZERO)) throw ExpressionException.FactorialCalculation()
    val result = child1.factorial()
    return MathNumberNode(result)
  }

  private fun simplifyFunction(node: MathFunctionNode): MathNumberNode? {
    if (node.children.size != 1) error("No parameter in MathFunctionNode")
    val parameter = node.children.first()
    if (parameter !is MathNumberNode) return null
    val parameterBigDecimal = parameter.value
    val result =
      when (node.token) {
        Token.ArCos ->
          parameterBigDecimal
            .arcos(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token.ArSin ->
          parameterBigDecimal
            .arsin(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token.ArTan ->
          parameterBigDecimal
            .artan(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token.Cos ->
          parameterBigDecimal
            .cos(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token.Sin ->
          parameterBigDecimal
            .sin(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token.Tan ->
          parameterBigDecimal
            .tan(context.radianMode, context.mathContext)
            .rescaleTrig(context.mathContext)
        Token.Exp -> parameterBigDecimal.exp(context.mathContext)
        Token.Ln -> parameterBigDecimal.ln(context.mathContext)
        Token.Log -> parameterBigDecimal.log(context.mathContext)
      }
    return MathNumberNode(result)
  }

  // rescale to avoid precision loss when evaluating special cases in trigonometry
  private fun KBigDecimal.rescaleTrig(mathContext: KMathContext): KBigDecimal =
    this.setScale(mathContext.precision, KRoundingMode.HALF_EVEN)

  private fun ASTNode.extractNumberValue(index: Int): KBigDecimal? =
    (this.children[index] as? MathNumberNode)?.value
}
