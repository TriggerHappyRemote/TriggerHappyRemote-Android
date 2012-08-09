package com.triggerhappy.android.common;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.triggerhappy.android.R;
import com.triggerhappy.android.services.AudioCameraControlService;
import com.triggerhappy.android.view.AboutTriggerHappyActvity;
import com.triggerhappy.android.view.BasicTriggerActivity;
import com.triggerhappy.android.view.BulbRampingTriggerActivity;
import com.triggerhappy.android.view.HDRTriggerHappyActivity;
import com.triggerhappy.android.view.TriggerHappyAndroidActivity;

public abstract class TriggerHappyNavigation extends SherlockFragmentActivity
		implements OnNavigationListener, IProcessorListener {
	private boolean setNavigation;
	protected static final int TIME_SELECTOR = 0;
	protected AudioCameraControlService mBoundService;

	protected boolean mIsBound;
	
	protected boolean isRunning;

	protected ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mBoundService = ((AudioCameraControlService.AudioCameraBinder) service)
					.getService();
			mBoundService.registerListener(TriggerHappyNavigation.this);
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

	public boolean onPrepareOptionsMenu(Menu menu) {
		 return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_nav, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_about: {
				Intent intent = new Intent(this, AboutTriggerHappyActvity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			}
	
			case R.id.menu_start: {
				if(!isRunning)
				{
					this.isRunning = true;
					this.startProcessing();
				}
				return true;
			}
			
			case R.id.menu_stop: {
				this.isRunning = false;
				mBoundService.stopShots();
				return true;
			}
		}
		return false;
	}

	/**
	 * Function to initialize the drop down navigation menu
	 * 
	 * @param pos
	 *            - the position of the currently active nav item
	 */
	protected void initNavigation(int pos) {
		setNavigation = true;
		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
				context, R.array.locations, R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		getSupportActionBar().setDisplayShowTitleEnabled(false);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);
		getSupportActionBar().setSelectedNavigationItem(pos);
	}

	/**
	 * 
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (this.setNavigation) {
			this.setNavigation = false;
			return false;
		}
		Intent intent;
		switch (itemPosition) {
		case 0: {
			intent = new Intent(this, TriggerHappyAndroidActivity.class);
			startActivity(intent);
			break;
		}
		case 1: {
			intent = new Intent(this, BulbRampingTriggerActivity.class);
			startActivity(intent);
			break;
		}
		case 2: {
			intent = new Intent(this, HDRTriggerHappyActivity.class);
			startActivity(intent);
			break;
		}
		case 3: {
			intent = new Intent(this, BasicTriggerActivity.class);
			startActivity(intent);
			break;
		}
		}
		return false;
	}

	public void onFinishEditDialog(String inputText) {
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
	}

	protected void renderTimeDisplay(int id, TimerSettings timer) {
		TextView v = (TextView) findViewById(id);

		String result = "";
		boolean second = false;

		result += (timer.getHour() < 10) ? "0" + timer.getHour() + ":" : timer.getHour() + ":";
		result += (timer.getMinute() < 10) ? "0" + timer.getMinute() + ":" : timer.getMinute() + ":";

		if (timer.getSeconds() != 0) {
			result += (timer.getSeconds() < 10) ? "0" + timer.getSeconds() : timer.getSeconds();
			second = true;
		}

		if (timer.getSubSeconds() != 0) {
			result += " " + timer.getSubSecondString();
			second = true;
		}

		if (!second)
			result += 0;

		result += " >";

		v.setText(result);
	}

	abstract protected void startProcessing();

	public void onProcessorFinish() {
		this.isRunning = false;
//		System.out.println("Finished Running the Batch");
//		System.out.println(this.isRunning);
//		this.invalidateOptionsMenu();
	}

}
