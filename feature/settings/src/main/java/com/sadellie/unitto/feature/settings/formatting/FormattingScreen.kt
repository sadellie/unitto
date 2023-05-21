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

package com.sadellie.unitto.feature.settings.formatting

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.EMobiledata
import androidx.compose.material.icons.filled._123
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.OUTPUT_FORMAT
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.SEPARATORS
import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.UnittoSlider
import com.sadellie.unitto.core.ui.common.SegmentedButton
import com.sadellie.unitto.core.ui.common.SegmentedButtonsRow
import com.sadellie.unitto.core.ui.common.UnittoScreenWithLargeTopBar
import com.sadellie.unitto.core.ui.common.squashable
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium
import kotlin.math.roundToInt

@Composable
fun FormattingRoute(
    viewModel: FormattingViewModel = hiltViewModel(),
    navigateUpAction: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    FormattingScreen(
        navigateUpAction = navigateUpAction,
        uiState = uiState.value,
        onPrecisionChange = viewModel::updatePrecision,
        onSeparatorChange = viewModel::updateSeparator,
        onOutputFormatChange = viewModel::updateOutputFormat,
        togglePreview = viewModel::togglePreview
    )
}

@Composable
fun FormattingScreen(
    navigateUpAction: () -> Unit,
    uiState: FormattingUIState,
    onPrecisionChange: (Int) -> Unit,
    onSeparatorChange: (Int) -> Unit,
    onOutputFormatChange: (Int) -> Unit,
    togglePreview: () -> Unit,
    precisions: ClosedFloatingPointRange<Float> = 0f..16f, // 16th is a MAX_PRECISION (1000)
) {
    UnittoScreenWithLargeTopBar(
        title = stringResource(R.string.formatting_settings_group),
        navigationIcon = { NavigateUpButton(navigateUpAction) },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            item("preview") {
                Column(
                    Modifier
                        .padding(16.dp)
                        .squashable(
                            onClick = togglePreview,
                            cornerRadiusRange = 8.dp..32.dp,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Preview (click to switch)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = uiState.preview,
                        style = NumbersTextStyleDisplayMedium,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            item("precision_label") {
                ListItem(
                    leadingContent = {
                        Icon(Icons.Default.Architecture, stringResource(R.string.precision_setting))
                    },
                    headlineContent = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.precision_setting))
                            Text(if (uiState.precision >= precisions.endInclusive) stringResource(R.string.max_precision) else uiState.precision.toString())
                        }
                    },
                    supportingContent = {
                        Text(stringResource(R.string.precision_setting_support))
                    }
                )
            }

            item("precision_slider") {
                UnittoSlider(
                    modifier = Modifier.padding(start = 56.dp, end = 16.dp),
                    value = uiState.precision.toFloat(),
                    valueRange = precisions,
                    onValueChange = { onPrecisionChange(it.roundToInt()) },
                )
            }

            item("separator_label") {
                ListItem(
                    leadingContent = {
                        Icon(Icons.Default._123, stringResource(R.string.precision_setting))
                    },
                    headlineContent = { Text(stringResource(R.string.separator_setting)) },
                    supportingContent = { Text(stringResource(R.string.separator_setting_support)) },
                )
            }

            item("separator") {
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .wrapContentWidth()
                        .padding(start = 56.dp)
                ) {
                    SegmentedButtonsRow {
                        SEPARATORS.forEach { (separator, stringRes) ->
                            SegmentedButton(
                                label = stringResource(stringRes),
                                onClick = { onSeparatorChange(separator) },
                                selected = separator == uiState.separator
                            )
                        }
                    }
                }
            }

            item("output_format_label") {
                ListItem(
                    leadingContent = {
                        Icon(Icons.Default.EMobiledata, stringResource(R.string.precision_setting))
                    },
                    headlineContent = { Text(stringResource(R.string.output_format_setting)) },
                    supportingContent = { Text(stringResource(R.string.output_format_setting_support)) }
                )
            }

            item("output_format") {
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .wrapContentWidth()
                        .padding(start = 56.dp)
                ) {
                    SegmentedButtonsRow {
                        OUTPUT_FORMAT.forEach { (outputFormat, stringRes) ->
                            SegmentedButton(
                                label = stringResource(stringRes),
                                onClick = { onOutputFormatChange(outputFormat) },
                                selected = outputFormat == uiState.outputFormat
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFormattingScreen() {
    var currentPrecision by remember { mutableStateOf(6) }
    var currentSeparator by remember { mutableStateOf(Separator.COMMA) }
    var currentOutputFormat by remember { mutableStateOf(OutputFormat.PLAIN) }

    FormattingScreen(
        uiState = FormattingUIState(
            preview = "",
            precision = 3,
            separator = Separator.SPACES,
            outputFormat = OutputFormat.PLAIN
        ),
        onPrecisionChange = { currentPrecision = it },
        onSeparatorChange = { currentSeparator = it },
        onOutputFormatChange = { currentOutputFormat = it },
        navigateUpAction = {},
        togglePreview = {}
    )
}
