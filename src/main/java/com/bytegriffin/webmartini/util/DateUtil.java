package com.bytegriffin.webmartini.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	public static final String yyyyMMdd = "yyyy-MM-dd";
	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String HHmmss = "HH:mm:ss";
	
	public static String[] getSearchDateRange(String str) throws Exception{
		String[] searchDateRange = new String[2];
		if(str.indexOf(" - ") == -1){
			throw new Exception("输入的时间范围格式出错。");
		} else {
			searchDateRange[0] = str.split(" - ")[0];
			searchDateRange[1] = str.split(" - ")[1];
		}
		return searchDateRange;
	}
	
	public static String getCurrentDay() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMdd);
		return format.format(date);
	}

	public static String getCurrentDayTime() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		return format.format(date);
	}
	
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(HHmmss);
		return format.format(date);
	}
	

	public static Date strToDate(String date) {
		if(StringHelper.isNullOrBlank(date)){
			return null;
		}
		return strToDate(date, yyyyMMddHHmmss);
	}

	public static Date strToDate(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dateToStr(Date date) {
		if(date == null){
			return null;
		}
		return dateToStr(date, yyyyMMddHHmmss);
	}
	
	public static String formatDate(String date) {
		if(date == null){
			return null;
		}
		return dateToStr(strToDate(date, "yyyyMMdd"),yyyyMMdd);
	}

	public static String dateToStr(Date date, String formatstr) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		return format.format(date);
	}

	public static void main(String... args) {
		String str = "04-12";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		str = year + "-" + str;
		System.out.println("=====" + getCurrentDayTime());
	}
}
