package com.triggerhappy.android.common;

public class BulbRampingShot extends ICameraShot{
	private long cumulativeDelta;
	private long stepDelta;
	private long finalShutter;
	private boolean stepsCalc;
	
	public BulbRampingShot(){
		super();
		this.init();
	}
	
	protected void init(){
		this.cumulativeDelta = 0;
		this.stepDelta = 0;
		this.finalShutter = 0;
		this.stepsCalc = true;
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
	 * @return
	 */
	public long getInitialShutter(){
		return this.getShutterLength();
	}
	
	/**
	 * 
	 * @return
	 */
	public long getFinalShutter(){
		return this.finalShutter;
	}
	
	private void calculateSteps(){
		long avgShutter = (this.getInitialShutter() + this.finalShutter) / 2;
		
		int steps = (int) ((this.getDuration() / (avgShutter + this.getInterval())));

		if(steps <= 1){
			this.stepDelta = 0;

			this.cumulativeDelta = this.getInitialShutter();			
		}else{
			this.stepDelta = (this.getFinalShutter() - this.getInitialShutter()) / (steps - 1);

			this.cumulativeDelta = this.getInitialShutter();
		}
	}
	
	/**
	 * 
	 * @param milliseconds
	 */
	public void setFinalShutter(long milliseconds){
		this.finalShutter = milliseconds;
	}
	
	/**
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 */
	public void setFinalShutter(int hour, int minute, int second, long millisecond){
		this.finalShutter = TimeUtils.toMillisecond(hour, minute, second, millisecond);		
	}
	
	@Override
	public long calculateShutterLength(){
		if(stepsCalc){
			calculateSteps();
			stepsCalc = false;
		}
		
		long shutter = this.cumulativeDelta;
		
		this.cumulativeDelta += this.calculateProgression();
		return shutter;
	}
	
	private long calculateProgression(){
		return this.stepDelta;
	}
}
