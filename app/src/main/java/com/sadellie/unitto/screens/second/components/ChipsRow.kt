package com.sadellie.unitto.screens.second.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.UnitGroup

/**
 * Row of chips with [UnitGroup]s. Temporary solution
 *
 * @param items All [UnitGroup]s
 * @param chosenUnitGroup Currently selected [UnitGroup]
 * @param selectAction Action to perform when a chip is clicked
 * @param lazyListState Used for animated scroll when entering unit selection screen
 */
@Composable
fun ChipsRow(
    items: List<UnitGroup>,
    chosenUnitGroup: UnitGroup?,
    selectAction: (UnitGroup?) -> Unit,
    lazyListState: LazyListState
) {
    val chipShape = RoundedCornerShape(8.dp)
    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            val isSelected: Boolean = item == chosenUnitGroup
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .clip(chipShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                    )
                    // Remove border when selected
                    .border(
                        1.dp,
                        if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                        chipShape
                    )
                    .clickable { selectAction(item) }
                    .padding(
                        start = 8.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = isSelected) {
                    Icon(
                        modifier = Modifier.height(18.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.checked_filter_description)
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = item.res),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
