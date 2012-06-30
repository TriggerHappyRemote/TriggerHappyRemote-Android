package com.triggerhappy.android.view;

import android.os.Bundle;

import com.triggerhappy.android.R;
import com.triggerhappy.android.common.TriggerHappyNavigation;

public class HDRTriggerHappyActivity extends TriggerHappyNavigation{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.hdr);
	    
	    this.initNavigation(1);
    }
}
