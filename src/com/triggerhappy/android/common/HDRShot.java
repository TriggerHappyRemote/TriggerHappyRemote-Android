package com.triggerhappy.android.common;

import java.util.LinkedList;
import java.util.List;



public class HDRShot extends ICameraShot {
	@SuppressWarnings("unused")
	private boolean bulb;
	private double evInterval;
	
	private int numberOfShots;

	private List<HDRShotInfo> shots;
	
	private boolean calculate;
	
	
	public HDRShot(){
		super();
		
		init();
	}
	
	private void init(){
		this.numberOfShots = 0;
		this.bulb = true;
		this.evInterval = 0;
		this.calculate = true;
		
		shots = new LinkedList<HDRShotInfo>();
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
	}

	@Override
	public long getDelay() {
		if(this.calculate){
			this.calculateShots();
		}
		if(!shots.isEmpty())
			return shots.remove(0).getShutterLength();
		else
			return 0;
	}
	
	/**
	 * 
	 */
	private void calculateShots(){
		shots.add(new HDRShotInfo((long)(this.getShutterLength())));
		
		for(int i = 0; i < this.numberOfShots / 2; i++){
			shots.add(new HDRShotInfo((long)(this.getShutterLength() * Math.pow(2, this.evInterval * -1))));
			shots.add(new HDRShotInfo((long)(this.getShutterLength() * Math.pow(2, this.evInterval))));
		}
	}

}
