package com.triggerhappy.internal;

import junit.framework.TestCase;

import org.junit.Test;

import com.triggerhappy.android.common.BulbRampingShot;
import com.triggerhappy.android.common.ICameraShot.ShotStatus;

public class BrampUnitTest extends TestCase {
	final int DURATION = 0;
	final int INTERVAL = 1;
	final int START_SHUTTER = 2;
	final int END_SHUTTER = 3;
	
	long[] shortSettings = { 45, 5, 5, 15};
	long[] reverseSettings = { 45, 5, 15, 5};
	
	long[] shortProgression = {5, 5, 5, 10, 5, 15};
	long[] reverseProgression = {5, 15, 5, 10, 5, 5};
	
	@Test
	public void testSimpleBramp(){
		BulbRampingShot bramp = new BulbRampingShot();
		
		bramp.setDuration(shortSettings[DURATION]);
		assertEquals(shortSettings[DURATION], bramp.getDuration());
		
		bramp.setInterval(shortSettings[INTERVAL]);
		assertEquals(shortSettings[INTERVAL], bramp.getInterval());
		
		bramp.setShutterLength(shortSettings[START_SHUTTER]);
		assertEquals(shortSettings[START_SHUTTER], bramp.getInitialShutter());
		
		bramp.setFinalShutter(shortSettings[END_SHUTTER]);
		assertEquals(shortSettings[END_SHUTTER], bramp.getFinalShutter());
		
		int i;
		// Simulate running the loop without time delay
		for(i = 0; i < 6; i++){
			if(i%2 == 0){
				assertEquals(ShotStatus.INTERVAL, bramp.getStatus());
				assertEquals(shortSettings[INTERVAL], bramp.getDelay());
			}else{
				assertEquals(ShotStatus.SHOT, bramp.getStatus());
				assertEquals(shortProgression[i], bramp.getDelay());
			}
		}
	}
	
	@Test
	public void testReverseBramp(){
		BulbRampingShot bramp = new BulbRampingShot();
		
		bramp.setDuration(reverseSettings[DURATION]);
		assertEquals(reverseSettings[DURATION], bramp.getDuration());
		
		bramp.setInterval(reverseSettings[INTERVAL]);
		assertEquals(reverseSettings[INTERVAL], bramp.getInterval());
		
		bramp.setShutterLength(reverseSettings[START_SHUTTER]);
		assertEquals(reverseSettings[START_SHUTTER], bramp.getInitialShutter());
		
		bramp.setFinalShutter(reverseSettings[END_SHUTTER]);
		assertEquals(reverseSettings[END_SHUTTER], bramp.getFinalShutter());
		
		int i;
		// Simulate running the loop without time delay
		for(i = 0; i < 6; i++){
			if(i%2 == 0){
				assertEquals(ShotStatus.INTERVAL, bramp.getStatus());
				assertEquals(reverseSettings[INTERVAL], bramp.getDelay());
			}else{
				assertEquals(ShotStatus.SHOT, bramp.getStatus());
				assertEquals(reverseProgression[i], bramp.getDelay());
			}
		}
	}
	
}
