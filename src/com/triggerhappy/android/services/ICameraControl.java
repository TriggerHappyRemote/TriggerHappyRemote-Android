package com.triggerhappy.android.services;

import com.triggerhappy.android.common.ICameraShot;


public interface ICameraControl {
	
	public void fireShutter(long shutterDuration);
	
	public void closeShutter();
	
	public void openShutter();
	
	public void addShot(ICameraShot shot);
	
	public void processShots();
	

}
