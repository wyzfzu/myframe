package com.myframe.core.util;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * 日期工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class DateUtils {
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME = "HH:mm:ss";
	public static final String DATE2 = "yyyyMMdd";
	public static final String DATETIME2 = "yyyyMMddHHmmss";
	public static final String TIME2 = "HHmmss";
	
	private DateUtils() {
		
	}
	
	public static long nowMillis() {
		return System.currentTimeMillis();
	}
	
	public static Date now() {
		return nowDateTime().toDate();
	}

    public static DateTime nowDateTime() {
        return DateTime.now();
    }

	public static Date yestoday() {
		return nowDateTime().plusDays(-1).toDate();
	}
	
	public static Date tomorrow() {
		return nowDateTime().plusDays(1).toDate();
	}

	public static DateTime toDateTime(Date date) {
		return new DateTime(date.getTime());
	}
	
	public static Date dateStart(Date date) {
		return toDateTime(date).withTime(0, 0, 0, 0).toDate();
	}

    public static Date dateEnd(Date date) {
        return toDateTime(date).withTime(23, 59, 59, 0).toDate();
    }

	public static Date todayStart() {
		return nowDateTime().withTime(0, 0, 0, 0).toDate();
	}
	
	public static Date todayEnd() {
		return nowDateTime().withTime(23, 59, 59, 0).toDate();
	}

    public static Date createWithStart(int year, int month, int day) {
        return nowDateTime().withTimeAtStartOfDay()
                .withDate(year, month, day).toDate();
    }

    public static Date createWithEnd(int year, int month, int day) {
        return nowDateTime().withTime(23, 59, 59, 999)
                .withDate(year, month, day).toDate();
    }

    public static String dateStart(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return date.contains(":") ? date : date + " 00:00:00";
    }

    public static String dateEnd(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        return date.contains(":") ? date : date + " 23:59:59";
    }

	/////////////////////////////////////////////////////////////////
	// 日期操作
	
	public static Date addSeconds(Date date, int seconds) {
		return toDateTime(date).plusSeconds(seconds).toDate();
	}
	
	public static Date addMinutes(Date date, int minutes) {
		return toDateTime(date).plusMinutes(minutes).toDate();
	}
	
	public static Date addHours(Date date, int hours) {
		return toDateTime(date).plusHours(hours).toDate();
	}
	
	public static Date addDays(Date date, int days) {
		return toDateTime(date).plusDays(days).toDate();
	}
	
	public static Date addWeeks(Date date, int weeks) {
		return toDateTime(date).plusWeeks(weeks).toDate();
	}
	
	public static Date addMonths(Date date, int months) {
		return toDateTime(date).plusMonths(months).toDate();
	}
	
	public static Date addYears(Date date, int years) {
		return toDateTime(date).plusYears(years).toDate();
	}
	
	public static Duration toDuration(Date date1, Date date2) {
		return new Duration(date1.getTime(), date2.getTime());
	}
	
	public static long millis(Date date1, Date date2) {
		return Math.abs(date1.getTime() - date2.getTime());
	}
	
	public static int yearsBetween(Date date1, Date date2) {
		int years = Years.yearsBetween(toDateTime(date1), toDateTime(date2)).getYears();
		return Math.abs(years);
	}
	
	public static int monthsBetween(Date date1, Date date2) {
		int months = Months.monthsBetween(toDateTime(date1), toDateTime(date2)).getMonths();
		return Math.abs(months);
	}
	
	public static int weeksBetween(Date date1, Date date2) {
		int weeks = Weeks.weeksBetween(toDateTime(date1), toDateTime(date2)).getWeeks();
		return Math.abs(weeks);
	}
	
	public static long daysBetween(Date date1, Date date2) {
		return Math.abs(toDuration(date1, date2).getStandardDays());
	}
	
	public static long hoursBetween(Date date1, Date date2) {
		return Math.abs(toDuration(date1, date2).getStandardHours());
	}
	
	public static long minutesBetween(Date date1, Date date2) {
		return Math.abs(toDuration(date1, date2).getStandardMinutes());
	}

	public static long secondsBetween(Date date1, Date date2) {
		return Math.abs(toDuration(date1, date2).getStandardSeconds());
	}
	
	/////////////////////////////////////////////////////////////////
	// 日期比较

    public static boolean isAfter(Date date1, Date date2) {
        return compareDateTime(date1, date2) > 0;
    }

    public static boolean isBefore(Date date1, Date date2) {
        return compareDateTime(date1, date2) < 0;
    }

	public static int compareDate(Date date1, Date date2) {
		return dateStart(date1).compareTo(dateStart(date2));
	}
	
	public static int compareDateTime(Date date1, Date date2) {
		return toDateTime(date1).compareTo(toDateTime(date2));
	}
	
	public static boolean isLeapYear(Date date) {
		int year = getYear(date);
		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
	}
	
	public static boolean isEqualsDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return dateStart(date1).equals(dateStart(date2));
	}
	
	public static boolean isEqualsDateTime(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return date1.equals(date2);
	}
	
	/////////////////////////////////////////////////////////////////
	// 日期转字符串
	
	public static String format(Date date, String pattern) {
		return toDateTime(date).toString(pattern);
	}

	public static String format(long timeMillis, String pattern) {
		return new DateTime(timeMillis).toString(pattern);
	}

	public static String format(String pattern) {
		if (pattern == null || "".equals(pattern)) {
			pattern = DATETIME;
		}
		return nowDateTime().toString(pattern);
	}

	public static String formatDate() {
		return formatDate(now());
	}

	public static String formatDateTime() {
		return formatDateTime(now());
	}

	public static String formatDate2() {
		return formatDate2(now());
	}

	public static String formatDateTime2() {
		return formatDateTime2(now());
	}

	public static String formatTime() {
		return formatTime(now());
	}

	public static String formatTime2() {
		return formatTime2(now());
	}

	public static String formatDate(Date date) {
		return format(date, DATE);
	}

	public static String formatDate(long timeMillis) {
		return format(timeMillis, DATE);
	}
	
	public static String formatDate2(Date date) {
		return format(date, DATE2);
	}

	public static String formatDate2(long timeMillis) {
		return format(timeMillis, DATE2);
	}

	public static String formatDateTime(Date date) {
		return format(date, DATETIME);
	}

	public static String formatDateTime(long timeMillis) {
		return format(timeMillis, DATETIME);
	}

	public static String formatDateTime2(Date date) {
		return format(date, DATETIME2);
	}

	public static String formatDateTime2(long timeMillis) {
		return format(timeMillis, DATETIME2);
	}

	public static String formatTime(Date date) {
		return format(date, TIME);
	}
	
	public static String formatTime2(Date date) {
		return format(date, TIME2);
	}
	
	/////////////////////////////////////////////////////////////////
	// 字符串转日期
	
	public static Date parse(String date, String pattern) {
		return DateTime.parse(date, DateTimeFormat.forPattern(pattern)).toDate();
	}
	
	public static Date parseDate(String date) {
		return parse(date, DATE);
	}
	
	public static Date parseDateTime(String date) {
		return parse(date, DATETIME);
	}
	
	public static Date parseDate2(String date) {
		return parse(date, DATE2);
	}
	
	public static Date parseDateTime2(String date) {
		return parse(date, DATETIME2);
	}
	
	/////////////////////////////////////////////////////////////////
	// 获取各日期的年、月、日
	
	public static int getYear(Date date) {
		return toDateTime(date).getYear();
	}
	
	public static int getMonth(Date date) {
		return toDateTime(date).getMonthOfYear();
	}
	
	public static int getDayOfMonth(Date date) {
		return toDateTime(date).getDayOfMonth();
	}
	
	public static int getDayOfWeek(Date date) {
		return toDateTime(date).getDayOfWeek();
	}
	
	public static int getDayOfYear(Date date) {
		return toDateTime(date).getDayOfYear();
	}
	
	public static int getHour(Date date) {
		return toDateTime(date).getHourOfDay();
	}
	
	public static int getMinute(Date date) {
		return toDateTime(date).getMinuteOfHour();
	}
	
	public static int getSecond(Date date) {
		return toDateTime(date).getSecondOfMinute();
	}

    ///////////////////////////////////////////////////////////////
    // 获取该月的第一天
    public static Date getFirstDayOfMonth(Date date) {
        return toDateTime(date).dayOfMonth().setCopy(1).toDate();
    }
    // 获取该月的最后一天
    public static Date getLastDayOfMonth(Date date) {
        return toDateTime(date).dayOfMonth().withMaximumValue().toDate();
    }

}
