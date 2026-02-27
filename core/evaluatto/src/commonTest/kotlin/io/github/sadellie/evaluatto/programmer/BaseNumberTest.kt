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

class BaseNumberTest {

  @Test
  fun `assert negate`() {
    assertEquals(
      BaseNumber("FFFFFFFFFFFFFFF9", 16, DataUnit.QWORD),
      BaseNumber("7", 16, DataUnit.QWORD).negate(),
    )
    assertEquals(
      BaseNumber("7", 16, DataUnit.QWORD),
      BaseNumber("FFFFFFFFFFFFFFF9", 16, DataUnit.QWORD).negate(),
    )
  }

  @Test
  fun `assert plus`() {
    val abcd = BaseNumber("ABCD", 16, DataUnit.QWORD)
    assertEquals(BaseNumber("1579A", 16, DataUnit.QWORD), abcd + abcd)
  }

  @Test
  fun `assert plus small width`() {
    val abcd = BaseNumber("ABCD", 16, DataUnit.WORD)
    assertEquals(BaseNumber("579A", 16, DataUnit.WORD), abcd + abcd)
  }

  @Test
  fun `assert multiply`() {
    assertEquals(
      BaseNumber("30", 16, DataUnit.QWORD),
      BaseNumber("10", 16, DataUnit.QWORD) * BaseNumber("3", 16, DataUnit.QWORD),
    )
  }

  @Test
  fun `assert div`() {
    assertEquals(
      BaseNumber("5", 16, DataUnit.QWORD),
      BaseNumber("10", 16, DataUnit.QWORD) / BaseNumber("3", 16, DataUnit.QWORD),
    )
    assertEquals(
      BaseNumber("0", 16, DataUnit.QWORD),
      BaseNumber("8", 16, DataUnit.QWORD) / BaseNumber("9", 16, DataUnit.QWORD),
    )
  }

  @Test
  fun `assert and`() {
    assertEquals(
      BaseNumber("f", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD)
        .and(BaseNumber("f", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("f0", 16, DataUnit.QWORD),
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD)
        .and(BaseNumber("ff", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert or`() {
    assertEquals(
      BaseNumber("ff", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD)
        .or(BaseNumber("f", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("ffffffffffffffff", 16, DataUnit.QWORD),
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD).or(BaseNumber("f", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert not`() {
    assertEquals(
      BaseNumber("ffffffffffffff00", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD).not(),
    )
    assertEquals(
      BaseNumber("0", 16, DataUnit.QWORD),
      BaseNumber("ffffffffffffffff", 16, DataUnit.QWORD).not(),
    )
  }

  @Test
  fun `assert nand`() {
    assertEquals(
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD).nand(BaseNumber("f", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("ffffffffffffff0f", 16, DataUnit.QWORD),
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD).nand(BaseNumber("ff", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert nor`() {
    assertEquals(
      BaseNumber("ffffffffffffff00", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD)
        .nor(BaseNumber("f", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("0", 16, DataUnit.QWORD),
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD).nor(BaseNumber("f", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert xor`() {
    assertEquals(
      BaseNumber("f0", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD)
        .xor(BaseNumber("f", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("ffffffffffffff0f", 16, DataUnit.QWORD),
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD).xor(BaseNumber("ff", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert lsh`() {
    assertEquals(
      BaseNumber("ff0", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD).lsh(BaseNumber("4", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD),
      BaseNumber("ffffffffffffffff", 16, DataUnit.QWORD).lsh(BaseNumber("4", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert rsh`() {
    assertEquals(
      BaseNumber("f", 16, DataUnit.QWORD),
      BaseNumber("ff", 16, DataUnit.QWORD).rsh(BaseNumber("4", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("fffffffffffffff0", 16, DataUnit.QWORD),
      BaseNumber("ffffffffffffff00", 16, DataUnit.QWORD).rsh(BaseNumber("4", 16, DataUnit.QWORD)),
    )
  }

  @Test
  fun `assert mod`() {
    assertEquals(
      BaseNumber("1", 16, DataUnit.QWORD),
      BaseNumber("1a", 16, DataUnit.QWORD).mod(BaseNumber("5", 16, DataUnit.QWORD)),
    )
    assertEquals(
      BaseNumber("ffffffffffffffff", 16, DataUnit.QWORD),
      BaseNumber("ffffffffffffffff", 16, DataUnit.QWORD).mod(BaseNumber("10", 16, DataUnit.QWORD)),
    )
  }
}
