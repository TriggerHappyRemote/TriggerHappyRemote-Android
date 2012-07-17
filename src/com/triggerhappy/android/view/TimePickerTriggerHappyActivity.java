package com.triggerhappy.android.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class TimePickerTriggerHappyActivity extends TriggerHappyNavigation {
	private Spinner hourPicker;
	private Spinner minutePicker;
	private Spinner secondPicker;
	private Spinner subSecondPicker;
	
	private boolean enableSubSecond;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);
                
        Intent intent = getIntent();
        enableSubSecond = intent.getBooleanExtra("subSecond", false);
        
        setTitle(intent.getStringExtra("Title"));
        
        Spinner spin = (Spinner)findViewById(R.id.timePicker_hours);
        
        ArrayList<String> hourOptions = new ArrayList<String>();
        for(int i = 0; i < 24; i++){
        	hourOptions.add(i + " h");
        }
                
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, hourOptions);
        spin.setAdapter(hourAdapter);
        
        spin.setSelection((int)intent.getLongExtra("hours", 0));
        hourPicker = spin;
        
        spin = (Spinner)findViewById(R.id.timePicker_minutes);
        
        ArrayList<String> minOptions = new ArrayList<String>();
        for(int i = 0; i < 60; i++){
        	minOptions.add(i + " m");
        }
                
        ArrayAdapter<String> minAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, minOptions);
        spin.setAdapter(minAdapter);
        spin.setSelection((int)intent.getLongExtra("minutes", 0));
        
        minutePicker = spin;
        
        spin = (Spinner)findViewById(R.id.timePicker_seconds);
        ArrayList<String> secOptions = new ArrayList<String>();
        
        secOptions.add("0 s");
        
        for(int i = 1; i < 60; i++){
        	secOptions.add(i + " s");
        }
                
        ArrayAdapter<String> secAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, secOptions);
        spin.setAdapter(secAdapter);
        spin.setSelection((int)intent.getLongExtra("seconds", 0));

        secondPicker = spin;

        spin = (Spinner)findViewById(R.id.timePicker_subSeconds);
        ArrayList<String> subSecOptions = new ArrayList<String>();
        
    	subSecOptions.add("0 s");
    	subSecOptions.add("1/30 s");
    	subSecOptions.add("1/15 s");
        subSecOptions.add("1/8 s");
        subSecOptions.add("1/4 s");
        subSecOptions.add("1/2 s");

        ArrayAdapter<String> subSecAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subSecOptions);
        spin.setAdapter(subSecAdapter);
        spin.setSelection(getSubSecondIndex(intent.getLongExtra("subSeconds", 0)));
        
        subSecondPicker = spin;
    	subSecondPicker.setVisibility(View.GONE);
        
        ToggleButton modeChanger = (ToggleButton) findViewById(R.id.timePicker_enableSubSeconds);
        modeChanger.setOnCheckedChangeListener(onCheckedListener);
        if(!enableSubSecond)
        	modeChanger.setVisibility(View.GONE);
        
        if(getSubSecondIndex(intent.getLongExtra("subSeconds", 0)) > 0)
        	modeChanger.setChecked(true);
        
        Button intervalButton = (Button) findViewById(R.id.timePicker_OK);
		intervalButton.setOnTouchListener(this.OnTouchListener());

		intervalButton = (Button) findViewById(R.id.timePicker_CANCEL);
		intervalButton.setOnTouchListener(this.OnTouchListener());
    }
    
    private int getSubSecondIndex(long subSeconds){
    	switch((int)subSeconds){
    	case 0:
    		return 0;
    	case 33:
    		return 1;
    	case 67:
    		return 2;
    	case 125:
    		return 3;
    	case 250:
    		return 4;
    	case 500:
    		return 5;
		default:
			return 0;
    	}
    	
    }
    
    private long getHour(){
    	return hourPicker.getSelectedItemId();
    }
    
    private long getMinute(){
    	return minutePicker.getSelectedItemId();
    }
    
    private long getSecond(){
    	return secondPicker.getSelectedItemId();
    }
    
    private long getSubSecond(){
    	switch((int)subSecondPicker.getSelectedItemId()){
    	case 0:
    		return 0;
    	case 1:
    		return 33;
    	case 2:
    		return 67;
    	case 3:
    		return 125;
    	case 4:
    		return 250;
    	case 5:
    		return 500;
		default:
			return 0;
    	}
    }

    CompoundButton.OnCheckedChangeListener onCheckedListener =
            new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        	if(isChecked){
        		hourPicker.setVisibility(View.GONE);
        		subSecondPicker.setVisibility(View.VISIBLE);
        		
        		hourPicker.setSelection(0);
        	}else{
        		hourPicker.setVisibility(View.VISIBLE);
        		subSecondPicker.setVisibility(View.GONE);
        		
        		subSecondPicker.setSelection(0);
        	}
        }
    };
    
    private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == 0)
					switch(v.getId())
					{
						case R.id.timePicker_CANCEL:
						{
							setResult(RESULT_CANCELED);
							finish();
							break;
						}
						
						case R.id.timePicker_OK:
						{
							Intent intent = new Intent();
							intent.putExtra("hours", getHour());
							intent.putExtra("minutes", getMinute());
							intent.putExtra("seconds", getSecond());
							intent.putExtra("subSeconds", getSubSecond());
							
							setResult(RESULT_OK, intent);
							finish();
							break;
						}
					}
				return true;
			}
		};
	};
}
