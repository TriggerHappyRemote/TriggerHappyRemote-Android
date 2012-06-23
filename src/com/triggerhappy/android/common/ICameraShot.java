package com.triggerhappy.android.common;

public interface ICameraShot {
	
	public int getIcon();
	
	public String getType();
	
	public long getInterval();
	
	public long getShutterLength();
	
	public void setInterval();
	
	public void setShutterLength();
	
}
