package com.triggerhappy.android.services;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.ICameraShot;

public class AudioCameraControlService extends Service implements
		ICameraControl {
	private MediaPlayer mMediaPlayer;
	private Queue<ICameraShot> pendingShots;
	private AudioManager audioManager;
	
	private Timer shotScheduler;

	// Binder given to clients
	private final IBinder mBinder = new AudioCameraBinder();

	public class AudioCameraBinder extends Binder {
		public AudioCameraControlService getService() {
			return AudioCameraControlService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		shotScheduler = new Timer();
		audioManager = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);

		this.pendingShots = new PriorityQueue<ICameraShot>();

		mMediaPlayer = MediaPlayer.create(this, R.raw.test);
		mMediaPlayer.setLooping(true);
	}

	@SuppressWarnings("deprecation")
	private boolean remoteConnected() {
		return audioManager.isWiredHeadsetOn();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}

	public void fireShutter(long shutterDuration) {
		// TODO Auto-generated method stub

	}

	public void closeShutter() {
		while (mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}

	public void openShutter() {
//		if (this.remoteConnected()) {
			mMediaPlayer.start();
//		} else {
//			Toast.makeText(getApplicationContext(), R.string.warning, Toast.LENGTH_LONG).show();
//		}
	}

	public void addShot(ICameraShot shot) {
		this.pendingShots.add(shot);
	}
	
	private class qProcessTask extends  TimerTask {
		@Override
		public void run() {
			processShots();
		}
	}
	/**
	 * This is the function that will take one or more ICamerShot Objects and
	 * "process" them turning them into the proper combination of sound and
	 * silence
	 * 
	 * 
	 */
	public void processShots() {
		ICameraShot currentShot = pendingShots.peek();
		switch (currentShot.getStatus()) {
		case SHOT:
			openShutter();
			shotScheduler.schedule(new qProcessTask(), currentShot.getDelay());
			break;

		case INTERVAL:
			closeShutter();
			shotScheduler.schedule(new qProcessTask(), currentShot.getDelay());
			
			break;

		case DONE:
			ICameraShot isEmpty = pendingShots.poll();
			closeShutter();
			
			if(isEmpty == null){
				shotScheduler.cancel();
			}
			else{
				shotScheduler.schedule(new qProcessTask(), 0);
			}
			
			break;
		
		
		default:
			shotScheduler.cancel();
			break;
		}
		
	}
}
