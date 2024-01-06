/*
 * Unitto is a unit converter for Android
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

package com.sadellie.unitto.feature.bodymass

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SegmentedButton
import com.sadellie.unitto.core.ui.common.SegmentedButtonsRow
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.UnittoEmptyScreen
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTransformer
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.common.isEqualTo
import com.sadellie.unitto.data.common.isLessThan
import java.math.BigDecimal

@Composable
internal fun BodyMassRoute(
    openDrawer: () -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: BodyMassViewModel = hiltViewModel()
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        UIState.Loading -> UnittoEmptyScreen()
        is UIState.Ready -> BodyMassScreen(
            uiState = uiState,
            updateHeight1 = viewModel::updateHeight1,
            updateHeight2 = viewModel::updateHeight2,
            updateWeight = viewModel::updateWeight,
            updateIsMetric = viewModel::updateIsMetric,
            openDrawer = openDrawer,
            navigateToSettings = navigateToSettings
        )
    }
}

@Composable
private fun BodyMassScreen(
    uiState: UIState.Ready,
    updateHeight1: (TextFieldValue) -> Unit,
    updateHeight2: (TextFieldValue) -> Unit,
    updateWeight: (TextFieldValue) -> Unit,
    updateIsMetric: (Boolean) -> Unit,
    openDrawer: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val mContext = LocalContext.current
    val expressionTransformer = remember(uiState.formatterSymbols) {
        ExpressionTransformer(uiState.formatterSymbols)
    }
    val weightLabel = remember(uiState.isMetric) {
        val s1 = mContext.resources.getString(R.string.body_mass_weight)
        val s2 = if (uiState.isMetric) {
            mContext.resources.getString(R.string.unit_kilogram_short)
        } else {
            mContext.resources.getString(R.string.unit_pound_short)
        }

        "$s1, $s2"
    }

    UnittoScreenWithTopBar(
        title = { Text(stringResource(R.string.body_mass_title)) },
        navigationIcon = { MenuButton(openDrawer) },
        actions = { SettingsButton(navigateToSettings) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SegmentedButtonsRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    label = stringResource(R.string.body_mass_metric),
                    onClick = { updateIsMetric(true) },
                    selected = uiState.isMetric,
                    modifier = Modifier.weight(1f)
                )
                SegmentedButton(
                    label = stringResource(R.string.body_mass_imperial),
                    onClick = { updateIsMetric(false) },
                    selected = !uiState.isMetric,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(32.dp)
                    )
                    .padding(16.dp, 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Crossfade(targetState = uiState.isMetric) { isMetric ->
                    if (isMetric) {
                        BodyMassTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.height1,
                            onValueChange = updateHeight1,
                            label = "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_centimeter_short)}",
                            expressionFormatter = expressionTransformer,
                            imeAction = ImeAction.Next
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            BodyMassTextField(
                                modifier = Modifier.weight(1f),
                                value = uiState.height1,
                                onValueChange = updateHeight1,
                                label = "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_foot_short)}",
                                expressionFormatter = expressionTransformer,
                                imeAction = ImeAction.Next
                            )
                            BodyMassTextField(
                                modifier = Modifier.weight(1f),
                                value = uiState.height2,
                                onValueChange = updateHeight2,
                                label = "${stringResource(R.string.body_mass_height)}, ${stringResource(R.string.unit_inch_short)}",
                                expressionFormatter = expressionTransformer,
                                imeAction = ImeAction.Next
                            )
                        }
                    }
                }
                BodyMassTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.weight,
                    onValueChange = updateWeight,
                    label = weightLabel,
                    expressionFormatter = expressionTransformer,
                    imeAction = ImeAction.Done
                )
            }

            AnimatedContent(
                modifier = Modifier.fillMaxWidth(),
                targetState = uiState.result,
                transitionSpec = {
                    (fadeIn() togetherWith fadeOut()) using SizeTransform(false)
                }
            ) { targetState ->
                if (targetState.isEqualTo(BigDecimal.ZERO)) return@AnimatedContent

                val value = remember(targetState) {
                    targetState
                        .format(3, OutputFormat.PLAIN)
                        .formatExpression(uiState.formatterSymbols)
                }

                val classification = remember(targetState) {
                    getBodyMassData(targetState)
                }

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(classification.color)
                        .padding(16.dp, 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // TODO Link to web

                    Text(
                        text = stringResource(classification.classification),
                        style = MaterialTheme.typography.displaySmall,
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun BodyMassTextField(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    expressionFormatter: VisualTransformation,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val cleanText = it.text
                .replace(",", ".")
                .filter { char ->
                    Token.Digit.allWithDot.contains(char.toString())
                }
            onValueChange(it.copy(cleanText))
        },
        label = { AnimatedContent(label) { Text(it) } },
        singleLine = true,
        visualTransformation = expressionFormatter,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Immutable
private data class BodyMassData(
    val color: Color,
    @StringRes val classification: Int
)

@Stable
private fun getBodyMassData(value: BigDecimal): BodyMassData = when {
    value.isLessThan(BigDecimal("18.5")) -> {
        BodyMassData(
            color = Color(0x800EACDD),
            classification = R.string.body_mass_underweight
        )
    }
    value.isLessThan(BigDecimal("25")) -> {
        BodyMassData(
            color = Color(0x805BF724),
            classification = R.string.body_mass_normal
        )
    }
    value.isLessThan(BigDecimal("30")) -> {
        BodyMassData(
            color = Color(0x80DBEC18),
            classification = R.string.body_mass_overweight
        )
    }
    value.isLessThan(BigDecimal("35")) -> {
        BodyMassData(
            color = Color(0x80FF9634),
            classification = R.string.body_mass_obese_1
        )
    }
    value.isLessThan(BigDecimal("40")) -> {
        BodyMassData(
            color = Color(0x80F85F31),
            classification = R.string.body_mass_obese_2
        )
    }
    else -> {
        BodyMassData(
            color = Color(0x80FF2323),
            classification = R.string.body_mass_obese_3
        )
    }
}
