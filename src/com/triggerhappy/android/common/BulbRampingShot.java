package com.triggerhappy.android.common;

public class BulbRampingShot extends BasicIntervalShot{
	private long delta;
	private long currentDelta;
	
	public BulbRampingShot(){
		super();
	}
	
	protected void init(){
		this.delta = 0;
		this.currentDelta = 0;
	}
	
	public int getIcon() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 */
	public void setFinalShutter(){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public long getShutterLength(){
		long shutter = delta;
		
		this.delta += calculateProgression();
		return super.getShutterLength() + shutter;
	}
	
	private long calculateProgression(){
		return this.currentDelta;
	}
	
	protected long getCurrentStepDelta(){
		return this.delta;
	}
}
