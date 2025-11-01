/*
 * Copyright (c) 2003, 2017, Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyright IBM Corporation, 2001. All Rights Reserved.
 */
package org.gciatto.kt.math

import kotlin.js.JsName
import kotlin.jvm.JvmStatic

/**
 * Specifies a *rounding behavior* for numerical operations capable of discarding precision. Each
 * rounding mode indicates how the least significant returned digit of a rounded result is to be
 * calculated. If fewer digits are returned than the digits needed to represent the exact numerical
 * result, the discarded digits will be referred to as the *discarded fraction* regardless the
 * digits' contribution to the value of the number. In other words, considered as a numerical value,
 * the discarded fraction could have an absolute value greater than one.
 *
 * Each rounding mode description includes a table listing how different two-digit decimal values
 * would round to a one digit decimal value under the rounding mode in question. The result column
 * in the tables could be gotten by creating a `BigDecimal` number with the specified value, forming
 * a [MathContext] object with the proper settings (`precision` set to `1`, and the `roundingMode`
 * set to the rounding mode in question), and calling [round][BigDecimal.round] on this number with
 * the proper `MathContext`. A summary table showing the results of these rounding operations for
 * all rounding modes appears below.
 * <table class="striped">
 * <caption>**Summary of Rounding Operations Under Different Rounding Modes**</caption> <thead>
 * <tr><th scope="col" rowspan="2">Input Number</th><th scope="col" colspan=8>Result of rounding
 * input to one digit with the given rounding mode</th> </tr> * <tr style="vertical-align:top">
 * <th>`UP`</th> <th>`DOWN`</th> <th>`CEILING`</th> <th>`FLOOR`</th> <th>`HALF_UP`</th>
 * <th>`HALF_DOWN`</th> <th>`HALF_EVEN`</th> <th>`UNNECESSARY`</th> </tr></thead> * <tbody
 * style="text-align:right">
 * <tr><th scope="row">5.5</th> <td>6</td> <td>5</td> <td>6</td> <td>5</td> <td>6</td> <td>5</td>
 * <td>6</td> <td>throw `ArithmeticException`</td> </tr> * <tr><th scope="row">2.5</th> <td>3</td>
 * <td>2</td> <td>3</td> <td>2</td> <td>3</td> <td>2</td> <td>2</td> <td>throw
 * `ArithmeticException`</td> </tr> * <tr><th scope="row">1.6</th> <td>2</td> <td>1</td> <td>2</td>
 * <td>1</td> <td>2</td> <td>2</td> <td>2</td> <td>throw `ArithmeticException`</td> </tr> * <tr><th
 * scope="row">1.1</th> <td>2</td> <td>1</td> <td>2</td> <td>1</td> <td>1</td> <td>1</td> <td>1</td>
 * <td>throw `ArithmeticException`</td> </tr> * <tr><th scope="row">1.0</th> <td>1</td> <td>1</td>
 * <td>1</td> <td>1</td> <td>1</td> <td>1</td> <td>1</td> <td>1</td> </tr> * <tr><th
 * scope="row">-1.0</th> <td>-1</td> <td>-1</td> <td>-1</td> <td>-1</td> <td>-1</td> <td>-1</td>
 * <td>-1</td> <td>-1</td> </tr> * <tr><th scope="row">-1.1</th> <td>-2</td> <td>-1</td> <td>-1</td>
 * <td>-2</td> <td>-1</td> <td>-1</td> <td>-1</td> <td>throw `ArithmeticException`</td> </tr> *
 * <tr><th scope="row">-1.6</th> <td>-2</td> <td>-1</td> <td>-1</td> <td>-2</td> <td>-2</td>
 * <td>-2</td> <td>-2</td> <td>throw `ArithmeticException`</td> </tr> * <tr><th
 * scope="row">-2.5</th> <td>-3</td> <td>-2</td> <td>-2</td> <td>-3</td> <td>-3</td> <td>-2</td>
 * <td>-2</td> <td>throw `ArithmeticException`</td> </tr> * <tr><th scope="row">-5.5</th>
 * <td>-6</td> <td>-5</td> <td>-5</td> <td>-6</td> <td>-6</td> <td>-5</td> <td>-6</td> <td>throw
 * `ArithmeticException`</td> </tr></tbody> * </table> *
 *
 * This `enum` is intended to replace the integer-based enumeration of rounding mode constants in
 * [BigDecimal] ([BigDecimal.ROUND_UP], [BigDecimal.ROUND_DOWN], etc. ).
 *
 * @see BigDecimal
 * @see MathContext
 * @author Josh Bloch
 * @author Mike Cowlishaw
 * @author Joseph D. Darcy
 * @since 1.5
 */
@Suppress("DEPRECATION")
enum class RoundingMode(@JsName("value") val value: Int) {
  /**
   * Rounding mode to round away from zero. Always increments the digit prior to a non-zero
   * discarded fraction. Note that this rounding mode never decreases the magnitude of the
   * calculated value.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode UP Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `UP` rounding </th></tr></thead> * <tbody style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>6</td> </tr> * <tr><th scope="row">2.5</th> <td>3</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>2</td> </tr> * <tr><th scope="row">1.1</th> <td>2</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-2</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-2</td> </tr> * <tr><th scope="row">-2.5</th> <td>-3</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-6</td> </tr></tbody> * </table> *
   */
  UP(0),

  /**
   * Rounding mode to round towards zero. Never increments the digit prior to a discarded fraction
   * (i.e., truncates). Note that this rounding mode never increases the magnitude of the calculated
   * value.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode DOWN Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `DOWN` rounding </th></tr></thead> * <tbody
   * style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>5</td> </tr> * <tr><th scope="row">2.5</th> <td>2</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>1</td> </tr> * <tr><th scope="row">1.1</th> <td>1</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-1</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-1</td> </tr> * <tr><th scope="row">-2.5</th> <td>-2</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-5</td> </tr></tbody> * </table> *
   */
  DOWN(1),

  /**
   * Rounding mode to round towards positive infinity. If the result is positive, behaves as for
   * `RoundingMode.UP`; if negative, behaves as for `RoundingMode.DOWN`. Note that this rounding
   * mode never decreases the calculated value.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode CEILING Examples</caption> <thead>
   * <tr style="vertical-align:top"><th>Input Number</th> <th>Input rounded to one digit<br></br>
   * with `CEILING` rounding </th></tr></thead> * <tbody style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>6</td> </tr> * <tr><th scope="row">2.5</th> <td>3</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>2</td> </tr> * <tr><th scope="row">1.1</th> <td>2</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-1</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-1</td> </tr> * <tr><th scope="row">-2.5</th> <td>-2</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-5</td> </tr></tbody> * </table> *
   */
  CEILING(2),

  /**
   * Rounding mode to round towards negative infinity. If the result is positive, behave as for
   * `RoundingMode.DOWN`; if negative, behave as for `RoundingMode.UP`. Note that this rounding mode
   * never increases the calculated value.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode FLOOR Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `FLOOR` rounding </th></tr></thead> * <tbody
   * style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>5</td> </tr> * <tr><th scope="row">2.5</th> <td>2</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>1</td> </tr> * <tr><th scope="row">1.1</th> <td>1</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-2</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-2</td> </tr> * <tr><th scope="row">-2.5</th> <td>-3</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-6</td> </tr></tbody> * </table> *
   */
  FLOOR(3),

  /**
   * Rounding mode to round towards &quot;nearest neighbor&quot; unless both neighbors are
   * equidistant, in which case round up. Behaves as for `RoundingMode.UP` if the discarded fraction
   * is 0.5; otherwise, behaves as for `RoundingMode.DOWN`. Note that this is the rounding mode
   * commonly taught at school.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode HALF_UP Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `HALF_UP` rounding </th></tr></thead> * <tbody
   * style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>6</td> </tr> * <tr><th scope="row">2.5</th> <td>3</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>2</td> </tr> * <tr><th scope="row">1.1</th> <td>1</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-1</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-2</td> </tr> * <tr><th scope="row">-2.5</th> <td>-3</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-6</td> </tr></tbody> * </table> *
   */
  HALF_UP(4),

  /**
   * Rounding mode to round towards &quot;nearest neighbor&quot; unless both neighbors are
   * equidistant, in which case round down. Behaves as for `RoundingMode.UP` if the discarded
   * fraction is &gt; 0.5; otherwise, behaves as for `RoundingMode.DOWN`.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode HALF_DOWN Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `HALF_DOWN` rounding </th></tr></thead> * <tbody
   * style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>5</td> </tr> * <tr><th scope="row">2.5</th> <td>2</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>2</td> </tr> * <tr><th scope="row">1.1</th> <td>1</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-1</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-2</td> </tr> * <tr><th scope="row">-2.5</th> <td>-2</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-5</td> </tr></tbody> * </table> *
   */
  HALF_DOWN(5),

  /**
   * Rounding mode to round towards the &quot;nearest neighbor&quot; unless both neighbors are
   * equidistant, in which case, round towards the even neighbor. Behaves as for
   * `RoundingMode.HALF_UP` if the digit to the left of the discarded fraction is odd; behaves as
   * for `RoundingMode.HALF_DOWN` if it's even. Note that this is the rounding mode that
   * statistically minimizes cumulative error when applied repeatedly over a sequence of
   * calculations. It is sometimes known as &quot;Banker&#39;s rounding,&quot; and is chiefly used
   * in the USA. This rounding mode is analogous to the rounding policy used for `float` and
   * `double` arithmetic in Java.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode HALF_EVEN Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `HALF_EVEN` rounding </th></tr></thead> * <tbody
   * style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>6</td> </tr> * <tr><th scope="row">2.5</th> <td>2</td> </tr> *
   * <tr><th scope="row">1.6</th> <td>2</td> </tr> * <tr><th scope="row">1.1</th> <td>1</td> </tr> *
   * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th scope="row">-1.0</th> <td>-1</td>
   * </tr> * <tr><th scope="row">-1.1</th> <td>-1</td> </tr> * <tr><th scope="row">-1.6</th>
   * <td>-2</td> </tr> * <tr><th scope="row">-2.5</th> <td>-2</td> </tr> * <tr><th
   * scope="row">-5.5</th> <td>-6</td> </tr></tbody> * </table> *
   */
  HALF_EVEN(6),

  /**
   * Rounding mode to assert that the requested operation has an exact result, hence no rounding is
   * necessary. If this rounding mode is specified on an operation that yields an inexact result, an
   * `ArithmeticException` is thrown.
   *
   * Example:
   * <table class="striped">
   * <caption>Rounding mode UNNECESSARY Examples</caption> <thead>
   * <tr style="vertical-align:top"><th scope="col">Input Number</th> <th scope="col">Input rounded
   * to one digit<br></br> with `UNNECESSARY` rounding </th></tr></thead> * <tbody
   * style="text-align:right">
   * <tr><th scope="row">5.5</th> <td>throw `ArithmeticException`</td> </tr> * <tr><th
   * scope="row">2.5</th> <td>throw `ArithmeticException`</td> </tr> * <tr><th scope="row">1.6</th>
   * <td>throw `ArithmeticException`</td> </tr> * <tr><th scope="row">1.1</th> <td>throw
   * `ArithmeticException`</td> </tr> * <tr><th scope="row">1.0</th> <td>1</td> </tr> * <tr><th
   * scope="row">-1.0</th> <td>-1</td> </tr> * <tr><th scope="row">-1.1</th> <td>throw
   * `ArithmeticException`</td> </tr> * <tr><th scope="row">-1.6</th> <td>throw
   * `ArithmeticException`</td> </tr> * <tr><th scope="row">-2.5</th> <td>throw
   * `ArithmeticException`</td> </tr> * <tr><th scope="row">-5.5</th> <td>throw
   * `ArithmeticException`</td> </tr></tbody> * </table> *
   */
  UNNECESSARY(7);

  companion object {
    /**
     * Returns the `RoundingMode` object corresponding to a legacy integer rounding mode constant in
     * [BigDecimal].
     *
     * @param rm legacy integer rounding mode to convert
     * @return `RoundingMode` corresponding to the given integer.
     * @throws IllegalArgumentException integer is out of range
     */
    @JvmStatic @JsName("valueOfInt") fun valueOf(rm: Int): RoundingMode = values()[rm]
  }
}
