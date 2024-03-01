/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.feature.graphing.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Delete
import com.sadellie.unitto.core.designsystem.icons.symbols.Edit
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.feature.graphing.GraphFunction
import com.sadellie.unitto.feature.graphing.R
import com.sadellie.unitto.feature.graphing.graphLineColors

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun GraphingFunctionsList(
  modifier: Modifier,
  functions: List<GraphFunction>,
  onRemove: (removedFunction: GraphFunction) -> Unit,
  onCreateFunction: () -> Unit,
  onEditFunction: (GraphFunction) -> Unit,
) {
  Scaffold(
    modifier = modifier,
    floatingActionButton = {
      FloatingActionButton(onClick = onCreateFunction) {
        Icon(
          imageVector = Symbols.Add,
          contentDescription = null,
          modifier = Modifier.size(FloatingActionButtonDefaults.MediumIconSize),
        )
      }
    },
    contentWindowInsets = WindowInsets(0),
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
      HorizontalDivider()

      if (functions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
          Text(
            text = stringResource(R.string.graphing_functions_list_placeholder),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
          )
        }
        return@Column
      }

      var selectedFunctionId by remember { mutableStateOf<Int?>(null) }
      LazyColumn {
        items(functions, GraphFunction::id) { function ->
          val isExpanded = selectedFunctionId == function.id
          GraphingFunctionListItem(
            modifier =
              Modifier.animateItem()
                .clickable { selectedFunctionId = if (isExpanded) null else function.id }
                .fillMaxWidth(),
            graphFunction = function,
            isExpanded = isExpanded,
            onEdit = { onEditFunction(function) },
            onDelete = { onRemove(function) },
          )
        }
      }
    }
  }
}

@Composable
private fun GraphingFunctionListItem(
  modifier: Modifier,
  graphFunction: GraphFunction,
  isExpanded: Boolean,
  onDelete: () -> Unit,
  onEdit: () -> Unit,
) {
  val transition = updateTransition(isExpanded, label = "Expanded transition")
  val backgroundColor =
    transition.animateColor(label = "Background color") {
      if (it) MaterialTheme.colorScheme.surfaceContainerHigh
      else MaterialTheme.colorScheme.background
    }
  val contentColor =
    transition.animateColor(label = "Background color") {
      if (it) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground
    }
  val cornerRadius = transition.animateDp(label = "Corner radius") { if (it) 8.dp else 4.dp }
  val outerPadding = transition.animateDp(label = "Outer padding") { if (it) 8.dp else 0.dp }

  Column(
    modifier =
      Modifier.padding(outerPadding.value)
        .clip(RoundedCornerShape(cornerRadius.value))
        .background(backgroundColor.value)
        .then(modifier)
  ) {
    Row(
      modifier = Modifier.padding(12.dp).height(IntrinsicSize.Max),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Box(
        Modifier.background(graphFunction.color, CircleShape)
          .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
          .aspectRatio(1f)
          .fillMaxSize()
      )

      Text(
        text = graphFunction.expression,
        style = MaterialTheme.typography.bodyLarge,
        color = contentColor.value,
      )
    }

    AnimatedVisibility(visible = isExpanded) {
      Column(modifier = Modifier.padding(bottom = 12.dp)) {
        FunctionOption(
          title = stringResource(R.string.common_edit),
          icon = Symbols.Edit,
          onClick = onEdit,
          enabled = !transition.isRunning,
        )
        FunctionOption(
          title = stringResource(R.string.common_delete),
          icon = Symbols.Delete,
          onClick = onDelete,
          enabled = !transition.isRunning,
        )
      }
    }
  }
}

@Composable
private fun FunctionOption(
  title: String,
  icon: ImageVector,
  onClick: () -> Unit,
  enabled: Boolean,
) {
  Row(
    modifier = Modifier.clickable(enabled) { onClick() }.fillMaxWidth().padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Icon(
      imageVector = icon,
      contentDescription = title,
      tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(text = title, color = MaterialTheme.colorScheme.onSurfaceVariant)
  }
}

@Composable
@Preview
private fun GraphingFunctionsListEmptyPreview() {
  Box(Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize()) {
    var functions by remember { mutableStateOf(emptyList<GraphFunction>()) }

    GraphingFunctionsList(
      modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).align(Alignment.BottomCenter),
      functions = functions,
      onRemove = { removedItem -> functions = functions.filter { it.id != removedItem.id } },
      onEditFunction = {},
      onCreateFunction = {},
    )
  }
}

@Composable
@Preview
private fun GraphingFunctionsListPreview() {
  Box(Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize()) {
    var functions by remember {
      mutableStateOf(List(3) { GraphFunction(it, "$it", graphLineColors[it]) })
    }

    GraphingFunctionsList(
      modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).align(Alignment.BottomCenter),
      functions = functions,
      onRemove = { removedItem -> functions = functions.filter { it.id != removedItem.id } },
      onEditFunction = {},
      onCreateFunction = {},
    )
  }
}

@Composable
@Preview
private fun GraphingFunctionListItemPreview() {
  var isExpanded by remember { mutableStateOf(false) }

  GraphingFunctionListItem(
    modifier = Modifier.clickable { isExpanded = !isExpanded }.fillMaxWidth(),
    graphFunction = GraphFunction(0, "123+456+cos(x)", graphLineColors.first()),
    isExpanded = isExpanded,
    onEdit = {},
    onDelete = {},
  )
}
