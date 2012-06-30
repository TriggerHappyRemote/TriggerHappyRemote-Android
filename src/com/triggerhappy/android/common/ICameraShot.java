package com.triggerhappy.android.common;

public abstract class ICameraShot {
	
	public enum ShotStatus {DONE, INTERVAL, SHOT};
	private long shutterLength;
	private long intervalLength;
	private long duration;
	private ShotStatus currentStatus;
	
	public ICameraShot(){
		this.shutterLength = 0;
		this.intervalLength = 0;
		this.duration = 0;
		this.currentStatus = ShotStatus.INTERVAL;
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
	 * Returns the objects current settings
	 * without modifying any current settings
	 * 
	 * @return the time in milliseconds
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
	 * @param milliseconds
	 */
	public void setDuration(long milliseconds){
		this.duration = milliseconds;
	}
	
	/**
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 */
	public void setDuration(int hour, int minute, int second, long millisecond){
		this.duration = toMillisecond(hour, minute, second, millisecond);		
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
	 * @param milliseconds
	 */
	public void setInterval(long milliseconds){
		this.intervalLength = milliseconds;
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
	 * 
	 * @param milliseconds
	 */
	public void setShutterLength(long milliseconds){
		this.shutterLength = milliseconds;
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
	public ShotStatus getStatus(){
		return this.currentStatus;
	}
	
	protected void setStatus(ShotStatus newStatus){
		this.currentStatus = newStatus;
	}
	
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
