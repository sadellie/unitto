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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class CalculateExpressionProgrammerTest {
  @Test
  fun `simple number base10`() = runTest {
    assertEquals("123", programmerCalculateExpression("123", 10, DataUnit.QWORD))
  }

  @Test
  fun `simple plus operations`() = runTest {
    assertEquals("19CE", programmerCalculateExpression("123+ABC+DEF", 16, DataUnit.QWORD))
  }

  @Test
  fun `simple multiply operations`() = runTest {
    assertEquals("AA04690C", programmerCalculateExpression("123×ABC×DEF", 16, DataUnit.QWORD))
  }

  @Test
  fun `simple divide operations`() = runTest {
    // decimals are not allowed
    assertEquals("0", programmerCalculateExpression("123÷ABC", 16, DataUnit.QWORD))
    assertEquals("4", programmerCalculateExpression("8÷2", 16, DataUnit.QWORD))
  }

  @Test
  fun `unary minus`() = runTest {
    // decimals are not allowed
    assertEquals("FFFFFFFFFFFFF544", programmerCalculateExpression("−ABC", 16, DataUnit.QWORD))
    assertEquals("ABC", programmerCalculateExpression("−FFFFFFFFFFFFF544", 16, DataUnit.QWORD))
  }

  @Test
  fun `bitwise operations`() = runTest {
    assertEquals("3", programmerCalculateExpression("123and3", 16, DataUnit.QWORD))
    assertEquals("0", programmerCalculateExpression("Aand1", 16, DataUnit.QWORD))

    assertEquals("918", programmerCalculateExpression("123lsh3", 16, DataUnit.QWORD))
    assertEquals("14", programmerCalculateExpression("Alsh1", 16, DataUnit.QWORD))

    assertEquals("0", programmerCalculateExpression("123mod3", 16, DataUnit.QWORD))
    assertEquals("0", programmerCalculateExpression("Amod1", 16, DataUnit.QWORD))

    assertEquals("FFFFFFFFFFFFFFFC", programmerCalculateExpression("123nand3", 16, DataUnit.QWORD))
    assertEquals("FFFFFFFFFFFFFFFF", programmerCalculateExpression("Anand1", 16, DataUnit.QWORD))

    assertEquals("FFFFFFFFFFFFFEDC", programmerCalculateExpression("123nor3", 16, DataUnit.QWORD))
    assertEquals("FFFFFFFFFFFFFFF4", programmerCalculateExpression("Anor1", 16, DataUnit.QWORD))

    assertEquals("123", programmerCalculateExpression("123or3", 16, DataUnit.QWORD))
    assertEquals("B", programmerCalculateExpression("Aor1", 16, DataUnit.QWORD))

    assertEquals("24", programmerCalculateExpression("123rsh3", 16, DataUnit.QWORD))
    assertEquals("5", programmerCalculateExpression("Arsh1", 16, DataUnit.QWORD))

    assertEquals("120", programmerCalculateExpression("123xor3", 16, DataUnit.QWORD))
    assertEquals("B", programmerCalculateExpression("Axor1", 16, DataUnit.QWORD))

    assertEquals("FFFFFFFFFFFFFFFC", programmerCalculateExpression("not3", 16, DataUnit.QWORD))
    assertEquals("FFFFFFFFFFFFFFF5", programmerCalculateExpression("notA", 16, DataUnit.QWORD))
  }
}
