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

package com.cybrosys.palmcalc;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cybrosys.bmi.bmiactivity;
import com.cybrosys.clock.GlobalClockActivity;
import com.cybrosys.scientific.ScientificActivity;
import com.cybrosys.basic.BasicActivity;
import com.cybrosys.share.ShareApp;
import com.cybrosys.tip.TipActivity;
import com.cybrosys.unit.AndroidQAActivity;
import com.cybrosys.scientific.Demoscreen;
import com.cybrosys.currency.CurrencyMain;

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

public class PalmCalcActivity extends SherlockFragmentActivity implements
		android.view.View.OnClickListener {

	Button btnClear, btnSend;
	EditText etxtFeedback;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	TextView tabCenter;
	TextView tabText;
	Dialog dialog;
	public static Context ctx;
	static ActionBar bar;
	private static final int RESULT_SETTINGS = 1;
	final String PREFS_NAME = "MyPrefsFile";
	SharedPreferences settings;

	public static int inDispheight;
	public static int inDispwidth;
	DisplayMetrics metrics;
	
	//icons 
	static int inArray[] = new int[] { R.drawable.dig_sci, R.drawable.dig_unit,
			R.drawable.dig_cur, R.drawable.dig_cal, R.drawable.dig_tip,
			R.drawable.dig_gbl, R.drawable.dig_bmi };

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = PalmCalcActivity.this;
		init();
		ctx = PalmCalcActivity.this;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		int inCur = mViewPager.getCurrentItem();
		ctx = PalmCalcActivity.this;
		init();
		mViewPager.setCurrentItem(inCur);
	}

	public void onResume() {
		super.onResume();
		ctx = PalmCalcActivity.this;
	}

	// initializing the views
	public void init() {
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);
		showUserSettings();
		setContentView(mViewPager);
		bar = getSupportActionBar();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		
		//for getting the screen size
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		inDispheight = (int) (metrics.heightPixels * .6f);
		inDispwidth = (int) (metrics.widthPixels * .8f);
		mTabsAdapter = new TabsAdapter(this, mViewPager);
		settings = getSharedPreferences(PREFS_NAME, 0);

		// first time run check
		if (settings.getBoolean("my_first_time", true)) {
			Intent intent = new Intent(PalmCalcActivity.this, Demoscreen.class);
			PalmCalcActivity.this.startActivity(intent);
			settings.edit().putBoolean("my_first_time", false).commit();
		}

		// adding the fragments to the tabs adapter class
		mTabsAdapter.addTab(bar.newTab(), ScientificActivity.class, null);
		mTabsAdapter.addTab(bar.newTab(), AndroidQAActivity.class, null);
		mTabsAdapter.addTab(bar.newTab(), CurrencyMain.class, null);
		mTabsAdapter.addTab(bar.newTab(), BasicActivity.class, null);
		mTabsAdapter.addTab(bar.newTab(), TipActivity.class, null);
		mTabsAdapter.addTab(bar.newTab(), GlobalClockActivity.class, null);
		mTabsAdapter.addTab(bar.newTab(), bmiactivity.class, null);
		mTabsAdapter.addTab(bar.newTab(), ShareApp.class, null);

		StartScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.settings, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			break;
		case R.id.feedback:
			Feedback();

			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(PalmCalcActivity.this)
				.setIcon(R.drawable.palm_icon)
				.setTitle("Confirm Exit")

				.setMessage("Do you want to Exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).setNegativeButton("No", null).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			showUserSettings();
			break;

		}

	}

	private void Feedback() {
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.feedback);
		etxtFeedback = (EditText) dialog.findViewById(R.id.etxtFeedback);
		btnClear = (Button) dialog.findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this);
		btnSend = (Button) dialog.findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
		dialog.show();
	}

	// for sending feedback through mail
	private void feedbackmail() {
		String to = "android@cybrosys.com";
		String subject = "PalmCalc FeedBack";
		String message = etxtFeedback.getText().toString();
		if (message.length() != 0) {
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
			email.putExtra(Intent.EXTRA_SUBJECT, subject);
			email.putExtra(Intent.EXTRA_TEXT, message);
			email.setType("message/rfc822");
			startActivity(Intent.createChooser(email,
					"Choose an Email client :"));
			Toast.makeText(this, "Thank you for your Valuable feedback....",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Please Give your feedback ",
					Toast.LENGTH_SHORT).show();

		}
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.btnClear:
				etxtFeedback.setText("");
				break;
			case R.id.btnSend:
				feedbackmail();
				dialog.dismiss();
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
	}

	// Work like a listener on changing preference
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int inOrien = Integer.parseInt(sharedPrefs.getString("prefOrientation",
				"0"));
		switch (inOrien) {
		case 0:
			// Orientation based on sensor state
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			break;
		case 1:
			// sets orientation to landscape independent of sensor state
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case 2:
			// sets orientation to portrait independent of sensor state
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;

		}
		Boolean isScreen = sharedPrefs.getBoolean("prefScreen", false);
		if (isScreen)
			// to keep the screen on while application is running
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

	// start-up screen show
	public void StartScreen() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int inStartup = Integer.parseInt(sharedPrefs.getString(
				"prefStartScreen", "0"));
		switch (inStartup) {
		case 0:
			mViewPager.setCurrentItem(0);
			break;
		case 1:
			mViewPager.setCurrentItem(1);
			break;
		case 2:
			mViewPager.setCurrentItem(2);
			break;
		case 3:
			mViewPager.setCurrentItem(3);
			break;
		case 4:
			mViewPager.setCurrentItem(4);
			break;
		case 5:
			mViewPager.setCurrentItem(5);
			break;
		case 6:
			mViewPager.setCurrentItem(6);
			break;

		}
	}

	// class for managing the viewpager and tabs
	public static class TabsAdapter extends FragmentStatePagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;

			}
		}

		public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());

			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);

			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);

			notifyDataSetChanged();
		}

		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			new Fragment().setRetainInstance(true);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		// To change title based on the page position
		public void onPageSelected(int position) {
			notifyDataSetChanged();
			switch (position) {
			case 0:
				bar.setTitle("Scientific Calculator");
				bar.setIcon(inArray[0]);
				break;
			case 1:
				bar.setTitle("Unit Converter");
				bar.setIcon(inArray[1]);
				break;
			case 2:
				bar.setTitle("Currency Converter");
				bar.setIcon(inArray[2]);
				break;
			case 3:
				bar.setTitle("Basic Calculator");
				bar.setIcon(inArray[3]);
				break;
			case 4:
				bar.setTitle("Tip Calculator");
				bar.setIcon(inArray[4]);
				break;
			case 5:
				bar.setTitle("Global Clock");
				bar.setIcon(inArray[5]);
				break;
			case 6:
				bar.setTitle("BMI Calculator");
				bar.setIcon(inArray[6]);
				break;
			default:
				bar.setTitle("PalmCalc");
				bar.setIcon(R.drawable.palm_icon);
				break;
			}

		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();

			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);

				}
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}
}