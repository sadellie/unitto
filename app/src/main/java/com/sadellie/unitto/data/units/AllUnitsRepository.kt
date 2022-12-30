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

package com.sadellie.unitto.data.units

import android.content.Context
import com.sadellie.unitto.R
import com.sadellie.unitto.data.preferences.MAX_PRECISION
import com.sadellie.unitto.data.setMinimumRequiredScale
import com.sadellie.unitto.data.trimZeros
import com.sadellie.unitto.data.units.database.MyBasedUnit
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This repository provides access to all collection of units in the app.
 */
@Singleton
class AllUnitsRepository @Inject constructor() {
    /**
     * This is a collection of all available units.
     */
    private val allUnits: List<AbstractUnit> by lazy {
        mapOfCollections.values.flatten()
    }

    /**
     * Mapped [UnitGroup] to [List] of [AbstractUnit]s.
     */
    private val mapOfCollections by lazy {
        hashMapOf(
            UnitGroup.LENGTH to lengthCollection,
            UnitGroup.CURRENCY to currencyCollection,
            UnitGroup.MASS to massCollection,
            UnitGroup.SPEED to speedCollection,
            UnitGroup.TEMPERATURE to temperatureCollection,
            UnitGroup.AREA to areaCollection,
            UnitGroup.TIME to timeCollection,
            UnitGroup.VOLUME to volumeCollection,
            UnitGroup.DATA to dataCollection,
            UnitGroup.PRESSURE to pressureCollection,
            UnitGroup.ACCELERATION to accelerationCollection,
            UnitGroup.ENERGY to energyCollection,
            UnitGroup.POWER to powerCollection,
            UnitGroup.ANGLE to angleCollection,
            UnitGroup.DATA_TRANSFER to dataTransferCollection,
            UnitGroup.FLUX to fluxCollection,
        )
    }

    /**
     * Get [AbstractUnit] by specified id from [MyUnitIDS].
     *
     * @param unitId Unit id from [MyUnitIDS]. Don't use literal strings here.
     * @return [AbstractUnit] from [AllUnitsRepository.allUnits] that has the given id.
     * @throws NoSuchElementException If there is no [AbstractUnit] in [AllUnitsRepository.allUnits]
     * that has the requested unitId.
     */
    fun getById(unitId: String): AbstractUnit {
        return allUnits.first { it.unitId == unitId }
    }

    /**
     * Looks for a collection of units of the given [UnitGroup].
     *
     * @param unitGroup Requested [UnitGroup]
     * @return List of [AbstractUnit]s. Will return null if the is no collection for the specified
     * [UnitGroup].
     *
     * @throws [NoSuchElementException] from [Map.getValue]
     */
    fun getCollectionByGroup(unitGroup: UnitGroup): List<AbstractUnit> {
        return mapOfCollections.getValue(unitGroup)
    }

    /**
     * Filter [AllUnitsRepository.allUnits] and group them.
     *
     * @param hideBrokenCurrencies When set to True will remove [AbstractUnit]s that have
     * [AbstractUnit.isEnabled] set to False, which means that [AbstractUnit] can not be used.
     * @param chosenUnitGroup If provided will scope list to a specific [UnitGroup].
     * @param favoritesOnly When True will filter only [AbstractUnit]s with [AbstractUnit.isFavorite]
     * set to True.
     * @param searchQuery When not empty, will search by [AbstractUnit.renderedName] using [sortByLev].
     * @param allUnitsGroups All [UnitGroup]s. Determines which units will be used for filtering.
     * @return Grouped by [UnitGroup] list of [AbstractUnit]s.
     */
    fun filterUnits(
        hideBrokenCurrencies: Boolean,
        chosenUnitGroup: UnitGroup?,
        favoritesOnly: Boolean,
        searchQuery: String,
        allUnitsGroups: List<UnitGroup>
    ): Map<UnitGroup, List<AbstractUnit>> {
        var basicFilteredUnits: Sequence<AbstractUnit> = if (chosenUnitGroup == null) {
            allUnits.filter { it.group in allUnitsGroups }
        } else {
            val collection = getCollectionByGroup(chosenUnitGroup)
            collection
        }.asSequence()

        if (favoritesOnly) {
            basicFilteredUnits = basicFilteredUnits.filter { it.isFavorite }
        }
        if (hideBrokenCurrencies) {
            basicFilteredUnits = basicFilteredUnits.filter { it.isEnabled }
        }
        val unitsToShow = if (searchQuery.isEmpty()) {
            // Query is empty, i.e. we want to see all units and they need to be sorted by usage
            basicFilteredUnits.sortedByDescending { it.counter }
        } else {
            // We are searching for a specific unit, we don't care about popularity
            // We need search accuracy
            basicFilteredUnits.sortByLev(searchQuery)
        }

        return unitsToShow.groupBy { it.group }
    }

    /**
     * Maps data from database to [allUnits] item: favorites, counters, renderedNames and etc.
     *
     * @param context [Context] that is used to fill [AbstractUnit.renderedName]. Rendered names are used when
     * searching.
     * @param allBasedUnits List from database. See: [MyBasedUnit].
     */
    fun loadFromDatabase(context: Context, allBasedUnits: List<MyBasedUnit>) {
        allUnits.forEach {
            // Loading unit names so that we can search through them
            it.renderedName = context.getString(it.displayName)
            val based = allBasedUnits.firstOrNull { based -> based.unitId == it.unitId }
            // Loading paired units
            it.pairedUnit = based?.pairedUnitId
            // Loading favorite state
            it.isFavorite = based?.isFavorite ?: false
            it.counter = based?.frequency ?: 0
        }
    }

    /**
     * Update [AbstractUnit.basicUnit] properties for currencies from [currencyCollection].
     *
     * @param conversions Map: [AbstractUnit.unitId] and [BigDecimal] that will replace current
     * [AbstractUnit.basicUnit].
     */
    fun updateBasicUnitsForCurrencies(
        conversions: Map<String, BigDecimal>
    ) {
        getCollectionByGroup(UnitGroup.CURRENCY).forEach {
            // Getting rates from map. We set ZERO as default so that it can be skipped
            val rate = conversions.getOrElse(it.unitId) { BigDecimal.ZERO }
            // We make sure that we don't divide by zero
            if (rate > BigDecimal.ZERO) {
                it.isEnabled = true
                it.basicUnit = BigDecimal.ONE.setScale(MAX_PRECISION).div(rate)
            } else {
                // Hiding broken currencies
                it.isEnabled = false
            }
        }
    }

    // NOTE: Ignore formatting below it's easier to read this lines as table
    private val lengthCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.attometer,                 BigDecimal.valueOf(1.0),                             UnitGroup.LENGTH, R.string.attometer,                 R.string.attometer_short),
            MyUnit(MyUnitIDS.nanometer,                 BigDecimal.valueOf(1.0E+9),                          UnitGroup.LENGTH, R.string.nanometer,                 R.string.nanometer_short),
            MyUnit(MyUnitIDS.micrometer,                BigDecimal.valueOf(1.0E+12),                         UnitGroup.LENGTH, R.string.micrometer,                R.string.micrometer_short),
            MyUnit(MyUnitIDS.millimeter,                BigDecimal.valueOf(1.0E+15),                         UnitGroup.LENGTH, R.string.millimeter,                R.string.millimeter_short),
            MyUnit(MyUnitIDS.centimeter,                BigDecimal.valueOf(1.0E+16),                         UnitGroup.LENGTH, R.string.centimeter,                R.string.centimeter_short),
            MyUnit(MyUnitIDS.decimeter,                 BigDecimal.valueOf(1.0E+17),                         UnitGroup.LENGTH, R.string.decimeter,                 R.string.decimeter_short),
            MyUnit(MyUnitIDS.meter,                     BigDecimal.valueOf(1.0E+18),                         UnitGroup.LENGTH, R.string.meter,                     R.string.meter_short),
            MyUnit(MyUnitIDS.kilometer,                 BigDecimal.valueOf(1.0E+21),                         UnitGroup.LENGTH, R.string.kilometer,                 R.string.kilometer_short),
            MyUnit(MyUnitIDS.inch,                      BigDecimal.valueOf(25_400_000_000_000_000),          UnitGroup.LENGTH, R.string.inch,                      R.string.inch_short),
            MyUnit(MyUnitIDS.foot,                      BigDecimal.valueOf(304_800_000_000_002_200),         UnitGroup.LENGTH, R.string.foot,                      R.string.foot_short),
            MyUnit(MyUnitIDS.yard,                      BigDecimal.valueOf(914_400_000_000_006_400),         UnitGroup.LENGTH, R.string.yard,                      R.string.yard_short),
            MyUnit(MyUnitIDS.mile,                      BigDecimal.valueOf(1_609_344_000_000_010_500_000.0), UnitGroup.LENGTH, R.string.mile,                      R.string.mile_short),
            MyUnit(MyUnitIDS.light_year,                BigDecimal.valueOf(9.460730472E+33),                 UnitGroup.LENGTH, R.string.light_year,                R.string.light_year_short),
            MyUnit(MyUnitIDS.parsec,                    BigDecimal.valueOf(3.08567758149136E+34),            UnitGroup.LENGTH, R.string.parsec,                    R.string.parsec_short),
            MyUnit(MyUnitIDS.kiloparsec,                BigDecimal.valueOf(3.08567758149136E+37),            UnitGroup.LENGTH, R.string.kiloparsec,                R.string.kiloparsec_short),
            MyUnit(MyUnitIDS.megaparsec,                BigDecimal.valueOf(3.08567758149136E+40),            UnitGroup.LENGTH, R.string.megaparsec,                R.string.megaparsec_short),
            MyUnit(MyUnitIDS.mercury_equatorial_radius, BigDecimal.valueOf(2.4397E+24),                      UnitGroup.LENGTH, R.string.mercury_equatorial_radius, R.string.mercury_equatorial_radius_short),
            MyUnit(MyUnitIDS.venus_equatorial_radius,   BigDecimal.valueOf(6.0518E+24),                      UnitGroup.LENGTH, R.string.venus_equatorial_radius,   R.string.venus_equatorial_radius_short),
            MyUnit(MyUnitIDS.earth_equatorial_radius,   BigDecimal.valueOf(6.371E+24),                       UnitGroup.LENGTH, R.string.earth_equatorial_radius,   R.string.earth_equatorial_radius_short),
            MyUnit(MyUnitIDS.mars_equatorial_radius,    BigDecimal.valueOf(3.3895E+24),                      UnitGroup.LENGTH, R.string.mars_equatorial_radius,    R.string.mars_equatorial_radius_short),
            MyUnit(MyUnitIDS.jupiter_equatorial_radius, BigDecimal.valueOf(6.9911E+25),                      UnitGroup.LENGTH, R.string.jupiter_equatorial_radius, R.string.jupiter_equatorial_radius_short),
            MyUnit(MyUnitIDS.saturn_equatorial_radius,  BigDecimal.valueOf(5.8232E+25),                      UnitGroup.LENGTH, R.string.saturn_equatorial_radius,  R.string.saturn_equatorial_radius_short),
            MyUnit(MyUnitIDS.uranus_equatorial_radius,  BigDecimal.valueOf(2.5362E+25),                      UnitGroup.LENGTH, R.string.uranus_equatorial_radius,  R.string.uranus_equatorial_radius_short),
            MyUnit(MyUnitIDS.neptune_equatorial_radius, BigDecimal.valueOf(2.4622E+25),                      UnitGroup.LENGTH, R.string.neptune_equatorial_radius, R.string.neptune_equatorial_radius_short),
            MyUnit(MyUnitIDS.sun_equatorial_radius,     BigDecimal.valueOf(6.95508E+26),                     UnitGroup.LENGTH, R.string.sun_equatorial_radius,     R.string.sun_equatorial_radius_short),
        )
    }
    private val currencyCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.currency_1inch,    BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_1inch,    R.string.currency_1inch_short),
            MyUnit(MyUnitIDS.currency_ada,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ada,      R.string.currency_ada_short),
            MyUnit(MyUnitIDS.currency_aed,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_aed,      R.string.currency_aed_short),
            MyUnit(MyUnitIDS.currency_afn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_afn,      R.string.currency_afn_short),
            MyUnit(MyUnitIDS.currency_algo,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_algo,     R.string.currency_algo_short),
            MyUnit(MyUnitIDS.currency_all,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_all,      R.string.currency_all_short),
            MyUnit(MyUnitIDS.currency_amd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_amd,      R.string.currency_amd_short),
            MyUnit(MyUnitIDS.currency_ang,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ang,      R.string.currency_ang_short),
            MyUnit(MyUnitIDS.currency_aoa,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_aoa,      R.string.currency_aoa_short),
            MyUnit(MyUnitIDS.currency_ars,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ars,      R.string.currency_ars_short),
            MyUnit(MyUnitIDS.currency_atom,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_atom,     R.string.currency_atom_short),
            MyUnit(MyUnitIDS.currency_aud,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_aud,      R.string.currency_aud_short),
            MyUnit(MyUnitIDS.currency_avax,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_avax,     R.string.currency_avax_short),
            MyUnit(MyUnitIDS.currency_awg,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_awg,      R.string.currency_awg_short),
            MyUnit(MyUnitIDS.currency_azn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_azn,      R.string.currency_azn_short),
            MyUnit(MyUnitIDS.currency_bam,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bam,      R.string.currency_bam_short),
            MyUnit(MyUnitIDS.currency_bbd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bbd,      R.string.currency_bbd_short),
            MyUnit(MyUnitIDS.currency_bch,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bch,      R.string.currency_bch_short),
            MyUnit(MyUnitIDS.currency_bdt,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bdt,      R.string.currency_bdt_short),
            MyUnit(MyUnitIDS.currency_bgn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bgn,      R.string.currency_bgn_short),
            MyUnit(MyUnitIDS.currency_bhd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bhd,      R.string.currency_bhd_short),
            MyUnit(MyUnitIDS.currency_bif,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bif,      R.string.currency_bif_short),
            MyUnit(MyUnitIDS.currency_bmd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bmd,      R.string.currency_bmd_short),
            MyUnit(MyUnitIDS.currency_bnb,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bnb,      R.string.currency_bnb_short),
            MyUnit(MyUnitIDS.currency_bnd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bnd,      R.string.currency_bnd_short),
            MyUnit(MyUnitIDS.currency_bob,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bob,      R.string.currency_bob_short),
            MyUnit(MyUnitIDS.currency_brl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_brl,      R.string.currency_brl_short),
            MyUnit(MyUnitIDS.currency_bsd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bsd,      R.string.currency_bsd_short),
            MyUnit(MyUnitIDS.currency_btc,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_btc,      R.string.currency_btc_short),
            MyUnit(MyUnitIDS.currency_btn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_btn,      R.string.currency_btn_short),
            MyUnit(MyUnitIDS.currency_busd,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_busd,     R.string.currency_busd_short),
            MyUnit(MyUnitIDS.currency_bwp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bwp,      R.string.currency_bwp_short),
            MyUnit(MyUnitIDS.currency_byn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_byn,      R.string.currency_byn_short),
            MyUnit(MyUnitIDS.currency_byr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_byr,      R.string.currency_byr_short),
            MyUnit(MyUnitIDS.currency_bzd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_bzd,      R.string.currency_bzd_short),
            MyUnit(MyUnitIDS.currency_cad,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cad,      R.string.currency_cad_short),
            MyUnit(MyUnitIDS.currency_cdf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cdf,      R.string.currency_cdf_short),
            MyUnit(MyUnitIDS.currency_chf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_chf,      R.string.currency_chf_short),
            MyUnit(MyUnitIDS.currency_chz,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_chz,      R.string.currency_chz_short),
            MyUnit(MyUnitIDS.currency_clf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_clf,      R.string.currency_clf_short),
            MyUnit(MyUnitIDS.currency_clp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_clp,      R.string.currency_clp_short),
            MyUnit(MyUnitIDS.currency_cny,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cny,      R.string.currency_cny_short),
            MyUnit(MyUnitIDS.currency_cop,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cop,      R.string.currency_cop_short),
            MyUnit(MyUnitIDS.currency_crc,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_crc,      R.string.currency_crc_short),
            MyUnit(MyUnitIDS.currency_cro,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cro,      R.string.currency_cro_short),
            MyUnit(MyUnitIDS.currency_cuc,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cuc,      R.string.currency_cuc_short),
            MyUnit(MyUnitIDS.currency_cup,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cup,      R.string.currency_cup_short),
            MyUnit(MyUnitIDS.currency_cve,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_cve,      R.string.currency_cve_short),
            MyUnit(MyUnitIDS.currency_czk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_czk,      R.string.currency_czk_short),
            MyUnit(MyUnitIDS.currency_dai,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_dai,      R.string.currency_dai_short),
            MyUnit(MyUnitIDS.currency_djf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_djf,      R.string.currency_djf_short),
            MyUnit(MyUnitIDS.currency_dkk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_dkk,      R.string.currency_dkk_short),
            MyUnit(MyUnitIDS.currency_doge,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_doge,     R.string.currency_doge_short),
            MyUnit(MyUnitIDS.currency_dop,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_dop,      R.string.currency_dop_short),
            MyUnit(MyUnitIDS.currency_dot,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_dot,      R.string.currency_dot_short),
            MyUnit(MyUnitIDS.currency_dzd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_dzd,      R.string.currency_dzd_short),
            MyUnit(MyUnitIDS.currency_egld,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_egld,     R.string.currency_egld_short),
            MyUnit(MyUnitIDS.currency_egp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_egp,      R.string.currency_egp_short),
            MyUnit(MyUnitIDS.currency_enj,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_enj,      R.string.currency_enj_short),
            MyUnit(MyUnitIDS.currency_ern,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ern,      R.string.currency_ern_short),
            MyUnit(MyUnitIDS.currency_etb,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_etb,      R.string.currency_etb_short),
            MyUnit(MyUnitIDS.currency_etc,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_etc,      R.string.currency_etc_short),
            MyUnit(MyUnitIDS.currency_eth,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_eth,      R.string.currency_eth_short),
            MyUnit(MyUnitIDS.currency_eur,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_eur,      R.string.currency_eur_short),
            MyUnit(MyUnitIDS.currency_fil,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_fil,      R.string.currency_fil_short),
            MyUnit(MyUnitIDS.currency_fjd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_fjd,      R.string.currency_fjd_short),
            MyUnit(MyUnitIDS.currency_fkp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_fkp,      R.string.currency_fkp_short),
            MyUnit(MyUnitIDS.currency_ftt,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ftt,      R.string.currency_ftt_short),
            MyUnit(MyUnitIDS.currency_gbp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gbp,      R.string.currency_gbp_short),
            MyUnit(MyUnitIDS.currency_gel,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gel,      R.string.currency_gel_short),
            MyUnit(MyUnitIDS.currency_ggp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ggp,      R.string.currency_ggp_short),
            MyUnit(MyUnitIDS.currency_ghs,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ghs,      R.string.currency_ghs_short),
            MyUnit(MyUnitIDS.currency_gip,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gip,      R.string.currency_gip_short),
            MyUnit(MyUnitIDS.currency_gmd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gmd,      R.string.currency_gmd_short),
            MyUnit(MyUnitIDS.currency_gnf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gnf,      R.string.currency_gnf_short),
            MyUnit(MyUnitIDS.currency_grt,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_grt,      R.string.currency_grt_short),
            MyUnit(MyUnitIDS.currency_gtq,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gtq,      R.string.currency_gtq_short),
            MyUnit(MyUnitIDS.currency_gyd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_gyd,      R.string.currency_gyd_short),
            MyUnit(MyUnitIDS.currency_hkd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_hkd,      R.string.currency_hkd_short),
            MyUnit(MyUnitIDS.currency_hnl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_hnl,      R.string.currency_hnl_short),
            MyUnit(MyUnitIDS.currency_hrk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_hrk,      R.string.currency_hrk_short),
            MyUnit(MyUnitIDS.currency_htg,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_htg,      R.string.currency_htg_short),
            MyUnit(MyUnitIDS.currency_huf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_huf,      R.string.currency_huf_short),
            MyUnit(MyUnitIDS.currency_icp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_icp,      R.string.currency_icp_short),
            MyUnit(MyUnitIDS.currency_idr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_idr,      R.string.currency_idr_short),
            MyUnit(MyUnitIDS.currency_ils,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ils,      R.string.currency_ils_short),
            MyUnit(MyUnitIDS.currency_imp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_imp,      R.string.currency_imp_short),
            MyUnit(MyUnitIDS.currency_inj,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_inj,      R.string.currency_inj_short),
            MyUnit(MyUnitIDS.currency_inr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_inr,      R.string.currency_inr_short),
            MyUnit(MyUnitIDS.currency_iqd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_iqd,      R.string.currency_iqd_short),
            MyUnit(MyUnitIDS.currency_irr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_irr,      R.string.currency_irr_short),
            MyUnit(MyUnitIDS.currency_isk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_isk,      R.string.currency_isk_short),
            MyUnit(MyUnitIDS.currency_jep,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_jep,      R.string.currency_jep_short),
            MyUnit(MyUnitIDS.currency_jmd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_jmd,      R.string.currency_jmd_short),
            MyUnit(MyUnitIDS.currency_jod,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_jod,      R.string.currency_jod_short),
            MyUnit(MyUnitIDS.currency_jpy,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_jpy,      R.string.currency_jpy_short),
            MyUnit(MyUnitIDS.currency_kes,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kes,      R.string.currency_kes_short),
            MyUnit(MyUnitIDS.currency_kgs,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kgs,      R.string.currency_kgs_short),
            MyUnit(MyUnitIDS.currency_khr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_khr,      R.string.currency_khr_short),
            MyUnit(MyUnitIDS.currency_kmf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kmf,      R.string.currency_kmf_short),
            MyUnit(MyUnitIDS.currency_kpw,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kpw,      R.string.currency_kpw_short),
            MyUnit(MyUnitIDS.currency_krw,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_krw,      R.string.currency_krw_short),
            MyUnit(MyUnitIDS.currency_ksm,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ksm,      R.string.currency_ksm_short),
            MyUnit(MyUnitIDS.currency_kwd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kwd,      R.string.currency_kwd_short),
            MyUnit(MyUnitIDS.currency_kyd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kyd,      R.string.currency_kyd_short),
            MyUnit(MyUnitIDS.currency_kzt,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_kzt,      R.string.currency_kzt_short),
            MyUnit(MyUnitIDS.currency_lak,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lak,      R.string.currency_lak_short),
            MyUnit(MyUnitIDS.currency_lbp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lbp,      R.string.currency_lbp_short),
            MyUnit(MyUnitIDS.currency_link,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_link,     R.string.currency_link_short),
            MyUnit(MyUnitIDS.currency_lkr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lkr,      R.string.currency_lkr_short),
            MyUnit(MyUnitIDS.currency_lrd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lrd,      R.string.currency_lrd_short),
            MyUnit(MyUnitIDS.currency_lsl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lsl,      R.string.currency_lsl_short),
            MyUnit(MyUnitIDS.currency_ltc,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ltc,      R.string.currency_ltc_short),
            MyUnit(MyUnitIDS.currency_ltl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ltl,      R.string.currency_ltl_short),
            MyUnit(MyUnitIDS.currency_luna,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_luna,     R.string.currency_luna_short),
            MyUnit(MyUnitIDS.currency_lvl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lvl,      R.string.currency_lvl_short),
            MyUnit(MyUnitIDS.currency_lyd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_lyd,      R.string.currency_lyd_short),
            MyUnit(MyUnitIDS.currency_mad,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mad,      R.string.currency_mad_short),
            MyUnit(MyUnitIDS.currency_matic,    BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_matic,    R.string.currency_matic_short),
            MyUnit(MyUnitIDS.currency_mdl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mdl,      R.string.currency_mdl_short),
            MyUnit(MyUnitIDS.currency_mga,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mga,      R.string.currency_mga_short),
            MyUnit(MyUnitIDS.currency_mkd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mkd,      R.string.currency_mkd_short),
            MyUnit(MyUnitIDS.currency_mmk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mmk,      R.string.currency_mmk_short),
            MyUnit(MyUnitIDS.currency_mnt,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mnt,      R.string.currency_mnt_short),
            MyUnit(MyUnitIDS.currency_mop,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mop,      R.string.currency_mop_short),
            MyUnit(MyUnitIDS.currency_mro,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mro,      R.string.currency_mro_short),
            MyUnit(MyUnitIDS.currency_mur,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mur,      R.string.currency_mur_short),
            MyUnit(MyUnitIDS.currency_mvr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mvr,      R.string.currency_mvr_short),
            MyUnit(MyUnitIDS.currency_mwk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mwk,      R.string.currency_mwk_short),
            MyUnit(MyUnitIDS.currency_mxn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mxn,      R.string.currency_mxn_short),
            MyUnit(MyUnitIDS.currency_myr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_myr,      R.string.currency_myr_short),
            MyUnit(MyUnitIDS.currency_mzn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_mzn,      R.string.currency_mzn_short),
            MyUnit(MyUnitIDS.currency_nad,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_nad,      R.string.currency_nad_short),
            MyUnit(MyUnitIDS.currency_ngn,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ngn,      R.string.currency_ngn_short),
            MyUnit(MyUnitIDS.currency_nio,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_nio,      R.string.currency_nio_short),
            MyUnit(MyUnitIDS.currency_nok,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_nok,      R.string.currency_nok_short),
            MyUnit(MyUnitIDS.currency_npr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_npr,      R.string.currency_npr_short),
            MyUnit(MyUnitIDS.currency_nzd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_nzd,      R.string.currency_nzd_short),
            MyUnit(MyUnitIDS.currency_omr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_omr,      R.string.currency_omr_short),
            MyUnit(MyUnitIDS.currency_one,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_one,      R.string.currency_one_short),
            MyUnit(MyUnitIDS.currency_pab,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_pab,      R.string.currency_pab_short),
            MyUnit(MyUnitIDS.currency_pen,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_pen,      R.string.currency_pen_short),
            MyUnit(MyUnitIDS.currency_pgk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_pgk,      R.string.currency_pgk_short),
            MyUnit(MyUnitIDS.currency_php,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_php,      R.string.currency_php_short),
            MyUnit(MyUnitIDS.currency_pkr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_pkr,      R.string.currency_pkr_short),
            MyUnit(MyUnitIDS.currency_pln,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_pln,      R.string.currency_pln_short),
            MyUnit(MyUnitIDS.currency_pyg,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_pyg,      R.string.currency_pyg_short),
            MyUnit(MyUnitIDS.currency_qar,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_qar,      R.string.currency_qar_short),
            MyUnit(MyUnitIDS.currency_ron,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ron,      R.string.currency_ron_short),
            MyUnit(MyUnitIDS.currency_rsd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_rsd,      R.string.currency_rsd_short),
            MyUnit(MyUnitIDS.currency_rub,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_rub,      R.string.currency_rub_short),
            MyUnit(MyUnitIDS.currency_rwf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_rwf,      R.string.currency_rwf_short),
            MyUnit(MyUnitIDS.currency_sar,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sar,      R.string.currency_sar_short),
            MyUnit(MyUnitIDS.currency_sbd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sbd,      R.string.currency_sbd_short),
            MyUnit(MyUnitIDS.currency_scr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_scr,      R.string.currency_scr_short),
            MyUnit(MyUnitIDS.currency_sdg,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sdg,      R.string.currency_sdg_short),
            MyUnit(MyUnitIDS.currency_sek,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sek,      R.string.currency_sek_short),
            MyUnit(MyUnitIDS.currency_sgd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sgd,      R.string.currency_sgd_short),
            MyUnit(MyUnitIDS.currency_shib,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_shib,     R.string.currency_shib_short),
            MyUnit(MyUnitIDS.currency_shp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_shp,      R.string.currency_shp_short),
            MyUnit(MyUnitIDS.currency_sll,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sll,      R.string.currency_sll_short),
            MyUnit(MyUnitIDS.currency_sol,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sol,      R.string.currency_sol_short),
            MyUnit(MyUnitIDS.currency_sos,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_sos,      R.string.currency_sos_short),
            MyUnit(MyUnitIDS.currency_srd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_srd,      R.string.currency_srd_short),
            MyUnit(MyUnitIDS.currency_std,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_std,      R.string.currency_std_short),
            MyUnit(MyUnitIDS.currency_svc,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_svc,      R.string.currency_svc_short),
            MyUnit(MyUnitIDS.currency_syp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_syp,      R.string.currency_syp_short),
            MyUnit(MyUnitIDS.currency_szl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_szl,      R.string.currency_szl_short),
            MyUnit(MyUnitIDS.currency_thb,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_thb,      R.string.currency_thb_short),
            MyUnit(MyUnitIDS.currency_theta,    BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_theta,    R.string.currency_theta_short),
            MyUnit(MyUnitIDS.currency_tjs,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_tjs,      R.string.currency_tjs_short),
            MyUnit(MyUnitIDS.currency_tmt,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_tmt,      R.string.currency_tmt_short),
            MyUnit(MyUnitIDS.currency_tnd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_tnd,      R.string.currency_tnd_short),
            MyUnit(MyUnitIDS.currency_top,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_top,      R.string.currency_top_short),
            MyUnit(MyUnitIDS.currency_trx,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_trx,      R.string.currency_trx_short),
            MyUnit(MyUnitIDS.currency_try,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_try,      R.string.currency_try_short),
            MyUnit(MyUnitIDS.currency_ttd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ttd,      R.string.currency_ttd_short),
            MyUnit(MyUnitIDS.currency_twd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_twd,      R.string.currency_twd_short),
            MyUnit(MyUnitIDS.currency_tzs,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_tzs,      R.string.currency_tzs_short),
            MyUnit(MyUnitIDS.currency_uah,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_uah,      R.string.currency_uah_short),
            MyUnit(MyUnitIDS.currency_ugx,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_ugx,      R.string.currency_ugx_short),
            MyUnit(MyUnitIDS.currency_uni,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_uni,      R.string.currency_uni_short),
            MyUnit(MyUnitIDS.currency_usd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_usd,      R.string.currency_usd_short),
            MyUnit(MyUnitIDS.currency_usdc,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_usdc,     R.string.currency_usdc_short),
            MyUnit(MyUnitIDS.currency_usdt,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_usdt,     R.string.currency_usdt_short),
            MyUnit(MyUnitIDS.currency_uyu,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_uyu,      R.string.currency_uyu_short),
            MyUnit(MyUnitIDS.currency_uzs,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_uzs,      R.string.currency_uzs_short),
            MyUnit(MyUnitIDS.currency_vef,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_vef,      R.string.currency_vef_short),
            MyUnit(MyUnitIDS.currency_vet,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_vet,      R.string.currency_vet_short),
            MyUnit(MyUnitIDS.currency_vnd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_vnd,      R.string.currency_vnd_short),
            MyUnit(MyUnitIDS.currency_vuv,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_vuv,      R.string.currency_vuv_short),
            MyUnit(MyUnitIDS.currency_wbtc,     BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_wbtc,     R.string.currency_wbtc_short),
            MyUnit(MyUnitIDS.currency_wst,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_wst,      R.string.currency_wst_short),
            MyUnit(MyUnitIDS.currency_xaf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xaf,      R.string.currency_xaf_short),
            MyUnit(MyUnitIDS.currency_xag,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xag,      R.string.currency_xag_short),
            MyUnit(MyUnitIDS.currency_xau,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xau,      R.string.currency_xau_short),
            MyUnit(MyUnitIDS.currency_xcd,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xcd,      R.string.currency_xcd_short),
            MyUnit(MyUnitIDS.currency_xdr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xdr,      R.string.currency_xdr_short),
            MyUnit(MyUnitIDS.currency_xlm,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xlm,      R.string.currency_xlm_short),
            MyUnit(MyUnitIDS.currency_xmr,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xmr,      R.string.currency_xmr_short),
            MyUnit(MyUnitIDS.currency_xof,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xof,      R.string.currency_xof_short),
            MyUnit(MyUnitIDS.currency_xpf,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xpf,      R.string.currency_xpf_short),
            MyUnit(MyUnitIDS.currency_xrp,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_xrp,      R.string.currency_xrp_short),
            MyUnit(MyUnitIDS.currency_yer,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_yer,      R.string.currency_yer_short),
            MyUnit(MyUnitIDS.currency_zar,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_zar,      R.string.currency_zar_short),
            MyUnit(MyUnitIDS.currency_zmk,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_zmk,      R.string.currency_zmk_short),
            MyUnit(MyUnitIDS.currency_zmw,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_zmw,      R.string.currency_zmw_short),
            MyUnit(MyUnitIDS.currency_zwl,      BigDecimal.ONE, UnitGroup.CURRENCY, R.string.currency_zwl,      R.string.currency_zwl_short),
        )
    }
    private val massCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.electron_mass_rest, BigDecimal.valueOf(9.1093897E-28), UnitGroup.MASS, R.string.electron_mass_rest, R.string.electron_mass_rest_short),
            MyUnit(MyUnitIDS.atomic_mass_unit,   BigDecimal.valueOf(1.6605402E-24), UnitGroup.MASS, R.string.atomic_mass_unit,   R.string.atomic_mass_unit_short),
            MyUnit(MyUnitIDS.milligram,          BigDecimal.valueOf(1E-3),          UnitGroup.MASS, R.string.milligram,          R.string.milligram_short),
            MyUnit(MyUnitIDS.gram,               BigDecimal.valueOf(1),             UnitGroup.MASS, R.string.gram,               R.string.gram_short),
            MyUnit(MyUnitIDS.kilogram,           BigDecimal.valueOf(1E+3),          UnitGroup.MASS, R.string.kilogram,           R.string.kilogram_short),
            MyUnit(MyUnitIDS.metric_ton,         BigDecimal.valueOf(1E+6),          UnitGroup.MASS, R.string.metric_ton,         R.string.metric_ton_short),
            MyUnit(MyUnitIDS.imperial_ton,       BigDecimal.valueOf(1016046.9088),  UnitGroup.MASS, R.string.imperial_ton,       R.string.imperial_ton_short),
            MyUnit(MyUnitIDS.ounce,              BigDecimal.valueOf(28.349523125),  UnitGroup.MASS, R.string.ounce,              R.string.ounce_short),
            MyUnit(MyUnitIDS.carat,              BigDecimal.valueOf(0.2),           UnitGroup.MASS, R.string.carat,              R.string.carat_short),
            MyUnit(MyUnitIDS.pound,              BigDecimal.valueOf(453.59237),     UnitGroup.MASS, R.string.pound,              R.string.pound_short),
            MyUnit(MyUnitIDS.mercury_mass,       BigDecimal.valueOf(3.30104E+26),   UnitGroup.MASS, R.string.mercury_mass,       R.string.mercury_mass_short),
            MyUnit(MyUnitIDS.venus_mass,         BigDecimal.valueOf(4.86732E+27),   UnitGroup.MASS, R.string.venus_mass,         R.string.venus_mass_short),
            MyUnit(MyUnitIDS.earth_mass,         BigDecimal.valueOf(5.97219E+27),   UnitGroup.MASS, R.string.earth_mass,         R.string.earth_mass_short),
            MyUnit(MyUnitIDS.mars_mass,          BigDecimal.valueOf(6.41693E+26),   UnitGroup.MASS, R.string.mars_mass,          R.string.mars_mass_short),
            MyUnit(MyUnitIDS.jupiter_mass,       BigDecimal.valueOf(1.89813E+30),   UnitGroup.MASS, R.string.jupiter_mass,       R.string.jupiter_mass_short),
            MyUnit(MyUnitIDS.saturn_mass,        BigDecimal.valueOf(5.68319E+29),   UnitGroup.MASS, R.string.saturn_mass,        R.string.saturn_mass_short),
            MyUnit(MyUnitIDS.uranus_mass,        BigDecimal.valueOf(8.68103E+28),   UnitGroup.MASS, R.string.uranus_mass,        R.string.uranus_mass_short),
            MyUnit(MyUnitIDS.neptune_mass,       BigDecimal.valueOf(1.0241E+29),    UnitGroup.MASS, R.string.neptune_mass,       R.string.neptune_mass_short),
            MyUnit(MyUnitIDS.sun_mass,           BigDecimal.valueOf(1.9891E+33),    UnitGroup.MASS, R.string.sun_mass,           R.string.sun_mass_short),
        )
    }
    private val speedCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.millimeter_per_hour,           BigDecimal.valueOf(1),                      UnitGroup.SPEED,    R.string.millimeter_per_hour,           R.string.millimeter_per_hour_short),
            MyUnit(MyUnitIDS.millimeter_per_minute,         BigDecimal.valueOf(60),                     UnitGroup.SPEED,    R.string.millimeter_per_minute,         R.string.millimeter_per_minute_short),
            MyUnit(MyUnitIDS.millimeter_per_second,         BigDecimal.valueOf(3_600),                  UnitGroup.SPEED,    R.string.millimeter_per_second,         R.string.millimeter_per_second_short),
            MyUnit(MyUnitIDS.centimeter_per_hour,           BigDecimal.valueOf(10),                     UnitGroup.SPEED,    R.string.centimeter_per_hour,           R.string.centimeter_per_hour_short),
            MyUnit(MyUnitIDS.centimeter_per_minute,         BigDecimal.valueOf(600),                    UnitGroup.SPEED,    R.string.centimeter_per_minute,         R.string.centimeter_per_minute_short),
            MyUnit(MyUnitIDS.centimeter_per_second,         BigDecimal.valueOf(36_000),                 UnitGroup.SPEED,    R.string.centimeter_per_second,         R.string.centimeter_per_second_short),
            MyUnit(MyUnitIDS.meter_per_hour,                BigDecimal.valueOf(1_000),                  UnitGroup.SPEED,    R.string.meter_per_hour,                R.string.meter_per_hour_short),
            MyUnit(MyUnitIDS.meter_per_minute,              BigDecimal.valueOf(60_000),                 UnitGroup.SPEED,    R.string.meter_per_minute,              R.string.meter_per_minute_short),
            MyUnit(MyUnitIDS.meter_per_second,              BigDecimal.valueOf(3_600_000),              UnitGroup.SPEED,    R.string.meter_per_second,              R.string.meter_per_second_short),
            MyUnit(MyUnitIDS.kilometer_per_hour,            BigDecimal.valueOf(1_000_000),              UnitGroup.SPEED,    R.string.kilometer_per_hour,            R.string.kilometer_per_hour_short),
            MyUnit(MyUnitIDS.kilometer_per_minute,          BigDecimal.valueOf(60_000_000),             UnitGroup.SPEED,    R.string.kilometer_per_minute,          R.string.kilometer_per_minute_short),
            MyUnit(MyUnitIDS.kilometer_per_second,          BigDecimal.valueOf(3_600_000_000),          UnitGroup.SPEED,    R.string.kilometer_per_second,          R.string.kilometer_per_second_short),
            MyUnit(MyUnitIDS.foot_per_hour,                 BigDecimal.valueOf(304.8),                  UnitGroup.SPEED,    R.string.foot_per_hour,                 R.string.foot_per_hour_short),
            MyUnit(MyUnitIDS.foot_per_minute,               BigDecimal.valueOf(18_288),                 UnitGroup.SPEED,    R.string.foot_per_minute,               R.string.foot_per_minute_short),
            MyUnit(MyUnitIDS.foot_per_second,               BigDecimal.valueOf(1_097_280),              UnitGroup.SPEED,    R.string.foot_per_second,               R.string.foot_per_second_short),
            MyUnit(MyUnitIDS.yard_per_hour,                 BigDecimal.valueOf(914.4),                  UnitGroup.SPEED,    R.string.yard_per_hour,                 R.string.yard_per_hour_short),
            MyUnit(MyUnitIDS.yard_per_minute,               BigDecimal.valueOf(54_864),                 UnitGroup.SPEED,    R.string.yard_per_minute,               R.string.yard_per_minute_short),
            MyUnit(MyUnitIDS.yard_per_second,               BigDecimal.valueOf(3_291_840),              UnitGroup.SPEED,    R.string.yard_per_second,               R.string.yard_per_second_short),
            MyUnit(MyUnitIDS.mile_per_hour,                 BigDecimal.valueOf(1_609_344),              UnitGroup.SPEED,    R.string.mile_per_hour,                 R.string.mile_per_hour_short),
            MyUnit(MyUnitIDS.mile_per_minute,               BigDecimal.valueOf(96_560_640),             UnitGroup.SPEED,    R.string.mile_per_minute,               R.string.mile_per_minute_short),
            MyUnit(MyUnitIDS.mile_per_second,               BigDecimal.valueOf(5_793_638_400),          UnitGroup.SPEED,    R.string.mile_per_second,               R.string.mile_per_second_short),
            MyUnit(MyUnitIDS.knot,                          BigDecimal.valueOf(1_852_000),              UnitGroup.SPEED,    R.string.knot,                          R.string.knot_short),
            MyUnit(MyUnitIDS.velocity_of_light_in_vacuum,   BigDecimal.valueOf(1_079_252_848_799_998),  UnitGroup.SPEED,    R.string.velocity_of_light_in_vacuum,   R.string.velocity_of_light_in_vacuum_short),
            MyUnit(MyUnitIDS.cosmic_velocity_first,         BigDecimal.valueOf(28_440_000_000),         UnitGroup.SPEED,    R.string.cosmic_velocity_first,         R.string.cosmic_velocity_first_short),
            MyUnit(MyUnitIDS.cosmic_velocity_second,        BigDecimal.valueOf(40_320_000_000),         UnitGroup.SPEED,    R.string.cosmic_velocity_second,        R.string.cosmic_velocity_second_short),
            MyUnit(MyUnitIDS.cosmic_velocity_third,         BigDecimal.valueOf(60_012_000_000),         UnitGroup.SPEED,    R.string.cosmic_velocity_third,         R.string.cosmic_velocity_third_short),
            MyUnit(MyUnitIDS.earths_orbital_speed,          BigDecimal.valueOf(107_154_000_000),        UnitGroup.SPEED,    R.string.earths_orbital_speed,          R.string.earths_orbital_speed_short),
            MyUnit(MyUnitIDS.mach,                          BigDecimal.valueOf(1_236_960_000),          UnitGroup.SPEED,    R.string.mach,                          R.string.mach_short),
            MyUnit(MyUnitIDS.mach_si_standard,              BigDecimal.valueOf(1_062_167_040),          UnitGroup.SPEED,    R.string.mach_si_standard,              R.string.mach_si_standard_short),
        )
    }
    private val temperatureCollection: List<AbstractUnit> by lazy {
        listOf(
            object : AbstractUnit(
                unitId = MyUnitIDS.celsius,
                basicUnit = BigDecimal.ONE,
                group = UnitGroup.TEMPERATURE,
                displayName = R.string.celsius,
                shortName = R.string.celsius_short,
            ) {
                override fun convert(unitTo: AbstractUnit, value: BigDecimal, scale: Int): BigDecimal {
                    return when (unitTo.unitId) {
                        MyUnitIDS.fahrenheit -> {
                            value
                                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                                .times(BigDecimal.valueOf(1.8))
                                .plus(BigDecimal(32))
                        }
                        MyUnitIDS.kelvin -> {
                            value
                                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                                .plus(BigDecimal.valueOf(273.15))
                        }
                        else -> value
                    }
                        .setMinimumRequiredScale(scale)
                        .trimZeros()
                }
            },
            object : AbstractUnit(
                unitId = MyUnitIDS.fahrenheit,
                basicUnit = BigDecimal.ONE,
                group = UnitGroup.TEMPERATURE,
                displayName = R.string.fahrenheit,
                shortName = R.string.fahrenheit_short,
            ) {
                override fun convert(unitTo: AbstractUnit, value: BigDecimal, scale: Int): BigDecimal {
                    return when (unitTo.unitId) {
                        MyUnitIDS.celsius -> {
                            value
                                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                                .minus(BigDecimal(32))
                                .times(BigDecimal(5))
                                .div(BigDecimal(9))
                        }
                        MyUnitIDS.kelvin -> {
                            value
                                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                                .minus(BigDecimal(32))
                                .times(BigDecimal(5))
                                .div(BigDecimal(9))
                                .add(BigDecimal.valueOf(273.15))
                        }
                        else -> value
                    }
                        .setMinimumRequiredScale(scale)
                        .trimZeros()
                }
            },
            object : AbstractUnit(
                unitId = MyUnitIDS.kelvin,
                basicUnit = BigDecimal.ONE,
                group = UnitGroup.TEMPERATURE,
                displayName = R.string.kelvin,
                shortName = R.string.kelvin_short,
            ) {
                override fun convert(unitTo: AbstractUnit, value: BigDecimal, scale: Int): BigDecimal {
                    return when (unitTo.unitId) {
                        MyUnitIDS.celsius -> {
                            value
                                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                                .minus(BigDecimal(273.15))
                        }
                        MyUnitIDS.fahrenheit -> {
                            value
                                .setScale(MAX_PRECISION, RoundingMode.HALF_EVEN)
                                .minus(BigDecimal.valueOf(273.15))
                                .times(BigDecimal.valueOf(1.8))
                                .plus(BigDecimal(32))
                        }
                        else -> value
                    }
                        .setMinimumRequiredScale(scale)
                        .trimZeros()
                }
            },
        )
    }
    private val areaCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.acre,                      BigDecimal.valueOf(6.083246572E+31),        UnitGroup.AREA, R.string.acre,                      R.string.acre_short ),
            MyUnit(MyUnitIDS.hectare,                   BigDecimal.valueOf(1.503202964E+32),        UnitGroup.AREA, R.string.hectare,                   R.string.hectare_short ),
            MyUnit(MyUnitIDS.square_foot,               BigDecimal.valueOf(1.396521251E+27),        UnitGroup.AREA, R.string.square_foot,               R.string.square_foot_short),
            MyUnit(MyUnitIDS.square_mile,               BigDecimal.valueOf(3.893277806E+34),        UnitGroup.AREA, R.string.square_mile,               R.string.square_mile_short),
            MyUnit(MyUnitIDS.square_yard,               BigDecimal.valueOf(1.256869126E+28),        UnitGroup.AREA, R.string.square_yard,               R.string.square_yard_short),
            MyUnit(MyUnitIDS.square_inch,               BigDecimal.valueOf(9.698064247E+24),        UnitGroup.AREA, R.string.square_inch,               R.string.square_inch_short),
            MyUnit(MyUnitIDS.square_micrometer,         BigDecimal.valueOf(1.503202964E+16),    UnitGroup.AREA, R.string.square_micrometer,         R.string.square_micrometer_short),
            MyUnit(MyUnitIDS.square_millimeter,         BigDecimal.valueOf(1.503202964E+22),        UnitGroup.AREA, R.string.square_millimeter,         R.string.square_millimeter_short),
            MyUnit(MyUnitIDS.square_centimeter,         BigDecimal.valueOf(1.503202964E+24),        UnitGroup.AREA, R.string.square_centimeter,         R.string.square_centimeter_short),
            MyUnit(MyUnitIDS.square_decimeter,          BigDecimal.valueOf(1.503202964E+26),        UnitGroup.AREA, R.string.square_decimeter,          R.string.square_decimeter_short),
            MyUnit(MyUnitIDS.square_meter,              BigDecimal.valueOf(1.503202964E+28),        UnitGroup.AREA, R.string.square_meter,              R.string.square_meter_short),
            MyUnit(MyUnitIDS.square_kilometer,          BigDecimal.valueOf(1.503202964E+34),        UnitGroup.AREA, R.string.square_kilometer,          R.string.square_kilometer_short),
            MyUnit(MyUnitIDS.electron_cross_section,    BigDecimal.valueOf(1.0),                    UnitGroup.AREA, R.string.electron_cross_section,    R.string.electron_cross_section_short),
        )
    }
    private val timeCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.attosecond,    BigDecimal.valueOf(1),                                  UnitGroup.TIME, R.string.attosecond,    R.string.attosecond_short),
            MyUnit(MyUnitIDS.nanosecond,    BigDecimal.valueOf(1_000_000_000),                      UnitGroup.TIME, R.string.nanosecond,    R.string.nanosecond_short),
            MyUnit(MyUnitIDS.microsecond,   BigDecimal.valueOf(1_000_000_000_000),                  UnitGroup.TIME, R.string.microsecond,   R.string.microsecond_short),
            MyUnit(MyUnitIDS.millisecond,   BigDecimal.valueOf(1_000_000_000_000_000),              UnitGroup.TIME, R.string.millisecond,   R.string.millisecond_short),
            MyUnit(MyUnitIDS.jiffy,         BigDecimal.valueOf(10_000_000_000_000_000),             UnitGroup.TIME, R.string.jiffy,         R.string.jiffy_short),
            MyUnit(MyUnitIDS.second,        BigDecimal.valueOf(1_000_000_000_000_000_000),          UnitGroup.TIME, R.string.second,        R.string.second_short),
            MyUnit(MyUnitIDS.minute,        BigDecimal.valueOf(60_000_000_000_000_000_000.0),       UnitGroup.TIME, R.string.minute,        R.string.minute_short),
            MyUnit(MyUnitIDS.hour,          BigDecimal.valueOf(3_600_000_000_000_000_000_000.0),    UnitGroup.TIME, R.string.hour,          R.string.hour_short),
            MyUnit(MyUnitIDS.day,           BigDecimal.valueOf(86_400_000_000_000_000_000_000.0),   UnitGroup.TIME, R.string.day,           R.string.day_short),
            MyUnit(MyUnitIDS.week,          BigDecimal.valueOf(604_800_000_000_000_000_000_000.0),  UnitGroup.TIME, R.string.week,          R.string.week_short),
        )
    }
    private val volumeCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.attoliter,             BigDecimal.valueOf(1),                          UnitGroup.VOLUME, R.string.attoliter,            R.string.attoliter_short),
            MyUnit(MyUnitIDS.milliliter,            BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.VOLUME, R.string.milliliter,           R.string.milliliter_short),
            MyUnit(MyUnitIDS.liter,                 BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.VOLUME, R.string.liter,                R.string.liter_short),
            MyUnit(MyUnitIDS.us_liquid_gallon,      BigDecimal.valueOf(3_785_411_783_999_977_000),  UnitGroup.VOLUME, R.string.us_liquid_gallon,     R.string.us_liquid_gallon_short),
            MyUnit(MyUnitIDS.us_liquid_quart,       BigDecimal.valueOf(946_352_945_999_994_200),    UnitGroup.VOLUME, R.string.us_liquid_quart,      R.string.us_liquid_quart_short),
            MyUnit(MyUnitIDS.us_liquid_pint,        BigDecimal.valueOf(473_176_472_999_997_100),    UnitGroup.VOLUME, R.string.us_liquid_pint,       R.string.us_liquid_pint_short),
            MyUnit(MyUnitIDS.us_legal_cup,          BigDecimal.valueOf(236_588_236_499_998_560),    UnitGroup.VOLUME, R.string.us_legal_cup,         R.string.us_legal_cup_short),
            MyUnit(MyUnitIDS.us_fluid_ounce,        BigDecimal.valueOf(29_573_529_562_499_996),     UnitGroup.VOLUME, R.string.us_fluid_ounce,       R.string.us_fluid_ounce_short),
            MyUnit(MyUnitIDS.us_tablespoon,         BigDecimal.valueOf(14_786_764_781_249_998),     UnitGroup.VOLUME, R.string.us_tablespoon,        R.string.us_tablespoon_short),
            MyUnit(MyUnitIDS.us_teaspoon,           BigDecimal.valueOf(4_928_921_593_749_952),      UnitGroup.VOLUME, R.string.us_teaspoon,          R.string.us_teaspoon_short),
            MyUnit(MyUnitIDS.imperial_gallon,       BigDecimal.valueOf(4_546_089_999_999_954_400),  UnitGroup.VOLUME, R.string.imperial_gallon,      R.string.imperial_gallon_short),
            MyUnit(MyUnitIDS.imperial_quart,        BigDecimal.valueOf(1_136_522_500_000_001_400),  UnitGroup.VOLUME, R.string.imperial_quart,       R.string.imperial_quart_short),
            MyUnit(MyUnitIDS.imperial_pint,         BigDecimal.valueOf(568_261_250_000_000_700),    UnitGroup.VOLUME, R.string.imperial_pint,        R.string.imperial_pint_short),
            MyUnit(MyUnitIDS.imperial_cup,          BigDecimal.valueOf(284_130_625_000_000_350),    UnitGroup.VOLUME, R.string.imperial_cup,         R.string.imperial_cup_short),
            MyUnit(MyUnitIDS.imperial_fluid_ounce,  BigDecimal.valueOf(28_413_062_500_000_036),     UnitGroup.VOLUME, R.string.imperial_fluid_ounce, R.string.imperial_fluid_ounce_short),
            MyUnit(MyUnitIDS.imperial_tablespoon,   BigDecimal.valueOf(17_758_164_062_500_148),     UnitGroup.VOLUME, R.string.imperial_tablespoon,  R.string.imperial_tablespoon_short),
            MyUnit(MyUnitIDS.imperial_teaspoon,     BigDecimal.valueOf(5_919_388_020_833_314),      UnitGroup.VOLUME, R.string.imperial_teaspoon,    R.string.imperial_teaspoon_short),
            MyUnit(MyUnitIDS.cubic_millimeter,      BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.VOLUME, R.string.cubic_millimeter,     R.string.cubic_millimeter_short),
            MyUnit(MyUnitIDS.cubic_centimeter,      BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.VOLUME, R.string.cubic_centimeter,     R.string.cubic_centimeter_short),
            MyUnit(MyUnitIDS.cubic_meter,           BigDecimal.valueOf(1.0E+21),                    UnitGroup.VOLUME, R.string.cubic_meter,          R.string.cubic_meter_short),
            MyUnit(MyUnitIDS.cubic_kilometer,       BigDecimal.valueOf(1.0E+30),                    UnitGroup.VOLUME, R.string.cubic_kilometer,      R.string.cubic_kilometer_short),
        )
    }
    private val dataCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.bit,       BigDecimal.valueOf(1),                          UnitGroup.DATA, R.string.bit,       R.string.bit_short),
            MyUnit(MyUnitIDS.kibibit,   BigDecimal.valueOf(1_024),                      UnitGroup.DATA, R.string.kibibit,   R.string.kibibit_short),
            MyUnit(MyUnitIDS.kilobit,   BigDecimal.valueOf(1_000),                      UnitGroup.DATA, R.string.kilobit,   R.string.kilobit_short),
            MyUnit(MyUnitIDS.megabit,   BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA, R.string.megabit,   R.string.megabit_short),
            MyUnit(MyUnitIDS.mebibit,   BigDecimal.valueOf(1_048_576),                  UnitGroup.DATA, R.string.mebibit,   R.string.mebibit_short),
            MyUnit(MyUnitIDS.gigabit,   BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA, R.string.gigabit,   R.string.gigabit_short),
            MyUnit(MyUnitIDS.terabit,   BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA, R.string.terabit,   R.string.terabit_short),
            MyUnit(MyUnitIDS.petabit,   BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA, R.string.petabit,   R.string.petabit_short),
            MyUnit(MyUnitIDS.exabit,    BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA, R.string.exabit,    R.string.exabit_short),
            MyUnit(MyUnitIDS.byte,      BigDecimal.valueOf(8),                          UnitGroup.DATA, R.string.byte_,     R.string.byte_short),
            MyUnit(MyUnitIDS.kibibyte,  BigDecimal.valueOf(8_192),                      UnitGroup.DATA, R.string.kibibyte,  R.string.kibibyte_short),
            MyUnit(MyUnitIDS.kilobyte,  BigDecimal.valueOf(8_000),                      UnitGroup.DATA, R.string.kilobyte,  R.string.kilobyte_short),
            MyUnit(MyUnitIDS.megabyte,  BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA, R.string.megabyte,  R.string.megabyte_short),
            MyUnit(MyUnitIDS.mebibyte,  BigDecimal.valueOf(8_388_608),                  UnitGroup.DATA, R.string.mebibyte,  R.string.mebibyte_short),
            MyUnit(MyUnitIDS.gigabyte,  BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA, R.string.gigabyte,  R.string.gigabyte_short),
            MyUnit(MyUnitIDS.terabyte,  BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA, R.string.terabyte,  R.string.terabyte_short),
            MyUnit(MyUnitIDS.petabyte,  BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA, R.string.petabyte,  R.string.petabyte_short),
            MyUnit(MyUnitIDS.exabyte,   BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA, R.string.exabyte,   R.string.exabyte_short),
        )
    }
    private val pressureCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.attopascal,            BigDecimal.valueOf(1),                      UnitGroup.PRESSURE, R.string.attopascal,            R.string.attopascal_short),
            MyUnit(MyUnitIDS.femtopascal,           BigDecimal.valueOf(1E+3),                   UnitGroup.PRESSURE, R.string.femtopascal,           R.string.femtopascal_short),
            MyUnit(MyUnitIDS.picopascal,            BigDecimal.valueOf(1E+6),                   UnitGroup.PRESSURE, R.string.picopascal,            R.string.picopascal_short),
            MyUnit(MyUnitIDS.nanopascal,            BigDecimal.valueOf(1E+9),                   UnitGroup.PRESSURE, R.string.nanopascal,            R.string.nanopascal_short),
            MyUnit(MyUnitIDS.micropascal,           BigDecimal.valueOf(1E+12),                  UnitGroup.PRESSURE, R.string.micropascal,           R.string.micropascal_short),
            MyUnit(MyUnitIDS.millipascal,           BigDecimal.valueOf(1E+15),                  UnitGroup.PRESSURE, R.string.millipascal,           R.string.millipascal_short),
            MyUnit(MyUnitIDS.centipascal,           BigDecimal.valueOf(1E+16),                  UnitGroup.PRESSURE, R.string.centipascal,           R.string.centipascal_short),
            MyUnit(MyUnitIDS.decipascal,            BigDecimal.valueOf(1E+17),                  UnitGroup.PRESSURE, R.string.decipascal,            R.string.decipascal_short),
            MyUnit(MyUnitIDS.pascal,                BigDecimal.valueOf(1E+18),                  UnitGroup.PRESSURE, R.string.pascal,                R.string.pascal_short),
            MyUnit(MyUnitIDS.dekapascal,            BigDecimal.valueOf(1E+19),                  UnitGroup.PRESSURE, R.string.dekapascal,            R.string.dekapascal_short),
            MyUnit(MyUnitIDS.hectopascal,           BigDecimal.valueOf(1E+20),                  UnitGroup.PRESSURE, R.string.hectopascal,           R.string.hectopascal_short),
            MyUnit(MyUnitIDS.bar,                   BigDecimal.valueOf(1E+23),                  UnitGroup.PRESSURE, R.string.bar,                   R.string.bar_short),
            MyUnit(MyUnitIDS.megapascal,            BigDecimal.valueOf(1E+24),                  UnitGroup.PRESSURE, R.string.megapascal,            R.string.megapascal_short),
            MyUnit(MyUnitIDS.gigapascal,            BigDecimal.valueOf(1E+27),                  UnitGroup.PRESSURE, R.string.gigapascal,            R.string.gigapascal_short),
            MyUnit(MyUnitIDS.terapascal,            BigDecimal.valueOf(1E+30),                  UnitGroup.PRESSURE, R.string.terapascal,            R.string.terapascal_short),
            MyUnit(MyUnitIDS.petapascal,            BigDecimal.valueOf(1E+33),                  UnitGroup.PRESSURE, R.string.petapascal,            R.string.petapascal_short),
            MyUnit(MyUnitIDS.exapascal,             BigDecimal.valueOf(1E+36),                  UnitGroup.PRESSURE, R.string.exapascal,             R.string.exapascal_short),
            MyUnit(MyUnitIDS.psi,                   BigDecimal.valueOf(6.8947572931783E+21),    UnitGroup.PRESSURE, R.string.psi,                   R.string.psi_short),
            MyUnit(MyUnitIDS.ksi,                   BigDecimal.valueOf(6.8947572931783E+24),    UnitGroup.PRESSURE, R.string.ksi,                   R.string.ksi_short),
            MyUnit(MyUnitIDS.standard_atmosphere,   BigDecimal.valueOf(101.325E+21),            UnitGroup.PRESSURE, R.string.standard_atmosphere,   R.string.standard_atmosphere_short),
            MyUnit(MyUnitIDS.torr,                  BigDecimal.valueOf(1.3332236842108281E+20), UnitGroup.PRESSURE, R.string.torr,                  R.string.torr_short),
            MyUnit(MyUnitIDS.millimeter_of_mercury, BigDecimal.valueOf(1.3332236842108281E+20), UnitGroup.PRESSURE, R.string.millimeter_of_mercury, R.string.millimeter_of_mercury_short),
        )
    }
    private val accelerationCollection: List<AbstractUnit> by lazy {
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
    private val energyCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.electron_volt,             BigDecimal.valueOf(0.160217733),   UnitGroup.ENERGY,   R.string.electron_volt,             R.string.electron_volt_short),
            MyUnit(MyUnitIDS.attojoule,                 BigDecimal.valueOf(1),             UnitGroup.ENERGY,   R.string.attojoule,                 R.string.attojoule_short),
            MyUnit(MyUnitIDS.joule,                     BigDecimal.valueOf(1E+18),         UnitGroup.ENERGY,   R.string.joule,                     R.string.joule_short),
            MyUnit(MyUnitIDS.kilojoule,                 BigDecimal.valueOf(1E+21),         UnitGroup.ENERGY,   R.string.kilojoule,                 R.string.kilojoule_short),
            MyUnit(MyUnitIDS.megajoule,                 BigDecimal.valueOf(1E+24),         UnitGroup.ENERGY,   R.string.megajoule,                 R.string.megajoule_short),
            MyUnit(MyUnitIDS.gigajoule,                 BigDecimal.valueOf(1E+27),         UnitGroup.ENERGY,   R.string.gigajoule,                 R.string.gigajoule_short),
            MyUnit(MyUnitIDS.energy_ton,                BigDecimal.valueOf(4.184E+27),     UnitGroup.ENERGY,   R.string.energy_ton,                R.string.energy_ton_short),
            MyUnit(MyUnitIDS.kiloton,                   BigDecimal.valueOf(4.184E+30),     UnitGroup.ENERGY,   R.string.kiloton,                   R.string.kiloton_short),
            MyUnit(MyUnitIDS.megaton,                   BigDecimal.valueOf(4.184E+33),     UnitGroup.ENERGY,   R.string.megaton,                   R.string.megaton_short),
            MyUnit(MyUnitIDS.gigaton,                   BigDecimal.valueOf(4.184E+36),     UnitGroup.ENERGY,   R.string.gigaton,                   R.string.gigaton_short),
            MyUnit(MyUnitIDS.energy_horse_power_metric, BigDecimal.valueOf(2.6477955E+24), UnitGroup.ENERGY,   R.string.energy_horse_power_metric, R.string.energy_horse_power_metric_short),
            MyUnit(MyUnitIDS.calorie_th,                BigDecimal.valueOf(4184E+15),      UnitGroup.ENERGY,   R.string.calorie_th,                R.string.calorie_th_short),
            MyUnit(MyUnitIDS.kilocalorie_th,            BigDecimal.valueOf(4184E+18),      UnitGroup.ENERGY,   R.string.kilocalorie_th,            R.string.kilocalorie_th_short),
        )
    }
    private val powerCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.attowatt,                  BigDecimal.valueOf(1),                              UnitGroup.POWER,    R.string.attowatt,                  R.string.attowatt_short),
            MyUnit(MyUnitIDS.watt,                      BigDecimal.valueOf(1_000_000_000_000_000_000),      UnitGroup.POWER,    R.string.watt,                      R.string.watt_short),
            MyUnit(MyUnitIDS.kilowatt,                  BigDecimal.valueOf(1.0E+21),                        UnitGroup.POWER,    R.string.kilowatt,                  R.string.kilowatt_short),
            MyUnit(MyUnitIDS.megawatt,                  BigDecimal.valueOf(1.0E+24),                        UnitGroup.POWER,    R.string.megawatt,                  R.string.megawatt_short),
            MyUnit(MyUnitIDS.horse_power_mechanical,    BigDecimal.valueOf(745_699_871_582_285_700_000.0),  UnitGroup.POWER,    R.string.horse_power_mechanical,    R.string.horse_power_mechanical_short),
        )
    }
    private val angleCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.angle_second,  BigDecimal.valueOf(1),              UnitGroup.ANGLE,    R.string.angle_second,  R.string.angle_second_short),
            MyUnit(MyUnitIDS.angle_minute,  BigDecimal.valueOf(60),             UnitGroup.ANGLE,    R.string.angle_minute,  R.string.angle_minute_short),
            MyUnit(MyUnitIDS.degree,        BigDecimal.valueOf(3600),           UnitGroup.ANGLE,    R.string.degree,        R.string.degree_short),
            MyUnit(MyUnitIDS.radian,        BigDecimal.valueOf(206264.8062471), UnitGroup.ANGLE,    R.string.radian,        R.string.radian_short),
            MyUnit(MyUnitIDS.sextant,       BigDecimal.valueOf(216000),         UnitGroup.ANGLE,    R.string.sextant,       R.string.sextant_short),
            MyUnit(MyUnitIDS.turn,          BigDecimal.valueOf(1296000),        UnitGroup.ANGLE,    R.string.turn,          R.string.turn_short),
        )
    }
    private val dataTransferCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.bit_per_second,        BigDecimal.valueOf(1),                          UnitGroup.DATA_TRANSFER, R.string.bit_per_second,        R.string.bit_per_second_short),
            MyUnit(MyUnitIDS.kibibit_per_second,    BigDecimal.valueOf(1_024),                      UnitGroup.DATA_TRANSFER, R.string.kibibit_per_second,    R.string.kibibit_per_second_short),
            MyUnit(MyUnitIDS.kilobit_per_second,    BigDecimal.valueOf(1_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobit_per_second,    R.string.kilobit_per_second_short),
            MyUnit(MyUnitIDS.megabit_per_second,    BigDecimal.valueOf(1_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabit_per_second,    R.string.megabit_per_second_short),
            MyUnit(MyUnitIDS.mebibit_per_second,    BigDecimal.valueOf(1_048_576),                  UnitGroup.DATA_TRANSFER, R.string.mebibit_per_second,    R.string.mebibit_per_second_short),
            MyUnit(MyUnitIDS.gigabit_per_second,    BigDecimal.valueOf(1_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabit_per_second,    R.string.gigabit_per_second_short),
            MyUnit(MyUnitIDS.terabit_per_second,    BigDecimal.valueOf(1_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabit_per_second,    R.string.terabit_per_second_short),
            MyUnit(MyUnitIDS.petabit_per_second,    BigDecimal.valueOf(1_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabit_per_second,    R.string.petabit_per_second_short),
            MyUnit(MyUnitIDS.exabit_per_second,     BigDecimal.valueOf(1_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabit_per_second,     R.string.exabit_per_second_short),
            MyUnit(MyUnitIDS.byte_per_second,       BigDecimal.valueOf(8),                          UnitGroup.DATA_TRANSFER, R.string.byte_per_second,       R.string.byte_per_second_short),
            MyUnit(MyUnitIDS.kibibyte_per_second,   BigDecimal.valueOf(8_192),                      UnitGroup.DATA_TRANSFER, R.string.kibibyte_per_second,   R.string.kibibyte_per_second_short),
            MyUnit(MyUnitIDS.kilobyte_per_second,   BigDecimal.valueOf(8_000),                      UnitGroup.DATA_TRANSFER, R.string.kilobyte_per_second,   R.string.kilobyte_per_second_short),
            MyUnit(MyUnitIDS.megabyte_per_second,   BigDecimal.valueOf(8_000_000),                  UnitGroup.DATA_TRANSFER, R.string.megabyte_per_second,   R.string.megabyte_per_second_short),
            MyUnit(MyUnitIDS.mebibyte_per_second,   BigDecimal.valueOf(8_388_608),                  UnitGroup.DATA_TRANSFER, R.string.mebibyte_per_second,   R.string.mebibyte_per_second_short),
            MyUnit(MyUnitIDS.gigabyte_per_second,   BigDecimal.valueOf(8_000_000_000),              UnitGroup.DATA_TRANSFER, R.string.gigabyte_per_second,   R.string.gigabyte_per_second_short),
            MyUnit(MyUnitIDS.terabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000),          UnitGroup.DATA_TRANSFER, R.string.terabyte_per_second,   R.string.terabyte_per_second_short),
            MyUnit(MyUnitIDS.petabyte_per_second,   BigDecimal.valueOf(8_000_000_000_000_000),      UnitGroup.DATA_TRANSFER, R.string.petabyte_per_second,   R.string.petabyte_per_second_short),
            MyUnit(MyUnitIDS.exabyte_per_second,    BigDecimal.valueOf(8_000_000_000_000_000_000),  UnitGroup.DATA_TRANSFER, R.string.exabyte_per_second,    R.string.exabyte_per_second_short),
        )
    }
    private val fluxCollection: List<AbstractUnit> by lazy {
        listOf(
            MyUnit(MyUnitIDS.maxwell,    BigDecimal.valueOf(1),                     UnitGroup.FLUX, R.string.maxwell,       R.string.maxwell_short),
            MyUnit(MyUnitIDS.microweber, BigDecimal.valueOf(100),                   UnitGroup.FLUX, R.string.microweber,    R.string.microweber_short),
            MyUnit(MyUnitIDS.milliweber, BigDecimal.valueOf(100000),                UnitGroup.FLUX, R.string.milliweber,    R.string.milliweber_short),
            MyUnit(MyUnitIDS.weber,      BigDecimal.valueOf(100000000),             UnitGroup.FLUX, R.string.weber,         R.string.weber_short),
            MyUnit(MyUnitIDS.kiloweber,  BigDecimal.valueOf(100000000000),          UnitGroup.FLUX, R.string.kiloweber,     R.string.kiloweber_short),
            MyUnit(MyUnitIDS.megaweber,  BigDecimal.valueOf(100000000000000),       UnitGroup.FLUX, R.string.megaweber,     R.string.megaweber_short),
            MyUnit(MyUnitIDS.gigaweber,  BigDecimal.valueOf(100000000000000000),    UnitGroup.FLUX, R.string.gigaweber,     R.string.gigaweber_short),
        )
    }
}
