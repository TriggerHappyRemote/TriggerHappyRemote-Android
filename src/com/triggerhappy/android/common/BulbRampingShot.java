package com.triggerhappy.android.common;

public class BulbRampingShot extends ICameraShot{
	private long cumulativeDelta;
	private long stepDelta;
	private boolean stepsCalc;
	
	public BulbRampingShot(){
		super();
		this.init();
	}
	
	protected void init(){
		this.cumulativeDelta = 0;
		this.stepDelta = 0;
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
	
	
	private void calculateSteps(){
		long avgShutter = (this.getInitialShutter() + this.getFinalShutter()) / 2;
		
		int steps = (int) ((this.getDuration() / (avgShutter + this.getInterval())));

		if(steps <= 1){
			this.stepDelta = 0;

			this.cumulativeDelta = this.getInitialShutter();			
		}else{
			this.stepDelta = (this.getFinalShutter() - this.getInitialShutter()) / (steps - 1);

			this.cumulativeDelta = this.getInitialShutter();
		}
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
