/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter.collections

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_currency_1inch
import unitto.core.common.generated.resources.unit_currency_1inch_short
import unitto.core.common.generated.resources.unit_currency_ada
import unitto.core.common.generated.resources.unit_currency_ada_short
import unitto.core.common.generated.resources.unit_currency_aed
import unitto.core.common.generated.resources.unit_currency_aed_short
import unitto.core.common.generated.resources.unit_currency_afn
import unitto.core.common.generated.resources.unit_currency_afn_short
import unitto.core.common.generated.resources.unit_currency_algo
import unitto.core.common.generated.resources.unit_currency_algo_short
import unitto.core.common.generated.resources.unit_currency_all
import unitto.core.common.generated.resources.unit_currency_all_short
import unitto.core.common.generated.resources.unit_currency_amd
import unitto.core.common.generated.resources.unit_currency_amd_short
import unitto.core.common.generated.resources.unit_currency_ang
import unitto.core.common.generated.resources.unit_currency_ang_short
import unitto.core.common.generated.resources.unit_currency_aoa
import unitto.core.common.generated.resources.unit_currency_aoa_short
import unitto.core.common.generated.resources.unit_currency_ars
import unitto.core.common.generated.resources.unit_currency_ars_short
import unitto.core.common.generated.resources.unit_currency_atom
import unitto.core.common.generated.resources.unit_currency_atom_short
import unitto.core.common.generated.resources.unit_currency_aud
import unitto.core.common.generated.resources.unit_currency_aud_short
import unitto.core.common.generated.resources.unit_currency_avax
import unitto.core.common.generated.resources.unit_currency_avax_short
import unitto.core.common.generated.resources.unit_currency_awg
import unitto.core.common.generated.resources.unit_currency_awg_short
import unitto.core.common.generated.resources.unit_currency_azn
import unitto.core.common.generated.resources.unit_currency_azn_short
import unitto.core.common.generated.resources.unit_currency_bam
import unitto.core.common.generated.resources.unit_currency_bam_short
import unitto.core.common.generated.resources.unit_currency_bbd
import unitto.core.common.generated.resources.unit_currency_bbd_short
import unitto.core.common.generated.resources.unit_currency_bch
import unitto.core.common.generated.resources.unit_currency_bch_short
import unitto.core.common.generated.resources.unit_currency_bdt
import unitto.core.common.generated.resources.unit_currency_bdt_short
import unitto.core.common.generated.resources.unit_currency_bgn
import unitto.core.common.generated.resources.unit_currency_bgn_short
import unitto.core.common.generated.resources.unit_currency_bhd
import unitto.core.common.generated.resources.unit_currency_bhd_short
import unitto.core.common.generated.resources.unit_currency_bif
import unitto.core.common.generated.resources.unit_currency_bif_short
import unitto.core.common.generated.resources.unit_currency_bmd
import unitto.core.common.generated.resources.unit_currency_bmd_short
import unitto.core.common.generated.resources.unit_currency_bnb
import unitto.core.common.generated.resources.unit_currency_bnb_short
import unitto.core.common.generated.resources.unit_currency_bnd
import unitto.core.common.generated.resources.unit_currency_bnd_short
import unitto.core.common.generated.resources.unit_currency_bob
import unitto.core.common.generated.resources.unit_currency_bob_short
import unitto.core.common.generated.resources.unit_currency_brl
import unitto.core.common.generated.resources.unit_currency_brl_short
import unitto.core.common.generated.resources.unit_currency_bsd
import unitto.core.common.generated.resources.unit_currency_bsd_short
import unitto.core.common.generated.resources.unit_currency_btc
import unitto.core.common.generated.resources.unit_currency_btc_short
import unitto.core.common.generated.resources.unit_currency_btn
import unitto.core.common.generated.resources.unit_currency_btn_short
import unitto.core.common.generated.resources.unit_currency_busd
import unitto.core.common.generated.resources.unit_currency_busd_short
import unitto.core.common.generated.resources.unit_currency_bwp
import unitto.core.common.generated.resources.unit_currency_bwp_short
import unitto.core.common.generated.resources.unit_currency_byn
import unitto.core.common.generated.resources.unit_currency_byn_short
import unitto.core.common.generated.resources.unit_currency_byr
import unitto.core.common.generated.resources.unit_currency_byr_short
import unitto.core.common.generated.resources.unit_currency_bzd
import unitto.core.common.generated.resources.unit_currency_bzd_short
import unitto.core.common.generated.resources.unit_currency_cad
import unitto.core.common.generated.resources.unit_currency_cad_short
import unitto.core.common.generated.resources.unit_currency_cdf
import unitto.core.common.generated.resources.unit_currency_cdf_short
import unitto.core.common.generated.resources.unit_currency_chf
import unitto.core.common.generated.resources.unit_currency_chf_short
import unitto.core.common.generated.resources.unit_currency_chz
import unitto.core.common.generated.resources.unit_currency_chz_short
import unitto.core.common.generated.resources.unit_currency_clf
import unitto.core.common.generated.resources.unit_currency_clf_short
import unitto.core.common.generated.resources.unit_currency_clp
import unitto.core.common.generated.resources.unit_currency_clp_short
import unitto.core.common.generated.resources.unit_currency_cny
import unitto.core.common.generated.resources.unit_currency_cny_short
import unitto.core.common.generated.resources.unit_currency_cop
import unitto.core.common.generated.resources.unit_currency_cop_short
import unitto.core.common.generated.resources.unit_currency_crc
import unitto.core.common.generated.resources.unit_currency_crc_short
import unitto.core.common.generated.resources.unit_currency_cro
import unitto.core.common.generated.resources.unit_currency_cro_short
import unitto.core.common.generated.resources.unit_currency_cuc
import unitto.core.common.generated.resources.unit_currency_cuc_short
import unitto.core.common.generated.resources.unit_currency_cup
import unitto.core.common.generated.resources.unit_currency_cup_short
import unitto.core.common.generated.resources.unit_currency_cve
import unitto.core.common.generated.resources.unit_currency_cve_short
import unitto.core.common.generated.resources.unit_currency_czk
import unitto.core.common.generated.resources.unit_currency_czk_short
import unitto.core.common.generated.resources.unit_currency_dai
import unitto.core.common.generated.resources.unit_currency_dai_short
import unitto.core.common.generated.resources.unit_currency_djf
import unitto.core.common.generated.resources.unit_currency_djf_short
import unitto.core.common.generated.resources.unit_currency_dkk
import unitto.core.common.generated.resources.unit_currency_dkk_short
import unitto.core.common.generated.resources.unit_currency_doge
import unitto.core.common.generated.resources.unit_currency_doge_short
import unitto.core.common.generated.resources.unit_currency_dop
import unitto.core.common.generated.resources.unit_currency_dop_short
import unitto.core.common.generated.resources.unit_currency_dot
import unitto.core.common.generated.resources.unit_currency_dot_short
import unitto.core.common.generated.resources.unit_currency_dzd
import unitto.core.common.generated.resources.unit_currency_dzd_short
import unitto.core.common.generated.resources.unit_currency_egld
import unitto.core.common.generated.resources.unit_currency_egld_short
import unitto.core.common.generated.resources.unit_currency_egp
import unitto.core.common.generated.resources.unit_currency_egp_short
import unitto.core.common.generated.resources.unit_currency_enj
import unitto.core.common.generated.resources.unit_currency_enj_short
import unitto.core.common.generated.resources.unit_currency_ern
import unitto.core.common.generated.resources.unit_currency_ern_short
import unitto.core.common.generated.resources.unit_currency_etb
import unitto.core.common.generated.resources.unit_currency_etb_short
import unitto.core.common.generated.resources.unit_currency_etc
import unitto.core.common.generated.resources.unit_currency_etc_short
import unitto.core.common.generated.resources.unit_currency_eth
import unitto.core.common.generated.resources.unit_currency_eth_short
import unitto.core.common.generated.resources.unit_currency_eur
import unitto.core.common.generated.resources.unit_currency_eur_short
import unitto.core.common.generated.resources.unit_currency_fil
import unitto.core.common.generated.resources.unit_currency_fil_short
import unitto.core.common.generated.resources.unit_currency_fjd
import unitto.core.common.generated.resources.unit_currency_fjd_short
import unitto.core.common.generated.resources.unit_currency_fkp
import unitto.core.common.generated.resources.unit_currency_fkp_short
import unitto.core.common.generated.resources.unit_currency_ftt
import unitto.core.common.generated.resources.unit_currency_ftt_short
import unitto.core.common.generated.resources.unit_currency_gbp
import unitto.core.common.generated.resources.unit_currency_gbp_short
import unitto.core.common.generated.resources.unit_currency_gel
import unitto.core.common.generated.resources.unit_currency_gel_short
import unitto.core.common.generated.resources.unit_currency_ggp
import unitto.core.common.generated.resources.unit_currency_ggp_short
import unitto.core.common.generated.resources.unit_currency_ghs
import unitto.core.common.generated.resources.unit_currency_ghs_short
import unitto.core.common.generated.resources.unit_currency_gip
import unitto.core.common.generated.resources.unit_currency_gip_short
import unitto.core.common.generated.resources.unit_currency_gmd
import unitto.core.common.generated.resources.unit_currency_gmd_short
import unitto.core.common.generated.resources.unit_currency_gnf
import unitto.core.common.generated.resources.unit_currency_gnf_short
import unitto.core.common.generated.resources.unit_currency_grt
import unitto.core.common.generated.resources.unit_currency_grt_short
import unitto.core.common.generated.resources.unit_currency_gtq
import unitto.core.common.generated.resources.unit_currency_gtq_short
import unitto.core.common.generated.resources.unit_currency_gyd
import unitto.core.common.generated.resources.unit_currency_gyd_short
import unitto.core.common.generated.resources.unit_currency_hkd
import unitto.core.common.generated.resources.unit_currency_hkd_short
import unitto.core.common.generated.resources.unit_currency_hnl
import unitto.core.common.generated.resources.unit_currency_hnl_short
import unitto.core.common.generated.resources.unit_currency_hrk
import unitto.core.common.generated.resources.unit_currency_hrk_short
import unitto.core.common.generated.resources.unit_currency_htg
import unitto.core.common.generated.resources.unit_currency_htg_short
import unitto.core.common.generated.resources.unit_currency_huf
import unitto.core.common.generated.resources.unit_currency_huf_short
import unitto.core.common.generated.resources.unit_currency_icp
import unitto.core.common.generated.resources.unit_currency_icp_short
import unitto.core.common.generated.resources.unit_currency_idr
import unitto.core.common.generated.resources.unit_currency_idr_short
import unitto.core.common.generated.resources.unit_currency_ils
import unitto.core.common.generated.resources.unit_currency_ils_short
import unitto.core.common.generated.resources.unit_currency_imp
import unitto.core.common.generated.resources.unit_currency_imp_short
import unitto.core.common.generated.resources.unit_currency_inj
import unitto.core.common.generated.resources.unit_currency_inj_short
import unitto.core.common.generated.resources.unit_currency_inr
import unitto.core.common.generated.resources.unit_currency_inr_short
import unitto.core.common.generated.resources.unit_currency_iqd
import unitto.core.common.generated.resources.unit_currency_iqd_short
import unitto.core.common.generated.resources.unit_currency_irr
import unitto.core.common.generated.resources.unit_currency_irr_short
import unitto.core.common.generated.resources.unit_currency_isk
import unitto.core.common.generated.resources.unit_currency_isk_short
import unitto.core.common.generated.resources.unit_currency_jep
import unitto.core.common.generated.resources.unit_currency_jep_short
import unitto.core.common.generated.resources.unit_currency_jmd
import unitto.core.common.generated.resources.unit_currency_jmd_short
import unitto.core.common.generated.resources.unit_currency_jod
import unitto.core.common.generated.resources.unit_currency_jod_short
import unitto.core.common.generated.resources.unit_currency_jpy
import unitto.core.common.generated.resources.unit_currency_jpy_short
import unitto.core.common.generated.resources.unit_currency_kes
import unitto.core.common.generated.resources.unit_currency_kes_short
import unitto.core.common.generated.resources.unit_currency_kgs
import unitto.core.common.generated.resources.unit_currency_kgs_short
import unitto.core.common.generated.resources.unit_currency_khr
import unitto.core.common.generated.resources.unit_currency_khr_short
import unitto.core.common.generated.resources.unit_currency_kmf
import unitto.core.common.generated.resources.unit_currency_kmf_short
import unitto.core.common.generated.resources.unit_currency_kpw
import unitto.core.common.generated.resources.unit_currency_kpw_short
import unitto.core.common.generated.resources.unit_currency_krw
import unitto.core.common.generated.resources.unit_currency_krw_short
import unitto.core.common.generated.resources.unit_currency_ksm
import unitto.core.common.generated.resources.unit_currency_ksm_short
import unitto.core.common.generated.resources.unit_currency_kwd
import unitto.core.common.generated.resources.unit_currency_kwd_short
import unitto.core.common.generated.resources.unit_currency_kyd
import unitto.core.common.generated.resources.unit_currency_kyd_short
import unitto.core.common.generated.resources.unit_currency_kzt
import unitto.core.common.generated.resources.unit_currency_kzt_short
import unitto.core.common.generated.resources.unit_currency_lak
import unitto.core.common.generated.resources.unit_currency_lak_short
import unitto.core.common.generated.resources.unit_currency_lbp
import unitto.core.common.generated.resources.unit_currency_lbp_short
import unitto.core.common.generated.resources.unit_currency_link
import unitto.core.common.generated.resources.unit_currency_link_short
import unitto.core.common.generated.resources.unit_currency_lkr
import unitto.core.common.generated.resources.unit_currency_lkr_short
import unitto.core.common.generated.resources.unit_currency_lrd
import unitto.core.common.generated.resources.unit_currency_lrd_short
import unitto.core.common.generated.resources.unit_currency_lsl
import unitto.core.common.generated.resources.unit_currency_lsl_short
import unitto.core.common.generated.resources.unit_currency_ltc
import unitto.core.common.generated.resources.unit_currency_ltc_short
import unitto.core.common.generated.resources.unit_currency_ltl
import unitto.core.common.generated.resources.unit_currency_ltl_short
import unitto.core.common.generated.resources.unit_currency_luna
import unitto.core.common.generated.resources.unit_currency_luna_short
import unitto.core.common.generated.resources.unit_currency_lvl
import unitto.core.common.generated.resources.unit_currency_lvl_short
import unitto.core.common.generated.resources.unit_currency_lyd
import unitto.core.common.generated.resources.unit_currency_lyd_short
import unitto.core.common.generated.resources.unit_currency_mad
import unitto.core.common.generated.resources.unit_currency_mad_short
import unitto.core.common.generated.resources.unit_currency_matic
import unitto.core.common.generated.resources.unit_currency_matic_short
import unitto.core.common.generated.resources.unit_currency_mdl
import unitto.core.common.generated.resources.unit_currency_mdl_short
import unitto.core.common.generated.resources.unit_currency_mga
import unitto.core.common.generated.resources.unit_currency_mga_short
import unitto.core.common.generated.resources.unit_currency_mkd
import unitto.core.common.generated.resources.unit_currency_mkd_short
import unitto.core.common.generated.resources.unit_currency_mmk
import unitto.core.common.generated.resources.unit_currency_mmk_short
import unitto.core.common.generated.resources.unit_currency_mnt
import unitto.core.common.generated.resources.unit_currency_mnt_short
import unitto.core.common.generated.resources.unit_currency_mop
import unitto.core.common.generated.resources.unit_currency_mop_short
import unitto.core.common.generated.resources.unit_currency_mro
import unitto.core.common.generated.resources.unit_currency_mro_short
import unitto.core.common.generated.resources.unit_currency_mur
import unitto.core.common.generated.resources.unit_currency_mur_short
import unitto.core.common.generated.resources.unit_currency_mvr
import unitto.core.common.generated.resources.unit_currency_mvr_short
import unitto.core.common.generated.resources.unit_currency_mwk
import unitto.core.common.generated.resources.unit_currency_mwk_short
import unitto.core.common.generated.resources.unit_currency_mxn
import unitto.core.common.generated.resources.unit_currency_mxn_short
import unitto.core.common.generated.resources.unit_currency_myr
import unitto.core.common.generated.resources.unit_currency_myr_short
import unitto.core.common.generated.resources.unit_currency_mzn
import unitto.core.common.generated.resources.unit_currency_mzn_short
import unitto.core.common.generated.resources.unit_currency_nad
import unitto.core.common.generated.resources.unit_currency_nad_short
import unitto.core.common.generated.resources.unit_currency_ngn
import unitto.core.common.generated.resources.unit_currency_ngn_short
import unitto.core.common.generated.resources.unit_currency_nio
import unitto.core.common.generated.resources.unit_currency_nio_short
import unitto.core.common.generated.resources.unit_currency_nok
import unitto.core.common.generated.resources.unit_currency_nok_short
import unitto.core.common.generated.resources.unit_currency_npr
import unitto.core.common.generated.resources.unit_currency_npr_short
import unitto.core.common.generated.resources.unit_currency_nzd
import unitto.core.common.generated.resources.unit_currency_nzd_short
import unitto.core.common.generated.resources.unit_currency_omr
import unitto.core.common.generated.resources.unit_currency_omr_short
import unitto.core.common.generated.resources.unit_currency_one
import unitto.core.common.generated.resources.unit_currency_one_short
import unitto.core.common.generated.resources.unit_currency_pab
import unitto.core.common.generated.resources.unit_currency_pab_short
import unitto.core.common.generated.resources.unit_currency_pen
import unitto.core.common.generated.resources.unit_currency_pen_short
import unitto.core.common.generated.resources.unit_currency_pgk
import unitto.core.common.generated.resources.unit_currency_pgk_short
import unitto.core.common.generated.resources.unit_currency_php
import unitto.core.common.generated.resources.unit_currency_php_short
import unitto.core.common.generated.resources.unit_currency_pkr
import unitto.core.common.generated.resources.unit_currency_pkr_short
import unitto.core.common.generated.resources.unit_currency_pln
import unitto.core.common.generated.resources.unit_currency_pln_short
import unitto.core.common.generated.resources.unit_currency_pyg
import unitto.core.common.generated.resources.unit_currency_pyg_short
import unitto.core.common.generated.resources.unit_currency_qar
import unitto.core.common.generated.resources.unit_currency_qar_short
import unitto.core.common.generated.resources.unit_currency_ron
import unitto.core.common.generated.resources.unit_currency_ron_short
import unitto.core.common.generated.resources.unit_currency_rsd
import unitto.core.common.generated.resources.unit_currency_rsd_short
import unitto.core.common.generated.resources.unit_currency_rub
import unitto.core.common.generated.resources.unit_currency_rub_short
import unitto.core.common.generated.resources.unit_currency_rwf
import unitto.core.common.generated.resources.unit_currency_rwf_short
import unitto.core.common.generated.resources.unit_currency_sar
import unitto.core.common.generated.resources.unit_currency_sar_short
import unitto.core.common.generated.resources.unit_currency_sbd
import unitto.core.common.generated.resources.unit_currency_sbd_short
import unitto.core.common.generated.resources.unit_currency_scr
import unitto.core.common.generated.resources.unit_currency_scr_short
import unitto.core.common.generated.resources.unit_currency_sdg
import unitto.core.common.generated.resources.unit_currency_sdg_short
import unitto.core.common.generated.resources.unit_currency_sek
import unitto.core.common.generated.resources.unit_currency_sek_short
import unitto.core.common.generated.resources.unit_currency_sgd
import unitto.core.common.generated.resources.unit_currency_sgd_short
import unitto.core.common.generated.resources.unit_currency_shib
import unitto.core.common.generated.resources.unit_currency_shib_short
import unitto.core.common.generated.resources.unit_currency_shp
import unitto.core.common.generated.resources.unit_currency_shp_short
import unitto.core.common.generated.resources.unit_currency_sll
import unitto.core.common.generated.resources.unit_currency_sll_short
import unitto.core.common.generated.resources.unit_currency_sol
import unitto.core.common.generated.resources.unit_currency_sol_short
import unitto.core.common.generated.resources.unit_currency_sos
import unitto.core.common.generated.resources.unit_currency_sos_short
import unitto.core.common.generated.resources.unit_currency_srd
import unitto.core.common.generated.resources.unit_currency_srd_short
import unitto.core.common.generated.resources.unit_currency_std
import unitto.core.common.generated.resources.unit_currency_std_short
import unitto.core.common.generated.resources.unit_currency_svc
import unitto.core.common.generated.resources.unit_currency_svc_short
import unitto.core.common.generated.resources.unit_currency_syp
import unitto.core.common.generated.resources.unit_currency_syp_short
import unitto.core.common.generated.resources.unit_currency_szl
import unitto.core.common.generated.resources.unit_currency_szl_short
import unitto.core.common.generated.resources.unit_currency_thb
import unitto.core.common.generated.resources.unit_currency_thb_short
import unitto.core.common.generated.resources.unit_currency_theta
import unitto.core.common.generated.resources.unit_currency_theta_short
import unitto.core.common.generated.resources.unit_currency_tjs
import unitto.core.common.generated.resources.unit_currency_tjs_short
import unitto.core.common.generated.resources.unit_currency_tmt
import unitto.core.common.generated.resources.unit_currency_tmt_short
import unitto.core.common.generated.resources.unit_currency_tnd
import unitto.core.common.generated.resources.unit_currency_tnd_short
import unitto.core.common.generated.resources.unit_currency_top
import unitto.core.common.generated.resources.unit_currency_top_short
import unitto.core.common.generated.resources.unit_currency_trx
import unitto.core.common.generated.resources.unit_currency_trx_short
import unitto.core.common.generated.resources.unit_currency_try
import unitto.core.common.generated.resources.unit_currency_try_short
import unitto.core.common.generated.resources.unit_currency_ttd
import unitto.core.common.generated.resources.unit_currency_ttd_short
import unitto.core.common.generated.resources.unit_currency_twd
import unitto.core.common.generated.resources.unit_currency_twd_short
import unitto.core.common.generated.resources.unit_currency_tzs
import unitto.core.common.generated.resources.unit_currency_tzs_short
import unitto.core.common.generated.resources.unit_currency_uah
import unitto.core.common.generated.resources.unit_currency_uah_short
import unitto.core.common.generated.resources.unit_currency_ugx
import unitto.core.common.generated.resources.unit_currency_ugx_short
import unitto.core.common.generated.resources.unit_currency_uni
import unitto.core.common.generated.resources.unit_currency_uni_short
import unitto.core.common.generated.resources.unit_currency_usd
import unitto.core.common.generated.resources.unit_currency_usd_short
import unitto.core.common.generated.resources.unit_currency_usdc
import unitto.core.common.generated.resources.unit_currency_usdc_short
import unitto.core.common.generated.resources.unit_currency_usdt
import unitto.core.common.generated.resources.unit_currency_usdt_short
import unitto.core.common.generated.resources.unit_currency_uyu
import unitto.core.common.generated.resources.unit_currency_uyu_short
import unitto.core.common.generated.resources.unit_currency_uzs
import unitto.core.common.generated.resources.unit_currency_uzs_short
import unitto.core.common.generated.resources.unit_currency_vef
import unitto.core.common.generated.resources.unit_currency_vef_short
import unitto.core.common.generated.resources.unit_currency_vet
import unitto.core.common.generated.resources.unit_currency_vet_short
import unitto.core.common.generated.resources.unit_currency_vnd
import unitto.core.common.generated.resources.unit_currency_vnd_short
import unitto.core.common.generated.resources.unit_currency_vuv
import unitto.core.common.generated.resources.unit_currency_vuv_short
import unitto.core.common.generated.resources.unit_currency_wbtc
import unitto.core.common.generated.resources.unit_currency_wbtc_short
import unitto.core.common.generated.resources.unit_currency_wst
import unitto.core.common.generated.resources.unit_currency_wst_short
import unitto.core.common.generated.resources.unit_currency_xaf
import unitto.core.common.generated.resources.unit_currency_xaf_short
import unitto.core.common.generated.resources.unit_currency_xag
import unitto.core.common.generated.resources.unit_currency_xag_short
import unitto.core.common.generated.resources.unit_currency_xau
import unitto.core.common.generated.resources.unit_currency_xau_short
import unitto.core.common.generated.resources.unit_currency_xcd
import unitto.core.common.generated.resources.unit_currency_xcd_short
import unitto.core.common.generated.resources.unit_currency_xdr
import unitto.core.common.generated.resources.unit_currency_xdr_short
import unitto.core.common.generated.resources.unit_currency_xlm
import unitto.core.common.generated.resources.unit_currency_xlm_short
import unitto.core.common.generated.resources.unit_currency_xmr
import unitto.core.common.generated.resources.unit_currency_xmr_short
import unitto.core.common.generated.resources.unit_currency_xof
import unitto.core.common.generated.resources.unit_currency_xof_short
import unitto.core.common.generated.resources.unit_currency_xpf
import unitto.core.common.generated.resources.unit_currency_xpf_short
import unitto.core.common.generated.resources.unit_currency_xrp
import unitto.core.common.generated.resources.unit_currency_xrp_short
import unitto.core.common.generated.resources.unit_currency_yer
import unitto.core.common.generated.resources.unit_currency_yer_short
import unitto.core.common.generated.resources.unit_currency_zar
import unitto.core.common.generated.resources.unit_currency_zar_short
import unitto.core.common.generated.resources.unit_currency_zmk
import unitto.core.common.generated.resources.unit_currency_zmk_short
import unitto.core.common.generated.resources.unit_currency_zmw
import unitto.core.common.generated.resources.unit_currency_zmw_short
import unitto.core.common.generated.resources.unit_currency_zwl
import unitto.core.common.generated.resources.unit_currency_zwl_short

internal val currencyCollection: List<BasicUnit> by lazy {
  listOf(
    NormalUnit(
      UnitID.currency_1inch,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_1inch,
      Res.string.unit_currency_1inch_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ada,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ada,
      Res.string.unit_currency_ada_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_aed,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_aed,
      Res.string.unit_currency_aed_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_afn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_afn,
      Res.string.unit_currency_afn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_algo,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_algo,
      Res.string.unit_currency_algo_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_all,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_all,
      Res.string.unit_currency_all_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_amd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_amd,
      Res.string.unit_currency_amd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ang,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ang,
      Res.string.unit_currency_ang_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_aoa,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_aoa,
      Res.string.unit_currency_aoa_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ars,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ars,
      Res.string.unit_currency_ars_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_atom,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_atom,
      Res.string.unit_currency_atom_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_aud,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_aud,
      Res.string.unit_currency_aud_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_avax,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_avax,
      Res.string.unit_currency_avax_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_awg,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_awg,
      Res.string.unit_currency_awg_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_azn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_azn,
      Res.string.unit_currency_azn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bam,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bam,
      Res.string.unit_currency_bam_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bbd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bbd,
      Res.string.unit_currency_bbd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bch,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bch,
      Res.string.unit_currency_bch_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bdt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bdt,
      Res.string.unit_currency_bdt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bgn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bgn,
      Res.string.unit_currency_bgn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bhd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bhd,
      Res.string.unit_currency_bhd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bif,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bif,
      Res.string.unit_currency_bif_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bmd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bmd,
      Res.string.unit_currency_bmd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bnb,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bnb,
      Res.string.unit_currency_bnb_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bnd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bnd,
      Res.string.unit_currency_bnd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bob,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bob,
      Res.string.unit_currency_bob_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_brl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_brl,
      Res.string.unit_currency_brl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bsd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bsd,
      Res.string.unit_currency_bsd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_btc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_btc,
      Res.string.unit_currency_btc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_btn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_btn,
      Res.string.unit_currency_btn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_busd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_busd,
      Res.string.unit_currency_busd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bwp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bwp,
      Res.string.unit_currency_bwp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_byn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_byn,
      Res.string.unit_currency_byn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_byr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_byr,
      Res.string.unit_currency_byr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_bzd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_bzd,
      Res.string.unit_currency_bzd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cad,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cad,
      Res.string.unit_currency_cad_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cdf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cdf,
      Res.string.unit_currency_cdf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_chf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_chf,
      Res.string.unit_currency_chf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_chz,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_chz,
      Res.string.unit_currency_chz_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_clf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_clf,
      Res.string.unit_currency_clf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_clp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_clp,
      Res.string.unit_currency_clp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cny,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cny,
      Res.string.unit_currency_cny_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cop,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cop,
      Res.string.unit_currency_cop_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_crc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_crc,
      Res.string.unit_currency_crc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cro,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cro,
      Res.string.unit_currency_cro_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cuc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cuc,
      Res.string.unit_currency_cuc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cup,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cup,
      Res.string.unit_currency_cup_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_cve,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_cve,
      Res.string.unit_currency_cve_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_czk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_czk,
      Res.string.unit_currency_czk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_dai,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_dai,
      Res.string.unit_currency_dai_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_djf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_djf,
      Res.string.unit_currency_djf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_dkk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_dkk,
      Res.string.unit_currency_dkk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_doge,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_doge,
      Res.string.unit_currency_doge_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_dop,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_dop,
      Res.string.unit_currency_dop_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_dot,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_dot,
      Res.string.unit_currency_dot_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_dzd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_dzd,
      Res.string.unit_currency_dzd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_egld,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_egld,
      Res.string.unit_currency_egld_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_egp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_egp,
      Res.string.unit_currency_egp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_enj,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_enj,
      Res.string.unit_currency_enj_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ern,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ern,
      Res.string.unit_currency_ern_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_etb,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_etb,
      Res.string.unit_currency_etb_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_etc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_etc,
      Res.string.unit_currency_etc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_eth,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_eth,
      Res.string.unit_currency_eth_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_eur,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_eur,
      Res.string.unit_currency_eur_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_fil,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_fil,
      Res.string.unit_currency_fil_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_fjd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_fjd,
      Res.string.unit_currency_fjd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_fkp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_fkp,
      Res.string.unit_currency_fkp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ftt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ftt,
      Res.string.unit_currency_ftt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gbp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gbp,
      Res.string.unit_currency_gbp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gel,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gel,
      Res.string.unit_currency_gel_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ggp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ggp,
      Res.string.unit_currency_ggp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ghs,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ghs,
      Res.string.unit_currency_ghs_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gip,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gip,
      Res.string.unit_currency_gip_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gmd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gmd,
      Res.string.unit_currency_gmd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gnf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gnf,
      Res.string.unit_currency_gnf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_grt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_grt,
      Res.string.unit_currency_grt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gtq,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gtq,
      Res.string.unit_currency_gtq_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_gyd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_gyd,
      Res.string.unit_currency_gyd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_hkd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_hkd,
      Res.string.unit_currency_hkd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_hnl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_hnl,
      Res.string.unit_currency_hnl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_hrk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_hrk,
      Res.string.unit_currency_hrk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_htg,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_htg,
      Res.string.unit_currency_htg_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_huf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_huf,
      Res.string.unit_currency_huf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_icp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_icp,
      Res.string.unit_currency_icp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_idr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_idr,
      Res.string.unit_currency_idr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ils,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ils,
      Res.string.unit_currency_ils_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_imp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_imp,
      Res.string.unit_currency_imp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_inj,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_inj,
      Res.string.unit_currency_inj_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_inr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_inr,
      Res.string.unit_currency_inr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_iqd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_iqd,
      Res.string.unit_currency_iqd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_irr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_irr,
      Res.string.unit_currency_irr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_isk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_isk,
      Res.string.unit_currency_isk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_jep,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_jep,
      Res.string.unit_currency_jep_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_jmd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_jmd,
      Res.string.unit_currency_jmd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_jod,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_jod,
      Res.string.unit_currency_jod_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_jpy,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_jpy,
      Res.string.unit_currency_jpy_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kes,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kes,
      Res.string.unit_currency_kes_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kgs,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kgs,
      Res.string.unit_currency_kgs_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_khr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_khr,
      Res.string.unit_currency_khr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kmf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kmf,
      Res.string.unit_currency_kmf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kpw,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kpw,
      Res.string.unit_currency_kpw_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_krw,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_krw,
      Res.string.unit_currency_krw_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ksm,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ksm,
      Res.string.unit_currency_ksm_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kwd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kwd,
      Res.string.unit_currency_kwd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kyd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kyd,
      Res.string.unit_currency_kyd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_kzt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_kzt,
      Res.string.unit_currency_kzt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lak,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lak,
      Res.string.unit_currency_lak_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lbp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lbp,
      Res.string.unit_currency_lbp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_link,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_link,
      Res.string.unit_currency_link_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lkr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lkr,
      Res.string.unit_currency_lkr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lrd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lrd,
      Res.string.unit_currency_lrd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lsl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lsl,
      Res.string.unit_currency_lsl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ltc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ltc,
      Res.string.unit_currency_ltc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ltl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ltl,
      Res.string.unit_currency_ltl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_luna,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_luna,
      Res.string.unit_currency_luna_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lvl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lvl,
      Res.string.unit_currency_lvl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_lyd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_lyd,
      Res.string.unit_currency_lyd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mad,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mad,
      Res.string.unit_currency_mad_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_matic,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_matic,
      Res.string.unit_currency_matic_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mdl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mdl,
      Res.string.unit_currency_mdl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mga,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mga,
      Res.string.unit_currency_mga_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mkd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mkd,
      Res.string.unit_currency_mkd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mmk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mmk,
      Res.string.unit_currency_mmk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mnt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mnt,
      Res.string.unit_currency_mnt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mop,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mop,
      Res.string.unit_currency_mop_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mro,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mro,
      Res.string.unit_currency_mro_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mur,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mur,
      Res.string.unit_currency_mur_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mvr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mvr,
      Res.string.unit_currency_mvr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mwk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mwk,
      Res.string.unit_currency_mwk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mxn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mxn,
      Res.string.unit_currency_mxn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_myr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_myr,
      Res.string.unit_currency_myr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_mzn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_mzn,
      Res.string.unit_currency_mzn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_nad,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_nad,
      Res.string.unit_currency_nad_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ngn,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ngn,
      Res.string.unit_currency_ngn_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_nio,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_nio,
      Res.string.unit_currency_nio_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_nok,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_nok,
      Res.string.unit_currency_nok_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_npr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_npr,
      Res.string.unit_currency_npr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_nzd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_nzd,
      Res.string.unit_currency_nzd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_omr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_omr,
      Res.string.unit_currency_omr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_one,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_one,
      Res.string.unit_currency_one_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_pab,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_pab,
      Res.string.unit_currency_pab_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_pen,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_pen,
      Res.string.unit_currency_pen_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_pgk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_pgk,
      Res.string.unit_currency_pgk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_php,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_php,
      Res.string.unit_currency_php_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_pkr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_pkr,
      Res.string.unit_currency_pkr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_pln,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_pln,
      Res.string.unit_currency_pln_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_pyg,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_pyg,
      Res.string.unit_currency_pyg_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_qar,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_qar,
      Res.string.unit_currency_qar_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ron,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ron,
      Res.string.unit_currency_ron_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_rsd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_rsd,
      Res.string.unit_currency_rsd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_rub,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_rub,
      Res.string.unit_currency_rub_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_rwf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_rwf,
      Res.string.unit_currency_rwf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sar,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sar,
      Res.string.unit_currency_sar_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sbd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sbd,
      Res.string.unit_currency_sbd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_scr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_scr,
      Res.string.unit_currency_scr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sdg,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sdg,
      Res.string.unit_currency_sdg_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sek,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sek,
      Res.string.unit_currency_sek_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sgd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sgd,
      Res.string.unit_currency_sgd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_shib,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_shib,
      Res.string.unit_currency_shib_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_shp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_shp,
      Res.string.unit_currency_shp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sll,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sll,
      Res.string.unit_currency_sll_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sol,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sol,
      Res.string.unit_currency_sol_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_sos,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_sos,
      Res.string.unit_currency_sos_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_srd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_srd,
      Res.string.unit_currency_srd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_std,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_std,
      Res.string.unit_currency_std_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_svc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_svc,
      Res.string.unit_currency_svc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_syp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_syp,
      Res.string.unit_currency_syp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_szl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_szl,
      Res.string.unit_currency_szl_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_thb,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_thb,
      Res.string.unit_currency_thb_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_theta,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_theta,
      Res.string.unit_currency_theta_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_tjs,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_tjs,
      Res.string.unit_currency_tjs_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_tmt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_tmt,
      Res.string.unit_currency_tmt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_tnd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_tnd,
      Res.string.unit_currency_tnd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_top,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_top,
      Res.string.unit_currency_top_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_trx,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_trx,
      Res.string.unit_currency_trx_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_try,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_try,
      Res.string.unit_currency_try_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ttd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ttd,
      Res.string.unit_currency_ttd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_twd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_twd,
      Res.string.unit_currency_twd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_tzs,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_tzs,
      Res.string.unit_currency_tzs_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_uah,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_uah,
      Res.string.unit_currency_uah_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_ugx,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_ugx,
      Res.string.unit_currency_ugx_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_uni,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_uni,
      Res.string.unit_currency_uni_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_usd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_usd,
      Res.string.unit_currency_usd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_usdc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_usdc,
      Res.string.unit_currency_usdc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_usdt,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_usdt,
      Res.string.unit_currency_usdt_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_uyu,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_uyu,
      Res.string.unit_currency_uyu_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_uzs,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_uzs,
      Res.string.unit_currency_uzs_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_vef,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_vef,
      Res.string.unit_currency_vef_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_vet,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_vet,
      Res.string.unit_currency_vet_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_vnd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_vnd,
      Res.string.unit_currency_vnd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_vuv,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_vuv,
      Res.string.unit_currency_vuv_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_wbtc,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_wbtc,
      Res.string.unit_currency_wbtc_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_wst,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_wst,
      Res.string.unit_currency_wst_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xaf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xaf,
      Res.string.unit_currency_xaf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xag,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xag,
      Res.string.unit_currency_xag_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xau,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xau,
      Res.string.unit_currency_xau_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xcd,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xcd,
      Res.string.unit_currency_xcd_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xdr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xdr,
      Res.string.unit_currency_xdr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xlm,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xlm,
      Res.string.unit_currency_xlm_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xmr,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xmr,
      Res.string.unit_currency_xmr_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xof,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xof,
      Res.string.unit_currency_xof_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xpf,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xpf,
      Res.string.unit_currency_xpf_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_xrp,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_xrp,
      Res.string.unit_currency_xrp_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_yer,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_yer,
      Res.string.unit_currency_yer_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_zar,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_zar,
      Res.string.unit_currency_zar_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_zmk,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_zmk,
      Res.string.unit_currency_zmk_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_zmw,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_zmw,
      Res.string.unit_currency_zmw_short,
      backward = true,
    ),
    NormalUnit(
      UnitID.currency_zwl,
      KBigDecimal.ZERO,
      UnitGroup.CURRENCY,
      Res.string.unit_currency_zwl,
      Res.string.unit_currency_zwl_short,
      backward = true,
    ),
  )
}
