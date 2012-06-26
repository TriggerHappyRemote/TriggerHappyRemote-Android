package com.triggerhappy.android.common;

public abstract class ICameraShot {
	
	public enum ShotStatus {DONE, INTERVAL, SHOT};
	private long shutterLength;
	private long intervalLength;
	private long duration;
	
	public ICameraShot(){
		this.shutterLength = 0;
		this.intervalLength = 0;
		this.duration = 0;
	}
	
	/**
	 * Returns the id of the Icon for this 
	 * shot for display in a list
	 * 
	 * @return
	 */
	public abstract int getIcon();

	/**
	 * Returns the readable name of the Shot
	 * i.e. HDR, Bramp, Interval
	 * 
	 * @return - String Representation of this shot
	 */
	public abstract String getType();
	
	/**
	 * 
	 * @return
	 */
	public long getInterval(){
		return this.intervalLength;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getShutterLength(){
		return this.shutterLength;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getDuration(){
		return this.duration;
	}
	
	/**
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public void setInterval(int hour, int minute, int second, long millisecond){
		this.intervalLength = toMillisecond(hour, minute, second, millisecond);
	}
	
	/**
	 * Sets the shutter length. Completely overwrites any 
	 * existing value
	 * 
	 * @param hour - can be zero or more
	 * @param minute - can be zero or more
	 * @param second - can be zero or more
	 * @param milisecond - can be zero or more
	 */
	public void setShutterLength(int hour, int minute, int second, long millisecond){
		this.shutterLength = toMillisecond(hour, minute, second, millisecond);
	}
	
	private long toMillisecond(int hour, int minute, int second, long millisecond){
		return millisecond + second * 1000 + minute * 60000 + hour * 3600000;
	}
	/**
	 * Returns the current state of the shot 
	 * 
	 * @return 	DONE - This shot is complete, 
	 * 			INTERVAL - Getting ready to wait for the next shot, 
	 * 			SHOT - Getting ready to take a shot
	 */
	public abstract ShotStatus getStatus();
	
	/**
	 * Method that returns the next interval 
	 * or shot duration for the scheduler
	 * 
	 * This will alter the state of the object
	 * changing the current state and decrementing internal
	 * time remaining counter
	 * 
	 * @return the delay in milliseconds for the current status
	 */
	public abstract long getDelay();
}
