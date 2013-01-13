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

public class CalendarEvent {
	private DateTime mDate;
	private String mName;
	private String mContent;
	
	public DateTime getDate() {
		return mDate;
	}
	public void setDate(DateTime date) {
		mDate = date;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getContent() {
		return mContent;
	}
	public void setContent(String content) {
		mContent = content;
	}
}
