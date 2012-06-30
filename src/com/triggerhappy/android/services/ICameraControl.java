package com.triggerhappy.android.services;

import com.triggerhappy.android.common.ICameraShot;


public interface ICameraControl {
	
	/**
	 * 
	 * @param shutterDuration
	 */
	public void fireShutter(long shutterDuration);
	
	/**
	 * 
	 */
	public void closeShutter();
	
	/**
	 * 
	 */
	public void openShutter();
	
	/**
	 * 
	 * @param shot
	 */
	public void addShot(ICameraShot shot);
	
	/**
	 * 
	 */
	public void processShots();
}
