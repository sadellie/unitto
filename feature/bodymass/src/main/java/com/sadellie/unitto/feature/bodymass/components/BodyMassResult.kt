/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.bodymass.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import java.math.BigDecimal

@Composable
internal fun BodyMassResult(
    modifier: Modifier = Modifier,
    value: BigDecimal,
    range: Pair<BigDecimal, BigDecimal>,
    rangeSuffix: String,
    formatterSymbols: FormatterSymbols,
) {
    val formattedValue = remember(value, formatterSymbols) {
        value
            .format(3, OutputFormat.PLAIN)
            .formatExpression(formatterSymbols)
    }

    val formattedRange = remember(range, formatterSymbols) {
        val low = range.first
            .format(0, OutputFormat.PLAIN)
            .formatExpression(formatterSymbols)

        val high = range.second
            .format(0, OutputFormat.PLAIN)
            .formatExpression(formatterSymbols)

        "$low – $high $rangeSuffix"
    }

    val classification = remember(value) {
        getBodyMassData(value)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(classification.color)
                .padding(16.dp, 32.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(classification.classification),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = formattedValue,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp, 32.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = formattedRange,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = stringResource(R.string.body_mass_normal_weight),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

@Immutable
private data class BodyMassData(
    val minValue: BigDecimal,
    val color: Color,
    @StringRes val classification: Int,
)

@Stable
private fun getBodyMassData(value: BigDecimal): BodyMassData =
    indexes.first { value >= it.minValue }

private val indexes by lazy {
    listOf(
        BodyMassData(
            minValue = BigDecimal("40"),
            color = Color(0x80FF2323),
            classification = R.string.body_mass_obese_3,
        ),
        BodyMassData(
            minValue = BigDecimal("35"),
            color = Color(0x80F85F31),
            classification = R.string.body_mass_obese_2,
        ),
        BodyMassData(
            minValue = BigDecimal("30"),
            color = Color(0x80FF9634),
            classification = R.string.body_mass_obese_1,
        ),
        BodyMassData(
            minValue = BigDecimal("25"),
            color = Color(0x80DBEC18),
            classification = R.string.body_mass_overweight,
        ),
        BodyMassData(
            minValue = BigDecimal("18.5"),
            color = Color(0x805BF724),
            classification = R.string.body_mass_normal,
        ),
        BodyMassData(
            minValue = BigDecimal("0"),
            color = Color(0x800EACDD),
            classification = R.string.body_mass_underweight,
        ),
    )
}

@Preview
@Composable
fun PreviewBodyMassResult() {
    BodyMassResult(
        value = BigDecimal(18.5),
        range = BigDecimal(50) to BigDecimal(80),
        rangeSuffix = "kg",
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
    )
}
