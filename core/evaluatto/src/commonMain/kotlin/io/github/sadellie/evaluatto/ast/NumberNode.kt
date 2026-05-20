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
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isLessThan
import io.github.sadellie.evaluatto.programmer.BaseNumber

internal interface NumberNode : AtomicNode {
  override val token: Token.Number
}

internal data class MathNumberNode(
  val value: KBigDecimal,
  override val token: Token.Number = Token.Number(value.toPlainString()),
) : NumberNode {
  constructor(
    token: Token.Number,
    context: ScriptContext.Math,
  ) : this(
    (if (token.symbol == ".") KBigDecimal.ZERO else KBigDecimal(token.symbol)).setScale(
      context.scale,
      context.roundingMode,
    ),
    token,
  )

  fun negate(): MathNumberNode {
    val newValue = value.negate()
    val isNegative = value.isLessThan(KBigDecimal.ZERO)
    val newTokenSymbol =
      if (isNegative) {
        token.symbol.removePrefix(Token.UnaryMinus.symbol)
      } else {
        Token.UnaryMinus.symbol + token.symbol
      }
    return copy(value = newValue, token = token.copy(symbol = newTokenSymbol))
  }
}

internal data class ProgrammerNumberNode(override val token: Token.Number, val value: BaseNumber) :
  NumberNode {
  constructor(
    baseNumber: BaseNumber,
    context: ScriptContext.Programmer,
  ) : this(Token.Number(baseNumber.toString(context.base)), baseNumber)

  constructor(
    token: Token.Number,
    context: ScriptContext.Programmer,
  ) : this(token, BaseNumber(token.symbol, context.base, context.dataUnit))

  fun negate(context: ScriptContext.Programmer) = ProgrammerNumberNode(value.negate(), context)
}
