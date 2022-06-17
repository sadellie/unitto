package com.sadellie.unitto.data.units

import androidx.annotation.StringRes
import com.sadellie.unitto.R

val ALL_UNIT_GROUPS: List<UnitGroup> by lazy {
    UnitGroup.values().toList()
}

/**
 * As not all measurements can be converted between each other, we separate them into groups.
 * Within one group all measurements can be converted
 */
enum class UnitGroup(
    @StringRes val res: Int,
    val canNegate: Boolean = false
) {
    LENGTH(res = R.string.length),
    CURRENCY(res = R.string.currency),
    MASS(res = R.string.mass),
    SPEED(res = R.string.speed),
    TEMPERATURE(res = R.string.temperature, canNegate = true),
    AREA(res = R.string.area),
    TIME(res = R.string.time),
    VOLUME(res = R.string.volume),
    DATA(res = R.string.data),
    PRESSURE(res = R.string.pressure),
    ACCELERATION(res = R.string.acceleration),
    ENERGY(res = R.string.energy),
    POWER(res = R.string.power),
    ANGLE(res = R.string.angle),
    DATA_TRANSFER(res = R.string.data_transfer)
}
