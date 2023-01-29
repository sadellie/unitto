package com.sadellie.unitto.feature.tools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Default.Schedule,
                            null // TODO describe
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