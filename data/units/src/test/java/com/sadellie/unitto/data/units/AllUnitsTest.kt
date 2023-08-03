/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.data.units

import com.sadellie.unitto.data.model.NumberBaseUnit
import com.sadellie.unitto.data.model.UnitGroup
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class AllUnitsTest {

    // Group and it's tested unit ids
    private var history: MutableMap<UnitGroup, Set<String>> = mutableMapOf()
    private val allUnitsRepository = AllUnitsRepository()

    @Test
    fun testAcceleration() = testWithUnits {
        attometer_per_square_second.checkWith(femtometer_per_square_second, "69", "0.069")
        femtometer_per_square_second.checkWith(picometer_per_square_second, "69", "0.069")
        picometer_per_square_second.checkWith(micrometer_per_square_second, "69", "0.00007")
        nanometer_per_square_second.checkWith(centimeter_per_square_second, "69", "0.000007")
        micrometer_per_square_second.checkWith(nanometer_per_square_second, "69", "69000")
        millimeter_per_square_second.checkWith(centimeter_per_square_second, "69", "6.9")
        centimeter_per_square_second.checkWith(meter_per_square_second, "69", "0.69")
        decimeter_per_square_second.checkWith(meter_per_square_second, "69", "6.9")
        meter_per_square_second.checkWith(micrometer_per_square_second, "69", "69000000")
        kilometer_per_square_second.checkWith(hectometer_per_square_second, "69", "690")
        dekameter_per_square_second.checkWith(gal, "69", "69000")
        hectometer_per_square_second.checkWith(gal, "69", "690000")
        gal.checkWith(centimeter_per_square_second, "69", "69")
        mercury_surface_gravity.checkWith(meter_per_square_second, "1", "3.7")
        venus_surface_gravity.checkWith(meter_per_square_second, "1", "8.87")
        earth_surface_gravity.checkWith(meter_per_square_second, "1", "9.80655")
        mars_surface_gravity.checkWith(meter_per_square_second, "1", "3.71")
        jupiter_surface_gravity.checkWith(meter_per_square_second, "1", "24.79")
        saturn_surface_gravity.checkWith(meter_per_square_second, "1", "10.44")
        uranus_surface_gravity.checkWith(meter_per_square_second, "1", "8.87")
        neptune_surface_gravity.checkWith(meter_per_square_second, "1", "11.15")
        sun_surface_gravity.checkWith(meter_per_square_second, "1", "274")
    }

    @Test
    fun testAngle() = testWithUnits {
        angle_second.checkWith(angle_minute, "420.5", "7.00833")
        angle_minute.checkWith(radian, "420.5", "0.12232")
        degree.checkWith(turn, "420.5", "1.16806")
        radian.checkWith(turn, "420.5", "66.92465")
        sextant.checkWith(radian, "420.5", "440.34657")
        turn.checkWith(angle_minute, "420.5", "9082800")
    }

    @Test
    fun testArea() = testWithUnits {
        cent.checkWith(acre, "75.9", "7590")
        acre.checkWith(square_kilometer, "75.9", "0.30716")
        hectare.checkWith(square_foot, "75.9", "8169808.00585")
        square_foot.checkWith(square_decimeter, "75.9", "705.13407")
        square_mile.checkWith(square_foot, "75.9", "2115970560.8762")
        square_yard.checkWith(square_foot, "75.9", "683.1")
        square_inch.checkWith(square_foot, "75.9", "0.52708")
        square_micrometer.checkWith(square_millimeter, "75.9", "0.00008")
        square_millimeter.checkWith(square_centimeter, "75.9", "0.759")
        square_centimeter.checkWith(acre, "75.9", "0.000002")
        square_decimeter.checkWith(square_meter, "75.9", "0.759")
        square_meter.checkWith(acre, "75.9", "0.01876")
        square_kilometer.checkWith(hectare, "75.9", "7590")
        electron_cross_section.checkWith(square_micrometer, "75.9", "0.000000000000005")
    }

    @Test
    fun testDataTransfer() = testWithUnits {
        bit_per_second.checkWith(kilobit_per_second, "2048", "2.048")
        kibibit_per_second.checkWith(bit_per_second, "2048", "2097152")
        kilobit_per_second.checkWith(bit_per_second, "2048", "2048000")
        megabit_per_second.checkWith(kilobit_per_second, "2048", "2048000")
        mebibit_per_second.checkWith(kilobit_per_second, "2048", "2147483.648")
        gigabit_per_second.checkWith(kilobit_per_second, "2048", "2048000000")
        gibibit_per_second.checkWith(mebibit_per_second, "24", "24576")
        terabit_per_second.checkWith(gigabyte_per_second, "2048", "256000")
        petabit_per_second.checkWith(gigabyte_per_second, "2048", "256000000")
        exabit_per_second.checkWith(petabit_per_second, "2048", "2048000")
        byte_per_second.checkWith(bit_per_second, "2048", "16384")
        kibibyte_per_second.checkWith(bit_per_second, "2048", "16777216")
        kilobyte_per_second.checkWith(kibibit_per_second, "2048", "16000")
        megabyte_per_second.checkWith(kilobyte_per_second, "2048", "2048000")
        mebibyte_per_second.checkWith(bit_per_second, "2048", "17179869184")
        gigabyte_per_second.checkWith(kilobyte_per_second, "2048", "2048000000")
        gibibyte_per_second.checkWith(mebibit_per_second, "24", "196608")
        terabyte_per_second.checkWith(gigabyte_per_second, "2048", "2048000")
        petabyte_per_second.checkWith(terabyte_per_second, "2048", "2048000")
        exabyte_per_second.checkWith(petabyte_per_second, "2048", "2048000")
    }

    @Test
    fun testEnergy() = testWithUnits {
        electron_volt.checkWith(joule, "56000000000000000000", "8.97219")
        attojoule.checkWith(calorie_th, "41840000000000", "0.00001")
        joule.checkWith(kilocalorie_th, "4184", "1")
        kilojoule.checkWith(calorie_th, "4184000000", "1000000000000")
        megajoule.checkWith(calorie_th, "0.4184", "100000")
        gigajoule.checkWith(calorie_th, "0.4184", "100000000")
        energy_ton.checkWith(calorie_th, "0.4184", "418400000")
        kiloton.checkWith(calorie_th, "0.4184", "418400000000")
        megaton.checkWith(calorie_th, "0.4184", "418400000000000")
        gigaton.checkWith(calorie_th, "0.000000004184", "4184000000")
        energy_horse_power_metric.checkWith(joule, "10", "26477955")
        calorie_th.checkWith(joule, "10", "41.84")
        kilocalorie_th.checkWith(joule, "10", "41840")
    }

    @Test
    fun testData() = testWithUnits {
        bit.checkWith(kilobit, "2048", "2.048")
        kibibit.checkWith(bit, "2048", "2097152")
        kilobit.checkWith(bit, "2048", "2048000")
        megabit.checkWith(kilobit, "2048", "2048000")
        mebibit.checkWith(kilobit, "2048", "2147483.648")
        gigabit.checkWith(kilobit, "2048", "2048000000")
        gibibit.checkWith(mebibit, "24", "24576")
        terabit.checkWith(gigabyte, "2048", "256000")
        petabit.checkWith(gigabyte, "2048", "256000000")
        exabit.checkWith(petabit, "2048", "2048000")
        byte.checkWith(bit, "2048", "16384")
        kibibyte.checkWith(bit, "2048", "16777216")
        kilobyte.checkWith(kibibit, "2048", "16000")
        megabyte.checkWith(kilobyte, "2048", "2048000")
        mebibyte.checkWith(bit, "2048", "17179869184")
        gigabyte.checkWith(kilobyte, "2048", "2048000000")
        gibibyte.checkWith(mebibit, "24", "196608")
        terabyte.checkWith(gigabyte, "2048", "2048000")
        petabyte.checkWith(terabyte, "2048", "2048000")
        exabyte.checkWith(petabyte, "2048", "2048000")
    }

    @Test
    fun testLength() = testWithUnits {
        attometer.checkWith(micrometer, "158000000000.7", "0.158")
        nanometer.checkWith(inch, "158000000000.7", "6220.47244")
        micrometer.checkWith(inch, "158000000000.7", "6220472.44097")
        millimeter.checkWith(inch, "158000.7", "6220.5")
        centimeter.checkWith(inch, "158000.7", "62205")
        decimeter.checkWith(foot, "158000.7", "51837.5")
        meter.checkWith(yard, "158000.7", "172791.66667")
        kilometer.checkWith(mile, "100", "62.13712")
        nautical_mile.checkWith(kilometer, "100", "185.2")
        inch.checkWith(foot, "100", "8.33333")
        foot.checkWith(inch, "100", "1200")
        yard.checkWith(foot, "100", "300")
        mile.checkWith(foot, "100", "528000")
        light_year.checkWith(foot, "0.0000001", "3103914196.85037")
        parsec.checkWith(foot, "0.00000001", "1012361411.25044")
        kiloparsec.checkWith(foot, "0.00000000001", "1012361411.25044")
        megaparsec.checkWith(foot, "0.00000000000001", "1012361411.25044")
        mercury_equatorial_radius.checkWith(kilometer, "1", "2439.7")
        venus_equatorial_radius.checkWith(kilometer, "1", "6051.8")
        earth_equatorial_radius.checkWith(kilometer, "1", "6371")
        mars_equatorial_radius.checkWith(kilometer, "1", "3389.5")
        jupiter_equatorial_radius.checkWith(kilometer, "1", "69911")
        saturn_equatorial_radius.checkWith(kilometer, "1", "58232")
        uranus_equatorial_radius.checkWith(kilometer, "1", "25362")
        neptune_equatorial_radius.checkWith(kilometer, "1", "24622")
        sun_equatorial_radius.checkWith(kilometer, "1", "695508")
    }

    @Test
    fun testMass() = testWithUnits {
        electron_mass_rest.checkWith(milligram, "1500000000000000000", "0.000001")
        atomic_mass_unit.checkWith(electron_mass_rest, "150", "273433.27951")
        microgram.checkWith(gram, "1500", "0.0015")
        milligram.checkWith(ounce, "1500", "0.05291")
        gram.checkWith(ounce, "1500", "52.91094")
        kilogram.checkWith(carat, "1500", "7500000")
        metric_ton.checkWith(carat, "1500", "7500000000")
        imperial_ton.checkWith(kilogram, "1500", "1524070.3632")
        ounce.checkWith(pound, "1500", "93.75")
        carat.checkWith(pound, "1500", "0.66139")
        pound.checkWith(kilogram, "1500", "680.38856")
        mercury_mass.checkWith(kilogram, "1", "330104000000000000000000")
        venus_mass.checkWith(kilogram, "1", "4867320000000000000000000")
        earth_mass.checkWith(kilogram, "1", "5972190000000000000000000")
        mars_mass.checkWith(kilogram, "1", "641693000000000000000000")
        jupiter_mass.checkWith(kilogram, "1", "1898130000000000000000000000")
        saturn_mass.checkWith(kilogram, "1", "568319000000000000000000000")
        uranus_mass.checkWith(kilogram, "1", "86810300000000000000000000")
        neptune_mass.checkWith(kilogram, "1", "102410000000000000000000000")
        sun_mass.checkWith(kilogram, "1", "1989100000000000000000000000000")
    }

    @Test
    fun testPower() = testWithUnits {
        attowatt.checkWith(watt, "3950000000000000", "0.00395")
        watt.checkWith(kilowatt, "395", "0.395")
        kilowatt.checkWith(horse_power_mechanical, "395", "529.70373")
        megawatt.checkWith(horse_power_mechanical, "395", "529703.72539")
        horse_power_mechanical.checkWith(kilowatt, "395", "294.55145")
    }

    @Test
    fun testPressure() = testWithUnits {
        attopascal.checkWith(femtopascal, "456", "0.456")
        femtopascal.checkWith(picopascal, "456", "0.456")
        picopascal.checkWith(nanopascal, "456", "0.456")
        nanopascal.checkWith(micropascal, "456", "0.456")
        micropascal.checkWith(millipascal, "456", "0.456")
        millipascal.checkWith(centipascal, "456", "45.6")
        centipascal.checkWith(decipascal, "456", "45.6")
        decipascal.checkWith(dekapascal, "456", "4.56")
        pascal.checkWith(bar, "456", "0.00456")
        dekapascal.checkWith(bar, "456", "0.0456")
        hectopascal.checkWith(psi, "456", "6.61372")
        millibar.checkWith(psi, "456", "6.61372")
        bar.checkWith(ksi, "456", "6.61372")
        kilopascal.checkWith(psi, "456", "66.13721")
        megapascal.checkWith(ksi, "456", "66.13721")
        gigapascal.checkWith(torr, "456", "3420281273.13024")
        terapascal.checkWith(gigapascal, "456", "456000")
        petapascal.checkWith(gigapascal, "456", "456000000")
        exapascal.checkWith(gigapascal, "456", "456000000000")
        psi.checkWith(gigapascal, "456", "0.00314")
        ksi.checkWith(gigapascal, "456", "3.14401")
        standard_atmosphere.checkWith(torr, "456", "346560")
        torr.checkWith(hectopascal, "456", "607.95")
        micron_of_mercury.checkWith(hectopascal, "456", "0.60795")
        millimeter_of_mercury.checkWith(hectopascal, "456", "607.95")
    }

    @Test
    fun testSpeed() = testWithUnits {
        millimeter_per_hour.checkWith(kilometer_per_hour, "396", "0.0004")
        millimeter_per_minute.checkWith(kilometer_per_hour, "396", "0.02376")
        millimeter_per_second.checkWith(kilometer_per_hour, "396", "1.4256")
        centimeter_per_hour.checkWith(kilometer_per_hour, "396", "0.00396")
        centimeter_per_minute.checkWith(kilometer_per_hour, "396", "0.2376")
        centimeter_per_second.checkWith(kilometer_per_hour, "396", "14.256")
        meter_per_hour.checkWith(kilometer_per_hour, "396", "0.396")
        meter_per_minute.checkWith(kilometer_per_hour, "396", "23.76")
        meter_per_second.checkWith(kilometer_per_hour, "396", "1425.6")
        kilometer_per_hour.checkWith(mile_per_hour, "396", "246.06299")
        kilometer_per_minute.checkWith(mile_per_hour, "396", "14763.77953")
        kilometer_per_second.checkWith(mile_per_hour, "396", "885826.77165")
        foot_per_hour.checkWith(mile_per_hour, "396", "0.075")
        foot_per_minute.checkWith(mile_per_hour, "396", "4.5")
        foot_per_second.checkWith(mile_per_hour, "396", "270")
        yard_per_hour.checkWith(mile_per_hour, "396", "0.225")
        yard_per_minute.checkWith(mile_per_hour, "396", "13.5")
        yard_per_second.checkWith(mile_per_hour, "396", "810")
        mile_per_hour.checkWith(foot_per_hour, "396", "2090880")
        mile_per_minute.checkWith(foot_per_hour, "396", "125452800")
        mile_per_second.checkWith(foot_per_hour, "396", "7527168000")
        knot.checkWith(meter_per_hour, "396", "733392")
        velocity_of_light_in_vacuum.checkWith(meter_per_second, "1", "299792458")
        cosmic_velocity_first.checkWith(meter_per_second, "1", "7900")
        cosmic_velocity_second.checkWith(meter_per_second, "1", "11200")
        cosmic_velocity_third.checkWith(meter_per_second, "1", "16670")
        earths_orbital_speed.checkWith(meter_per_second, "1", "29765")
        mach.checkWith(meter_per_second, "1", "343.6")
        mach_si_standard.checkWith(meter_per_second, "1", "295.0464")
    }

    @Test
    fun testTemperature() = testWithUnits {
        celsius.checkWith(celsius, "0", "0")
        celsius.checkWith(fahrenheit, "0", "32")
        celsius.checkWith(kelvin, "0", "273.15")
        celsius.checkWith(celsius, "36.6", "36.6")
        celsius.checkWith(fahrenheit, "36.6", "97.88")
        celsius.checkWith(kelvin, "36.6", "309.75")
        celsius.checkWith(celsius, "-36.6", "-36.6")
        celsius.checkWith(fahrenheit, "-36.6", "-33.88")
        celsius.checkWith(kelvin, "-36.6", "236.55")

        fahrenheit.checkWith(celsius, "0", "-17.77778")
        fahrenheit.checkWith(fahrenheit, "0", "0")
        fahrenheit.checkWith(kelvin, "0", "255.37222")
        fahrenheit.checkWith(celsius, "36.6", "2.55556")
        fahrenheit.checkWith(fahrenheit, "36.6", "36.6")
        fahrenheit.checkWith(kelvin, "36.6", "275.70556")
        fahrenheit.checkWith(celsius, "-36.6", "-38.11111")
        fahrenheit.checkWith(fahrenheit, "-36.6", "-36.6")
        fahrenheit.checkWith(kelvin, "-36.6", "235.03889")

        kelvin.checkWith(celsius, "0", "-273.15")
        kelvin.checkWith(fahrenheit, "0", "-459.67")
        kelvin.checkWith(kelvin, "0", "0")
        kelvin.checkWith(celsius, "36.6", "-236.55")
        kelvin.checkWith(fahrenheit, "36.6", "-393.79")
        kelvin.checkWith(kelvin, "36.6", "36.6")
        kelvin.checkWith(celsius, "-36.6", "-309.75")
        kelvin.checkWith(fahrenheit, "-36.6", "-525.55")
        kelvin.checkWith(kelvin, "-36.6", "-36.6")
    }

    @Test
    fun testTime() = testWithUnits {
        attosecond.checkWith(millisecond, "366000000000", "0.00037")
        nanosecond.checkWith(millisecond, "366", "0.00037")
        microsecond.checkWith(millisecond, "366", "0.366")
        millisecond.checkWith(second, "366", "0.366")
        jiffy.checkWith(second, "366", "3.66")
        second.checkWith(millisecond, "366", "366000")
        minute.checkWith(millisecond, "366", "21960000")
        hour.checkWith(millisecond, "366", "1317600000")
        day.checkWith(hour, "366", "8784")
        week.checkWith(hour, "366", "61488")
    }

    @Test
    fun testVolume() = testWithUnits {
        attoliter.checkWith(millimeter, "1507000000000", "0.00151")
        milliliter.checkWith(liter, "1507", "1.507")
        liter.checkWith(us_liquid_gallon, "1507", "398.10728")
        us_liquid_gallon.checkWith(us_fluid_ounce, "1507", "192896")
        us_liquid_quart.checkWith(us_liquid_gallon, "1507", "376.75")
        us_liquid_pint.checkWith(us_liquid_gallon, "1507", "188.375")
        us_legal_cup.checkWith(us_liquid_gallon, "1507", "94.1875")
        us_fluid_ounce.checkWith(us_liquid_gallon, "1507", "11.77344")
        us_tablespoon.checkWith(us_liquid_gallon, "1507", "5.88672")
        us_teaspoon.checkWith(us_liquid_gallon, "1507", "1.96224")
        imperial_gallon.checkWith(us_liquid_gallon, "1507", "1809.83154")
        imperial_quart.checkWith(us_liquid_gallon, "1507", "452.45788")
        imperial_pint.checkWith(us_liquid_gallon, "1507", "226.22894")
        imperial_cup.checkWith(us_liquid_gallon, "1507", "113.11447")
        imperial_fluid_ounce.checkWith(us_liquid_gallon, "1507", "11.31145")
        imperial_tablespoon.checkWith(us_liquid_gallon, "1507", "7.06965")
        imperial_teaspoon.checkWith(us_liquid_gallon, "1507", "2.35655")
        cubic_millimeter.checkWith(us_liquid_gallon, "1507", "0.0004")
        cubic_centimeter.checkWith(us_liquid_gallon, "1507", "0.39811")
        cubic_meter.checkWith(us_liquid_gallon, "1507", "398107.2829")
        cubic_kilometer.checkWith(us_liquid_gallon, "0.0000001507", "39810.72829")
    }

    @Test
    fun testFlux() = testWithUnits {
        maxwell.checkWith(milliweber, "68.2", "0.00068")
        weber.checkWith(milliweber, "68.2", "68200")
        milliweber.checkWith(weber, "68.2", "0.0682")
        microweber.checkWith(milliweber, "68.2", "0.0682")
        kiloweber.checkWith(weber, "68.2", "68200")
        megaweber.checkWith(weber, "68.2", "68200000")
        gigaweber.checkWith(weber, "68.2", "68200000000")
    }

    @Test
    fun testNumberBase() = testWithUnits {
        binary.checkWith(octal, "1000001001", "1011")
        ternary.checkWith(decimal, "10112020111", "69430")
        quaternary.checkWith(quinary, "20321", "4234")
        quinary.checkWith(nonary, "4234", "702")
        senary.checkWith(nonary, "4234", "1274")
        septenary.checkWith(nonary, "4234", "2041")
        octal.checkWith(undecimal, "42343277", "5107945")
        nonary.checkWith(duodecimal, "42343287", "69b9a81")
        decimal.checkWith(duodecimal, "42343287", "12220273")
        undecimal.checkWith(hexadecimal, "4234a287", "4e3f0c2")
        duodecimal.checkWith(hexadecimal, "4234a287", "8f30d07")
        tridecimal.checkWith(hexadecimal, "4234a287", "f9c3ff4")
        tetradecimal.checkWith(hexadecimal, "bb", "a5")
        pentadecimal.checkWith(hexadecimal, "BABE", "9a82")
        hexadecimal.checkWith(quinary, "FADE", "4023342")
    }

    @Test
    fun testElectrostaticCapacitance() = testWithUnits {
        attofarad.checkWith(nanofarad, "364354322342", "364.35432")
        statfarad.checkWith(microfarad, "123312", "0.1372")
        farad.checkWith(kilofarad, "123312", "123.312")
        exafarad.checkWith(petafarad, "123312", "123312000")
        picofarad.checkWith(nanofarad, "11233", "11.233")
        nanofarad.checkWith(millifarad, "11233", "0.01123")
        microfarad.checkWith(nanofarad, "1123433", "1123433000")
        millifarad.checkWith(nanofarad, "112", "112000000")
        kilofarad.checkWith(microfarad, "11132", "11132000000000")
        megafarad.checkWith(kilofarad, "11132", "11132000")
        gigafarad.checkWith(petafarad, "11132", "0.01113")
        petafarad.checkWith(kilofarad, "11132", "11132000000000000")
    }

    @Test
    fun testPrefix() = testWithUnits {
        prefix_quetta.checkWith(prefix_base, "1", "1000000000000000000000000000000")
        prefix_ronna.checkWith(prefix_base, "1", "1000000000000000000000000000")
        prefix_yotta.checkWith(prefix_base, "1", "1000000000000000000000000")
        prefix_zetta.checkWith(prefix_base, "1", "1000000000000000000000")
        prefix_exa.checkWith(prefix_base, "1", "1000000000000000000")
        prefix_peta.checkWith(prefix_base, "1", "1000000000000000")
        prefix_tera.checkWith(prefix_base, "1", "1000000000000")
        prefix_giga.checkWith(prefix_base, "1", "1000000000")
        prefix_mega.checkWith(prefix_base, "1", "1000000")
        prefix_kilo.checkWith(prefix_base, "1", "1000")
        prefix_hecto.checkWith(prefix_base, "1", "100")
        prefix_deca.checkWith(prefix_base, "1", "10")
        prefix_base.checkWith(prefix_base, "77777", "77777")
        prefix_deci.checkWith(prefix_base, "1", "0.1")
        prefix_centi.checkWith(prefix_base, "1", "0.01")
        prefix_milli.checkWith(prefix_base, "1", "0.001")
        prefix_micro.checkWith(prefix_base, "1", "0.000001")
        prefix_nano.checkWith(prefix_base, "1", "0.000000001")
        prefix_pico.checkWith(prefix_base, "1", "0.000000000001")
        prefix_femto.checkWith(prefix_base, "1", "0.000000000000001")
        prefix_atto.checkWith(prefix_base, "1", "0.000000000000000001")
        prefix_zepto.checkWith(prefix_base, "1", "0.000000000000000000001")
        prefix_yocto.checkWith(prefix_base, "1", "0.000000000000000000000001")
        prefix_ronto.checkWith(prefix_base, "1", "0.000000000000000000000000001")
        prefix_quecto.checkWith(prefix_base, "1", "0.000000000000000000000000000001")
    }

    @Test
    fun testForce() = testWithUnits {
        newton.checkWith(pond, "6553", "668220.03436")
        kilonewton.checkWith(kilopound_force, "6553", "1473.173")
        gram_force.checkWith(kilonewton, "6553", "0.06426")
        kilogram_force.checkWith(ton_force, "6553", "6.553")
        ton_force.checkWith(millinewton, "6553", "64262977450")
        millinewton.checkWith(kilonewton, "6553", "0.00655")
        attonewton.checkWith(dyne, "6553123123", "0.00066")
        dyne.checkWith(joule_per_meter, "6553", "0.06553")
        joule_per_meter.checkWith(pond, "6553", "668220.03436")
        joule_per_centimeter.checkWith(kilopond, "6553", "6.6822")
        kilopound_force.checkWith(kilopond, "6553", "2972390.80061")
        pound_force.checkWith(ounce_force, "6553", "104848")
        ounce_force.checkWith(pound_force, "6553", "409.5625")
        pond.checkWith(kilonewton, "6553", "0.06426")
        kilopond.checkWith(kilonewton, "6553", "64.26298")
    }

    @Test
    fun testTorque() = testWithUnits {
        newton_meter.checkWith(dyne_meter, "2134", "213400000")
        newton_centimeter.checkWith(kilogram_force_millimeter, "2345", "2391.23452")
        newton_millimeter.checkWith(pound_force_inch, "2345", "20.755")
        kilonewton_meter.checkWith(kilogram_force_meter, "2134", "217607.43985")
        dyne_meter.checkWith(newton_centimeter, "2134", "2.134")
        dyne_centimeter.checkWith(gram_force_centimeter, "2134", "2.17607")
        dyne_millimeter.checkWith(gram_force_millimeter, "2134", "2.17607")
        kilogram_force_meter.checkWith(dyne_millimeter, "2134", "2092739110000")
        kilogram_force_centimeter.checkWith(gram_force_meter, "2134", "21340")
        kilogram_force_millimeter.checkWith(ounce_force_inch, "2134", "2963.56822")
        gram_force_meter.checkWith(newton_millimeter, "2134", "20927.3911")
        gram_force_centimeter.checkWith(kilogram_force_centimeter, "2134", "2.134")
        gram_force_millimeter.checkWith(kilonewton_meter, "2134", "0.00002")
        ounce_force_foot.checkWith(kilogram_force_millimeter, "2134", "18439.75503")
        ounce_force_inch.checkWith(dyne_meter, "2134", "1506935.1968")
        pound_force_foot.checkWith(newton_millimeter, "2134", "2893315.612")
        pound_force_inch.checkWith(ounce_force_foot, "2134", "2845.33337")
    }

    @Test
    fun testFlowRate() = testWithUnits {
        liter_per_hour.checkWith(milliliter_per_minute, "312", "5200")
        liter_per_minute.checkWith(cubic_foot_per_minute, "312", "11.01818")
        liter_per_second.checkWith(cubic_meter_per_minute, "312", "18.72")
        milliliter_per_hour.checkWith(liter_per_hour, "312", "0.312")
        milliliter_per_minute.checkWith(liter_per_second, "312", "0.0052")
        milliliter_per_second.checkWith(cubic_foot_per_minute, "312", "0.66109")
        cubic_meter_per_hour.checkWith(cubic_foot_per_hour, "312", "11018.17602")
        cubic_meter_per_minute.checkWith(liter_per_minute, "312", "312000")
        cubic_meter_per_second.checkWith(milliliter_per_minute, "312", "18720000000")
        cubic_millimeter_per_hour.checkWith(milliliter_per_minute, "312", "0.0052")
        cubic_millimeter_per_minute.checkWith(liter_per_minute, "312", "0.00031")
        cubic_millimeter_per_second.checkWith(cubic_millimeter_per_minute, "312", "18720")
        cubic_foot_per_hour.checkWith(milliliter_per_hour, "312", "8834856.1367")
        cubic_foot_per_minute.checkWith(cubic_meter_per_hour, "312", "530.09137")
        cubic_foot_per_second.checkWith(cubic_meter_per_second, "312", "8.83486")
        gallons_per_hour_us.checkWith(liter_per_hour, "312", "1181.04848")
        gallons_per_minute_us.checkWith(gallons_per_hour_imperial, "312", "15587.66074")
        gallons_per_second_us.checkWith(cubic_meter_per_minute, "312", "70.86291")
        gallons_per_hour_imperial.checkWith(liter_per_second, "312", "0.39399")
        gallons_per_minute_imperial.checkWith(cubic_foot_per_minute, "312", "50.08962")
        gallons_per_second_imperial.checkWith(cubic_meter_per_minute, "312", "85.1028")
    }

    @Test
    fun testLuminance() = testWithUnits {
        candela_per_square_meter.checkWith(lumen_per_square_foot_per_steradian, "6723", "624.58714")
        candela_per_square_centimeter.checkWith(candela_per_square_inch, "6723", "43374.1068")
        candela_per_square_foot.checkWith(apostilb, "6723", "227343.77056")
        candela_per_square_inch.checkWith(lumen_per_square_centimeter_per_steradian, "6723", "1042.06708")
        kilocandela_per_square_meter.checkWith(lambert, "6723", "2112.09274")
        stilb.checkWith(blondel, "6723", "211209274.10086")
        lumen_per_square_meter_per_steradian.checkWith(lumen_per_square_centimeter_per_steradian, "6723", "0.6723")
        lumen_per_square_centimeter_per_steradian.checkWith(millilambert, "6723", "21120927.41009")
        lumen_per_square_foot_per_steradian.checkWith(skot, "6723", "227343770.55975")
        watt_per_square_centimeter_per_steradian.checkWith(stilb, "6723", "4591809")
        nit.checkWith(candela_per_square_foot, "6723", "624.58714")
        millinit.checkWith(lumen_per_square_foot_per_steradian, "6723", "0.62459")
        lambert.checkWith(foot_lambert, "6723", "6245871.37921")
        millilambert.checkWith(apostilb, "6723", "67230")
        foot_lambert.checkWith(watt_per_square_centimeter_per_steradian, "6723", "0.00337")
        apostilb.checkWith(kilocandela_per_square_meter, "6723", "2.14")
        blondel.checkWith(candela_per_square_centimeter, "6723", "0.214")
        bril.checkWith(skot, "6723", "0.6723")
        skot.checkWith(lumen_per_square_meter_per_steradian, "6723", "2.14")
    }

    private fun String.checkWith(checkingId: String, value: String, expected: String) {
        val unitFrom = allUnitsRepository.getById(this)
        val unitTo = allUnitsRepository.getById(checkingId)

        val actual = if (unitFrom.group == UnitGroup.NUMBER_BASE) {
            (unitFrom as NumberBaseUnit)
                .convertToBase(value, (unitTo as NumberBaseUnit).base)
        } else {
            unitFrom
                .convert(unitTo, BigDecimal(value), 5)
                .toPlainString()
        }
        assertEquals("Failed at $this to $checkingId", expected, actual)
        println("PASSED: $this -> $expected == $actual")
        val content: Set<String> = history.getOrDefault(unitFrom.group, setOf())
        history[unitFrom.group] = content.plus(this)
    }

    @After
    fun after() {
        val unitGroup = history.keys.first()
        // GROUP : testedCount / totalCount
        println("${unitGroup.name} : ${history[unitGroup]?.size} / ${allUnitsRepository.getCollectionByGroup(unitGroup).size}")
    }

    private fun testWithUnits(block: MyUnitIDS.() -> Unit): Unit = with(MyUnitIDS, block = block)
}
