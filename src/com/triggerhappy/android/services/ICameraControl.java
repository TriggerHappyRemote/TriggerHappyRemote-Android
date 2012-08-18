package com.triggerhappy.android.services;

import com.triggerhappy.android.common.ICameraShot;

/**
 * Outward facing interface for access by
 * Activities
 * 
 * @author chris
 *
 */
public interface ICameraControl {
		
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
	public void stopShots();
}
