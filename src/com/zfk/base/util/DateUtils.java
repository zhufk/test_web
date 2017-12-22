package com.zfk.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	private static final long ONE_DAY_INTERVAL = 86400000L;

	private static final String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	public static final String MONTH = "yyyy-MM";

	public static final String DAY = "yyyy-MM-dd";

	public static final String MINUTE = "yyyy-MM-dd HH:mm";

	public static final String SECOND = "yyyy-MM-dd HH:mm:ss";

	public static final String MILLISECOND = "yyyy-MM-dd HH:mm:ss SSSS";

	public static final String MONTH_N = "yyyyMM";
	public static final String DAY_N = "yyyyMMdd";
	public static final String MINUTE_N = "yyyyMMddHHmm";
	public static final String SECOND_N = "yyyyMMddHHmmss";
	public static final String MILLISECOND_N = "yyyyMMddHHmmssSSSS";
	private static final byte BASE_DATE_FORMAT_LEN = 10;

	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
		}
		return null;
	}

	public static long pastDays(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t / 86400000L;
	}

	public static long pastHour(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t / 3600000L;
	}

	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t / 60000L;
	}

	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / 86400000L;
		long hour = timeMillis / 3600000L - day * 24L;
		long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
		long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
		long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L
				- s * 1000L;
		return new StringBuilder().append(day > 0L ? new StringBuilder().append(day).append(",").toString() : "")
				.append(hour).append(":").append(min).append(":").append(s).append(".").append(sss).toString();
	}

	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / 86400000.0D;
	}

	public static Date format(String stringDate) {
		try {
			if ((stringDate == null) || ("".equals(stringDate.trim()))) {
				return null;
			}
			String format = "yyyy-MM-dd";
			String format_long = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat f = null;
			if (format.length() == stringDate.length()) {
				f = new SimpleDateFormat(format);
			} else if (format_long.length() == stringDate.length()) {
				f = new SimpleDateFormat(format_long);
			}

			if (f != null) {
				return f.parse(stringDate);
			}
			return null;
		} catch (Exception e) {
			logger.error("转换日期出错", e);
		}
		return null;
	}

	public static Date getStartTimeByDay(Date day) {
		return format(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
	}

	public static Date getLastTimeByDay(Date day) {
		return format(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
	}

	public static String msInterDateString(String strDate, int intStep) throws ParseException {
		String strFormat = "yyyy-MM-dd";
		Date dtDate = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
		myFormatter.setLenient(false);
		dtDate = myFormatter.parse(strDate);

		cal.setTime(dtDate);
		cal.add(5, intStep);

		return msFormatDateTime(cal.getTime(), "yyyy-MM-dd");
	}

	public static String msFormatDateTime(Date dtmDate, String strFormat) {
		if (strFormat.equals("")) {
			strFormat = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat myFormat = new SimpleDateFormat(strFormat);

		return myFormat.format(Long.valueOf(dtmDate.getTime()));
	}

	public static String msInterMonthString(String strDate, int intStep) throws ParseException {
		String strFormat = "yyyy-MM-dd";
		Date dtDate = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
		myFormatter.setLenient(false);
		dtDate = myFormatter.parse(strDate);

		cal.setTime(dtDate);
		cal.add(2, intStep);

		return msFormatDateTime(cal.getTime(), "yyyy-MM-dd");
	}

	@Deprecated
	public static int dateDiff(Date startDate, Date endDate) {
		assert ((startDate.getTime() % 86400000L == 0L) && (endDate.getTime() % 86400000L == 0L));
		long interval = endDate.getTime() - startDate.getTime();
		return (int) (interval / 86400000L);
	}

	public static int dateDayDiff(Date startDate, Date endDate) {
		Calendar c = new GregorianCalendar();
		c.setTime(endDate);
		int endDay = c.get(6);
		c.setTime(startDate);
		int startDay = c.get(6);
		return endDay - startDay;
	}

	public static Date string2Date(String strDate, String formatStr) {
		try {
			return string2Date(strDate, formatStr, Locale.getDefault());
		} catch (ParseException e) {
		}
		return null;
	}

	private static Date string2Date(String strDate, String formatStr, Locale locale) throws ParseException {
		Date date = new SimpleDateFormat(formatStr, locale).parse(strDate);
		return date;
	}

	public static boolean isDate(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return false;
		}
		if (strDate.length() == 8)
			return isDate(octetDate2TensDate(strDate), "-");
		if (strDate.length() == 10) {
			return isDate(strDate, "-");
		}
		return false;
	}

	public static String octetDate2TensDate(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		StringBuilder sb = new StringBuilder(strDate);
		sb.insert(4, "-");
		sb.insert(7, "-");
		return sb.toString();
	}

	public static boolean isDate(String strDate, String splitChar) {
		if (strDate != null) {
			String[] arrDate = strDate.split(new StringBuilder().append("\\").append(splitChar).toString());

			if (arrDate.length != 3) {
				return false;
			}
			if ((arrDate[0].length() != 4) || (arrDate[1].length() > 2) || (arrDate[2].length() > 2)
					|| (arrDate[1].length() < 1) || (arrDate[2].length() < 1)) {
				return false;
			}
			int year = 0;
			int month = 0;
			int day = 0;

			year = NumberUtils.toInt(arrDate[0], -1);
			month = NumberUtils.toInt(arrDate[1], -1);
			day = NumberUtils.toInt(arrDate[2], -1);
			if ((year == -1) || (month == -1) || (day == -1)) {
				return false;
			}
			if ((year < 1000) || (year > 9999)) {
				return false;
			}
			if ((month < 1) || (month > 12)) {
				return false;
			}
			if (day == 0) {
				return false;
			}
			if (day > 31) {
				return false;
			}

			switch (month) {
			case 2:
				if (day > 29) {
					return false;
				}

				if ((year % 4 > 0) && (day > 28)) {
					return false;
				}
			case 4:
			case 6:
			case 9:
			case 11:
				if (day > 30)
					return false;
				break;
			}
		} else {
			return false;
		}

		return true;
	}

	public static Date msInterDate(String strDate, int intStep) throws ParseException {
		String strFormat = "yyyy-MM-dd";
		Date dtDate = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
		myFormatter.setLenient(false);
		dtDate = myFormatter.parse(strDate);

		cal.setTime(dtDate);
		cal.add(5, intStep);

		return cal.getTime();
	}

	public static String getFirstDayByMonth(int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cale = Calendar.getInstance();
		cale.add(2, month);
		cale.set(5, 1);
		String firstDay = format.format(cale.getTime());
		return firstDay;
	}

	public static String getFirstDayByMonth(Date time, int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cale = new GregorianCalendar();
		cale.setTime(time);
		cale.add(2, month);
		cale.set(5, 1);
		String firstDay = format.format(cale.getTime());
		return firstDay;
	}

	public static String getLastDayByMonth(int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cale = Calendar.getInstance();
		cale.add(2, month + 1);
		cale.set(5, 0);
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	public static String getLastDayByMonth(Date time, int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cale = new GregorianCalendar();
		cale.setTime(time);
		cale.add(2, month + 1);
		cale.set(5, 0);
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	public static int msCompareDate(String strDate1, String strDate2) throws ParseException {
		String strFormat = "yyyy-MM-dd";
		Date dtDate1 = null;
		Date dtDate2 = null;
		int intCom = 0;
		SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
		myFormatter.setLenient(false);
		dtDate1 = myFormatter.parse(strDate1);
		dtDate2 = myFormatter.parse(strDate2);

		intCom = dtDate1.compareTo(dtDate2);
		if (intCom > 0) {
			return 1;
		}
		if (intCom < 0) {
			return -1;
		}
		return 0;
	}

	public static long getUnixTimestamp(Date date) {
		return date == null ? 0L : date.getTime() / 1000L;
	}

	public static long getUnixTimestampNow() {
		Date date = new Date();
		return date.getTime() / 1000L;
	}

	public static Date getEveryFewDate(Date date, int day) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(6, day);

		return c.getTime();
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.DateUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */