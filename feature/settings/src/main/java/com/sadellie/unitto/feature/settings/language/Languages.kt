/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import com.sadellie.unitto.core.base.R

internal val languages by lazy {
    mapOf(
        "" to R.string.system,
        "en" to R.string.locale_en,
        "en-GB" to R.string.locale_en_rGB,
        "de" to R.string.locale_de,
        "es" to R.string.locale_es,
        "fr" to R.string.locale_fr,
        "hu" to R.string.locale_hu,
        "id" to R.string.locale_in,
        "it" to R.string.locale_it,
        "nl" to R.string.locale_nl,
        "ru" to R.string.locale_ru,
        "tr" to R.string.locale_tr,
    )
}
