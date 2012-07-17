package com.triggerhappy.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.BasicIntervalShot;
import com.triggerhappy.android.common.ICameraShot;
import com.triggerhappy.android.common.TimerSettings;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class TriggerHappyAndroidActivity extends TriggerHappyNavigation{
	final private int SHUTTER_LENGTH = 0;
	final private int INTERVAL_LENGTH = 1;
	final private int DURATION = 2;
	
	private TimerSettings intervalSettings;
	private TimerSettings shutterSettings;
	private TimerSettings durationSettings;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.interval);
	    intervalSettings = null;
	    shutterSettings = null;
	    durationSettings = null;
	    
	    doBindService();
	    
	    this.initNavigation(0);
		Button button = (Button) findViewById(R.id.intervalLength_button);
		button.setOnTouchListener(this.OnTouchListener());

		button = (Button) findViewById(R.id.shutterLength_button);
		button.setOnTouchListener(this.OnTouchListener());

		button = (Button) findViewById(R.id.duration_button);
		button.setOnTouchListener(this.OnTouchListener());

		button = (Button) findViewById(R.id.startInterval_button);
		button.setOnTouchListener(this.OnTouchListener());
    }
    
	private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == 1)
				{
					switch(v.getId()){
						case R.id.intervalLength_button:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
							intent.putExtra("Title", "Interval Length");
							if(intervalSettings != null){
								intent.putExtra("hours", intervalSettings.getHour());
								intent.putExtra("minutes", intervalSettings.getMinute());
								intent.putExtra("seconds", intervalSettings.getSeconds());
								intent.putExtra("subSeconds", intervalSettings.getSubSeconds());
							}
							startActivityForResult(intent, INTERVAL_LENGTH);
							break;
						}
						
						case R.id.shutterLength_button:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
							intent.putExtra("subSecond", true);
							intent.putExtra("Title", "Shutter Length");
							if(shutterSettings != null){
								intent.putExtra("hours", shutterSettings.getHour());
								intent.putExtra("minutes", shutterSettings.getMinute());
								intent.putExtra("seconds", shutterSettings.getSeconds());
								intent.putExtra("subSeconds", shutterSettings.getSubSeconds());
							}
							startActivityForResult(intent, SHUTTER_LENGTH);
							break;
						}
						
						case R.id.duration_button:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
							intent.putExtra("Title", "Duration Length");
							if(durationSettings != null){
								intent.putExtra("hours", durationSettings.getHour());
								intent.putExtra("minutes", durationSettings.getMinute());
								intent.putExtra("seconds", durationSettings.getSeconds());
								intent.putExtra("subSeconds", durationSettings.getSubSeconds());
							}
							startActivityForResult(intent, DURATION);
							break;
						}
						
						case R.id.startInterval_button:
						{
							// Verify that we have settings for all settings
							if (intervalSettings != null && shutterSettings != null && durationSettings != null && mIsBound || true){
									ICameraShot shoot = new BasicIntervalShot();
									
									shoot.setDuration((int)durationSettings.getHour(), (int)durationSettings.getMinute(), (int)durationSettings.getSeconds(), durationSettings.getSubSeconds());
									shoot.setInterval((int)intervalSettings.getHour(), (int)intervalSettings.getMinute(), (int)intervalSettings.getSeconds(), intervalSettings.getSubSeconds());
									shoot.setShutterLength((int)shutterSettings.getHour(), (int)shutterSettings.getMinute(), (int)shutterSettings.getSeconds(), shutterSettings.getSubSeconds());
									
									mBoundService.addShot(shoot);
									
									mBoundService.processShots();
									
							}
							
						}
					}
				}
				return false;
			}
		};
	};
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK){
			TimerSettings timer = new TimerSettings();
			timer.setHour(data.getLongExtra("hours", 0));
			timer.setMinute(data.getLongExtra("minutes", 0));
			timer.setSeconds(data.getLongExtra("seconds", 0));
			timer.setSubSeconds(data.getLongExtra("subSeconds", 0));
			
			if(requestCode == INTERVAL_LENGTH){
				intervalSettings = timer;
				renderTimeDisplay(R.id.intervalLength_display, timer);
			}else if(requestCode == SHUTTER_LENGTH){
				shutterSettings = timer;
				renderTimeDisplay(R.id.shutterLength_display, timer);
			}else if(requestCode == DURATION){
				durationSettings = timer;
				renderTimeDisplay(R.id.durationLength_display, timer);
			}
		}
	}
}