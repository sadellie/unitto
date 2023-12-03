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

package com.sadellie.unitto.data.backup

import com.sadellie.unitto.data.database.TimeZoneEntity
import com.sadellie.unitto.data.database.UnitsEntity
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

// Have to use this wrapper since entity classes are in database module
@Suppress("UNUSED")
internal class UserDataTableAdapter {
    @ToJson
    fun toJson(unitsEntity: UnitsEntity): UserDataUnit =
        UserDataUnit(
            unitId = unitsEntity.unitId,
            isFavorite = unitsEntity.isFavorite,
            pairedUnitId = unitsEntity.pairedUnitId,
            frequency = unitsEntity.frequency
        )

    @FromJson
    fun fromJson(userDataUnit: UserDataUnit): UnitsEntity =
        UnitsEntity(
            unitId = userDataUnit.unitId,
            isFavorite = userDataUnit.isFavorite,
            pairedUnitId = userDataUnit.pairedUnitId,
            frequency = userDataUnit.frequency
        )

    @ToJson
    fun toJson(timeZoneEntity: TimeZoneEntity): UserDataTimezone =
        UserDataTimezone(
            id = timeZoneEntity.id,
            position = timeZoneEntity.position,
            label = timeZoneEntity.label
        )

    @FromJson
    fun fromJson(userDataTimezone: UserDataTimezone): TimeZoneEntity =
        TimeZoneEntity(
            id = userDataTimezone.id,
            position = userDataTimezone.position,
            label = userDataTimezone.label
        )
}
