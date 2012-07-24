package com.triggerhappy.android.services;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.ICameraShot;
import com.triggerhappy.android.common.IProcessorListener;
import com.triggerhappy.android.view.TriggerHappyAndroidActivity;

public class AudioCameraControlService extends Service implements
		ICameraControl {
	private MediaPlayer mMediaPlayer;
	private Queue<ICameraShot> pendingShots;
	private AudioManager audioManager;
	private boolean isProcessing;
	
	private final List<IProcessorListener> mListeners = new ArrayList<IProcessorListener>();

	private Timer shotScheduler;

	public void registerListener(IProcessorListener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(IProcessorListener listener) {
        mListeners.remove(listener);
    }

	// Binder given to clients
	private final IBinder mBinder = new AudioCameraBinder();

	public class AudioCameraBinder extends Binder {
		public AudioCameraControlService getService() {
			return AudioCameraControlService.this;
		}
	}

	private void processFinished() {
        for (int i=mListeners.size()-1; i>=0; i--) {
            mListeners.get(i).onProcessorFinish();
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
		
		this.isProcessing = false;

		mMediaPlayer = MediaPlayer.create(this, R.raw.test);
		mMediaPlayer.setLooping(true);
	}

	@SuppressWarnings({ "deprecation"})
	public boolean remoteConnected() {
		return audioManager.isWiredHeadsetOn();
	}
	
	public boolean isRunning(){
		return isProcessing;
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
	
	public void startProcessing(){
		if(this.pendingShots.isEmpty())
			return;
		
		Notification note=new Notification(R.drawable.ic_launcher,
                "Camera Timer Started",
                System.currentTimeMillis());
		Intent i=new Intent(this, TriggerHappyAndroidActivity.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
		Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent pi=PendingIntent.getActivity(this, 0,
		                        i, 0);
		
		note.setLatestEventInfo(this, "Trigger Happy Remote",
		    "X Shots Taken",
		    pi);
		note.flags|=Notification.FLAG_NO_CLEAR;
		
		startForeground(1337, note);

		this.isProcessing = true;
		this.processShots();
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
			shotScheduler = new Timer();
			shotScheduler.schedule(new qProcessTask(), currentShot.getDelay());
			break;

		case INTERVAL:
			closeShutter();
			shotScheduler = new Timer();
			shotScheduler.schedule(new qProcessTask(), currentShot.getDelay());
			break;

		case DONE:
			stopForeground(true);
			this.isProcessing = false;
			closeShutter();
			processFinished();
			break;
		}
		
	}

	@Override
	public void stopShots() {
		this.closeShutter();
		this.shotScheduler.cancel();
		this.pendingShots.clear();
		this.isProcessing = false;
		shotScheduler = new Timer();
		stopForeground(true);
	}
}
