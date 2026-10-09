/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialExpressiveTheme
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
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import com.sadellie.unitto.core.designsystem.theme.isDark
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

  Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Sizes.large)) {
    BodyMassBlock(
      modifier = Modifier.fillMaxWidth(),
      bodyMassData = classification,
      value = value,
      formatterSymbols = formatterSymbols,
    )

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

@Composable
private fun BodyMassBlock(
  modifier: Modifier,
  bodyMassData: BodyMassData,
  value: KBigDecimal,
  formatterSymbols: FormatterSymbols,
) {
  Column(
    modifier =
      modifier
        .clip(MaterialTheme.shapes.extraLarge)
        .background(bodyMassData.backgroundColor)
        .padding(Sizes.large)
  ) {
    val textColor = bodyMassData.textColor
    Text(
      text = stringResource(bodyMassData.classificationLabel),
      style = MaterialTheme.typography.displaySmall,
      color = textColor,
    )
    val formattedValue =
      remember(value, formatterSymbols) {
        value
          .toFormattedString(BODY_MASS_VALUE_SCALE, OutputFormat.PLAIN)
          .formatExpression(formatterSymbols)
      }
    Text(
      text = formattedValue,
      style = MaterialTheme.typography.bodyLarge,
      color = textColor,
    )
  }
}

@Immutable
private data class BodyMassData(
  val minValue: KBigDecimal,
  val classificationLabel: StringResource,
  private val lightBackgroundColor: Color,
  private val lightTextColor: Color,
  private val darkBackgroundColor: Color,
  private val darkTextColor: Color,
) {
  val backgroundColor: Color
    @Composable
    get() =
      if (MaterialTheme.colorScheme.background.isDark()) darkBackgroundColor
      else lightBackgroundColor

  val textColor: Color
    @Composable
    get() = if (MaterialTheme.colorScheme.background.isDark()) darkTextColor else lightTextColor
}

@Stable
private fun getBodyMassData(value: KBigDecimal): BodyMassData = indexes.first {
  value >= it.minValue
}

private val indexes by lazy {
  listOf(
    BodyMassData(
      minValue = KBigDecimal("40"),
      classificationLabel = Res.string.body_mass_obese_3,
      lightBackgroundColor = Color(0xFFDE3730),
      lightTextColor = Color(0xFFFFFFFF),
      darkBackgroundColor = Color(0xFF8C1D18),
      darkTextColor = Color(0xFFFFFFFF),
    ),
    BodyMassData(
      minValue = KBigDecimal("35"),
      classificationLabel = Res.string.body_mass_obese_2,
      lightBackgroundColor = Color(0xFFD9531E),
      lightTextColor = Color(0xFFFFFFFF),
      darkBackgroundColor = Color(0xFF8A3312),
      darkTextColor = Color(0xFFFFFFFF),
    ),
    BodyMassData(
      minValue = KBigDecimal("30"),
      classificationLabel = Res.string.body_mass_obese_1,
      lightBackgroundColor = Color(0xFFF59E0B),
      lightTextColor = Color(0xFF000000),
      darkBackgroundColor = Color(0xFF92400E),
      darkTextColor = Color(0xFFFFFFFF),
    ),
    BodyMassData(
      minValue = KBigDecimal("25"),
      classificationLabel = Res.string.body_mass_overweight,
      lightBackgroundColor = Color(0xFFD4C511),
      lightTextColor = Color(0xFF000000),
      darkBackgroundColor = Color(0xFF665D08),
      darkTextColor = Color(0xFFFFFFFF),
    ),
    BodyMassData(
      minValue = KBigDecimal("18.5"),
      classificationLabel = Res.string.body_mass_normal,
      lightBackgroundColor = Color(0xFF4ADE80),
      lightTextColor = Color(0xFF000000),
      darkBackgroundColor = Color(0xFF166534),
      darkTextColor = Color(0xFFFFFFFF),
    ),
    BodyMassData(
      minValue = KBigDecimal("0"),
      classificationLabel = Res.string.body_mass_underweight,
      lightBackgroundColor = Color(0xFF0EA5E9),
      lightTextColor = Color(0xFF000000),
      darkBackgroundColor = Color(0xFF075985),
      darkTextColor = Color(0xFFFFFFFF),
    ),
  )
}
private const val BODY_MASS_VALUE_SCALE = 3
private const val BODY_MASS_RANGE_SCALE = 0

@Preview
@Composable
private fun PreviewBodyMassResult() {
  BodyMassResult(
    value = KBigDecimal(18.5),
    range = KBigDecimal(50.0) to KBigDecimal(80.0),
    rangeSuffix = "kg",
    formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
  )
}

@Preview
@Composable
private fun PreviewBodyMassBlock() {
  Row {
    MaterialExpressiveTheme(colorScheme = LightThemeColors) {
      Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.background)) {
        indexes.forEach { index ->
          BodyMassBlock(
            modifier = Modifier,
            bodyMassData = index,
            value = index.minValue,
            formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
          )
        }
      }
    }

    MaterialExpressiveTheme(colorScheme = DarkThemeColors) {
      Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.background)) {
        indexes.forEach { index ->
          BodyMassBlock(
            modifier = Modifier,
            bodyMassData = index,
            value = index.minValue,
            formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
          )
        }
      }
    }
  }
}
