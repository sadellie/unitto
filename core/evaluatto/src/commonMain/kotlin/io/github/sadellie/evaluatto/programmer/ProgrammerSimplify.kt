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

import com.sadellie.unitto.core.common.KBigInteger
import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.Simplify
import io.github.sadellie.evaluatto.ast.ASTNode
import io.github.sadellie.evaluatto.ast.BracketsNode
import io.github.sadellie.evaluatto.ast.ConstantNode
import io.github.sadellie.evaluatto.ast.DivideNode
import io.github.sadellie.evaluatto.ast.FactorialNode
import io.github.sadellie.evaluatto.ast.MathFunctionNode
import io.github.sadellie.evaluatto.ast.MathModuloNode
import io.github.sadellie.evaluatto.ast.MultiplyNode
import io.github.sadellie.evaluatto.ast.NumberNode
import io.github.sadellie.evaluatto.ast.PlusNode
import io.github.sadellie.evaluatto.ast.PowerNode
import io.github.sadellie.evaluatto.ast.ProgrammerNumberNode
import io.github.sadellie.evaluatto.ast.ProgrammerOperatorNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import io.github.sadellie.evaluatto.ast.SqrtNode
import io.github.sadellie.evaluatto.ast.UnaryMinusNode
import io.github.sadellie.evaluatto.ast.UnaryNotNode
import io.github.sadellie.evaluatto.ast.simplifyBottomToTop
import kotlin.collections.forEach

internal class ProgrammerSimplify(
  override val input: ASTNode,
  private val context: ScriptContext.Programmer,
) : Simplify {
  override fun simplify(tree: ASTNode): ASTNode? =
    simplifyBottomToTop(context, tree) { currentNode ->
      when (currentNode) {
        is PlusNode -> simplifyPlus(currentNode)
        is MultiplyNode -> simplifyMultiply(currentNode)
        is DivideNode -> simplifyDivide(currentNode)
        is UnaryMinusNode -> simplifyUnaryMinus(currentNode)
        is UnaryNotNode -> simplifyUnaryNot(currentNode)
        is ProgrammerOperatorNode -> simplifyProgrammerOperator(currentNode)
        is ConstantNode,
        is NumberNode,
        is BracketsNode,
        is MathFunctionNode,
        is FactorialNode,
        is MathModuloNode,
        is PowerNode,
        is SqrtNode -> return@simplifyBottomToTop null
      }
    }

  private fun simplifyUnaryMinus(node: UnaryMinusNode): ProgrammerNumberNode? {
    val child = node.children.firstOrNull()
    if (child is ProgrammerNumberNode) return child.negate(context)
    return null
  }

  private fun simplifyUnaryNot(node: UnaryNotNode): ProgrammerNumberNode? {
    val child = node.children.firstOrNull()
    if (child is ProgrammerNumberNode) return ProgrammerNumberNode(child.value.not(), context)
    return null
  }

  private fun simplifyPlus(node: PlusNode): PlusNode? {
    val numberNodes = node.children.filterIsInstance<ProgrammerNumberNode>()
    if (numberNodes.size < 2) return null
    var sumOfNodes = BaseNumber(KBigInteger.ZERO, context.dataUnit)
    numberNodes.forEach { node -> sumOfNodes += node.value }
    // kill all children and place their sum instead
    val updatedChildren =
      node.children
        .filter { child -> child !is ProgrammerNumberNode }
        .plus(ProgrammerNumberNode(sumOfNodes, context))
    val updatedNode = node.withNewChildren(updatedChildren)
    return updatedNode
  }

  private fun simplifyMultiply(node: MultiplyNode): MultiplyNode? {
    val numberNodes = node.children.filterIsInstance<ProgrammerNumberNode>()
    if (numberNodes.size < 2) return null
    var productOfNodes = BaseNumber(KBigInteger.ONE, context.dataUnit)
    numberNodes.forEach { node -> productOfNodes *= node.value }
    val updatedChildren =
      node.children
        .filter { child -> child !is ProgrammerNumberNode }
        .plus(ProgrammerNumberNode(productOfNodes, context))
    val updatedNode = node.withNewChildren(updatedChildren)
    return updatedNode
  }

  private fun simplifyDivide(node: DivideNode): ProgrammerNumberNode? {
    if (node.children.size != 2) return null
    val child1 = (node.children[0] as? ProgrammerNumberNode)?.value ?: return null
    val child2 = (node.children[1] as? ProgrammerNumberNode)?.value ?: return null
    if (child2.value == KBigInteger.ZERO) throw ExpressionException.DivideByZero()
    val result = child1.div(child2)
    return ProgrammerNumberNode(result, context)
  }

  private fun simplifyProgrammerOperator(node: ProgrammerOperatorNode): ProgrammerNumberNode? {
    if (node.children.size != 2) return null
    val child1 = (node.children[0] as? ProgrammerNumberNode)?.value ?: return null
    val child2 = (node.children[1] as? ProgrammerNumberNode)?.value ?: return null
    val result =
      when (node.token) {
        Token.And -> child1.and(child2)
        Token.Lsh -> child1.lsh(child2)
        Token.Mod -> child1.mod(child2)
        Token.Nand -> child1.nand(child2)
        Token.Nor -> child1.nor(child2)
        Token.Or -> child1.or(child2)
        Token.Rsh -> child1.rsh(child2)
        Token.Xor -> child1.xor(child2)
        Token.Not -> return null
      }
    return ProgrammerNumberNode(result, context)
  }
}
