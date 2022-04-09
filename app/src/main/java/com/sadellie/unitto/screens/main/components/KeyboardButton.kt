package com.sadellie.unitto.screens.main.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.ui.theme.NumbersTextStyleTitleLarge


/**
 * Button for keyboard
 *
 * @param modifier Modifier that is applied to a [Button] component
 * @param digit Symbol to show on button
 * @param enabled Current state of this button
 * @param onClick Action to perform when clicking this button
 */
@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    digit: String,
    enabled: Boolean = true,
    onClick: (String) -> Unit = {},
) {
    Button(
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = { onClick(digit) },
        enabled = enabled,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = digit,
            style = NumbersTextStyleTitleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
