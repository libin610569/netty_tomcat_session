package com.lb.mysession.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author libin
 * 时间操作类
 */

public class DateUtil {

	private static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_5 = "yyyy.MM.dd HH:mm";
	private static final String FORMAT_11 = "yyyy-MM-dd HH";
	private static final String FORMAT_13 = "yyyy-MM-dd HH";
	private static final String FORMAT_12 = "yyyyMMddHH";
	private static final String FORMAT_6 = "HH:mm";
	private static final String FORMAT_1 = "yyyy-MM-dd";

	private static final String FORMAT_10 = "yyyy-MM";
	private static final String FORMAT_8 = "yyyyMMdd";
	private static final String FORMAT_9 = "yyyyMMddHHmmss";
	private static final String FORMAT_7 = "yyyyMMdd";
	private static final String FORMAT_2 = "HH:mm:ss";

	private static final String FORMAT_3 = "yyyyMMdd";
	private static final String FORMAT_4 = "MM-dd HH:mm";
	private static final String FORMAT_15 = "MM-dd HH";

	//

	public static String getOldTaskActityId() {

		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.add(Calendar.HOUR, -1);// 取当前日期的前一天.

		// cal.add(Calendar.DAY_OF_MONTH, +1);//取当前日期的后一天.

		// 通过格式化输出日期
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				FORMAT_12);

		// System.out.println("Today is:"+format.format(Calendar.getInstance().getTime()));

		// System.out.println("yesterday is:"+format.format(cal.getTime()));
		return format.format(cal.getTime());
	}

	public static String getDiskDate(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_12);

		if (date.length > 0) {
			return simple.format(date[0]);
		}

		return simple.format(new Date());
	}

	public static String getOldTaskActityId2() {

		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.add(Calendar.HOUR, -1);// 取当前日期的前一天.

		// cal.add(Calendar.DAY_OF_MONTH, +1);//取当前日期的后一天.

		// 通过格式化输出日期
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				FORMAT_13);

		// System.out.println("Today is:"+format.format(Calendar.getInstance().getTime()));

		// System.out.println("yesterday is:"+format.format(cal.getTime()));
		return format.format(cal.getTime());
	}

	public static String getTaskActityId(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_12);

		if (date.length > 0) {
			return simple.format(date[0]);
		}

		return simple.format(new Date());
	}

	public static String toDate15(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_15);

		if (date.length > 0) {
			return simple.format(date[0]);
		}

		return simple.format(new Date());
	}

	public static String toQqb(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_11);

		if (date.length > 0) {
			return simple.format(date[0]);
		}

		return simple.format(new Date());
	}

	public static String getMondthDay(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	public static int getWeekDay() {
		// 鏄熸湡鏃� 4 4 6 6
		int d = new Date().getDay();
		if (d == 0) {
			d = 7;
		}
		return d;
	}

	public static String getDateComment(Date date) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(FORMAT_0);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			Date endTime = new Date();
			diff = endTime.getTime() - date.getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			if (day != 0) {
				return day + "天前";
			} else if (hour != 0) {
				return hour + "小时前";
			} else if (min != 0) {
				return min + "分钟前";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "刚刚";
	}

	public static String toFullByQuiz(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_8);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 023 如果参数长度不为�?,则取第一个参数进行格式化�?br> 024 否则取当前日期时间，精确到秒 �?2010-4-15 9:36:38
	 * 025
	 * 
	 * 026
	 * 
	 * @param Date
	 *            027 ... 可变参数 028
	 * @return java.lang.String 029
	 * **/
	public static String toFullBy6(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_6);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 023 如果参数长度不为�?,则取第一个参数进行格式化�?br> 024 否则取当前日期时间，精确到秒 �?2010-4-15 9:36:38
	 * 025
	 * 
	 * 026
	 * 
	 * @param Date
	 *            027 ... 可变参数 028
	 * @return java.lang.String 029
	 * **/
	public static String toFullBy7(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_7);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	public static String toFullBy9(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_9);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	public static String toFullBy5(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_5);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 023 如果参数长度不为�?,则取第一个参数进行格式化�?br> 024 否则取当前日期时间，精确到秒 �?2010-4-15 9:36:38
	 * 025
	 * 
	 * 026
	 * 
	 * @param Date
	 *            027 ... 可变参数 028
	 * @return java.lang.String 029
	 * **/

	public static String toId(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_3);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 023 如果参数长度不为�?,则取第一个参数进行格式化�?br> 024 否则取当前日期时间，精确到秒 �?2010-4-15 9:36:38
	 * 025
	 * 
	 * 026
	 * 
	 * @param Date
	 *            027 ... 可变参数 028
	 * @return java.lang.String 029
	 * **/

	public static String toFull(Date... date) {
		try {
			if (date == null) {
				return "";
			}
			SimpleDateFormat simple = new SimpleDateFormat(FORMAT_0);

			if (date.length > 0) {
				return simple.format(date[0]);

			}

			return simple.format(new Date());
		} catch (Exception exx) {
			exx.printStackTrace();
		}

		return null;

	}

	public static String toFull2(Date... date) {

		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_4);

		if (date.length > 0) {
			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 039 如果参数长度不为�?,则取第一个参数进行格式化�?br> 040 否则取当前日�?�?2010-4-15 041
	 * 
	 * 042
	 * 
	 * @param Date
	 *            043 ... 可变参数 044
	 * @return java.lang.String 045
	 * **/

	public static String toDate(Date... date) {

		if (date.length > 0) {
			if (date[0] == null) {
				return "";
			}
			SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);
			return simple.format(date[0]);

		}
		return "";

	}

	/**
	 * 055 如果参数长度不为�?,则取第一个参数进行格式化�?br> 056 否则取当前日期时间，精确到秒<br>
	 * 057 �?9:36:38 058
	 * 
	 * 059
	 * 
	 * @param Date
	 *            060 ... 可变参数 061
	 * @return java.lang.String 062
	 * **/

	public static String toTime(Date... date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_2);

		if (date.length > 0) {

			return simple.format(date[0]);

		}

		return simple.format(new Date());

	}

	/**
	 * 072 根据字符串格式去转换相应格式的日期和时间 073
	 * 
	 * 074
	 * 
	 * @param java
	 *            .lang.String 必要参数 075
	 * @return java.util.Date 076
	 * @exception java.text.ParseException
	 *                077 如果参数格式不正确会抛出此异�?078
	 * **/

	public static Date reverse2Date(String date) {
		try {
			if (date == null || "".equals(date) || date.trim().length() < 10
					|| date.indexOf("null") != -1) {
				return null;
			}
			SimpleDateFormat simple = null;
			switch (date.trim().length()) {
			case 19:// 日期+时间
				simple = new SimpleDateFormat(FORMAT_0);
				break;
			case 10:// 仅日�?

				simple = new SimpleDateFormat(FORMAT_1);

				break;

			case 8:// 仅时�?

				simple = new SimpleDateFormat(FORMAT_2);

				break;

			default:
				break;
			}

			return simple.parse(date);

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}

	}

	public static Date reverse2Date2(String date) {
		try {
			if (date == null || "".equals(date) || date.trim().length() < 10
					|| date.indexOf("null") != -1) {
				return null;
			}
			SimpleDateFormat simple = null;

			simple = new SimpleDateFormat(FORMAT_0);

			return simple.parse(date);

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}

	}

	public static Date checkDate(String str) {
		Date d = null;
		String regex = "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0[1-9]|1[0-2])([-/.]?)(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])([-/.]?)(?:29|30)|(?:0[13578]|1[02])([-/.]?)31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
		/*
		 * 验证标准�? 1. 年四位，3089-12-19 2. �?01-12 3. �?01-31，闰�?月为01-29，平�?月为01-28
		 * 4. 年月日之间只能可以是 - 5. 日期与时间中间只能有�?��空格 6. 时：00-23 7. 分：00-59 8. 秒：00-59
		 */
		boolean b = str.matches(regex);
		if (b) {
			d = DateUtil.reverse2Date(str);
		} else {
			d = null;
		}
		return d;

	}

	/**
	 * 102 将带有时、分、秒格式的日期转化为00:00:00<br>
	 * 103 方便日期推算,格式化后的是yyyy-MM-dd 00:00:00 104
	 * 
	 * @param java
	 *            .util.Date... date的长度可以为0,即不用给参数 105
	 * @return java.util.Date 106
	 * **/

	public static Date startOfADay(Date... date) {

		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);

		Date date_ = date.length == 0 ? new Date() : date[0];// 如果date为null则取当前时间

		String d = simple.format(date_);

		try {

			return simple.parse(d);

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 102 将带有时、分、秒格式的日期转化为00:00:00<br>
	 * 103 方便日期推算,格式化后的是yyyy-MM-dd 00:00:00 104
	 * 
	 * @param java
	 *            .util.Date... date的长度可以为0,即不用给参数 105
	 * @return java.util.Date 106
	 * **/

	public static Date startOfADay2(String d) {

		SimpleDateFormat simple = new SimpleDateFormat(FORMAT_8);

		try {

			return simple.parse(d);

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 119 推算�?��月内向前或向后偏移多少天,当然推算年也可以使用 120
	 * 
	 * @param date
	 *            要被偏移的日�?<br>
	 *            121 amout 偏移�?br> 122 b 是否先将date格式化为yyyy-MM-dd 00:00:00 �? 123
	 *            是否严格按整天计�?124
	 * @return java.util.Date 125
	 * **/

	public static Date addDayOfMonth(Date date, Integer amount, Boolean b) {

		date = date == null ? new Date() : date;// 如果date为null则取当前日期

		if (b) {

			date = startOfADay(date);

		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, amount);

		return cal.getTime();

	}

	/**
	 * 137 推算�?��小时内向前或向后偏移多少分钟,除了秒�?毫秒不可以以�?其他都可�?138
	 * 
	 * @param date
	 *            要被偏移的日�?<br>
	 *            139 amout 偏移�?br> 140 b 是否先将date格式化为yyyy-MM-dd HH:mm:00 �? 141
	 *            是否严格按整分钟计算 142
	 * @return java.util.Date 143
	 * **/

	public static Date addMinuteOfHour(Date date, Integer amount, Boolean b) {

		date = date == null ? new Date() : date;// 如果date为null则取当前日期

		if (b) {

			SimpleDateFormat simple = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:00");

			try {

				date = simple.parse(simple.format(date));

			} catch (ParseException e) {

				e.printStackTrace();

			}

		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.MINUTE, amount);

		return cal.getTime();

	}

	// 获取系统允许时间
	public static long getSystemByNow() throws Exception {
		String dateStart = "2013-08-14 09:29:58";
		String dateStop = "2013-09-06 11:31:48";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = new Date();

			// 毫秒ms
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			/*
			 * System.out.print("两个时间相差："); System.out.print(diffDays + " 天, ");
			 * System.out.print(diffHours + " 小时, ");
			 * System.out.print(diffMinutes + " 分钟, ");
			 * System.out.print(diffSeconds + " 秒.");
			 */
			return diffDays;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// 获取系统允许时间
	public static long getSystemByNow(String dateStart, String dateStop)
			throws Exception {
		/*
		 * String dateStart = "2013-08-14 09:29:58"; String dateStop =
		 * "2013-09-06 11:31:48";
		 */

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = new Date();

			// 毫秒ms
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			return diffDays;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static String addDay(Calendar date, int c_day) {

		if (date == null) {
			return "";
		}

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		// Date beginDate = new Date();
		// date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - c_day);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dft.format(endDate);

	}

	public static String addDay_gs2(Calendar date, int c_day) {

		if (date == null) {
			return "";
		}

		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - c_day);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dft.format(endDate);

	}

	public static String getNextDay() {
		Calendar date = Calendar.getInstance();
		if (date == null) {
			return "";
		}

		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dft.format(endDate);

	}

	public static String addDay2(Calendar date, int c_day) {

		if (date == null) {
			return "";
		}

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - c_day);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dft.format(endDate);

	}

	public static String getNowYMD() {
		// Calendar now = Calendar.getInstance();

		// return now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) +
		// 1)+"-"+now.get(Calendar.DATE);
		Date d = new Date();
		// System.out.println(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);

		return dateNowStr;
	}

	public static void main(String[] args) throws Exception {

		System.out.println(new Date().getMonth());
	}

	public static String addDay23(Date date, int c_day) {

		if (date == null) {
			return "";
		}

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		long tt = date.getTime() + 1000 * 60 * 60 * 24 * c_day;
		Date endDate = null;
		try {
			endDate = new Date(tt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dft.format(endDate);

	}

	public static void getDate(String unixDate) {

		SimpleDateFormat fm1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long unixLong = 0;
		String date = "";
		try {
			unixLong = Long.parseLong(unixDate) * 1000;
		} catch (Exception ex) {
			System.out.println("String转换Long错误，请确认数据可以转换！");
		}

		try {
			date = fm1.format(unixLong);
			System.out.println(new Date(date).toLocaleString());
			date = fm2.format(new Date(date));
		} catch (Exception ex) {
			System.out.println("String转换Date错误，请确认数据可以转换！");
		}
	}

	public static long getTimestamp() throws ParseException {

		Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.parse("2009/12/11 00:00:00");
		Date date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.parse("1970/01/01 08:00:00");
		long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime()
				- date2.getTime() : date2.getTime() - date1.getTime();
		long rand = (int) (Math.random() * 1000);

		return rand;
	}
}
