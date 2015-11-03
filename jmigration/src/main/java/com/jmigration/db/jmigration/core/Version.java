package com.jmigration.db.jmigration.core;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Version {
	int major = 0;
	int minor = 0;
	int point = 0;
	int hotfix = 0;
	int year = 0;
	int month = 0;
	int day = 0;
	int hour = 0;
	int minute = 0;
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public int getMinor() {
		return minor;
	}
	public void setMinor(int minor) {
		this.minor = minor;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getHotfix() {
		return hotfix;
	}
	public void setHotfix(int hotfix) {
		this.hotfix = hotfix;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public Version(int major, int minor, int point, int hotfix, int year, int month, int day, int hour, int minute) {
		super();

		@SuppressWarnings("unused")
		LocalDateTime versionDateTime = LocalDateTime.of(year, month, day, hour, minute, 0, 0);
		
		this.major = major;
		this.minor = minor;
		this.point = point;
		this.hotfix = hotfix;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	public long getVersion() {
		return  (major * 10000000000000000L) + (minor * 100000000000000L) + (point * 10000000000000L) + (hotfix * 1000000000000L) + (year * 100000000L) + (month * 1000000L) + (day * 10000L) + (hour * 100L) + minute;
	}
	
	public static Version getDummyVersion(int majorVersion) {
		Calendar calendar = Calendar.getInstance();
		return new Version(majorVersion,1,0,0,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
	}
	
}
