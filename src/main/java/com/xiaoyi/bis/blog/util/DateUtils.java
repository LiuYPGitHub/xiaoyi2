package com.xiaoyi.bis.blog.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	private static DateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat(DateFormat.SIMPLE);
	public static SimpleDateFormat format1 = new SimpleDateFormat(DateFormat.FULL);

	/**
	 * 将Integer转化为00格式的string类型
	 * 
	 * @return
	 */
	public static String FormatConversion(Integer num) {
		Format f1 = new DecimalFormat("00");
		return f1.format(num);
	}

	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static int distanceDays(Date start, Date end) {
		return (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static int distanceHours(Date start, Date end) {
		return (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60));
	}

	public static int distanceMinute(Date start, Date end) {
		return (int) ((end.getTime() - start.getTime()) / (1000 * 60));
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return date1>date2: 1 | date1<date2: -1 | date1==date2: 0
	 */
	public static int compareDate(Date date1, Date date2) throws Exception {
		if (null == date1 || null == date2) {
			throw new Exception("date1 and date2 can not be null");
		}
		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 获得两个日期相差天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDateDifference(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return 0;
		}

		try {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();

			int year1 = 0;
			int month1 = 0;
			int day1 = 0;

			c2.setTime(endDate);
			int year2 = c2.get(Calendar.YEAR);
			int month2 = c2.get(Calendar.MONTH);
			int day2 = c2.get(Calendar.DAY_OF_MONTH);

			int pointer = -1;
			int compareResult = compareDate(beginDate, endDate);
			switch (compareResult) {
			case 0:
				return 0;
			case -1:
				do {
					pointer++;
					c1.setTime(beginDate);
					c1.add(Calendar.DAY_OF_MONTH, pointer);
					year1 = c1.get(Calendar.YEAR);
					month1 = c1.get(Calendar.MONTH);
					day1 = c1.get(Calendar.DAY_OF_MONTH);
				} while (year1 != year2 || month1 != month2 || day1 != day2);
				return pointer;
			case 1:
				return 0;
			default:
				return 0;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Date longToDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		Date date = cal.getTime();
		return date;
	}

	public static void printDate(Date date) {
		System.out.println(format1.format(date));
	}

	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal.getTime();
	}

	public static Date nextDay(Date date) {
		long time = date.getTime() + 24 * 60 * 60 * 1000;
		return longToDate(time);
	}

	/**
	 * 日期格式
	 * 
	 * @param stringDate
	 * @param dateFormat
	 * @return
	 */
	public static Long stringToDateLong(String stringDate, String dateFormat) {
		Date date = stringToDate(stringDate, dateFormat);

		if (date != null) {
			return date.getTime();
		}

		return null;

	}

	public static Date stringToDate(String stringDate, String dateFormat) {
		if (StringUtils.isEmpty(stringDate))
			return null;

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		try {
			Date date = (Date) format.parse(stringDate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * yyyy-MM-dd to date
	 * 
	 * @param stringDate
	 * @return
	 */
	public static Date stringToDate(String stringDate) {
		return stringToDate(stringDate, DateFormat.DEFAULT);
	}
	
	public static Date stringToDate2(String stringDate) {
		return stringToDate(stringDate, DateFormat.FULL);
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DateFormat.DEFAULT);
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获得下个月一号
	 * 
	 * @return
	 */
	public static Date nextMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获得下一年的一月一号
	 * 
	 * @return
	 */
	public static Date nextYearFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.YEAR, 1);
		return calendar.getTime();
	}
	
	/**
	 * 日期加一天
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(date);
	      calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
	      date = calendar.getTime();
	      return date;
	}

	/**
	 * 日期加一个月
	 * 
	 * @return
	 */
	public static Date nextMonth(Date date) {
		return nextMonths(date, 1);
	}

	/**
	 * 日期加一个月
	 * 
	 * @return
	 */
	public static Date nextMonths(Date date, Integer n) {
		if (date == null) {
			date = new Date();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.MONTH, n);
		return calendar.getTime();
	}

	/**
	 * 日期加一个年
	 * 
	 * @return
	 */
	public static Date nextYear(Date date) {
		return nextYears(date, 1);
	}

	/**
	 * 日期加一年
	 * 
	 * @return
	 */
	public static Date nextYears(Date date, Integer n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.YEAR, n);
		return calendar.getTime();
	}

	private static int getDateField(Date date, int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}

	public static int getYearsBetweenDate(Date begin, Date end) {
		int bYear = getDateField(begin, Calendar.YEAR);
		int eYear = getDateField(end, Calendar.YEAR);
		return eYear - bYear;
	}

	public static int getMonthsBetweenDate(Date begin, Date end) {
		int bMonth = getDateField(begin, Calendar.MONTH);
		int eMonth = getDateField(end, Calendar.MONTH);
		return eMonth - bMonth;
	}

	public static int getWeeksBetweenDate(Date begin, Date end) {
		int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
		int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
		return eWeek - bWeek;
	}

	public static int getDaysBetweenDate(Date begin, Date end) {

		long time = getStartDate(end).getTime() - getStartDate(begin).getTime();
		int day = (int) (time / (24 * 60 * 60 * 1000));

		return day;
	}

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date, int amount) {
		Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date, amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}

	public static Date getSpecficDateStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得时间 小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {

		if (date == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}

	public static Integer calcDateProcess(Date start, Date end) {

		if (start == null || end == null) {
			return 0;
		}

		int process = 0;
		Date today = new Date();

		int days = DateUtils.getDaysBetweenDate(start, DateUtils.stringToDate(DateUtils.dateToString(today, "yyyy-MM-dd")));
		int distance = DateUtils.getDaysBetweenDate(start, end);

		if (days <= 0) {
			process = 0;
		} else if (distance == 0) {
			process = 100;
		} else if (end.getTime() < today.getTime()) {
			process = 100;
		} else {
			double percent = (days * 1.0d) * 100 / distance;
			process = (int) percent;
		}

		return process;
	}

}
