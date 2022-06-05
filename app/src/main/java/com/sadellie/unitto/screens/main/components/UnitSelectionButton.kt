package com.sadellie.unitto.screens.main.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R


/**
 * Button to select a unit
 *
 * @param modifier Modifier that is applied to a [Button]
 * @param onClick Function to call when button is clicked (navigate to a unit selection screen)
 * @param label Text on button
 * @param isLoading Show "Loading" text and disable button
 */
@Composable
fun UnitSelectionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: Int,
    isLoading: Boolean
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
    ) {
        AnimatedContent(
            targetState = label,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) {
            Text(
                text = stringResource(if (isLoading) R.string.loading_label else label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
