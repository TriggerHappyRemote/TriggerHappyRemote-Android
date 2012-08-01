package com.triggerhappy.android.common;

import java.io.Serializable;

public class TimerSettings implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long hour;
	private long minute;
	private long seconds;
	private long subSeconds;
	
	public TimerSettings(){
		this.hour = 0;
		this.minute = 0;
		this.seconds = 0;
		this.subSeconds = 0;
	}
	
	public String toString(){
		return hour + " h: " + minute + " m: " + seconds + " " + subSeconds + " s";
	}
	
	public void setHour(long _hour){
		this.hour = _hour;
	}
	
	public long getHour(){
		return this.hour;
	}
	
	public void setMinute(long _minute){
		this.minute = _minute;
	}
	
	public long getMinute(){
		return this.minute;
	}
	
	public void setSeconds(long _seconds){
		this.seconds = _seconds;
	}
	
	public long getSeconds(){
		return this.seconds;
	}
	
	public void setSubSeconds(long _subSeconds){
		this.subSeconds = _subSeconds;
	}
	
	public long getSubSeconds(){
		return this.subSeconds;
	}
	
	public String getSubSecondString(){
		switch((int)this.subSeconds){
		case 33:
			return "1/30";
		case 67:
			return "1/15";
		case 125:
			return "1/8";
		case 250:
			return "1/4";
		case 500:
			return "1/2";
		default:
			return "0";
		}
	}

}
