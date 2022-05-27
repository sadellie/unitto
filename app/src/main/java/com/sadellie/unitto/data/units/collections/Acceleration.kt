package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import java.math.BigDecimal

val ACCELERATION_COLLECTION: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attometer_per_square_second,  BigDecimal.valueOf(1),           UnitGroup.ACCELERATION, R.string.attometer_per_square_second,  R.string.attometer_per_square_second_short),
        MyUnit(MyUnitIDS.femtometer_per_square_second, BigDecimal.valueOf(1E+3),        UnitGroup.ACCELERATION, R.string.femtometer_per_square_second, R.string.femtometer_per_square_second_short),
        MyUnit(MyUnitIDS.picometer_per_square_second,  BigDecimal.valueOf(1E+6),        UnitGroup.ACCELERATION, R.string.picometer_per_square_second,  R.string.picometer_per_square_second_short),
        MyUnit(MyUnitIDS.nanometer_per_square_second,  BigDecimal.valueOf(1E+9),        UnitGroup.ACCELERATION, R.string.nanometer_per_square_second,  R.string.nanometer_per_square_second_short),
        MyUnit(MyUnitIDS.micrometer_per_square_second, BigDecimal.valueOf(1E+12),       UnitGroup.ACCELERATION, R.string.micrometer_per_square_second, R.string.micrometer_per_square_second_short),
        MyUnit(MyUnitIDS.millimeter_per_square_second, BigDecimal.valueOf(1E+15),       UnitGroup.ACCELERATION, R.string.millimeter_per_square_second, R.string.millimeter_per_square_second_short),
        MyUnit(MyUnitIDS.centimeter_per_square_second, BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.centimeter_per_square_second, R.string.centimeter_per_square_second_short),
        MyUnit(MyUnitIDS.decimeter_per_square_second,  BigDecimal.valueOf(1E+17),       UnitGroup.ACCELERATION, R.string.decimeter_per_square_second,  R.string.decimeter_per_square_second_short),
        MyUnit(MyUnitIDS.meter_per_square_second,      BigDecimal.valueOf(1E+18),       UnitGroup.ACCELERATION, R.string.meter_per_square_second,      R.string.meter_per_square_second_short),
        MyUnit(MyUnitIDS.kilometer_per_square_second,  BigDecimal.valueOf(1E+21),       UnitGroup.ACCELERATION, R.string.kilometer_per_square_second,  R.string.kilometer_per_square_second_short),
        MyUnit(MyUnitIDS.dekameter_per_square_second,  BigDecimal.valueOf(1E+19),       UnitGroup.ACCELERATION, R.string.dekameter_per_square_second,  R.string.dekameter_per_square_second_short),
        MyUnit(MyUnitIDS.hectometer_per_square_second, BigDecimal.valueOf(1E+20),       UnitGroup.ACCELERATION, R.string.hectometer_per_square_second, R.string.hectometer_per_square_second_short),
        MyUnit(MyUnitIDS.gal,                          BigDecimal.valueOf(1E+16),       UnitGroup.ACCELERATION, R.string.gal,                          R.string.gal_short),
        MyUnit(MyUnitIDS.mercury_surface_gravity,      BigDecimal.valueOf(3.7E+18),     UnitGroup.ACCELERATION, R.string.mercury_surface_gravity,      R.string.mercury_surface_gravity_short),
        MyUnit(MyUnitIDS.venus_surface_gravity,        BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.venus_surface_gravity,        R.string.venus_surface_gravity_short),
        MyUnit(MyUnitIDS.earth_surface_gravity,        BigDecimal.valueOf(9.80655E+18), UnitGroup.ACCELERATION, R.string.earth_surface_gravity,        R.string.earth_surface_gravity_short),
        MyUnit(MyUnitIDS.mars_surface_gravity,         BigDecimal.valueOf(3.71E+18),    UnitGroup.ACCELERATION, R.string.mars_surface_gravity,         R.string.mars_surface_gravity_short),
        MyUnit(MyUnitIDS.jupiter_surface_gravity,      BigDecimal.valueOf(2.479E+19),   UnitGroup.ACCELERATION, R.string.jupiter_surface_gravity,      R.string.jupiter_surface_gravity_short),
        MyUnit(MyUnitIDS.saturn_surface_gravity,       BigDecimal.valueOf(1.044E+19),   UnitGroup.ACCELERATION, R.string.saturn_surface_gravity,       R.string.saturn_surface_gravity_short),
        MyUnit(MyUnitIDS.uranus_surface_gravity,       BigDecimal.valueOf(8.87E+18),    UnitGroup.ACCELERATION, R.string.uranus_surface_gravity,       R.string.uranus_surface_gravity_short),
        MyUnit(MyUnitIDS.neptune_surface_gravity,      BigDecimal.valueOf(1.115E+19),   UnitGroup.ACCELERATION, R.string.neptune_surface_gravity,      R.string.neptune_surface_gravity_short),
        MyUnit(MyUnitIDS.sun_surface_gravity,          BigDecimal.valueOf(2.74E+20),    UnitGroup.ACCELERATION, R.string.sun_surface_gravity,          R.string.sun_surface_gravity_short),
    )
}
