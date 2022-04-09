package com.sadellie.unitto.data.units

import com.sadellie.unitto.data.units.collections.*

/**
 * This is a collection of all available units
 */
val ALL_UNITS: List<AbstractUnit> by lazy {
    LENGTH_COLLECTION +
            CURRENCY_COLLECTION +
            MASS_COLLECTION +
            TIME_COLLECTION +
            TEMPERATURE_COLLECTION +
            SPEED_COLLECTION +
            AREA_COLLECTION +
            VOLUME_COLLECTION +
            DATA_COLLECTION +
            ENERGY_COLLECTION +
            POWER_COLLECTION +
            ANGLE_COLLECTION +
            DATA_TRANSFER_COLLECTION
}
