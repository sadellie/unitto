package com.sadellie.unitto.screens.setttings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R


/**
 * Alert dialog that has a list of options in it
 *
 * @param title Dialog title
 * @param listItems List of options
 * @param selectedItemIndex Currently selected item index
 * @param selectAction Action to perform when clicking an option
 * @param dismissAction Action to perform when clicking "cancel"
 * @param supportText Text above list of options
 */
@Composable
fun AlertDialogWithList(
    title: String,
    listItems: Map<Int, Int> = emptyMap(),
    selectedItemIndex: Int = 0,
    selectAction: (Int) -> Unit = {},
    dismissAction: () -> Unit,
    supportText: String? = null,
    dismissButtonLabel: String = stringResource(id = R.string.cancel_label)
) {
    AlertDialog(
        onDismissRequest = dismissAction,
        title = {
            Text(text = title)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                supportText?.let {
                    Text(supportText)
                }
                LazyColumn {
                    items(listItems.toList()) {
                        CustomDialogContentListItem(
                            label = stringResource(it.second),
                            selected = selectedItemIndex == it.first,
                            onClick = {
                                selectAction(it.first)
                                dismissAction()
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = dismissAction) {
                Text(text = dismissButtonLabel, color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}


/**
 * An item that represents one option item
 *
 * @param label Option label
 * @param selected Whether this option is selected
 * @param onClick Action to perform when this item is clicked
 */
@Composable
private fun CustomDialogContentListItem(
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = label
        )
    }
}
