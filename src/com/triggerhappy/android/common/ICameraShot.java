package com.triggerhappy.android.common;

public abstract class ICameraShot {
	
	public enum ShotStatus {DONE, INTERVAL, SHOT};
	
	protected long elapsedTime;
	
	private long shutterLength;
	private long intervalLength;
	private long duration;
	private ShotStatus currentStatus;
	
	public ICameraShot(){
		this.shutterLength = 0;
		this.intervalLength = 0;
		this.duration = 0;
		this.currentStatus = ShotStatus.INTERVAL;
		this.elapsedTime = 0;
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
	 * Safe to call does not modify anything
	 * 
	 * @return
	 */
	public long getShutterLength(){
		return this.shutterLength;
	}
	
	/**
	 * May modify underlying values only call
	 * if you know what you are doing!
	 * 
	 * @return
	 */
	protected long calculateShutterLength(){
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
		this.duration = TimeUtils.toMillisecond(hour, minute, second, millisecond);		
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
		this.intervalLength = TimeUtils.toMillisecond(hour, minute, second, millisecond);
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
		this.shutterLength = TimeUtils.toMillisecond(hour, minute, second, millisecond);
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
	public long getDelay() {
		long delay = 0;
		long reduction = 0;

		switch (this.getStatus()) {
			case SHOT: {
				long shutterLength = this.calculateShutterLength();
				this.elapsedTime += shutterLength;
				delay = shutterLength;
				
				reduction = this.toggleStatus();
				break;
			}
	
			case INTERVAL: {
				this.elapsedTime += this.getInterval();
				delay = this.getInterval();
				
				reduction = this.toggleStatus();
				break;
			}
			
			default: {// DONE status
				break;
			}
		}
		return delay - reduction;
	}

	/**
	 * 
	 * @return
	 */
	protected long toggleStatus(){
		long result = 0;
		if (this.elapsedTime == this.getDuration()){
			this.setStatus(ShotStatus.DONE);
		}else if(this.elapsedTime > this.getDuration()){
			result = this.elapsedTime - this.getDuration();
			this.setStatus(ShotStatus.DONE);
		}else if(this.getStatus() == ShotStatus.INTERVAL){
			this.setStatus(ShotStatus.SHOT);
		}else if(this.getStatus() == ShotStatus.SHOT){
			this.setStatus(ShotStatus.INTERVAL);
		}else{
			// #TODO Error!!!
		}
		
		return result;
	}
}
