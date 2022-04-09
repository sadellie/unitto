package com.sadellie.unitto.screens.second.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R


/**
 * Placeholder that can be seen when there are no units found
 *
 */
@Composable
fun SearchPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Big icon in the middle
        Icon(
            Icons.Default.SearchOff,
            contentDescription = stringResource(id = R.string.empty_search_result_description),
            modifier = Modifier.size(48.dp)
        )
        // Primary text
        Text(
            text = stringResource(id = R.string.search_placeholder),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        // Secondary text with tips
        Text(
            text = stringResource(id = R.string.search_placeholder_secondary),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
