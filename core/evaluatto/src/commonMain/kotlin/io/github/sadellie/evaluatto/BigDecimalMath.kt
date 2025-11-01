/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KBigDecimalMath
import com.sadellie.unitto.core.common.KMathContext

internal fun KBigDecimal.sin(radianMode: Boolean, mathContext: KMathContext): KBigDecimal {
  val angle = if (radianMode) this else KBigDecimalMath.toRadians(this, mathContext)
  return KBigDecimalMath.sin(angle, mathContext)
}

internal fun KBigDecimal.arsin(radianMode: Boolean, mathContext: KMathContext): KBigDecimal {
  val angle = KBigDecimalMath.asin(this, mathContext)
  return if (radianMode) angle else KBigDecimalMath.toDegrees(angle, mathContext)
}

internal fun KBigDecimal.cos(radianMode: Boolean, mathContext: KMathContext): KBigDecimal {
  val angle = if (radianMode) this else KBigDecimalMath.toRadians(this, mathContext)
  return KBigDecimalMath.cos(angle, mathContext)
}

internal fun KBigDecimal.arcos(radianMode: Boolean, mathContext: KMathContext): KBigDecimal {
  val angle = KBigDecimalMath.acos(this, mathContext)
  return if (radianMode) angle else KBigDecimalMath.toDegrees(angle, mathContext)
}

internal fun KBigDecimal.tan(radianMode: Boolean, mathContext: KMathContext): KBigDecimal {
  val angle = if (radianMode) this else KBigDecimalMath.toRadians(this, mathContext)
  return KBigDecimalMath.tan(angle, mathContext)
}

internal fun KBigDecimal.artan(radianMode: Boolean, mathContext: KMathContext): KBigDecimal {
  val angle = KBigDecimalMath.atan(this, mathContext)
  return if (radianMode) angle else KBigDecimalMath.toDegrees(angle, mathContext)
}

internal fun KBigDecimal.ln(mathContext: KMathContext): KBigDecimal {
  return KBigDecimalMath.log(this, mathContext)
}

internal fun KBigDecimal.log(mathContext: KMathContext): KBigDecimal {
  return KBigDecimalMath.log10(this, mathContext)
}

internal fun KBigDecimal.exp(mathContext: KMathContext): KBigDecimal {
  return KBigDecimalMath.exp(this, mathContext)
}

internal fun KBigDecimal.factorial(): KBigDecimal {
  if (this.remainder(KBigDecimal.ONE).compareTo(KBigDecimal.ZERO) != 0)
    throw ExpressionException.FactorialCalculation()
  if (this < KBigDecimal.ZERO) throw ExpressionException.FactorialCalculation()
  if (this > maxFactorial) throw ExpressionException.TooBig()

  if (this.compareTo(KBigDecimal.ZERO) == 0) return KBigDecimal.ONE

  var expr = this
  for (i in 1 until this.toInt()) {
    expr *= KBigDecimal(i.toDouble())
  }
  return expr
}

private val maxFactorial by lazy { KBigDecimal("9999") }
