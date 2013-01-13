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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.GridView;

/**
 * @author Daniel
 * GridView for showing days of week.
 */
public class DayOfWeekGridView extends GridView{
	private DayOfWeekAdapter mAdapter;

	public DayOfWeekGridView(Context context) {
		super(context);
		
		mAdapter = new DayOfWeekAdapter(context);
		
		setNumColumns(7);
		setAdapter(mAdapter);
		setBackgroundColor(Color.DKGRAY); // Default background color
	}
	
	/**
	 * Sets the text color at certain position
	 * @param position day of week (
	 * 0: sunday, 1: monday, 2: tuesday,
	 * 3: wednesday, 3: thursday, 4: friday,
	 * 5: saturday)
	 * @param color color of the text
	 */
	public void setTextColor(int position, int color) {
		mAdapter.setTextColor(position, color);
	}

	/**
	 * Sets the text color to a given color list
	 * @param colors colors of days [sun, mon, tue, wed, thu, fri, sat]
	 */
	public void setTextColor(int[] colors) {
		mAdapter.setTextColor(colors);
	}

	/**
	 * Set the background to a given Drawable, or remove the background.
	 * @param background The Drawable to use as the background, or null to remove the background
	 */
	public void setCellBackgroundDrawable(Drawable background) {
		mAdapter.setBackgroundDrawable(background);
	}

	/**
	 * Sets the background color for this view.
	 * @param the color of the background
	 */
	public void setCellBackgroundColor(int color) {
		mAdapter.setBackgroundColor(color);
	}
	
	/** 
	 * Set the height of the adapter.
	 * @param height height of adapter
	 */
	public void setHeight(int height) {
		mAdapter.setHeight(height);
	}

}
