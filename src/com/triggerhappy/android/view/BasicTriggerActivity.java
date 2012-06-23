package com.triggerhappy.android.view;

import android.app.Activity;
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
import android.widget.ToggleButton;

import com.triggerhappy.android.R;
import com.triggerhappy.android.services.AudioCameraControlService;

public class BasicTriggerActivity extends Activity {

	private ToggleButton toggle;

	private AudioCameraControlService mBoundService;
	
	private boolean mIsBound;

	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        mBoundService = ((AudioCameraControlService.AudioCameraBinder)service).getService();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	        mBoundService = null;
	    }
	};

	void doBindService() {
		System.out.println("Bind the Service Please");
	    getApplicationContext().bindService(new Intent(this, 
	            AudioCameraControlService.class), mConnection, Context.BIND_AUTO_CREATE);
	    mIsBound = true;
	}

	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        getApplicationContext().unbindService(mConnection);
	        mIsBound = false;
	    }
	}

	private OnTouchListener OnTouchListener() {
		return new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if(!mIsBound)
					return false;
				if (toggle.isChecked()) {
					if (event.getAction() == 0) {
						if (mBoundService.isPlaying())
							mBoundService.closeShutter();
						else
							mBoundService.openShutter();
					}
				} else {
					if (event.getAction() == 1) {
						mBoundService.closeShutter();
					} else if (event.getAction() == 0) {
						mBoundService.openShutter();
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
		
		this.doBindService();

		setContentView(R.layout.basic);

		Button primaryFire = (Button) findViewById(R.id.fireButton);
		primaryFire.setOnTouchListener(this.OnTouchListener());

		toggle = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	protected void onDestroy() {
	    super.onDestroy();
	    doUnbindService();

	}
}