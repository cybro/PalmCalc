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

package com.cybrosys.scientific;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

class EventListener implements View.OnKeyListener, OnClickListener,
		View.OnLongClickListener {
	Button btnPopup, btnuniversal, btnelectro, btnAtomic, btnphysico, btnOther,
			btncloseMain, btnseletone, btnselettwo, btnseletthree,
			btnseletfour, btnseletfive, btnseletsix, btnseletseven;
	ImageButton imgbtnback, imgbtnclose;
	Dialog dlogDialog;
	PopupWindow popmWx, popmW2, popmW3, popmW4, popmW5, popmW6;

	int inP = 0;
	PopupWindow popmW1;
	Button btnHistory[] = null;
	TextView txtvHistory[] = null;
	TableRow tblrRowL[] = null;
	Button btns[] = new Button[9];
	Button btnsM[] = new Button[10];
	View vwLayout;
	TableLayout tblltTable;
	Logic mHandler;
	ViewPager mPager;
	private Symbols mSymbols = new Symbols();
	int inShift = 0, inHyp = 0;
	public static Context ctx = PalmCalcActivity.ctx;
	static SharedPreferences spMemory;
	SharedPreferences shPref;
	private static String PREFNAME = "nypref";
	static SharedPreferences.Editor editor;

	void setHandler(Logic handler, ViewPager pager) {
		mHandler = handler;
		mPager = pager;
	}

	// to clear memory
	static void clearMem() {
		PreferenceClass.setMyStringPref(ctx, "0");
		spMemory = ctx.getSharedPreferences(PREFNAME, 0);
		editor = spMemory.edit();
		editor.clear();
		editor.commit();
	}

	public void showD() {
		showcon();

	}

	// show constants selection dialog
	@SuppressWarnings("deprecation")
	public void showcon() {

		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vwLayout = inflater.inflate(R.layout.firstone,
				(ViewGroup) ((Activity) ctx).findViewById(R.id.popup_element));

		popmW1 = new PopupWindow(vwLayout, PalmCalcActivity.inDispwidth,
				PalmCalcActivity.inDispheight, true);

		popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);

		popmW1.setBackgroundDrawable(new BitmapDrawable());
		popmW1.setOutsideTouchable(true);
		imgbtnclose = (ImageButton) vwLayout.findViewById(R.id.butcancelmain);
		imgbtnclose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popmW1.dismiss();
			}
		});
		btnuniversal = (Button) vwLayout.findViewById(R.id.butuniverse);
		btnuniversal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				universal();
			}

			private void universal() {
				LayoutInflater inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View vwLayout = inflater.inflate(R.layout.universe,
						(ViewGroup) ((Activity) ctx).findViewById(R.id.unive));
				popmW2 = new PopupWindow(vwLayout,
						PalmCalcActivity.inDispwidth,
						PalmCalcActivity.inDispheight, true);
				popmW2.setBackgroundDrawable(new BitmapDrawable());
				popmW2.setOutsideTouchable(true);
				popmW1.setOutsideTouchable(true);
				popmW2.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);
				btnseletone = (Button) vwLayout.findViewById(R.id.btnunione);
				btnseletone.setText(Html
						.fromHtml("Speed of Light in Vacuum<br/><small>299,792,458m.s<sup>-1</sup></small>"));
				btnseletone.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW2.dismiss();
						popmW1.dismiss();
						mHandler.insert("299792458");
					}
				});
				btnselettwo = (Button) vwLayout.findViewById(R.id.btnunitwo);
				btnselettwo.setText(Html
						.fromHtml("Gravitational Constant<br/><small>6.67428*10<sup>-11</sup>m<sup>3</sup>.kg<sup>-1</sup>.s<sup>-2</sup></small>"));
				btnselettwo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW2.dismiss();
						popmW1.dismiss();
						mHandler.insert("6.67428e-11");
					}
				});
				btnseletthree = (Button) vwLayout
						.findViewById(R.id.btnunithree);
				btnseletthree.setText(Html
						.fromHtml("Planck Constant<br/><small>6.62606896*10<sup>-34</sup> inJ.s</small>"));
				btnseletthree.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW2.dismiss();
						popmW1.dismiss();
						mHandler.insert("6.62606896e-34");
					}
				});
				btnseletfour = (Button) vwLayout.findViewById(R.id.btnunifour);
				btnseletfour.setText(Html
						.fromHtml("Reduced Planck Constant<br/><small>1.054571628*10<sup>-34</sup> inJ.s</small>"));
				btnseletfour.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW2.dismiss();
						popmW1.dismiss();
						mHandler.insert("1.054571628e-34");
					}
				});
				imgbtnclose = (ImageButton) vwLayout
						.findViewById(R.id.btncanceluni);
				imgbtnclose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW1.dismiss();
						popmW2.dismiss();
					}
				});
				imgbtnback = (ImageButton) vwLayout
						.findViewById(R.id.btnbackuni);
				imgbtnback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW2.dismiss();
					}
				});

			}
		});
		btnelectro = (Button) vwLayout.findViewById(R.id.btnelectro);
		btnelectro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Electro();
			}

			private void Electro() {
				LayoutInflater inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View vwLayout = inflater.inflate(R.layout.electro,
						(ViewGroup) ((Activity) ctx).findViewById(R.id.elec));
				popmW3 = new PopupWindow(vwLayout,
						PalmCalcActivity.inDispwidth,
						PalmCalcActivity.inDispheight, true);
				popmW3.setBackgroundDrawable(new BitmapDrawable());
				popmW3.setOutsideTouchable(true);
				popmW1.setOutsideTouchable(true);
				popmW3.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);
				btnseletone = (Button) vwLayout.findViewById(R.id.butselectone);
				btnseletone.setText(Html
						.fromHtml("Magnetic Constant<br/><small>1.256637067*10<sup>-6</sup> N.A<sup>-2</sup></small>"));
				btnseletone.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW3.dismiss();
						popmW1.dismiss();
						mHandler.insert("1.256637061e-6");
					}
				});
				btnselettwo = (Button) vwLayout.findViewById(R.id.butselecttwo);
				btnselettwo.setText(Html
						.fromHtml("Electric Constant<br/><small>8.854187817*10<sup>-12</sup> F.m<sup>-1</sup></small>"));
				btnselettwo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW3.dismiss();
						popmW1.dismiss();
						mHandler.insert("8.854187817e-12");
					}
				});
				btnseletthree = (Button) vwLayout
						.findViewById(R.id.butselectthree);
				btnseletthree.setText(Html
						.fromHtml("Elementary Charge<br/><small>1.602176487*10<sup>-19</sup>c</small>"));
				btnseletthree.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW3.dismiss();
						popmW1.dismiss();
						mHandler.insert("1.602176487e-19");
					}
				});
				btnseletfour = (Button) vwLayout
						.findViewById(R.id.butselectfour);
				btnseletfour.setText(Html
						.fromHtml("Magnetic Flux Quantum<br/><small>2.067833667*10<sup>-15</sup>Wb</small>"));
				btnseletfour.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW3.dismiss();
						popmW1.dismiss();
						mHandler.insert("2.067833667e-15");
					}
				});
				btnseletfive = (Button) vwLayout
						.findViewById(R.id.butSelectfive);
				btnseletfive.setText(Html
						.fromHtml("Conductance Quantum<br/><small>7.7480917*10<sup>-5</sup>S</small>"));
				btnseletfive.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW3.dismiss();
						popmW1.dismiss();
						mHandler.insert("7.7480917e-5");
					}
				});
				imgbtnclose = (ImageButton) vwLayout
						.findViewById(R.id.butcancelelectro);
				imgbtnclose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW1.dismiss();
						popmW3.dismiss();
					}
				});
				imgbtnback = (ImageButton) vwLayout
						.findViewById(R.id.btnbackelectro);
				imgbtnback.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW3.dismiss();
					}
				});
			}
		});
		btnAtomic = (Button) vwLayout.findViewById(R.id.btnatomic);
		btnAtomic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atomic();
			}

			private void atomic() {
				LayoutInflater inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View vwLayout = inflater.inflate(R.layout.atomic,
						(ViewGroup) ((Activity) ctx).findViewById(R.id.atomic));
				popmW4 = new PopupWindow(vwLayout,
						PalmCalcActivity.inDispwidth,
						PalmCalcActivity.inDispheight, true);
				popmW4.setBackgroundDrawable(new BitmapDrawable());
				popmW4.setOutsideTouchable(true);
				popmW1.setOutsideTouchable(true);
				popmW4.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);
				btnseletone = (Button) vwLayout.findViewById(R.id.butatomone);
				btnseletone.setText(Html
						.fromHtml("Electron Mass<br/><small>9.10938215*10<sup>-13</sup>Kg</small>"));
				btnseletone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
						mHandler.insert("9.10938215e-31");
					}
				});
				btnselettwo = (Button) vwLayout.findViewById(R.id.butatomtwo);
				btnselettwo.setText(Html
						.fromHtml("Proton Mass<br/><small>1.672621637*10<sup>-27</sup>Kg</small>"));
				btnselettwo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
						mHandler.insert("1.672621637e-27");
					}
				});
				btnseletthree = (Button) vwLayout
						.findViewById(R.id.butatomthree);
				btnseletthree.setText(Html
						.fromHtml("Fine Structure Constant<br/><small>0.007297353</small>"));
				btnseletthree.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
						mHandler.insert("0.007297353");
					}
				});
				btnseletfour = (Button) vwLayout.findViewById(R.id.atomfour);
				btnseletfour.setText(Html
						.fromHtml("Rydberg Constant<br/><small>10,973,731.57 m<sup>-1</sup></small>"));
				btnseletfour.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
						mHandler.insert("1097373157e-1");
					}
				});
				btnseletfive = (Button) vwLayout.findViewById(R.id.butatomfive);
				btnseletfive.setText(Html
						.fromHtml("Bohr Radious<br/><small>5.291772086*10<sup>-11</sup>m</small>"));
				btnseletfive.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
						mHandler.insert("5.291772086e-11");
					}
				});
				btnseletsix = (Button) vwLayout.findViewById(R.id.butatomsix);
				btnseletsix.setText(Html
						.fromHtml("Classical Electron Radius<br/><small>2.817940289*10<sup>-15</sup>m</small>"));
				btnseletsix.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
						mHandler.insert("2.817940289e-15");
					}
				});
				imgbtnclose = (ImageButton) vwLayout
						.findViewById(R.id.butcancelatom);
				imgbtnclose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW4.dismiss();
						popmW1.dismiss();
					}
				});
				imgbtnback = (ImageButton) vwLayout
						.findViewById(R.id.btnbackatom);
				imgbtnback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW4.dismiss();
					}
				});
			}
		});
		btnphysico = (Button) vwLayout.findViewById(R.id.btnphyche);
		btnphysico.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				physico();
			}

			private void physico() {
				LayoutInflater inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View vwLayout = inflater.inflate(R.layout.physico,
						(ViewGroup) ((Activity) ctx).findViewById(R.id.physi));
				popmW5 = new PopupWindow(vwLayout,
						PalmCalcActivity.inDispwidth,
						PalmCalcActivity.inDispheight, true);
				popmW5.setBackgroundDrawable(new BitmapDrawable());
				popmW5.setOutsideTouchable(true);
				popmW1.setOutsideTouchable(true);
				popmW5.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);

				btnseletone = (Button) vwLayout.findViewById(R.id.btnphyone);
				btnseletone.setText(Html
						.fromHtml("Atomic Mass Unit<br/><small>1.660538782*10<sup>-27</sup>Kg</small>"));
				btnseletone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("1.660538782e-27");
					}
				});
				btnselettwo = (Button) vwLayout.findViewById(R.id.btnphytwo);
				btnselettwo.setText(Html
						.fromHtml("Avogadro Constant<br/><small>6.02214179*10<sup>23</sup>mol<sup>-1</sup></small>"));
				btnselettwo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("6.02214179e-23");
					}
				});
				btnseletthree = (Button) vwLayout
						.findViewById(R.id.btnphythree);
				btnseletthree.setText(Html
						.fromHtml("Faraday Constant<br/><small>96,458.3399 C.mol<sup>-1</sup></small>"));
				btnseletthree.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("964853399");
					}
				});
				btnseletfour = (Button) vwLayout.findViewById(R.id.btnphyfour);
				btnseletfour.setText(Html
						.fromHtml("Molar Gas Constant<br/><small>8.314472 J.mol<sup>-1</sup>.K<sup>-1</sup></small>"));
				btnseletfour.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("8314472");
					}
				});
				btnseletfive = (Button) vwLayout.findViewById(R.id.btnphyfive);
				btnseletfive.setText(Html
						.fromHtml("Boltzmann Constant<br/><small>1.3806504*10<sup>-23</sup>J.K<sup>-1</sup></small>"));
				btnseletfive.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("13806504e-23");
					}
				});
				btnseletsix = (Button) vwLayout.findViewById(R.id.btnphysix);
				btnseletsix.setText(Html
						.fromHtml("Stefan-Boltzmann Constant<br/><small>5.6704*10<sup>-8</sup>W.m<sup>-2</sup>.K<sup>-4</sup></small>"));
				btnseletsix.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("5.6704e-8");
					}
				});
				btnseletseven = (Button) vwLayout
						.findViewById(R.id.btnphyseven);
				btnseletseven.setText(Html
						.fromHtml("Electron Volt<br/><small>1.602176487*10<sup>-19</sup>J</small>"));
				btnseletseven.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();
						popmW1.dismiss();
						mHandler.insert("1.602176487e-19");
					}
				});

				imgbtnclose = (ImageButton) vwLayout
						.findViewById(R.id.butcancelphy);
				imgbtnclose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW1.dismiss();
						popmW5.dismiss();
					}
				});
				imgbtnback = (ImageButton) vwLayout
						.findViewById(R.id.btnbackphy);
				imgbtnback.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popmW5.dismiss();

					}
				});
			}
		});

		// other
		// option---------------------------------------------------------------------------
		btnOther = (Button) vwLayout.findViewById(R.id.btnother);
		btnOther.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Other();
				popmW1.dismiss();
			}

			private void Other() {

				// Toast.makeText(getApplicationContext(), "other",
				// Toast.LENGTH_SHORT).show();
				LayoutInflater inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View vwLayout = inflater.inflate(R.layout.other,
						(ViewGroup) ((Activity) ctx).findViewById(R.id.other));
				popmW6 = new PopupWindow(vwLayout,
						PalmCalcActivity.inDispwidth,
						PalmCalcActivity.inDispheight, true);
				popmW6.setBackgroundDrawable(new BitmapDrawable());
				popmW6.setOutsideTouchable(true);
				popmW1.setOutsideTouchable(true);
				popmW6.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);
				btnseletone = (Button) vwLayout.findViewById(R.id.btngravity);
				btnseletone.setText(Html
						.fromHtml("Standard Gravity<br/><small>9.80665 m.s<sup>-2</sup></small>"));
				btnseletone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW6.dismiss();
						popmW1.dismiss();
						mHandler.insert("9.80665");
					}
				});
				imgbtnclose = (ImageButton) vwLayout
						.findViewById(R.id.butcancelother);
				imgbtnclose.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						popmW6.dismiss();
						popmW1.dismiss();
					}
				});
				imgbtnback = (ImageButton) vwLayout
						.findViewById(R.id.btnbackother);
				imgbtnback.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						popmW6.dismiss();
						popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);

					}
				});
			}
		});
	}

	// set calculation mode to FSE
	public void setFSE() {
		String strDisplay = "" + 0;
		String strMode = ScientificActivity.txtvFSE.getText().toString();

		if (strMode.contains("FIX")) {
			mode(strDisplay, 1);
		} else if (strMode.contains("SCI")) {
			mode(strDisplay, 2);
		}
	}

	// To store to Memory
	@SuppressWarnings("deprecation")
	public void Memstore() {
		mHandler.onEnter();
		String strDisplay = mHandler.getDisplayText();
		if (isValidNumber(strDisplay)) {
			final String strTemp = strDisplay;
			spMemory = ctx.getSharedPreferences(PREFNAME, 0);
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vwLayout = inflater.inflate(R.layout.memory,
					(ViewGroup) ((Activity) ctx)
							.findViewById(R.id.popup_element));
			popmW1 = new PopupWindow(vwLayout, PalmCalcActivity.inDispwidth,
					PalmCalcActivity.inDispheight, true);
			popmW1.setBackgroundDrawable(new BitmapDrawable());
			popmW1.setOutsideTouchable(true);
			popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 0);
			ImageButton btnCancel = (ImageButton) vwLayout
					.findViewById(R.id.butcancelmain);
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popmW1.dismiss();

				}
			});
			btns[0] = (Button) vwLayout.findViewById(R.id.btn1);
			btns[1] = (Button) vwLayout.findViewById(R.id.btn2);
			btns[2] = (Button) vwLayout.findViewById(R.id.btn3);
			btns[3] = (Button) vwLayout.findViewById(R.id.btn4);
			btns[4] = (Button) vwLayout.findViewById(R.id.btn5);
			btns[5] = (Button) vwLayout.findViewById(R.id.btn6);
			btns[6] = (Button) vwLayout.findViewById(R.id.btn7);
			btns[7] = (Button) vwLayout.findViewById(R.id.btn8);
			btns[8] = (Button) vwLayout.findViewById(R.id.btn9);

			for (int inI = 0; inI < 9; inI++) {

				btns[inI].setText(spMemory.getString("" + inI, "0"));

				final int inJ = inI;

				btns[inJ].setText(spMemory.getString("" + inJ, ""));

				btns[inJ].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						btns[inJ].setText(strTemp);
						editor = spMemory.edit();
						editor.putString("" + inJ, btns[inJ].getText()
								.toString().equalsIgnoreCase("") ? ""
								: btns[inJ].getText().toString());
						editor.commit();
						popmW1.dismiss();
					}
				});
			}
			inShift = 0;
		} else
			Toast.makeText(ctx, "Save Failed!", Toast.LENGTH_SHORT).show();
	}

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

	// For showing the calculation history
	@SuppressWarnings("deprecation")
	public void showHistory() {
		shPref = ScientificActivity.ctx.getSharedPreferences("myHistpref", 0);
		int inSize = shPref.getInt("HistIndex", 0);
		System.out.println("" + inSize);
		String[] str = new String[inSize];
		for (int inI = 0; inI < inSize; inI++) {
			str[inI] = shPref.getString("hist" + inI, "");
			System.out.println(str[inI]);
		}
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vwLayout = inflater.inflate(R.layout.pop_history,
				(ViewGroup) ((Activity) ctx).findViewById(R.id.popup_element));

		popmW1 = new PopupWindow(vwLayout, PalmCalcActivity.inDispwidth,
				PalmCalcActivity.inDispheight, true);
		popmW1.setBackgroundDrawable(new BitmapDrawable());
		popmW1.setOutsideTouchable(true);
		popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 0);
		tblltTable = (TableLayout) vwLayout.findViewById(R.id.tablelay);
		ImageButton btnCancel = (ImageButton) vwLayout
				.findViewById(R.id.butcancelmain);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popmW1.dismiss();

			}
		});
		txtvHistory = new TextView[inSize];
		btnHistory = new Button[inSize];
		tblrRowL = new TableRow[inSize];
		TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1f);
		TableRow.LayoutParams textParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT, .1f);
		int inJ = 0, inL = inSize - 1;
		for (int inI = 0; inI < inSize; inI++) {
			if (!str[inI].equalsIgnoreCase("")) {
				btnHistory[inJ] = new Button(ctx);
				txtvHistory[inJ] = new TextView(ctx);
				txtvHistory[inJ].setText("" + (inJ + 1));
				txtvHistory[inJ].setGravity(Gravity.CENTER);
				txtvHistory[inJ].setTextColor(ScientificActivity.ctx
						.getResources().getColor(R.color.HistColor));
				txtvHistory[inJ].setLayoutParams(textParams);
				btnHistory[inJ].setText(str[inL]);
				btnHistory[inJ].setTextColor(Color.WHITE);
				btnHistory[inJ].setGravity(Gravity.LEFT);
				btnHistory[inJ].setLayoutParams(buttonParams);
				btnHistory[inJ].setBackgroundDrawable(ctx.getResources()
						.getDrawable(R.drawable.button_effect));
				tblrRowL[inJ] = new TableRow(ctx);
				tblrRowL[inJ].addView(txtvHistory[inJ]);
				tblrRowL[inJ].addView(btnHistory[inJ]);
				tblltTable.addView(tblrRowL[inJ]);
				final int inK = inJ;
				btnHistory[inK].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// etxt.setText(btns[inK].getText().toString());
						mHandler.insert(btnHistory[inK].getText().toString());
						popmW1.dismiss();
					}
				});

				inJ++;
				inL--;
			}

		}
		if (inSize == 0) {
			TextView txtvHistory = new TextView(ctx);
			txtvHistory.setLayoutParams(textParams);
			txtvHistory.setGravity(Gravity.CENTER);
			txtvHistory.setTextColor(Color.WHITE);
			txtvHistory.setText("History Empty");
			TableRow tblrRowL = new TableRow(ctx);
			tblrRowL.addView(txtvHistory);
			tblltTable.addView(tblrRowL);
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void onClick(View vwView) {
		vibrate();
		ctx = PalmCalcActivity.ctx;
		int id = vwView.getId();
		ScientificActivity.txtvShift.setText("");
		switch (id) {

		case R.id.buttonDel:
			mHandler.onDelete();
			break;
		case R.id.button3:
			if (inShift == 0)
				mHandler.insert("3");
			else {
				mHandler.insert(",");
				inShift = 0;
			}
			break;
		case R.id.buttonDot:
			if (inShift == 0)
				mHandler.insert(".");
			else {
				showD();
				inShift = 0;
			}
			break;
		case R.id.buttonAC:
			mHandler.onClear();
			break;

		case R.id.ButtonEqual:
			String strDisplayEq = mHandler.getDisplayText();
			if (!strDisplayEq.equalsIgnoreCase(""))
				mHandler.onEnter();
			break;

		case R.id.ButtonAns:
			if (inShift == 0)
				mHandler.onShow();
			else {
				showHistory();
				inShift = 0;
			}
			break;
		case R.id.buttonDeg:
			mHandler.onDegChange();
			break;
		case R.id.buttonAlt:

			if (inShift == 0) {
				String[] strMode = new String[] { "Floatpt", "FIX", "SCI" };
				int inSizeM = 3;

				LayoutInflater inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				vwLayout = inflater.inflate(R.layout.pop_history,
						(ViewGroup) ((Activity) ctx)
								.findViewById(R.id.popup_element));
				TextView txtvHeaderPop = (TextView) vwLayout
						.findViewById(R.id.txtvHeaderPop);
				txtvHeaderPop.setText("MODE");
				popmW1 = new PopupWindow(vwLayout,
						PalmCalcActivity.inDispwidth,
						PalmCalcActivity.inDispheight, true);
				popmW1.setBackgroundDrawable(new BitmapDrawable());
				popmW1.setOutsideTouchable(true);
				popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 0);
				tblltTable = (TableLayout) vwLayout.findViewById(R.id.tablelay);
				ImageButton btnCancel = (ImageButton) vwLayout
						.findViewById(R.id.butcancelmain);
				btnCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popmW1.dismiss();

					}
				});
				txtvHistory = new TextView[inSizeM];
				btnHistory = new Button[inSizeM];
				tblrRowL = new TableRow[inSizeM];
				TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.WRAP_CONTENT, 1f);
				TableRow.LayoutParams textParams = new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.WRAP_CONTENT, .1f);
				int inJ = 0;
				for (int inI = 0; inI < inSizeM; inI++) {
					btnHistory[inJ] = new Button(ctx);
					txtvHistory[inJ] = new TextView(ctx);
					txtvHistory[inJ].setText("" + (inJ + 1));
					txtvHistory[inJ].setLayoutParams(textParams);
					btnHistory[inJ].setText(strMode[inI]);
					txtvHistory[inJ].setGravity(Gravity.CENTER);
					txtvHistory[inJ].setTextColor(ScientificActivity.ctx
							.getResources().getColor(R.color.HistColor));
					btnHistory[inJ].setTextColor(Color.WHITE);
					btnHistory[inJ].setGravity(Gravity.LEFT);
					btnHistory[inJ].setLayoutParams(buttonParams);
					btnHistory[inJ].setBackgroundDrawable(ctx.getResources()
							.getDrawable(R.drawable.button_effect));
					tblrRowL[inJ] = new TableRow(ctx);
					tblrRowL[inJ].addView(txtvHistory[inJ]);
					tblrRowL[inJ].addView(btnHistory[inJ]);
					tblltTable.addView(tblrRowL[inJ]);
					final int inK = inJ;
					btnHistory[inK].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// etxt.setText(btns[inK].getText().toString());
							ScientificActivity.txtvFSE.setText(btnHistory[inK]
									.getText().toString());
							popmW1.dismiss();
						}
					});

					inJ++;

				}

			} else {
				setFSE();
				inShift = 0;
			}
			mHandler.onFSEChange();
			break;
		case R.id.buttonShift:
			if (inShift == 0) {
				inShift = 1;
				mHandler.onShiftPress();
			} else
				inShift = 0;

			break;
		case R.id.buttonHyp:
			if (inHyp == 0) {
				inHyp = 1;
				mHandler.onHypPress();
			} else {
				inHyp = 0;
				ScientificActivity.txtvHyp.setText("");
			}
			break;
		case R.id.buttonMS:
			mHandler.onEnter();
			String strDisplay = mHandler.getDisplayText();
			if (inShift == 0) {

				if (isValidNumber(strDisplay)) {

					PreferenceClass.setMyStringPref(ctx, strDisplay);
					Toast.makeText(ctx, "Memory Saved!", Toast.LENGTH_SHORT)
							.show();
					mHandler.onClear();
				} else
					Toast.makeText(ctx, "Save Failed!", Toast.LENGTH_SHORT)
							.show();

			} else {
				Memstore();
			}

			break;
		case R.id.buttonMR:
			if (inShift == 0) {
				String strPref = PreferenceClass.getMyStringPref(ctx);
				if (!strPref.equalsIgnoreCase("")) {
					mHandler.insert(strPref);
				} else
					Toast.makeText(ctx, "Empty", Toast.LENGTH_SHORT).show();
			} else {
				Memread();
				inShift = 0;

			}
			break;
		case R.id.buttonMp:
			mHandler.onEnter();
			String strDisplay2 = mHandler.getDisplayText();
			if (isValidNumber(strDisplay2)) {
				String strPref2 = PreferenceClass.getMyStringPref(ctx);
				if (!strPref2.equalsIgnoreCase("")) {
					if (inShift == 0) {
						try {
							PreferenceClass.setMyStringPref(
									ctx,
									""
											+ mSymbols.eval(strPref2 + "+"
													+ strDisplay2));
							mHandler.onClear();
						} catch (SyntaxException e) {

							e.printStackTrace();
						}
						Toast.makeText(ctx, "Value added", Toast.LENGTH_SHORT)
								.show();
					} else {
						Memplus();

						inShift = 0;
					}
				}
			}
			break;

		default:
			if (vwView instanceof Button) {
				String strText = ((Button) vwView).getTag().toString();
				if (strText.contains(",")) {
					int inInComa = strText.lastIndexOf(",");
					if (inShift == 1) {
						strText = strText.substring(inInComa + 1,
								strText.length());
						inShift = 0;
					} else {
						strText = strText.substring(0, inInComa);
					}

				}

				if (inHyp == 1) {

					if (strText.contains("sin(")) {
						strText = strText.replace("sin(", "sinh(");
					}
					if (strText.contains("cos(")) {
						strText = strText.replace("cos(", "cosh(");
					}
					if (strText.contains("tan(")) {
						strText = strText.replace("tan(", "tanh(");
					}
					ScientificActivity.txtvHyp.setText("");
					inHyp = 0;
				}
				mHandler.insert(strText);

			}
		}
	}

	// Memory plus function
	private void Memplus() {
		mHandler.onEnter();
		String strDisplay2 = mHandler.getDisplayText();
		if (isValidNumber(strDisplay2)) {
			String strPref2 = PreferenceClass.getMyStringPref(ctx);
			if (!strPref2.equalsIgnoreCase("")) {
				try {
					PreferenceClass.setMyStringPref(ctx,
							"" + mSymbols.eval(strPref2 + "-" + strDisplay2));
					mHandler.onClear();
				} catch (SyntaxException e) {

					e.printStackTrace();
				}
				Toast.makeText(ctx, "Value substracted", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	// Memory read function
	@SuppressWarnings("deprecation")
	private void Memread() {
		spMemory = ctx.getSharedPreferences(PREFNAME, 0);

		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		vwLayout = inflater.inflate(R.layout.memory,
				(ViewGroup) ((Activity) ctx).findViewById(R.id.popup_element));

		popmW1 = new PopupWindow(vwLayout, PalmCalcActivity.inDispwidth,
				PalmCalcActivity.inDispheight, true);
		popmW1.setBackgroundDrawable(new BitmapDrawable());
		popmW1.setOutsideTouchable(true);

		popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 0);
		ImageButton btnCancel = (ImageButton) vwLayout
				.findViewById(R.id.butcancelmain);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popmW1.dismiss();

			}
		});
		btns[0] = (Button) vwLayout.findViewById(R.id.btn1);
		btns[1] = (Button) vwLayout.findViewById(R.id.btn2);
		btns[2] = (Button) vwLayout.findViewById(R.id.btn3);
		btns[3] = (Button) vwLayout.findViewById(R.id.btn4);
		btns[4] = (Button) vwLayout.findViewById(R.id.btn5);
		btns[5] = (Button) vwLayout.findViewById(R.id.btn6);
		btns[6] = (Button) vwLayout.findViewById(R.id.btn7);
		btns[7] = (Button) vwLayout.findViewById(R.id.btn8);
		btns[8] = (Button) vwLayout.findViewById(R.id.btn9);
		for (int inI = 0; inI < 9; inI++) {
			btns[inI].setText(spMemory.getString("" + inI, ""));
			final int inK = inI;
			btns[inK].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mHandler.insert(btns[inK].getText().toString());
					popmW1.dismiss();
				}
			});

		}

	}

	// to handle long press
	@Override
	public boolean onLongClick(View vwView) {
		int id = vwView.getId();
		ctx = PalmCalcActivity.ctx;
		switch (id) {
		case R.id.buttonAC:
			mHandler.onClear();
			break;
		case R.id.buttonDel:
			mHandler.onDelete();
			break;
		case R.id.buttonDeg:
			mHandler.onDegChange();
			break;
		case R.id.ButtonEqual:
			String strDisplayEq = mHandler.getDisplayText();
			if (!strDisplayEq.equalsIgnoreCase(""))
				mHandler.onEnter();
			break;

		case R.id.buttonShift:
			if (inShift == 0) {
				inShift = 1;
				mHandler.onShiftPress();
			} else
				inShift = 0;

			break;
		case R.id.buttonHyp:
			if (inHyp == 0) {
				inHyp = 1;
				mHandler.onHypPress();
			} else {
				inHyp = 0;
				ScientificActivity.txtvHyp.setText("");
			}
			break;

		case R.id.buttonMp:
			Memplus();
			break;
		case R.id.buttonMR:
			Memread();
			break;
		case R.id.buttonMS:
			Memstore();
			break;
		case R.id.buttonAlt:
			setFSE();
			mHandler.onFSEChange();
			break;
		case R.id.buttonDot:
			showD();
			break;
		case R.id.ButtonAns:
			showHistory();
			break;
		case R.id.button3:
			mHandler.insert(",");
			break;
		default:
			if (vwView instanceof Button) {
				String strText = ((Button) vwView).getTag().toString();
				if (strText.contains(",")) {
					int inInComa = strText.lastIndexOf(",");
					strText = strText.substring(inInComa + 1, strText.length());

				}
				if (inHyp == 1) {
					if (strText.contains("sin(")) {
						strText = strText.replace("sin(", "sinh(");
					}
					if (strText.contains("cos(")) {
						strText = strText.replace("cos(", "cosh(");
					}
					if (strText.contains("tan(")) {
						strText = strText.replace("tan(", "tanh(");
					}
					ScientificActivity.txtvHyp.setText("");
					inHyp = 0;
				}
				mHandler.insert(strText);

			}

			break;

		}

		return true;
	}

	@Override
	public boolean onKey(View vwView, int keyCode, KeyEvent keyEvent) {
		int action = keyEvent.getAction();

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
				|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			boolean eat = mHandler
					.eatHorizontalMove(keyCode == KeyEvent.KEYCODE_DPAD_LEFT);
			return eat;
		}
		if (action == KeyEvent.ACTION_MULTIPLE
				&& keyCode == KeyEvent.KEYCODE_UNKNOWN) {
			return true;
		}
		if (keyEvent.getUnicodeChar() == '=') {
			if (action == KeyEvent.ACTION_UP) {
				mHandler.onEnter();
			}
			return true;
		}
		if (keyCode != KeyEvent.KEYCODE_DPAD_CENTER
				&& keyCode != KeyEvent.KEYCODE_DPAD_UP
				&& keyCode != KeyEvent.KEYCODE_DPAD_DOWN
				&& keyCode != KeyEvent.KEYCODE_ENTER) {
			return false;
		}
		if (action == KeyEvent.ACTION_UP) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				mHandler.onEnter();
				break;

			case KeyEvent.KEYCODE_DPAD_UP:
				mHandler.onUp();
				break;

			case KeyEvent.KEYCODE_DPAD_DOWN:
				mHandler.onDown();
				break;
			}
		}
		return true;
	}

	// format the values based on SCI mode
	public static String sci(double dblValue, int inPrecision) {
		NumberFormat formatter = new DecimalFormat();
		String strResult = null;
		switch (inPrecision) {
		case 1:
			formatter = new DecimalFormat("0E0");
			strResult = formatter.format(dblValue);
			break;
		case 2:
			formatter = new DecimalFormat("0.#E0");
			strResult = formatter.format(dblValue);
			break;
		case 3:
			formatter = new DecimalFormat("0.##E0");
			strResult = formatter.format(dblValue);
			break;
		case 4:
			formatter = new DecimalFormat("0.###E0");
			strResult = formatter.format(dblValue);
			break;
		case 5:
			formatter = new DecimalFormat("0.####E0");
			strResult = formatter.format(dblValue);
			break;
		case 6:
			formatter = new DecimalFormat("0.#####E0");
			strResult = formatter.format(dblValue);
			break;
		case 7:
			formatter = new DecimalFormat("0.######E0");
			strResult = formatter.format(dblValue);
			break;
		case 8:
			formatter = new DecimalFormat("0.#######E0");
			strResult = formatter.format(dblValue);
			break;
		case 9:
			formatter = new DecimalFormat("0.########E0");
			strResult = formatter.format(dblValue);
			break;
		case 10:
			formatter = new DecimalFormat("0.#########E0");
			strResult = formatter.format(dblValue);
			break;

		default:
			break;
		}
		return strResult;
	}

	// format the values based on FIX mode
	public static String fix(double value, int precision) {
		NumberFormat formatter = new DecimalFormat();
		String strResult = null;
		switch (precision) {
		case 1:
			formatter = new DecimalFormat("0");
			strResult = formatter.format(value);
			break;
		case 2:
			formatter = new DecimalFormat("0.#");
			strResult = formatter.format(value);
			break;
		case 3:
			formatter = new DecimalFormat("0.##");
			strResult = formatter.format(value);
			break;
		case 4:
			formatter = new DecimalFormat("0.###");
			strResult = formatter.format(value);
			break;
		case 5:
			formatter = new DecimalFormat("0.####");
			strResult = formatter.format(value);
			break;
		case 6:
			formatter = new DecimalFormat("0.#####");
			strResult = formatter.format(value);
			break;
		case 7:
			formatter = new DecimalFormat("0.######");
			strResult = formatter.format(value);
			break;
		case 8:
			formatter = new DecimalFormat("0.#######");
			strResult = formatter.format(value);
			break;
		case 9:
			formatter = new DecimalFormat("0.########");
			strResult = formatter.format(value);
			break;
		case 10:
			formatter = new DecimalFormat("0.#########");
			strResult = formatter.format(value);
			break;

		default:
			break;
		}
		return strResult;
	}

	public static String sciDisp(double dblValue, int inPrecision) {
		NumberFormat formatter = new DecimalFormat();
		String strResult = null;
		switch (inPrecision) {
		case 1:
			formatter = new DecimalFormat("0E0");
			strResult = formatter.format(dblValue);
			break;
		case 2:
			formatter = new DecimalFormat("0.0E0");
			strResult = formatter.format(dblValue);
			break;
		case 3:
			formatter = new DecimalFormat("0.00E0");
			strResult = formatter.format(dblValue);
			break;
		case 4:
			formatter = new DecimalFormat("0.000E0");
			strResult = formatter.format(dblValue);
			break;
		case 5:
			formatter = new DecimalFormat("0.0000E0");
			strResult = formatter.format(dblValue);
			break;
		case 6:
			formatter = new DecimalFormat("0.00000E0");
			strResult = formatter.format(dblValue);
			break;
		case 7:
			formatter = new DecimalFormat("0.000000E0");
			strResult = formatter.format(dblValue);
			break;
		case 8:
			formatter = new DecimalFormat("0.0000000E0");
			strResult = formatter.format(dblValue);
			break;
		case 9:
			formatter = new DecimalFormat("0.00000000E0");
			strResult = formatter.format(dblValue);
			break;
		case 10:
			formatter = new DecimalFormat("0.000000000E0");
			strResult = formatter.format(dblValue);
			break;

		default:
			break;
		}
		return strResult;
	}

	public static String fixDisp(double value, int precision) {
		NumberFormat formatter = new DecimalFormat();
		String strResult = null;
		switch (precision) {
		case 1:
			formatter = new DecimalFormat("0");
			strResult = formatter.format(value);
			break;
		case 2:
			formatter = new DecimalFormat("0.0");
			strResult = formatter.format(value);
			break;
		case 3:
			formatter = new DecimalFormat("0.00");
			strResult = formatter.format(value);
			break;
		case 4:
			formatter = new DecimalFormat("0.000");
			strResult = formatter.format(value);
			break;
		case 5:
			formatter = new DecimalFormat("0.0000");
			strResult = formatter.format(value);
			break;
		case 6:
			formatter = new DecimalFormat("0.00000");
			strResult = formatter.format(value);
			break;
		case 7:
			formatter = new DecimalFormat("0.000000");
			strResult = formatter.format(value);
			break;
		case 8:
			formatter = new DecimalFormat("0.0000000");
			strResult = formatter.format(value);
			break;
		case 9:
			formatter = new DecimalFormat("0.00000000");
			strResult = formatter.format(value);
			break;
		case 10:
			formatter = new DecimalFormat("0.000000000");
			strResult = formatter.format(value);
			break;

		default:
			break;
		}
		return strResult;
	}

	// to check the string is double value
	public static boolean isValidNumber(String strValue) {
		if (strValue.contains('\u2212' + "")) {
			strValue = strValue.replace('\u2212', '-');
		}
		final String Digits = "(\\p{Digit}+)";
		final String HexDigits = "(\\p{XDigit}+)";
		final String Exp = "[eE][+-]?" + Digits;
		final String fpRegex = ("[\\x00-\\x20]*" + "[+-]?(" + "NaN|"
				+ "Infinity|" + "((("
				+ Digits
				+ "(\\.)?("
				+ Digits
				+ "?)("
				+ Exp
				+ ")?)|"
				+ "(\\.("
				+ Digits
				+ ")("
				+ Exp
				+ ")?)|"
				+ "(("
				+ "(0[xX]"
				+ HexDigits
				+ "(\\.)?)|"
				+ "(0[xX]"
				+ HexDigits
				+ "?(\\.)"
				+ HexDigits
				+ ")" +

				")[pP][+-]?" + Digits + "))" + "[fFdD]?))" + "[\\x00-\\x20]*");
		if (Pattern.matches(fpRegex, strValue)) {
			Double.valueOf(strValue);
			return true;
		} else {
			return false;

		}
	}

	@SuppressWarnings("deprecation")
	public void mode(String strDisplay, int inMode) {

		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vwLayout = inflater.inflate(R.layout.fse,
				(ViewGroup) ((Activity) ctx).findViewById(R.id.popup_element));
		popmW1 = new PopupWindow(vwLayout, PalmCalcActivity.inDispwidth,
				PalmCalcActivity.inDispheight, true);
		popmW1.setBackgroundDrawable(new BitmapDrawable());
		popmW1.setOutsideTouchable(true);
		TextView txtvHeaderPop = (TextView) vwLayout
				.findViewById(R.id.txtvHeaderFse);
		popmW1.showAtLocation(vwLayout, Gravity.CENTER, 0, 30);
		ImageButton btnCancel = (ImageButton) vwLayout
				.findViewById(R.id.butcancelmain);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popmW1.dismiss();

			}
		});

		btnsM[0] = (Button) vwLayout.findViewById(R.id.btn1);
		btnsM[1] = (Button) vwLayout.findViewById(R.id.btn2);
		btnsM[2] = (Button) vwLayout.findViewById(R.id.btn3);
		btnsM[3] = (Button) vwLayout.findViewById(R.id.btn4);
		btnsM[4] = (Button) vwLayout.findViewById(R.id.btn5);
		btnsM[5] = (Button) vwLayout.findViewById(R.id.btn6);
		btnsM[6] = (Button) vwLayout.findViewById(R.id.btn7);
		btnsM[7] = (Button) vwLayout.findViewById(R.id.btn8);
		btnsM[8] = (Button) vwLayout.findViewById(R.id.btn9);
		btnsM[9] = (Button) vwLayout.findViewById(R.id.btn10);
		switch (inMode) {
		case 1:
			for (int inI = 1; inI <= 10; inI++) {
				btnsM[inI - 1].setText(fixDisp(Double.parseDouble(strDisplay),
						inI));
				final int inK = inI;
				btnsM[inI - 1].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						PreferenceClass.setMyIntPref(ctx, inK);
						ScientificActivity.txtvFSE.setText("FIX:" + (inK - 1));
						popmW1.dismiss();
					}
				});
			}
			break;
		case 2:
			txtvHeaderPop.setText("Significant Digits");
			for (int inI = 1; inI <= 10; inI++) {
				btnsM[inI - 1].setText(sciDisp(Double.parseDouble(strDisplay),
						inI));
				final int inK = inI;
				btnsM[inI - 1].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						inP = inK;
						ScientificActivity.txtvFSE.setText("SCI:" + inK);
						PreferenceClass.setMyIntPref(ctx, inP);
						popmW1.dismiss();
					}
				});
			}
			break;
		case 3:
			break;

		}
	}
}
