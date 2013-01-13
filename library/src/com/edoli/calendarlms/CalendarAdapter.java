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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter{
	private Context mContext;
	private DateTime mDate;
	private GridView mGridView;
	
	private int mMonth;
	
	private CalendarEvent[] mCalendarEvents;
	
	public CalendarAdapter(Context context, DateTime date, GridView gridView) {
		mContext = context;
		mDate = date;
		mGridView = gridView;
		
		int firstDayOfWeek = mDate.get(DateTimeFieldType.dayOfWeek());
		mMonth = mDate.getMonthOfYear();
		mDate = mDate.minusDays(firstDayOfWeek);
	}

	@Override
	public int getCount() {
		return 7 * 6;
	}

	@Override
	public Object getItem(int position) {
		return mDate.plusDays(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public boolean isEnabled(int position) {
		DateTime date = mDate.plusDays(position);
		if (date.getMonthOfYear() != mMonth) {
			return false;
		} 
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		RelativeLayout view = new RelativeLayout(context);
				
		DateTime date = mDate.plusDays(position);
		AbsListView.LayoutParams layoutParams = null;
		
		int gHeight = mGridView.getMeasuredHeight();
		int cHeight = gHeight / 6;
		int gWidth = mGridView.getMeasuredWidth();
		int cWidth = gWidth / 7;
		int row = position / 7;
		int column = position % 7;

		layoutParams = new AbsListView.LayoutParams(cWidth, cHeight);
		
		if (column == 6) {
			layoutParams.width = gWidth - cWidth * 6;
		} 
		if (row == 5) {
			layoutParams.height = gHeight - cHeight * 5;
		}
		
		view.setLayoutParams(layoutParams);
		
		int day = date.getDayOfMonth();
		
		TextView dayView = new TextView(context);
		RelativeLayout.LayoutParams dayParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		dayParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		dayView.setLayoutParams(dayParams);
		dayView.setText(day + "");
		dayView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		
		if (column == 0) {
			dayView.setTextColor(Color.RED);
		}

		// Set Background of cell

		int fillColor = 0;
		int strokeColor = 0;
		
		if (date.getMonthOfYear() != mMonth) {
			fillColor = Color.argb(51, 0, 0 ,0);
			strokeColor = Color.GRAY;
			dayView.setTextColor(Color.GRAY);
		} else {
			fillColor = Color.argb(51, 238, 238, 230);
			strokeColor = Color.GRAY;
		}
		
		// Set different color or event cell
		if (mCalendarEvents != null) {
			for (CalendarEvent event: mCalendarEvents) {
				DateTime eventDate = event.getDate();
				if (eventDate.getDayOfYear() == date.getDayOfYear()) {
					fillColor = Color.argb(51, 205, 92 , 92);
				}
			}
		}
		
		ShapeDrawable drawable = new CellShapeDrawable(fillColor, strokeColor, row, column);
		view.setBackgroundDrawable(drawable);
		
		view.addView(dayView);
		
		return view;
	}

	public void setCalendarEvents(CalendarEvent[] calendarEvents) {
		mCalendarEvents = calendarEvents;
	}
	
	private class CellShapeDrawable extends ShapeDrawable {
		private final int fill, stroke, row, column;
		
		public CellShapeDrawable(int fill, int stroke, int row, int column) {
			super(new RectShape());
			this.fill = fill;
			this.stroke = stroke;
			this.row = row;
			this.column = column;
		}
		
		@Override
		protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
			paint.setColor(this.fill);
			canvas.drawRect(canvas.getClipBounds(), paint);
			
			// border
			paint.setColor(this.stroke);
			
			// left border
			if (column != 0) {
				canvas.drawLine(0, 0, 0, canvas.getClipBounds().bottom, paint);
			}
			
			// right border
			//canvas.drawLine(canvas.getClipBounds().right - 1, 0, canvas.getClipBounds().right - 1, canvas.getClipBounds().bottom, paint);
			
			// top border
			if (row != 0) {
				canvas.drawLine(0, 0, canvas.getClipBounds().right, 0, paint);
			}
			
			// bottom border
			//canvas.drawLine(0, canvas.getClipBounds().bottom - 1, canvas.getClipBounds().right, canvas.getClipBounds().bottom - 1, paint);
		}
		
	}
}
