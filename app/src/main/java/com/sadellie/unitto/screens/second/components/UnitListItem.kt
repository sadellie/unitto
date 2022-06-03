package com.sadellie.unitto.screens.second.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit

/**
 * Represents one list item. Once clicked will navigate up
 *
 * @param modifier Modifier that will be applied to a box that surrounds row.
 * @param changeAction Function to change current unit. Called when choosing unit
 * @param favoriteAction Function to mark unit as favorite. It's a toggle
 * @param item The unit itself
 * @param isSelected Whether this unit is selected or not (current pair of unit)
 * @param convertValue Used for right side units. Shows conversion from unit on the left
 */
@Composable
fun UnitListItem(
    modifier: Modifier,
    changeAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
    item: AbstractUnit,
    isSelected: Boolean,
    convertValue: (AbstractUnit) -> String
) {
    var isFavorite: Boolean by rememberSaveable { mutableStateOf(item.isFavorite) }
    var convertedValue: String by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(), // You can also change the color and radius of the ripple
                onClick = {
                    changeAction(item)
                }
            )
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                    RoundedCornerShape(24.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = item.displayName),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = convertedValue + stringResource(id = item.shortName),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { favoriteAction(item); isFavorite = !isFavorite }) {
                AnimatedContent(
                    targetState = isFavorite,
                    transitionSpec = {
                        (scaleIn() with scaleOut()).using(SizeTransform(clip = false))
                    }
                ) {
                    Icon(
                        if (item.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.favorite_button_description)
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        // Converting value
        convertedValue = convertValue(item)
    }
}
