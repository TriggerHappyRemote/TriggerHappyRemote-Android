package com.triggerhappy.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

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

		if(savedInstanceState != null){
			this.durationSettings = (TimerSettings) savedInstanceState.getSerializable("durationLength");
			this.shutterSettings = (TimerSettings) savedInstanceState.getSerializable("shutterLength");
			this.intervalSettings = (TimerSettings) savedInstanceState.getSerializable("intervalLength");
		}
    	
	    this.initNavigation(0);
	    RelativeLayout lyt = (RelativeLayout)findViewById(R.id.intervalLength_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());

	    lyt = (RelativeLayout)findViewById(R.id.shutterLength_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());

	    lyt = (RelativeLayout)findViewById(R.id.duration_button);
	    lyt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	    lyt.setOnTouchListener(OnTouchListener());
		
		if(this.intervalSettings != null)
			renderTimeDisplay(R.id.intervalLength_display, this.intervalSettings);
		if(this.shutterSettings != null)
			renderTimeDisplay(R.id.shutterLength_display, this.shutterSettings);
		if(this.durationSettings != null)
			renderTimeDisplay(R.id.durationLength_display, this.durationSettings);
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

	@Override
	protected void startProcessing() {
		// Verify that we have settings for all settings
		if (intervalSettings != null && shutterSettings != null && durationSettings != null){
			ICameraShot shoot = new BasicIntervalShot();

			shoot.setDuration((int)durationSettings.getHour(), (int)durationSettings.getMinute(), (int)durationSettings.getSeconds(), durationSettings.getSubSeconds());
			shoot.setInterval((int)intervalSettings.getHour(), (int)intervalSettings.getMinute(), (int)intervalSettings.getSeconds(), intervalSettings.getSubSeconds());
			shoot.setShutterLength((int)shutterSettings.getHour(), (int)shutterSettings.getMinute(), (int)shutterSettings.getSeconds(), shutterSettings.getSubSeconds());
			
			
			Bundle bndl = new Bundle();
			bndl.putSerializable("Shot", shoot);
			Intent intent = new Intent(getBaseContext(), ShotStatusActivity.class);
			
			intent.putExtras(bndl);

			startActivity(intent);
		}else
			System.out.println("Didn't get in the loop");
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("shutterLength", shutterSettings);
		outState.putSerializable("intervalLength", intervalSettings);
		outState.putSerializable("durationLength", durationSettings);
		super.onSaveInstanceState(outState);
	}
}