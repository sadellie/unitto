package com.sadellie.unitto.feature.tools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.UnittoLargeTopAppBar

@Composable
internal fun ToolsScreen(
    navigateUpAction: () -> Unit,
    navigateToEpoch: () -> Unit
) {
    UnittoLargeTopAppBar(
        title = stringResource(R.string.tools_screen),
        navigateUpAction = navigateUpAction
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Card(
                    Modifier
                        .clickable(onClick = {})
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.tools_notice_title),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(R.string.tools_notice_description),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = stringResource(R.string.click_to_read_more),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
            item {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Schedule,
                            stringResource(R.string.epoch_converter)
                        )
                    },
                    headlineText = { Text(stringResource(R.string.epoch_converter)) },
                    supportingText = { Text(stringResource(R.string.epoch_converter_support)) },
                    modifier = Modifier.clickable { navigateToEpoch() }
                )
            }
        }
    }
}

@Preview
@Composable
internal fun PreviewToolsScreen() {
    ToolsScreen(
        navigateUpAction = {},
        navigateToEpoch = {}
    )
}