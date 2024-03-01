/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package io.github.sadellie.evaluatto

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.MathContext

internal fun BigDecimal.sin(radianMode: Boolean, mathContext: MathContext): BigDecimal {
  val angle = if (radianMode) this else BigDecimalMath.toRadians(this, mathContext)
  return BigDecimalMath.sin(angle, mathContext)
}

internal fun BigDecimal.arsin(radianMode: Boolean, mathContext: MathContext): BigDecimal {
  val angle = BigDecimalMath.asin(this, mathContext)
  return if (radianMode) angle else BigDecimalMath.toDegrees(angle, mathContext)
}

internal fun BigDecimal.cos(radianMode: Boolean, mathContext: MathContext): BigDecimal {
  val angle = if (radianMode) this else BigDecimalMath.toRadians(this, mathContext)
  return BigDecimalMath.cos(angle, mathContext)
}

internal fun BigDecimal.arcos(radianMode: Boolean, mathContext: MathContext): BigDecimal {
  val angle = BigDecimalMath.acos(this, mathContext)
  return if (radianMode) angle else BigDecimalMath.toDegrees(angle, mathContext)
}

internal fun BigDecimal.tan(radianMode: Boolean, mathContext: MathContext): BigDecimal {
  val angle = if (radianMode) this else BigDecimalMath.toRadians(this, mathContext)
  return BigDecimalMath.tan(angle, mathContext)
}

internal fun BigDecimal.artan(radianMode: Boolean, mathContext: MathContext): BigDecimal {
  val angle = BigDecimalMath.atan(this, mathContext)
  return if (radianMode) angle else BigDecimalMath.toDegrees(angle, mathContext)
}

internal fun BigDecimal.ln(mathContext: MathContext): BigDecimal {
  return BigDecimalMath.log(this, mathContext)
}

internal fun BigDecimal.log(mathContext: MathContext): BigDecimal {
  return BigDecimalMath.log10(this, mathContext)
}

internal fun BigDecimal.exp(mathContext: MathContext): BigDecimal {
  return BigDecimalMath.exp(this, mathContext)
}

internal fun BigDecimal.factorial(): BigDecimal {
  if (this.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0)
    throw ExpressionException.FactorialCalculation()
  if (this < BigDecimal.ZERO) throw ExpressionException.FactorialCalculation()
  if (this > maxFactorial) throw ExpressionException.TooBig()

  if (this.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ONE

  var expr = this
  for (i in 1 until this.toInt()) {
    expr *= BigDecimal(i)
  }
  return expr
}

private val maxFactorial by lazy { BigDecimal("9999") }
