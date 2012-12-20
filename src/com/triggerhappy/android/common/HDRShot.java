package com.triggerhappy.android.common;

import java.util.LinkedList;
import java.util.List;



public class HDRShot extends ICameraShot {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double evInterval;
	
	private int numberOfShots;

	private List<HDRShotInfo> shots;
	
	public HDRShot(){
		super();
		
		init();
	}
	
	private void init(){
		this.numberOfShots = 3;
		this.evInterval = 1;
		shots = new LinkedList<HDRShotInfo>();
		this.calculateShots();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIcon() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 
	 */
	public void setEVInterval(double _evInterval){
		this.evInterval = _evInterval;
		this.calculateShots();
	}
	
	/**
	 * 
	 * @return
	 */
	public double getEVInterval(){
		return this.evInterval;
	}
	
	public void setNumberOfShots(int _numberOfShots){
		this.numberOfShots = _numberOfShots;
		this.calculateShots();
	}

	@Override
	public long getDelay() {
		if(this.getStatus() == ShotStatus.INTERVAL){
			this.toggleStatus();
			return 1000;
			
		}else if(this.getStatus() == ShotStatus.SHOT){
			this.toggleStatus();
			return shots.remove(0).getShutterLength();
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected long toggleStatus(){
		long result = 0;
		if (this.shots.isEmpty()){
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

	/**
	 * 
	 */
	private void calculateShots(){
		shots.add(new HDRShotInfo((long)(this.getShutterLength())));
		
		for(int i = 0; i < this.numberOfShots / 2; i++){
			shots.add(new HDRShotInfo((long)(this.getShutterLength() * Math.pow(2, this.evInterval * -1 * i))));
			shots.add(new HDRShotInfo((long)(this.getShutterLength() * Math.pow(2, this.evInterval * i))));
		}
	}

}
