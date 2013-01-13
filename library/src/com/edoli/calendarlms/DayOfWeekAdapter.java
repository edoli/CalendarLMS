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
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.edoli.calendarlms.util.UnitUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class DayOfWeekAdapter extends BaseAdapter{
	private static final int DEFAULT_TEXT_WEEKDAY_COLOR = Color.rgb(255, 255, 204);
	private static final int DEFAULT_TEXT_SAT_COLOR = Color.BLUE;
	private static final int DEFAULT_TEXT_SUN_COLOR = Color.RED;
	
	private Context mContext;
	private DateTime mDate;
	
	private Drawable mBackground = new ColorDrawable(Color.TRANSPARENT);
	private int mHeight;
	private int[] mTextColors = new int[] {
			DEFAULT_TEXT_SUN_COLOR,
			DEFAULT_TEXT_WEEKDAY_COLOR,
			DEFAULT_TEXT_WEEKDAY_COLOR,
			DEFAULT_TEXT_WEEKDAY_COLOR,
			DEFAULT_TEXT_WEEKDAY_COLOR,
			DEFAULT_TEXT_WEEKDAY_COLOR,
			DEFAULT_TEXT_SAT_COLOR,
	};
	
	public DayOfWeekAdapter(Context context) {
		mContext = context;
		mDate = DateTime.now();
		
		int firstDayOfWeek = mDate.get(DateTimeFieldType.dayOfWeek());
		mDate = mDate.minusDays(firstDayOfWeek);
		
		mHeight = (int) UnitUtils.convertToPixel(mContext, 18); // Default height of this adapter
	}

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		RelativeLayout view = new RelativeLayout(context);
		
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, mHeight);
		view.setLayoutParams(layoutParams);
		
		
		TextView dayView = new TextView(context);
		RelativeLayout.LayoutParams dayParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		dayParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		dayView.setLayoutParams(dayParams);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("EEE");
		dayView.setText(formatter.print(mDate.plusDays(position)));
		view.addView(dayView);
		
		// Set Color
		dayView.setTextColor(mTextColors[position]);
		view.setBackgroundDrawable(mBackground);
		
		return view;
	}
	
	/**
	 * Sets the text color at certain position.
	 * @param position day of week (
	 * 0: sunday, 1: monday, 2: tuesday,
	 * 3: wednesday, 3: thursday, 4: friday,
	 * 5: saturday)
	 * @param color color of the text
	 */
	public void setTextColor(int position, int color) {
		mTextColors[position] = color;
	}
	
	/**
	 * Set the text color to a given color list.
	 * @param colors colors of days [sun, mon, tue, wed, thu, fri, sat]
	 */
	public void setTextColor(int[] colors) {
		mTextColors = colors;
	}
	
	/**
	 * Set the background to a given Drawable, or remove the background.
	 * @param background The Drawable to use as the background, or null to remove the background
	 */
	public void setBackgroundDrawable(Drawable background) {
		mBackground = background;
	}

	/**
	 * Sets the background color for this view.
	 * @param the color of the background
	 */
	public void setBackgroundColor(int color) {
		mBackground = new ColorDrawable(color);
	}
	
	/** 
	 * Set the height of the adapter.
	 * @param height height of adapter
	 */
	public void setHeight(int height) {
		mHeight = height;
	}

}
