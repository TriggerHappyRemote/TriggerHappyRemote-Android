package com.triggerhappy.android.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class BasicTriggerActivity extends TriggerHappyNavigation {

	private ToggleButton toggle;
	private boolean isPlaying;

	private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if(!mIsBound)
					return false;
				if (toggle.isChecked()) {
					if (event.getAction() == 0) {
						if (isPlaying){
							mBoundService.closeShutter();
							isPlaying = false;
						}else{
							mBoundService.openShutter();
							isPlaying = true;
						}
					}
				} else {
					if (event.getAction() == 1) {
						mBoundService.closeShutter();
						isPlaying = false;
					} else if (event.getAction() == 0) {
						mBoundService.openShutter();
						isPlaying = true;
					}
				}
				return false;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mIsBound = false;
		isPlaying = false;
		
		this.doBindService();

		setContentView(R.layout.basic);

		Button primaryFire = (Button) findViewById(R.id.fireButton);
		primaryFire.setOnTouchListener(this.OnTouchListener());

		toggle = (ToggleButton) findViewById(R.id.toggleButton1);
		
		this.initNavigation(3);
	}

	protected void onDestroy() {
	    super.onDestroy();
	    super.doUnbindService();

	}

	@Override
	protected void startProcessing() {
		// TODO Auto-generated method stub
		
	}
}