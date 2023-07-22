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

package com.sadellie.unitto.data.timezone

import com.sadellie.unitto.data.common.lev
import com.sadellie.unitto.data.model.UnittoTimeZone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeZonesRepository @Inject constructor() {
    private val allTimeZones: HashMap<String, UnittoTimeZone> = hashMapOf(
        "alfa_time_zone"                           to UnittoTimeZone(id = "alfa_time_zone",                          nameRes = "Alfa Time Zone",                          offsetSeconds = 3600),
        "australian_central_daylight_time"         to UnittoTimeZone(id = "australian_central_daylight_time",        nameRes = "Australian Central Daylight Time",        offsetSeconds = 37800),
        "australian_central_standard_time"         to UnittoTimeZone(id = "australian_central_standard_time",        nameRes = "Australian Central Standard Time",        offsetSeconds = 34200),
        "acre_time"                                to UnittoTimeZone(id = "acre_time",                               nameRes = "Acre Time",                               offsetSeconds = -18000),
        "australian_central_western_standard_time" to UnittoTimeZone(id = "australian_central_western_standard_time", nameRes = "Australian Central Western Standard Time", offsetSeconds = 31500),
        "arabia_daylight_time"                     to UnittoTimeZone(id = "arabia_daylight_time",                    nameRes = "Arabia Daylight Time",                    offsetSeconds = 14400),
        "atlantic_daylight_time"                   to UnittoTimeZone(id = "atlantic_daylight_time",                  nameRes = "Atlantic Daylight Time",                  offsetSeconds = -10800),
        "heure_avanc_e_de_latlantique_french"      to UnittoTimeZone(id = "heure_avanc_e_de_latlantique_french",     nameRes = "Heure Avanc-e de l'Atlantique (French)",  offsetSeconds = -10800),
        "australian_eastern_daylight_time"         to UnittoTimeZone(id = "australian_eastern_daylight_time",        nameRes = "Australian Eastern Daylight Time",        offsetSeconds = 39600),
        "eastern_daylight_time"                    to UnittoTimeZone(id = "eastern_daylight_time",                   nameRes = "Eastern Daylight Time",                   offsetSeconds = 39600),
        "eastern_daylight_saving_time"             to UnittoTimeZone(id = "eastern_daylight_saving_time",            nameRes = "Eastern Daylight Saving Time",            offsetSeconds = 39600),
        "australian_eastern_standard_time"         to UnittoTimeZone(id = "australian_eastern_standard_time",        nameRes = "Australian Eastern Standard Time",        offsetSeconds = 36000),
        "eastern_standard_time"                    to UnittoTimeZone(id = "eastern_standard_time",                   nameRes = "Eastern Standard Time",                   offsetSeconds = 36000),
        "australian_eastern_time"                  to UnittoTimeZone(id = "australian_eastern_time",                 nameRes = "Australian Eastern Time",                 offsetSeconds = 36000),
        "afghanistan_time"                         to UnittoTimeZone(id = "afghanistan_time",                        nameRes = "Afghanistan Time",                        offsetSeconds = 16200),
        "alaska_daylight_time"                     to UnittoTimeZone(id = "alaska_daylight_time",                    nameRes = "Alaska Daylight Time",                    offsetSeconds = -28800),
        "alaska_standard_time"                     to UnittoTimeZone(id = "alaska_standard_time",                    nameRes = "Alaska Standard Time",                    offsetSeconds = -32400),
        "alma_ata_time"                            to UnittoTimeZone(id = "alma_ata_time",                           nameRes = "Alma-Ata Time",                           offsetSeconds = 21600),
        "amazon_summer_time"                       to UnittoTimeZone(id = "amazon_summer_time",                      nameRes = "Amazon Summer Time",                      offsetSeconds = -10800),
        "armenia_daylight_time"                    to UnittoTimeZone(id = "armenia_daylight_time",                   nameRes = "Armenia Daylight Time",                   offsetSeconds = 18000),
        "amazon_time"                              to UnittoTimeZone(id = "amazon_time",                             nameRes = "Amazon Time",                             offsetSeconds = -14400),
        "armenia_time"                             to UnittoTimeZone(id = "armenia_time",                            nameRes = "Armenia Time",                            offsetSeconds = 14400),
        "anadyr_summer_time"                       to UnittoTimeZone(id = "anadyr_summer_time",                      nameRes = "Anadyr Summer Time",                      offsetSeconds = 43200),
        "anadyr_time"                              to UnittoTimeZone(id = "anadyr_time",                             nameRes = "Anadyr Time",                             offsetSeconds = 43200),
        "aqtobe_time"                              to UnittoTimeZone(id = "aqtobe_time",                             nameRes = "Aqtobe Time",                             offsetSeconds = 18000),
        "argentina_time"                           to UnittoTimeZone(id = "argentina_time",                          nameRes = "Argentina Time",                          offsetSeconds = -10800),
        "arabic_standard_time"                     to UnittoTimeZone(id = "arabic_standard_time",                    nameRes = "Arabic Standard Time",                    offsetSeconds = 10800),
        "atlantic_standard_time"                   to UnittoTimeZone(id = "atlantic_standard_time",                  nameRes = "Atlantic Standard Time",                  offsetSeconds = -14400),
        "tiempo_est_ndar_del_atl_ntico_spanish"    to UnittoTimeZone(id = "tiempo_est_ndar_del_atl_ntico_spanish",   nameRes = "Tiempo Est-ndar del Atl-ntico (Spanish)", offsetSeconds = -14400),
        "heure_normale_de_latlantique_french"      to UnittoTimeZone(id = "heure_normale_de_latlantique_french",     nameRes = "Heure Normale de l'Atlantique (French)",  offsetSeconds = -14400),
        "australian_western_daylight_time"         to UnittoTimeZone(id = "australian_western_daylight_time",        nameRes = "Australian Western Daylight Time",        offsetSeconds = 32400),
        "western_daylight_time"                    to UnittoTimeZone(id = "western_daylight_time",                   nameRes = "Western Daylight Time",                   offsetSeconds = 32400),
        "western_summer_time"                      to UnittoTimeZone(id = "western_summer_time",                     nameRes = "Western Summer Time",                     offsetSeconds = 32400),
        "australian_western_standard_time"         to UnittoTimeZone(id = "australian_western_standard_time",        nameRes = "Australian Western Standard Time",        offsetSeconds = 28800),
        "western_standard_time"                    to UnittoTimeZone(id = "western_standard_time",                   nameRes = "Western Standard Time",                   offsetSeconds = 28800),
        "western_australia_time"                   to UnittoTimeZone(id = "western_australia_time",                  nameRes = "Western Australia Time",                  offsetSeconds = 28800),
        "azores_summer_time"                       to UnittoTimeZone(id = "azores_summer_time",                      nameRes = "Azores Summer Time",                      offsetSeconds = 0),
        "azores_daylight_time"                     to UnittoTimeZone(id = "azores_daylight_time",                    nameRes = "Azores Daylight Time",                    offsetSeconds = 0),
        "azores_time"                              to UnittoTimeZone(id = "azores_time",                             nameRes = "Azores Time",                             offsetSeconds = -3600),
        "azores_standard_time"                     to UnittoTimeZone(id = "azores_standard_time",                    nameRes = "Azores Standard Time",                    offsetSeconds = -3600),
        "azerbaijan_summer_time"                   to UnittoTimeZone(id = "azerbaijan_summer_time",                  nameRes = "Azerbaijan Summer Time",                  offsetSeconds = 18000),
        "azerbaijan_time"                          to UnittoTimeZone(id = "azerbaijan_time",                         nameRes = "Azerbaijan Time",                         offsetSeconds = 14400),
        "anywhere_on_earth"                        to UnittoTimeZone(id = "anywhere_on_earth",                       nameRes = "Anywhere on Earth",                       offsetSeconds = -43200),
        "bravo_time_zone"                          to UnittoTimeZone(id = "bravo_time_zone",                         nameRes = "Bravo Time Zone",                         offsetSeconds = 7200),
        "brunei_darussalam_time"                   to UnittoTimeZone(id = "brunei_darussalam_time",                  nameRes = "Brunei Darussalam Time",                  offsetSeconds = 28800),
        "brunei_time"                              to UnittoTimeZone(id = "brunei_time",                             nameRes = "Brunei Time",                             offsetSeconds = 28800),
        "bolivia_time"                             to UnittoTimeZone(id = "bolivia_time",                            nameRes = "Bolivia Time",                            offsetSeconds = -14400),
        "brasilia_summer_time"                     to UnittoTimeZone(id = "brasilia_summer_time",                    nameRes = "Brasilia Summer Time",                    offsetSeconds = -7200),
        "brazil_summer_time"                       to UnittoTimeZone(id = "brazil_summer_time",                      nameRes = "Brazil Summer Time",                      offsetSeconds = -7200),
        "brazilian_summer_time"                    to UnittoTimeZone(id = "brazilian_summer_time",                   nameRes = "Brazilian Summer Time",                   offsetSeconds = -7200),
        "bras_lia_time"                            to UnittoTimeZone(id = "bras_lia_time",                           nameRes = "Bras-lia Time",                           offsetSeconds = -10800),
        "brazil_time"                              to UnittoTimeZone(id = "brazil_time",                             nameRes = "Brazil Time",                             offsetSeconds = -10800),
        "brazilian_time"                           to UnittoTimeZone(id = "brazilian_time",                          nameRes = "Brazilian Time",                          offsetSeconds = -10800),
        "bangladesh_standard_time"                 to UnittoTimeZone(id = "bangladesh_standard_time",                nameRes = "Bangladesh Standard Time",                offsetSeconds = 21600),
        "bougainville_standard_time"               to UnittoTimeZone(id = "bougainville_standard_time",              nameRes = "Bougainville Standard Time",              offsetSeconds = 39600),
        "british_summer_time"                      to UnittoTimeZone(id = "british_summer_time",                     nameRes = "British Summer Time",                     offsetSeconds = 3600),
        "british_daylight_time"                    to UnittoTimeZone(id = "british_daylight_time",                   nameRes = "British Daylight Time",                   offsetSeconds = 3600),
        "british_daylight_saving_time"             to UnittoTimeZone(id = "british_daylight_saving_time",            nameRes = "British Daylight Saving Time",            offsetSeconds = 3600),
        "bhutan_time"                              to UnittoTimeZone(id = "bhutan_time",                             nameRes = "Bhutan Time",                             offsetSeconds = 21600),
        "charlie_time_zone"                        to UnittoTimeZone(id = "charlie_time_zone",                       nameRes = "Charlie Time Zone",                       offsetSeconds = 10800),
        "casey_time"                               to UnittoTimeZone(id = "casey_time",                              nameRes = "Casey Time",                              offsetSeconds = 28800),
        "central_africa_time"                      to UnittoTimeZone(id = "central_africa_time",                     nameRes = "Central Africa Time",                     offsetSeconds = 7200),
        "cocos_islands_time"                       to UnittoTimeZone(id = "cocos_islands_time",                      nameRes = "Cocos Islands Time",                      offsetSeconds = 23400),
        "central_daylight_time"                    to UnittoTimeZone(id = "central_daylight_time",                   nameRes = "Central Daylight Time",                   offsetSeconds = -18000),
        "central_daylight_saving_time"             to UnittoTimeZone(id = "central_daylight_saving_time",            nameRes = "Central Daylight Saving Time",            offsetSeconds = -18000),
        "north_american_central_daylight_time"     to UnittoTimeZone(id = "north_american_central_daylight_time",    nameRes = "North American Central Daylight Time",    offsetSeconds = -18000),
        "heure_avanc_e_du_centre_french"           to UnittoTimeZone(id = "heure_avanc_e_du_centre_french",          nameRes = "Heure Avanc-e du Centre (French)",        offsetSeconds = -18000),
        "cuba_daylight_time"                       to UnittoTimeZone(id = "cuba_daylight_time",                      nameRes = "Cuba Daylight Time",                      offsetSeconds = -14400),
        "central_european_summer_time"             to UnittoTimeZone(id = "central_european_summer_time",            nameRes = "Central European Summer Time",            offsetSeconds = 7200),
        "central_european_daylight_time"           to UnittoTimeZone(id = "central_european_daylight_time",          nameRes = "Central European Daylight Time",          offsetSeconds = 7200),
        "european_central_summer_time"             to UnittoTimeZone(id = "european_central_summer_time",            nameRes = "European Central Summer Time",            offsetSeconds = 7200),
        "mitteleurop_ische_sommerzeit_german"      to UnittoTimeZone(id = "mitteleurop_ische_sommerzeit_german",     nameRes = "Mitteleurop-ische Sommerzeit (German)",   offsetSeconds = 7200),
        "central_european_time"                    to UnittoTimeZone(id = "central_european_time",                   nameRes = "Central European Time",                   offsetSeconds = 3600),
        "european_central_time"                    to UnittoTimeZone(id = "european_central_time",                   nameRes = "European Central Time",                   offsetSeconds = 3600),
        "central_europe_time"                      to UnittoTimeZone(id = "central_europe_time",                     nameRes = "Central Europe Time",                     offsetSeconds = 3600),
        "mitteleurop_ische_zeit_german"            to UnittoTimeZone(id = "mitteleurop_ische_zeit_german",           nameRes = "Mitteleurop-ische Zeit (German)",         offsetSeconds = 3600),
        "chatham_island_daylight_time"             to UnittoTimeZone(id = "chatham_island_daylight_time",            nameRes = "Chatham Island Daylight Time",            offsetSeconds = 49500),
        "chatham_daylight_time"                    to UnittoTimeZone(id = "chatham_daylight_time",                   nameRes = "Chatham Daylight Time",                   offsetSeconds = 49500),
        "chatham_island_standard_time"             to UnittoTimeZone(id = "chatham_island_standard_time",            nameRes = "Chatham Island Standard Time",            offsetSeconds = 45900),
        "choibalsan_summer_time"                   to UnittoTimeZone(id = "choibalsan_summer_time",                  nameRes = "Choibalsan Summer Time",                  offsetSeconds = 32400),
        "choibalsan_daylight_time"                 to UnittoTimeZone(id = "choibalsan_daylight_time",                nameRes = "Choibalsan Daylight Time",                offsetSeconds = 32400),
        "choibalsan_daylight_saving_time"          to UnittoTimeZone(id = "choibalsan_daylight_saving_time",         nameRes = "Choibalsan Daylight Saving Time",         offsetSeconds = 32400),
        "choibalsan_time"                          to UnittoTimeZone(id = "choibalsan_time",                         nameRes = "Choibalsan Time",                         offsetSeconds = 28800),
        "chuuk_time"                               to UnittoTimeZone(id = "chuuk_time",                              nameRes = "Chuuk Time",                              offsetSeconds = 36000),
        "cayman_islands_daylight_saving_time"      to UnittoTimeZone(id = "cayman_islands_daylight_saving_time",     nameRes = "Cayman Islands Daylight Saving Time",     offsetSeconds = -14400),
        "cayman_islands_standard_time"             to UnittoTimeZone(id = "cayman_islands_standard_time",            nameRes = "Cayman Islands Standard Time",            offsetSeconds = -18000),
        "cayman_islands_time"                      to UnittoTimeZone(id = "cayman_islands_time",                     nameRes = "Cayman Islands Time",                     offsetSeconds = -18000),
        "cook_island_time"                         to UnittoTimeZone(id = "cook_island_time",                        nameRes = "Cook Island Time",                        offsetSeconds = -36000),
        "chile_summer_time"                        to UnittoTimeZone(id = "chile_summer_time",                       nameRes = "Chile Summer Time",                       offsetSeconds = -10800),
        "chile_daylight_time"                      to UnittoTimeZone(id = "chile_daylight_time",                     nameRes = "Chile Daylight Time",                     offsetSeconds = -10800),
        "chile_standard_time"                      to UnittoTimeZone(id = "chile_standard_time",                     nameRes = "Chile Standard Time",                     offsetSeconds = -14400),
        "chile_time"                               to UnittoTimeZone(id = "chile_time",                              nameRes = "Chile Time",                              offsetSeconds = -14400),
        "chile_standard_time"                      to UnittoTimeZone(id = "chile_standard_time",                     nameRes = "Chile Standard Time",                     offsetSeconds = -14400),
        "colombia_time"                            to UnittoTimeZone(id = "colombia_time",                           nameRes = "Colombia Time",                           offsetSeconds = -18000),
        "central_standard_time"                    to UnittoTimeZone(id = "central_standard_time",                   nameRes = "Central Standard Time",                   offsetSeconds = -21600),
        "central_time"                             to UnittoTimeZone(id = "central_time",                            nameRes = "Central Time",                            offsetSeconds = -21600),
        "north_american_central_standard_time"     to UnittoTimeZone(id = "north_american_central_standard_time",    nameRes = "North American Central Standard Time",    offsetSeconds = -21600),
        "tiempo_central_est_ndar_spanish"          to UnittoTimeZone(id = "tiempo_central_est_ndar_spanish",         nameRes = "Tiempo Central Est-ndar (Spanish)",       offsetSeconds = -21600),
        "heure_normale_du_centre_french"           to UnittoTimeZone(id = "heure_normale_du_centre_french",          nameRes = "Heure Normale du Centre (French)",        offsetSeconds = -21600),
        "china_standard_time"                      to UnittoTimeZone(id = "china_standard_time",                     nameRes = "China Standard Time",                     offsetSeconds = 28800),
        "cuba_standard_time"                       to UnittoTimeZone(id = "cuba_standard_time",                      nameRes = "Cuba Standard Time",                      offsetSeconds = -18000),
        "cape_verde_time"                          to UnittoTimeZone(id = "cape_verde_time",                         nameRes = "Cape Verde Time",                         offsetSeconds = -3600),
        "christmas_island_time"                    to UnittoTimeZone(id = "christmas_island_time",                   nameRes = "Christmas Island Time",                   offsetSeconds = 25200),
        "chamorro_standard_time"                   to UnittoTimeZone(id = "chamorro_standard_time",                  nameRes = "Chamorro Standard Time",                  offsetSeconds = 36000),
        "guam_standard_time"                       to UnittoTimeZone(id = "guam_standard_time",                      nameRes = "Guam Standard Time",                      offsetSeconds = 36000),
        "delta_time_zone"                          to UnittoTimeZone(id = "delta_time_zone",                         nameRes = "Delta Time Zone",                         offsetSeconds = 14400),
        "davis_time"                               to UnittoTimeZone(id = "davis_time",                              nameRes = "Davis Time",                              offsetSeconds = 25200),
        "dumont_durville_time"                     to UnittoTimeZone(id = "dumont_durville_time",                    nameRes = "Dumont-d'Urville Time",                   offsetSeconds = 36000),
        "echo_time_zone"                           to UnittoTimeZone(id = "echo_time_zone",                          nameRes = "Echo Time Zone",                          offsetSeconds = 18000),
        "easter_island_summer_time"                to UnittoTimeZone(id = "easter_island_summer_time",               nameRes = "Easter Island Summer Time",               offsetSeconds = -18000),
        "easter_island_daylight_time"              to UnittoTimeZone(id = "easter_island_daylight_time",             nameRes = "Easter Island Daylight Time",             offsetSeconds = -18000),
        "easter_island_standard_time"              to UnittoTimeZone(id = "easter_island_standard_time",             nameRes = "Easter Island Standard Time",             offsetSeconds = -21600),
        "eastern_africa_time"                      to UnittoTimeZone(id = "eastern_africa_time",                     nameRes = "Eastern Africa Time",                     offsetSeconds = 10800),
        "east_africa_time"                         to UnittoTimeZone(id = "east_africa_time",                        nameRes = "East Africa Time",                        offsetSeconds = 10800),
        "ecuador_time"                             to UnittoTimeZone(id = "ecuador_time",                            nameRes = "Ecuador Time",                            offsetSeconds = -18000),
        "eastern_daylight_time"                    to UnittoTimeZone(id = "eastern_daylight_time",                   nameRes = "Eastern Daylight Time",                   offsetSeconds = -14400),
        "eastern_daylight_savings_time"            to UnittoTimeZone(id = "eastern_daylight_savings_time",           nameRes = "Eastern Daylight Savings Time",           offsetSeconds = -14400),
        "north_american_eastern_daylight_time"     to UnittoTimeZone(id = "north_american_eastern_daylight_time",    nameRes = "North American Eastern Daylight Time",    offsetSeconds = -14400),
        "heure_avanc_e_de_lest_french"             to UnittoTimeZone(id = "heure_avanc_e_de_lest_french",            nameRes = "Heure Avanc-e de l'Est (French)",         offsetSeconds = -14400),
        "tiempo_de_verano_del_este_spanish"        to UnittoTimeZone(id = "tiempo_de_verano_del_este_spanish",       nameRes = "Tiempo de verano del Este (Spanish)",     offsetSeconds = -14400),
        "eastern_european_summer_time"             to UnittoTimeZone(id = "eastern_european_summer_time",            nameRes = "Eastern European Summer Time",            offsetSeconds = 10800),
        "eastern_european_daylight_time"           to UnittoTimeZone(id = "eastern_european_daylight_time",          nameRes = "Eastern European Daylight Time",          offsetSeconds = 10800),
        "osteurop_ische_sommerzeit_german"         to UnittoTimeZone(id = "osteurop_ische_sommerzeit_german",        nameRes = "Osteurop-ische Sommerzeit (German)",      offsetSeconds = 10800),
        "eastern_european_time"                    to UnittoTimeZone(id = "eastern_european_time",                   nameRes = "Eastern European Time",                   offsetSeconds = 7200),
        "osteurop_ische_zeit_german"               to UnittoTimeZone(id = "osteurop_ische_zeit_german",              nameRes = "Osteurop-ische Zeit (German)",            offsetSeconds = 7200),
        "eastern_greenland_summer_time"            to UnittoTimeZone(id = "eastern_greenland_summer_time",           nameRes = "Eastern Greenland Summer Time",           offsetSeconds = 0),
        "east_greenland_summer_time"               to UnittoTimeZone(id = "east_greenland_summer_time",              nameRes = "East Greenland Summer Time",              offsetSeconds = 0),
        "east_greenland_time"                      to UnittoTimeZone(id = "east_greenland_time",                     nameRes = "East Greenland Time",                     offsetSeconds = -3600),
        "eastern_greenland_time"                   to UnittoTimeZone(id = "eastern_greenland_time",                  nameRes = "Eastern Greenland Time",                  offsetSeconds = -3600),
        "eastern_standard_time"                    to UnittoTimeZone(id = "eastern_standard_time",                   nameRes = "Eastern Standard Time",                   offsetSeconds = -18000),
        "eastern_time_"                            to UnittoTimeZone(id = "eastern_time_",                           nameRes = "Eastern Time ",                           offsetSeconds = -18000),
        "north_american_eastern_standard_time"     to UnittoTimeZone(id = "north_american_eastern_standard_time",    nameRes = "North American Eastern Standard Time",    offsetSeconds = -18000),
        "tiempo_del_este_spanish"                  to UnittoTimeZone(id = "tiempo_del_este_spanish",                 nameRes = "Tiempo del Este (Spanish)",               offsetSeconds = -18000),
        "heure_normale_de_lest_french"             to UnittoTimeZone(id = "heure_normale_de_lest_french",            nameRes = "Heure Normale de l'Est (French)",         offsetSeconds = -18000),
        "foxtrot_time_zone"                        to UnittoTimeZone(id = "foxtrot_time_zone",                       nameRes = "Foxtrot Time Zone",                       offsetSeconds = 21600),
        "further_eastern_european_time"            to UnittoTimeZone(id = "further_eastern_european_time",           nameRes = "Further-Eastern European Time",           offsetSeconds = 10800),
        "fiji_summer_time"                         to UnittoTimeZone(id = "fiji_summer_time",                        nameRes = "Fiji Summer Time",                        offsetSeconds = 46800),
        "fiji_daylight_time"                       to UnittoTimeZone(id = "fiji_daylight_time",                      nameRes = "Fiji Daylight Time",                      offsetSeconds = 46800),
        "fiji_time"                                to UnittoTimeZone(id = "fiji_time",                               nameRes = "Fiji Time",                               offsetSeconds = 43200),
        "falkland_islands_summer_time"             to UnittoTimeZone(id = "falkland_islands_summer_time",            nameRes = "Falkland Islands Summer Time",            offsetSeconds = -10800),
        "falkland_island_daylight_time"            to UnittoTimeZone(id = "falkland_island_daylight_time",           nameRes = "Falkland Island Daylight Time",           offsetSeconds = -10800),
        "falkland_island_time"                     to UnittoTimeZone(id = "falkland_island_time",                    nameRes = "Falkland Island Time",                    offsetSeconds = -14400),
        "falkland_island_standard_time"            to UnittoTimeZone(id = "falkland_island_standard_time",           nameRes = "Falkland Island Standard Time",           offsetSeconds = -14400),
        "fernando_de_noronha_time"                 to UnittoTimeZone(id = "fernando_de_noronha_time",                nameRes = "Fernando de Noronha Time",                offsetSeconds = -7200),
        "golf_time_zone"                           to UnittoTimeZone(id = "golf_time_zone",                          nameRes = "Golf Time Zone",                          offsetSeconds = 25200),
        "galapagos_time"                           to UnittoTimeZone(id = "galapagos_time",                          nameRes = "Galapagos Time",                          offsetSeconds = -21600),
        "gambier_time"                             to UnittoTimeZone(id = "gambier_time",                            nameRes = "Gambier Time",                            offsetSeconds = -32400),
        "gambier_islands_time"                     to UnittoTimeZone(id = "gambier_islands_time",                    nameRes = "Gambier Islands Time",                    offsetSeconds = -32400),
        "georgia_standard_time"                    to UnittoTimeZone(id = "georgia_standard_time",                   nameRes = "Georgia Standard Time",                   offsetSeconds = 14400),
        "french_guiana_time"                       to UnittoTimeZone(id = "french_guiana_time",                      nameRes = "French Guiana Time",                      offsetSeconds = -10800),
        "gilbert_island_time"                      to UnittoTimeZone(id = "gilbert_island_time",                     nameRes = "Gilbert Island Time",                     offsetSeconds = 43200),
        "greenwich_mean_time"                      to UnittoTimeZone(id = "greenwich_mean_time",                     nameRes = "Greenwich Mean Time",                     offsetSeconds = 0),
        "coordinated_universal_time"               to UnittoTimeZone(id = "coordinated_universal_time",              nameRes = "Coordinated Universal Time",              offsetSeconds = 0),
        "greenwich_time"                           to UnittoTimeZone(id = "greenwich_time",                          nameRes = "Greenwich Time",                          offsetSeconds = 0),
        "gulf_standard_time"                       to UnittoTimeZone(id = "gulf_standard_time",                      nameRes = "Gulf Standard Time",                      offsetSeconds = 14400),
        "south_georgia_time"                       to UnittoTimeZone(id = "south_georgia_time",                      nameRes = "South Georgia Time",                      offsetSeconds = -7200),
        "guyana_time"                              to UnittoTimeZone(id = "guyana_time",                             nameRes = "Guyana Time",                             offsetSeconds = -14400),
        "hotel_time_zone"                          to UnittoTimeZone(id = "hotel_time_zone",                         nameRes = "Hotel Time Zone",                         offsetSeconds = 28800),
        "hawaii_aleutian_daylight_time"            to UnittoTimeZone(id = "hawaii_aleutian_daylight_time",           nameRes = "Hawaii-Aleutian Daylight Time",           offsetSeconds = -32400),
        "hawaii_daylight_time"                     to UnittoTimeZone(id = "hawaii_daylight_time",                    nameRes = "Hawaii Daylight Time",                    offsetSeconds = -32400),
        "hong_kong_time"                           to UnittoTimeZone(id = "hong_kong_time",                          nameRes = "Hong Kong Time",                          offsetSeconds = 28800),
        "hovd_summer_time"                         to UnittoTimeZone(id = "hovd_summer_time",                        nameRes = "Hovd Summer Time",                        offsetSeconds = 28800),
        "hovd_daylight_time"                       to UnittoTimeZone(id = "hovd_daylight_time",                      nameRes = "Hovd Daylight Time",                      offsetSeconds = 28800),
        "hovd_daylight_saving_time"                to UnittoTimeZone(id = "hovd_daylight_saving_time",               nameRes = "Hovd Daylight Saving Time",               offsetSeconds = 28800),
        "hovd_time"                                to UnittoTimeZone(id = "hovd_time",                               nameRes = "Hovd Time",                               offsetSeconds = 25200),
        "hawaii_standard_time"                     to UnittoTimeZone(id = "hawaii_standard_time",                    nameRes = "Hawaii Standard Time",                    offsetSeconds = -36000),
        "hawaii_aleutian_standard_time"            to UnittoTimeZone(id = "hawaii_aleutian_standard_time",           nameRes = "Hawaii-Aleutian Standard Time",           offsetSeconds = -36000),
        "india_time_zone"                          to UnittoTimeZone(id = "india_time_zone",                         nameRes = "India Time Zone",                         offsetSeconds = 32400),
        "indochina_time"                           to UnittoTimeZone(id = "indochina_time",                          nameRes = "Indochina Time",                          offsetSeconds = 25200),
        "israel_daylight_time"                     to UnittoTimeZone(id = "israel_daylight_time",                    nameRes = "Israel Daylight Time",                    offsetSeconds = 10800),
        "indian_chagos_time"                       to UnittoTimeZone(id = "indian_chagos_time",                      nameRes = "Indian Chagos Time",                      offsetSeconds = 21600),
        "iran_daylight_time"                       to UnittoTimeZone(id = "iran_daylight_time",                      nameRes = "Iran Daylight Time",                      offsetSeconds = 16200),
        "iran_summer_time"                         to UnittoTimeZone(id = "iran_summer_time",                        nameRes = "Iran Summer Time",                        offsetSeconds = 16200),
        "iran_daylight_time"                       to UnittoTimeZone(id = "iran_daylight_time",                      nameRes = "Iran Daylight Time",                      offsetSeconds = 16200),
        "irkutsk_summer_time"                      to UnittoTimeZone(id = "irkutsk_summer_time",                     nameRes = "Irkutsk Summer Time",                     offsetSeconds = 32400),
        "irkutsk_time"                             to UnittoTimeZone(id = "irkutsk_time",                            nameRes = "Irkutsk Time",                            offsetSeconds = 28800),
        "iran_standard_time"                       to UnittoTimeZone(id = "iran_standard_time",                      nameRes = "Iran Standard Time",                      offsetSeconds = 12600),
        "iran_time"                                to UnittoTimeZone(id = "iran_time",                               nameRes = "Iran Time",                               offsetSeconds = 12600),
        "india_standard_time"                      to UnittoTimeZone(id = "india_standard_time",                     nameRes = "India Standard Time",                     offsetSeconds = 19800),
        "india_time"                               to UnittoTimeZone(id = "india_time",                              nameRes = "India Time",                              offsetSeconds = 19800),
        "indian_standard_time"                     to UnittoTimeZone(id = "indian_standard_time",                    nameRes = "Indian Standard Time",                    offsetSeconds = 19800),
        "irish_standard_time"                      to UnittoTimeZone(id = "irish_standard_time",                     nameRes = "Irish Standard Time",                     offsetSeconds = 3600),
        "irish_summer_time"                        to UnittoTimeZone(id = "irish_summer_time",                       nameRes = "Irish Summer Time",                       offsetSeconds = 3600),
        "israel_standard_time"                     to UnittoTimeZone(id = "israel_standard_time",                    nameRes = "Israel Standard Time",                    offsetSeconds = 7200),
        "japan_standard_time"                      to UnittoTimeZone(id = "japan_standard_time",                     nameRes = "Japan Standard Time",                     offsetSeconds = 32400),
        "kilo_time_zone"                           to UnittoTimeZone(id = "kilo_time_zone",                          nameRes = "Kilo Time Zone",                          offsetSeconds = 36000),
        "kyrgyzstan_time"                          to UnittoTimeZone(id = "kyrgyzstan_time",                         nameRes = "Kyrgyzstan Time",                         offsetSeconds = 21600),
        "kosrae_time"                              to UnittoTimeZone(id = "kosrae_time",                             nameRes = "Kosrae Time",                             offsetSeconds = 39600),
        "krasnoyarsk_summer_time"                  to UnittoTimeZone(id = "krasnoyarsk_summer_time",                 nameRes = "Krasnoyarsk Summer Time",                 offsetSeconds = 28800),
        "krasnoyarsk_time"                         to UnittoTimeZone(id = "krasnoyarsk_time",                        nameRes = "Krasnoyarsk Time",                        offsetSeconds = 25200),
        "korea_standard_time"                      to UnittoTimeZone(id = "korea_standard_time",                     nameRes = "Korea Standard Time",                     offsetSeconds = 32400),
        "korean_standard_time"                     to UnittoTimeZone(id = "korean_standard_time",                    nameRes = "Korean Standard Time",                    offsetSeconds = 32400),
        "korea_time"                               to UnittoTimeZone(id = "korea_time",                              nameRes = "Korea Time",                              offsetSeconds = 32400),
        "kuybyshev_time"                           to UnittoTimeZone(id = "kuybyshev_time",                          nameRes = "Kuybyshev Time",                          offsetSeconds = 14400),
        "samara_summer_time"                       to UnittoTimeZone(id = "samara_summer_time",                      nameRes = "Samara Summer Time",                      offsetSeconds = 14400),
        "lima_time_zone"                           to UnittoTimeZone(id = "lima_time_zone",                          nameRes = "Lima Time Zone",                          offsetSeconds = 39600),
        "lord_howe_daylight_time"                  to UnittoTimeZone(id = "lord_howe_daylight_time",                 nameRes = "Lord Howe Daylight Time",                 offsetSeconds = 39600),
        "lord_howe_standard_time"                  to UnittoTimeZone(id = "lord_howe_standard_time",                 nameRes = "Lord Howe Standard Time",                 offsetSeconds = 37800),
        "line_islands_time"                        to UnittoTimeZone(id = "line_islands_time",                       nameRes = "Line Islands Time",                       offsetSeconds = 50400),
        "mike_time_zone"                           to UnittoTimeZone(id = "mike_time_zone",                          nameRes = "Mike Time Zone",                          offsetSeconds = 43200),
        "magadan_summer_time"                      to UnittoTimeZone(id = "magadan_summer_time",                     nameRes = "Magadan Summer Time",                     offsetSeconds = 43200),
        "magadan_island_summer_time"               to UnittoTimeZone(id = "magadan_island_summer_time",              nameRes = "Magadan Island Summer Time",              offsetSeconds = 43200),
        "magadan_time"                             to UnittoTimeZone(id = "magadan_time",                            nameRes = "Magadan Time",                            offsetSeconds = 39600),
        "magadan_island_time"                      to UnittoTimeZone(id = "magadan_island_time",                     nameRes = "Magadan Island Time",                     offsetSeconds = 39600),
        "marquesas_time"                           to UnittoTimeZone(id = "marquesas_time",                          nameRes = "Marquesas Time",                          offsetSeconds = -34200),
        "mawson_time"                              to UnittoTimeZone(id = "mawson_time",                             nameRes = "Mawson Time",                             offsetSeconds = 18000),
        "mountain_daylight_time"                   to UnittoTimeZone(id = "mountain_daylight_time",                  nameRes = "Mountain Daylight Time",                  offsetSeconds = -21600),
        "mountain_daylight_saving_time"            to UnittoTimeZone(id = "mountain_daylight_saving_time",           nameRes = "Mountain Daylight Saving Time",           offsetSeconds = -21600),
        "north_american_mountain_daylight_time"    to UnittoTimeZone(id = "north_american_mountain_daylight_time",   nameRes = "North American Mountain Daylight Time",   offsetSeconds = -21600),
        "heure_avanc_e_des_rocheuses_french"       to UnittoTimeZone(id = "heure_avanc_e_des_rocheuses_french",      nameRes = "Heure Avanc-e des Rocheuses (French)",    offsetSeconds = -21600),
        "marshall_islands_time"                    to UnittoTimeZone(id = "marshall_islands_time",                   nameRes = "Marshall Islands Time",                   offsetSeconds = 43200),
        "myanmar_time"                             to UnittoTimeZone(id = "myanmar_time",                            nameRes = "Myanmar Time",                            offsetSeconds = 23400),
        "moscow_daylight_time"                     to UnittoTimeZone(id = "moscow_daylight_time",                    nameRes = "Moscow Daylight Time",                    offsetSeconds = 14400),
        "moscow_summer_time"                       to UnittoTimeZone(id = "moscow_summer_time",                      nameRes = "Moscow Summer Time",                      offsetSeconds = 14400),
        "moscow_standard_time"                     to UnittoTimeZone(id = "moscow_standard_time",                    nameRes = "Moscow Standard Time",                    offsetSeconds = 10800),
        "moscow_time"                              to UnittoTimeZone(id = "moscow_time",                             nameRes = "Moscow Time",                             offsetSeconds = 10800),
        "mountain_standard_time"                   to UnittoTimeZone(id = "mountain_standard_time",                  nameRes = "Mountain Standard Time",                  offsetSeconds = -25200),
        "mountain_time"                            to UnittoTimeZone(id = "mountain_time",                           nameRes = "Mountain Time",                           offsetSeconds = -25200),
        "north_american_mountain_standard_time"    to UnittoTimeZone(id = "north_american_mountain_standard_time",   nameRes = "North American Mountain Standard Time",   offsetSeconds = -25200),
        "heure_normale_des_rocheuses_french"       to UnittoTimeZone(id = "heure_normale_des_rocheuses_french",      nameRes = "Heure Normale des Rocheuses (French)",    offsetSeconds = -25200),
        "mauritius_time"                           to UnittoTimeZone(id = "mauritius_time",                          nameRes = "Mauritius Time",                          offsetSeconds = 14400),
        "maldives_time"                            to UnittoTimeZone(id = "maldives_time",                           nameRes = "Maldives Time",                           offsetSeconds = 18000),
        "malaysia_time"                            to UnittoTimeZone(id = "malaysia_time",                           nameRes = "Malaysia Time",                           offsetSeconds = 28800),
        "malaysian_standard_time"                  to UnittoTimeZone(id = "malaysian_standard_time",                 nameRes = "Malaysian Standard Time",                 offsetSeconds = 28800),
        "november_time_zone"                       to UnittoTimeZone(id = "november_time_zone",                      nameRes = "November Time Zone",                      offsetSeconds = -3600),
        "new_caledonia_time"                       to UnittoTimeZone(id = "new_caledonia_time",                      nameRes = "New Caledonia Time",                      offsetSeconds = 39600),
        "newfoundland_daylight_time"               to UnittoTimeZone(id = "newfoundland_daylight_time",              nameRes = "Newfoundland Daylight Time",              offsetSeconds = -9000),
        "heure_avanc_e_de_terre_neuve_french"      to UnittoTimeZone(id = "heure_avanc_e_de_terre_neuve_french",     nameRes = "Heure Avanc-e de Terre-Neuve (French)",   offsetSeconds = -9000),
        "norfolk_daylight_time"                    to UnittoTimeZone(id = "norfolk_daylight_time",                   nameRes = "Norfolk Daylight Time",                   offsetSeconds = 43200),
        "norfolk_island_daylight_time"             to UnittoTimeZone(id = "norfolk_island_daylight_time",            nameRes = "Norfolk Island Daylight Time",            offsetSeconds = 43200),
        "norfolk_time"                             to UnittoTimeZone(id = "norfolk_time",                            nameRes = "Norfolk Time",                            offsetSeconds = 39600),
        "norfolk_island_time"                      to UnittoTimeZone(id = "norfolk_island_time",                     nameRes = "Norfolk Island Time",                     offsetSeconds = 39600),
        "novosibirsk_summer_time"                  to UnittoTimeZone(id = "novosibirsk_summer_time",                 nameRes = "Novosibirsk Summer Time",                 offsetSeconds = 25200),
        "omsk_summer_time"                         to UnittoTimeZone(id = "omsk_summer_time",                        nameRes = "Omsk Summer Time",                        offsetSeconds = 25200),
        "novosibirsk_time"                         to UnittoTimeZone(id = "novosibirsk_time",                        nameRes = "Novosibirsk Time",                        offsetSeconds = 25200),
        "omsk_standard_time"                       to UnittoTimeZone(id = "omsk_standard_time",                      nameRes = "Omsk Standard Time",                      offsetSeconds = 25200),
        "nepal_time"                               to UnittoTimeZone(id = "nepal_time",                              nameRes = "Nepal Time",                              offsetSeconds = 20700),
        "nauru_time"                               to UnittoTimeZone(id = "nauru_time",                              nameRes = "Nauru Time",                              offsetSeconds = 43200),
        "newfoundland_standard_time"               to UnittoTimeZone(id = "newfoundland_standard_time",              nameRes = "Newfoundland Standard Time",              offsetSeconds = -12600),
        "heure_normale_de_terre_neuve_french"      to UnittoTimeZone(id = "heure_normale_de_terre_neuve_french",     nameRes = "Heure Normale de Terre-Neuve (French)",   offsetSeconds = -12600),
        "niue_time"                                to UnittoTimeZone(id = "niue_time",                               nameRes = "Niue Time",                               offsetSeconds = -39600),
        "new_zealand_daylight_time"                to UnittoTimeZone(id = "new_zealand_daylight_time",               nameRes = "New Zealand Daylight Time",               offsetSeconds = 46800),
        "new_zealand_standard_time"                to UnittoTimeZone(id = "new_zealand_standard_time",               nameRes = "New Zealand Standard Time",               offsetSeconds = 43200),
        "oscar_time_zone"                          to UnittoTimeZone(id = "oscar_time_zone",                         nameRes = "Oscar Time Zone",                         offsetSeconds = -7200),
        "omsk_summer_time"                         to UnittoTimeZone(id = "omsk_summer_time",                        nameRes = "Omsk Summer Time",                        offsetSeconds = 25200),
        "novosibirsk_summer_time"                  to UnittoTimeZone(id = "novosibirsk_summer_time",                 nameRes = "Novosibirsk Summer Time",                 offsetSeconds = 25200),
        "omsk_standard_time"                       to UnittoTimeZone(id = "omsk_standard_time",                      nameRes = "Omsk Standard Time",                      offsetSeconds = 21600),
        "omsk_time"                                to UnittoTimeZone(id = "omsk_time",                               nameRes = "Omsk Time",                               offsetSeconds = 21600),
        "novosibirsk_time"                         to UnittoTimeZone(id = "novosibirsk_time",                        nameRes = "Novosibirsk Time",                        offsetSeconds = 21600),
        "oral_time"                                to UnittoTimeZone(id = "oral_time",                               nameRes = "Oral Time",                               offsetSeconds = 18000),
        "papa_time_zone"                           to UnittoTimeZone(id = "papa_time_zone",                          nameRes = "Papa Time Zone",                          offsetSeconds = -10800),
        "pacific_daylight_time"                    to UnittoTimeZone(id = "pacific_daylight_time",                   nameRes = "Pacific Daylight Time",                   offsetSeconds = -25200),
        "pacific_daylight_saving_time"             to UnittoTimeZone(id = "pacific_daylight_saving_time",            nameRes = "Pacific Daylight Saving Time",            offsetSeconds = -25200),
        "north_american_pacific_daylight_time"     to UnittoTimeZone(id = "north_american_pacific_daylight_time",    nameRes = "North American Pacific Daylight Time",    offsetSeconds = -25200),
        "heure_avanc_e_du_pacifique_french"        to UnittoTimeZone(id = "heure_avanc_e_du_pacifique_french",       nameRes = "Heure Avanc-e du Pacifique (French)",     offsetSeconds = -25200),
        "peru_time"                                to UnittoTimeZone(id = "peru_time",                               nameRes = "Peru Time",                               offsetSeconds = -18000),
        "kamchatka_summer_time"                    to UnittoTimeZone(id = "kamchatka_summer_time",                   nameRes = "Kamchatka Summer Time",                   offsetSeconds = 43200),
        "kamchatka_time"                           to UnittoTimeZone(id = "kamchatka_time",                          nameRes = "Kamchatka Time",                          offsetSeconds = 43200),
        "petropavlovsk_kamchatski_time"            to UnittoTimeZone(id = "petropavlovsk_kamchatski_time",           nameRes = "Petropavlovsk-Kamchatski Time",           offsetSeconds = 43200),
        "papua_new_guinea_time"                    to UnittoTimeZone(id = "papua_new_guinea_time",                   nameRes = "Papua New Guinea Time",                   offsetSeconds = 36000),
        "phoenix_island_time"                      to UnittoTimeZone(id = "phoenix_island_time",                     nameRes = "Phoenix Island Time",                     offsetSeconds = 46800),
        "philippine_time"                          to UnittoTimeZone(id = "philippine_time",                         nameRes = "Philippine Time",                         offsetSeconds = 28800),
        "philippine_standard_time"                 to UnittoTimeZone(id = "philippine_standard_time",                nameRes = "Philippine Standard Time",                offsetSeconds = 28800),
        "pakistan_standard_time"                   to UnittoTimeZone(id = "pakistan_standard_time",                  nameRes = "Pakistan Standard Time",                  offsetSeconds = 18000),
        "pakistan_time"                            to UnittoTimeZone(id = "pakistan_time",                           nameRes = "Pakistan Time",                           offsetSeconds = 18000),
        "pierre_&_miquelon_daylight_time"          to UnittoTimeZone(id = "pierre_&_miquelon_daylight_time",         nameRes = "Pierre & Miquelon Daylight Time",         offsetSeconds = -7200),
        "pierre_&_miquelon_standard_time"          to UnittoTimeZone(id = "pierre_&_miquelon_standard_time",         nameRes = "Pierre & Miquelon Standard Time",         offsetSeconds = -10800),
        "pohnpei_standard_time"                    to UnittoTimeZone(id = "pohnpei_standard_time",                   nameRes = "Pohnpei Standard Time",                   offsetSeconds = 39600),
        "pacific_standard_time"                    to UnittoTimeZone(id = "pacific_standard_time",                   nameRes = "Pacific Standard Time",                   offsetSeconds = -28800),
        "pacific_time"                             to UnittoTimeZone(id = "pacific_time",                            nameRes = "Pacific Time",                            offsetSeconds = -28800),
        "north_american_pacific_standard_time"     to UnittoTimeZone(id = "north_american_pacific_standard_time",    nameRes = "North American Pacific Standard Time",    offsetSeconds = -28800),
        "tiempo_del_pac_fico_spanish"              to UnittoTimeZone(id = "tiempo_del_pac_fico_spanish",             nameRes = "Tiempo del Pac-fico (Spanish)",           offsetSeconds = -28800),
        "heure_normale_du_pacifique_french"        to UnittoTimeZone(id = "heure_normale_du_pacifique_french",       nameRes = "Heure Normale du Pacifique (French)",     offsetSeconds = -28800),
        "pitcairn_standard_time"                   to UnittoTimeZone(id = "pitcairn_standard_time",                  nameRes = "Pitcairn Standard Time",                  offsetSeconds = -28800),
        "palau_time"                               to UnittoTimeZone(id = "palau_time",                              nameRes = "Palau Time",                              offsetSeconds = 32400),
        "paraguay_summer_time"                     to UnittoTimeZone(id = "paraguay_summer_time",                    nameRes = "Paraguay Summer Time",                    offsetSeconds = -10800),
        "paraguay_time"                            to UnittoTimeZone(id = "paraguay_time",                           nameRes = "Paraguay Time",                           offsetSeconds = -14400),
        "pyongyang_time"                           to UnittoTimeZone(id = "pyongyang_time",                          nameRes = "Pyongyang Time",                          offsetSeconds = 30600),
        "pyongyang_standard_time"                  to UnittoTimeZone(id = "pyongyang_standard_time",                 nameRes = "Pyongyang Standard Time",                 offsetSeconds = 30600),
        "quebec_time_zone"                         to UnittoTimeZone(id = "quebec_time_zone",                        nameRes = "Quebec Time Zone",                        offsetSeconds = -14400),
        "qyzylorda_time"                           to UnittoTimeZone(id = "qyzylorda_time",                          nameRes = "Qyzylorda Time",                          offsetSeconds = 21600),
        "romeo_time_zone"                          to UnittoTimeZone(id = "romeo_time_zone",                         nameRes = "Romeo Time Zone",                         offsetSeconds = -18000),
        "reunion_time"                             to UnittoTimeZone(id = "reunion_time",                            nameRes = "Reunion Time",                            offsetSeconds = 14400),
        "rothera_time"                             to UnittoTimeZone(id = "rothera_time",                            nameRes = "Rothera Time",                            offsetSeconds = -10800),
        "sierra_time_zone"                         to UnittoTimeZone(id = "sierra_time_zone",                        nameRes = "Sierra Time Zone",                        offsetSeconds = -21600),
        "sakhalin_time"                            to UnittoTimeZone(id = "sakhalin_time",                           nameRes = "Sakhalin Time",                           offsetSeconds = 39600),
        "samara_time"                              to UnittoTimeZone(id = "samara_time",                             nameRes = "Samara Time",                             offsetSeconds = 14400),
        "samara_standard_time"                     to UnittoTimeZone(id = "samara_standard_time",                    nameRes = "Samara Standard Time",                    offsetSeconds = 14400),
        "south_africa_standard_time"               to UnittoTimeZone(id = "south_africa_standard_time",              nameRes = "South Africa Standard Time",              offsetSeconds = 7200),
        "south_african_standard_time"              to UnittoTimeZone(id = "south_african_standard_time",             nameRes = "South African Standard Time",             offsetSeconds = 7200),
        "solomon_islands_time"                     to UnittoTimeZone(id = "solomon_islands_time",                    nameRes = "Solomon Islands Time",                    offsetSeconds = 39600),
        "solomon_island_time"                      to UnittoTimeZone(id = "solomon_island_time",                     nameRes = "Solomon Island Time",                     offsetSeconds = 39600),
        "seychelles_time"                          to UnittoTimeZone(id = "seychelles_time",                         nameRes = "Seychelles Time",                         offsetSeconds = 14400),
        "singapore_time"                           to UnittoTimeZone(id = "singapore_time",                          nameRes = "Singapore Time",                          offsetSeconds = 28800),
        "singapore_standard_time"                  to UnittoTimeZone(id = "singapore_standard_time",                 nameRes = "Singapore Standard Time",                 offsetSeconds = 28800),
        "srednekolymsk_time"                       to UnittoTimeZone(id = "srednekolymsk_time",                      nameRes = "Srednekolymsk Time",                      offsetSeconds = 39600),
        "suriname_time"                            to UnittoTimeZone(id = "suriname_time",                           nameRes = "Suriname Time",                           offsetSeconds = -10800),
        "samoa_standard_time"                      to UnittoTimeZone(id = "samoa_standard_time",                     nameRes = "Samoa Standard Time",                     offsetSeconds = -39600),
        "syowa_time"                               to UnittoTimeZone(id = "syowa_time",                              nameRes = "Syowa Time",                              offsetSeconds = 10800),
        "tango_time_zone"                          to UnittoTimeZone(id = "tango_time_zone",                         nameRes = "Tango Time Zone",                         offsetSeconds = -25200),
        "tahiti_time"                              to UnittoTimeZone(id = "tahiti_time",                             nameRes = "Tahiti Time",                             offsetSeconds = -36000),
        "french_southern_and_antarctic_time"       to UnittoTimeZone(id = "french_southern_and_antarctic_time",      nameRes = "French Southern and Antarctic Time",      offsetSeconds = 18000),
        "kerguelen_islands_time"                   to UnittoTimeZone(id = "kerguelen_islands_time",                  nameRes = "Kerguelen (Islands) Time",                offsetSeconds = 18000),
        "tajikistan_time"                          to UnittoTimeZone(id = "tajikistan_time",                         nameRes = "Tajikistan Time",                         offsetSeconds = 18000),
        "tokelau_time"                             to UnittoTimeZone(id = "tokelau_time",                            nameRes = "Tokelau Time",                            offsetSeconds = 46800),
        "east_timor_time"                          to UnittoTimeZone(id = "east_timor_time",                         nameRes = "East Timor Time",                         offsetSeconds = 32400),
        "turkmenistan_time"                        to UnittoTimeZone(id = "turkmenistan_time",                       nameRes = "Turkmenistan Time",                       offsetSeconds = 18000),
        "tonga_summer_time"                        to UnittoTimeZone(id = "tonga_summer_time",                       nameRes = "Tonga Summer Time",                       offsetSeconds = 50400),
        "tonga_time"                               to UnittoTimeZone(id = "tonga_time",                              nameRes = "Tonga Time",                              offsetSeconds = 46800),
        "turkey_time"                              to UnittoTimeZone(id = "turkey_time",                             nameRes = "Turkey Time",                             offsetSeconds = 10800),
        "tuvalu_time"                              to UnittoTimeZone(id = "tuvalu_time",                             nameRes = "Tuvalu Time",                             offsetSeconds = 43200),
        "uniform_time_zone"                        to UnittoTimeZone(id = "uniform_time_zone",                       nameRes = "Uniform Time Zone",                       offsetSeconds = -28800),
        "ulaanbaatar_summer_time"                  to UnittoTimeZone(id = "ulaanbaatar_summer_time",                 nameRes = "Ulaanbaatar Summer Time",                 offsetSeconds = 32400),
        "ulan_bator_summer_time"                   to UnittoTimeZone(id = "ulan_bator_summer_time",                  nameRes = "Ulan Bator Summer Time",                  offsetSeconds = 32400),
        "ulaanbaatar_time"                         to UnittoTimeZone(id = "ulaanbaatar_time",                        nameRes = "Ulaanbaatar Time",                        offsetSeconds = 28800),
        "ulan_bator_time"                          to UnittoTimeZone(id = "ulan_bator_time",                         nameRes = "Ulan Bator Time",                         offsetSeconds = 28800),
        "coordinated_universal_time"               to UnittoTimeZone(id = "coordinated_universal_time",              nameRes = "Coordinated Universal Time",              offsetSeconds = 0),
        "uruguay_summer_time"                      to UnittoTimeZone(id = "uruguay_summer_time",                     nameRes = "Uruguay Summer Time",                     offsetSeconds = -7200),
        "uruguay_time"                             to UnittoTimeZone(id = "uruguay_time",                            nameRes = "Uruguay Time",                            offsetSeconds = -10800),
        "uzbekistan_time"                          to UnittoTimeZone(id = "uzbekistan_time",                         nameRes = "Uzbekistan Time",                         offsetSeconds = 18000),
        "victor_time_zone"                         to UnittoTimeZone(id = "victor_time_zone",                        nameRes = "Victor Time Zone",                        offsetSeconds = -32400),
        "venezuelan_standard_time"                 to UnittoTimeZone(id = "venezuelan_standard_time",                nameRes = "Venezuelan Standard Time",                offsetSeconds = -14400),
        "hora_legal_de_venezuela_spanish"          to UnittoTimeZone(id = "hora_legal_de_venezuela_spanish",         nameRes = "Hora Legal de Venezuela (Spanish)",       offsetSeconds = -14400),
        "vladivostok_summer_time"                  to UnittoTimeZone(id = "vladivostok_summer_time",                 nameRes = "Vladivostok Summer Time",                 offsetSeconds = 39600),
        "vladivostok_time"                         to UnittoTimeZone(id = "vladivostok_time",                        nameRes = "Vladivostok Time",                        offsetSeconds = 36000),
        "vostok_time"                              to UnittoTimeZone(id = "vostok_time",                             nameRes = "Vostok Time",                             offsetSeconds = 21600),
        "vanuatu_time"                             to UnittoTimeZone(id = "vanuatu_time",                            nameRes = "Vanuatu Time",                            offsetSeconds = 39600),
        "efate_time"                               to UnittoTimeZone(id = "efate_time",                              nameRes = "Efate Time",                              offsetSeconds = 39600),
        "whiskey_time_zone"                        to UnittoTimeZone(id = "whiskey_time_zone",                       nameRes = "Whiskey Time Zone",                       offsetSeconds = -36000),
        "wake_time"                                to UnittoTimeZone(id = "wake_time",                               nameRes = "Wake Time",                               offsetSeconds = 43200),
        "western_argentine_summer_time"            to UnittoTimeZone(id = "western_argentine_summer_time",           nameRes = "Western Argentine Summer Time",           offsetSeconds = -10800),
        "west_africa_summer_time"                  to UnittoTimeZone(id = "west_africa_summer_time",                 nameRes = "West Africa Summer Time",                 offsetSeconds = 7200),
        "west_africa_time"                         to UnittoTimeZone(id = "west_africa_time",                        nameRes = "West Africa Time",                        offsetSeconds = 3600),
        "western_european_summer_time"             to UnittoTimeZone(id = "western_european_summer_time",            nameRes = "Western European Summer Time",            offsetSeconds = 3600),
        "western_european_daylight_time"           to UnittoTimeZone(id = "western_european_daylight_time",          nameRes = "Western European Daylight Time",          offsetSeconds = 3600),
        "westeurop_ische_sommerzeit_german"        to UnittoTimeZone(id = "westeurop_ische_sommerzeit_german",       nameRes = "Westeurop-ische Sommerzeit (German)",     offsetSeconds = 3600),
        "western_european_time"                    to UnittoTimeZone(id = "western_european_time",                   nameRes = "Western European Time",                   offsetSeconds = 0),
        "greenwich_mean_time"                      to UnittoTimeZone(id = "greenwich_mean_time",                     nameRes = "Greenwich Mean Time",                     offsetSeconds = 0),
        "westeurop_ische_zeit_german"              to UnittoTimeZone(id = "westeurop_ische_zeit_german",             nameRes = "Westeurop-ische Zeit (German)",           offsetSeconds = 0),
        "wallis_and_futuna_time"                   to UnittoTimeZone(id = "wallis_and_futuna_time",                  nameRes = "Wallis and Futuna Time",                  offsetSeconds = 43200),
        "western_greenland_summer_time"            to UnittoTimeZone(id = "western_greenland_summer_time",           nameRes = "Western Greenland Summer Time",           offsetSeconds = -7200),
        "west_greenland_summer_time"               to UnittoTimeZone(id = "west_greenland_summer_time",              nameRes = "West Greenland Summer Time",              offsetSeconds = -7200),
        "west_greenland_time"                      to UnittoTimeZone(id = "west_greenland_time",                     nameRes = "West Greenland Time",                     offsetSeconds = -10800),
        "western_greenland_time"                   to UnittoTimeZone(id = "western_greenland_time",                  nameRes = "Western Greenland Time",                  offsetSeconds = -10800),
        "western_indonesian_time"                  to UnittoTimeZone(id = "western_indonesian_time",                 nameRes = "Western Indonesian Time",                 offsetSeconds = 25200),
        "waktu_indonesia_barat"                    to UnittoTimeZone(id = "waktu_indonesia_barat",                   nameRes = "Waktu Indonesia Barat",                   offsetSeconds = 25200),
        "eastern_indonesian_time"                  to UnittoTimeZone(id = "eastern_indonesian_time",                 nameRes = "Eastern Indonesian Time",                 offsetSeconds = 32400),
        "waktu_indonesia_timur"                    to UnittoTimeZone(id = "waktu_indonesia_timur",                   nameRes = "Waktu Indonesia Timur",                   offsetSeconds = 32400),
        "central_indonesian_time"                  to UnittoTimeZone(id = "central_indonesian_time",                 nameRes = "Central Indonesian Time",                 offsetSeconds = 28800),
        "waktu_indonesia_tengah"                   to UnittoTimeZone(id = "waktu_indonesia_tengah",                  nameRes = "Waktu Indonesia Tengah",                  offsetSeconds = 28800),
        "west_samoa_time"                          to UnittoTimeZone(id = "west_samoa_time",                         nameRes = "West Samoa Time",                         offsetSeconds = 46800),
        "samoa_time"                               to UnittoTimeZone(id = "samoa_time",                              nameRes = "Samoa Time",                              offsetSeconds = 46800),
        "western_sahara_summer_time"               to UnittoTimeZone(id = "western_sahara_summer_time",              nameRes = "Western Sahara Summer Time",              offsetSeconds = 3600),
        "western_sahara_standard_time"             to UnittoTimeZone(id = "western_sahara_standard_time",            nameRes = "Western Sahara Standard Time",            offsetSeconds = 0),
        "x_ray_time_zone"                          to UnittoTimeZone(id = "x_ray_time_zone",                         nameRes = "X-ray Time Zone",                         offsetSeconds = -39600),
        "yankee_time_zone"                         to UnittoTimeZone(id = "yankee_time_zone",                        nameRes = "Yankee Time Zone",                        offsetSeconds = -43200),
        "yakutsk_summer_time"                      to UnittoTimeZone(id = "yakutsk_summer_time",                     nameRes = "Yakutsk Summer Time",                     offsetSeconds = 36000),
        "yakutsk_time"                             to UnittoTimeZone(id = "yakutsk_time",                            nameRes = "Yakutsk Time",                            offsetSeconds = 32400),
        "yap_time"                                 to UnittoTimeZone(id = "yap_time",                                nameRes = "Yap Time",                                offsetSeconds = 36000),
        "yekaterinburg_summer_time"                to UnittoTimeZone(id = "yekaterinburg_summer_time",               nameRes = "Yekaterinburg Summer Time",               offsetSeconds = 21600),
        "yekaterinburg_time"                       to UnittoTimeZone(id = "yekaterinburg_time",                      nameRes = "Yekaterinburg Time",                      offsetSeconds = 18000),
        "zulu_time_zone"                           to UnittoTimeZone(id = "zulu_time_zone",                          nameRes = "Zulu Time Zone",                          offsetSeconds = 0)
    )

    val favoriteTimeZones: MutableStateFlow<List<UnittoTimeZone>> = MutableStateFlow(emptyList())

//    UNCOMMENT FOR RELEASE
//    val favoriteTimeZones: Flow<List<UnittoTimeZone>> = dao
//        .getAll()
//        .map { list ->
//            val favorites = mutableListOf<UnittoTimeZone>()
//            list.forEach { entity ->
//                val foundTimeZone = allTimeZones[entity.id] ?: return@forEach
//                val mapped = foundTimeZone.copy(
//                    position = entity.position
//                )
//                favorites.add(mapped)
//            }
//
//            favorites
//        }

    suspend fun swapTimeZones(from: String, to: String) = withContext(Dispatchers.IO) {
//        UNCOMMENT FOR RELEASE
//        dao.swap(from, to)

        favoriteTimeZones.update {
            val fromIndex = it.indexOfFirst { it.id == from }
            val toIndex = it.indexOfFirst { it.id == to }

            it
                .toMutableList()
                .apply {
                    add(toIndex, removeAt(fromIndex))
                }
        }

        return@withContext
    }

    suspend fun delete(timeZone: UnittoTimeZone) = withContext(Dispatchers.IO) {
//        UNCOMMENT FOR RELEASE
//        // Only PrimaryKey is needed
//        dao.remove(TimeZoneEntity(id = timeZone.id, position = 0))

        favoriteTimeZones.update { it.minus(timeZone) }
    }

    suspend fun filterAllTimeZones(searchQuery: String): List<UnittoTimeZone> =
        withContext(Dispatchers.IO) {
            val query = searchQuery.trim().lowercase()
            val threshold: Int = query.length / 2
            val timeZonesWithDist = mutableListOf<Pair<UnittoTimeZone, Int>>()

            allTimeZones.values.forEach { timeZone ->
                val timeZoneName = timeZone.nameRes

                if (timeZone.code.lowercase() == query) {
                    timeZonesWithDist.add(timeZone to 1)
                    return@forEach
                }

                when {
                    // not zero, so that lev can have that
                    timeZoneName.startsWith(query) -> {
                        timeZonesWithDist.add(timeZone to 1)
                        return@forEach
                    }

                    timeZoneName.contains(query) -> {
                        timeZonesWithDist.add(timeZone to 2)
                        return@forEach
                    }
                }

                val levDist = timeZoneName
                    .substring(0, minOf(query.length, timeZoneName.length))
                    .lev(query)

                if (levDist < threshold) {
                    timeZonesWithDist.add(timeZone to levDist)
                }
            }

            return@withContext timeZonesWithDist.sortedBy { it.second }.map { it.first }
        }

    suspend fun addToFavorites(timeZone: UnittoTimeZone) {
//        UNCOMMENT FOR RELEASE
//        dao.insert(
//            TimeZoneEntity(
//                id = timeZone.id,
//                position = System.currentTimeMillis().toInt()
//            )
//        )
        favoriteTimeZones.update { it.plus(timeZone) }
    }
}
