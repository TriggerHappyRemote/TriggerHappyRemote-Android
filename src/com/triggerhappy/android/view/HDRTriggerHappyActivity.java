package com.triggerhappy.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TimerSettings;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class HDRTriggerHappyActivity extends TriggerHappyNavigation{
	private TimerSettings shutterSettings;

	final private int SHUTTER_LENGTH = 0;
	final private int EXPOSURE_INTERVAL = 1;
	final private int NUMBER_OF_SHOTS = 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.hdr);
	    
	    this.initNavigation(2);
	    
	    Button btn = (Button)findViewById(R.id.hdr_base);
	    btn.setOnTouchListener(OnTouchListener());
    }

	private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == 1)
				{
					switch(v.getId()){
						case R.id.hdr_base:
						{
							Intent intent = new Intent(getBaseContext(), TimePickerTriggerHappyActivity.class);
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
			
			if(requestCode == SHUTTER_LENGTH){
				shutterSettings = timer;
				renderTimeDisplay(R.id.brampInterval_display, timer);
			}
		}
	}
	
	
	@Override
	protected void startProcessing() {
		// TODO Auto-generated method stub
		
	}
}
