package com.triggerhappy.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.BulbRampingShot;
import com.triggerhappy.android.common.TimerSettings;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class BulbRampingTriggerActivity extends TriggerHappyNavigation{
	final private int SHUTTER_LENGTH = 0;
	final private int INTERVAL_LENGTH = 1;
	final private int DURATION = 2;
	final private int FINAL_SHUTTER = 3;
	
	private TimerSettings intervalSettings;
	private TimerSettings shutterSettings;
	private TimerSettings durationSettings;
	private TimerSettings finalShutterSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bramp);

	    intervalSettings = null;
	    shutterSettings = null;
	    durationSettings = null;
	    finalShutterSettings = null;

        this.initNavigation(1);
        
	    doBindService();
	    
	    RelativeLayout lyt = (RelativeLayout)findViewById(R.id.brampDuration_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());
	    
	    lyt = (RelativeLayout)findViewById(R.id.brampInterval_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());
	    
	    lyt = (RelativeLayout)findViewById(R.id.brampShutter_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());
	    
	    lyt = (RelativeLayout)findViewById(R.id.brampFinalShutter_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());
    }
	    
	private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == 1)
				{
					switch(v.getId()){
						case R.id.brampShutter_button:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
							intent.putExtra("Title", "Shutter Length");
							intent.putExtra("subSecond", true);
							if(shutterSettings != null){
								intent.putExtra("hours", shutterSettings.getHour());
								intent.putExtra("minutes", shutterSettings.getMinute());
								intent.putExtra("seconds", shutterSettings.getSeconds());
								intent.putExtra("subSeconds", shutterSettings.getSubSeconds());
							}
							startActivityForResult(intent, SHUTTER_LENGTH);
							break;
						}
						
						case R.id.brampInterval_button:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
							intent.putExtra("Title", "Interval Length");
							intent.putExtra("subSecond", true);
							if(intervalSettings != null){
								intent.putExtra("hours", intervalSettings.getHour());
								intent.putExtra("minutes", intervalSettings.getMinute());
								intent.putExtra("seconds", intervalSettings.getSeconds());
								intent.putExtra("subSeconds", intervalSettings.getSubSeconds());
							}
							startActivityForResult(intent, INTERVAL_LENGTH);
							break;
						}
						
						case R.id.brampFinalShutter_button:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
							intent.putExtra("Title", "Final Shutter Length");
							intent.putExtra("subSecond", true);
							if(finalShutterSettings != null){
								intent.putExtra("hours", finalShutterSettings.getHour());
								intent.putExtra("minutes", finalShutterSettings.getMinute());
								intent.putExtra("seconds", finalShutterSettings.getSeconds());
								intent.putExtra("subSeconds", finalShutterSettings.getSubSeconds());

							}
							startActivityForResult(intent, FINAL_SHUTTER);
							break;
						}
						
						case R.id.brampDuration_button:
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
				renderTimeDisplay(R.id.brampInterval_display, timer);
			}else if(requestCode == SHUTTER_LENGTH){
				shutterSettings = timer;
				renderTimeDisplay(R.id.brampShutter_display, timer);
			}else if(requestCode == DURATION){
				durationSettings = timer;
				renderTimeDisplay(R.id.brampDuration_display, timer);
			}else if (requestCode == FINAL_SHUTTER){
				finalShutterSettings = timer;
				renderTimeDisplay(R.id.brampFinalShutter_display, timer);
			}
		}
	}

	@Override
	protected void startProcessing() {
		// Verify that we have settings for all settings
		if (intervalSettings != null && shutterSettings != null && durationSettings != null && finalShutterSettings != null && mIsBound){
				BulbRampingShot shoot = new BulbRampingShot();
				
				shoot.setDuration((int)durationSettings.getHour(), (int)durationSettings.getMinute(), (int)durationSettings.getSeconds(), durationSettings.getSubSeconds());
				shoot.setInterval((int)intervalSettings.getHour(), (int)intervalSettings.getMinute(), (int)intervalSettings.getSeconds(), intervalSettings.getSubSeconds());
				shoot.setShutterLength((int)shutterSettings.getHour(), (int)shutterSettings.getMinute(), (int)shutterSettings.getSeconds(), shutterSettings.getSubSeconds());
				shoot.setFinalShutter((int)finalShutterSettings.getHour(), (int)finalShutterSettings.getMinute(), (int)finalShutterSettings.getSeconds(), finalShutterSettings.getSubSeconds());
				
				mBoundService.addShot(shoot);
				
				mBoundService.startProcessing();
				
		}
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		this.durationSettings = (TimerSettings) savedInstanceState.getSerializable("durationLength");
		this.shutterSettings = (TimerSettings) savedInstanceState.getSerializable("shutterLength");
		this.intervalSettings = (TimerSettings) savedInstanceState.getSerializable("intervalLength");
		this.finalShutterSettings = (TimerSettings) savedInstanceState.getSerializable("finalShutterLength");
		
		if(this.intervalSettings != null)
			renderTimeDisplay(R.id.brampInterval_display, this.intervalSettings);
		if(this.shutterSettings != null)
			renderTimeDisplay(R.id.brampShutter_display, this.shutterSettings);
		if(this.durationSettings != null)
			renderTimeDisplay(R.id.brampDuration_display, this.durationSettings);
		if(this.finalShutterSettings != null)
			renderTimeDisplay(R.id.brampFinalShutter_display, this.finalShutterSettings);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("shutterLength", shutterSettings);
		outState.putSerializable("intervalLength", intervalSettings);
		outState.putSerializable("finalShutterLength", finalShutterSettings);
		outState.putSerializable("durationLength", durationSettings);
		super.onSaveInstanceState(outState);
	}
}
