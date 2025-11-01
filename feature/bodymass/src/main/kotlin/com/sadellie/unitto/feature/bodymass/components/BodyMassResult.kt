/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.textfield.formatExpression
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.body_mass_normal
import unitto.core.common.generated.resources.body_mass_normal_weight
import unitto.core.common.generated.resources.body_mass_obese_1
import unitto.core.common.generated.resources.body_mass_obese_2
import unitto.core.common.generated.resources.body_mass_obese_3
import unitto.core.common.generated.resources.body_mass_overweight
import unitto.core.common.generated.resources.body_mass_underweight

@Composable
internal fun BodyMassResult(
  modifier: Modifier = Modifier,
  value: KBigDecimal,
  range: Pair<KBigDecimal, KBigDecimal>,
  rangeSuffix: String,
  formatterSymbols: FormatterSymbols,
) {
  val formattedValue =
    remember(value, formatterSymbols) {
      value
        .toFormattedString(BODY_MASS_VALUE_SCALE, OutputFormat.PLAIN)
        .formatExpression(formatterSymbols)
    }

  val formattedRange =
    remember(range, formatterSymbols, rangeSuffix) {
      val low =
        range.first
          .toFormattedString(BODY_MASS_RANGE_SCALE, OutputFormat.PLAIN)
          .formatExpression(formatterSymbols)
      val high =
        range.second
          .toFormattedString(BODY_MASS_RANGE_SCALE, OutputFormat.PLAIN)
          .formatExpression(formatterSymbols)

      "$low – $high $rangeSuffix"
    }

  val classification = remember(value) { getBodyMassData(value) }

  Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
    Column(
      modifier =
        Modifier.clip(MaterialTheme.shapes.extraLarge)
          .background(classification.color)
          .padding(Sizes.large)
          .fillMaxWidth()
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
      modifier =
        Modifier.clip(MaterialTheme.shapes.extraLarge)
          .background(MaterialTheme.colorScheme.secondaryContainer)
          .padding(Sizes.large)
          .fillMaxWidth()
    ) {
      Text(
        text = formattedRange,
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
      )
      Text(
        text = stringResource(Res.string.body_mass_normal_weight),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
      )
    }
  }
}

@Immutable
private data class BodyMassData(
  val minValue: KBigDecimal,
  val color: Color,
  val classification: StringResource,
)

@Stable
private fun getBodyMassData(value: KBigDecimal): BodyMassData =
  indexes.first { value >= it.minValue }

private val indexes by lazy {
  listOf(
    BodyMassData(
      minValue = KBigDecimal("40"),
      color = Color(0x80FF2323),
      classification = Res.string.body_mass_obese_3,
    ),
    BodyMassData(
      minValue = KBigDecimal("35"),
      color = Color(0x80F85F31),
      classification = Res.string.body_mass_obese_2,
    ),
    BodyMassData(
      minValue = KBigDecimal("30"),
      color = Color(0x80FF9634),
      classification = Res.string.body_mass_obese_1,
    ),
    BodyMassData(
      minValue = KBigDecimal("25"),
      color = Color(0x80DBEC18),
      classification = Res.string.body_mass_overweight,
    ),
    BodyMassData(
      minValue = KBigDecimal("18.5"),
      color = Color(0x805BF724),
      classification = Res.string.body_mass_normal,
    ),
    BodyMassData(
      minValue = KBigDecimal("0"),
      color = Color(0x800EACDD),
      classification = Res.string.body_mass_underweight,
    ),
  )
}
private const val BODY_MASS_VALUE_SCALE = 3
private const val BODY_MASS_RANGE_SCALE = 0

@Preview
@Composable
fun PreviewBodyMassResult() {
  BodyMassResult(
    value = KBigDecimal(18.5),
    range = KBigDecimal(50) to KBigDecimal(80),
    rangeSuffix = "kg",
    formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
  )
}
