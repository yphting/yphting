package com.accp.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期与时间
 * 
 * @author yuno
 *
 */
public class DateTime {
	// 时间格式转换器
	private static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 当前时间
	 * 
	 * @return 当前时间字符串
	 */
	public static String nowDate() {
		String time = simpleDate.format(new Date());
		return time;
	}
}