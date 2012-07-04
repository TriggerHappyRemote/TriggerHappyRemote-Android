package com.triggerhappy.android.common;

public class TimeUtils {
	/**
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static long toMillisecond(int hour, int minute, int second, long millisecond){
		return millisecond + second * 1000 + minute * 60000 + hour * 3600000;
	}

}
