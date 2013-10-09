/**
 * <Palmcalc is a multipurpose application consisting of calculators, converters
 * and world clock> Copyright (C) <2013> <Cybrosys Technologies pvt. ltd.>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 **/

package com.cybrosys.clock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

// This class used to select various countries included in TimeZones.
public class AddTimeZone extends Activity {
	Time timeSetter;
	String hour, min;
	int minute, hourOfDay;
	List<TimeZoneEntry> timezones = new ArrayList<TimeZoneEntry>();
	List<TimeZoneEntry> timezonesorig = new ArrayList<TimeZoneEntry>();
	List<HashMap<String, String>> clockList;
	ArrayAdapter<TimeZoneEntry> adapter = null;
	SimpleAdapter clockadapter;
	ListView tzlist = null;
	EditText tzfilter = null;
	TimeZoneEntry current = null;
	SQLiteDatabase db = null;

	// Country names list declarations of various Time Zones.
	String[] tzids = { "Africa/Abidjan", "Africa/Accra", "Africa/Addis_Ababa",
			"Africa/Algiers", "Africa/Asmara", "Africa/Asmera",
			"Africa/Bamako", "Africa/Bangui", "Africa/Banjul", "Africa/Bissau",
			"Africa/Blantyre", "Africa/Brazzaville", "Africa/Bujumbura",
			"Africa/Cairo", "Africa/Casablanca", "Africa/Ceuta",
			"Africa/Conakry", "Africa/Dakar", "Africa/Dar_es_Salaam",
			"Africa/Djibouti", "Africa/Douala", "Africa/El_Aaiun",
			"Africa/Freetown", "Africa/Gaborone", "Africa/Harare",
			"Africa/Johannesburg", "Africa/Kampala", "Africa/Khartoum",
			"Africa/Kigali", "Africa/Kinshasa", "Africa/Lagos",
			"Africa/Libreville", "Africa/Lome", "Africa/Luanda",
			"Africa/Lubumbashi", "Africa/Lusaka", "Africa/Malabo",
			"Africa/Maputo", "Africa/Maseru", "Africa/Mbabane",
			"Africa/Mogadishu", "Africa/Monrovia", "Africa/Nairobi",
			"Africa/Ndjamena", "Africa/Niamey", "Africa/Nouakchott",
			"Africa/Ouagadougou", "Africa/Porto-Novo", "Africa/Sao_Tome",
			"Africa/Timbuktu", "Africa/Tripoli", "Africa/Tunis",
			"Africa/Windhoek", "America/Adak", "America/Anchorage",
			"America/Anguilla", "America/Antigua", "America/Araguaina",
			"America/Argentina/Buenos_Aires", "America/Argentina/Catamarca",
			"America/Argentina/ComodRivadavia", "America/Argentina/Cordoba",
			"America/Argentina/Jujuy", "America/Argentina/La_Rioja",
			"America/Argentina/Mendoza", "America/Argentina/Rio_Gallegos",
			"America/Argentina/Salta", "America/Argentina/San_Juan",
			"America/Argentina/San_Luis", "America/Argentina/Tucuman",
			"America/Argentina/Ushuaia", "America/Aruba", "America/Asuncion",
			"America/Atikokan", "America/Atka", "America/Bahia",
			"America/Bahia_Banderas", "America/Barbados", "America/Belem",
			"America/Belize", "America/Blanc-Sablon", "America/Boa_Vista",
			"America/Bogota", "America/Boise", "America/Buenos_Aires",
			"America/Cambridge_Bay", "America/Campo_Grande", "America/Cancun",
			"America/Caracas", "America/Catamarca", "America/Cayenne",
			"America/Cayman", "America/Chicago", "America/Chihuahua",
			"America/Coral_Harbour", "America/Cordoba", "America/Costa_Rica",
			"America/Cuiaba", "America/Curacao", "America/Danmarkshavn",
			"America/Dawson", "America/Dawson_Creek", "America/Denver",
			"America/Detroit", "America/Dominica", "America/Edmonton",
			"America/Eirunepe", "America/El_Salvador", "America/Ensenada",
			"America/Fort_Wayne", "America/Fortaleza", "America/Glace_Bay",
			"America/Godthab", "America/Goose_Bay", "America/Grand_Turk",
			"America/Grenada", "America/Guadeloupe", "America/Guatemala",
			"America/Guayaquil", "America/Guyana", "America/Halifax",
			"America/Havana", "America/Hermosillo",
			"America/Indiana/Indianapolis", "America/Indiana/Knox",
			"America/Indiana/Marengo", "America/Indiana/Petersburg",
			"America/Indiana/Tell_City", "America/Indiana/Vevay",
			"America/Indiana/Vincennes", "America/Indiana/Winamac",
			"America/Indianapolis", "America/Inuvik", "America/Iqaluit",
			"America/Jamaica", "America/Jujuy", "America/Juneau",
			"America/Kentucky/Louisville", "America/Kentucky/Monticello",
			"America/Knox_IN", "America/Kralendijk", "America/La_Paz",
			"America/Lima", "America/Los_Angeles", "America/Louisville",
			"America/Lower_Princes", "America/Maceio", "America/Managua",
			"America/Manaus", "America/Marigot", "America/Martinique",
			"America/Matamoros", "America/Mazatlan", "America/Mendoza",
			"America/Menominee", "America/Merida", "America/Metlakatla",
			"America/Mexico_City", "America/Miquelon", "America/Moncton",
			"America/Monterrey", "America/Montevideo", "America/Montreal",
			"America/Montserrat", "America/Nassau", "America/New_York",
			"America/Nipigon", "America/Nome", "America/Noronha",
			"America/North_Dakota/Beulah", "America/North_Dakota/Center",
			"America/North_Dakota/New_Salem", "America/Ojinaga",
			"America/Panama", "America/Pangnirtung", "America/Paramaribo",
			"America/Phoenix", "America/Port-au-Prince",
			"America/Port_of_Spain", "America/Porto_Acre",
			"America/Porto_Velho", "America/Puerto_Rico",
			"America/Rainy_River", "America/Rankin_Inlet", "America/Recife",
			"America/Regina", "America/Resolute", "America/Rio_Branco",
			"America/Rosario", "America/Santa_Isabel", "America/Santarem",
			"America/Santiago", "America/Santo_Domingo", "America/Sao_Paulo",
			"America/Scoresbysund", "America/Shiprock", "America/Sitka",
			"America/St_Barthelemy", "America/St_Johns", "America/St_Kitts",
			"America/St_Lucia", "America/St_Thomas", "America/St_Vincent",
			"America/Swift_Current", "America/Tegucigalpa", "America/Thule",
			"America/Thunder_Bay", "America/Tijuana", "America/Toronto",
			"America/Tortola", "America/Vancouver", "America/Virgin",
			"America/Whitehorse", "America/Winnipeg", "America/Yakutat",
			"America/Yellowknife", "Antarctica/Casey", "Antarctica/Davis",
			"Antarctica/DumontDUrville", "Antarctica/Macquarie",
			"Antarctica/Mawson", "Antarctica/McMurdo", "Antarctica/Palmer",
			"Antarctica/Rothera", "Antarctica/South_Pole", "Antarctica/Syowa",
			"Antarctica/Vostok", "Arctic/Longyearbyen", "Asia/Aden",
			"Asia/Almaty", "Asia/Amman", "Asia/Anadyr", "Asia/Aqtau",
			"Asia/Aqtobe", "Asia/Ashgabat", "Asia/Ashkhabad", "Asia/Baghdad",
			"Asia/Bahrain", "Asia/Baku", "Asia/Bangkok", "Asia/Beirut",
			"Asia/Bishkek", "Asia/Brunei", "Asia/Calcutta", "Asia/Choibalsan",
			"Asia/Chongqing", "Asia/Chungking", "Asia/Colombo", "Asia/Dacca",
			"Asia/Damascus", "Asia/Dhaka", "Asia/Dili", "Asia/Dubai",
			"Asia/Dushanbe", "Asia/Gaza", "Asia/Harbin", "Asia/Ho_Chi_Minh",
			"Asia/Hong_Kong", "Asia/Hovd", "Asia/Irkutsk", "Asia/Istanbul",
			"Asia/Jakarta", "Asia/Jayapura", "Asia/Jerusalem", "Asia/Kabul",
			"Asia/Kamchatka", "Asia/Karachi", "Asia/Kashgar", "Asia/Kathmandu",
			"Asia/Katmandu", "Asia/Kolkata", "Asia/Krasnoyarsk",
			"Asia/Kuala_Lumpur", "Asia/Kuching", "Asia/Kuwait", "Asia/Macao",
			"Asia/Macau", "Asia/Magadan", "Asia/Makassar", "Asia/Manila",
			"Asia/Muscat", "Asia/Nicosia", "Asia/Novokuznetsk",
			"Asia/Novosibirsk", "Asia/Omsk", "Asia/Oral", "Asia/Phnom_Penh",
			"Asia/Pontianak", "Asia/Pyongyang", "Asia/Qatar", "Asia/Qyzylorda",
			"Asia/Rangoon", "Asia/Riyadh", "Asia/Saigon", "Asia/Sakhalin",
			"Asia/Samarkand", "Asia/Seoul", "Asia/Shanghai", "Asia/Singapore",
			"Asia/Taipei", "Asia/Tashkent", "Asia/Tbilisi", "Asia/Tehran",
			"Asia/Tel_Aviv", "Asia/Thimbu", "Asia/Thimphu", "Asia/Tokyo",
			"Asia/Ujung_Pandang", "Asia/Ulaanbaatar", "Asia/Ulan_Bator",
			"Asia/Urumqi", "Asia/Vientiane", "Asia/Vladivostok",
			"Asia/Yakutsk", "Asia/Yekaterinburg", "Asia/Yerevan",
			"Atlantic/Azores", "Atlantic/Bermuda", "Atlantic/Canary",
			"Atlantic/Cape_Verde", "Atlantic/Faeroe", "Atlantic/Faroe",
			"Atlantic/Jan_Mayen", "Atlantic/Madeira", "Atlantic/Reykjavik",
			"Atlantic/South_Georgia", "Atlantic/St_Helena", "Atlantic/Stanley",
			"Australia/ACT", "Australia/Adelaide", "Australia/Brisbane",
			"Australia/Broken_Hill", "Australia/Canberra", "Australia/Currie",
			"Australia/Darwin", "Australia/Eucla", "Australia/Hobart",
			"Australia/LHI", "Australia/Lindeman", "Australia/Lord_Howe",
			"Australia/Melbourne", "Australia/NSW", "Australia/North",
			"Australia/Perth", "Australia/Queensland", "Australia/South",
			"Australia/Sydney", "Australia/Tasmania", "Australia/Victoria",
			"Australia/West", "Australia/Yancowinna", "Brazil/Acre",
			"Brazil/DeNoronha", "Brazil/East", "Brazil/West", "CET", "CST6CDT",
			"Canada/Atlantic", "Canada/Central", "Canada/East-Saskatchewan",
			"Canada/Eastern", "Canada/Mountain", "Canada/Newfoundland",
			"Canada/Pacific", "Canada/Saskatchewan", "Canada/Yukon",
			"Chile/Continental", "Chile/EasterIsland", "Cuba", "EET", "EST",
			"EST5EDT", "Egypt", "Eire", "Etc/GMT", "Etc/GMT+0", "Etc/GMT+1",
			"Etc/GMT+10", "Etc/GMT+11", "Etc/GMT+12", "Etc/GMT+2", "Etc/GMT+3",
			"Etc/GMT+4", "Etc/GMT+5", "Etc/GMT+6", "Etc/GMT+7", "Etc/GMT+8",
			"Etc/GMT+9", "Etc/GMT-0", "Etc/GMT-1", "Etc/GMT-10", "Etc/GMT-11",
			"Etc/GMT-12", "Etc/GMT-13", "Etc/GMT-14", "Etc/GMT-2", "Etc/GMT-3",
			"Etc/GMT-4", "Etc/GMT-5", "Etc/GMT-6", "Etc/GMT-7", "Etc/GMT-8",
			"Etc/GMT-9", "Etc/GMT0", "Etc/Greenwich", "Etc/UCT", "Etc/UTC",
			"Etc/Universal", "Etc/Zulu", "Europe/Amsterdam", "Europe/Andorra",
			"Europe/Athens", "Europe/Belfast", "Europe/Belgrade",
			"Europe/Berlin", "Europe/Bratislava", "Europe/Brussels",
			"Europe/Bucharest", "Europe/Budapest", "Europe/Chisinau",
			"Europe/Copenhagen", "Europe/Dublin", "Europe/Gibraltar",
			"Europe/Guernsey", "Europe/Helsinki", "Europe/Isle_of_Man",
			"Europe/Istanbul", "Europe/Jersey", "Europe/Kaliningrad",
			"Europe/Kiev", "Europe/Lisbon", "Europe/Ljubljana",
			"Europe/London", "Europe/Luxembourg", "Europe/Madrid",
			"Europe/Malta", "Europe/Mariehamn", "Europe/Minsk",
			"Europe/Monaco", "Europe/Moscow", "Europe/Nicosia", "Europe/Oslo",
			"Europe/Paris", "Europe/Podgorica", "Europe/Prague", "Europe/Riga",
			"Europe/Rome", "Europe/Samara", "Europe/San_Marino",
			"Europe/Sarajevo", "Europe/Simferopol", "Europe/Skopje",
			"Europe/Sofia", "Europe/Stockholm", "Europe/Tallinn",
			"Europe/Tirane", "Europe/Tiraspol", "Europe/Uzhgorod",
			"Europe/Vaduz", "Europe/Vatican", "Europe/Vienna",
			"Europe/Vilnius", "Europe/Volgograd", "Europe/Warsaw",
			"Europe/Zagreb", "Europe/Zaporozhye", "Europe/Zurich", "Factory",
			"GB", "GB-Eire", "GMT", "GMT+0", "GMT-0", "GMT0", "Greenwich",
			"HST", "Hongkong", "Iceland", "Indian/Antananarivo",
			"Indian/Chagos", "Indian/Christmas", "Indian/Cocos",
			"Indian/Comoro", "Indian/Kerguelen", "Indian/Mahe",
			"Indian/Maldives", "Indian/Mauritius", "Indian/Mayotte",
			"Indian/Reunion", "Iran", "Israel", "Jamaica", "Japan",
			"Kwajalein", "Libya", "MET", "MST", "MST7MDT", "Mexico/BajaNorte",
			"Mexico/BajaSur", "Mexico/General", "NZ", "NZ-CHAT", "Navajo",
			"PRC", "PST8PDT", "Pacific/Apia", "Pacific/Auckland",
			"Pacific/Chatham", "Pacific/Easter", "Pacific/Efate",
			"Pacific/Enderbury", "Pacific/Fakaofo", "Pacific/Fiji",
			"Pacific/Funafti", "Pacific/Galapagos", "Pacific/Gambier",
			"Pacific/Guadalcanal", "Pacific/Guam", "Pacific/Honolulu",
			"Pacific/Johnston", "Pacific/Kiritimati", "Pacific/Kosrae",
			"Pacific/Kwajalein", "Pacific/Majuro", "Pacific/Marquesas",
			"Pacific/Midway", "Pacific/Nauru", "Pacific/Niue",
			"Pacific/Norfolk", "Pacific/Noumea", "Pacific/Pago_Pago",
			"Pacific/Palau", "Pacific/Pitcairn", "Pacific/Ponape",
			"Pacific/Port_Moresby", "Pacific/Rarotonga", "Pacific/Saipan",
			"Pacific/Samoa", "Pacific/Tahiti", "Pacific/Tarawa",
			"Pacific/Tongatapu", "Pacific/Truk", "Pacific/Wake",
			"Pacific/Wallis", "Pacific/Yap", "Poland", "Portugal", "ROC",
			"ROK", "Singapore", "Turkey", "UCT", "US/Alaska", "US/Aleutian",
			"US/Arizona", "US/Central", "US/East-Indiana", "US/Eastern",
			"US/Hawaii", "US/Indiana-Starke", "US/Michigan", "US/Mountain",
			"US/Pacific", "US/Pacific-New", "US/Samoa", "UTC", "Universal",
			"W-SU", "WET", "Zulu" };
	// Flags corresponding to country Names
	int[] images = {
			R.drawable.flag_ci,
			R.drawable.flag_gh,
			R.drawable.flag_et,
			R.drawable.flag_dz,
			R.drawable.flag_er,
			R.drawable.flag_er,
			R.drawable.flag_ml,
			R.drawable.flag_cf,
			R.drawable.flag_gm,
			R.drawable.flag_gw,
			R.drawable.flag_mw,
			R.drawable.flag_cg,
			R.drawable.flag_bi,
			R.drawable.flag_eg,
			R.drawable.flag_ma,
			R.drawable.flag_es,
			R.drawable.flag_gn,
			R.drawable.flag_sn,
			R.drawable.flag_tz,
			R.drawable.flag_dj,
			R.drawable.flag_cm,
			R.drawable.flag_eh,
			R.drawable.flag_sl,
			R.drawable.flag_bw,
			R.drawable.flag_zw,
			R.drawable.flag_za,
			R.drawable.flag_ug,
			R.drawable.flag_sd,
			R.drawable.flag_rw,
			R.drawable.flag_cd,
			R.drawable.flag_ng,
			R.drawable.flag_ga,
			R.drawable.flag_tg,
			R.drawable.flag_ao,
			R.drawable.flag_cd,
			R.drawable.flag_zm,
			R.drawable.flag_gq,
			R.drawable.flag_na,
			R.drawable.flag_ls,
			R.drawable.flag_sz,
			R.drawable.flag_so,
			R.drawable.flag_lr,
			R.drawable.flag_ke,
			R.drawable.flag_td,
			R.drawable.flag_ne,
			R.drawable.flag_mr,
			R.drawable.flag_bf,
			R.drawable.flag_bj,
			R.drawable.flag_st,
			R.drawable.flag_ml,
			R.drawable.flag_ly,
			R.drawable.flag_tn,
			R.drawable.flag_na,
			// America
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_al,
			R.drawable.flag_ag,
			R.drawable.flag_br,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_ar,
			R.drawable.flag_rw,
			R.drawable.flag_py,
			R.drawable.flag_ca,
			R.drawable.flag_us,
			R.drawable.flag_br,
			R.drawable.flag_mx,
			R.drawable.flag_bb,
			R.drawable.flag_br,
			R.drawable.flag_bz,
			R.drawable.flag_ca,
			R.drawable.flag_br,
			R.drawable.flag_co,
			R.drawable.flag_us,
			R.drawable.flag_ar,
			R.drawable.flag_ca,
			R.drawable.flag_br,
			R.drawable.flag_my,
			R.drawable.flag_vc,
			R.drawable.flag_ar,
			R.drawable.flag_gf,
			R.drawable.flag_ky,
			R.drawable.flag_us,
			R.drawable.flag_mx,
			R.drawable.flag_ca,
			R.drawable.flag_ar,
			R.drawable.flag_cr,
			// R.drawable.flag_ca,
			R.drawable.flag_br,
			R.drawable.flag_cw,
			R.drawable.flag_gl,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_dm,
			R.drawable.flag_ca,
			R.drawable.flag_br,
			R.drawable.flag_sv,
			R.drawable.flag_mx,
			R.drawable.flag_us,
			R.drawable.flag_br,
			R.drawable.flag_ca,
			R.drawable.flag_gl,
			R.drawable.flag_ca,
			R.drawable.flag_tc,
			R.drawable.flag_gd,
			R.drawable.flag_gp,
			R.drawable.flag_gt,
			R.drawable.flag_ec,
			R.drawable.flag_gs,
			R.drawable.flag_ca,
			R.drawable.flag_cu,
			R.drawable.flag_mx,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_jm,
			R.drawable.flag_ar,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_bq,
			R.drawable.flag_bo,
			R.drawable.flag_pe,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_sx,
			R.drawable.flag_br,
			R.drawable.flag_ni,
			R.drawable.flag_br,
			R.drawable.flag_mf,
			R.drawable.flag_mq,
			R.drawable.flag_mx,
			R.drawable.flag_mx,
			R.drawable.flag_ar,
			R.drawable.flag_us,
			R.drawable.flag_mx,
			R.drawable.flag_us,
			R.drawable.flag_mx,
			R.drawable.flag_pm,
			R.drawable.flag_ca,
			R.drawable.flag_mx,
			R.drawable.flag_us,
			R.drawable.flag_ca,
			R.drawable.flag_ms,
			R.drawable.flag_bs,
			R.drawable.flag_us,
			R.drawable.flag_ca,
			R.drawable.flag_us,
			R.drawable.flag_br,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_mx,
			R.drawable.flag_pa,
			R.drawable.flag_ca,
			R.drawable.flag_sr,
			R.drawable.flag_us,
			R.drawable.flag_ht,
			R.drawable.flag_tt,
			R.drawable.flag_pr,
			R.drawable.flag_br,
			R.drawable.flag_pr,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_br,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_br,
			R.drawable.flag_ar,
			R.drawable.flag_mx,
			R.drawable.flag_br,
			R.drawable.flag_cl,
			R.drawable.flag_do,
			R.drawable.flag_br,
			R.drawable.flag_gl,
			R.drawable.flag_us,
			R.drawable.flag_us,
			R.drawable.flag_bl,
			R.drawable.flag_ca,
			R.drawable.flag_kn,
			R.drawable.flag_lc,
			R.drawable.flag_vi,
			R.drawable.flag_vc,
			R.drawable.flag_ca,
			R.drawable.flag_hn,
			R.drawable.flag_gl,
			R.drawable.flag_ca,
			R.drawable.flag_mx,
			R.drawable.flag_ca,
			R.drawable.flag_vg,
			R.drawable.flag_ca,
			R.drawable.flag_vi,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_us,
			R.drawable.flag_ca,

			// Antartica
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			R.drawable.flag_aq,
			// Artic
			R.drawable.flag_sj,
			// Asia
			R.drawable.flag_ye,
			R.drawable.flag_kz,
			R.drawable.flag_jo,
			R.drawable.flag_ru,
			R.drawable.flag_kz,
			R.drawable.flag_kz,
			R.drawable.flag_tm,
			R.drawable.flag_tm,
			R.drawable.flag_iq,
			R.drawable.flag_bh,
			R.drawable.flag_az,
			R.drawable.flag_th,
			R.drawable.flag_lb,
			R.drawable.flag_kg,
			R.drawable.flag_bn,
			R.drawable.flag_in,
			R.drawable.flag_mn,
			R.drawable.flag_cn,
			R.drawable.flag_cn,
			R.drawable.flag_lk,
			R.drawable.flag_bd,
			R.drawable.flag_sy,
			R.drawable.flag_bd,
			R.drawable.flag_tl,
			R.drawable.flag_ae,
			R.drawable.flag_tj,
			R.drawable.flag_ps,
			R.drawable.flag_cn,
			R.drawable.flag_vn,
			R.drawable.flag_hk,
			R.drawable.flag_mn,
			R.drawable.flag_ru,
			R.drawable.flag_tr,
			R.drawable.flag_id,
			R.drawable.flag_id,
			R.drawable.flag_il,
			R.drawable.flag_af,
			R.drawable.flag_ru,
			R.drawable.flag_pk,
			R.drawable.flag_cn,
			R.drawable.flag_np,
			R.drawable.flag_np,
			R.drawable.flag_in,
			R.drawable.flag_ru,
			R.drawable.flag_my,
			R.drawable.flag_my,
			R.drawable.flag_kw,
			R.drawable.flag_mo,
			R.drawable.flag_mo,
			R.drawable.flag_ru,
			R.drawable.flag_id,
			R.drawable.flag_ph,
			R.drawable.flag_om,
			R.drawable.flag_cy,
			R.drawable.flag_ru,
			R.drawable.flag_ru,
			R.drawable.flag_ru,
			R.drawable.flag_kz,
			R.drawable.flag_kh,
			R.drawable.flag_id,
			R.drawable.flag_kp,
			R.drawable.flag_qa,
			R.drawable.flag_kz,
			R.drawable.flag_mm,
			R.drawable.flag_sa,
			R.drawable.flag_vn,
			R.drawable.flag_ru,
			R.drawable.flag_uz,
			R.drawable.flag_kr,
			R.drawable.flag_cn,
			R.drawable.flag_sg,
			R.drawable.flag_tw,
			R.drawable.flag_uz,
			R.drawable.flag_ge,
			R.drawable.flag_ir,
			R.drawable.flag_il,
			R.drawable.flag_bt,
			R.drawable.flag_bt,
			R.drawable.flag_jp,
			R.drawable.flag_id,
			R.drawable.flag_mn,
			R.drawable.flag_mn,
			R.drawable.flag_cn,
			R.drawable.flag_la,
			R.drawable.flag_ru,
			R.drawable.flag_ru,
			R.drawable.flag_ru,
			R.drawable.flag_am,
			// Atlantic
			R.drawable.flag_pt,
			R.drawable.flag_bm,
			R.drawable.flag_es,
			R.drawable.flag_cv,
			R.drawable.flag_fo,
			R.drawable.flag_fo,
			R.drawable.flag_no,
			R.drawable.flag_pt,
			R.drawable.flag_is,
			R.drawable.flag_gs,
			R.drawable.flag_sh,
			R.drawable.flag_fk,
			// Australia
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			R.drawable.flag_au,
			// Brazil
			R.drawable.flag_br,
			R.drawable.flag_br,
			R.drawable.flag_br,
			R.drawable.flag_br,
			R.drawable.flag_xx, // CET
			R.drawable.flag_us, // cst6cdt
			// Canada
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			R.drawable.flag_ca,
			// Chile
			R.drawable.flag_cl,
			R.drawable.flag_cl,
			R.drawable.flag_cu, // Cuba
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_eg,
			R.drawable.flag_gb,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			// Europe
			R.drawable.flag_nl,
			R.drawable.flag_ad,
			R.drawable.flag_gr,
			R.drawable.flag_gb,
			R.drawable.flag_rs,
			R.drawable.flag_de,
			R.drawable.flag_sk,
			R.drawable.flag_be,
			R.drawable.flag_ro,
			R.drawable.flag_hu,
			R.drawable.flag_md,
			R.drawable.flag_dk,
			R.drawable.flag_ie,
			R.drawable.flag_gi,
			R.drawable.flag_gg,
			R.drawable.flag_fi,
			R.drawable.flag_im,
			R.drawable.flag_tr,
			R.drawable.flag_je,
			R.drawable.flag_ru,
			R.drawable.flag_ua,
			R.drawable.flag_pt,
			R.drawable.flag_si,
			R.drawable.flag_gb,
			R.drawable.flag_lu,
			R.drawable.flag_es,
			R.drawable.flag_mt,
			R.drawable.flag_ax,
			R.drawable.flag_by,
			R.drawable.flag_mc,
			R.drawable.flag_ru,
			R.drawable.flag_cy,
			R.drawable.flag_no,
			R.drawable.flag_fr,
			R.drawable.flag_me,
			R.drawable.flag_cz,
			R.drawable.flag_lv,
			R.drawable.flag_it,
			R.drawable.flag_ru,
			R.drawable.flag_sm,
			R.drawable.flag_ba,
			R.drawable.flag_ua,
			R.drawable.flag_mk,
			R.drawable.flag_bg,
			R.drawable.flag_se,
			R.drawable.flag_ee,
			R.drawable.flag_al,
			R.drawable.flag_md,
			R.drawable.flag_ua,
			R.drawable.flag_li,
			R.drawable.flag_va,
			R.drawable.flag_at,
			R.drawable.flag_lt,
			R.drawable.flag_ru,
			R.drawable.flag_pl,
			R.drawable.flag_hr,
			R.drawable.flag_ua,
			R.drawable.flag_ch,
			R.drawable.flag_xx, // Factory
			R.drawable.flag_gb,
			R.drawable.flag_gb,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_hk,
			R.drawable.flag_is,
			R.drawable.flag_mg,
			R.drawable.flag_io,
			R.drawable.flag_cx,
			R.drawable.flag_cc,
			R.drawable.flag_km,
			R.drawable.flag_tf,
			R.drawable.flag_sc,
			R.drawable.flag_mv,
			R.drawable.flag_mu,
			R.drawable.flag_yt,
			R.drawable.flag_re,
			R.drawable.flag_ir,
			R.drawable.flag_il,
			R.drawable.flag_jm,
			R.drawable.flag_jp,
			R.drawable.flag_mh,
			R.drawable.flag_ly,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			R.drawable.flag_xx,
			// Mexico
			R.drawable.flag_mx,
			R.drawable.flag_mx,
			R.drawable.flag_mx,
			R.drawable.flag_nz,
			R.drawable.flag_nz,
			R.drawable.flag_us,
			R.drawable.flag_cn,
			R.drawable.flag_us,
			// Pacific
			R.drawable.flag_ws, R.drawable.flag_nz, R.drawable.flag_nz,
			R.drawable.flag_cl, R.drawable.flag_vu, R.drawable.flag_ki,
			R.drawable.flag_tk, R.drawable.flag_fj, R.drawable.flag_tv,
			R.drawable.flag_ec, R.drawable.flag_pf, R.drawable.flag_sb,
			R.drawable.flag_gu, R.drawable.flag_us, R.drawable.flag_um,
			R.drawable.flag_ki, R.drawable.flag_fm, R.drawable.flag_mh,
			R.drawable.flag_mh, R.drawable.flag_pf, R.drawable.flag_um,
			R.drawable.flag_nr, R.drawable.flag_nu, R.drawable.flag_nf,
			R.drawable.flag_nc, R.drawable.flag_as, R.drawable.flag_pw,
			R.drawable.flag_pn, R.drawable.flag_fm, R.drawable.flag_pg,
			R.drawable.flag_ck, R.drawable.flag_mp, R.drawable.flag_as,
			R.drawable.flag_pf, R.drawable.flag_ki, R.drawable.flag_to,
			R.drawable.flag_fm, R.drawable.flag_um, R.drawable.flag_wf,
			R.drawable.flag_fm, R.drawable.flag_pl,
			R.drawable.flag_pt,
			R.drawable.flag_tw,
			R.drawable.flag_kr,
			R.drawable.flag_sg,
			R.drawable.flag_tr,
			R.drawable.flag_xx,
			// US
			R.drawable.flag_us, R.drawable.flag_us, R.drawable.flag_us,
			R.drawable.flag_us, R.drawable.flag_us, R.drawable.flag_us,
			R.drawable.flag_us, R.drawable.flag_us, R.drawable.flag_us,
			R.drawable.flag_us, R.drawable.flag_us, R.drawable.flag_us,
			R.drawable.flag_as, R.drawable.flag_xx, R.drawable.flag_xx,
			R.drawable.flag_ru, R.drawable.flag_xx, R.drawable.flag_xx };

	// method used for screen orientation and window-fullscreen settings
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(PalmCalcActivity.ctx);
		int inOrien = Integer.parseInt(sharedPrefs.getString("prefOrientation",
				"0"));
		switch (inOrien) {
		case 0:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			break;
		case 1:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case 2:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;

		}
		Boolean isScreen = sharedPrefs.getBoolean("prefScreen", false);
		if (isScreen)
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Boolean isNoti = sharedPrefs.getBoolean("prefNoti", false);
		if (isNoti) {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

	// Vibration
	public void vibrate() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(PalmCalcActivity.ctx);
		Boolean isVibe = sharedPrefs.getBoolean("prefVibe", false);
		Vibrator vibe = (Vibrator) PalmCalcActivity.ctx
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (isVibe) {
			vibe.vibrate(100);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			showUserSettings();
			super.onCreate(savedInstanceState);
			setContentView(R.layout.clocktimezonelist);

			current = new TimeZoneEntry();

			tzlist = (ListView) findViewById(R.id.tzlist);
			tzlist.setTextFilterEnabled(true);
			timezonesorig = new ArrayList<TimeZoneEntry>();
			clockList = new ArrayList<HashMap<String, String>>();
			populateListOfAllTZs();
			tzlist.setOnItemClickListener(ListsingleClick);
			for (int i = 0; i < tzids.length; i++) {
				if (!tzids[i].equals("America/Creston")) {
					TimeZoneEntry tze = new TimeZoneEntry(tzids[i]);
					timezonesorig.add(tze);
				}
			}
			tzfilter = (EditText) findViewById(R.id.tzsearch);
			tzfilter.addTextChangedListener(new FilterWatcher());
		} catch (Exception e) {
		}
	}

	// Method used for editing the database values on a list value select
	public void DB_edit() {
		db = (new SQLiteHelper(PalmCalcActivity.ctx)).getWritableDatabase();
	}

	public void DB_close() {
		db.close();
	}

	public void DB_Read() {
		db = (new SQLiteHelper(PalmCalcActivity.ctx)).getReadableDatabase();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	// Method for populating the list of all Time Zones.
	private void populateListOfAllTZs() {
		try {
			for (int i = 0; i < tzids.length; i++) {
				@SuppressWarnings("unused")
				String zonename = tzids[i];
				HashMap<String, String> clockhm = new HashMap<String, String>();
				clockhm.put("txt", tzids[i]);
				clockhm.put("flag", Integer.toString(images[i]));
				clockList.add(clockhm);
			}
			String[] from = { "flag", "txt" };
			int[] to = { R.id.clockimage, R.id.clockcontry };
			clockadapter = new SimpleAdapter(getBaseContext(), clockList,
					R.layout.clock_time_list, from, to);
			tzlist.setAdapter(clockadapter);
		} catch (Exception e) {
		}
	}

	// List click for selecting the TimeZone list to get it on
	// GlobalClockActivity_Main screen.
	OnItemClickListener ListsingleClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			try {
				vibrate();
				current = timezonesorig.get(position);
				AddTimeZone.this.DB_Read();
				if (current.alreadySaved(db)) {
					finish();
					AddTimeZone.this.DB_close();
				} else {
					String strSubstring = String.valueOf(clockList
							.get(position));
					ContentValues cv = new ContentValues();
					cv.put("tzid", strSubstring.substring(
							strSubstring.indexOf("txt=") + 4,
							strSubstring.indexOf(",")));
					cv.put("images", strSubstring.substring(
							strSubstring.indexOf("flag=") + 5,
							strSubstring.length() - 1));
					cv.put("contryname", strSubstring.substring(
							strSubstring.indexOf("txt=") + 4,
							strSubstring.indexOf(",")));
					AddTimeZone.this.DB_edit();
					db.insert("timezones", null, cv);
					AddTimeZone.this.DB_close();
					finish();
				}
			} catch (Exception e) {
			}
		}
	};

	// The class FilterWatcher is used as a Name Search option.
	// used to get TimeZone lists by search option.
	class FilterWatcher implements TextWatcher {
		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			try {
				clockList.clear();
				for (int i = 0; i < tzids.length; i++) {
					HashMap<String, String> clockhm1 = new HashMap<String, String>();
					if (tzids[i].toString().toUpperCase()
							.contains(s.toString().toUpperCase())) {
						clockhm1.put("txt", tzids[i]);
						clockhm1.put("flag", Integer.toString(images[i]));
						clockList.add(clockhm1);
					}
				}
				clockadapter.notifyDataSetChanged();
			} catch (Exception e) {
			}
		}
	}
}