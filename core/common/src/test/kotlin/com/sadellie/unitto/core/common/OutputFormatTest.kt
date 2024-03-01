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

package com.sadellie.unitto.core.common

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * This test makes sure that values stored in datastore match with current codebase. If fails,
 * setting in user data that uses this object will be invalid - migration step is required.
 */
class OutputFormatTest {

  @Test fun testExists() = Assert.assertNotNull(OutputFormat)

  @Test fun testOutputFormatPlain() = assertEquals(0, OutputFormat.PLAIN)

  @Test fun testOutputFormatAllowEngineering() = assertEquals(1, OutputFormat.ALLOW_ENGINEERING)

  @Test fun testOutputFormatForceEngineering() = assertEquals(2, OutputFormat.FORCE_ENGINEERING)
}
