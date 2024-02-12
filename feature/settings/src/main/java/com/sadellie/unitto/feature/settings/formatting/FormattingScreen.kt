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

package com.sadellie.unitto.feature.settings.formatting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.EMobiledata
import androidx.compose.material.icons.filled._123
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.ListItem
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.PagedIsland
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.common.SegmentedButton
import com.sadellie.unitto.core.ui.common.SegmentedButtonsRow
import com.sadellie.unitto.core.ui.common.Slider
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.core.ui.theme.LocalNumberTypography
import com.sadellie.unitto.data.common.format
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun FormattingRoute(
    viewModel: FormattingViewModel = hiltViewModel(),
    navigateUpAction: () -> Unit,
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        null -> EmptyScreen()
        else -> {
            FormattingScreen(
                navigateUpAction = navigateUpAction,
                uiState = uiState,
                onPrecisionChange = viewModel::updatePrecision,
                updateFormatterSymbols = viewModel::updateFormatterSymbols,
                onOutputFormatChange = viewModel::updateOutputFormat,
            )
        }
    }
}

@Composable
fun FormattingScreen(
    navigateUpAction: () -> Unit,
    uiState: FormattingUIState,
    onPrecisionChange: (Int) -> Unit,
    updateFormatterSymbols: (grouping: String, fractional: String) -> Unit,
    onOutputFormatChange: (Int) -> Unit,
    precisions: ClosedFloatingPointRange<Float> = 0f..16f, // 16th is a MAX_PRECISION (1000)
) {
    val resources = LocalContext.current.resources

    val precisionText: String by remember(uiState.precision, uiState.formatterSymbols) {
        derivedStateOf {
            return@derivedStateOf if (uiState.precision >= precisions.endInclusive) {
                resources.getString(
                    R.string.settings_precision_max,
                    MAX_PRECISION.toString().formatExpression(uiState.formatterSymbols)
                )
            } else {
                uiState.precision.toString()
            }
        }
    }

    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.settings_formatting),
        navigationIcon = { NavigateUpButton(navigateUpAction) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            PagedIsland(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                pagerState = rememberPagerState { 2 },
            ) { currentPage ->
                val preview = when (currentPage) {
                    0 -> "123456.${"789123456".repeat(ceil(uiState.precision.toDouble() / 9.0).toInt())}"
                    1 -> "0.${"1".padStart(uiState.precision, '0')}"
                    else -> ""
                }
                    .toBigDecimalOrNull()
                    ?.format(uiState.precision, uiState.outputFormat)
                    ?.formatExpression(uiState.formatterSymbols)
                    ?: ""

                Text(
                    text = preview,
                    style = LocalNumberTypography.current.displayMedium,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Default.Architecture,
                        stringResource(R.string.settings_precision)
                    )
                },
                headlineContent = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.settings_precision))
                        Text(precisionText)
                    }
                },
                supportingContent = {
                    Text(stringResource(R.string.settings_precision_support))
                }
            )

            Slider(
                modifier = Modifier.padding(start = 56.dp, end = 16.dp),
                value = uiState.precision.toFloat(),
                valueRange = precisions,
                onValueChange = { onPrecisionChange(it.roundToInt()) },
            )

            ListItem(
                leadingContent = {
                    Icon(Icons.Default._123, stringResource(R.string.settings_thousands_separator))
                },
                headlineContent = { Text(stringResource(R.string.settings_thousands_separator)) },
            )

            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentWidth()
                    .padding(start = 56.dp)
            ) {
                SegmentedButtonsRow {
                    SegmentedButton(
                        label = stringResource(R.string.settings_space),
                        onClick = { updateFormatterSymbols(Token.SPACE, uiState.formatterSymbols.fractional) },
                        selected = uiState.formatterSymbols.grouping == Token.SPACE
                    )
                    SegmentedButton(
                        label = stringResource(R.string.settings_period),
                        onClick = { updateFormatterSymbols(Token.PERIOD, Token.COMMA) },
                        selected = uiState.formatterSymbols.grouping == Token.PERIOD,
                    )
                    SegmentedButton(
                        label = stringResource(R.string.comma),
                        onClick = { updateFormatterSymbols(Token.COMMA, Token.PERIOD) },
                        selected = uiState.formatterSymbols.grouping == Token.COMMA,
                    )
                }
            }

            AnimatedVisibility(
                visible = uiState.formatterSymbols.grouping == Token.SPACE,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(start = 40.dp)
                ) {
                    ListItem(
                        modifier = Modifier,
                        headlineContent = { Text(stringResource(R.string.settings_decimal_separator)) },
                    )
                    Row(
                        Modifier
                            .horizontalScroll(rememberScrollState())
                            .wrapContentWidth()
                            .padding(start = 16.dp)
                    ) {
                        SegmentedButtonsRow {
                            SegmentedButton(
                                label = stringResource(R.string.settings_period),
                                onClick = { updateFormatterSymbols(Token.SPACE, Token.PERIOD) },
                                selected = uiState.formatterSymbols.fractional == Token.PERIOD,
                            )
                            SegmentedButton(
                                label = stringResource(R.string.comma),
                                onClick = { updateFormatterSymbols(Token.SPACE, Token.COMMA) },
                                selected = uiState.formatterSymbols.fractional == Token.COMMA,
                            )
                        }
                    }
                }
            }

            ListItem(
                leadingContent = {
                    Icon(Icons.Default.EMobiledata, stringResource(R.string.settings_precision))
                },
                headlineContent = { Text(stringResource(R.string.settings_exponential_notation)) },
                supportingContent = { Text(stringResource(R.string.settings_exponential_notation_support)) }
            )

            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentWidth()
                    .padding(start = 56.dp)
            ) {
                SegmentedButtonsRow {
                    SegmentedButton(
                        label = stringResource(R.string.settings_auto),
                        onClick = { onOutputFormatChange(OutputFormat.ALLOW_ENGINEERING) },
                        selected = OutputFormat.ALLOW_ENGINEERING == uiState.outputFormat
                    )
                    SegmentedButton(
                        label = stringResource(R.string.enabled_label),
                        onClick = { onOutputFormatChange(OutputFormat.FORCE_ENGINEERING) },
                        selected = OutputFormat.FORCE_ENGINEERING == uiState.outputFormat
                    )
                    SegmentedButton(
                        label = stringResource(R.string.disabled_label),
                        onClick = { onOutputFormatChange(OutputFormat.PLAIN) },
                        selected = OutputFormat.PLAIN == uiState.outputFormat
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFormattingScreen() {
    var currentPrecision by remember { mutableIntStateOf(6) }
    var currentFormatterSymbols by remember { mutableStateOf(FormatterSymbols(Token.SPACE, Token.PERIOD)) }
    var currentOutputFormat by remember { mutableIntStateOf(OutputFormat.PLAIN) }

    FormattingScreen(
        uiState = FormattingUIState(
            precision = 16,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = currentFormatterSymbols
        ),
        onPrecisionChange = { currentPrecision = it },
        updateFormatterSymbols = updateFormatterSymbols@{ grouping, fractional ->
            currentFormatterSymbols = FormatterSymbols(grouping, fractional)
        },
        onOutputFormatChange = { currentOutputFormat = it },
        navigateUpAction = {},
    )
}
