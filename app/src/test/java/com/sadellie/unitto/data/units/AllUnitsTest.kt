package com.sadellie.unitto.data.units


import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal

@RunWith(JUnit4::class)
class AllUnitsTest {

    // Group and it's tested unit ids
    var history: MutableMap<UnitGroup, Set<String>> = mutableMapOf()
    private val allUnitsRepository = AllUnitsRepository()

    @Test
    fun testAcceleration() {
        MyUnitIDS.attometer_per_square_second.checkWith(MyUnitIDS.femtometer_per_square_second, "69", "0.069")
        MyUnitIDS.femtometer_per_square_second.checkWith(MyUnitIDS.picometer_per_square_second, "69", "0.069")
        MyUnitIDS.picometer_per_square_second.checkWith(MyUnitIDS.micrometer_per_square_second, "69", "0.00007")
        MyUnitIDS.nanometer_per_square_second.checkWith(MyUnitIDS.centimeter_per_square_second, "69", "0.000007")
        MyUnitIDS.micrometer_per_square_second.checkWith(MyUnitIDS.nanometer_per_square_second, "69", "69000")
        MyUnitIDS.millimeter_per_square_second.checkWith(MyUnitIDS.centimeter_per_square_second, "69", "6.9")
        MyUnitIDS.centimeter_per_square_second.checkWith(MyUnitIDS.meter_per_square_second, "69", "0.69")
        MyUnitIDS.decimeter_per_square_second.checkWith(MyUnitIDS.meter_per_square_second, "69", "6.9")
        MyUnitIDS.meter_per_square_second.checkWith(MyUnitIDS.micrometer_per_square_second, "69", "69000000")
        MyUnitIDS.kilometer_per_square_second.checkWith(MyUnitIDS.hectometer_per_square_second, "69", "690")
        MyUnitIDS.dekameter_per_square_second.checkWith(MyUnitIDS.gal, "69", "69000")
        MyUnitIDS.hectometer_per_square_second.checkWith(MyUnitIDS.gal, "69", "690000")
        MyUnitIDS.gal.checkWith(MyUnitIDS.centimeter_per_square_second, "69", "69")
        MyUnitIDS.mercury_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "3.7")
        MyUnitIDS.venus_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "8.87")
        MyUnitIDS.earth_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "9.80655")
        MyUnitIDS.mars_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "3.71")
        MyUnitIDS.jupiter_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "24.79")
        MyUnitIDS.saturn_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "10.44")
        MyUnitIDS.uranus_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "8.87")
        MyUnitIDS.neptune_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "11.15")
        MyUnitIDS.sun_surface_gravity.checkWith(MyUnitIDS.meter_per_square_second, "1", "274")
    }

    @Test
    fun testAngle() {
        MyUnitIDS.angle_second.checkWith(MyUnitIDS.angle_minute, "420.5", "7.00833")
        MyUnitIDS.angle_minute.checkWith(MyUnitIDS.radian, "420.5", "0.12232")
        MyUnitIDS.degree.checkWith(MyUnitIDS.turn, "420.5", "1.16806")
        MyUnitIDS.radian.checkWith(MyUnitIDS.turn, "420.5", "66.92465")
        MyUnitIDS.sextant.checkWith(MyUnitIDS.radian, "420.5", "440.34657")
        MyUnitIDS.turn.checkWith(MyUnitIDS.angle_minute, "420.5", "9082800")
    }

    @Test
    fun testArea() {
        MyUnitIDS.acre.checkWith(MyUnitIDS.square_kilometer, "75.9", "0.30716")
        MyUnitIDS.hectare.checkWith(MyUnitIDS.square_foot, "75.9", "8169808.00585")
        MyUnitIDS.square_foot.checkWith(MyUnitIDS.square_decimeter, "75.9", "705.13407")
        MyUnitIDS.square_mile.checkWith(MyUnitIDS.square_foot, "75.9", "2115970560.8762")
        MyUnitIDS.square_yard.checkWith(MyUnitIDS.square_foot, "75.9", "683.1")
        MyUnitIDS.square_inch.checkWith(MyUnitIDS.square_foot, "75.9", "0.52708")
        MyUnitIDS.square_micrometer.checkWith(MyUnitIDS.square_millimeter, "75.9", "0.00008")
        MyUnitIDS.square_millimeter.checkWith(MyUnitIDS.square_centimeter, "75.9", "0.759")
        MyUnitIDS.square_centimeter.checkWith(MyUnitIDS.acre, "75.9", "0.000002")
        MyUnitIDS.square_decimeter.checkWith(MyUnitIDS.square_meter, "75.9", "0.759")
        MyUnitIDS.square_meter.checkWith(MyUnitIDS.acre, "75.9", "0.01876")
        MyUnitIDS.square_kilometer.checkWith(MyUnitIDS.hectare, "75.9", "7590")
        MyUnitIDS.electron_cross_section.checkWith(MyUnitIDS.square_micrometer, "75.9", "0.000000000000005")
    }

    @Test
    fun testDataTransfer() {
        MyUnitIDS.bit_per_second.checkWith(MyUnitIDS.kilobit_per_second, "2048", "2.048")
        MyUnitIDS.kibibit_per_second.checkWith(MyUnitIDS.bit_per_second, "2048", "2097152")
        MyUnitIDS.kilobit_per_second.checkWith(MyUnitIDS.bit_per_second, "2048", "2048000")
        MyUnitIDS.megabit_per_second.checkWith(MyUnitIDS.kilobit_per_second, "2048", "2048000")
        MyUnitIDS.mebibit_per_second.checkWith(MyUnitIDS.kilobit_per_second, "2048", "2147483.648")
        MyUnitIDS.gigabit_per_second.checkWith(MyUnitIDS.kilobit_per_second, "2048", "2048000000")
        MyUnitIDS.terabit_per_second.checkWith(MyUnitIDS.gigabyte_per_second, "2048", "256000")
        MyUnitIDS.petabit_per_second.checkWith(MyUnitIDS.gigabyte_per_second, "2048", "256000000")
        MyUnitIDS.exabit_per_second.checkWith(MyUnitIDS.petabit_per_second, "2048", "2048000")
        MyUnitIDS.byte_per_second.checkWith(MyUnitIDS.bit_per_second, "2048", "16384")
        MyUnitIDS.kibibyte_per_second.checkWith(MyUnitIDS.bit_per_second, "2048", "16777216")
        MyUnitIDS.kilobyte_per_second.checkWith(MyUnitIDS.kibibit_per_second, "2048", "16000")
        MyUnitIDS.megabyte_per_second.checkWith(MyUnitIDS.kilobyte_per_second, "2048", "2048000")
        MyUnitIDS.mebibyte_per_second.checkWith(MyUnitIDS.bit_per_second, "2048", "17179869184")
        MyUnitIDS.gigabyte_per_second.checkWith(MyUnitIDS.kilobyte_per_second, "2048", "2048000000")
        MyUnitIDS.terabyte_per_second.checkWith(MyUnitIDS.gigabyte_per_second, "2048", "2048000")
        MyUnitIDS.petabyte_per_second.checkWith(MyUnitIDS.terabyte_per_second, "2048", "2048000")
        MyUnitIDS.exabyte_per_second.checkWith(MyUnitIDS.petabyte_per_second, "2048", "2048000")
    }

    @Test
    fun testEnergy() {
        MyUnitIDS.electron_volt.checkWith(MyUnitIDS.joule, "56000000000000000000", "8.97219")
        MyUnitIDS.attojoule.checkWith(MyUnitIDS.calorie_th, "41840000000000", "0.00001")
        MyUnitIDS.joule.checkWith(MyUnitIDS.kilocalorie_th, "4184", "1")
        MyUnitIDS.kilojoule.checkWith(MyUnitIDS.calorie_th, "4184000000", "1000000000000")
        MyUnitIDS.megajoule.checkWith(MyUnitIDS.calorie_th, "0.4184", "100000")
        MyUnitIDS.gigajoule.checkWith(MyUnitIDS.calorie_th, "0.4184", "100000000")
        MyUnitIDS.energy_ton.checkWith(MyUnitIDS.calorie_th, "0.4184", "418400000")
        MyUnitIDS.kiloton.checkWith(MyUnitIDS.calorie_th, "0.4184", "418400000000")
        MyUnitIDS.megaton.checkWith(MyUnitIDS.calorie_th, "0.4184", "418400000000000")
        MyUnitIDS.gigaton.checkWith(MyUnitIDS.calorie_th, "0.000000004184", "4184000000")
        MyUnitIDS.energy_horse_power_metric.checkWith(MyUnitIDS.joule, "10", "26477955")
        MyUnitIDS.calorie_th.checkWith(MyUnitIDS.joule, "10", "41.84")
        MyUnitIDS.kilocalorie_th.checkWith(MyUnitIDS.joule, "10", "41840")
    }

    @Test
    fun testFileSize() {
        MyUnitIDS.bit.checkWith(MyUnitIDS.kilobit, "2048", "2.048")
        MyUnitIDS.kibibit.checkWith(MyUnitIDS.bit, "2048", "2097152")
        MyUnitIDS.kilobit.checkWith(MyUnitIDS.bit, "2048", "2048000")
        MyUnitIDS.megabit.checkWith(MyUnitIDS.kilobit, "2048", "2048000")
        MyUnitIDS.mebibit.checkWith(MyUnitIDS.kilobit, "2048", "2147483.648")
        MyUnitIDS.gigabit.checkWith(MyUnitIDS.kilobit, "2048", "2048000000")
        MyUnitIDS.terabit.checkWith(MyUnitIDS.gigabyte, "2048", "256000")
        MyUnitIDS.petabit.checkWith(MyUnitIDS.gigabyte, "2048", "256000000")
        MyUnitIDS.exabit.checkWith(MyUnitIDS.petabit, "2048", "2048000")
        MyUnitIDS.byte.checkWith(MyUnitIDS.bit, "2048", "16384")
        MyUnitIDS.kibibyte.checkWith(MyUnitIDS.bit, "2048", "16777216")
        MyUnitIDS.kilobyte.checkWith(MyUnitIDS.kibibit, "2048", "16000")
        MyUnitIDS.megabyte.checkWith(MyUnitIDS.kilobyte, "2048", "2048000")
        MyUnitIDS.mebibyte.checkWith(MyUnitIDS.bit, "2048", "17179869184")
        MyUnitIDS.gigabyte.checkWith(MyUnitIDS.kilobyte, "2048", "2048000000")
        MyUnitIDS.terabyte.checkWith(MyUnitIDS.gigabyte, "2048", "2048000")
        MyUnitIDS.petabyte.checkWith(MyUnitIDS.terabyte, "2048", "2048000")
        MyUnitIDS.exabyte.checkWith(MyUnitIDS.petabyte, "2048", "2048000")
    }

    @Test
    fun testLength() {
        MyUnitIDS.attometer.checkWith(MyUnitIDS.micrometer, "158000000000.7", "0.158")
        MyUnitIDS.nanometer.checkWith(MyUnitIDS.inch, "158000000000.7", "6220.47244")
        MyUnitIDS.micrometer.checkWith(MyUnitIDS.inch, "158000000000.7", "6220472.44097")
        MyUnitIDS.millimeter.checkWith(MyUnitIDS.inch, "158000.7", "6220.5")
        MyUnitIDS.centimeter.checkWith(MyUnitIDS.inch, "158000.7", "62205")
        MyUnitIDS.decimeter.checkWith(MyUnitIDS.foot, "158000.7", "51837.5")
        MyUnitIDS.meter.checkWith(MyUnitIDS.yard, "158000.7", "172791.66667")
        MyUnitIDS.kilometer.checkWith(MyUnitIDS.mile, "100", "62.13712")
        MyUnitIDS.inch.checkWith(MyUnitIDS.foot, "100", "8.33333")
        MyUnitIDS.foot.checkWith(MyUnitIDS.inch, "100", "1200")
        MyUnitIDS.yard.checkWith(MyUnitIDS.foot, "100", "300")
        MyUnitIDS.mile.checkWith(MyUnitIDS.foot, "100", "528000")
        MyUnitIDS.light_year.checkWith(MyUnitIDS.foot, "0.0000001", "3103914196.85037")
        MyUnitIDS.parsec.checkWith(MyUnitIDS.foot, "0.00000001", "1012361411.25044")
        MyUnitIDS.kiloparsec.checkWith(MyUnitIDS.foot, "0.00000000001", "1012361411.25044")
        MyUnitIDS.megaparsec.checkWith(MyUnitIDS.foot, "0.00000000000001", "1012361411.25044")
        MyUnitIDS.mercury_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "2439.7")
        MyUnitIDS.venus_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "6051.8")
        MyUnitIDS.earth_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "6371")
        MyUnitIDS.mars_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "3389.5")
        MyUnitIDS.jupiter_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "69911")
        MyUnitIDS.saturn_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "58232")
        MyUnitIDS.uranus_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "25362")
        MyUnitIDS.neptune_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "24622")
        MyUnitIDS.sun_equatorial_radius.checkWith(MyUnitIDS.kilometer, "1", "695508")
    }

    @Test
    fun testMass() {
        MyUnitIDS.electron_mass_rest.checkWith(MyUnitIDS.milligram, "1500000000000000000", "0.000001")
        MyUnitIDS.atomic_mass_unit.checkWith(MyUnitIDS.electron_mass_rest, "150", "273433.27951")
        MyUnitIDS.milligram.checkWith(MyUnitIDS.ounce, "1500", "0.05291")
        MyUnitIDS.gram.checkWith(MyUnitIDS.ounce, "1500", "52.91094")
        MyUnitIDS.kilogram.checkWith(MyUnitIDS.carat, "1500", "7500000")
        MyUnitIDS.metric_ton.checkWith(MyUnitIDS.carat, "1500", "7500000000")
        MyUnitIDS.imperial_ton.checkWith(MyUnitIDS.kilogram, "1500", "1524070.3632")
        MyUnitIDS.ounce.checkWith(MyUnitIDS.pound, "1500", "93.75")
        MyUnitIDS.carat.checkWith(MyUnitIDS.pound, "1500", "0.66139")
        MyUnitIDS.pound.checkWith(MyUnitIDS.kilogram, "1500", "680.38856")
        MyUnitIDS.mercury_mass.checkWith(MyUnitIDS.kilogram, "1", "330104000000000000000000")
        MyUnitIDS.venus_mass.checkWith(MyUnitIDS.kilogram, "1", "4867320000000000000000000")
        MyUnitIDS.earth_mass.checkWith(MyUnitIDS.kilogram, "1", "5972190000000000000000000")
        MyUnitIDS.mars_mass.checkWith(MyUnitIDS.kilogram, "1", "641693000000000000000000")
        MyUnitIDS.jupiter_mass.checkWith(MyUnitIDS.kilogram, "1", "1898130000000000000000000000")
        MyUnitIDS.saturn_mass.checkWith(MyUnitIDS.kilogram, "1", "568319000000000000000000000")
        MyUnitIDS.uranus_mass.checkWith(MyUnitIDS.kilogram, "1", "86810300000000000000000000")
        MyUnitIDS.neptune_mass.checkWith(MyUnitIDS.kilogram, "1", "102410000000000000000000000")
        MyUnitIDS.sun_mass.checkWith(MyUnitIDS.kilogram, "1", "1989100000000000000000000000000")
    }

    @Test
    fun testPower() {
        MyUnitIDS.attowatt.checkWith(MyUnitIDS.watt, "3950000000000000", "0.00395")
        MyUnitIDS.watt.checkWith(MyUnitIDS.kilowatt, "395", "0.395")
        MyUnitIDS.kilowatt.checkWith(MyUnitIDS.horse_power_mechanical, "395", "529.70373")
        MyUnitIDS.megawatt.checkWith(MyUnitIDS.horse_power_mechanical, "395", "529703.72539")
        MyUnitIDS.horse_power_mechanical.checkWith(MyUnitIDS.kilowatt, "395", "294.55145")
    }

    @Test
    fun testPressure() {
        MyUnitIDS.attopascal.checkWith(MyUnitIDS.femtopascal, "456", "0.456")
        MyUnitIDS.femtopascal.checkWith(MyUnitIDS.picopascal, "456", "0.456")
        MyUnitIDS.picopascal.checkWith(MyUnitIDS.nanopascal, "456", "0.456")
        MyUnitIDS.nanopascal.checkWith(MyUnitIDS.micropascal, "456", "0.456")
        MyUnitIDS.micropascal.checkWith(MyUnitIDS.millipascal, "456", "0.456")
        MyUnitIDS.millipascal.checkWith(MyUnitIDS.centipascal, "456", "45.6")
        MyUnitIDS.centipascal.checkWith(MyUnitIDS.decipascal, "456", "45.6")
        MyUnitIDS.decipascal.checkWith(MyUnitIDS.dekapascal, "456", "4.56")
        MyUnitIDS.pascal.checkWith(MyUnitIDS.bar, "456", "0.00456")
        MyUnitIDS.dekapascal.checkWith(MyUnitIDS.bar, "456", "0.0456")
        MyUnitIDS.hectopascal.checkWith(MyUnitIDS.psi, "456", "6.61372")
        MyUnitIDS.bar.checkWith(MyUnitIDS.ksi, "456", "6.61372")
        MyUnitIDS.megapascal.checkWith(MyUnitIDS.ksi, "456", "66.13721")
        MyUnitIDS.gigapascal.checkWith(MyUnitIDS.torr, "456", "3420281273.13024")
        MyUnitIDS.terapascal.checkWith(MyUnitIDS.gigapascal, "456", "456000")
        MyUnitIDS.petapascal.checkWith(MyUnitIDS.gigapascal, "456", "456000000")
        MyUnitIDS.exapascal.checkWith(MyUnitIDS.gigapascal, "456", "456000000000")
        MyUnitIDS.psi.checkWith(MyUnitIDS.gigapascal, "456", "0.00314")
        MyUnitIDS.ksi.checkWith(MyUnitIDS.gigapascal, "456", "3.14401")
        MyUnitIDS.standard_atmosphere.checkWith(MyUnitIDS.torr, "456", "346560")
        MyUnitIDS.torr.checkWith(MyUnitIDS.hectopascal, "456", "607.95")
        MyUnitIDS.millimeter_of_mercury.checkWith(MyUnitIDS.hectopascal, "456", "607.95")
    }

    @Test
    fun testSpeed() {
        MyUnitIDS.millimeter_per_hour.checkWith(MyUnitIDS.kilometer_per_hour, "396", "0.0004")
        MyUnitIDS.millimeter_per_minute.checkWith(MyUnitIDS.kilometer_per_hour, "396", "0.02376")
        MyUnitIDS.millimeter_per_second.checkWith(MyUnitIDS.kilometer_per_hour, "396", "1.4256")
        MyUnitIDS.centimeter_per_hour.checkWith(MyUnitIDS.kilometer_per_hour, "396", "0.00396")
        MyUnitIDS.centimeter_per_minute.checkWith(MyUnitIDS.kilometer_per_hour, "396", "0.2376")
        MyUnitIDS.centimeter_per_second.checkWith(MyUnitIDS.kilometer_per_hour, "396", "14.256")
        MyUnitIDS.meter_per_hour.checkWith(MyUnitIDS.kilometer_per_hour, "396", "0.396")
        MyUnitIDS.meter_per_minute.checkWith(MyUnitIDS.kilometer_per_hour, "396", "23.76")
        MyUnitIDS.meter_per_second.checkWith(MyUnitIDS.kilometer_per_hour, "396", "1425.6")
        MyUnitIDS.kilometer_per_hour.checkWith(MyUnitIDS.mile_per_hour, "396", "246.06299")
        MyUnitIDS.kilometer_per_minute.checkWith(MyUnitIDS.mile_per_hour, "396", "14763.77953")
        MyUnitIDS.kilometer_per_second.checkWith(MyUnitIDS.mile_per_hour, "396", "885826.77165")
        MyUnitIDS.foot_per_hour.checkWith(MyUnitIDS.mile_per_hour, "396", "0.075")
        MyUnitIDS.foot_per_minute.checkWith(MyUnitIDS.mile_per_hour, "396", "4.5")
        MyUnitIDS.foot_per_second.checkWith(MyUnitIDS.mile_per_hour, "396", "270")
        MyUnitIDS.yard_per_hour.checkWith(MyUnitIDS.mile_per_hour, "396", "0.225")
        MyUnitIDS.yard_per_minute.checkWith(MyUnitIDS.mile_per_hour, "396", "13.5")
        MyUnitIDS.yard_per_second.checkWith(MyUnitIDS.mile_per_hour, "396", "810")
        MyUnitIDS.mile_per_hour.checkWith(MyUnitIDS.foot_per_hour, "396", "2090880")
        MyUnitIDS.mile_per_minute.checkWith(MyUnitIDS.foot_per_hour, "396", "125452800")
        MyUnitIDS.mile_per_second.checkWith(MyUnitIDS.foot_per_hour, "396", "7527168000")
        MyUnitIDS.knot.checkWith(MyUnitIDS.meter_per_hour, "396", "733392")
        MyUnitIDS.velocity_of_light_in_vacuum.checkWith(MyUnitIDS.meter_per_second, "1", "299792458")
        MyUnitIDS.cosmic_velocity_first.checkWith(MyUnitIDS.meter_per_second, "1", "7900")
        MyUnitIDS.cosmic_velocity_second.checkWith(MyUnitIDS.meter_per_second, "1", "11200")
        MyUnitIDS.cosmic_velocity_third.checkWith(MyUnitIDS.meter_per_second, "1", "16670")
        MyUnitIDS.earths_orbital_speed.checkWith(MyUnitIDS.meter_per_second, "1", "29765")
        MyUnitIDS.mach.checkWith(MyUnitIDS.meter_per_second, "1", "343.6")
        MyUnitIDS.mach_si_standard.checkWith(MyUnitIDS.meter_per_second, "1", "295.0464")
    }

    @Test
    fun testTemperature() {
        MyUnitIDS.celsius.checkWith(MyUnitIDS.celsius, "0", "0")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.fahrenheit, "0", "32")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.kelvin, "0", "273.15")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.celsius, "36.6", "36.6")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.fahrenheit, "36.6", "97.88")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.kelvin, "36.6", "309.75")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.celsius, "-36.6", "-36.6")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.fahrenheit, "-36.6", "-33.88")
        MyUnitIDS.celsius.checkWith(MyUnitIDS.kelvin, "-36.6", "236.55")

        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.celsius, "0", "-17.77778")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.fahrenheit, "0", "0")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.kelvin, "0", "255.37222")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.celsius, "36.6", "2.55556")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.fahrenheit, "36.6", "36.6")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.kelvin, "36.6", "275.70556")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.celsius, "-36.6", "-38.11111")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.fahrenheit, "-36.6", "-36.6")
        MyUnitIDS.fahrenheit.checkWith(MyUnitIDS.kelvin, "-36.6", "235.03889")

        MyUnitIDS.kelvin.checkWith(MyUnitIDS.celsius, "0", "-273.15")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.fahrenheit, "0", "-459.67")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.kelvin, "0", "0")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.celsius, "36.6", "-236.55")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.fahrenheit, "36.6", "-393.79")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.kelvin, "36.6", "36.6")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.celsius, "-36.6", "-309.75")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.fahrenheit, "-36.6", "-525.55")
        MyUnitIDS.kelvin.checkWith(MyUnitIDS.kelvin, "-36.6", "-36.6")
    }

    @Test
    fun testTime() {
        MyUnitIDS.attosecond.checkWith(MyUnitIDS.millisecond, "366000000000", "0.00037")
        MyUnitIDS.nanosecond.checkWith(MyUnitIDS.millisecond, "366", "0.00037")
        MyUnitIDS.microsecond.checkWith(MyUnitIDS.millisecond, "366", "0.366")
        MyUnitIDS.millisecond.checkWith(MyUnitIDS.second, "366", "0.366")
        MyUnitIDS.jiffy.checkWith(MyUnitIDS.second, "366", "3.66")
        MyUnitIDS.second.checkWith(MyUnitIDS.millisecond, "366", "366000")
        MyUnitIDS.minute.checkWith(MyUnitIDS.millisecond, "366", "21960000")
        MyUnitIDS.hour.checkWith(MyUnitIDS.millisecond, "366", "1317600000")
        MyUnitIDS.day.checkWith(MyUnitIDS.hour, "366", "8784")
        MyUnitIDS.week.checkWith(MyUnitIDS.hour, "366", "61488")
    }

    @Test
    fun testVolume() {
        MyUnitIDS.attoliter.checkWith(MyUnitIDS.millimeter, "1507000000000", "0.00151")
        MyUnitIDS.milliliter.checkWith(MyUnitIDS.liter, "1507", "1.507")
        MyUnitIDS.liter.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "398.10728")
        MyUnitIDS.us_liquid_gallon.checkWith(MyUnitIDS.us_fluid_ounce, "1507", "192896")
        MyUnitIDS.us_liquid_quart.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "376.75")
        MyUnitIDS.us_liquid_pint.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "188.375")
        MyUnitIDS.us_legal_cup.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "94.1875")
        MyUnitIDS.us_fluid_ounce.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "11.77344")
        MyUnitIDS.us_tablespoon.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "5.88672")
        MyUnitIDS.us_teaspoon.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "1.96224")
        MyUnitIDS.imperial_gallon.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "1809.83154")
        MyUnitIDS.imperial_quart.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "452.45788")
        MyUnitIDS.imperial_pint.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "226.22894")
        MyUnitIDS.imperial_cup.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "113.11447")
        MyUnitIDS.imperial_fluid_ounce.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "11.31145")
        MyUnitIDS.imperial_tablespoon.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "7.06965")
        MyUnitIDS.imperial_teaspoon.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "2.35655")
        MyUnitIDS.cubic_millimeter.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "0.0004")
        MyUnitIDS.cubic_centimeter.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "0.39811")
        MyUnitIDS.cubic_meter.checkWith(MyUnitIDS.us_liquid_gallon, "1507", "398107.2829")
        MyUnitIDS.cubic_kilometer.checkWith(MyUnitIDS.us_liquid_gallon, "0.0000001507", "39810.72829")
    }

    private fun String.checkWith(checkingId: String, value: String, expected: String) {
        val unit = allUnitsRepository.getById(this)
        val actual = unit
            .convert(allUnitsRepository.getById(checkingId), BigDecimal(value), 5)
            .toPlainString()
        assertEquals("Failed at $this to $checkingId", expected, actual)
        println("PASSED: $this -> $expected == $actual")
        val content: Set<String> = history.getOrDefault(unit.group, setOf())
        history[unit.group] = content.plus(this)
    }

    @After
    fun after() {
        val unitGroup = history.keys.first()
        // GROUP : testedCount / totalCount
        println("${unitGroup.name} : ${history[unitGroup]?.size} / ${allUnitsRepository.getCollectionByGroup(unitGroup)?.size}")
    }
}
