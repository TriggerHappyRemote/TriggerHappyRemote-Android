package com.triggerhappy.android.common;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.triggerhappy.android.R;
import com.triggerhappy.android.view.AdvancedTriggerActivity;
import com.triggerhappy.android.view.BasicTriggerActivity;
import com.triggerhappy.android.view.HDRTriggerHappyActivity;
import com.triggerhappy.android.view.TriggerHappyAndroidActivity;

public class TriggerHappyNavigation extends SherlockActivity implements OnNavigationListener {
	private boolean setNavigation;
	
	/**
	 * Function to initialize the drop down navigation menu
	 * 
	 * @param pos - the position of the currently active nav item
	 */
	protected void initNavigation(int pos){
		setNavigation = true;
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
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
		if(this.setNavigation){
			this.setNavigation = false;
			return false;
		}
		Intent intent;
		switch(itemPosition)
		{
			case 0:
			{
				intent = new Intent(this, TriggerHappyAndroidActivity.class);
				break;
			}
			case 1:
			{
				intent = new Intent(this, AdvancedTriggerActivity.class);
				startActivity(intent);
				break;
			}
			case 2:
			{
				intent = new Intent(this, HDRTriggerHappyActivity.class);
				startActivity(intent);
				break;
			}
			case 3:
			{
				intent = new Intent(this, BasicTriggerActivity.class);
				startActivity(intent);
				break;
			}
		}
		
		// TODO Auto-generated method stub
		return false;
	}    
}
