/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 * Portions Copyright IBM Corporation, 1997, 2001. All Rights Reserved.
 */

package org.gciatto.kt.math

import kotlin.js.JsName
import kotlin.jvm.JvmField

/**
 * Immutable objects which encapsulate the context settings which describe certain rules for
 * numerical operators, such as those implemented by the [BigDecimal] class.
 *
 * The base-independent settings are:
 * 1. `precision`: the number of digits to be used for an operation; results are rounded to this
 *    precision
 * 1. `roundingMode`: a [RoundingMode] object which specifies the algorithm to be used for rounding.
 *
 * @see BigDecimal
 * @see RoundingMode
 * @author Mike Cowlishaw
 * @author Joseph D. Darcy
 * @since 1.5
 */
data class MathContext(
  val precision: Int = 9,
  val roundingMode: RoundingMode = RoundingMode.HALF_UP,
) {
  init {
    require(precision >= 0)
  }

  companion object {
    /**
     * A `MathContext` object whose settings have the values required for unlimited precision
     * arithmetic. The values of the settings are: ` precision=0 roundingMode=HALF_UP ` *
     */
    @JvmField @JsName("UNLIMITED") val UNLIMITED = MathContext(0, RoundingMode.HALF_UP)

    /**
     * A `MathContext` object with a precision setting matching the IEEE 754R Decimal32 format, 7
     * digits, and a rounding mode of [HALF_EVEN][RoundingMode.HALF_EVEN], the IEEE 754R default.
     */
    @JvmField @JsName("DECIMAL32") val DECIMAL32 = MathContext(7, RoundingMode.HALF_EVEN)

    /**
     * A `MathContext` object with a precision setting matching the IEEE 754R Decimal64 format, 16
     * digits, and a rounding mode of [HALF_EVEN][RoundingMode.HALF_EVEN], the IEEE 754R default.
     */
    @JvmField @JsName("DECIMAL64") val DECIMAL64 = MathContext(16, RoundingMode.HALF_EVEN)

    /**
     * A `MathContext` object with a precision setting matching the IEEE 754R Decimal128 format, 34
     * digits, and a rounding mode of [HALF_EVEN][RoundingMode.HALF_EVEN], the IEEE 754R default.
     */
    @JvmField @JsName("DECIMAL128") val DECIMAL128 = MathContext(34, RoundingMode.HALF_EVEN)
  }
}
