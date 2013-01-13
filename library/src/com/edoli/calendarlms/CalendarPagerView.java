/*
 * Copyright (C) 2013 Daniel Jeon
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.edoli.calendarlms;

import java.util.ArrayList;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class CalendarPagerView extends RelativeLayout{
	
	private DateTime mDate;
	private GestureDetector mGestureDetector;

	private CalendarView mCalendarView;
	private CalendarContentView mPrevCalendarView;
	private CalendarContentView mCurrentCalendarView;
	
	private OnCalendarListener mCalendarListener;
	
	private ArrayList<View> mReserveToRemove;
	
	private boolean mIsTransition;

	public CalendarPagerView(final Context context) {
		super(context);
		
		mReserveToRemove = new ArrayList<View>();
		mIsTransition = false;
		
		
		mGestureDetector = new GestureDetector(context, new CalendarGestureDetector());
	}
	
	public void setCalendarView(CalendarView calendarView) {
		mCalendarView = calendarView;
	}
	
	public void changeCalendar(boolean increase) {
		mPrevCalendarView = (CalendarContentView) getChildAt(0);
		
		Animation inAnimation = null;
		Animation outAnimation = null;
		DateTime date;
		
		int height = mPrevCalendarView.getMeasuredHeight();
				
		if (increase) {
			date = mDate.plusMonths(1);
			inAnimation = new TranslateAnimation(0, 0, height, 0);
			outAnimation = new TranslateAnimation(0, 0, 0, -height);
		} else {
			date = mDate.plusMonths(-1);
			inAnimation = new TranslateAnimation(0, 0, -height, 0);
			outAnimation = new TranslateAnimation(0, 0, 0, height);
		}

		setCurrentCalendar(date);
		
		startChangeAnimation(inAnimation, outAnimation);
	}
	
	public void changeCalendar(DateTime date) {
		Animation inAnimation = new AlphaAnimation(0, 1);
		Animation outAnimation = new AlphaAnimation(1, 0);
		setCurrentCalendar(date);
		
		startChangeAnimation(inAnimation, outAnimation);
	}

	private void startChangeAnimation(Animation inAnimation, Animation outAnimation) {
		inAnimation.setDuration(500);
		outAnimation.setDuration(500);
		inAnimation.setInterpolator(new AccelerateInterpolator());
		outAnimation.setInterpolator(new AccelerateInterpolator());
		outAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				mReserveToRemove.add(mPrevCalendarView);
				mIsTransition = false;
			}
		});
		
		mPrevCalendarView.setAnimation(outAnimation);
		mCurrentCalendarView.setAnimation(inAnimation);
		
		mIsTransition = true;
	}
	
	public void setCurrentCalendar(DateTime date) {
		mDate = date;
		mCalendarView.setDate(date);
		mCurrentCalendarView = new CalendarContentView(getContext());
		mCurrentCalendarView.setDate(date);
		mCurrentCalendarView.setCalendarListener(mCalendarListener);

		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMMMMMM yyyy");
		formatter = formatter.withLocale(Locale.ENGLISH);
		String strDate = formatter.print(date);
		
		mCalendarView.setTitle(strDate);
		mCalendarView.onCalendarChanged(date);
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(mCurrentCalendarView, params);
	}
	
	public CalendarContentView getCurrentCalendar() {
		return mCurrentCalendarView;
	}

	public void setCalendarEvents(CalendarEvent[] calendarEvents) {
		mCurrentCalendarView.setCalendarEvents(calendarEvents);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		for (View view : mReserveToRemove) {
			removeView(view);
		}
		mReserveToRemove.clear();
	}
	
	private class CalendarGestureDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			if (!mIsTransition) {
				if (velocityY > 100) {
					changeCalendar(false);
				} else if (velocityY < -100) {
					changeCalendar(true);
				}
			}
			return true;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

	public void setCalendarListener(OnCalendarListener calendarListener) {
		mCalendarListener = calendarListener;
	}
}
