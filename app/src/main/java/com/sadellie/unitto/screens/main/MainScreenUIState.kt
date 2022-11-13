/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.main

import com.sadellie.unitto.data.KEY_0

/**
 * Represents current state of the MainScreen
 *
 * @property inputValue Current input value. Can be expression or a simple number.
 * @property resultValue Current output value
 * @property calculatedValue Currently calculated value. Can be null if not needed (same as input or
 * expression in input is invalid)
 * @property deleteButtonEnabled Delete last symbol from input button state
 * @property dotButtonEnabled Add dot to input button state
 * @property negateButtonEnabled Switch input between positive and negative button state
 * @property isLoadingDatabase Whether we are loading data from Database. Need on app launch, once
 * we are done loading list on Units list can be sorted by usage properly/
 * @property isLoadingNetwork Whether we are loading data from network
 * @property showError Whether there was an error while loading data from network
 */
data class MainScreenUIState(
    var inputValue: String = KEY_0,
    var resultValue: String = KEY_0,
    var deleteButtonEnabled: Boolean = false,
    var dotButtonEnabled: Boolean = true,
    var negateButtonEnabled: Boolean = false,
    var isLoadingDatabase: Boolean = true,
    var isLoadingNetwork: Boolean = false,
    var showError: Boolean = false,
    var calculatedValue: String? = null
)
