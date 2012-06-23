package com.triggerhappy.android.services;

import java.util.ArrayList;
import java.util.List;

import com.triggerhappy.android.common.ICameraShot;

import com.triggerhappy.android.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class AudioCameraControlService extends Service implements ICameraControl{
	private MediaPlayer mMediaPlayer;
	private List<ICameraShot> pendingShots;
	
	// Binder given to clients
    private final IBinder mBinder = new AudioCameraBinder();

    public class AudioCameraBinder extends Binder {
    	public AudioCameraControlService getService() {
            return AudioCameraControlService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
    	System.out.println("Service Bound...");
        return mBinder;
    }

	@Override
	public void onCreate(){
		super.onCreate();
    	System.out.println("Service Create...");
		
		this.pendingShots = new ArrayList<ICameraShot>();
		
		mMediaPlayer = MediaPlayer.create(this, R.raw.ms1000);
		mMediaPlayer.setLooping(true);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		if(mMediaPlayer != null){
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		
	}	
	
	public void fireShutter(long shutterDuration) {
		// TODO Auto-generated method stub
		
	}

	public void closeShutter() {
		while(mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}

	public void openShutter() {
		mMediaPlayer.start();
	}

	public void addShot(ICameraShot shot) {
		this.pendingShots.add(shot);
	}

	/**
	 * This is the function that will take one or more ICamerShot
	 * Objects and "process" them turning them into 
	 * the proper combination of sound and silence
	 * 
	 * 
	 */
	public void processShots() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isPlaying(){
		return mMediaPlayer.isPlaying();
	}
	
}
