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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class CalendarContentView extends RelativeLayout implements OnItemSelectedListener{

	private GridView mCalendarGrid;
	
	private OnCalendarListener mCalendarListener;
	
	public CalendarContentView(Context context) {
		super(context);
		initiation(context);
	}
	
	public CalendarContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initiation(context);
	}
	
	private void initiation(Context context) {
		createCalendarGrid(context);
		
	}

	private void createCalendarGrid(Context context) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		mCalendarGrid = new GridView(context);
		mCalendarGrid.setId(3);
		mCalendarGrid.setNumColumns(7);
		mCalendarGrid.setOnItemSelectedListener(this);
		addView(mCalendarGrid, params);
	}
	
	public void setDate(DateTime date) {
		CalendarAdapter adapter = new CalendarAdapter(getContext(), date, mCalendarGrid);
		mCalendarGrid.setAdapter(adapter);

		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] {-android.R.attr.state_enabled, -android.R.attr.state_selected}, new ColorDrawable(Color.argb(51, 65, 238, 238)));
		mCalendarGrid.setSelector(states);
	}

	public void setCalendarEvents(CalendarEvent[] calendarEvents) {
		((CalendarAdapter) mCalendarGrid.getAdapter()).setCalendarEvents(calendarEvents);
		((CalendarAdapter) mCalendarGrid.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return false;
	}

	public void setCalendarListener(OnCalendarListener calendarListener) {
		mCalendarListener = calendarListener;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (mCalendarListener != null) {
			DateTime date = (DateTime) mCalendarGrid.getAdapter().getItem(position);
			mCalendarListener.onCalendarItemSelected(parent, view, position, id, date);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		if (mCalendarListener != null) {
			mCalendarListener.onNothingSelected(parent);
		}
		
	}	
}
