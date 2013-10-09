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

package com.cybrosys.basic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;
import com.cybrosys.basic.BasicActivity;

/**
 * Button with click-animation effect.
 */
class ColorButton extends Button implements OnClickListener {
	int CLICK_FEEDBACK_COLOR;
	static final int CLICK_FEEDBACK_INTERVAL = 10;
	static final int CLICK_FEEDBACK_DURATION = 350;

	float mTextX;
	float mTextY;
	long mAnimStart;
	OnClickListener mListener;
	Paint mFeedbackPaint;

	public ColorButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			PalmCalcActivity calc = (PalmCalcActivity) context;
			init(calc);
			mListener = BasicActivity.mListener;
			setOnClickListener(this);
		}
	}

	public void onClick(View view) {
		mListener.onClick(this);
	}

	private void init(PalmCalcActivity calc) {
		Resources res = getResources();

		CLICK_FEEDBACK_COLOR = res.getColor(R.color.magic_flame);
		mFeedbackPaint = new Paint();
		mFeedbackPaint.setStyle(Style.STROKE);
		mFeedbackPaint.setStrokeWidth(2);
		getPaint().setColor(res.getColor(R.color.button_text));

		mAnimStart = -1;
	}

	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH) {
		measureText();
	}

	private void measureText() {
		Paint paint = getPaint();
		mTextX = (getWidth() - paint.measureText(getText().toString())) / 2;
		mTextY = (getHeight() - paint.ascent() - paint.descent()) / 2;
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int before,
			int after) {
		measureText();
	}

	private void drawMagicFlame(int duration, Canvas canvas) {
		int alpha = 255 - 255 * duration / CLICK_FEEDBACK_DURATION;
		int color = CLICK_FEEDBACK_COLOR | (alpha << 24);

		mFeedbackPaint.setColor(color);
		canvas.drawRect(1, 1, getWidth() - 1, getHeight() - 1, mFeedbackPaint);
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mAnimStart != -1) {
			int animDuration = (int) (System.currentTimeMillis() - mAnimStart);

			if (animDuration >= CLICK_FEEDBACK_DURATION) {
				mAnimStart = -1;
			} else {
				drawMagicFlame(animDuration, canvas);
				postInvalidateDelayed(CLICK_FEEDBACK_INTERVAL);
			}
		} else if (isPressed()) {
			drawMagicFlame(0, canvas);
		}

		CharSequence text = getText();
		canvas.drawText(text, 0, text.length(), mTextX, mTextY, getPaint());
	}

	public void animateClickFeedback() {
		mAnimStart = System.currentTimeMillis();
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			animateClickFeedback();
			break;
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_CANCEL:
			invalidate();
			break;
		}

		return result;
	}
}
