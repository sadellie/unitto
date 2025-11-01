/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.locale_de
import unitto.core.common.generated.resources.locale_en
import unitto.core.common.generated.resources.locale_en_rGB
import unitto.core.common.generated.resources.locale_es
import unitto.core.common.generated.resources.locale_fr
import unitto.core.common.generated.resources.locale_hu
import unitto.core.common.generated.resources.locale_in
import unitto.core.common.generated.resources.locale_it
import unitto.core.common.generated.resources.locale_nl
import unitto.core.common.generated.resources.locale_pt_rBR
import unitto.core.common.generated.resources.locale_ru
import unitto.core.common.generated.resources.locale_tr
import unitto.core.common.generated.resources.locale_zh_rCN
import unitto.core.common.generated.resources.settings_system

internal val languages by lazy {
  listOf(
    "" to Res.string.settings_system,
    "en" to Res.string.locale_en,
    "en-GB" to Res.string.locale_en_rGB,
    "de" to Res.string.locale_de,
    "es" to Res.string.locale_es,
    "fr" to Res.string.locale_fr,
    "hu" to Res.string.locale_hu,
    "id" to Res.string.locale_in,
    "it" to Res.string.locale_it,
    "nl" to Res.string.locale_nl,
    "pt-BR" to Res.string.locale_pt_rBR,
    "ru" to Res.string.locale_ru,
    "tr" to Res.string.locale_tr,
    "zh-CN" to Res.string.locale_zh_rCN,
  )
}
