package com.sadellie.unitto.screens.setttings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * Represents one item in list on Settings screen
 *
 * @param modifier Modifier that is applied to a [Row]
 * @param onClick Action to perform when clicking this item
 * @param label Big text that is above support text
 * @param supportText Smaller text that is below label
 */
@Composable
fun SettingsListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    supportText: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            Modifier.padding(horizontal = 0.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = label
            )
            supportText?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
