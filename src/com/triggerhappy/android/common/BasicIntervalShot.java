package com.triggerhappy.android.common;

public class BasicIntervalShot extends ICameraShot {
	private long elapsedTime;

	public int getIcon() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDelay() {
		long delay = 0;
		long reduction = 0;

		switch (this.getStatus()) {
			case SHOT: {
				this.elapsedTime += this.getShutterLength();
				delay = this.getShutterLength();
				
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
	private long toggleStatus(){
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
