package com.triggerhappy.internal;

import org.junit.Test;

import com.triggerhappy.android.common.BasicIntervalShot;
import com.triggerhappy.android.common.ICameraShot.ShotStatus;

/**
 * @author chris
 *
 */
public class IntervalUnitTest extends InterfaceUnitTest {
	final int INTERVAL = 0;
	final int SHOT = 1;
	final int DURATION = 2;
	
	final int LONG_DURATION = 4;
	final int LONG_INTERVAL = 2;
	final int LONG_SHOT = 1;
	
	
	long[] shortIntervalData = {(long) 10, (long) 20, (long) 180};
	
	
	ShotStatus[] statusData = {ShotStatus.INTERVAL, ShotStatus.SHOT};
	
	@Test
	public void testShortInterval(){
		BasicIntervalShot shortInterval = new BasicIntervalShot();
		
		int iterations = (int) (2 * (shortIntervalData[2] / (shortIntervalData[0] + shortIntervalData[1])));
		
		shortInterval.setDuration(shortIntervalData[DURATION]);
		assertEquals(shortIntervalData[DURATION], shortInterval.getDuration());
		
		// Verify simple setters work
		shortInterval.setInterval(shortIntervalData[INTERVAL]);
		assertEquals(shortIntervalData[INTERVAL], shortInterval.getInterval());
		
		shortInterval.setShutterLength(shortIntervalData[SHOT]);
		assertEquals(shortIntervalData[SHOT], shortInterval.getShutterLength());
		
		int i;
		// Simulate running the loop without time delay
		for(i = 0; i < iterations; i++){
			assertEquals(statusData[i % 2], shortInterval.getStatus());
			assertEquals(shortIntervalData[i % 2], shortInterval.getDelay());
		}
		
		assertEquals(iterations, i);
		assertEquals(ShotStatus.DONE, shortInterval.getStatus());
		
	}
	
	@Test
	public void testLongInterval(){
		BasicIntervalShot longInterval = new BasicIntervalShot();
		
		longInterval.setDuration(hourData[LONG_DURATION], minData[LONG_DURATION], secData[LONG_DURATION], milliData[LONG_DURATION]);
		assertEquals(dataSetResults[LONG_DURATION], longInterval.getDuration());
		
		longInterval.setInterval(hourData[LONG_INTERVAL], minData[LONG_INTERVAL], secData[LONG_INTERVAL], milliData[LONG_INTERVAL]);
		assertEquals(dataSetResults[LONG_INTERVAL], longInterval.getInterval());
		
		longInterval.setShutterLength(hourData[LONG_SHOT], minData[LONG_SHOT], secData[LONG_SHOT], milliData[LONG_SHOT]);
		assertEquals(dataSetResults[LONG_SHOT], longInterval.getShutterLength());
		
		int iterations = (int) (2 * longInterval.getDuration() / (longInterval.getShutterLength() + longInterval.getInterval()));

		int i;
		// Simulate running the loop without time delay
		for(i = 0; i < iterations - 1; i++){
			if(i%2 == 0){
				assertEquals(ShotStatus.INTERVAL, longInterval.getStatus());
				assertEquals(dataSetResults[LONG_INTERVAL], longInterval.getDelay());
			}else{
				assertEquals(ShotStatus.SHOT, longInterval.getStatus());
				assertEquals(dataSetResults[LONG_SHOT], longInterval.getDelay());
			}
		}
		
		assertNotSame(dataSetResults[LONG_INTERVAL], longInterval.getDelay());
		assertEquals(ShotStatus.DONE, longInterval.getStatus());
		
		assertEquals(iterations, i + 1);
		assertEquals(ShotStatus.DONE, longInterval.getStatus());
	}
}
