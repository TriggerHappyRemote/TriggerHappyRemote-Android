package com.triggerhappy.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.triggerhappy.android.R;
import com.triggerhappy.android.common.ICameraShot;
import com.triggerhappy.android.common.IProcessorListener;
import com.triggerhappy.android.common.TriggerHappyNavigation;
import com.triggerhappy.android.services.AudioCameraControlService;

public class ShotStatusActivity extends TriggerHappyNavigation implements
		IProcessorListener {
	protected AudioCameraControlService mBoundService;
	protected boolean mIsBound;
	protected TextView mTimer;
	protected ICameraShot nextShot = null;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.status_nav, menu);
		
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_stop: {
				mBoundService.stopShots();
				finish();
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		mIsBound = false;
		nextShot = null;
		doBindService();
		Intent intent = getIntent();

		if(intent != null){
			if(intent.hasExtra("Shot")){
				nextShot = (ICameraShot) intent.getExtras().get("Shot");
			}
		}
		
		mTimer = (TextView) findViewById(R.id.StatusCountDown);
	}

	@Override
	public void onProcessorFinish() {
		finish();
		
		System.out.println("Finished but i didn't finish");
	}
	
	private void processQueue(){
		if(mIsBound){
			// Prep and start he service processing the shot
			mBoundService.addShot(nextShot);
			mBoundService.startProcessing();
		}
		
		if(!mBoundService.isRunning())
			finish();
			

		new CountDownTimer(nextShot.getDuration(), 100) {

			public void onTick(long millisUntilFinished) {
				renderTimer(millisUntilFinished);
			}

			public void onFinish() {
				mTimer.setText("done!");
			}
		}.start();
	}

	protected ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mBoundService = ((AudioCameraControlService.AudioCameraBinder) service)
					.getService();
			mBoundService.registerListener(ShotStatusActivity.this);
			mIsBound = true;
			System.out.println("SERvice bound!!");
			
			processQueue();
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

	private void renderTimer(long timeRemaining){
		String hour = "00";
		String min = "00";
		String sec = "00";
		String tenth = "0";
		int tmp = 0;
		
		if (timeRemaining > HOUR){
			tmp = (int) (timeRemaining / HOUR);
			hour = (tmp > 10) ? tmp + "" : "0" + tmp;
			
			timeRemaining %= HOUR;
		}
		
		if (timeRemaining > MINUTE){
			tmp = (int) (timeRemaining / MINUTE);
			min = (tmp > 10) ? tmp + "" : "0" + tmp;
			
			timeRemaining %= MINUTE;
		}
		
		if (timeRemaining > SECOND){
			tmp = (int) (timeRemaining / SECOND);
			sec = (tmp > 10) ? tmp + "" : "0" + tmp;
			
			timeRemaining %= SECOND;
		}
		
		if(timeRemaining > TENTH){
			tmp = (int) (timeRemaining / TENTH);
			tenth = tmp + "";
			
			timeRemaining %= TENTH;
		}
		
		mTimer.setText(hour + ":" + min + ":" + sec + "." + tenth);
	}
}
