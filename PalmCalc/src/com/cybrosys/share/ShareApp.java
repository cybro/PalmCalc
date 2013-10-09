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

package com.cybrosys.share;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

//fragment showing the share screen
public class ShareApp extends SherlockFragment {

	ImageButton btnShare, btnRateus, btnlikeus;
	TextView webAbout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.shreapp, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		webAbout = (TextView) getView().findViewById(R.id.textView1);
		webAbout.setText(Html
				.fromHtml("Thanks for downloading and using palmcalc. Hope you had a great experience and your purpose was served. Palmcalc is an open source initiative by a few youngsters of Cybrosys. We are also planning enhancement for the product in future. As it's a free product, we do not have the budget to market it in a traditional way. We require you to Support to make this a success and to make it even better in future. So please do encourage us by clicking on the Share button for sharing it with your friends, and don't forget to rate us."));

		btnRateus = (ImageButton) getView().findViewById(R.id.btnrateus);
		btnRateus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri
						.parse("market://details?id=com.cybrosys.palmcalc");
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					new AlertDialog.Builder(PalmCalcActivity.ctx)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle("PalmCalc")
							.setMessage("Play store not found!")
							.setNegativeButton("Ok", null).show();
				}

			}
		});
		btnShare = (ImageButton) getView().findViewById(R.id.btnshare);
		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareIt();
			}

			private void shareIt() {
				String message = " https://play.google.com/store/apps/details?id=com.cybrosys.palmcalc";
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("text/plain");
				share.putExtra(Intent.EXTRA_SUBJECT, "Check out this app");
				share.putExtra(Intent.EXTRA_TEXT, message);
				startActivity(Intent.createChooser(share, "share Via"));

			}
		});
		btnlikeus = (ImageButton) getView().findViewById(R.id.fblike);
		btnlikeus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myWebLink = new Intent(
						android.content.Intent.ACTION_VIEW);
				myWebLink.setData(Uri
						.parse("http://www.facebook.com/Palmcalc?fref=ts"));
				startActivity(myWebLink);
			}
		});
	}
}
