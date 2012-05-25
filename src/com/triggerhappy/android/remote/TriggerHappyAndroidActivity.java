package com.triggerhappy.android.remote;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TriggerHappyAndroidActivity extends TabActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;
	    
	    tabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
	    intent = new Intent().setClass(this, BasicTriggerActivity.class);
	    
	    spec = tabHost.newTabSpec("basic").setIndicator("Basic").setContent(intent);
	    
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, AdvancedTriggerActivity.class);
	    
	    spec = tabHost.newTabSpec("advanced").setIndicator("Advanced").setContent(intent);
	    
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, AboutTriggerHappyActvity.class);
	    
	    spec = tabHost.newTabSpec("about").setIndicator("About").setContent(intent);
	    
	    tabHost.addTab(spec);
    }
}