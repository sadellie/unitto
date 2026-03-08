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

package com.sadellie.unitto.feature.programmer

import io.github.sadellie.evaluatto.programmer.DataUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class ConvertExpressionBaseTest {
  private val dataUnit = DataUnit.QWORD

  @Test
  fun `convert single binary digit to hex`() {
    val result = convertExpressionBase("1", 2, 16, dataUnit)
    assertEquals("1", result)
  }

  @Test
  fun `convert single hex digit to binary`() {
    val result = convertExpressionBase("A", 16, 2, dataUnit)
    assertEquals("1010", result)
  }

  @Test
  fun `convert binary number to decimal`() {
    val result = convertExpressionBase("101", 2, 10, dataUnit)
    assertEquals("5", result)
  }

  @Test
  fun `convert decimal number to hex`() {
    val result = convertExpressionBase("10", 10, 16, dataUnit)
    assertEquals("A", result)
  }

  @Test
  fun `expression with plus operator preserves operator`() {
    val result = convertExpressionBase("101+11", 2, 16, dataUnit)
    assertEquals("5+3", result)
  }

  @Test
  fun `expression with xor operator preserves lowercase xor`() {
    val result = convertExpressionBase("101xor11", 2, 16, dataUnit)
    assertEquals("5xor3", result)
  }

  @Test
  fun `expression with and operator preserves lowercase and`() {
    val result = convertExpressionBase("110and101", 2, 16, dataUnit)
    assertEquals("6and5", result)
  }

  @Test
  fun `hex numbers remain uppercase after conversion`() {
    val result = convertExpressionBase("AxorC", 16, 2, dataUnit)
    assertEquals("1010xor1100", result)
  }

  @Test
  fun `empty string returns empty`() {
    val result = convertExpressionBase("", 2, 10, dataUnit)
    assertEquals("", result)
  }

  @Test
  fun `non digit parts are preserved unchanged`() {
    val result = convertExpressionBase("101==11&&AA!=FF", 2, 16, dataUnit)
    assertEquals("5==3&&AA!=FF", result)
  }

  @Test
  fun `complex expression with multiple operators`() {
    val result = convertExpressionBase("AandBxorCorD", 16, 10, dataUnit)
    assertEquals("10and11xor12or13", result)
  }

  @Test
  fun `lowercase letters in operators do not become part of number`() {
    val result = convertExpressionBase("FandA", 16, 10, dataUnit)
    assertEquals("15and10", result)
  }

  @Test
  fun `preserve all programmer operators in lowercase`() {
    val expression = "AorBxorCandDnandEnorF"
    val result = convertExpressionBase(expression, 16, 10, dataUnit)
    val expected = "10or11xor12and13nand14nor15"
    assertEquals(expected, result)
  }
}
