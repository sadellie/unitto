/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import com.sadellie.unitto.core.common.Config
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.icons.symbols.Info
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Translate
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.listedShapes
import com.sadellie.unitto.core.ui.rememberLinkOpener
import com.sadellie.unitto.feature.settings.components.AnnoyingBox
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_ok
import unitto.core.common.generated.resources.settings_language
import unitto.core.common.generated.resources.settings_language_llm
import unitto.core.common.generated.resources.settings_language_llm_support
import unitto.core.common.generated.resources.settings_translate_app
import unitto.core.common.generated.resources.settings_translate_app_support

@Composable
internal fun LanguageRoute(navigateUp: () -> Unit) {
  LanguageScreen(navigateUp = navigateUp)
}

@Composable
private fun LanguageScreen(navigateUp: () -> Unit) {
  val currentLangKey = remember { AppCompatDelegate.getApplicationLocales().toLanguageTags() }

  fun changeLanguage(langKey: String) {
    val selectedLocale =
      if (langKey == "") {
        LocaleListCompat.getEmptyLocaleList()
      } else {
        LocaleListCompat.forLanguageTags(langKey)
      }

    AppCompatDelegate.setApplicationLocales(selectedLocale)
    navigateUp()
  }

  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.settings_language),
    navigationIcon = { NavigateUpButton(navigateUp) },
  ) { padding ->
    val linkOpener = rememberLinkOpener()
    var showExperimentalLanguagesWarning by rememberSaveable { mutableStateOf(true) }
    LazyColumn(
      modifier = Modifier.consumeWindowInsets(padding).padding(horizontal = Sizes.large),
      contentPadding = padding,
      verticalArrangement = ListItemDefaults.ListArrangement,
    ) {
      item(key = "translate", contentType = ContentType.ANNOYING_BOX) {
        AnnoyingBox(
          modifier = Modifier.padding(vertical = Sizes.small).fillMaxWidth(),
          onClick = { linkOpener.launch(Config.TRANSLATE_LINK) },
          imageVector = Symbols.Translate,
          imageVectorContentDescription = stringResource(Res.string.settings_translate_app),
          title = stringResource(Res.string.settings_translate_app),
          support = stringResource(Res.string.settings_translate_app_support),
        )
      }

      languages.forEachIndexed { index, (key, res) ->
        item(key = key, contentType = ContentType.ITEM) {
          ListItemExpressive(
            onClick = { changeLanguage(key) },
            content = { Text(stringResource(res)) },
            leadingContent = {
              RadioButton(selected = currentLangKey == key, onClick = { changeLanguage(key) })
            },
            shapes = ListItemDefaults.listedShapes(index, languages.size),
          )
        }
      }
    }

    if (showExperimentalLanguagesWarning) {
      AlertDialog(
        icon = {
          Icon(Symbols.Info, null)
        },
        onDismissRequest = { showExperimentalLanguagesWarning = false },
        title = { Text(stringResource(Res.string.settings_language_llm)) },
        text = { Text(stringResource(Res.string.settings_language_llm_support)) },
        confirmButton = {
          TextButton(onClick = { showExperimentalLanguagesWarning = false }) {
            Text(stringResource(Res.string.common_ok))
          }
        },
      )
    }
  }
}

private enum class ContentType {
  ANNOYING_BOX,
  ITEM,
}

@Preview
@Composable
fun LanguageScreenPreview() = ExpressivePreview { LanguageScreen(navigateUp = {}) }
