package com.sadellie.unitto.data.units

import com.sadellie.unitto.data.units.collections.ACCELERATION_COLLECTION
import com.sadellie.unitto.data.units.collections.ANGLE_COLLECTION
import com.sadellie.unitto.data.units.collections.AREA_COLLECTION
import com.sadellie.unitto.data.units.collections.CURRENCY_COLLECTION
import com.sadellie.unitto.data.units.collections.DATA_COLLECTION
import com.sadellie.unitto.data.units.collections.DATA_TRANSFER_COLLECTION
import com.sadellie.unitto.data.units.collections.ENERGY_COLLECTION
import com.sadellie.unitto.data.units.collections.LENGTH_COLLECTION
import com.sadellie.unitto.data.units.collections.MASS_COLLECTION
import com.sadellie.unitto.data.units.collections.POWER_COLLECTION
import com.sadellie.unitto.data.units.collections.PRESSURE_COLLECTION
import com.sadellie.unitto.data.units.collections.SPEED_COLLECTION
import com.sadellie.unitto.data.units.collections.TEMPERATURE_COLLECTION
import com.sadellie.unitto.data.units.collections.TIME_COLLECTION
import com.sadellie.unitto.data.units.collections.VOLUME_COLLECTION

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
            PRESSURE_COLLECTION +
            ACCELERATION_COLLECTION +
            ENERGY_COLLECTION +
            POWER_COLLECTION +
            ANGLE_COLLECTION +
            DATA_TRANSFER_COLLECTION
}
