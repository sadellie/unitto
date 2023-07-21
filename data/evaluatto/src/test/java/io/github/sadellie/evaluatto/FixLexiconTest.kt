/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import org.junit.jupiter.api.Test

class FixLexiconTest {

    @Test
    fun `missing multiply`() {
        assertLex(
            "2×(69−420)", "2(69−420)"
        )

        assertLex(
            "0.×(69−420)", "0.(69−420)"
        )

        assertLex(
            ".0×(69−420)", ".0(69−420)"
        )

        assertLex(
            ".×(69−420)", ".(69−420)"
        )

        assertLex(
            "2×(69−420)×(23−4)×cos(9)×tan((sin⁻¹(.9)))",
            "2(69−420)(23−4)cos(9)tan((sin⁻¹(.9)))"
        )

        assertLex(
            "e×e+π", "ee+π"
        )
    }

    @Test
    fun `balanced brackets`() {
        assertLex(
            "123×(12+4)", "123(12+4"
        )

        assertLex(
            "12312+4", "12312+4"
        )

        assertLex(
            "123)))12+4", "123)))12+4"
        )

        assertLex(
            "sin(cos(tan(3)))", "sin(cos(tan(3"
        )

        assertLex(
            "sin(cos(tan(3)))", "sin(cos(tan(3)"
        )

        assertLex(
            "sin(cos(tan(3)))", "sin(cos(tan(3))"
        )
    }

    @Test
    fun `unpack percentage`() {
        // 132.5+14% −> 132.5+132.5*0.14
        assertLex(
            "132.5+(14÷100×(132.5))", "132.5+14%"
        )

        // 132.5+(14)% −> 132.5+(14)/100*132.5
        assertLex(
            "132.5+((14)÷100×(132.5))" , "132.5+(14)%"
        )

        // 132.5+(15+4)% −> 132.5+(15+4)*132.5/100
        assertLex(
            "132.5+((15+4)÷100×(132.5))", "132.5+(15+4)%"
        )

        // (132.5+12%)+(15+4)% −> (132.5+12/100*132.5)+(15+4)/100*(132.5+12/100*132.5)
        assertLex(
            "(132.5+(12÷100×(132.5)))+((15+4)÷100×((132.5+(12÷100×(132.5)))))", "(132.5+12%)+(15+4)%"
        )

        // 2% −> 2/100
        assertLex(
            "(2÷100)", "2%"
        )

        assertLex(
            "((2)÷100)", "(2)%"
        )

        assertLex(
            "(132.5+5)+(90÷100×((132.5+5)))", "(132.5+5)+90%"
        )

        assertLex(
            "((90÷100)+(90÷100×((90÷100))))", "(90%+90%)"
        )

        assertLex(
            "((90÷100)÷(90÷100))+((90÷100)−(90÷100×((90÷100))))", "(90%÷90%)+(90%−90%)"
        )

        assertLex("(80÷100)×(80÷100)", "80%80%")

        assertLex("10+(2.0÷100×(10))", "10+2.0%")

        assertLex("10+(2.÷100×(10))", "10+2.%")
    }
}
