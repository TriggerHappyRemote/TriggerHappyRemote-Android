package com.triggerhappy.android.services;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.ICameraShot;
import com.triggerhappy.android.common.IProcessorListener;
import com.triggerhappy.android.view.TriggerHappyAndroidActivity;
/**
 * 
 * 
 * Interface outline:
 * 	@public fire
 * 	@public stop
 * 	@public startProcessing
 * 	@private openShutter
 * 	@private closeShutter
 * 	
 * @author Christopher Kuchin
 *
 */
public class AudioCameraControlService extends Service implements
		ICameraControl {
	private MediaPlayer mMediaPlayer;
	private ICameraShot pendingShot;
	private AudioManager audioManager;
	private boolean isProcessing;
	
	private Handler mHandler = new Handler();
	private long mStartTime;

	private final List<IProcessorListener> mListeners = new ArrayList<IProcessorListener>();

	public void registerListener(IProcessorListener listener) {
		System.out.println("listeners added");
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
		for (int i = mListeners.size() - 1; i >= 0; i--) {
			System.out.println(i);
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
		audioManager = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);

		this.pendingShot = null;

		this.isProcessing = false;

		mMediaPlayer = MediaPlayer.create(this, R.raw.ms1000);
		mMediaPlayer.setVolume(1, 1);
		mMediaPlayer.setLooping(true);
	}

	@SuppressWarnings({ "deprecation" })
	public boolean remoteConnected() {
		return audioManager.isWiredHeadsetOn();
	}

	public boolean isRunning() {
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

	public void closeShutter() {
		while (mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}

	public void openShutter() {
		// Set the volume of played media to maximum.
		this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		this.mMediaPlayer.start();
		// } else {
		// Toast.makeText(getApplicationContext(), R.string.warning,
		// Toast.LENGTH_LONG).show();
		// }
	}

	public void addShot(ICameraShot shot) {
		this.pendingShot = shot;
	}

	public void startProcessing() {
		if (this.pendingShot == null)
			return;
		
		if (mStartTime == 0L) {
			mStartTime = System.currentTimeMillis();
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 100);
			startForeground(1337, prepareForegroundNotification());
			this.isProcessing = true;
		}

	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			if(pendingShot == null)
				return;
			
			ICameraShot currentShot = pendingShot;
			final long start = mStartTime;
			long millis = SystemClock.uptimeMillis() - start;
			
			switch (currentShot.getStatus()) {
			case SHOT:
				openShutter();
				break;

			case INTERVAL:
				closeShutter();
				break;

			case DONE:
				stopShots();
				return;
			}
			mHandler.postAtTime(this, start
					+ millis + currentShot.getDelay());
		}
	};

	@SuppressWarnings("deprecation")
	private Notification prepareForegroundNotification() {

		Notification note = new Notification(R.drawable.ic_launcher,
				"Camera Timer Started", System.currentTimeMillis());
		Intent i = new Intent(this, TriggerHappyAndroidActivity.class);

		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

		note.setLatestEventInfo(this, "Trigger Happy Remote", "X Shots Taken",
				pi);
		note.flags |= Notification.FLAG_NO_CLEAR;

		return note;
	}

	@Override
	public void stopShots() {
		mHandler.removeCallbacks(mUpdateTimeTask);
		this.closeShutter();
		this.pendingShot = null;
		this.isProcessing = false;
		stopForeground(true);
		processFinished();
		this.mStartTime = 0L;
	}
}
