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

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edoli.calendarlms.util.UnitUtils;

public class CalendarView extends RelativeLayout{
	private DateTime mDate;

	private TextView mTitle;
	private DayOfWeekGridView mDayOfWeekGrid;
	private CalendarPagerView mCalendarPager;
	
	private boolean mDisplayActionbar;
	
	private OnCalendarListener mCalendarListener;
	
	public CalendarView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		mDisplayActionbar = true;
		
		createTitle(context);
		createDayOfWeekView(context);
		createPager(context);
		setupDate();

	}
	
	public void setDisplayActionbar(boolean show) {
		if (show) {
			mDisplayActionbar = true;
		} else {
			mDisplayActionbar = false;
		}
	}
	
	public void setTitle(String title) {
		if (mDisplayActionbar) {
			if (getContext() instanceof Activity) {
				((Activity)getContext()).setTitle(title);
			}
			mTitle.setVisibility(GONE);
		} else {
			mTitle.setText(title);
		}
	}

	private void setupDate() {
		DateTime date = DateTime.now();
		date = date.withDayOfMonth(1);
		setDate(date);
		mCalendarPager.setCurrentCalendar(date);
	}

	private void createTitle(final Context context) {
		mTitle = new TextView(context);
		mTitle.setId(1);
		mTitle.setTypeface(null, Typeface.BOLD);
		mTitle.setGravity(Gravity.CENTER);
		mTitle.setBackgroundColor(Color.CYAN);
		int pad = (int) UnitUtils.convertToPixel(context, 8);
		mTitle.setPadding(0, pad, 0, pad);
		

		LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(CENTER_HORIZONTAL);
		addView(mTitle, params);
	}
	
	public void onCalendarChanged(DateTime date) {
		if (mCalendarListener != null) {
			mCalendarListener.onCalendarChanged(date);
		}
	}
	
	private void createDayOfWeekView(final Context context) {
		mDayOfWeekGrid = new DayOfWeekGridView(context);
		mDayOfWeekGrid.setId(2);
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(BELOW, mTitle.getId());
		addView(mDayOfWeekGrid, params);
	}
	
	private void createPager(final Context context) {
		mCalendarPager = new CalendarPagerView(context);
		mCalendarPager.setCalendarView(this);
		mCalendarPager.setCalendarListener(mCalendarListener);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(BELOW, mDayOfWeekGrid.getId());
		addView(mCalendarPager, params);
	}

	public void setCalendarEvents(CalendarEvent[] calendarEvents) {
		mCalendarPager.setCalendarEvents(calendarEvents);
	}
	
	public void setOnCalendarListener(OnCalendarListener listener) {
		mCalendarListener = listener;
	}

	public DateTime getDate() {
		return mDate;
	}

	public void setDate(DateTime date) {
		mDate = date;
	}
	
	public DayOfWeekGridView getDayOfWeekGridView() {
		return mDayOfWeekGrid;
	}
}
