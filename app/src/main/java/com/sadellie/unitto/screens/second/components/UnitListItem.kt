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
 * Represents one list item. Once clicked will navigate up.
 *
 * @param unit The unit itself.
 * @param isSelected Whether this unit is selected or not (current pair of unit).
 * @param selectAction Function to change current unit. Called when choosing unit.
 * @param favoriteAction Function to mark unit as favorite. It's a toggle.
 * @param shortNameLabel String on the second line.
 */
@Composable
private fun BasicUnitListItem(
    unit: AbstractUnit,
    isSelected: Boolean,
    selectAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
    shortNameLabel: String
) {
    var isFavorite: Boolean by rememberSaveable { mutableStateOf(unit.isFavorite) }
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(), // You can also change the color and radius of the ripple
                onClick = { selectAction(unit) }
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
                    text = stringResource(id = unit.displayName),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = shortNameLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { favoriteAction(unit); isFavorite = !isFavorite }) {
                AnimatedContent(
                    targetState = isFavorite,
                    transitionSpec = {
                        (scaleIn() with scaleOut()).using(SizeTransform(clip = false))
                    }
                ) {
                    Icon(
                        if (unit.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.favorite_button_description)
                    )
                }
            }
        }
    }
}

/**
 * Represents one list item. Once clicked will navigate up. Has without conversion.
 *
 * @param unit The unit itself.
 * @param isSelected Whether this unit is selected or not (current pair of unit).
 * @param selectAction Function to change current unit. Called when choosing unit.
 * @param favoriteAction Function to mark unit as favorite. It's a toggle.
 */
@Composable
fun UnitListItem(
    unit: AbstractUnit,
    isSelected: Boolean,
    selectAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
) = BasicUnitListItem(
    unit = unit,
    isSelected = isSelected,
    selectAction = selectAction,
    favoriteAction = favoriteAction,
    shortNameLabel = stringResource(id = unit.shortName)
)

/**
 * Represents one list item. Once clicked will navigate up. Has with conversion.
 *
 * @param unit The unit itself.
 * @param isSelected Whether this unit is selected or not (current pair of unit).
 * @param selectAction Function to change current unit. Called when choosing unit.
 * @param favoriteAction Function to mark unit as favorite. It's a toggle.
 * @param convertValue Function to call that will convert this unit.
 */
@Composable
fun UnitListItem(
    unit: AbstractUnit,
    isSelected: Boolean,
    selectAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
    convertValue: (AbstractUnit) -> String
) = BasicUnitListItem(
    unit = unit,
    isSelected = isSelected,
    selectAction = selectAction,
    favoriteAction = favoriteAction,
    shortNameLabel = "${convertValue(unit)} ${stringResource(id = unit.shortName)}"
)
