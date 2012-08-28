package com.triggerhappy.android.common;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.triggerhappy.android.R;
import com.triggerhappy.android.view.BasicTriggerActivity;
import com.triggerhappy.android.view.BulbRampingTriggerActivity;
import com.triggerhappy.android.view.HDRTriggerHappyActivity;
import com.triggerhappy.android.view.TriggerHappyAndroidActivity;

public abstract class TriggerHappyNavigation extends SherlockFragmentActivity
		implements OnNavigationListener {
	private boolean setNavigation;
	protected static final int TIME_SELECTOR = 0;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_nav, menu);
		return true;
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

		if(timer.getHour() > 0)
			result += (timer.getMinute() > 0 || timer.getSeconds() > 0 || timer.getSubSeconds() > 0) ? timer.getHour() + ":" : timer.getHour();
		
		if(timer.getMinute() > 0)
			result += (timer.getSeconds() > 0 || timer.getSubSeconds() > 0) ? "0" + timer.getMinute() + ":" : timer.getMinute() + ":";

		if (timer.getSeconds() != 0) {
			result += (timer.getSeconds() < 10 && (timer.getHour() > 0 || timer.getMinute() > 0)) ? "0" + timer.getSeconds() : timer.getSeconds() ;
			second = true;
		}

		if (timer.getSubSeconds() != 0) {
			result += " " + timer.getSubSecondString();
			second = true;
		}
		
		if(timer.getSubSeconds() > 0 || timer.getSeconds() > 0)
			result += " s";

		if (!second)
			result += 00;

		result += " >";

		v.setText(result);
	}
}
