package com.triggerhappy.internal;

import org.junit.Test;

import com.triggerhappy.android.common.BasicIntervalShot;

import junit.framework.TestCase;

public class InterfaceUnitTest extends TestCase {
	int[] hourData = {0, 0, 1, 1, 120};
	int[] minData = {0, 20, 0, 1, 200};
	int[] secData = {120, 0, 0, 1, 300};
	long[] milliData = {0, 0, 0, 1, 400};
	
	long[] dataSetResults = {120000, 1200000, 3600000, 3661001, 444300400};

	@Test
	public void testSetData(){
		// Verify test Data integrity
		assertEquals(hourData.length, minData.length);
		assertEquals(secData.length, milliData.length);
		assertEquals(hourData.length, secData.length);
		
		// Verify Conversion Algorithms are still working
		for(int i = 0; i < hourData.length; i++){
			BasicIntervalShot shot = new BasicIntervalShot();
			
			shot.setInterval(hourData[i], minData[i], secData[i], milliData[i]);
			shot.setShutterLength(hourData[i], minData[i], secData[i], milliData[i]);
			shot.setDuration(hourData[i], minData[i], secData[i], milliData[i]);
			
			assertEquals(dataSetResults[i], shot.getInterval());
			assertEquals(dataSetResults[i], shot.getShutterLength());
			assertEquals(dataSetResults[i], shot.getDuration());
		}
	}

}
