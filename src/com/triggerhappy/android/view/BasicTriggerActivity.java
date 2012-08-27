package com.triggerhappy.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.ICameraShot;
import com.triggerhappy.android.common.TriggerHappyNavigation;
import com.triggerhappy.android.services.AudioCameraControlService;

public class BasicTriggerActivity extends TriggerHappyNavigation {

	private ToggleButton toggle;
	private boolean isPlaying;
	protected AudioCameraControlService mBoundService;
	protected boolean mIsBound;
	protected TextView mTimer;
	protected ICameraShot nextShot = null;

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
	    this.doUnbindService();

	}

	@Override
	protected void startProcessing() {
		// TODO Auto-generated method stub
		
	}
	protected ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mBoundService = ((AudioCameraControlService.AudioCameraBinder) service)
					.getService();
			mIsBound = true;
		}

		public void onServiceDisconnected(ComponentName className) {
			mBoundService = null;
		}
	};

	protected void doBindService() {
		getApplicationContext().bindService(
				new Intent(this, AudioCameraControlService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	protected void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			getApplicationContext().unbindService(mConnection);
			mIsBound = false;
		}
	}
}