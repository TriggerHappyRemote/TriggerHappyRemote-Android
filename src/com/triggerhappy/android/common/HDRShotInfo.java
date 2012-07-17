package com.triggerhappy.android.common;

public class HDRShotInfo {
	private long shutter;

	public HDRShotInfo(long shutterLength){
		this.shutter = shutterLength;
	}
	
	public long getShutterLength(){
		return shutter;
	}
}
