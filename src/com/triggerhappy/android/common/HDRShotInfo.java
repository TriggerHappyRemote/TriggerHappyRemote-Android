package com.triggerhappy.android.common;

import java.io.Serializable;

public class HDRShotInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long shutter;

	public HDRShotInfo(long shutterLength){
		this.shutter = shutterLength;
	}
	
	public long getShutterLength(){
		return shutter;
	}
}
