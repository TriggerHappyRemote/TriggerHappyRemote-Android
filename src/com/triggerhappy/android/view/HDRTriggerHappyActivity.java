package com.triggerhappy.android.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.BulbRampingShot;
import com.triggerhappy.android.common.HDRShot;
import com.triggerhappy.android.common.TimerSettings;
import com.triggerhappy.android.common.TriggerHappyNavigation;
import com.triggerhappy.android.controller.HDRView;

public class HDRTriggerHappyActivity extends TriggerHappyNavigation{
	private TimerSettings shutterSettings;
	private double evInterval;
	private int noOfShots;

	final private int SHUTTER_LENGTH = 0;
	final private int EXPOSURE_INTERVAL = 1;
	final private int NUMBER_OF_SHOTS = 2;
	
	final CharSequence[] shotOptions = {"3", "5", "7", "9"};
	final CharSequence[] exposureInterval = {"3", "5", "7", "9"};

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.hdr);
	    
	    doBindService();
	    
	    this.initNavigation(2);
	    
	    this.evInterval = .33;
	    this.noOfShots = 3;
	    
	    Button btn = (Button)findViewById(R.id.hdr_base);
	    btn.setOnTouchListener(OnTouchListener());

	    
	    btn = (Button)findViewById(R.id.hdr_num);
	    btn.setOnTouchListener(OnTouchListener());
	    
	    btn = (Button)findViewById(R.id.hdr_exp);
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
						
						case R.id.hdr_num:
						{
							AlertDialog.Builder builder = new AlertDialog.Builder(HDRTriggerHappyActivity.this);
							builder.setTitle("Number of Shots");
							builder.setItems(shotOptions, new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog, int item) {
							    	HDRView v = (HDRView)findViewById(R.id.HDR_ViewFinder);
							    	v.setNoOfShots(Integer.parseInt((String) shotOptions[item]));
							        noOfShots = Integer.parseInt((String) shotOptions[item]);
							    	Toast.makeText(getApplicationContext(), shotOptions[item], Toast.LENGTH_SHORT).show();
							    }
							});
							AlertDialog alert = builder.create();
							alert.show();
							break;
						}
						
						case R.id.hdr_exp:
						{

							AlertDialog.Builder builder = new AlertDialog.Builder(HDRTriggerHappyActivity.this);
							builder.setTitle("Number of Shots");
							builder.setItems(exposureInterval, new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog, int item) {
									HDRView v = (HDRView) findViewById(R.id.HDR_ViewFinder);
									v.setExposureInterval(Double.parseDouble((String) exposureInterval[item]));
									evInterval = Double.parseDouble((String) exposureInterval[item]);							    }
							});
							AlertDialog alert = builder.create();
							alert.show();
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
				renderTimeDisplay(R.id.HDR_ViewFinder, timer);
			}
		}
	}
	
	protected void renderTimeDisplay(int id, TimerSettings timer) {
		HDRView v = (HDRView) findViewById(id);

		String result = "";
		boolean second = false;

		result += (timer.getHour() < 10) ? "0" + timer.getHour() + ":" : timer.getHour() + ":";
		result += (timer.getMinute() < 10) ? "0" + timer.getMinute() + ":" : timer.getMinute() + ":";

		if (timer.getSeconds() != 0) {
			result += (timer.getSeconds() < 10) ? "0" + timer.getSeconds() : timer.getSeconds();
			second = true;
		}

		if (timer.getSubSeconds() != 0) {
			result += " " + timer.getSubSecondString();
			second = true;
		}else if(!second){
			result += 0;
		}

		if (!second)
			result += 0;

		v.setShutterLength(result);
	}
	
    @Override
	protected void startProcessing() {
    	if(this.evInterval > 0 && this.noOfShots > 0 && this.shutterSettings != null){
			HDRShot shoot = new HDRShot();
			
			shoot.setEVInterval(this.evInterval);
			shoot.setShutterLength((int)shutterSettings.getHour(), (int)shutterSettings.getMinute(), (int)shutterSettings.getSeconds(), shutterSettings.getSubSeconds());
			shoot.setNumberOfShots(noOfShots);
			
			mBoundService.addShot(shoot);
			
			mBoundService.startProcessing();
    	}
	}
}
