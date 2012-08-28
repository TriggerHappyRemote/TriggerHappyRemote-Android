package com.triggerhappy.android.common;

import android.content.Intent;

import com.actionbarsherlock.view.MenuItem;
import com.triggerhappy.android.R;
import com.triggerhappy.android.view.AboutTriggerHappyActvity;

public abstract class TriggerHappyNavigationShots extends TriggerHappyNavigation {

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
				this.startProcessing();
				return true;
			}
		}
		return false;
	}
	
	abstract protected void startProcessing();

}
