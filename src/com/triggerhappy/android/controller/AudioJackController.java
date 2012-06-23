package com.triggerhappy.android.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

public class AudioJackController {
	
	NoisyAudioStreamReceiver myNoisyAudioStreamReceiver;
	
	private class NoisyAudioStreamReceiver extends BroadcastReceiver {
	    @Override
		public void onReceive(Context context, Intent intent) {
	        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
	            // Pause the playback
	        }
	    }

	}

	private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

//	private void startPlayback() {
//	    registerReceiver(new myNoisyAudioStreamReceiver(), intentFilter);
//	}
//
//	private void stopPlayback() {
//	    unregisterReceiver(myNoisyAudioStreamReceiver);
//	}
}
